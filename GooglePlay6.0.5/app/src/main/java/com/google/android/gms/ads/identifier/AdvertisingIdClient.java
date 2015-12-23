package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.common.zza;
import com.google.android.gms.internal.zzbe;
import com.google.android.gms.internal.zzbe.zza;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class AdvertisingIdClient
{
  private static boolean zzpk = false;
  private final Context mContext;
  zza zzpe;
  zzbe zzpf;
  boolean zzpg;
  Object zzph = new Object();
  zza zzpi;
  final long zzpj;
  
  public AdvertisingIdClient(Context paramContext)
  {
    this(paramContext, 30000L);
  }
  
  private AdvertisingIdClient(Context paramContext, long paramLong)
  {
    zzx.zzC(paramContext);
    this.mContext = paramContext;
    this.zzpg = false;
    this.zzpj = paramLong;
  }
  
  public static Info getAdvertisingIdInfo(Context paramContext)
    throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException
  {
    AdvertisingIdClient localAdvertisingIdClient = new AdvertisingIdClient(paramContext, -1L);
    try
    {
      localAdvertisingIdClient.zzb(false);
      Info localInfo = localAdvertisingIdClient.getInfo();
      return localInfo;
    }
    finally
    {
      localAdvertisingIdClient.finish();
    }
  }
  
  public static void setShouldSkipGmsCoreVersionCheck(boolean paramBoolean)
  {
    zzpk = paramBoolean;
  }
  
  private static zzbe zza$4541938d(zza paramzza)
    throws IOException
  {
    try
    {
      zzbe localzzbe = zzbe.zza.zzg(paramzza.zzoj());
      return localzzbe;
    }
    catch (InterruptedException localInterruptedException)
    {
      throw new IOException("Interrupted exception");
    }
    catch (Throwable localThrowable)
    {
      throw new IOException(localThrowable);
    }
  }
  
  private void zzaW()
  {
    synchronized (this.zzph)
    {
      if (this.zzpi != null) {
        this.zzpi.zzpn.countDown();
      }
    }
    try
    {
      this.zzpi.join();
      label31:
      if (this.zzpj > 0L) {
        this.zzpi = new zza(this, this.zzpj);
      }
      return;
      localObject2 = finally;
      throw localObject2;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label31;
    }
  }
  
  private static zza zzq(Context paramContext)
    throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException
  {
    try
    {
      paramContext.getPackageManager().getPackageInfo("com.android.vending", 0);
      if (zzpk)
      {
        Log.d("Ads", "Skipping gmscore version check");
        GoogleApiAvailability.getInstance();
        switch (GoogleApiAvailability.isGooglePlayServicesAvailable(paramContext))
        {
        case 1: 
        default: 
          throw new IOException("Google Play services not available");
        }
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new GooglePlayServicesNotAvailableException(9);
    }
    try
    {
      GooglePlayServicesUtil.zzae(paramContext);
      zza localzza = new zza();
      Intent localIntent = new Intent("com.google.android.gms.ads.identifier.service.START");
      localIntent.setPackage("com.google.android.gms");
      boolean bool;
      throw new IOException("Connection failure");
    }
    catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
    {
      try
      {
        bool = zzb.zzrf().zza(paramContext, localIntent, localzza, 1);
        if (!bool) {
          break label157;
        }
        return localzza;
      }
      catch (Throwable localThrowable)
      {
        throw new IOException(localThrowable);
      }
      localGooglePlayServicesNotAvailableException = localGooglePlayServicesNotAvailableException;
      throw new IOException(localGooglePlayServicesNotAvailableException);
    }
  }
  
  protected final void finalize()
    throws Throwable
  {
    finish();
    super.finalize();
  }
  
  /* Error */
  public final void finish()
  {
    // Byte code:
    //   0: ldc 195
    //   2: invokestatic 198	com/google/android/gms/common/internal/zzx:zzcy	(Ljava/lang/String;)V
    //   5: aload_0
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 42	com/google/android/gms/ads/identifier/AdvertisingIdClient:mContext	Landroid/content/Context;
    //   11: ifnull +10 -> 21
    //   14: aload_0
    //   15: getfield 200	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpe	Lcom/google/android/gms/common/zza;
    //   18: ifnonnull +6 -> 24
    //   21: aload_0
    //   22: monitorexit
    //   23: return
    //   24: aload_0
    //   25: getfield 44	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpg	Z
    //   28: ifeq +17 -> 45
    //   31: invokestatic 182	com/google/android/gms/common/stats/zzb:zzrf	()Lcom/google/android/gms/common/stats/zzb;
    //   34: aload_0
    //   35: getfield 42	com/google/android/gms/ads/identifier/AdvertisingIdClient:mContext	Landroid/content/Context;
    //   38: aload_0
    //   39: getfield 200	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpe	Lcom/google/android/gms/common/zza;
    //   42: invokevirtual 203	com/google/android/gms/common/stats/zzb:zza	(Landroid/content/Context;Landroid/content/ServiceConnection;)V
    //   45: aload_0
    //   46: iconst_0
    //   47: putfield 44	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpg	Z
    //   50: aload_0
    //   51: aconst_null
    //   52: putfield 205	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpf	Lcom/google/android/gms/internal/zzbe;
    //   55: aload_0
    //   56: aconst_null
    //   57: putfield 200	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpe	Lcom/google/android/gms/common/zza;
    //   60: aload_0
    //   61: monitorexit
    //   62: return
    //   63: astore_1
    //   64: aload_0
    //   65: monitorexit
    //   66: aload_1
    //   67: athrow
    //   68: astore_2
    //   69: ldc 207
    //   71: ldc 209
    //   73: aload_2
    //   74: invokestatic 213	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   77: pop
    //   78: goto -33 -> 45
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	81	0	this	AdvertisingIdClient
    //   63	4	1	localObject	Object
    //   68	6	2	localIllegalArgumentException	java.lang.IllegalArgumentException
    // Exception table:
    //   from	to	target	type
    //   7	21	63	finally
    //   21	23	63	finally
    //   24	45	63	finally
    //   45	62	63	finally
    //   64	66	63	finally
    //   69	78	63	finally
    //   24	45	68	java/lang/IllegalArgumentException
  }
  
  /* Error */
  public final Info getInfo()
    throws IOException
  {
    // Byte code:
    //   0: ldc 195
    //   2: invokestatic 198	com/google/android/gms/common/internal/zzx:zzcy	(Ljava/lang/String;)V
    //   5: aload_0
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 44	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpg	Z
    //   11: ifne +91 -> 102
    //   14: aload_0
    //   15: getfield 34	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzph	Ljava/lang/Object;
    //   18: astore 7
    //   20: aload 7
    //   22: monitorenter
    //   23: aload_0
    //   24: getfield 99	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpi	Lcom/google/android/gms/ads/identifier/AdvertisingIdClient$zza;
    //   27: ifnull +13 -> 40
    //   30: aload_0
    //   31: getfield 99	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpi	Lcom/google/android/gms/ads/identifier/AdvertisingIdClient$zza;
    //   34: getfield 220	com/google/android/gms/ads/identifier/AdvertisingIdClient$zza:zzpo	Z
    //   37: ifne +26 -> 63
    //   40: new 50	java/io/IOException
    //   43: dup
    //   44: ldc 222
    //   46: invokespecial 93	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   49: athrow
    //   50: astore 8
    //   52: aload 7
    //   54: monitorexit
    //   55: aload 8
    //   57: athrow
    //   58: astore_1
    //   59: aload_0
    //   60: monitorexit
    //   61: aload_1
    //   62: athrow
    //   63: aload 7
    //   65: monitorexit
    //   66: aload_0
    //   67: iconst_0
    //   68: invokevirtual 62	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzb	(Z)V
    //   71: aload_0
    //   72: getfield 44	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpg	Z
    //   75: ifne +27 -> 102
    //   78: new 50	java/io/IOException
    //   81: dup
    //   82: ldc 224
    //   84: invokespecial 93	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   87: athrow
    //   88: astore 9
    //   90: new 50	java/io/IOException
    //   93: dup
    //   94: ldc 224
    //   96: aload 9
    //   98: invokespecial 227	java/io/IOException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   101: athrow
    //   102: aload_0
    //   103: getfield 200	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpe	Lcom/google/android/gms/common/zza;
    //   106: invokestatic 40	com/google/android/gms/common/internal/zzx:zzC	(Ljava/lang/Object;)Ljava/lang/Object;
    //   109: pop
    //   110: aload_0
    //   111: getfield 205	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpf	Lcom/google/android/gms/internal/zzbe;
    //   114: invokestatic 40	com/google/android/gms/common/internal/zzx:zzC	(Ljava/lang/Object;)Ljava/lang/Object;
    //   117: pop
    //   118: new 229	com/google/android/gms/ads/identifier/AdvertisingIdClient$Info
    //   121: dup
    //   122: aload_0
    //   123: getfield 205	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpf	Lcom/google/android/gms/internal/zzbe;
    //   126: invokeinterface 235 1 0
    //   131: aload_0
    //   132: getfield 205	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzpf	Lcom/google/android/gms/internal/zzbe;
    //   135: iconst_1
    //   136: invokeinterface 239 2 0
    //   141: invokespecial 242	com/google/android/gms/ads/identifier/AdvertisingIdClient$Info:<init>	(Ljava/lang/String;Z)V
    //   144: astore 4
    //   146: aload_0
    //   147: monitorexit
    //   148: aload_0
    //   149: invokespecial 244	com/google/android/gms/ads/identifier/AdvertisingIdClient:zzaW	()V
    //   152: aload 4
    //   154: areturn
    //   155: astore 5
    //   157: ldc 207
    //   159: ldc 246
    //   161: aload 5
    //   163: invokestatic 213	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   166: pop
    //   167: new 50	java/io/IOException
    //   170: dup
    //   171: ldc 248
    //   173: invokespecial 93	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   176: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	177	0	this	AdvertisingIdClient
    //   58	4	1	localObject1	Object
    //   144	9	4	localInfo	Info
    //   155	7	5	localRemoteException	android.os.RemoteException
    //   18	46	7	localObject2	Object
    //   50	6	8	localObject3	Object
    //   88	9	9	localException	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   23	40	50	finally
    //   40	50	50	finally
    //   52	55	50	finally
    //   63	66	50	finally
    //   7	23	58	finally
    //   55	58	58	finally
    //   59	61	58	finally
    //   66	71	58	finally
    //   71	88	58	finally
    //   90	102	58	finally
    //   102	118	58	finally
    //   118	146	58	finally
    //   146	148	58	finally
    //   157	177	58	finally
    //   66	71	88	java/lang/Exception
    //   118	146	155	android/os/RemoteException
  }
  
  public void zzb(boolean paramBoolean)
    throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException
  {
    zzx.zzcy("Calling this from your main thread can lead to deadlock");
    try
    {
      if (this.zzpg) {
        finish();
      }
      this.zzpe = zzq(this.mContext);
      this.zzpf = zza$4541938d(this.zzpe);
      this.zzpg = true;
      if (paramBoolean) {
        zzaW();
      }
      return;
    }
    finally {}
  }
  
  public static final class Info
  {
    public final String zzpp;
    public final boolean zzpq;
    
    public Info(String paramString, boolean paramBoolean)
    {
      this.zzpp = paramString;
      this.zzpq = paramBoolean;
    }
    
    public final String toString()
    {
      return "{" + this.zzpp + "}" + this.zzpq;
    }
  }
  
  static final class zza
    extends Thread
  {
    private WeakReference<AdvertisingIdClient> zzpl;
    private long zzpm;
    CountDownLatch zzpn;
    boolean zzpo;
    
    public zza(AdvertisingIdClient paramAdvertisingIdClient, long paramLong)
    {
      this.zzpl = new WeakReference(paramAdvertisingIdClient);
      this.zzpm = paramLong;
      this.zzpn = new CountDownLatch(1);
      this.zzpo = false;
      start();
    }
    
    private void disconnect()
    {
      AdvertisingIdClient localAdvertisingIdClient = (AdvertisingIdClient)this.zzpl.get();
      if (localAdvertisingIdClient != null)
      {
        localAdvertisingIdClient.finish();
        this.zzpo = true;
      }
    }
    
    public final void run()
    {
      try
      {
        if (!this.zzpn.await(this.zzpm, TimeUnit.MILLISECONDS)) {
          disconnect();
        }
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        disconnect();
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.identifier.AdvertisingIdClient
 * JD-Core Version:    0.7.0.1
 */