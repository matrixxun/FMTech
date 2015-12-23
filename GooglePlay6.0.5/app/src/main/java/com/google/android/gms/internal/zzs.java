package com.google.android.gms.internal;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class zzs
{
  public static boolean DEBUG = Log.isLoggable("Volley", 2);
  public static String TAG = "Volley";
  
  public static void zza(String paramString, Object... paramVarArgs)
  {
    if (DEBUG) {
      Log.v(TAG, zzd(paramString, paramVarArgs));
    }
  }
  
  public static void zza(Throwable paramThrowable, String paramString, Object... paramVarArgs)
  {
    Log.e(TAG, zzd(paramString, paramVarArgs), paramThrowable);
  }
  
  public static void zzb(String paramString, Object... paramVarArgs)
  {
    Log.d(TAG, zzd(paramString, paramVarArgs));
  }
  
  public static void zzc(String paramString, Object... paramVarArgs)
  {
    Log.e(TAG, zzd(paramString, paramVarArgs));
  }
  
  private static String zzd(String paramString, Object... paramVarArgs)
  {
    StackTraceElement[] arrayOfStackTraceElement;
    int i;
    label20:
    String str4;
    if (paramVarArgs == null)
    {
      arrayOfStackTraceElement = new Throwable().fillInStackTrace().getStackTrace();
      i = 2;
      if (i >= arrayOfStackTraceElement.length) {
        break label174;
      }
      if (arrayOfStackTraceElement[i].getClass().equals(zzs.class)) {
        break label168;
      }
      String str2 = arrayOfStackTraceElement[i].getClassName();
      String str3 = str2.substring(1 + str2.lastIndexOf('.'));
      str4 = str3.substring(1 + str3.lastIndexOf('$'));
    }
    label168:
    label174:
    for (String str1 = str4 + "." + arrayOfStackTraceElement[i].getMethodName();; str1 = "<unknown>")
    {
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Long.valueOf(Thread.currentThread().getId());
      arrayOfObject[1] = str1;
      arrayOfObject[2] = paramString;
      return String.format(localLocale, "[%d] %s: %s", arrayOfObject);
      paramString = String.format(Locale.US, paramString, paramVarArgs);
      break;
      i++;
      break label20;
    }
  }
  
  static final class zza
  {
    public static final boolean zzaj = zzs.DEBUG;
    private final List<zza> zzak = new ArrayList();
    private boolean zzal = false;
    
    protected final void finalize()
      throws Throwable
    {
      if (!this.zzal)
      {
        zzd("Request on the loose");
        zzs.zzc("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
      }
    }
    
    public final void zza(String paramString, long paramLong)
    {
      try
      {
        if (this.zzal) {
          throw new IllegalStateException("Marker added to finished log");
        }
      }
      finally {}
      this.zzak.add(new zza(paramString, paramLong, SystemClock.elapsedRealtime()));
    }
    
    /* Error */
    public final void zzd(String paramString)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: iconst_1
      //   4: putfield 29	com/google/android/gms/internal/zzs$zza:zzal	Z
      //   7: aload_0
      //   8: getfield 27	com/google/android/gms/internal/zzs$zza:zzak	Ljava/util/List;
      //   11: invokeinterface 73 1 0
      //   16: istore_3
      //   17: iload_3
      //   18: ifne +16 -> 34
      //   21: lconst_0
      //   22: lstore 6
      //   24: lload 6
      //   26: lconst_0
      //   27: lcmp
      //   28: ifgt +58 -> 86
      //   31: aload_0
      //   32: monitorexit
      //   33: return
      //   34: aload_0
      //   35: getfield 27	com/google/android/gms/internal/zzs$zza:zzak	Ljava/util/List;
      //   38: iconst_0
      //   39: invokeinterface 77 2 0
      //   44: checkcast 54	com/google/android/gms/internal/zzs$zza$zza
      //   47: getfield 81	com/google/android/gms/internal/zzs$zza$zza:time	J
      //   50: lstore 4
      //   52: aload_0
      //   53: getfield 27	com/google/android/gms/internal/zzs$zza:zzak	Ljava/util/List;
      //   56: iconst_m1
      //   57: aload_0
      //   58: getfield 27	com/google/android/gms/internal/zzs$zza:zzak	Ljava/util/List;
      //   61: invokeinterface 73 1 0
      //   66: iadd
      //   67: invokeinterface 77 2 0
      //   72: checkcast 54	com/google/android/gms/internal/zzs$zza$zza
      //   75: getfield 81	com/google/android/gms/internal/zzs$zza$zza:time	J
      //   78: lload 4
      //   80: lsub
      //   81: lstore 6
      //   83: goto -59 -> 24
      //   86: aload_0
      //   87: getfield 27	com/google/android/gms/internal/zzs$zza:zzak	Ljava/util/List;
      //   90: iconst_0
      //   91: invokeinterface 77 2 0
      //   96: checkcast 54	com/google/android/gms/internal/zzs$zza$zza
      //   99: getfield 81	com/google/android/gms/internal/zzs$zza$zza:time	J
      //   102: lstore 8
      //   104: iconst_2
      //   105: anewarray 4	java/lang/Object
      //   108: astore 10
      //   110: aload 10
      //   112: iconst_0
      //   113: lload 6
      //   115: invokestatic 87	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   118: aastore
      //   119: aload 10
      //   121: iconst_1
      //   122: aload_1
      //   123: aastore
      //   124: ldc 89
      //   126: aload 10
      //   128: invokestatic 92	com/google/android/gms/internal/zzs:zzb	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   131: aload_0
      //   132: getfield 27	com/google/android/gms/internal/zzs$zza:zzak	Ljava/util/List;
      //   135: invokeinterface 96 1 0
      //   140: astore 11
      //   142: lload 8
      //   144: lstore 12
      //   146: aload 11
      //   148: invokeinterface 102 1 0
      //   153: ifeq -122 -> 31
      //   156: aload 11
      //   158: invokeinterface 106 1 0
      //   163: checkcast 54	com/google/android/gms/internal/zzs$zza$zza
      //   166: astore 14
      //   168: aload 14
      //   170: getfield 81	com/google/android/gms/internal/zzs$zza$zza:time	J
      //   173: lstore 15
      //   175: iconst_3
      //   176: anewarray 4	java/lang/Object
      //   179: astore 17
      //   181: aload 17
      //   183: iconst_0
      //   184: lload 15
      //   186: lload 12
      //   188: lsub
      //   189: invokestatic 87	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   192: aastore
      //   193: aload 17
      //   195: iconst_1
      //   196: aload 14
      //   198: getfield 109	com/google/android/gms/internal/zzs$zza$zza:zzam	J
      //   201: invokestatic 87	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   204: aastore
      //   205: aload 17
      //   207: iconst_2
      //   208: aload 14
      //   210: getfield 113	com/google/android/gms/internal/zzs$zza$zza:name	Ljava/lang/String;
      //   213: aastore
      //   214: ldc 115
      //   216: aload 17
      //   218: invokestatic 92	com/google/android/gms/internal/zzs:zzb	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   221: lload 15
      //   223: lstore 12
      //   225: goto -79 -> 146
      //   228: astore_2
      //   229: aload_0
      //   230: monitorexit
      //   231: aload_2
      //   232: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	233	0	this	zza
      //   0	233	1	paramString	String
      //   228	4	2	localObject	Object
      //   16	2	3	i	int
      //   50	29	4	l1	long
      //   22	92	6	l2	long
      //   102	41	8	l3	long
      //   108	19	10	arrayOfObject1	Object[]
      //   140	17	11	localIterator	java.util.Iterator
      //   144	80	12	l4	long
      //   166	43	14	localzza	zza
      //   173	49	15	l5	long
      //   179	38	17	arrayOfObject2	Object[]
      // Exception table:
      //   from	to	target	type
      //   2	17	228	finally
      //   34	83	228	finally
      //   86	142	228	finally
      //   146	221	228	finally
    }
    
    private static final class zza
    {
      public final String name;
      public final long time;
      public final long zzam;
      
      public zza(String paramString, long paramLong1, long paramLong2)
      {
        this.name = paramString;
        this.zzam = paramLong1;
        this.time = paramLong2;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzs
 * JD-Core Version:    0.7.0.1
 */