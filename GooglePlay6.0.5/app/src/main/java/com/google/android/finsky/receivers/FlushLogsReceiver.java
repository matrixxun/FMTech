package com.google.android.finsky.receivers;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.WakefulBroadcastReceiver;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.SelfUpdateScheduler;
import com.google.android.play.utils.config.GservicesValue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FlushLogsReceiver
  extends WakefulBroadcastReceiver
{
  private static final Intent INTENT_RECEIVER = new Intent(FinskyApp.get(), FlushLogsReceiver.class);
  private static final Intent INTENT_SERVICE = new Intent(FinskyApp.get(), FlushLogsService.class);
  private static Handler sScheduleLogsFlushHandler;
  private static Runnable sScheduleLogsFlushRunnable = new Runnable()
  {
    public final void run()
    {
      FlushLogsReceiver.scheduleLogsFlush(FinskyApp.get(), ((Long)G.flushLogsOnAppExitTimeoutMs.get()).longValue());
    }
  };
  
  public static void cancelLogsFlush()
  {
    getHandler().removeCallbacks(sScheduleLogsFlushRunnable);
    FinskyApp localFinskyApp = FinskyApp.get();
    if (((Long)FinskyPreferences.logsFlushScheduleExpiredTimestampMs.get()).longValue() > 0L)
    {
      FinskyPreferences.logsFlushScheduleExpiredTimestampMs.put(Long.valueOf(0L));
      ((AlarmManager)localFinskyApp.getSystemService("alarm")).cancel(PendingIntent.getBroadcast(localFinskyApp, 0, INTENT_RECEIVER, 0));
    }
  }
  
  private static Handler getHandler()
  {
    try
    {
      sScheduleLogsFlushHandler = new Handler(Looper.getMainLooper());
      return sScheduleLogsFlushHandler;
    }
    finally {}
  }
  
  static void scheduleLogsFlush(Context paramContext, long paramLong)
  {
    long l1 = ((Long)FinskyPreferences.logsFlushScheduleExpiredTimestampMs.get()).longValue();
    long l2 = System.currentTimeMillis();
    if ((l1 > 0L) && (l1 >= l2)) {
      return;
    }
    long l3 = l2 + paramLong;
    long l4 = l3 + ((Long)G.flushLogsScheduleExpiredTimeoutMs.get()).longValue();
    FinskyPreferences.logsFlushScheduleExpiredTimestampMs.put(Long.valueOf(l4));
    ((AlarmManager)paramContext.getSystemService("alarm")).set(0, l3, PendingIntent.getBroadcast(paramContext, 0, INTENT_RECEIVER, 0));
  }
  
  public static void scheduleLogsFlushImmediate()
  {
    scheduleLogsFlush(FinskyApp.get(), 0L);
  }
  
  public static void scheduleLogsFlushOnAppExit()
  {
    if (!FinskyApp.get().getExperiments().isEnabled(12603514L)) {
      return;
    }
    if (FinskyApp.get().mSelfUpdateScheduler.mUpdateInProgress)
    {
      cancelLogsFlush();
      scheduleLogsFlush(FinskyApp.get(), ((Long)G.flushLogsOnAppExitSelfUpdateTimeoutMs.get()).longValue());
      return;
    }
    getHandler().postDelayed(sScheduleLogsFlushRunnable, ((Long)G.flushLogsNoUserActivityTimeoutMs.get()).longValue());
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    startWakefulService(paramContext, INTENT_SERVICE);
  }
  
  public static class FlushLogsService
    extends IntentService
  {
    public FlushLogsService()
    {
      super();
    }
    
    protected void onHandleIntent(Intent paramIntent)
    {
      Account[] arrayOfAccount = AccountHandler.getAccounts(FinskyApp.get());
      final CountDownLatch localCountDownLatch = new CountDownLatch(arrayOfAccount.length);
      Runnable local1 = new Runnable()
      {
        public final void run()
        {
          localCountDownLatch.countDown();
        }
      };
      int i = arrayOfAccount.length;
      for (int j = 0; j < i; j++)
      {
        Account localAccount = arrayOfAccount[j];
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = FinskyLog.scrubPii(localAccount.name);
        FinskyLog.d("Flushing event logs for %s", arrayOfObject2);
        FinskyApp.get().getEventLogger(localAccount).flush(local1);
      }
      try
      {
        if (!localCountDownLatch.await(((Long)G.flushLogsWaitTimeoutMs.get()).longValue(), TimeUnit.MILLISECONDS))
        {
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = G.flushLogsWaitTimeoutMs.get();
          FinskyLog.d("Logs flushing took more than %d ms. Releasing wake lock.", arrayOfObject1);
        }
        FinskyPreferences.logsFlushScheduleExpiredTimestampMs.put(Long.valueOf(0L));
        WakefulBroadcastReceiver.completeWakefulIntent(paramIntent);
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;)
        {
          FinskyLog.d("Logs flushing was interrupted. Releasing wake lock.", new Object[0]);
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.FlushLogsReceiver
 * JD-Core Version:    0.7.0.1
 */