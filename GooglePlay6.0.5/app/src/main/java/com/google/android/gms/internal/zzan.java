package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class zzan
  extends zzam
{
  private static AdvertisingIdClient zznW = null;
  private static CountDownLatch zznX = new CountDownLatch(1);
  private static volatile boolean zznY;
  private boolean zznZ;
  
  private zzan(Context paramContext, zzaq paramzzaq, zzar paramzzar, boolean paramBoolean)
  {
    super(paramContext, paramzzaq, paramzzar);
    this.zznZ = paramBoolean;
  }
  
  private zza zzZ()
    throws IOException
  {
    try
    {
      if (!zznX.await(2L, TimeUnit.SECONDS))
      {
        zza localzza1 = new zza(null, false);
        return localzza1;
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      return new zza(null, false);
    }
    try
    {
      if (zznW == null)
      {
        zza localzza2 = new zza(null, false);
        return localzza2;
      }
    }
    finally {}
    AdvertisingIdClient.Info localInfo = zznW.getInfo();
    return new zza(zzk(localInfo.zzpp), localInfo.zzpq);
  }
  
  public static zzan zza(String paramString, Context paramContext, boolean paramBoolean)
  {
    zzah localzzah = new zzah();
    zza(paramString, paramContext, localzzah);
    if (paramBoolean) {}
    try
    {
      if (zznW == null) {
        new Thread(new zzb(paramContext)).start();
      }
      return new zzan(paramContext, localzzah, new zzat(239), paramBoolean);
    }
    finally {}
  }
  
  protected final void zzc(Context paramContext)
  {
    super.zzc(paramContext);
    try
    {
      if ((zznY) || (!this.zznZ))
      {
        zza(24, zzg(paramContext));
        zza(24, zznO);
        return;
      }
      zza localzza1 = zzZ();
      str = localzza1.zzoa;
      if (str == null) {
        return;
      }
      if (!localzza1.zzob) {
        break label98;
      }
      l = 1L;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        String str;
        return;
        long l = 0L;
      }
    }
    catch (zzam.zza localzza) {}
    zza(28, l);
    zza(26, 5L);
    zza(24, str);
    zza(28, zznO);
    return;
    label98:
  }
  
  final class zza
  {
    String zzoa;
    boolean zzob;
    
    public zza(String paramString, boolean paramBoolean)
    {
      this.zzoa = paramString;
      this.zzob = paramBoolean;
    }
  }
  
  private static final class zzb
    implements Runnable
  {
    private Context zzod;
    
    public zzb(Context paramContext)
    {
      this.zzod = paramContext.getApplicationContext();
      if (this.zzod == null) {
        this.zzod = paramContext;
      }
    }
    
    /* Error */
    public final void run()
    {
      // Byte code:
      //   0: ldc 30
      //   2: monitorenter
      //   3: invokestatic 34	com/google/android/gms/internal/zzan:zzaa	()Lcom/google/android/gms/ads/identifier/AdvertisingIdClient;
      //   6: ifnonnull +28 -> 34
      //   9: new 36	com/google/android/gms/ads/identifier/AdvertisingIdClient
      //   12: dup
      //   13: aload_0
      //   14: getfield 21	com/google/android/gms/internal/zzan$zzb:zzod	Landroid/content/Context;
      //   17: invokespecial 38	com/google/android/gms/ads/identifier/AdvertisingIdClient:<init>	(Landroid/content/Context;)V
      //   20: astore 7
      //   22: aload 7
      //   24: iconst_1
      //   25: invokevirtual 42	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzb	(Z)V
      //   28: aload 7
      //   30: invokestatic 46	com/google/android/gms/internal/zzan:zza	(Lcom/google/android/gms/ads/identifier/AdvertisingIdClient;)Lcom/google/android/gms/ads/identifier/AdvertisingIdClient;
      //   33: pop
      //   34: invokestatic 50	com/google/android/gms/internal/zzan:zzab	()Ljava/util/concurrent/CountDownLatch;
      //   37: invokevirtual 55	java/util/concurrent/CountDownLatch:countDown	()V
      //   40: ldc 30
      //   42: monitorexit
      //   43: return
      //   44: astore 5
      //   46: invokestatic 59	com/google/android/gms/internal/zzan:zza$138603	()Z
      //   49: pop
      //   50: invokestatic 50	com/google/android/gms/internal/zzan:zzab	()Ljava/util/concurrent/CountDownLatch;
      //   53: invokevirtual 55	java/util/concurrent/CountDownLatch:countDown	()V
      //   56: goto -16 -> 40
      //   59: astore_2
      //   60: ldc 30
      //   62: monitorexit
      //   63: aload_2
      //   64: athrow
      //   65: astore 4
      //   67: invokestatic 50	com/google/android/gms/internal/zzan:zzab	()Ljava/util/concurrent/CountDownLatch;
      //   70: invokevirtual 55	java/util/concurrent/CountDownLatch:countDown	()V
      //   73: goto -33 -> 40
      //   76: astore_3
      //   77: invokestatic 50	com/google/android/gms/internal/zzan:zzab	()Ljava/util/concurrent/CountDownLatch;
      //   80: invokevirtual 55	java/util/concurrent/CountDownLatch:countDown	()V
      //   83: goto -43 -> 40
      //   86: astore_1
      //   87: invokestatic 50	com/google/android/gms/internal/zzan:zzab	()Ljava/util/concurrent/CountDownLatch;
      //   90: invokevirtual 55	java/util/concurrent/CountDownLatch:countDown	()V
      //   93: aload_1
      //   94: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	95	0	this	zzb
      //   86	8	1	localObject1	Object
      //   59	5	2	localObject2	Object
      //   76	1	3	localGooglePlayServicesRepairableException	com.google.android.gms.common.GooglePlayServicesRepairableException
      //   65	1	4	localIOException	IOException
      //   44	1	5	localGooglePlayServicesNotAvailableException	com.google.android.gms.common.GooglePlayServicesNotAvailableException
      //   20	9	7	localAdvertisingIdClient	AdvertisingIdClient
      // Exception table:
      //   from	to	target	type
      //   3	34	44	com/google/android/gms/common/GooglePlayServicesNotAvailableException
      //   34	40	59	finally
      //   40	43	59	finally
      //   50	56	59	finally
      //   60	63	59	finally
      //   67	73	59	finally
      //   77	83	59	finally
      //   87	95	59	finally
      //   3	34	65	java/io/IOException
      //   3	34	76	com/google/android/gms/common/GooglePlayServicesRepairableException
      //   3	34	86	finally
      //   46	50	86	finally
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzan
 * JD-Core Version:    0.7.0.1
 */