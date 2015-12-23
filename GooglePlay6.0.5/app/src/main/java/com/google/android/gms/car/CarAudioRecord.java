package com.google.android.gms.car;

import java.io.InputStream;

public final class CarAudioRecord
{
  private final CarAudioManager mAudioManager;
  private volatile int mState;
  final int mStreamType;
  private final zzab zzacG;
  private InputStream zzacH;
  private final zza zzacI;
  
  /* Error */
  private void stop()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 23	com/google/android/gms/car/CarAudioRecord:mState	I
    //   6: iconst_1
    //   7: if_icmpeq +23 -> 30
    //   10: ldc 25
    //   12: iconst_3
    //   13: invokestatic 31	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   16: ifeq +11 -> 27
    //   19: ldc 25
    //   21: ldc 33
    //   23: invokestatic 39	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   26: pop
    //   27: aload_0
    //   28: monitorexit
    //   29: return
    //   30: aload_0
    //   31: iconst_0
    //   32: putfield 23	com/google/android/gms/car/CarAudioRecord:mState	I
    //   35: aload_0
    //   36: getfield 41	com/google/android/gms/car/CarAudioRecord:zzacG	Lcom/google/android/gms/car/zzab;
    //   39: aload_0
    //   40: getfield 43	com/google/android/gms/car/CarAudioRecord:zzacI	Lcom/google/android/gms/car/CarAudioRecord$zza;
    //   43: invokeinterface 49 2 0
    //   48: aload_0
    //   49: getfield 51	com/google/android/gms/car/CarAudioRecord:zzacH	Ljava/io/InputStream;
    //   52: invokevirtual 56	java/io/InputStream:close	()V
    //   55: ldc 25
    //   57: iconst_3
    //   58: invokestatic 31	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   61: ifeq -34 -> 27
    //   64: ldc 25
    //   66: ldc 58
    //   68: invokestatic 39	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   71: pop
    //   72: goto -45 -> 27
    //   75: astore_1
    //   76: aload_0
    //   77: monitorexit
    //   78: aload_1
    //   79: athrow
    //   80: astore 4
    //   82: ldc 25
    //   84: new 60	java/lang/StringBuilder
    //   87: dup
    //   88: ldc 62
    //   90: invokespecial 66	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   93: aload 4
    //   95: invokevirtual 70	android/os/RemoteException:getMessage	()Ljava/lang/String;
    //   98: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   101: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   104: invokestatic 80	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   107: pop
    //   108: aload_0
    //   109: getfield 23	com/google/android/gms/car/CarAudioRecord:mState	I
    //   112: iconst_1
    //   113: if_icmpne +21 -> 134
    //   116: aload_0
    //   117: getfield 51	com/google/android/gms/car/CarAudioRecord:zzacH	Ljava/io/InputStream;
    //   120: astore 10
    //   122: aload 10
    //   124: ifnull +10 -> 134
    //   127: aload_0
    //   128: getfield 51	com/google/android/gms/car/CarAudioRecord:zzacH	Ljava/io/InputStream;
    //   131: invokevirtual 56	java/io/InputStream:close	()V
    //   134: aload_0
    //   135: getfield 23	com/google/android/gms/car/CarAudioRecord:mState	I
    //   138: iconst_2
    //   139: if_icmpeq -84 -> 55
    //   142: aload_0
    //   143: getfield 82	com/google/android/gms/car/CarAudioRecord:mAudioManager	Lcom/google/android/gms/car/CarAudioManager;
    //   146: astore 6
    //   148: aload_0
    //   149: getfield 84	com/google/android/gms/car/CarAudioRecord:mStreamType	I
    //   152: ifeq +30 -> 182
    //   155: new 86	java/lang/RuntimeException
    //   158: dup
    //   159: new 60	java/lang/StringBuilder
    //   162: dup
    //   163: ldc 88
    //   165: invokespecial 66	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   168: aload_0
    //   169: getfield 84	com/google/android/gms/car/CarAudioRecord:mStreamType	I
    //   172: invokevirtual 91	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   175: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   178: invokespecial 92	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   181: athrow
    //   182: aload 6
    //   184: getfield 98	com/google/android/gms/car/CarAudioManager:zzacE	Ljava/lang/Object;
    //   187: astore 7
    //   189: aload 7
    //   191: monitorenter
    //   192: aload 6
    //   194: getfield 102	com/google/android/gms/car/CarAudioManager:zzacF	Ljava/util/LinkedList;
    //   197: aload_0
    //   198: invokevirtual 108	java/util/LinkedList:remove	(Ljava/lang/Object;)Z
    //   201: pop
    //   202: aload 7
    //   204: monitorexit
    //   205: aload_0
    //   206: iconst_2
    //   207: putfield 23	com/google/android/gms/car/CarAudioRecord:mState	I
    //   210: goto -155 -> 55
    //   213: astore 8
    //   215: aload 7
    //   217: monitorexit
    //   218: aload 8
    //   220: athrow
    //   221: astore_2
    //   222: goto -167 -> 55
    //   225: astore 11
    //   227: goto -93 -> 134
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	230	0	this	CarAudioRecord
    //   75	4	1	localObject1	Object
    //   221	1	2	localIOException1	java.io.IOException
    //   80	14	4	localRemoteException	android.os.RemoteException
    //   146	47	6	localCarAudioManager	CarAudioManager
    //   213	6	8	localObject3	Object
    //   120	3	10	localInputStream	InputStream
    //   225	1	11	localIOException2	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   2	27	75	finally
    //   30	35	75	finally
    //   35	55	75	finally
    //   55	72	75	finally
    //   82	122	75	finally
    //   127	134	75	finally
    //   134	182	75	finally
    //   182	192	75	finally
    //   205	210	75	finally
    //   218	221	75	finally
    //   35	55	80	android/os/RemoteException
    //   192	205	213	finally
    //   215	218	213	finally
    //   35	55	221	java/io/IOException
    //   127	134	225	java/io/IOException
  }
  
  /* Error */
  final void zzv$1385ff()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 23	com/google/android/gms/car/CarAudioRecord:mState	I
    //   6: istore_2
    //   7: iload_2
    //   8: iconst_2
    //   9: if_icmpne +6 -> 15
    //   12: aload_0
    //   13: monitorexit
    //   14: return
    //   15: aload_0
    //   16: invokespecial 111	com/google/android/gms/car/CarAudioRecord:stop	()V
    //   19: aload_0
    //   20: getfield 41	com/google/android/gms/car/CarAudioRecord:zzacG	Lcom/google/android/gms/car/zzab;
    //   23: aload_0
    //   24: getfield 43	com/google/android/gms/car/CarAudioRecord:zzacI	Lcom/google/android/gms/car/CarAudioRecord$zza;
    //   27: invokeinterface 114 2 0
    //   32: aload_0
    //   33: iconst_2
    //   34: putfield 23	com/google/android/gms/car/CarAudioRecord:mState	I
    //   37: ldc 25
    //   39: iconst_3
    //   40: invokestatic 31	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   43: ifeq -31 -> 12
    //   46: ldc 25
    //   48: ldc 116
    //   50: invokestatic 39	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   53: pop
    //   54: goto -42 -> 12
    //   57: astore_1
    //   58: aload_0
    //   59: monitorexit
    //   60: aload_1
    //   61: athrow
    //   62: astore_3
    //   63: goto -31 -> 32
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	66	0	this	CarAudioRecord
    //   57	4	1	localObject	Object
    //   6	4	2	i	int
    //   62	1	3	localRemoteException	android.os.RemoteException
    // Exception table:
    //   from	to	target	type
    //   2	7	57	finally
    //   15	19	57	finally
    //   19	32	57	finally
    //   32	54	57	finally
    //   19	32	62	android/os/RemoteException
  }
  
  private static final class zza
    extends zzac.zza
  {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarAudioRecord
 * JD-Core Version:    0.7.0.1
 */