package com.google.android.gms.measurement;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.measurement.internal.zzae;
import com.google.android.gms.measurement.internal.zzc;
import com.google.android.gms.measurement.internal.zzo;
import com.google.android.gms.measurement.internal.zzo.zza;
import com.google.android.gms.measurement.internal.zzt;
import com.google.android.gms.measurement.internal.zzu;

public final class AppMeasurementService
  extends Service
{
  private static Boolean zzNQ;
  private final Handler mHandler = new Handler();
  
  public static boolean zzad(Context paramContext)
  {
    zzx.zzC(paramContext);
    if (zzNQ != null) {
      return zzNQ.booleanValue();
    }
    boolean bool = zzae.zza(paramContext, AppMeasurementService.class);
    zzNQ = Boolean.valueOf(bool);
    return bool;
  }
  
  public final IBinder onBind(Intent paramIntent)
  {
    if (paramIntent == null)
    {
      zzt.zzaT(this).zzBh().zzbmW.zzeB("onBind called with null intent");
      return null;
    }
    String str = paramIntent.getAction();
    if ("com.google.android.gms.measurement.START".equals(str)) {
      return new zzu(zzt.zzaT(this));
    }
    zzt.zzaT(this).zzBh().zzbmZ.zzm("onBind received unknown action", str);
    return null;
  }
  
  public final void onCreate()
  {
    super.onCreate();
    zzo localzzo = zzt.zzaT(this).zzBh();
    if (zzc.isPackageSide())
    {
      localzzo.zzbne.zzeB("Device AppMeasurementService is starting up");
      return;
    }
    localzzo.zzbne.zzeB("Local AppMeasurementService is starting up");
  }
  
  public final void onDestroy()
  {
    zzo localzzo = zzt.zzaT(this).zzBh();
    if (zzc.isPackageSide()) {
      localzzo.zzbne.zzeB("Device AppMeasurementService is shutting down");
    }
    for (;;)
    {
      super.onDestroy();
      return;
      localzzo.zzbne.zzeB("Local AppMeasurementService is shutting down");
    }
  }
  
  public final void onRebind(Intent paramIntent)
  {
    if (paramIntent == null)
    {
      zzt.zzaT(this).zzBh().zzbmW.zzeB("onRebind called with null intent");
      return;
    }
    String str = paramIntent.getAction();
    zzt.zzaT(this).zzBh().zzbne.zzm("onRebind called. action", str);
  }
  
  /* Error */
  public final int onStartCommand(Intent paramIntent, int paramInt1, final int paramInt2)
  {
    // Byte code:
    //   0: getstatic 136	com/google/android/gms/measurement/AppMeasurementReceiver:zzqK	Ljava/lang/Object;
    //   3: astore 8
    //   5: aload 8
    //   7: monitorenter
    //   8: getstatic 140	com/google/android/gms/measurement/AppMeasurementReceiver:zzbkN	Landroid/os/PowerManager$WakeLock;
    //   11: astore 10
    //   13: aload 10
    //   15: ifnull +16 -> 31
    //   18: aload 10
    //   20: invokevirtual 145	android/os/PowerManager$WakeLock:isHeld	()Z
    //   23: ifeq +8 -> 31
    //   26: aload 10
    //   28: invokevirtual 148	android/os/PowerManager$WakeLock:release	()V
    //   31: aload 8
    //   33: monitorexit
    //   34: aload_0
    //   35: invokestatic 52	com/google/android/gms/measurement/internal/zzt:zzaT	(Landroid/content/Context;)Lcom/google/android/gms/measurement/internal/zzt;
    //   38: astore 5
    //   40: aload 5
    //   42: invokevirtual 56	com/google/android/gms/measurement/internal/zzt:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   45: astore 6
    //   47: aload_1
    //   48: invokevirtual 76	android/content/Intent:getAction	()Ljava/lang/String;
    //   51: astore 7
    //   53: invokestatic 106	com/google/android/gms/measurement/internal/zzc:isPackageSide	()Z
    //   56: ifeq +65 -> 121
    //   59: aload 6
    //   61: getfield 109	com/google/android/gms/measurement/internal/zzo:zzbne	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   64: ldc 150
    //   66: iload_3
    //   67: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   70: aload 7
    //   72: invokevirtual 159	com/google/android/gms/measurement/internal/zzo$zza:zze	(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
    //   75: ldc 161
    //   77: aload 7
    //   79: invokevirtual 84	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   82: ifeq +24 -> 106
    //   85: aload 5
    //   87: invokevirtual 165	com/google/android/gms/measurement/internal/zzt:zzBY	()Lcom/google/android/gms/measurement/internal/zzs;
    //   90: new 167	com/google/android/gms/measurement/AppMeasurementService$1
    //   93: dup
    //   94: aload_0
    //   95: aload 5
    //   97: iload_3
    //   98: aload 6
    //   100: invokespecial 170	com/google/android/gms/measurement/AppMeasurementService$1:<init>	(Lcom/google/android/gms/measurement/AppMeasurementService;Lcom/google/android/gms/measurement/internal/zzt;ILcom/google/android/gms/measurement/internal/zzo;)V
    //   103: invokevirtual 176	com/google/android/gms/measurement/internal/zzs:zzg	(Ljava/lang/Runnable;)V
    //   106: iconst_2
    //   107: ireturn
    //   108: astore 9
    //   110: aload 8
    //   112: monitorexit
    //   113: aload 9
    //   115: athrow
    //   116: astore 4
    //   118: goto -84 -> 34
    //   121: aload 6
    //   123: getfield 109	com/google/android/gms/measurement/internal/zzo:zzbne	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   126: ldc 178
    //   128: iload_3
    //   129: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   132: aload 7
    //   134: invokevirtual 159	com/google/android/gms/measurement/internal/zzo$zza:zze	(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
    //   137: goto -62 -> 75
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	140	0	this	AppMeasurementService
    //   0	140	1	paramIntent	Intent
    //   0	140	2	paramInt1	int
    //   0	140	3	paramInt2	int
    //   116	1	4	localSecurityException	java.lang.SecurityException
    //   38	58	5	localzzt	zzt
    //   45	77	6	localzzo	zzo
    //   51	82	7	str	String
    //   108	6	9	localObject2	Object
    //   11	16	10	localWakeLock	android.os.PowerManager.WakeLock
    // Exception table:
    //   from	to	target	type
    //   8	13	108	finally
    //   18	31	108	finally
    //   31	34	108	finally
    //   110	113	108	finally
    //   0	8	116	java/lang/SecurityException
    //   113	116	116	java/lang/SecurityException
  }
  
  public final boolean onUnbind(Intent paramIntent)
  {
    if (paramIntent == null)
    {
      zzt.zzaT(this).zzBh().zzbmW.zzeB("onUnbind called with null intent");
      return true;
    }
    String str = paramIntent.getAction();
    zzt.zzaT(this).zzBh().zzbne.zzm("onUnbind called for intent. action", str);
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.AppMeasurementService
 * JD-Core Version:    0.7.0.1
 */