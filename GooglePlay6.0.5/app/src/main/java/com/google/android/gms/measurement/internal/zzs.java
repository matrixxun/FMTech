package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.zzx;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public final class zzs
  extends zzw
{
  zzc zzbnH;
  zzc zzbnI;
  private final BlockingQueue<FutureTask<?>> zzbnJ = new LinkedBlockingQueue();
  private final BlockingQueue<FutureTask<?>> zzbnK = new LinkedBlockingQueue();
  private final Thread.UncaughtExceptionHandler zzbnL = new zzb("Thread death: Uncaught exception on worker thread");
  private final Thread.UncaughtExceptionHandler zzbnM = new zzb("Thread death: Uncaught exception on network thread");
  final Object zzbnN = new Object();
  final Semaphore zzbnO = new Semaphore(2);
  volatile boolean zzbnP;
  
  zzs(zzt paramzzt)
  {
    super(paramzzt);
  }
  
  public final void checkOnWorkerThread()
  {
    if (Thread.currentThread() != this.zzbnH) {
      throw new IllegalStateException("Call expected from worker thread");
    }
  }
  
  protected final void onInitialize() {}
  
  public final void zzBU()
  {
    if (Thread.currentThread() != this.zzbnI) {
      throw new IllegalStateException("Call expected from network thread");
    }
  }
  
  public final void zzg(Runnable paramRunnable)
    throws IllegalStateException
  {
    zziL();
    zzx.zzC(paramRunnable);
    zza localzza = new zza(paramRunnable, "Task exception on worker thread");
    synchronized (this.zzbnN)
    {
      this.zzbnJ.add(localzza);
      if (this.zzbnH == null)
      {
        this.zzbnH = new zzc("Measurement Worker", this.zzbnJ);
        this.zzbnH.setUncaughtExceptionHandler(this.zzbnL);
        this.zzbnH.start();
        return;
      }
      this.zzbnH.zzeZ();
    }
  }
  
  public final void zzh(Runnable paramRunnable)
    throws IllegalStateException
  {
    zziL();
    zzx.zzC(paramRunnable);
    zza localzza = new zza(paramRunnable, "Task exception on network thread");
    synchronized (this.zzbnN)
    {
      this.zzbnK.add(localzza);
      if (this.zzbnI == null)
      {
        this.zzbnI = new zzc("Measurement Network", this.zzbnK);
        this.zzbnI.setUncaughtExceptionHandler(this.zzbnM);
        this.zzbnI.start();
        return;
      }
      this.zzbnI.zzeZ();
    }
  }
  
  private final class zza<V>
    extends FutureTask<V>
  {
    private final String zzbnQ;
    
    zza(Runnable paramRunnable, String paramString)
    {
      super(null);
      zzx.zzC(paramString);
      this.zzbnQ = paramString;
    }
    
    protected final void setException(Throwable paramThrowable)
    {
      zzs.this.zzBh().zzbmW.zzm(this.zzbnQ, paramThrowable);
      super.setException(paramThrowable);
    }
  }
  
  private final class zzb
    implements Thread.UncaughtExceptionHandler
  {
    private final String zzbnQ;
    
    public zzb(String paramString)
    {
      zzx.zzC(paramString);
      this.zzbnQ = paramString;
    }
    
    public final void uncaughtException(Thread paramThread, Throwable paramThrowable)
    {
      try
      {
        zzs.this.zzBh().zzbmW.zzm(this.zzbnQ, paramThrowable);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
  }
  
  private final class zzc
    extends Thread
  {
    private final Object zzbnS;
    private final BlockingQueue<FutureTask<?>> zzbnT;
    
    public zzc(BlockingQueue<FutureTask<?>> paramBlockingQueue)
    {
      zzx.zzC(paramBlockingQueue);
      this.zzbnS = new Object();
      Object localObject;
      this.zzbnT = localObject;
      setName(paramBlockingQueue);
    }
    
    /* Error */
    public final void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 31	com/google/android/gms/measurement/internal/zzs$zzc:zzbnT	Ljava/util/concurrent/BlockingQueue;
      //   4: invokeinterface 44 1 0
      //   9: checkcast 46	java/util/concurrent/FutureTask
      //   12: astore_1
      //   13: aload_1
      //   14: ifnull +10 -> 24
      //   17: aload_1
      //   18: invokevirtual 48	java/util/concurrent/FutureTask:run	()V
      //   21: goto -21 -> 0
      //   24: aload_0
      //   25: getfield 29	com/google/android/gms/measurement/internal/zzs$zzc:zzbnS	Ljava/lang/Object;
      //   28: astore_2
      //   29: aload_2
      //   30: monitorenter
      //   31: aload_0
      //   32: getfield 31	com/google/android/gms/measurement/internal/zzs$zzc:zzbnT	Ljava/util/concurrent/BlockingQueue;
      //   35: invokeinterface 51 1 0
      //   40: ifnonnull +27 -> 67
      //   43: aload_0
      //   44: getfield 15	com/google/android/gms/measurement/internal/zzs$zzc:zzbnR	Lcom/google/android/gms/measurement/internal/zzs;
      //   47: getfield 57	com/google/android/gms/measurement/internal/zzs:zzbnP	Z
      //   50: istore 6
      //   52: iload 6
      //   54: ifne +13 -> 67
      //   57: aload_0
      //   58: getfield 29	com/google/android/gms/measurement/internal/zzs$zzc:zzbnS	Ljava/lang/Object;
      //   61: ldc2_w 58
      //   64: invokevirtual 63	java/lang/Object:wait	(J)V
      //   67: aload_2
      //   68: monitorexit
      //   69: aload_0
      //   70: getfield 15	com/google/android/gms/measurement/internal/zzs$zzc:zzbnR	Lcom/google/android/gms/measurement/internal/zzs;
      //   73: getfield 66	com/google/android/gms/measurement/internal/zzs:zzbnN	Ljava/lang/Object;
      //   76: astore 4
      //   78: aload 4
      //   80: monitorenter
      //   81: aload_0
      //   82: getfield 31	com/google/android/gms/measurement/internal/zzs$zzc:zzbnT	Ljava/util/concurrent/BlockingQueue;
      //   85: invokeinterface 51 1 0
      //   90: ifnonnull +141 -> 231
      //   93: aload_0
      //   94: getfield 15	com/google/android/gms/measurement/internal/zzs$zzc:zzbnR	Lcom/google/android/gms/measurement/internal/zzs;
      //   97: getfield 70	com/google/android/gms/measurement/internal/zzs:zzbnO	Ljava/util/concurrent/Semaphore;
      //   100: invokevirtual 75	java/util/concurrent/Semaphore:release	()V
      //   103: aload_0
      //   104: getfield 15	com/google/android/gms/measurement/internal/zzs$zzc:zzbnR	Lcom/google/android/gms/measurement/internal/zzs;
      //   107: getfield 66	com/google/android/gms/measurement/internal/zzs:zzbnN	Ljava/lang/Object;
      //   110: invokevirtual 78	java/lang/Object:notifyAll	()V
      //   113: aload_0
      //   114: aload_0
      //   115: getfield 15	com/google/android/gms/measurement/internal/zzs$zzc:zzbnR	Lcom/google/android/gms/measurement/internal/zzs;
      //   118: getfield 82	com/google/android/gms/measurement/internal/zzs:zzbnH	Lcom/google/android/gms/measurement/internal/zzs$zzc;
      //   121: if_acmpne +62 -> 183
      //   124: aload_0
      //   125: getfield 15	com/google/android/gms/measurement/internal/zzs$zzc:zzbnR	Lcom/google/android/gms/measurement/internal/zzs;
      //   128: aconst_null
      //   129: putfield 82	com/google/android/gms/measurement/internal/zzs:zzbnH	Lcom/google/android/gms/measurement/internal/zzs$zzc;
      //   132: aload 4
      //   134: monitorexit
      //   135: return
      //   136: astore 7
      //   138: aload_0
      //   139: getfield 15	com/google/android/gms/measurement/internal/zzs$zzc:zzbnR	Lcom/google/android/gms/measurement/internal/zzs;
      //   142: invokevirtual 86	com/google/android/gms/measurement/internal/zzs:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
      //   145: getfield 92	com/google/android/gms/measurement/internal/zzo:zzbmZ	Lcom/google/android/gms/measurement/internal/zzo$zza;
      //   148: new 94	java/lang/StringBuilder
      //   151: dup
      //   152: invokespecial 95	java/lang/StringBuilder:<init>	()V
      //   155: aload_0
      //   156: invokevirtual 99	com/google/android/gms/measurement/internal/zzs$zzc:getName	()Ljava/lang/String;
      //   159: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   162: ldc 105
      //   164: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   167: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   170: aload 7
      //   172: invokevirtual 114	com/google/android/gms/measurement/internal/zzo$zza:zzm	(Ljava/lang/String;Ljava/lang/Object;)V
      //   175: goto -108 -> 67
      //   178: astore_3
      //   179: aload_2
      //   180: monitorexit
      //   181: aload_3
      //   182: athrow
      //   183: aload_0
      //   184: aload_0
      //   185: getfield 15	com/google/android/gms/measurement/internal/zzs$zzc:zzbnR	Lcom/google/android/gms/measurement/internal/zzs;
      //   188: getfield 117	com/google/android/gms/measurement/internal/zzs:zzbnI	Lcom/google/android/gms/measurement/internal/zzs$zzc;
      //   191: if_acmpne +22 -> 213
      //   194: aload_0
      //   195: getfield 15	com/google/android/gms/measurement/internal/zzs$zzc:zzbnR	Lcom/google/android/gms/measurement/internal/zzs;
      //   198: aconst_null
      //   199: putfield 117	com/google/android/gms/measurement/internal/zzs:zzbnI	Lcom/google/android/gms/measurement/internal/zzs$zzc;
      //   202: goto -70 -> 132
      //   205: astore 5
      //   207: aload 4
      //   209: monitorexit
      //   210: aload 5
      //   212: athrow
      //   213: aload_0
      //   214: getfield 15	com/google/android/gms/measurement/internal/zzs$zzc:zzbnR	Lcom/google/android/gms/measurement/internal/zzs;
      //   217: invokevirtual 86	com/google/android/gms/measurement/internal/zzs:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
      //   220: getfield 120	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
      //   223: ldc 122
      //   225: invokevirtual 125	com/google/android/gms/measurement/internal/zzo$zza:zzeB	(Ljava/lang/String;)V
      //   228: goto -96 -> 132
      //   231: aload 4
      //   233: monitorexit
      //   234: goto -234 -> 0
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	237	0	this	zzc
      //   12	6	1	localFutureTask	FutureTask
      //   28	152	2	localObject1	Object
      //   178	4	3	localObject2	Object
      //   205	6	5	localObject4	Object
      //   50	3	6	bool	boolean
      //   136	35	7	localInterruptedException	java.lang.InterruptedException
      // Exception table:
      //   from	to	target	type
      //   57	67	136	java/lang/InterruptedException
      //   31	52	178	finally
      //   57	67	178	finally
      //   67	69	178	finally
      //   138	175	178	finally
      //   179	181	178	finally
      //   81	132	205	finally
      //   132	135	205	finally
      //   183	202	205	finally
      //   207	210	205	finally
      //   213	228	205	finally
      //   231	234	205	finally
    }
    
    public final void zzeZ()
    {
      synchronized (this.zzbnS)
      {
        this.zzbnS.notifyAll();
        return;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzs
 * JD-Core Version:    0.7.0.1
 */