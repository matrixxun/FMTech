package com.android.volley;

import java.util.concurrent.BlockingQueue;

public final class NetworkDispatcher
  extends Thread
{
  private final Cache mCache;
  private final ResponseDelivery mDelivery;
  private final Network mNetwork;
  private final BlockingQueue<Request<?>> mQueue;
  volatile boolean mQuit = false;
  
  public NetworkDispatcher(BlockingQueue<Request<?>> paramBlockingQueue, Network paramNetwork, Cache paramCache, ResponseDelivery paramResponseDelivery)
  {
    this.mQueue = paramBlockingQueue;
    this.mNetwork = paramNetwork;
    this.mCache = paramCache;
    this.mDelivery = paramResponseDelivery;
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
    //   10: getfield 24	com/android/volley/NetworkDispatcher:mQueue	Ljava/util/concurrent/BlockingQueue;
    //   13: invokeinterface 55 1 0
    //   18: checkcast 57	com/android/volley/Request
    //   21: astore 4
    //   23: aload 4
    //   25: ldc 59
    //   27: invokevirtual 63	com/android/volley/Request:addMarker	(Ljava/lang/String;)V
    //   30: aload 4
    //   32: invokevirtual 67	com/android/volley/Request:isCanceled	()Z
    //   35: ifeq +59 -> 94
    //   38: aload 4
    //   40: ldc 69
    //   42: invokevirtual 72	com/android/volley/Request:finish	(Ljava/lang/String;)V
    //   45: goto -40 -> 5
    //   48: astore 8
    //   50: aload 8
    //   52: invokestatic 49	android/os/SystemClock:elapsedRealtime	()J
    //   55: lload_1
    //   56: lsub
    //   57: putfield 76	com/android/volley/VolleyError:networkTimeMs	J
    //   60: aload 4
    //   62: aload 8
    //   64: invokevirtual 80	com/android/volley/Request:parseNetworkError	(Lcom/android/volley/VolleyError;)Lcom/android/volley/VolleyError;
    //   67: astore 9
    //   69: aload_0
    //   70: getfield 30	com/android/volley/NetworkDispatcher:mDelivery	Lcom/android/volley/ResponseDelivery;
    //   73: aload 4
    //   75: aload 9
    //   77: invokeinterface 86 3 0
    //   82: goto -77 -> 5
    //   85: astore_3
    //   86: aload_0
    //   87: getfield 22	com/android/volley/NetworkDispatcher:mQuit	Z
    //   90: ifeq -85 -> 5
    //   93: return
    //   94: getstatic 92	android/os/Build$VERSION:SDK_INT	I
    //   97: bipush 14
    //   99: if_icmplt +11 -> 110
    //   102: aload 4
    //   104: getfield 95	com/android/volley/Request:mDefaultTrafficStatsTag	I
    //   107: invokestatic 100	android/net/TrafficStats:setThreadStatsTag	(I)V
    //   110: aload_0
    //   111: getfield 26	com/android/volley/NetworkDispatcher:mNetwork	Lcom/android/volley/Network;
    //   114: aload 4
    //   116: invokeinterface 106 2 0
    //   121: astore 10
    //   123: aload 4
    //   125: ldc 108
    //   127: invokevirtual 63	com/android/volley/Request:addMarker	(Ljava/lang/String;)V
    //   130: aload 10
    //   132: getfield 113	com/android/volley/NetworkResponse:notModified	Z
    //   135: ifeq +84 -> 219
    //   138: aload 4
    //   140: getfield 116	com/android/volley/Request:mResponseDelivered	Z
    //   143: ifeq +76 -> 219
    //   146: aload 4
    //   148: ldc 118
    //   150: invokevirtual 72	com/android/volley/Request:finish	(Ljava/lang/String;)V
    //   153: goto -148 -> 5
    //   156: astore 5
    //   158: iconst_1
    //   159: anewarray 120	java/lang/Object
    //   162: astore 6
    //   164: aload 6
    //   166: iconst_0
    //   167: aload 5
    //   169: invokevirtual 124	java/lang/Exception:toString	()Ljava/lang/String;
    //   172: aastore
    //   173: aload 5
    //   175: ldc 126
    //   177: aload 6
    //   179: invokestatic 132	com/android/volley/VolleyLog:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   182: new 35	com/android/volley/VolleyError
    //   185: dup
    //   186: aload 5
    //   188: invokespecial 135	com/android/volley/VolleyError:<init>	(Ljava/lang/Throwable;)V
    //   191: astore 7
    //   193: aload 7
    //   195: invokestatic 49	android/os/SystemClock:elapsedRealtime	()J
    //   198: lload_1
    //   199: lsub
    //   200: putfield 76	com/android/volley/VolleyError:networkTimeMs	J
    //   203: aload_0
    //   204: getfield 30	com/android/volley/NetworkDispatcher:mDelivery	Lcom/android/volley/ResponseDelivery;
    //   207: aload 4
    //   209: aload 7
    //   211: invokeinterface 86 3 0
    //   216: goto -211 -> 5
    //   219: aload 4
    //   221: aload 10
    //   223: invokevirtual 139	com/android/volley/Request:parseNetworkResponse	(Lcom/android/volley/NetworkResponse;)Lcom/android/volley/Response;
    //   226: astore 11
    //   228: aload 4
    //   230: ldc 141
    //   232: invokevirtual 63	com/android/volley/Request:addMarker	(Ljava/lang/String;)V
    //   235: aload 4
    //   237: getfield 144	com/android/volley/Request:mShouldCache	Z
    //   240: ifeq +37 -> 277
    //   243: aload 11
    //   245: getfield 150	com/android/volley/Response:cacheEntry	Lcom/android/volley/Cache$Entry;
    //   248: ifnull +29 -> 277
    //   251: aload_0
    //   252: getfield 28	com/android/volley/NetworkDispatcher:mCache	Lcom/android/volley/Cache;
    //   255: aload 4
    //   257: invokevirtual 153	com/android/volley/Request:getCacheKey	()Ljava/lang/String;
    //   260: aload 11
    //   262: getfield 150	com/android/volley/Response:cacheEntry	Lcom/android/volley/Cache$Entry;
    //   265: invokeinterface 159 3 0
    //   270: aload 4
    //   272: ldc 161
    //   274: invokevirtual 63	com/android/volley/Request:addMarker	(Ljava/lang/String;)V
    //   277: aload 4
    //   279: iconst_1
    //   280: putfield 116	com/android/volley/Request:mResponseDelivered	Z
    //   283: aload_0
    //   284: getfield 30	com/android/volley/NetworkDispatcher:mDelivery	Lcom/android/volley/ResponseDelivery;
    //   287: aload 4
    //   289: aload 11
    //   291: invokeinterface 165 3 0
    //   296: goto -291 -> 5
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	299	0	this	NetworkDispatcher
    //   8	191	1	l	long
    //   85	1	3	localInterruptedException	java.lang.InterruptedException
    //   21	267	4	localRequest	Request
    //   156	31	5	localException	java.lang.Exception
    //   162	16	6	arrayOfObject	java.lang.Object[]
    //   191	19	7	localVolleyError1	VolleyError
    //   48	15	8	localVolleyError2	VolleyError
    //   67	9	9	localVolleyError3	VolleyError
    //   121	101	10	localNetworkResponse	NetworkResponse
    //   226	64	11	localResponse	Response
    // Exception table:
    //   from	to	target	type
    //   23	45	48	com/android/volley/VolleyError
    //   94	110	48	com/android/volley/VolleyError
    //   110	153	48	com/android/volley/VolleyError
    //   219	277	48	com/android/volley/VolleyError
    //   277	296	48	com/android/volley/VolleyError
    //   9	23	85	java/lang/InterruptedException
    //   23	45	156	java/lang/Exception
    //   94	110	156	java/lang/Exception
    //   110	153	156	java/lang/Exception
    //   219	277	156	java/lang/Exception
    //   277	296	156	java/lang/Exception
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.NetworkDispatcher
 * JD-Core Version:    0.7.0.1
 */