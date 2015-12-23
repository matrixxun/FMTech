package com.google.android.finsky.billing.iab;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.VendingProtos.AppDataProto;
import com.google.android.finsky.protos.VendingProtos.CheckForNotificationsRequestProto;
import com.google.android.finsky.protos.VendingProtos.DataMessageProto;
import com.google.android.finsky.protos.VendingProtos.GetMarketMetadataResponseProto;
import com.google.android.finsky.protos.VendingProtos.PendingNotificationsProto;
import com.google.android.finsky.utils.BackgroundThreadFactory;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.api.VendingRequest;

public class PendingNotificationsService
  extends Service
{
  private static String ACTION_ALARM = "action_alarm";
  public static String ACTION_RESTART_ALARM = "action_restart_alarm";
  
  private static PendingIntent createPendingIntentForMarketAlarm(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent(ACTION_ALARM);
    localIntent.setClass(paramContext, PendingNotificationsService.class);
    localIntent.putExtra("account", paramString);
    localIntent.setData(Uri.fromParts("vendingpending", paramString, null));
    return PendingIntent.getService(paramContext, 0, localIntent, 1073741824);
  }
  
  public static boolean handlePendingNotifications(Context paramContext, String paramString, VendingProtos.PendingNotificationsProto paramPendingNotificationsProto, boolean paramBoolean)
  {
    boolean bool1 = paramPendingNotificationsProto.hasNextCheckMillis;
    boolean bool2 = false;
    int i;
    int k;
    label84:
    VendingProtos.DataMessageProto localDataMessageProto;
    Intent localIntent2;
    if (bool1)
    {
      long l = paramPendingNotificationsProto.nextCheckMillis;
      if (FinskyLog.DEBUG)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Long.valueOf(l);
        FinskyLog.v("Got next_check_millis=%d", arrayOfObject2);
      }
      if (l > 0L)
      {
        setMarketAlarm(FinskyApp.get(), paramString, l);
        bool2 = true;
      }
    }
    else
    {
      i = 0;
      VendingProtos.DataMessageProto[] arrayOfDataMessageProto = paramPendingNotificationsProto.notification;
      int j = arrayOfDataMessageProto.length;
      k = 0;
      if (k >= j) {
        break label318;
      }
      localDataMessageProto = arrayOfDataMessageProto[k];
      String str = localDataMessageProto.category;
      if (FinskyLog.DEBUG) {
        FinskyLog.v("Processing pending notification with category=%s", new Object[] { str });
      }
      Intent localIntent1 = new Intent("com.google.android.c2dm.intent.RECEIVE");
      localIntent1.addCategory(str);
      localIntent2 = IntentUtils.createIntentForReceiver(paramContext.getPackageManager(), FinskyApp.get().getPackageName(), localIntent1);
      if (localIntent2 != null) {
        break label251;
      }
      FinskyLog.w("Cannot find receiver for intent category: %s", new Object[] { str });
    }
    for (;;)
    {
      k++;
      break label84;
      bool2 = false;
      if (!paramBoolean) {
        break;
      }
      FinskyApp localFinskyApp = FinskyApp.get();
      FinskyLog.d("Canceling alarm for account=%s", new Object[] { paramString });
      VendingPreferences.getMarketAlarmStartTime(paramString).put(Long.valueOf(0L));
      PendingIntent localPendingIntent = createPendingIntentForMarketAlarm(localFinskyApp, paramString);
      ((AlarmManager)localFinskyApp.getSystemService("alarm")).cancel(localPendingIntent);
      bool2 = true;
      break;
      label251:
      for (VendingProtos.AppDataProto localAppDataProto : localDataMessageProto.appData) {
        localIntent2.putExtra(localAppDataProto.key, localAppDataProto.value);
      }
      paramContext.sendOrderedBroadcast(localIntent2, null);
      bool2 = true;
      i++;
    }
    label318:
    if (FinskyLog.DEBUG)
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(i);
      FinskyLog.v("Handled %d notifications.", arrayOfObject1);
    }
    return bool2;
  }
  
  public static void setMarketAlarm(Context paramContext, String paramString, long paramLong)
  {
    setMarketAlarm(paramContext, paramString, paramLong, System.currentTimeMillis());
  }
  
  public static void setMarketAlarm(Context paramContext, String paramString, long paramLong1, long paramLong2)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramString;
    arrayOfObject[1] = Long.valueOf(paramLong1);
    FinskyLog.d("Setting alarm for account=%s, duration=%d", arrayOfObject);
    PendingIntent localPendingIntent = createPendingIntentForMarketAlarm(paramContext, paramString);
    ((AlarmManager)paramContext.getSystemService("alarm")).set(3, paramLong1 + SystemClock.elapsedRealtime(), localPendingIntent);
    VendingPreferences.getMarketAlarmStartTime(paramString).put(Long.valueOf(paramLong2));
    VendingPreferences.getMarketAlarmTimeout(paramString).put(Long.valueOf(paramLong1));
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    if (paramIntent == null)
    {
      stopSelf();
      return 2;
    }
    String str1 = paramIntent.getAction();
    if (str1.equals(ACTION_ALARM))
    {
      final String str2 = paramIntent.getStringExtra("account");
      VendingApi localVendingApi = FinskyApp.get().getVendingApi(str2);
      Response.Listener local1 = new Response.Listener() {};
      Response.ErrorListener local2 = new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          FinskyLog.d("CheckForPendingNotifications failed: error=%s", new Object[] { paramAnonymousVolleyError });
          PendingNotificationsService.access$000(FinskyApp.get(), str2);
          PendingNotificationsService.this.stopSelf();
        }
      };
      VendingRequest localVendingRequest = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.CheckForNotificationsRequestProto.class, new VendingProtos.CheckForNotificationsRequestProto(), VendingProtos.GetMarketMetadataResponseProto.class, local1, localVendingApi.mApiContext, local2);
      localVendingApi.mRequestQueue.add(localVendingRequest);
      return 2;
    }
    if (str1.equals(ACTION_RESTART_ALARM))
    {
      BackgroundThreadFactory.createThread(new Runnable()
      {
        public final void run()
        {
          Account[] arrayOfAccount = AccountHandler.getAccounts(PendingNotificationsService.this);
          int i = arrayOfAccount.length;
          int j = 0;
          if (j < i)
          {
            String str = arrayOfAccount[j].name;
            if (FinskyLog.DEBUG) {
              FinskyLog.v("Checking for pending alarms for account=%s", new Object[] { str });
            }
            long l1 = ((Long)VendingPreferences.getMarketAlarmStartTime(str).get()).longValue();
            if (l1 == 0L) {
              if (FinskyLog.DEBUG) {
                FinskyLog.v("No pending alarm.", new Object[0]);
              }
            }
            for (;;)
            {
              j++;
              break;
              long l2 = ((Long)VendingPreferences.getMarketAlarmTimeout(str).get()).longValue();
              long l3 = System.currentTimeMillis();
              long l4 = l3 - l1;
              if (l4 < 0L)
              {
                Object[] arrayOfObject2 = new Object[2];
                arrayOfObject2[0] = Long.valueOf(l3);
                arrayOfObject2[1] = Long.valueOf(l1);
                FinskyLog.e("Current time is wrong? current time=%d, alarm start time=%d", arrayOfObject2);
                l4 = 0L;
              }
              long l5 = l2 - l4;
              if (l5 < 20000L)
              {
                if (FinskyLog.DEBUG)
                {
                  Object[] arrayOfObject1 = new Object[1];
                  arrayOfObject1[0] = Long.valueOf(l5);
                  FinskyLog.v("remaining=%d, delaying alarm for a while.", arrayOfObject1);
                }
                l5 = 20000L;
              }
              PendingNotificationsService.setMarketAlarm(PendingNotificationsService.this, str, l5, l3);
            }
          }
          PendingNotificationsService.this.stopSelf();
        }
      }).start();
      return 2;
    }
    FinskyLog.e("unexpected action: %s", new Object[] { str1 });
    stopSelf();
    return 2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.iab.PendingNotificationsService
 * JD-Core Version:    0.7.0.1
 */