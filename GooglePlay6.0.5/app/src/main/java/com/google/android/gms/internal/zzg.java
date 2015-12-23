package com.google.android.gms.internal;

import java.util.concurrent.BlockingQueue;

public final class zzg
  extends Thread
{
  private final zzb zzj;
  private final zzn zzk;
  volatile boolean zzl = false;
  private final BlockingQueue<zzk<?>> zzy;
  private final zzf zzz;
  
  public zzg(BlockingQueue<zzk<?>> paramBlockingQueue, zzf paramzzf, zzb paramzzb, zzn paramzzn)
  {
    this.zzy = paramBlockingQueue;
    this.zzz = paramzzf;
    this.zzj = paramzzb;
    this.zzk = paramzzn;
  }
  
  /* Error */
  public final void run()
  {
    // Byte code:
    //   0: bipush 10
    //   2: invokestatic 43	android/os/Process:setThreadPriority	(I)V
    //   5: invokestatic 49	android/os/SystemClock:elapsedRealtime	()J
    //   8: lstore_1
    //   9: aload_0
    //   10: getfield 24	com/google/android/gms/internal/zzg:zzy	Ljava/util/concurrent/BlockingQueue;
    //   13: invokeinterface 55 1 0
    //   18: checkcast 57	com/google/android/gms/internal/zzk
    //   21: astore 4
    //   23: aload 4
    //   25: ldc 59
    //   27: invokevirtual 63	com/google/android/gms/internal/zzk:zzc	(Ljava/lang/String;)V
    //   30: aload 4
    //   32: getfield 66	com/google/android/gms/internal/zzk:zzK	Z
    //   35: ifeq +57 -> 92
    //   38: aload 4
    //   40: ldc 68
    //   42: invokevirtual 71	com/google/android/gms/internal/zzk:zzd	(Ljava/lang/String;)V
    //   45: goto -40 -> 5
    //   48: astore 8
    //   50: aload 8
    //   52: invokestatic 49	android/os/SystemClock:elapsedRealtime	()J
    //   55: lload_1
    //   56: lsub
    //   57: putfield 75	com/google/android/gms/internal/zzr:zzB	J
    //   60: aload 8
    //   62: invokestatic 79	com/google/android/gms/internal/zzk:zzb	(Lcom/google/android/gms/internal/zzr;)Lcom/google/android/gms/internal/zzr;
    //   65: astore 9
    //   67: aload_0
    //   68: getfield 30	com/google/android/gms/internal/zzg:zzk	Lcom/google/android/gms/internal/zzn;
    //   71: aload 4
    //   73: aload 9
    //   75: invokeinterface 85 3 0
    //   80: goto -75 -> 5
    //   83: astore_3
    //   84: aload_0
    //   85: getfield 22	com/google/android/gms/internal/zzg:zzl	Z
    //   88: ifeq -83 -> 5
    //   91: return
    //   92: getstatic 91	android/os/Build$VERSION:SDK_INT	I
    //   95: bipush 14
    //   97: if_icmplt +11 -> 108
    //   100: aload 4
    //   102: getfield 94	com/google/android/gms/internal/zzk:zzF	I
    //   105: invokestatic 99	android/net/TrafficStats:setThreadStatsTag	(I)V
    //   108: aload_0
    //   109: getfield 26	com/google/android/gms/internal/zzg:zzz	Lcom/google/android/gms/internal/zzf;
    //   112: aload 4
    //   114: invokeinterface 104 2 0
    //   119: astore 10
    //   121: aload 4
    //   123: ldc 106
    //   125: invokevirtual 63	com/google/android/gms/internal/zzk:zzc	(Ljava/lang/String;)V
    //   128: aload 10
    //   130: getfield 111	com/google/android/gms/internal/zzi:zzA	Z
    //   133: ifeq +84 -> 217
    //   136: aload 4
    //   138: getfield 114	com/google/android/gms/internal/zzk:zzL	Z
    //   141: ifeq +76 -> 217
    //   144: aload 4
    //   146: ldc 116
    //   148: invokevirtual 71	com/google/android/gms/internal/zzk:zzd	(Ljava/lang/String;)V
    //   151: goto -146 -> 5
    //   154: astore 5
    //   156: iconst_1
    //   157: anewarray 118	java/lang/Object
    //   160: astore 6
    //   162: aload 6
    //   164: iconst_0
    //   165: aload 5
    //   167: invokevirtual 122	java/lang/Exception:toString	()Ljava/lang/String;
    //   170: aastore
    //   171: aload 5
    //   173: ldc 124
    //   175: aload 6
    //   177: invokestatic 129	com/google/android/gms/internal/zzs:zza	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   180: new 35	com/google/android/gms/internal/zzr
    //   183: dup
    //   184: aload 5
    //   186: invokespecial 132	com/google/android/gms/internal/zzr:<init>	(Ljava/lang/Throwable;)V
    //   189: astore 7
    //   191: aload 7
    //   193: invokestatic 49	android/os/SystemClock:elapsedRealtime	()J
    //   196: lload_1
    //   197: lsub
    //   198: putfield 75	com/google/android/gms/internal/zzr:zzB	J
    //   201: aload_0
    //   202: getfield 30	com/google/android/gms/internal/zzg:zzk	Lcom/google/android/gms/internal/zzn;
    //   205: aload 4
    //   207: aload 7
    //   209: invokeinterface 85 3 0
    //   214: goto -209 -> 5
    //   217: aload 4
    //   219: aload 10
    //   221: invokevirtual 135	com/google/android/gms/internal/zzk:zza	(Lcom/google/android/gms/internal/zzi;)Lcom/google/android/gms/internal/zzm;
    //   224: astore 11
    //   226: aload 4
    //   228: ldc 137
    //   230: invokevirtual 63	com/google/android/gms/internal/zzk:zzc	(Ljava/lang/String;)V
    //   233: aload 4
    //   235: getfield 140	com/google/android/gms/internal/zzk:zzJ	Z
    //   238: ifeq +37 -> 275
    //   241: aload 11
    //   243: getfield 146	com/google/android/gms/internal/zzm:zzaf	Lcom/google/android/gms/internal/zzb$zza;
    //   246: ifnull +29 -> 275
    //   249: aload_0
    //   250: getfield 28	com/google/android/gms/internal/zzg:zzj	Lcom/google/android/gms/internal/zzb;
    //   253: aload 4
    //   255: getfield 150	com/google/android/gms/internal/zzk:zzE	Ljava/lang/String;
    //   258: aload 11
    //   260: getfield 146	com/google/android/gms/internal/zzm:zzaf	Lcom/google/android/gms/internal/zzb$zza;
    //   263: invokeinterface 155 3 0
    //   268: aload 4
    //   270: ldc 157
    //   272: invokevirtual 63	com/google/android/gms/internal/zzk:zzc	(Ljava/lang/String;)V
    //   275: aload 4
    //   277: iconst_1
    //   278: putfield 114	com/google/android/gms/internal/zzk:zzL	Z
    //   281: aload_0
    //   282: getfield 30	com/google/android/gms/internal/zzg:zzk	Lcom/google/android/gms/internal/zzn;
    //   285: aload 4
    //   287: aload 11
    //   289: invokeinterface 160 3 0
    //   294: goto -289 -> 5
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	297	0	this	zzg
    //   8	189	1	l	long
    //   83	1	3	localInterruptedException	java.lang.InterruptedException
    //   21	265	4	localzzk	zzk
    //   154	31	5	localException	java.lang.Exception
    //   160	16	6	arrayOfObject	java.lang.Object[]
    //   189	19	7	localzzr1	zzr
    //   48	13	8	localzzr2	zzr
    //   65	9	9	localzzr3	zzr
    //   119	101	10	localzzi	zzi
    //   224	64	11	localzzm	zzm
    // Exception table:
    //   from	to	target	type
    //   23	45	48	com/google/android/gms/internal/zzr
    //   92	108	48	com/google/android/gms/internal/zzr
    //   108	151	48	com/google/android/gms/internal/zzr
    //   217	275	48	com/google/android/gms/internal/zzr
    //   275	294	48	com/google/android/gms/internal/zzr
    //   9	23	83	java/lang/InterruptedException
    //   23	45	154	java/lang/Exception
    //   92	108	154	java/lang/Exception
    //   108	151	154	java/lang/Exception
    //   217	275	154	java/lang/Exception
    //   275	294	154	java/lang/Exception
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzg
 * JD-Core Version:    0.7.0.1
 */