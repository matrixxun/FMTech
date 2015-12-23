package com.google.android.gcm;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.util.Log;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class GCMBaseIntentService
  extends IntentService
{
  private static final Object LOCK = GCMBaseIntentService.class;
  private static final int MAX_BACKOFF_MS = (int)TimeUnit.SECONDS.toMillis(3600L);
  private static final String TOKEN = Long.toBinaryString(sRandom.nextLong());
  private static int sCounter = 0;
  private static final Random sRandom = new Random();
  private static PowerManager.WakeLock sWakeLock;
  private final String[] mSenderIds;
  
  protected GCMBaseIntentService()
  {
    this(getName("DynamicSenderIds"), null);
  }
  
  private GCMBaseIntentService(String paramString, String[] paramArrayOfString)
  {
    super(paramString);
    this.mSenderIds = paramArrayOfString;
  }
  
  public GCMBaseIntentService(String... paramVarArgs)
  {
    this(getName(GCMRegistrar.getFlatSenderIds(paramVarArgs)), paramVarArgs);
  }
  
  private static String getName(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder("GCMIntentService-").append(paramString).append("-");
    int i = 1 + sCounter;
    sCounter = i;
    String str = i;
    Log.v("GCMBaseIntentService", "Intent service name: " + str);
    return str;
  }
  
  static void runIntentInService(Context paramContext, Intent paramIntent, String paramString)
  {
    synchronized (LOCK)
    {
      if (sWakeLock == null) {
        sWakeLock = ((PowerManager)paramContext.getSystemService("power")).newWakeLock(1, "GCM_LIB");
      }
      Log.v("GCMBaseIntentService", "Acquiring wakelock");
      sWakeLock.acquire();
      paramIntent.setClassName(paramContext, paramString);
      paramContext.startService(paramIntent);
      return;
    }
  }
  
  public abstract void onError$5ffc00fd(String paramString);
  
  public final void onHandleIntent(Intent paramIntent)
  {
    try
    {
      Context localContext = getApplicationContext();
      String str1 = paramIntent.getAction();
      String str6;
      String str7;
      if (str1.equals("com.google.android.c2dm.intent.REGISTRATION"))
      {
        GCMRegistrar.setRetryBroadcastReceiver(localContext);
        String str5 = paramIntent.getStringExtra("registration_id");
        str6 = paramIntent.getStringExtra("error");
        str7 = paramIntent.getStringExtra("unregistered");
        Log.d("GCMBaseIntentService", "handleRegistration: registrationId = " + str5 + ", error = " + str6 + ", unregistered = " + str7);
        if (str5 != null)
        {
          GCMRegistrar.resetBackoff(localContext);
          GCMRegistrar.setRegistrationId(localContext, str5);
          onRegistered$5ffc00fd(str5);
        }
      }
      synchronized (LOCK)
      {
        while (sWakeLock != null)
        {
          Log.v("GCMBaseIntentService", "Releasing wakelock");
          sWakeLock.release();
          throw localObject1;
          Log.d("GCMBaseIntentService", "Registration error: " + str6);
          if ("SERVICE_NOT_AVAILABLE".equals(str6))
          {
            k = localContext.getSharedPreferences("com.google.android.gcm", 0).getInt("backoff_ms", 3000);
            m = k / 2 + sRandom.nextInt(k);
            Log.d("GCMBaseIntentService", "Scheduling registration retry, backoff = " + m + " (" + k + ")");
            localIntent2 = new Intent("com.google.android.gcm.intent.RETRY");
            localIntent2.putExtra("token", TOKEN);
            localPendingIntent = PendingIntent.getBroadcast(localContext, 0, localIntent2, 0);
            ((AlarmManager)localContext.getSystemService("alarm")).set(3, SystemClock.elapsedRealtime() + m, localPendingIntent);
            if (k < MAX_BACKOFF_MS) {
              GCMRegistrar.setBackoff(localContext, k * 2);
            }
          }
          else
          {
            onError$5ffc00fd(str6);
            continue;
            if (str1.equals("com.google.android.c2dm.intent.RECEIVE"))
            {
              str3 = paramIntent.getStringExtra("message_type");
              if (str3 != null)
              {
                if (str3.equals("deleted_messages"))
                {
                  str4 = paramIntent.getStringExtra("total_deleted");
                  if (str4 != null) {
                    try
                    {
                      j = Integer.parseInt(str4);
                      Log.v("GCMBaseIntentService", "Received deleted messages notification: " + j);
                    }
                    catch (NumberFormatException localNumberFormatException)
                    {
                      Log.e("GCMBaseIntentService", "GCM returned invalid number of deleted messages: " + str4);
                    }
                  }
                }
                else
                {
                  Log.e("GCMBaseIntentService", "Received unknown special message: " + str3);
                }
              }
              else {
                onMessage$3b2d1350(paramIntent);
              }
            }
            else if (str1.equals("com.google.android.gcm.intent.RETRY"))
            {
              str2 = paramIntent.getStringExtra("token");
              if (!TOKEN.equals(str2))
              {
                Log.e("GCMBaseIntentService", "Received invalid token: " + str2);
                for (;;)
                {
                  synchronized (LOCK)
                  {
                    if (sWakeLock != null)
                    {
                      Log.v("GCMBaseIntentService", "Releasing wakelock");
                      sWakeLock.release();
                      return;
                    }
                  }
                  Log.e("GCMBaseIntentService", "Wakelock reference is null");
                }
              }
              if (GCMRegistrar.getRegistrationId(localContext).length() <= 0) {
                break label823;
              }
              i = 1;
              if (i != 0)
              {
                Log.v("GCMRegistrar", "Unregistering app " + localContext.getPackageName());
                localIntent1 = new Intent("com.google.android.c2dm.intent.UNREGISTER");
                localIntent1.setPackage("com.google.android.gsf");
                localIntent1.putExtra("app", PendingIntent.getBroadcast(localContext, 0, new Intent(), 0));
                localContext.startService(localIntent1);
              }
              else
              {
                if (this.mSenderIds == null) {
                  throw new IllegalStateException("sender id not set on constructor");
                }
                GCMRegistrar.internalRegister(localContext, this.mSenderIds);
                continue;
                Log.e("GCMBaseIntentService", "Wakelock reference is null");
                continue;
                localObject5 = finally;
                throw localObject5;
              }
            }
          }
        }
        Log.e("GCMBaseIntentService", "Wakelock reference is null");
      }
    }
    finally
    {
      synchronized (LOCK)
      {
        for (;;)
        {
          if (sWakeLock != null)
          {
            Log.v("GCMBaseIntentService", "Releasing wakelock");
            sWakeLock.release();
            return;
            if (str7 != null)
            {
              GCMRegistrar.resetBackoff(localContext);
              GCMRegistrar.setRegistrationId(localContext, "");
              onUnregistered$5ffc00fd(localContext);
              continue;
              localObject1 = finally;
            }
          }
        }
      }
    }
    for (;;)
    {
      int k;
      int m;
      Intent localIntent2;
      PendingIntent localPendingIntent;
      String str3;
      String str4;
      int j;
      String str2;
      Intent localIntent1;
      label823:
      int i = 0;
    }
  }
  
  public abstract void onMessage$3b2d1350(Intent paramIntent);
  
  public abstract void onRegistered$5ffc00fd(String paramString);
  
  public abstract void onUnregistered$5ffc00fd(Context paramContext);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gcm.GCMBaseIntentService
 * JD-Core Version:    0.7.0.1
 */