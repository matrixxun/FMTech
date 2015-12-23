package com.google.android.gms.checkin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.common.zza;
import java.io.IOException;

public final class CheckinServiceClient
{
  /* Error */
  public static java.lang.String zzb(Context paramContext, zza paramzza)
    throws IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 20	com/google/android/gms/common/zza:zzoj	()Landroid/os/IBinder;
    //   4: invokestatic 26	com/google/android/gms/internal/zzmp$zza:zzbY	(Landroid/os/IBinder;)Lcom/google/android/gms/internal/zzmp;
    //   7: invokeinterface 32 1 0
    //   12: astore 8
    //   14: invokestatic 38	com/google/android/gms/common/stats/zzb:zzrf	()Lcom/google/android/gms/common/stats/zzb;
    //   17: aload_0
    //   18: aload_1
    //   19: invokevirtual 42	com/google/android/gms/common/stats/zzb:zza	(Landroid/content/Context;Landroid/content/ServiceConnection;)V
    //   22: aload 8
    //   24: areturn
    //   25: astore 9
    //   27: ldc 44
    //   29: ldc 46
    //   31: aload 9
    //   33: invokestatic 52	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   36: pop
    //   37: aload 8
    //   39: areturn
    //   40: astore 6
    //   42: ldc 44
    //   44: ldc 54
    //   46: aload 6
    //   48: invokestatic 52	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   51: pop
    //   52: new 8	java/io/IOException
    //   55: dup
    //   56: ldc 56
    //   58: invokespecial 60	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   61: athrow
    //   62: astore_3
    //   63: invokestatic 38	com/google/android/gms/common/stats/zzb:zzrf	()Lcom/google/android/gms/common/stats/zzb;
    //   66: aload_0
    //   67: aload_1
    //   68: invokevirtual 42	com/google/android/gms/common/stats/zzb:zza	(Landroid/content/Context;Landroid/content/ServiceConnection;)V
    //   71: aload_3
    //   72: athrow
    //   73: astore_2
    //   74: new 8	java/io/IOException
    //   77: dup
    //   78: ldc 62
    //   80: invokespecial 60	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   83: athrow
    //   84: astore 4
    //   86: ldc 44
    //   88: ldc 46
    //   90: aload 4
    //   92: invokestatic 52	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   95: pop
    //   96: goto -25 -> 71
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	99	0	paramContext	Context
    //   0	99	1	paramzza	zza
    //   73	1	2	localInterruptedException	java.lang.InterruptedException
    //   62	10	3	localObject	Object
    //   84	7	4	localIllegalArgumentException1	java.lang.IllegalArgumentException
    //   40	7	6	localRemoteException	android.os.RemoteException
    //   12	26	8	str	java.lang.String
    //   25	7	9	localIllegalArgumentException2	java.lang.IllegalArgumentException
    // Exception table:
    //   from	to	target	type
    //   14	22	25	java/lang/IllegalArgumentException
    //   0	14	40	android/os/RemoteException
    //   0	14	62	finally
    //   42	62	62	finally
    //   74	84	62	finally
    //   0	14	73	java/lang/InterruptedException
    //   63	71	84	java/lang/IllegalArgumentException
  }
  
  public static zza zzq(Context paramContext)
    throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException
  {
    try
    {
      paramContext.getPackageManager().getPackageInfo("com.android.vending", 0);
      zza localzza;
      Intent localIntent;
      throw new IOException("Connection failure.");
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      try
      {
        GooglePlayServicesUtil.zzae(paramContext);
        localzza = new zza();
        localIntent = new Intent("com.google.android.gms.checkin.BIND_TO_SERVICE");
        localIntent.setPackage("com.google.android.gms");
        if (!zzb.zzrf().zza(paramContext, localIntent, localzza, 1)) {
          break label82;
        }
        return localzza;
      }
      catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
      {
        throw new IOException(localGooglePlayServicesNotAvailableException);
      }
      localNameNotFoundException = localNameNotFoundException;
      throw new GooglePlayServicesNotAvailableException(9);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.checkin.CheckinServiceClient
 * JD-Core Version:    0.7.0.1
 */