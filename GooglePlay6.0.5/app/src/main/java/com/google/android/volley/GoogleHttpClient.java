package com.google.android.volley;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.google.android.common.http.UrlRules;
import com.google.android.common.http.UrlRules.Rule;
import com.google.android.pseudonymous_http.PseudonymousCookieSource;
import com.google.android.pseudonymous_http.PseudonymousCookieSource.Helper;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public final class GoogleHttpClient
  implements HttpClient
{
  private final String mAppName;
  final AndroidHttpClient mClient;
  private final ThreadLocal<Boolean> mConnectionAllocated = new ThreadLocal();
  private final CookieSourceApplier mCookieSourceApplier;
  public final ContentResolver mResolver;
  
  public GoogleHttpClient(Context paramContext, String paramString)
  {
    this(paramContext, paramString, true);
  }
  
  private GoogleHttpClient(Context paramContext, String paramString, boolean paramBoolean)
  {
    String str1 = paramString + " (" + Build.DEVICE + " " + Build.ID + ")";
    this.mClient = AndroidHttpClient.newInstance(str1 + "; gzip", paramContext);
    this.mCookieSourceApplier = new CookieSourceApplier(this.mClient, null, (byte)0);
    this.mResolver = paramContext.getContentResolver();
    this.mAppName = paramString;
    SchemeRegistry localSchemeRegistry = getConnectionManager().getSchemeRegistry();
    Iterator localIterator = localSchemeRegistry.getSchemeNames().iterator();
    if (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      Scheme localScheme = localSchemeRegistry.unregister(str2);
      SocketFactory localSocketFactory = localScheme.getSocketFactory();
      if ((localSocketFactory instanceof LayeredSocketFactory)) {}
      for (Object localObject = new WrappedLayeredSocketFactory((LayeredSocketFactory)localSocketFactory, (byte)0);; localObject = new WrappedSocketFactory(localSocketFactory, (byte)0))
      {
        localSchemeRegistry.register(new Scheme(str2, (SocketFactory)localObject, localScheme.getDefaultPort()));
        break;
      }
    }
  }
  
  /* Error */
  private HttpResponse executeWithoutRewriting(HttpUriRequest paramHttpUriRequest, HttpContext paramHttpContext)
    throws IOException
  {
    // Byte code:
    //   0: invokestatic 163	android/os/SystemClock:elapsedRealtime	()J
    //   3: lstore_3
    //   4: aload_0
    //   5: getfield 30	com/google/android/volley/GoogleHttpClient:mConnectionAllocated	Ljava/lang/ThreadLocal;
    //   8: aconst_null
    //   9: invokevirtual 167	java/lang/ThreadLocal:set	(Ljava/lang/Object;)V
    //   12: aload_0
    //   13: getfield 80	com/google/android/volley/GoogleHttpClient:mResolver	Landroid/content/ContentResolver;
    //   16: ldc 169
    //   18: iconst_0
    //   19: invokestatic 175	com/google/android/gsf/Gservices:getBoolean	(Landroid/content/ContentResolver;Ljava/lang/String;Z)Z
    //   22: ifeq +193 -> 215
    //   25: invokestatic 180	android/os/Process:myUid	()I
    //   28: istore 23
    //   30: iload 23
    //   32: invokestatic 186	android/net/TrafficStats:getUidTxBytes	(I)J
    //   35: lstore 24
    //   37: iload 23
    //   39: invokestatic 189	android/net/TrafficStats:getUidRxBytes	(I)J
    //   42: lstore 26
    //   44: aload_0
    //   45: getfield 72	com/google/android/volley/GoogleHttpClient:mCookieSourceApplier	Lcom/google/android/volley/GoogleHttpClient$CookieSourceApplier;
    //   48: aload_1
    //   49: invokestatic 193	com/google/android/volley/GoogleHttpClient$CookieSourceApplier:access$400$339301cd	(Lcom/google/android/volley/GoogleHttpClient$CookieSourceApplier;Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   52: astore 14
    //   54: aload 14
    //   56: ifnonnull +147 -> 203
    //   59: aconst_null
    //   60: astore 28
    //   62: aload 28
    //   64: ifnull +48 -> 112
    //   67: invokestatic 163	android/os/SystemClock:elapsedRealtime	()J
    //   70: lstore 29
    //   72: lload 29
    //   74: lload_3
    //   75: lsub
    //   76: lstore 31
    //   78: new 195	com/google/android/common/http/NetworkStatsEntity
    //   81: dup
    //   82: aload 28
    //   84: aload_0
    //   85: getfield 82	com/google/android/volley/GoogleHttpClient:mAppName	Ljava/lang/String;
    //   88: iload 23
    //   90: lload 24
    //   92: lload 26
    //   94: lload 31
    //   96: lload 29
    //   98: invokespecial 198	com/google/android/common/http/NetworkStatsEntity:<init>	(Lorg/apache/http/HttpEntity;Ljava/lang/String;IJJJJ)V
    //   101: astore 33
    //   103: aload 14
    //   105: aload 33
    //   107: invokeinterface 204 2 0
    //   112: aload 14
    //   114: invokeinterface 208 1 0
    //   119: invokeinterface 213 1 0
    //   124: istore 15
    //   126: invokestatic 163	android/os/SystemClock:elapsedRealtime	()J
    //   129: lload_3
    //   130: lsub
    //   131: lstore 18
    //   133: aload_0
    //   134: getfield 30	com/google/android/volley/GoogleHttpClient:mConnectionAllocated	Ljava/lang/ThreadLocal;
    //   137: invokevirtual 216	java/lang/ThreadLocal:get	()Ljava/lang/Object;
    //   140: ifnonnull +92 -> 232
    //   143: iload 15
    //   145: iflt +87 -> 232
    //   148: iconst_1
    //   149: istore 20
    //   151: iconst_4
    //   152: anewarray 4	java/lang/Object
    //   155: astore 21
    //   157: aload 21
    //   159: iconst_0
    //   160: lload 18
    //   162: invokestatic 222	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   165: aastore
    //   166: aload 21
    //   168: iconst_1
    //   169: iload 15
    //   171: invokestatic 227	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   174: aastore
    //   175: aload 21
    //   177: iconst_2
    //   178: aload_0
    //   179: getfield 82	com/google/android/volley/GoogleHttpClient:mAppName	Ljava/lang/String;
    //   182: aastore
    //   183: aload 21
    //   185: iconst_3
    //   186: iload 20
    //   188: invokestatic 227	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   191: aastore
    //   192: ldc 228
    //   194: aload 21
    //   196: invokestatic 234	android/util/EventLog:writeEvent	(I[Ljava/lang/Object;)I
    //   199: pop
    //   200: aload 14
    //   202: areturn
    //   203: aload 14
    //   205: invokeinterface 238 1 0
    //   210: astore 28
    //   212: goto -150 -> 62
    //   215: aload_0
    //   216: getfield 72	com/google/android/volley/GoogleHttpClient:mCookieSourceApplier	Lcom/google/android/volley/GoogleHttpClient$CookieSourceApplier;
    //   219: aload_1
    //   220: invokestatic 193	com/google/android/volley/GoogleHttpClient$CookieSourceApplier:access$400$339301cd	(Lcom/google/android/volley/GoogleHttpClient$CookieSourceApplier;Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   223: astore 13
    //   225: aload 13
    //   227: astore 14
    //   229: goto -117 -> 112
    //   232: iconst_0
    //   233: istore 20
    //   235: goto -84 -> 151
    //   238: astore 16
    //   240: ldc 240
    //   242: ldc 242
    //   244: aload 16
    //   246: invokestatic 248	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   249: pop
    //   250: aload 14
    //   252: areturn
    //   253: astore 5
    //   255: invokestatic 163	android/os/SystemClock:elapsedRealtime	()J
    //   258: lload_3
    //   259: lsub
    //   260: lstore 8
    //   262: aload_0
    //   263: getfield 30	com/google/android/volley/GoogleHttpClient:mConnectionAllocated	Ljava/lang/ThreadLocal;
    //   266: invokevirtual 216	java/lang/ThreadLocal:get	()Ljava/lang/Object;
    //   269: ifnonnull +61 -> 330
    //   272: iconst_m1
    //   273: iflt +57 -> 330
    //   276: iconst_1
    //   277: istore 10
    //   279: iconst_4
    //   280: anewarray 4	java/lang/Object
    //   283: astore 11
    //   285: aload 11
    //   287: iconst_0
    //   288: lload 8
    //   290: invokestatic 222	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   293: aastore
    //   294: aload 11
    //   296: iconst_1
    //   297: iconst_m1
    //   298: invokestatic 227	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   301: aastore
    //   302: aload 11
    //   304: iconst_2
    //   305: aload_0
    //   306: getfield 82	com/google/android/volley/GoogleHttpClient:mAppName	Ljava/lang/String;
    //   309: aastore
    //   310: aload 11
    //   312: iconst_3
    //   313: iload 10
    //   315: invokestatic 227	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   318: aastore
    //   319: ldc 228
    //   321: aload 11
    //   323: invokestatic 234	android/util/EventLog:writeEvent	(I[Ljava/lang/Object;)I
    //   326: pop
    //   327: aload 5
    //   329: athrow
    //   330: iconst_0
    //   331: istore 10
    //   333: goto -54 -> 279
    //   336: astore 6
    //   338: ldc 240
    //   340: ldc 242
    //   342: aload 6
    //   344: invokestatic 248	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   347: pop
    //   348: goto -21 -> 327
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	351	0	this	GoogleHttpClient
    //   0	351	1	paramHttpUriRequest	HttpUriRequest
    //   0	351	2	paramHttpContext	HttpContext
    //   3	256	3	l1	long
    //   253	75	5	localObject1	Object
    //   336	7	6	localException1	java.lang.Exception
    //   260	29	8	l2	long
    //   277	55	10	i	int
    //   283	39	11	arrayOfObject1	Object[]
    //   223	3	13	localHttpResponse	HttpResponse
    //   52	199	14	localObject2	Object
    //   124	46	15	j	int
    //   238	7	16	localException2	java.lang.Exception
    //   131	30	18	l3	long
    //   149	85	20	k	int
    //   155	40	21	arrayOfObject2	Object[]
    //   28	61	23	m	int
    //   35	56	24	l4	long
    //   42	51	26	l5	long
    //   60	151	28	localHttpEntity	org.apache.http.HttpEntity
    //   70	27	29	l6	long
    //   76	19	31	l7	long
    //   101	5	33	localNetworkStatsEntity	com.google.android.common.http.NetworkStatsEntity
    // Exception table:
    //   from	to	target	type
    //   126	143	238	java/lang/Exception
    //   151	200	238	java/lang/Exception
    //   4	54	253	finally
    //   67	72	253	finally
    //   78	112	253	finally
    //   112	126	253	finally
    //   203	212	253	finally
    //   215	225	253	finally
    //   255	272	336	java/lang/Exception
    //   279	327	336	java/lang/Exception
  }
  
  /* Error */
  private static RequestWrapper wrapRequest(HttpRequest paramHttpRequest)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: instanceof 254
    //   4: ifeq +21 -> 25
    //   7: new 256	org/apache/http/impl/client/EntityEnclosingRequestWrapper
    //   10: dup
    //   11: aload_0
    //   12: checkcast 254	org/apache/http/HttpEntityEnclosingRequest
    //   15: invokespecial 259	org/apache/http/impl/client/EntityEnclosingRequestWrapper:<init>	(Lorg/apache/http/HttpEntityEnclosingRequest;)V
    //   18: astore_2
    //   19: aload_2
    //   20: invokevirtual 264	org/apache/http/impl/client/RequestWrapper:resetHeaders	()V
    //   23: aload_2
    //   24: areturn
    //   25: new 261	org/apache/http/impl/client/RequestWrapper
    //   28: dup
    //   29: aload_0
    //   30: invokespecial 267	org/apache/http/impl/client/RequestWrapper:<init>	(Lorg/apache/http/HttpRequest;)V
    //   33: astore_2
    //   34: goto -15 -> 19
    //   37: astore_1
    //   38: new 269	org/apache/http/client/ClientProtocolException
    //   41: dup
    //   42: aload_1
    //   43: invokespecial 272	org/apache/http/client/ClientProtocolException:<init>	(Ljava/lang/Throwable;)V
    //   46: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	47	0	paramHttpRequest	HttpRequest
    //   37	6	1	localProtocolException	org.apache.http.ProtocolException
    //   18	16	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	19	37	org/apache/http/ProtocolException
    //   19	23	37	org/apache/http/ProtocolException
    //   25	34	37	org/apache/http/ProtocolException
  }
  
  /* Error */
  private static RequestWrapper wrapRequest(HttpUriRequest paramHttpUriRequest)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: instanceof 254
    //   4: ifeq +21 -> 25
    //   7: new 256	org/apache/http/impl/client/EntityEnclosingRequestWrapper
    //   10: dup
    //   11: aload_0
    //   12: checkcast 254	org/apache/http/HttpEntityEnclosingRequest
    //   15: invokespecial 259	org/apache/http/impl/client/EntityEnclosingRequestWrapper:<init>	(Lorg/apache/http/HttpEntityEnclosingRequest;)V
    //   18: astore_2
    //   19: aload_2
    //   20: invokevirtual 264	org/apache/http/impl/client/RequestWrapper:resetHeaders	()V
    //   23: aload_2
    //   24: areturn
    //   25: new 261	org/apache/http/impl/client/RequestWrapper
    //   28: dup
    //   29: aload_0
    //   30: invokespecial 267	org/apache/http/impl/client/RequestWrapper:<init>	(Lorg/apache/http/HttpRequest;)V
    //   33: astore_2
    //   34: goto -15 -> 19
    //   37: astore_1
    //   38: new 269	org/apache/http/client/ClientProtocolException
    //   41: dup
    //   42: aload_1
    //   43: invokespecial 272	org/apache/http/client/ClientProtocolException:<init>	(Ljava/lang/Throwable;)V
    //   46: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	47	0	paramHttpUriRequest	HttpUriRequest
    //   37	6	1	localProtocolException	org.apache.http.ProtocolException
    //   18	16	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	19	37	org/apache/http/ProtocolException
    //   19	23	37	org/apache/http/ProtocolException
    //   25	34	37	org/apache/http/ProtocolException
  }
  
  public final <T> T execute(HttpHost paramHttpHost, HttpRequest paramHttpRequest, ResponseHandler<? extends T> paramResponseHandler)
    throws IOException, ClientProtocolException
  {
    CookieSourceApplier localCookieSourceApplier = this.mCookieSourceApplier;
    RequestWrapper localRequestWrapper = wrapRequest(paramHttpRequest);
    SetCookie localSetCookie = new SetCookie(localCookieSourceApplier.this$0, paramResponseHandler, localRequestWrapper, localCookieSourceApplier.mCookieSource, (byte)0);
    return localCookieSourceApplier.mClient.execute(paramHttpHost, PseudonymousCookieSource.Helper.setRequestCookie(localRequestWrapper, localCookieSourceApplier.mCookieSource), localSetCookie);
  }
  
  public final <T> T execute(HttpHost paramHttpHost, HttpRequest paramHttpRequest, ResponseHandler<? extends T> paramResponseHandler, HttpContext paramHttpContext)
    throws IOException, ClientProtocolException
  {
    CookieSourceApplier localCookieSourceApplier = this.mCookieSourceApplier;
    RequestWrapper localRequestWrapper = wrapRequest(paramHttpRequest);
    SetCookie localSetCookie = new SetCookie(localCookieSourceApplier.this$0, paramResponseHandler, localRequestWrapper, localCookieSourceApplier.mCookieSource, (byte)0);
    return localCookieSourceApplier.mClient.execute(paramHttpHost, PseudonymousCookieSource.Helper.setRequestCookie(localRequestWrapper, localCookieSourceApplier.mCookieSource), localSetCookie, paramHttpContext);
  }
  
  public final <T> T execute(HttpUriRequest paramHttpUriRequest, ResponseHandler<? extends T> paramResponseHandler)
    throws IOException, ClientProtocolException
  {
    CookieSourceApplier localCookieSourceApplier = this.mCookieSourceApplier;
    SetCookie localSetCookie = new SetCookie(localCookieSourceApplier.this$0, paramResponseHandler, paramHttpUriRequest, localCookieSourceApplier.mCookieSource, (byte)0);
    return localCookieSourceApplier.mClient.execute(PseudonymousCookieSource.Helper.setRequestCookie(paramHttpUriRequest, localCookieSourceApplier.mCookieSource), localSetCookie);
  }
  
  public final <T> T execute(HttpUriRequest paramHttpUriRequest, ResponseHandler<? extends T> paramResponseHandler, HttpContext paramHttpContext)
    throws IOException, ClientProtocolException
  {
    CookieSourceApplier localCookieSourceApplier = this.mCookieSourceApplier;
    SetCookie localSetCookie = new SetCookie(localCookieSourceApplier.this$0, paramResponseHandler, paramHttpUriRequest, localCookieSourceApplier.mCookieSource, (byte)0);
    return localCookieSourceApplier.mClient.execute(paramHttpUriRequest, localSetCookie, paramHttpContext);
  }
  
  public final HttpResponse execute(HttpHost paramHttpHost, HttpRequest paramHttpRequest)
    throws IOException
  {
    CookieSourceApplier localCookieSourceApplier = this.mCookieSourceApplier;
    return PseudonymousCookieSource.Helper.updateFromResponseCookie(wrapRequest(paramHttpRequest), localCookieSourceApplier.mClient.execute(paramHttpHost, PseudonymousCookieSource.Helper.setRequestCookie(wrapRequest(paramHttpRequest), localCookieSourceApplier.mCookieSource)), localCookieSourceApplier.mCookieSource);
  }
  
  public final HttpResponse execute(HttpHost paramHttpHost, HttpRequest paramHttpRequest, HttpContext paramHttpContext)
    throws IOException
  {
    CookieSourceApplier localCookieSourceApplier = this.mCookieSourceApplier;
    return PseudonymousCookieSource.Helper.updateFromResponseCookie(wrapRequest(paramHttpRequest), localCookieSourceApplier.mClient.execute(paramHttpHost, PseudonymousCookieSource.Helper.setRequestCookie(wrapRequest(paramHttpRequest), localCookieSourceApplier.mCookieSource), paramHttpContext), localCookieSourceApplier.mCookieSource);
  }
  
  public final HttpResponse execute(HttpUriRequest paramHttpUriRequest)
    throws IOException
  {
    return execute(paramHttpUriRequest, null);
  }
  
  public final HttpResponse execute(HttpUriRequest paramHttpUriRequest, HttpContext paramHttpContext)
    throws IOException
  {
    String str1 = paramHttpUriRequest.getURI().toString();
    UrlRules.Rule localRule = UrlRules.getRules(this.mResolver).matchRule(str1);
    String str2 = localRule.apply(str1);
    if (str2 == null)
    {
      Log.w("GoogleHttpClient", "Blocked by " + localRule.mName + ": " + str1);
      throw new BlockedRequestException(localRule);
    }
    if (str2 == str1) {
      return executeWithoutRewriting(paramHttpUriRequest, paramHttpContext);
    }
    try
    {
      URI localURI = new URI(str2);
      RequestWrapper localRequestWrapper = wrapRequest(paramHttpUriRequest);
      localRequestWrapper.setURI(localURI);
      if (Log.isLoggable("GoogleHttpClient", 3)) {
        Log.d("GoogleHttpClient", "Rule " + localRule.mName + ": " + str1 + " -> " + str2);
      }
      return executeWithoutRewriting(localRequestWrapper, paramHttpContext);
    }
    catch (URISyntaxException localURISyntaxException)
    {
      throw new RuntimeException("Bad URL from rule: " + localRule.mName, localURISyntaxException);
    }
  }
  
  public final ClientConnectionManager getConnectionManager()
  {
    return this.mClient.getConnectionManager();
  }
  
  public final HttpParams getParams()
  {
    return this.mClient.getParams();
  }
  
  public static final class BlockedRequestException
    extends IOException
  {
    BlockedRequestException(UrlRules.Rule paramRule)
    {
      super();
    }
  }
  
  private final class CookieSourceApplier
  {
    final AndroidHttpClient mClient;
    final PseudonymousCookieSource mCookieSource;
    
    private CookieSourceApplier(AndroidHttpClient paramAndroidHttpClient, PseudonymousCookieSource paramPseudonymousCookieSource)
    {
      this.mClient = paramAndroidHttpClient;
      this.mCookieSource = paramPseudonymousCookieSource;
    }
  }
  
  private final class SetCookie<T>
    implements ResponseHandler<T>
  {
    private final PseudonymousCookieSource mCookieSource;
    private final ResponseHandler<T> mHandler;
    private final HttpUriRequest mRequest;
    
    private SetCookie(HttpUriRequest paramHttpUriRequest, PseudonymousCookieSource paramPseudonymousCookieSource)
    {
      this.mHandler = paramHttpUriRequest;
      this.mRequest = paramPseudonymousCookieSource;
      Object localObject;
      this.mCookieSource = localObject;
    }
    
    public final T handleResponse(HttpResponse paramHttpResponse)
      throws ClientProtocolException, IOException
    {
      return this.mHandler.handleResponse(PseudonymousCookieSource.Helper.updateFromResponseCookie(this.mRequest, paramHttpResponse, this.mCookieSource));
    }
  }
  
  private final class WrappedLayeredSocketFactory
    extends GoogleHttpClient.WrappedSocketFactory
    implements LayeredSocketFactory
  {
    private LayeredSocketFactory mDelegate;
    
    private WrappedLayeredSocketFactory(LayeredSocketFactory paramLayeredSocketFactory)
    {
      super(paramLayeredSocketFactory, (byte)0);
      this.mDelegate = paramLayeredSocketFactory;
    }
    
    public final Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean)
      throws IOException
    {
      return this.mDelegate.createSocket(paramSocket, paramString, paramInt, paramBoolean);
    }
  }
  
  private class WrappedSocketFactory
    implements SocketFactory
  {
    private SocketFactory mDelegate;
    
    private WrappedSocketFactory(SocketFactory paramSocketFactory)
    {
      this.mDelegate = paramSocketFactory;
    }
    
    public final Socket connectSocket(Socket paramSocket, String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2, HttpParams paramHttpParams)
      throws IOException
    {
      GoogleHttpClient.this.mConnectionAllocated.set(Boolean.TRUE);
      return this.mDelegate.connectSocket(paramSocket, paramString, paramInt1, paramInetAddress, paramInt2, paramHttpParams);
    }
    
    public final Socket createSocket()
      throws IOException
    {
      return this.mDelegate.createSocket();
    }
    
    public final boolean isSecure(Socket paramSocket)
    {
      return this.mDelegate.isSecure(paramSocket);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.GoogleHttpClient
 * JD-Core Version:    0.7.0.1
 */