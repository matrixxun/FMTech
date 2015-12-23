package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.measurement.AppMeasurementService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class zzz
  extends zzw
{
  final zza zzboE;
  zzl zzboF;
  private Boolean zzboG;
  private final zze zzboH;
  private final zzaa zzboI;
  private final List<Runnable> zzboJ = new ArrayList();
  private final zze zzboK;
  
  protected zzz(zzt paramzzt)
  {
    super(paramzzt);
    this.zzboI = new zzaa(paramzzt.zzri);
    this.zzboE = new zza();
    this.zzboH = new zze(paramzzt)
    {
      public final void run()
      {
        zzz.zzb(zzz.this);
      }
    };
    this.zzboK = new zze(paramzzt)
    {
      public final void run()
      {
        zzz.this.zzBh().zzbmZ.zzeB("Tasks have been queued for a long time");
      }
    };
  }
  
  private void connectToService()
  {
    boolean bool1 = true;
    super.checkOnWorkerThread();
    zziL();
    if (isConnected()) {
      return;
    }
    boolean bool2;
    if (this.zzboG == null)
    {
      this.zzboG = super.zzBZ().zzCA();
      if (this.zzboG == null)
      {
        super.zzBh().zzbne.zzeB("State of service unknown");
        super.checkOnWorkerThread();
        zziL();
        if (!zzc.isPackageSide()) {
          break label180;
        }
        bool2 = bool1;
      }
    }
    zza localzza2;
    Context localContext2;
    for (;;)
    {
      this.zzboG = Boolean.valueOf(bool2);
      super.zzBZ().zzax(this.zzboG.booleanValue());
      if (!this.zzboG.booleanValue()) {
        break label367;
      }
      super.zzBh().zzbne.zzeB("Using measurement service");
      localzza2 = this.zzboE;
      localzza2.zzboL.checkOnWorkerThread();
      localContext2 = localzza2.zzboL.getContext();
      try
      {
        if (!localzza2.zzboM) {
          break;
        }
        localzza2.zzboL.zzBh().zzbne.zzeB("Connection attempt already in progress");
        return;
      }
      finally {}
      label180:
      Intent localIntent2 = new Intent("com.google.android.gms.measurement.START");
      localIntent2.setComponent(new ComponentName("com.google.android.gms", "com.google.android.gms.measurement.service.MeasurementBrokerService"));
      zzb localzzb2 = zzb.zzrf();
      super.zzBh().zzbne.zzeB("Checking service availability");
      if (localzzb2.zza(super.getContext(), localIntent2, new ServiceConnection()
      {
        public final void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder) {}
        
        public final void onServiceDisconnected(ComponentName paramAnonymousComponentName) {}
      }, 0))
      {
        super.zzBh().zzbne.zzeB("Service available");
        bool2 = bool1;
      }
      else
      {
        bool2 = false;
      }
    }
    if (localzza2.zzboN != null)
    {
      localzza2.zzboL.zzBh().zzbne.zzeB("Already awaiting connection attempt");
      return;
    }
    localzza2.zzboN = new zzn(localContext2, Looper.getMainLooper(), new GoogleApiClient.Builder(localContext2).zzoy(), localzza2, localzza2);
    localzza2.zzboL.zzBh().zzbne.zzeB("Connecting to remote service");
    localzza2.zzboM = true;
    localzza2.zzboN.zzqk();
    return;
    label367:
    List localList = super.getContext().getPackageManager().queryIntentServices(new Intent(super.getContext(), AppMeasurementService.class), 65536);
    if ((localList != null) && (localList.size() > 0)) {}
    while (bool1)
    {
      super.zzBh().zzbne.zzeB("Using local app measurement service");
      Intent localIntent1 = new Intent("com.google.android.gms.measurement.START");
      localIntent1.setComponent(new ComponentName(super.getContext(), AppMeasurementService.class));
      zza localzza1 = this.zzboE;
      localzza1.zzboL.checkOnWorkerThread();
      Context localContext1 = localzza1.zzboL.getContext();
      zzb localzzb1 = zzb.zzrf();
      try
      {
        if (!localzza1.zzboM) {
          break label523;
        }
        localzza1.zzboL.zzBh().zzbne.zzeB("Connection attempt already in progress");
        return;
      }
      finally {}
      bool1 = false;
      continue;
      label523:
      localzza1.zzboM = true;
      localzzb1.zza(localContext1, localIntent1, localzza1.zzboL.zzboE, 129);
      return;
    }
    if (super.zzCa().isMainProcess())
    {
      super.zzBh().zzbne.zzeB("Using direct local measurement implementation");
      zza(new zzu(this.zzbkM, (byte)0));
      return;
    }
    super.zzBh().zzbmW.zzeB("Not in main process. Unable to use local measurement implementation. Please register the AppMeasurementService service in the app manifest");
  }
  
  private void zza(zzl paramzzl)
  {
    super.checkOnWorkerThread();
    zzx.zzC(paramzzl);
    this.zzboF = paramzzl;
    zziM();
    super.checkOnWorkerThread();
    super.zzBh().zzbne.zzm("Processing queued up service tasks", Integer.valueOf(this.zzboJ.size()));
    Iterator localIterator = this.zzboJ.iterator();
    while (localIterator.hasNext())
    {
      Runnable localRunnable = (Runnable)localIterator.next();
      super.zzBY().zzg(localRunnable);
    }
    this.zzboJ.clear();
    this.zzboK.cancel();
  }
  
  private void zzi(Runnable paramRunnable)
    throws IllegalStateException
  {
    super.checkOnWorkerThread();
    if (isConnected())
    {
      paramRunnable.run();
      return;
    }
    if (this.zzboJ.size() >= zzc.zzBK())
    {
      super.zzBh().zzbmW.zzeB("Discarding data. Max runnable queue size reached");
      return;
    }
    this.zzboJ.add(paramRunnable);
    this.zzboK.zzr(60000L);
    connectToService();
  }
  
  private void zziM()
  {
    super.checkOnWorkerThread();
    this.zzboI.start();
    this.zzboH.zzr(zzc.getConnectionCacheTimeMillis());
  }
  
  public final boolean isConnected()
  {
    super.checkOnWorkerThread();
    zziL();
    return this.zzboF != null;
  }
  
  protected final void onInitialize() {}
  
  protected final void zzCU()
  {
    super.checkOnWorkerThread();
    zziL();
    zzi(new Runnable()
    {
      public final void run()
      {
        zzl localzzl = zzz.this.zzboF;
        if (localzzl == null)
        {
          zzz.this.zzBh().zzbmW.zzeB("Discarding data. Failed to send app launch");
          return;
        }
        try
        {
          localzzl.zza(zzz.this.zzBV().zzez(zzz.this.zzBh().zzCv()));
          zzz.zzd(zzz.this);
          return;
        }
        catch (RemoteException localRemoteException)
        {
          zzz.this.zzBh().zzbmW.zzm("Failed to send app launch to AppMeasurementService", localRemoteException);
        }
      }
    });
  }
  
  protected final void zza(final UserAttributeParcel paramUserAttributeParcel)
  {
    super.checkOnWorkerThread();
    zziL();
    zzi(new Runnable()
    {
      public final void run()
      {
        zzl localzzl = zzz.this.zzboF;
        if (localzzl == null)
        {
          zzz.this.zzBh().zzbmW.zzeB("Discarding data. Failed to set user attribute");
          return;
        }
        try
        {
          localzzl.zza(paramUserAttributeParcel, zzz.this.zzBV().zzez(zzz.this.zzBh().zzCv()));
          zzz.zzd(zzz.this);
          return;
        }
        catch (RemoteException localRemoteException)
        {
          zzz.this.zzBh().zzbmW.zzm("Failed to send attribute to AppMeasurementService", localRemoteException);
        }
      }
    });
  }
  
  protected final class zza
    implements ServiceConnection, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
  {
    volatile boolean zzboM;
    volatile zzn zzboN;
    
    protected zza() {}
    
    public final void onConnected(Bundle paramBundle)
    {
      zzx.zzcx("MeasurementServiceConnection.onConnected");
      for (;;)
      {
        final zzl localzzl;
        try
        {
          this.zzboM = false;
        }
        finally {}
        try
        {
          localzzl = (zzl)this.zzboN.zzqn();
          this.zzboN = null;
          zzz.this.zzBY().zzg(new Runnable()
          {
            public final void run()
            {
              if (!zzz.this.isConnected())
              {
                zzz.this.zzBh().zzbnd.zzeB("Connected to remote service");
                zzz.zza(zzz.this, localzzl);
              }
            }
          });
          return;
        }
        catch (IllegalStateException localIllegalStateException)
        {
          continue;
        }
        catch (DeadObjectException localDeadObjectException)
        {
          continue;
        }
        this.zzboN = null;
      }
    }
    
    public final void onConnectionFailed(ConnectionResult paramConnectionResult)
    {
      zzx.zzcx("MeasurementServiceConnection.onConnectionFailed");
      zzz.this.zzBh().zzbmZ.zzm("Service connection failed", paramConnectionResult);
      try
      {
        this.zzboM = false;
        this.zzboN = null;
        return;
      }
      finally {}
    }
    
    public final void onConnectionSuspended(int paramInt)
    {
      zzx.zzcx("MeasurementServiceConnection.onConnectionSuspended");
      zzz.this.zzBh().zzbnd.zzeB("Service connection suspended");
      zzz.this.zzBY().zzg(new Runnable()
      {
        public final void run()
        {
          zzz.zza(zzz.this, new ComponentName(zzz.this.getContext(), AppMeasurementService.class));
        }
      });
    }
    
    /* Error */
    public final void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      // Byte code:
      //   0: ldc 113
      //   2: invokestatic 37	com/google/android/gms/common/internal/zzx:zzcx	(Ljava/lang/String;)V
      //   5: aload_0
      //   6: monitorenter
      //   7: aload_0
      //   8: iconst_0
      //   9: putfield 39	com/google/android/gms/measurement/internal/zzz$zza:zzboM	Z
      //   12: aload_2
      //   13: ifnonnull +21 -> 34
      //   16: aload_0
      //   17: getfield 20	com/google/android/gms/measurement/internal/zzz$zza:zzboL	Lcom/google/android/gms/measurement/internal/zzz;
      //   20: invokevirtual 74	com/google/android/gms/measurement/internal/zzz:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
      //   23: getfield 116	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
      //   26: ldc 118
      //   28: invokevirtual 100	com/google/android/gms/measurement/internal/zzo$zza:zzeB	(Ljava/lang/String;)V
      //   31: aload_0
      //   32: monitorexit
      //   33: return
      //   34: aconst_null
      //   35: astore 4
      //   37: aload_2
      //   38: invokeinterface 124 1 0
      //   43: astore 7
      //   45: ldc 126
      //   47: aload 7
      //   49: invokevirtual 132	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   52: istore 8
      //   54: aconst_null
      //   55: astore 4
      //   57: iload 8
      //   59: ifeq +57 -> 116
      //   62: aload_2
      //   63: invokestatic 138	com/google/android/gms/measurement/internal/zzl$zza:zzfs	(Landroid/os/IBinder;)Lcom/google/android/gms/measurement/internal/zzl;
      //   66: astore 4
      //   68: aload_0
      //   69: getfield 20	com/google/android/gms/measurement/internal/zzz$zza:zzboL	Lcom/google/android/gms/measurement/internal/zzz;
      //   72: invokevirtual 74	com/google/android/gms/measurement/internal/zzz:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
      //   75: getfield 141	com/google/android/gms/measurement/internal/zzo:zzbne	Lcom/google/android/gms/measurement/internal/zzo$zza;
      //   78: ldc 143
      //   80: invokevirtual 100	com/google/android/gms/measurement/internal/zzo$zza:zzeB	(Ljava/lang/String;)V
      //   83: aload 4
      //   85: ifnonnull +74 -> 159
      //   88: invokestatic 149	com/google/android/gms/common/stats/zzb:zzrf	()Lcom/google/android/gms/common/stats/zzb;
      //   91: aload_0
      //   92: getfield 20	com/google/android/gms/measurement/internal/zzz$zza:zzboL	Lcom/google/android/gms/measurement/internal/zzz;
      //   95: invokevirtual 153	com/google/android/gms/measurement/internal/zzz:getContext	()Landroid/content/Context;
      //   98: aload_0
      //   99: getfield 20	com/google/android/gms/measurement/internal/zzz$zza:zzboL	Lcom/google/android/gms/measurement/internal/zzz;
      //   102: getfield 157	com/google/android/gms/measurement/internal/zzz:zzboE	Lcom/google/android/gms/measurement/internal/zzz$zza;
      //   105: invokevirtual 161	com/google/android/gms/common/stats/zzb:zza	(Landroid/content/Context;Landroid/content/ServiceConnection;)V
      //   108: aload_0
      //   109: monitorexit
      //   110: return
      //   111: astore_3
      //   112: aload_0
      //   113: monitorexit
      //   114: aload_3
      //   115: athrow
      //   116: aload_0
      //   117: getfield 20	com/google/android/gms/measurement/internal/zzz$zza:zzboL	Lcom/google/android/gms/measurement/internal/zzz;
      //   120: invokevirtual 74	com/google/android/gms/measurement/internal/zzz:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
      //   123: getfield 116	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
      //   126: ldc 163
      //   128: aload 7
      //   130: invokevirtual 88	com/google/android/gms/measurement/internal/zzo$zza:zzm	(Ljava/lang/String;Ljava/lang/Object;)V
      //   133: aconst_null
      //   134: astore 4
      //   136: goto -53 -> 83
      //   139: astore 5
      //   141: aload_0
      //   142: getfield 20	com/google/android/gms/measurement/internal/zzz$zza:zzboL	Lcom/google/android/gms/measurement/internal/zzz;
      //   145: invokevirtual 74	com/google/android/gms/measurement/internal/zzz:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
      //   148: getfield 116	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
      //   151: ldc 165
      //   153: invokevirtual 100	com/google/android/gms/measurement/internal/zzo$zza:zzeB	(Ljava/lang/String;)V
      //   156: goto -73 -> 83
      //   159: aload_0
      //   160: getfield 20	com/google/android/gms/measurement/internal/zzz$zza:zzboL	Lcom/google/android/gms/measurement/internal/zzz;
      //   163: invokevirtual 55	com/google/android/gms/measurement/internal/zzz:zzBY	()Lcom/google/android/gms/measurement/internal/zzs;
      //   166: new 167	com/google/android/gms/measurement/internal/zzz$zza$1
      //   169: dup
      //   170: aload_0
      //   171: aload 4
      //   173: invokespecial 168	com/google/android/gms/measurement/internal/zzz$zza$1:<init>	(Lcom/google/android/gms/measurement/internal/zzz$zza;Lcom/google/android/gms/measurement/internal/zzl;)V
      //   176: invokevirtual 66	com/google/android/gms/measurement/internal/zzs:zzg	(Ljava/lang/Runnable;)V
      //   179: goto -71 -> 108
      //   182: astore 6
      //   184: goto -76 -> 108
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	187	0	this	zza
      //   0	187	1	paramComponentName	ComponentName
      //   0	187	2	paramIBinder	IBinder
      //   111	4	3	localObject	Object
      //   35	137	4	localzzl	zzl
      //   139	1	5	localRemoteException	RemoteException
      //   182	1	6	localIllegalArgumentException	java.lang.IllegalArgumentException
      //   43	86	7	str	java.lang.String
      //   52	6	8	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   7	12	111	finally
      //   16	33	111	finally
      //   37	54	111	finally
      //   62	83	111	finally
      //   88	108	111	finally
      //   108	110	111	finally
      //   112	114	111	finally
      //   116	133	111	finally
      //   141	156	111	finally
      //   159	179	111	finally
      //   37	54	139	android/os/RemoteException
      //   62	83	139	android/os/RemoteException
      //   116	133	139	android/os/RemoteException
      //   88	108	182	java/lang/IllegalArgumentException
    }
    
    public final void onServiceDisconnected(final ComponentName paramComponentName)
    {
      zzx.zzcx("MeasurementServiceConnection.onServiceDisconnected");
      zzz.this.zzBh().zzbnd.zzeB("Service disconnected");
      zzz.this.zzBY().zzg(new Runnable()
      {
        public final void run()
        {
          zzz.zza(zzz.this, paramComponentName);
        }
      });
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzz
 * JD-Core Version:    0.7.0.1
 */