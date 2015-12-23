package com.android.volley;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VolleyLog
{
  public static boolean DEBUG = Log.isLoggable("Volley", 2);
  public static String TAG = "Volley";
  
  private static String buildMessage(String paramString, Object... paramVarArgs)
  {
    String str1;
    StackTraceElement[] arrayOfStackTraceElement;
    String str2;
    if (paramVarArgs == null)
    {
      str1 = paramString;
      arrayOfStackTraceElement = new Throwable().fillInStackTrace().getStackTrace();
      str2 = "<unknown>";
    }
    for (int i = 2;; i++) {
      if (i < arrayOfStackTraceElement.length)
      {
        if (!arrayOfStackTraceElement[i].getClass().equals(VolleyLog.class))
        {
          String str3 = arrayOfStackTraceElement[i].getClassName();
          String str4 = str3.substring(1 + str3.lastIndexOf('.'));
          String str5 = str4.substring(1 + str4.lastIndexOf('$'));
          str2 = str5 + "." + arrayOfStackTraceElement[i].getMethodName();
        }
      }
      else
      {
        Locale localLocale = Locale.US;
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = Long.valueOf(Thread.currentThread().getId());
        arrayOfObject[1] = str2;
        arrayOfObject[2] = str1;
        return String.format(localLocale, "[%d] %s: %s", arrayOfObject);
        str1 = String.format(Locale.US, paramString, paramVarArgs);
        break;
      }
    }
  }
  
  public static void d(String paramString, Object... paramVarArgs)
  {
    Log.d(TAG, buildMessage(paramString, paramVarArgs));
  }
  
  public static void e(String paramString, Object... paramVarArgs)
  {
    Log.e(TAG, buildMessage(paramString, paramVarArgs));
  }
  
  public static void e(Throwable paramThrowable, String paramString, Object... paramVarArgs)
  {
    Log.e(TAG, buildMessage(paramString, paramVarArgs), paramThrowable);
  }
  
  public static void v(String paramString, Object... paramVarArgs)
  {
    if (DEBUG) {
      Log.v(TAG, buildMessage(paramString, paramVarArgs));
    }
  }
  
  public static void wtf(String paramString, Object... paramVarArgs)
  {
    Log.wtf(TAG, buildMessage(paramString, paramVarArgs));
  }
  
  static final class MarkerLog
  {
    public static final boolean ENABLED = VolleyLog.DEBUG;
    private boolean mFinished = false;
    private final List<Marker> mMarkers = new ArrayList();
    
    public final void add(String paramString, long paramLong)
    {
      try
      {
        if (this.mFinished) {
          throw new IllegalStateException("Marker added to finished log");
        }
      }
      finally {}
      this.mMarkers.add(new Marker(paramString, paramLong, SystemClock.elapsedRealtime()));
    }
    
    protected final void finalize()
      throws Throwable
    {
      if (!this.mFinished)
      {
        finish("Request on the loose");
        VolleyLog.e("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
      }
    }
    
    /* Error */
    public final void finish(String paramString)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: iconst_1
      //   4: putfield 29	com/android/volley/VolleyLog$MarkerLog:mFinished	Z
      //   7: aload_0
      //   8: getfield 27	com/android/volley/VolleyLog$MarkerLog:mMarkers	Ljava/util/List;
      //   11: invokeinterface 72 1 0
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
      //   35: getfield 27	com/android/volley/VolleyLog$MarkerLog:mMarkers	Ljava/util/List;
      //   38: iconst_0
      //   39: invokeinterface 76 2 0
      //   44: checkcast 40	com/android/volley/VolleyLog$MarkerLog$Marker
      //   47: getfield 80	com/android/volley/VolleyLog$MarkerLog$Marker:time	J
      //   50: lstore 4
      //   52: aload_0
      //   53: getfield 27	com/android/volley/VolleyLog$MarkerLog:mMarkers	Ljava/util/List;
      //   56: iconst_m1
      //   57: aload_0
      //   58: getfield 27	com/android/volley/VolleyLog$MarkerLog:mMarkers	Ljava/util/List;
      //   61: invokeinterface 72 1 0
      //   66: iadd
      //   67: invokeinterface 76 2 0
      //   72: checkcast 40	com/android/volley/VolleyLog$MarkerLog$Marker
      //   75: getfield 80	com/android/volley/VolleyLog$MarkerLog$Marker:time	J
      //   78: lload 4
      //   80: lsub
      //   81: lstore 6
      //   83: goto -59 -> 24
      //   86: aload_0
      //   87: getfield 27	com/android/volley/VolleyLog$MarkerLog:mMarkers	Ljava/util/List;
      //   90: iconst_0
      //   91: invokeinterface 76 2 0
      //   96: checkcast 40	com/android/volley/VolleyLog$MarkerLog$Marker
      //   99: getfield 80	com/android/volley/VolleyLog$MarkerLog$Marker:time	J
      //   102: lstore 8
      //   104: iconst_2
      //   105: anewarray 4	java/lang/Object
      //   108: astore 10
      //   110: aload 10
      //   112: iconst_0
      //   113: lload 6
      //   115: invokestatic 86	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   118: aastore
      //   119: aload 10
      //   121: iconst_1
      //   122: aload_1
      //   123: aastore
      //   124: ldc 88
      //   126: aload 10
      //   128: invokestatic 91	com/android/volley/VolleyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   131: aload_0
      //   132: getfield 27	com/android/volley/VolleyLog$MarkerLog:mMarkers	Ljava/util/List;
      //   135: invokeinterface 95 1 0
      //   140: astore 11
      //   142: aload 11
      //   144: invokeinterface 101 1 0
      //   149: ifeq -118 -> 31
      //   152: aload 11
      //   154: invokeinterface 105 1 0
      //   159: checkcast 40	com/android/volley/VolleyLog$MarkerLog$Marker
      //   162: astore 12
      //   164: aload 12
      //   166: getfield 80	com/android/volley/VolleyLog$MarkerLog$Marker:time	J
      //   169: lstore 13
      //   171: iconst_3
      //   172: anewarray 4	java/lang/Object
      //   175: astore 15
      //   177: aload 15
      //   179: iconst_0
      //   180: lload 13
      //   182: lload 8
      //   184: lsub
      //   185: invokestatic 86	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   188: aastore
      //   189: aload 15
      //   191: iconst_1
      //   192: aload 12
      //   194: getfield 108	com/android/volley/VolleyLog$MarkerLog$Marker:thread	J
      //   197: invokestatic 86	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   200: aastore
      //   201: aload 15
      //   203: iconst_2
      //   204: aload 12
      //   206: getfield 112	com/android/volley/VolleyLog$MarkerLog$Marker:name	Ljava/lang/String;
      //   209: aastore
      //   210: ldc 114
      //   212: aload 15
      //   214: invokestatic 91	com/android/volley/VolleyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   217: lload 13
      //   219: lstore 8
      //   221: goto -79 -> 142
      //   224: astore_2
      //   225: aload_0
      //   226: monitorexit
      //   227: aload_2
      //   228: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	229	0	this	MarkerLog
      //   0	229	1	paramString	String
      //   224	4	2	localObject	Object
      //   16	2	3	i	int
      //   50	29	4	l1	long
      //   22	92	6	l2	long
      //   102	118	8	l3	long
      //   108	19	10	arrayOfObject1	Object[]
      //   140	13	11	localIterator	java.util.Iterator
      //   162	43	12	localMarker	Marker
      //   169	49	13	l4	long
      //   175	38	15	arrayOfObject2	Object[]
      // Exception table:
      //   from	to	target	type
      //   2	17	224	finally
      //   34	83	224	finally
      //   86	142	224	finally
      //   142	217	224	finally
    }
    
    private static final class Marker
    {
      public final String name;
      public final long thread;
      public final long time;
      
      public Marker(String paramString, long paramLong1, long paramLong2)
      {
        this.name = paramString;
        this.thread = paramLong1;
        this.time = paramLong2;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.VolleyLog
 * JD-Core Version:    0.7.0.1
 */