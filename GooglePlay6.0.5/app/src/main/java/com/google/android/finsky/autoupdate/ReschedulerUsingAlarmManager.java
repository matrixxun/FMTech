package com.google.android.finsky.autoupdate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.gearhead.GearheadStateMonitor;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;

public final class ReschedulerUsingAlarmManager
  extends ReschedulerStrategy
{
  private static PendingIntent getPendingIntent()
  {
    Intent localIntent = new Intent(FinskyApp.get(), CheckWifiAndAutoUpdate.class);
    return PendingIntent.getService(FinskyApp.get(), 0, localIntent, 0);
  }
  
  static void setAlarm()
  {
    AlarmManager localAlarmManager = (AlarmManager)FinskyApp.get().getSystemService("alarm");
    PendingIntent localPendingIntent = getPendingIntent();
    Long localLong = (Long)G.autoUpdateWifiCheckIntervalMs.get();
    if (localLong.longValue() > 0L) {
      localAlarmManager.set(3, SystemClock.elapsedRealtime() + localLong.longValue(), localPendingIntent);
    }
    FinskyLog.d("Scheduling recheck in %d MS", new Object[] { localLong });
  }
  
  public final boolean canUpdateNow()
  {
    return (!GearheadStateMonitor.getInstance().isProjecting()) && (!shouldWaitForWifi());
  }
  
  public final void schedule()
  {
    ((AlarmManager)FinskyApp.get().getSystemService("alarm")).cancel(getPendingIntent());
    FinskyLog.d("Canceling auto-update wifi check.", new Object[0]);
    setAlarm();
    if (shouldWaitForWifi())
    {
      FinskyLog.d("Checking wifi: disabled, will check wifi again later.", new Object[0]);
      logAutoUpdateRescheduled(false);
    }
  }
  
  public static class CheckWifiAndAutoUpdate
    extends Service
    implements UpdateChecker.AutoUpdateCompletionStatusListener
  {
    public IBinder onBind(Intent paramIntent)
    {
      return null;
    }
    
    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
    {
      if (ReschedulerStrategy.shouldWaitForWifi())
      {
        FinskyLog.d("Checking wifi: disabled, will check wifi again later.", new Object[0]);
        ReschedulerStrategy.logAutoUpdateRescheduled(false);
        ReschedulerUsingAlarmManager.setAlarm();
        stopSelf();
      }
      for (;;)
      {
        return 2;
        ReschedulerStrategy.loadPrerequisites(new Runnable()
        {
          public final void run()
          {
            if (GearheadStateMonitor.getInstance().isProjecting())
            {
              ReschedulerUsingAlarmManager.setAlarm();
              ReschedulerUsingAlarmManager.CheckWifiAndAutoUpdate.this.stopSelf();
              return;
            }
            FinskyLog.d("Checking wifi: enabled, proceeding with auto-update.", new Object[0]);
            ReschedulerStrategy.continueToUpdateChecks(ReschedulerUsingAlarmManager.CheckWifiAndAutoUpdate.this, true);
          }
        });
      }
    }
    
    public final void updateCheckComplete(boolean paramBoolean)
    {
      stopSelf();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.autoupdate.ReschedulerUsingAlarmManager
 * JD-Core Version:    0.7.0.1
 */