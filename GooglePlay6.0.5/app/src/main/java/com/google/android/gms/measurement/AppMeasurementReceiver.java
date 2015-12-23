package com.google.android.gms.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.measurement.internal.zzae;
import com.google.android.gms.measurement.internal.zzc;
import com.google.android.gms.measurement.internal.zzo;
import com.google.android.gms.measurement.internal.zzo.zza;
import com.google.android.gms.measurement.internal.zzt;

public final class AppMeasurementReceiver
  extends BroadcastReceiver
{
  static Boolean zzNP;
  static PowerManager.WakeLock zzbkN;
  static final Object zzqK = new Object();
  
  public static boolean zzac(Context paramContext)
  {
    zzx.zzC(paramContext);
    if (zzNP != null) {
      return zzNP.booleanValue();
    }
    boolean bool = zzae.zza$6aa51a6e(paramContext, AppMeasurementReceiver.class);
    zzNP = Boolean.valueOf(bool);
    return bool;
  }
  
  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    localzzo = zzt.zzaT(paramContext).zzBh();
    String str = paramIntent.getAction();
    if (zzc.isPackageSide()) {
      localzzo.zzbne.zzm("Device AppMeasurementReceiver got", str);
    }
    for (;;)
    {
      boolean bool;
      Intent localIntent;
      if ("com.google.android.gms.measurement.UPLOAD".equals(str))
      {
        bool = AppMeasurementService.zzad(paramContext);
        localIntent = new Intent(paramContext, AppMeasurementService.class);
        localIntent.setAction("com.google.android.gms.measurement.UPLOAD");
      }
      synchronized (zzqK)
      {
        paramContext.startService(localIntent);
        if (!bool)
        {
          return;
          localzzo.zzbne.zzm("Local AppMeasurementReceiver got", str);
          continue;
        }
        try
        {
          PowerManager localPowerManager = (PowerManager)paramContext.getSystemService("power");
          if (zzbkN == null)
          {
            PowerManager.WakeLock localWakeLock = localPowerManager.newWakeLock(1, "AppMeasurement WakeLock");
            zzbkN = localWakeLock;
            localWakeLock.setReferenceCounted(false);
          }
          zzbkN.acquire(1000L);
        }
        catch (SecurityException localSecurityException)
        {
          for (;;)
          {
            localzzo.zzbmZ.zzeB("AppMeasurementService at risk of not starting. For more reliable app measurements, add the WAKE_LOCK permission to your manifest.");
          }
        }
        return;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.AppMeasurementReceiver
 * JD-Core Version:    0.7.0.1
 */