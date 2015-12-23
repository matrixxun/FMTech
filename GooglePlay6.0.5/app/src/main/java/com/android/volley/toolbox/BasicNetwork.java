package com.android.volley.toolbox;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

public final class BasicNetwork
  implements Network
{
  protected static final boolean DEBUG = VolleyLog.DEBUG;
  private static int DEFAULT_POOL_SIZE = 4096;
  private static int SLOW_REQUEST_THRESHOLD_MS = 3000;
  protected final HttpStack mHttpStack;
  protected final ByteArrayPool mPool;
  
  public BasicNetwork(HttpStack paramHttpStack)
  {
    this(paramHttpStack, new ByteArrayPool(DEFAULT_POOL_SIZE));
  }
  
  public BasicNetwork(HttpStack paramHttpStack, ByteArrayPool paramByteArrayPool)
  {
    this.mHttpStack = paramHttpStack;
    this.mPool = paramByteArrayPool;
  }
  
  private static void attemptRetryOnException(String paramString, Request<?> paramRequest, VolleyError paramVolleyError)
    throws VolleyError
  {
    RetryPolicy localRetryPolicy = paramRequest.mRetryPolicy;
    int i = paramRequest.getTimeoutMs();
    try
    {
      localRetryPolicy.retry(paramVolleyError);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramString;
      arrayOfObject2[1] = Integer.valueOf(i);
      paramRequest.addMarker(String.format("%s-retry [timeout=%s]", arrayOfObject2));
      return;
    }
    catch (VolleyError localVolleyError)
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = paramString;
      arrayOfObject1[1] = Integer.valueOf(i);
      paramRequest.addMarker(String.format("%s-timeout-giveup [timeout=%s]", arrayOfObject1));
      throw localVolleyError;
    }
  }
  
  private static Map<String, String> convertHeaders(Header[] paramArrayOfHeader)
  {
    TreeMap localTreeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    for (int i = 0; i < paramArrayOfHeader.length; i++) {
      localTreeMap.put(paramArrayOfHeader[i].getName(), paramArrayOfHeader[i].getValue());
    }
    return localTreeMap;
  }
  
  private byte[] entityToBytes(HttpEntity paramHttpEntity)
    throws IOException, ServerError
  {
    PoolingByteArrayOutputStream localPoolingByteArrayOutputStream = new PoolingByteArrayOutputStream(this.mPool, (int)paramHttpEntity.getContentLength());
    byte[] arrayOfByte1 = null;
    InputStream localInputStream;
    try
    {
      localInputStream = paramHttpEntity.getContent();
      arrayOfByte1 = null;
      if (localInputStream == null) {
        throw new ServerError();
      }
    }
    finally {}
    try
    {
      paramHttpEntity.consumeContent();
      this.mPool.returnBuf(arrayOfByte1);
      localPoolingByteArrayOutputStream.close();
      throw localObject;
      arrayOfByte1 = this.mPool.getBuf(1024);
      for (;;)
      {
        int i = localInputStream.read(arrayOfByte1);
        if (i == -1) {
          break;
        }
        localPoolingByteArrayOutputStream.write(arrayOfByte1, 0, i);
      }
      byte[] arrayOfByte2 = localPoolingByteArrayOutputStream.toByteArray();
      try
      {
        paramHttpEntity.consumeContent();
        this.mPool.returnBuf(arrayOfByte1);
        localPoolingByteArrayOutputStream.close();
        return arrayOfByte2;
      }
      catch (IOException localIOException2)
      {
        for (;;)
        {
          VolleyLog.v("Error occured when calling consumingContent", new Object[0]);
        }
      }
    }
    catch (IOException localIOException1)
    {
      for (;;)
      {
        VolleyLog.v("Error occured when calling consumingContent", new Object[0]);
      }
    }
  }
  
  /* Error */
  public final com.android.volley.NetworkResponse performRequest(Request<?> paramRequest)
    throws VolleyError
  {
    // Byte code:
    //   0: invokestatic 177	android/os/SystemClock:elapsedRealtime	()J
    //   3: lstore_2
    //   4: aconst_null
    //   5: astore 4
    //   7: invokestatic 183	java/util/Collections:emptyMap	()Ljava/util/Map;
    //   10: astore 5
    //   12: new 185	java/util/HashMap
    //   15: dup
    //   16: invokespecial 186	java/util/HashMap:<init>	()V
    //   19: astore 6
    //   21: aload_1
    //   22: getfield 190	com/android/volley/Request:mCacheEntry	Lcom/android/volley/Cache$Entry;
    //   25: astore 15
    //   27: aconst_null
    //   28: astore 4
    //   30: aload 15
    //   32: ifnull +68 -> 100
    //   35: aload 15
    //   37: getfield 196	com/android/volley/Cache$Entry:etag	Ljava/lang/String;
    //   40: ifnull +18 -> 58
    //   43: aload 6
    //   45: ldc 198
    //   47: aload 15
    //   49: getfield 196	com/android/volley/Cache$Entry:etag	Ljava/lang/String;
    //   52: invokeinterface 108 3 0
    //   57: pop
    //   58: aload 15
    //   60: getfield 202	com/android/volley/Cache$Entry:lastModified	J
    //   63: lconst_0
    //   64: lcmp
    //   65: istore 16
    //   67: aconst_null
    //   68: astore 4
    //   70: iload 16
    //   72: ifle +28 -> 100
    //   75: aload 6
    //   77: ldc 204
    //   79: new 206	java/util/Date
    //   82: dup
    //   83: aload 15
    //   85: getfield 202	com/android/volley/Cache$Entry:lastModified	J
    //   88: invokespecial 209	java/util/Date:<init>	(J)V
    //   91: invokestatic 215	org/apache/http/impl/cookie/DateUtils:formatDate	(Ljava/util/Date;)Ljava/lang/String;
    //   94: invokeinterface 108 3 0
    //   99: pop
    //   100: aload_0
    //   101: getfield 40	com/android/volley/toolbox/BasicNetwork:mHttpStack	Lcom/android/volley/toolbox/HttpStack;
    //   104: aload_1
    //   105: aload 6
    //   107: invokeinterface 220 3 0
    //   112: astore 4
    //   114: aload 4
    //   116: invokeinterface 226 1 0
    //   121: astore 18
    //   123: aload 18
    //   125: invokeinterface 231 1 0
    //   130: istore 19
    //   132: aload 4
    //   134: invokeinterface 235 1 0
    //   139: invokestatic 237	com/android/volley/toolbox/BasicNetwork:convertHeaders	([Lorg/apache/http/Header;)Ljava/util/Map;
    //   142: astore 5
    //   144: iload 19
    //   146: sipush 304
    //   149: if_icmpne +73 -> 222
    //   152: aload_1
    //   153: getfield 190	com/android/volley/Request:mCacheEntry	Lcom/android/volley/Cache$Entry;
    //   156: astore 20
    //   158: aload 20
    //   160: ifnonnull +23 -> 183
    //   163: new 239	com/android/volley/NetworkResponse
    //   166: dup
    //   167: sipush 304
    //   170: aconst_null
    //   171: aload 5
    //   173: iconst_1
    //   174: invokestatic 177	android/os/SystemClock:elapsedRealtime	()J
    //   177: lload_2
    //   178: lsub
    //   179: invokespecial 242	com/android/volley/NetworkResponse:<init>	(I[BLjava/util/Map;ZJ)V
    //   182: areturn
    //   183: aload 20
    //   185: getfield 246	com/android/volley/Cache$Entry:responseHeaders	Ljava/util/Map;
    //   188: aload 5
    //   190: invokeinterface 250 2 0
    //   195: new 239	com/android/volley/NetworkResponse
    //   198: dup
    //   199: sipush 304
    //   202: aload 20
    //   204: getfield 254	com/android/volley/Cache$Entry:data	[B
    //   207: aload 20
    //   209: getfield 246	com/android/volley/Cache$Entry:responseHeaders	Ljava/util/Map;
    //   212: iconst_1
    //   213: invokestatic 177	android/os/SystemClock:elapsedRealtime	()J
    //   216: lload_2
    //   217: lsub
    //   218: invokespecial 242	com/android/volley/NetworkResponse:<init>	(I[BLjava/util/Map;ZJ)V
    //   221: areturn
    //   222: aload 4
    //   224: invokeinterface 258 1 0
    //   229: ifnull +150 -> 379
    //   232: aload_0
    //   233: aload 4
    //   235: invokeinterface 258 1 0
    //   240: invokespecial 260	com/android/volley/toolbox/BasicNetwork:entityToBytes	(Lorg/apache/http/HttpEntity;)[B
    //   243: astore 28
    //   245: aload 28
    //   247: astore 10
    //   249: invokestatic 177	android/os/SystemClock:elapsedRealtime	()J
    //   252: lload_2
    //   253: lsub
    //   254: lstore 23
    //   256: getstatic 22	com/android/volley/toolbox/BasicNetwork:DEBUG	Z
    //   259: ifne +13 -> 272
    //   262: lload 23
    //   264: getstatic 24	com/android/volley/toolbox/BasicNetwork:SLOW_REQUEST_THRESHOLD_MS	I
    //   267: i2l
    //   268: lcmp
    //   269: ifle +366 -> 635
    //   272: iconst_5
    //   273: anewarray 4	java/lang/Object
    //   276: astore 25
    //   278: aload 25
    //   280: iconst_0
    //   281: aload_1
    //   282: aastore
    //   283: aload 25
    //   285: iconst_1
    //   286: lload 23
    //   288: invokestatic 265	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   291: aastore
    //   292: aload 10
    //   294: ifnull +93 -> 387
    //   297: aload 10
    //   299: arraylength
    //   300: invokestatic 68	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   303: astore 26
    //   305: aload 25
    //   307: iconst_2
    //   308: aload 26
    //   310: aastore
    //   311: aload 25
    //   313: iconst_3
    //   314: aload 18
    //   316: invokeinterface 231 1 0
    //   321: invokestatic 68	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   324: aastore
    //   325: aload 25
    //   327: iconst_4
    //   328: aload_1
    //   329: getfield 52	com/android/volley/Request:mRetryPolicy	Lcom/android/volley/RetryPolicy;
    //   332: invokeinterface 268 1 0
    //   337: invokestatic 68	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   340: aastore
    //   341: ldc_w 270
    //   344: aload 25
    //   346: invokestatic 273	com/android/volley/VolleyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   349: goto +286 -> 635
    //   352: new 112	java/io/IOException
    //   355: dup
    //   356: invokespecial 274	java/io/IOException:<init>	()V
    //   359: athrow
    //   360: astore 22
    //   362: ldc_w 276
    //   365: aload_1
    //   366: new 278	com/android/volley/TimeoutError
    //   369: dup
    //   370: invokespecial 279	com/android/volley/TimeoutError:<init>	()V
    //   373: invokestatic 281	com/android/volley/toolbox/BasicNetwork:attemptRetryOnException	(Ljava/lang/String;Lcom/android/volley/Request;Lcom/android/volley/VolleyError;)V
    //   376: goto -372 -> 4
    //   379: iconst_0
    //   380: newarray byte
    //   382: astore 10
    //   384: goto -135 -> 249
    //   387: ldc_w 283
    //   390: astore 26
    //   392: goto -87 -> 305
    //   395: new 239	com/android/volley/NetworkResponse
    //   398: dup
    //   399: iload 19
    //   401: aload 10
    //   403: aload 5
    //   405: iconst_0
    //   406: invokestatic 177	android/os/SystemClock:elapsedRealtime	()J
    //   409: lload_2
    //   410: lsub
    //   411: invokespecial 242	com/android/volley/NetworkResponse:<init>	(I[BLjava/util/Map;ZJ)V
    //   414: astore 27
    //   416: aload 27
    //   418: areturn
    //   419: astore 21
    //   421: ldc_w 285
    //   424: aload_1
    //   425: new 278	com/android/volley/TimeoutError
    //   428: dup
    //   429: invokespecial 279	com/android/volley/TimeoutError:<init>	()V
    //   432: invokestatic 281	com/android/volley/toolbox/BasicNetwork:attemptRetryOnException	(Ljava/lang/String;Lcom/android/volley/Request;Lcom/android/volley/VolleyError;)V
    //   435: goto -431 -> 4
    //   438: astore 14
    //   440: new 287	java/lang/RuntimeException
    //   443: dup
    //   444: new 289	java/lang/StringBuilder
    //   447: dup
    //   448: ldc_w 291
    //   451: invokespecial 293	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   454: aload_1
    //   455: invokevirtual 296	com/android/volley/Request:getUrl	()Ljava/lang/String;
    //   458: invokevirtual 300	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   461: invokevirtual 303	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   464: aload 14
    //   466: invokespecial 306	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   469: athrow
    //   470: astore 9
    //   472: aconst_null
    //   473: astore 10
    //   475: aload 4
    //   477: ifnull +109 -> 586
    //   480: aload 4
    //   482: invokeinterface 226 1 0
    //   487: invokeinterface 231 1 0
    //   492: istore 11
    //   494: iconst_2
    //   495: anewarray 4	java/lang/Object
    //   498: astore 12
    //   500: aload 12
    //   502: iconst_0
    //   503: iload 11
    //   505: invokestatic 68	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   508: aastore
    //   509: aload 12
    //   511: iconst_1
    //   512: aload_1
    //   513: invokevirtual 296	com/android/volley/Request:getUrl	()Ljava/lang/String;
    //   516: aastore
    //   517: ldc_w 308
    //   520: aload 12
    //   522: invokestatic 311	com/android/volley/VolleyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   525: aload 10
    //   527: ifnull +79 -> 606
    //   530: new 239	com/android/volley/NetworkResponse
    //   533: dup
    //   534: iload 11
    //   536: aload 10
    //   538: aload 5
    //   540: iconst_0
    //   541: invokestatic 177	android/os/SystemClock:elapsedRealtime	()J
    //   544: lload_2
    //   545: lsub
    //   546: invokespecial 242	com/android/volley/NetworkResponse:<init>	(I[BLjava/util/Map;ZJ)V
    //   549: astore 13
    //   551: iload 11
    //   553: sipush 401
    //   556: if_icmpeq +11 -> 567
    //   559: iload 11
    //   561: sipush 403
    //   564: if_icmpne +32 -> 596
    //   567: ldc_w 313
    //   570: aload_1
    //   571: new 315	com/android/volley/AuthFailureError
    //   574: dup
    //   575: aload 13
    //   577: invokespecial 318	com/android/volley/AuthFailureError:<init>	(Lcom/android/volley/NetworkResponse;)V
    //   580: invokestatic 281	com/android/volley/toolbox/BasicNetwork:attemptRetryOnException	(Ljava/lang/String;Lcom/android/volley/Request;Lcom/android/volley/VolleyError;)V
    //   583: goto -579 -> 4
    //   586: new 320	com/android/volley/NoConnectionError
    //   589: dup
    //   590: aload 9
    //   592: invokespecial 323	com/android/volley/NoConnectionError:<init>	(Ljava/lang/Throwable;)V
    //   595: athrow
    //   596: new 114	com/android/volley/ServerError
    //   599: dup
    //   600: aload 13
    //   602: invokespecial 324	com/android/volley/ServerError:<init>	(Lcom/android/volley/NetworkResponse;)V
    //   605: athrow
    //   606: new 326	com/android/volley/NetworkError
    //   609: dup
    //   610: iconst_0
    //   611: invokespecial 329	com/android/volley/NetworkError:<init>	(B)V
    //   614: athrow
    //   615: astore 9
    //   617: goto -142 -> 475
    //   620: astore 14
    //   622: goto -182 -> 440
    //   625: astore 8
    //   627: goto -206 -> 421
    //   630: astore 7
    //   632: goto -270 -> 362
    //   635: iload 19
    //   637: sipush 200
    //   640: if_icmplt -288 -> 352
    //   643: iload 19
    //   645: sipush 299
    //   648: if_icmple -253 -> 395
    //   651: goto -299 -> 352
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	654	0	this	BasicNetwork
    //   0	654	1	paramRequest	Request<?>
    //   3	542	2	l1	long
    //   5	476	4	localHttpResponse	org.apache.http.HttpResponse
    //   10	529	5	localMap	Map
    //   19	87	6	localHashMap	java.util.HashMap
    //   630	1	7	localSocketTimeoutException1	java.net.SocketTimeoutException
    //   625	1	8	localConnectTimeoutException1	org.apache.http.conn.ConnectTimeoutException
    //   470	121	9	localIOException1	IOException
    //   615	1	9	localIOException2	IOException
    //   247	290	10	arrayOfByte1	byte[]
    //   492	73	11	i	int
    //   498	23	12	arrayOfObject1	Object[]
    //   549	52	13	localNetworkResponse1	com.android.volley.NetworkResponse
    //   438	27	14	localMalformedURLException1	java.net.MalformedURLException
    //   620	1	14	localMalformedURLException2	java.net.MalformedURLException
    //   25	59	15	localEntry1	com.android.volley.Cache.Entry
    //   65	6	16	bool	boolean
    //   121	194	18	localStatusLine	org.apache.http.StatusLine
    //   130	519	19	j	int
    //   156	52	20	localEntry2	com.android.volley.Cache.Entry
    //   419	1	21	localConnectTimeoutException2	org.apache.http.conn.ConnectTimeoutException
    //   360	1	22	localSocketTimeoutException2	java.net.SocketTimeoutException
    //   254	33	23	l2	long
    //   276	69	25	arrayOfObject2	Object[]
    //   303	88	26	localObject	Object
    //   414	3	27	localNetworkResponse2	com.android.volley.NetworkResponse
    //   243	3	28	arrayOfByte2	byte[]
    // Exception table:
    //   from	to	target	type
    //   249	272	360	java/net/SocketTimeoutException
    //   272	292	360	java/net/SocketTimeoutException
    //   297	305	360	java/net/SocketTimeoutException
    //   305	349	360	java/net/SocketTimeoutException
    //   352	360	360	java/net/SocketTimeoutException
    //   395	416	360	java/net/SocketTimeoutException
    //   249	272	419	org/apache/http/conn/ConnectTimeoutException
    //   272	292	419	org/apache/http/conn/ConnectTimeoutException
    //   297	305	419	org/apache/http/conn/ConnectTimeoutException
    //   305	349	419	org/apache/http/conn/ConnectTimeoutException
    //   352	360	419	org/apache/http/conn/ConnectTimeoutException
    //   395	416	419	org/apache/http/conn/ConnectTimeoutException
    //   12	27	438	java/net/MalformedURLException
    //   35	58	438	java/net/MalformedURLException
    //   58	67	438	java/net/MalformedURLException
    //   75	100	438	java/net/MalformedURLException
    //   100	144	438	java/net/MalformedURLException
    //   152	158	438	java/net/MalformedURLException
    //   163	183	438	java/net/MalformedURLException
    //   183	222	438	java/net/MalformedURLException
    //   222	245	438	java/net/MalformedURLException
    //   379	384	438	java/net/MalformedURLException
    //   12	27	470	java/io/IOException
    //   35	58	470	java/io/IOException
    //   58	67	470	java/io/IOException
    //   75	100	470	java/io/IOException
    //   100	144	470	java/io/IOException
    //   152	158	470	java/io/IOException
    //   163	183	470	java/io/IOException
    //   183	222	470	java/io/IOException
    //   222	245	470	java/io/IOException
    //   379	384	470	java/io/IOException
    //   249	272	615	java/io/IOException
    //   272	292	615	java/io/IOException
    //   297	305	615	java/io/IOException
    //   305	349	615	java/io/IOException
    //   352	360	615	java/io/IOException
    //   395	416	615	java/io/IOException
    //   249	272	620	java/net/MalformedURLException
    //   272	292	620	java/net/MalformedURLException
    //   297	305	620	java/net/MalformedURLException
    //   305	349	620	java/net/MalformedURLException
    //   352	360	620	java/net/MalformedURLException
    //   395	416	620	java/net/MalformedURLException
    //   12	27	625	org/apache/http/conn/ConnectTimeoutException
    //   35	58	625	org/apache/http/conn/ConnectTimeoutException
    //   58	67	625	org/apache/http/conn/ConnectTimeoutException
    //   75	100	625	org/apache/http/conn/ConnectTimeoutException
    //   100	144	625	org/apache/http/conn/ConnectTimeoutException
    //   152	158	625	org/apache/http/conn/ConnectTimeoutException
    //   163	183	625	org/apache/http/conn/ConnectTimeoutException
    //   183	222	625	org/apache/http/conn/ConnectTimeoutException
    //   222	245	625	org/apache/http/conn/ConnectTimeoutException
    //   379	384	625	org/apache/http/conn/ConnectTimeoutException
    //   12	27	630	java/net/SocketTimeoutException
    //   35	58	630	java/net/SocketTimeoutException
    //   58	67	630	java/net/SocketTimeoutException
    //   75	100	630	java/net/SocketTimeoutException
    //   100	144	630	java/net/SocketTimeoutException
    //   152	158	630	java/net/SocketTimeoutException
    //   163	183	630	java/net/SocketTimeoutException
    //   183	222	630	java/net/SocketTimeoutException
    //   222	245	630	java/net/SocketTimeoutException
    //   379	384	630	java/net/SocketTimeoutException
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.BasicNetwork
 * JD-Core Version:    0.7.0.1
 */