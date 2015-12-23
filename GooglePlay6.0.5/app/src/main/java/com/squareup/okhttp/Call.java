package com.squareup.okhttp;

import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Network;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.AuthenticatorAdapter;
import com.squareup.okhttp.internal.http.CacheRequest;
import com.squareup.okhttp.internal.http.CacheStrategy;
import com.squareup.okhttp.internal.http.CacheStrategy.Factory;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpEngine.2;
import com.squareup.okhttp.internal.http.HttpEngine.NetworkInterceptorChain;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RealResponseBody;
import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.okhttp.internal.http.RouteSelector;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintStream;
import java.net.CookieHandler;
import java.net.Inet6Address;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

public final class Call
{
  volatile boolean canceled;
  final OkHttpClient client;
  HttpEngine engine;
  private boolean executed;
  Request originalRequest;
  
  protected Call(OkHttpClient paramOkHttpClient, Request paramRequest)
  {
    OkHttpClient localOkHttpClient = new OkHttpClient(paramOkHttpClient);
    if (localOkHttpClient.proxySelector == null) {
      localOkHttpClient.proxySelector = ProxySelector.getDefault();
    }
    if (localOkHttpClient.cookieHandler == null) {
      localOkHttpClient.cookieHandler = CookieHandler.getDefault();
    }
    if (localOkHttpClient.socketFactory == null) {
      localOkHttpClient.socketFactory = SocketFactory.getDefault();
    }
    if (localOkHttpClient.sslSocketFactory == null) {
      localOkHttpClient.sslSocketFactory = paramOkHttpClient.getDefaultSSLSocketFactory();
    }
    if (localOkHttpClient.hostnameVerifier == null) {
      localOkHttpClient.hostnameVerifier = OkHostnameVerifier.INSTANCE;
    }
    if (localOkHttpClient.certificatePinner == null) {
      localOkHttpClient.certificatePinner = CertificatePinner.DEFAULT;
    }
    if (localOkHttpClient.authenticator == null) {
      localOkHttpClient.authenticator = AuthenticatorAdapter.INSTANCE;
    }
    if (localOkHttpClient.connectionPool == null) {
      localOkHttpClient.connectionPool = ConnectionPool.getDefault();
    }
    if (localOkHttpClient.protocols == null) {
      localOkHttpClient.protocols = OkHttpClient.DEFAULT_PROTOCOLS;
    }
    if (localOkHttpClient.connectionSpecs == null) {
      localOkHttpClient.connectionSpecs = OkHttpClient.DEFAULT_CONNECTION_SPECS;
    }
    if (localOkHttpClient.network == null) {
      localOkHttpClient.network = Network.DEFAULT;
    }
    this.client = localOkHttpClient;
    this.originalRequest = paramRequest;
  }
  
  public final Response execute()
    throws IOException
  {
    try
    {
      if (this.executed) {
        throw new IllegalStateException("Already Executed");
      }
    }
    finally {}
    this.executed = true;
    Response localResponse;
    try
    {
      this.client.dispatcher.executed(this);
      localResponse = new ApplicationInterceptorChain(0, this.originalRequest).proceed(this.originalRequest);
      if (localResponse == null) {
        throw new IOException("Canceled");
      }
    }
    finally
    {
      this.client.dispatcher.finished(this);
    }
    this.client.dispatcher.finished(this);
    return localResponse;
  }
  
  final Response getResponse$232b810d(Request paramRequest)
    throws IOException
  {
    RequestBody localRequestBody = paramRequest.body;
    Request.Builder localBuilder11;
    int i;
    if (localRequestBody != null)
    {
      localBuilder11 = paramRequest.newBuilder();
      MediaType localMediaType = localRequestBody.contentType();
      if (localMediaType != null) {
        localBuilder11.header("Content-Type", localMediaType.toString());
      }
      long l13 = localRequestBody.contentLength();
      if (l13 != -1L)
      {
        localBuilder11.header("Content-Length", Long.toString(l13));
        localBuilder11.removeHeader("Transfer-Encoding");
        paramRequest = localBuilder11.build();
      }
    }
    else
    {
      this.engine = new HttpEngine(this.client, paramRequest, false, false, false, null, null, null, null);
      i = 0;
    }
    HttpEngine localHttpEngine3;
    HttpEngine localHttpEngine1;
    label228:
    int k;
    label278:
    Connection localConnection1;
    int j;
    label304:
    HttpEngine localHttpEngine2;
    for (;;)
    {
      if (this.canceled)
      {
        this.engine.releaseConnection();
        return null;
        localBuilder11.header("Transfer-Encoding", "chunked");
        localBuilder11.removeHeader("Content-Length");
        break;
      }
      try
      {
        localHttpEngine3 = this.engine;
        if (localHttpEngine3.cacheStrategy != null) {
          break label1793;
        }
        if (localHttpEngine3.transport == null) {
          break label326;
        }
        throw new IllegalStateException();
      }
      catch (IOException localIOException)
      {
        localHttpEngine1 = this.engine;
        if ((localHttpEngine1.routeSelector != null) && (localHttpEngine1.connection != null)) {
          localHttpEngine1.routeSelector.connectFailed(localHttpEngine1.connection, localIOException);
        }
        if (localHttpEngine1.routeSelector != null) {
          break label228;
        }
      }
      if (localHttpEngine1.connection != null) {
        if (localHttpEngine1.routeSelector != null)
        {
          RouteSelector localRouteSelector1 = localHttpEngine1.routeSelector;
          if ((!localRouteSelector1.hasNextConnectionSpec()) && (!localRouteSelector1.hasNextInetSocketAddress()) && (!localRouteSelector1.hasNextProxy()) && (!localRouteSelector1.hasNextPostponed())) {
            break label2852;
          }
          k = 1;
          if (k == 0) {}
        }
        else
        {
          localConnection1 = localHttpEngine1.connection;
          if (localHttpEngine1.client.retryOnConnectionFailure) {
            break label2858;
          }
          j = 0;
          if (j != 0) {
            break label2997;
          }
        }
      }
      localHttpEngine2 = null;
      label312:
      if (localHttpEngine2 == null) {
        break label3054;
      }
      this.engine = localHttpEngine2;
    }
    label326:
    Request localRequest3 = localHttpEngine3.userRequest;
    Request.Builder localBuilder5 = localRequest3.newBuilder();
    if (localRequest3.header("Host") == null) {
      localBuilder5.header("Host", HttpEngine.hostHeader(localRequest3.url()));
    }
    if (((localHttpEngine3.connection == null) || (localHttpEngine3.connection.protocol != Protocol.HTTP_1_0)) && (localRequest3.header("Connection") == null)) {
      localBuilder5.header("Connection", "Keep-Alive");
    }
    if (localRequest3.header("Accept-Encoding") == null)
    {
      localHttpEngine3.transparentGzip = true;
      localBuilder5.header("Accept-Encoding", "gzip");
    }
    CookieHandler localCookieHandler = localHttpEngine3.client.cookieHandler;
    if (localCookieHandler != null)
    {
      Map localMap = OkHeaders.toMultimap$29224fb9(localBuilder5.build().headers);
      OkHeaders.addCookies(localBuilder5, localCookieHandler.get(localRequest3.uri(), localMap));
    }
    if (localRequest3.header("User-Agent") == null) {
      localBuilder5.header("User-Agent", "okhttp/2.2.0");
    }
    Request localRequest4 = localBuilder5.build();
    InternalCache localInternalCache2 = Internal.instance.internalCache(localHttpEngine3.client);
    Response localResponse4;
    label544:
    CacheStrategy.Factory localFactory;
    CacheStrategy localCacheStrategy;
    label584:
    Request localRequest5;
    long l3;
    label863:
    long l6;
    label956:
    long l7;
    label984:
    long l8;
    label1007:
    Response.Builder localBuilder9;
    if (localInternalCache2 != null)
    {
      localResponse4 = localInternalCache2.get$7633b7c3();
      localFactory = new CacheStrategy.Factory(System.currentTimeMillis(), localRequest4, localResponse4);
      if (localFactory.cacheResponse == null) {
        localCacheStrategy = new CacheStrategy(localFactory.request, null, (byte)0);
      }
      CacheControl localCacheControl1;
      for (;;)
      {
        if ((localCacheStrategy.networkRequest != null) && (localFactory.request.cacheControl().onlyIfCached)) {
          localCacheStrategy = new CacheStrategy(null, null, (byte)0);
        }
        localHttpEngine3.cacheStrategy = localCacheStrategy;
        localHttpEngine3.networkRequest = localHttpEngine3.cacheStrategy.networkRequest;
        localHttpEngine3.cacheResponse = localHttpEngine3.cacheStrategy.cacheResponse;
        if ((localResponse4 != null) && (localHttpEngine3.cacheResponse == null)) {
          Util.closeQuietly(localResponse4.body);
        }
        if (localHttpEngine3.networkRequest == null) {
          break label1889;
        }
        if (localHttpEngine3.connection != null) {
          break label1677;
        }
        localRequest5 = localHttpEngine3.networkRequest;
        if (localHttpEngine3.connection == null) {
          break label1453;
        }
        throw new IllegalStateException();
        if ((localFactory.request.isHttps()) && (localFactory.cacheResponse.handshake == null))
        {
          localCacheStrategy = new CacheStrategy(localFactory.request, null, (byte)0);
        }
        else if (!CacheStrategy.isCacheable(localFactory.cacheResponse, localFactory.request))
        {
          localCacheStrategy = new CacheStrategy(localFactory.request, null, (byte)0);
        }
        else
        {
          localCacheControl1 = localFactory.request.cacheControl();
          if ((!localCacheControl1.noCache) && (!CacheStrategy.Factory.hasConditions(localFactory.request))) {
            break;
          }
          localCacheStrategy = new CacheStrategy(localFactory.request, null, (byte)0);
        }
      }
      if (localFactory.servedDate == null) {
        break label3584;
      }
      l3 = Math.max(0L, localFactory.receivedResponseMillis - localFactory.servedDate.getTime());
      if (localFactory.ageSeconds != -1) {
        l3 = Math.max(l3, TimeUnit.SECONDS.toMillis(localFactory.ageSeconds));
      }
      long l4 = localFactory.receivedResponseMillis - localFactory.sentRequestMillis;
      long l5 = localFactory.nowMillis - localFactory.receivedResponseMillis + (l3 + l4);
      CacheControl localCacheControl2 = localFactory.cacheResponse.cacheControl();
      if (localCacheControl2.maxAgeSeconds != -1)
      {
        l6 = TimeUnit.SECONDS.toMillis(localCacheControl2.maxAgeSeconds);
        if (localCacheControl1.maxAgeSeconds == -1) {
          break label3571;
        }
        l7 = Math.min(l6, TimeUnit.SECONDS.toMillis(localCacheControl1.maxAgeSeconds));
        if (localCacheControl1.minFreshSeconds == -1) {
          break label3565;
        }
        l8 = TimeUnit.SECONDS.toMillis(localCacheControl1.minFreshSeconds);
        long l9 = 0L;
        CacheControl localCacheControl3 = localFactory.cacheResponse.cacheControl();
        if ((!localCacheControl3.mustRevalidate) && (localCacheControl1.maxStaleSeconds != -1)) {
          l9 = TimeUnit.SECONDS.toMillis(localCacheControl1.maxStaleSeconds);
        }
        if ((localCacheControl3.noCache) || (l5 + l8 >= l9 + l7)) {
          break label1323;
        }
        localBuilder9 = localFactory.cacheResponse.newBuilder();
        if (l8 + l5 >= l7) {
          localBuilder9.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
        }
        if (l5 > 86400000L) {
          if ((localFactory.cacheResponse.cacheControl().maxAgeSeconds != -1) || (localFactory.expires != null)) {
            break label3602;
          }
        }
      }
    }
    label1677:
    label3602:
    for (int m = 1;; m = 0)
    {
      if (m != 0) {
        localBuilder9.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
      }
      localCacheStrategy = new CacheStrategy(null, localBuilder9.build(), (byte)0);
      break label584;
      if (localFactory.expires != null)
      {
        if (localFactory.servedDate != null) {}
        for (long l12 = localFactory.servedDate.getTime();; l12 = localFactory.receivedResponseMillis)
        {
          l6 = localFactory.expires.getTime() - l12;
          if (l6 > 0L) {
            break;
          }
          l6 = 0L;
          break;
        }
      }
      if ((localFactory.lastModified != null) && (localFactory.cacheResponse.request.url().getQuery() == null))
      {
        if (localFactory.servedDate != null) {}
        for (long l10 = localFactory.servedDate.getTime();; l10 = localFactory.sentRequestMillis)
        {
          long l11 = l10 - localFactory.lastModified.getTime();
          if (l11 <= 0L) {
            break label3590;
          }
          l6 = l11 / 10L;
          break;
        }
        label1323:
        Request.Builder localBuilder10 = localFactory.request.newBuilder();
        if (localFactory.lastModified != null) {
          localBuilder10.header("If-Modified-Since", localFactory.lastModifiedString);
        }
        Request localRequest6;
        for (;;)
        {
          if (localFactory.etag != null) {
            localBuilder10.header("If-None-Match", localFactory.etag);
          }
          localRequest6 = localBuilder10.build();
          if (!CacheStrategy.Factory.hasConditions(localRequest6)) {
            break label1437;
          }
          localCacheStrategy = new CacheStrategy(localRequest6, localFactory.cacheResponse, (byte)0);
          break;
          if (localFactory.servedDate != null) {
            localBuilder10.header("If-Modified-Since", localFactory.servedDateString);
          }
        }
        label1437:
        localCacheStrategy = new CacheStrategy(localRequest6, null, (byte)0);
        break label584;
        label1453:
        if (localHttpEngine3.routeSelector == null)
        {
          OkHttpClient localOkHttpClient = localHttpEngine3.client;
          String str3 = localRequest5.url().getHost();
          if ((str3 == null) || (str3.length() == 0)) {
            throw new UnknownHostException(localRequest5.url().toString());
          }
          boolean bool = localRequest5.isHttps();
          SSLSocketFactory localSSLSocketFactory = null;
          HostnameVerifier localHostnameVerifier = null;
          CertificatePinner localCertificatePinner = null;
          if (bool)
          {
            localSSLSocketFactory = localOkHttpClient.sslSocketFactory;
            localHostnameVerifier = localOkHttpClient.hostnameVerifier;
            localCertificatePinner = localOkHttpClient.certificatePinner;
          }
          localHttpEngine3.routeSelector = new RouteSelector(new Address(str3, Util.getEffectivePort(localRequest5.url()), localOkHttpClient.socketFactory, localSSLSocketFactory, localHostnameVerifier, localCertificatePinner, localOkHttpClient.authenticator, localOkHttpClient.proxy, localOkHttpClient.protocols, localOkHttpClient.connectionSpecs, localOkHttpClient.proxySelector), localRequest5.uri(), localOkHttpClient, localRequest5);
        }
        RouteSelector localRouteSelector2 = localHttpEngine3.routeSelector;
        Connection localConnection4 = localRouteSelector2.nextUnconnected();
        Internal.instance.connectAndSetOwner(localRouteSelector2.client, localConnection4, localHttpEngine3, localRouteSelector2.request);
        localHttpEngine3.connection = localConnection4;
        localHttpEngine3.route = localHttpEngine3.connection.route;
        localHttpEngine3.transport = Internal.instance.newTransport(localHttpEngine3.connection, localHttpEngine3);
        long l2;
        if ((localHttpEngine3.callerWritesRequestBody) && (localHttpEngine3.permitsRequestBody()) && (localHttpEngine3.requestBodyOut == null))
        {
          l2 = OkHeaders.contentLength(localRequest4);
          if (!localHttpEngine3.bufferRequestBody) {
            break label1849;
          }
          if (l2 > 2147483647L) {
            throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
          }
          if (l2 == -1L) {
            break label1834;
          }
          localHttpEngine3.transport.writeRequestHeaders(localHttpEngine3.networkRequest);
          localHttpEngine3.requestBodyOut = new RetryableSink((int)l2);
        }
        HttpEngine localHttpEngine4;
        for (;;)
        {
          localHttpEngine4 = this.engine;
          if (localHttpEngine4.userResponse != null) {
            break label2276;
          }
          if ((localHttpEngine4.networkRequest != null) || (localHttpEngine4.cacheResponse != null)) {
            break;
          }
          throw new IllegalStateException("call sendRequest() first!");
          label1834:
          localHttpEngine3.requestBodyOut = new RetryableSink();
          continue;
          label1849:
          localHttpEngine3.transport.writeRequestHeaders(localHttpEngine3.networkRequest);
          localHttpEngine3.requestBodyOut = localHttpEngine3.transport.createRequestBody(localHttpEngine3.networkRequest, l2);
        }
        label1889:
        if (localHttpEngine3.connection != null)
        {
          Internal.instance.recycle(localHttpEngine3.client.connectionPool, localHttpEngine3.connection);
          localHttpEngine3.connection = null;
        }
        Response.Builder localBuilder8;
        if (localHttpEngine3.cacheResponse != null)
        {
          localBuilder8 = localHttpEngine3.cacheResponse.newBuilder();
          localBuilder8.request = localHttpEngine3.userRequest;
        }
        Response.Builder localBuilder7;
        for (localHttpEngine3.userResponse = localBuilder8.priorResponse(HttpEngine.stripBody(localHttpEngine3.priorResponse)).cacheResponse(HttpEngine.stripBody(localHttpEngine3.cacheResponse)).build();; localHttpEngine3.userResponse = localBuilder7.build())
        {
          localHttpEngine3.userResponse = localHttpEngine3.unzip(localHttpEngine3.userResponse);
          break;
          Response.Builder localBuilder6 = new Response.Builder();
          localBuilder6.request = localHttpEngine3.userRequest;
          localBuilder7 = localBuilder6.priorResponse(HttpEngine.stripBody(localHttpEngine3.priorResponse));
          localBuilder7.protocol = Protocol.HTTP_1_1;
          localBuilder7.code = 504;
          localBuilder7.message = "Unsatisfiable Request (only-if-cached)";
          localBuilder7.body = HttpEngine.EMPTY_BODY;
        }
        Response localResponse2;
        if (localHttpEngine4.networkRequest != null)
        {
          if (!localHttpEngine4.forWebSocket) {
            break label2307;
          }
          localHttpEngine4.transport.writeRequestHeaders(localHttpEngine4.networkRequest);
          localResponse2 = localHttpEngine4.readNetworkResponse();
          label2117:
          localHttpEngine4.receiveHeaders(localResponse2.headers);
          if (localHttpEngine4.cacheResponse == null) {
            break label2557;
          }
          if (!HttpEngine.validate(localHttpEngine4.cacheResponse, localResponse2)) {
            break label2546;
          }
          Response.Builder localBuilder4 = localHttpEngine4.cacheResponse.newBuilder();
          localBuilder4.request = localHttpEngine4.userRequest;
          localHttpEngine4.userResponse = localBuilder4.priorResponse(HttpEngine.stripBody(localHttpEngine4.priorResponse)).headers(HttpEngine.combine(localHttpEngine4.cacheResponse.headers, localResponse2.headers)).cacheResponse(HttpEngine.stripBody(localHttpEngine4.cacheResponse)).networkResponse(HttpEngine.stripBody(localResponse2)).build();
          localResponse2.body.close();
          localHttpEngine4.releaseConnection();
          Internal.instance.internalCache(localHttpEngine4.client);
          HttpEngine.stripBody(localHttpEngine4.userResponse);
          localHttpEngine4.userResponse = localHttpEngine4.unzip(localHttpEngine4.userResponse);
        }
        label2276:
        Response localResponse1;
        HttpEngine localHttpEngine5;
        label2544:
        label2546:
        label2557:
        do
        {
          localResponse1 = this.engine.getResponse();
          localHttpEngine5 = this.engine;
          if (localHttpEngine5.userResponse != null) {
            break label3057;
          }
          throw new IllegalStateException();
          if (!localHttpEngine4.callerWritesRequestBody)
          {
            localResponse2 = new HttpEngine.NetworkInterceptorChain(localHttpEngine4, 0, localHttpEngine4.networkRequest).proceed(localHttpEngine4.networkRequest);
            break label2117;
          }
          if ((localHttpEngine4.bufferedRequestBody != null) && (localHttpEngine4.bufferedRequestBody.buffer().size > 0L)) {
            localHttpEngine4.bufferedRequestBody.emit();
          }
          if (localHttpEngine4.sentRequestMillis == -1L)
          {
            if ((OkHeaders.contentLength(localHttpEngine4.networkRequest) == -1L) && ((localHttpEngine4.requestBodyOut instanceof RetryableSink)))
            {
              long l1 = ((RetryableSink)localHttpEngine4.requestBodyOut).content.size;
              localHttpEngine4.networkRequest = localHttpEngine4.networkRequest.newBuilder().header("Content-Length", Long.toString(l1)).build();
            }
            localHttpEngine4.transport.writeRequestHeaders(localHttpEngine4.networkRequest);
          }
          if (localHttpEngine4.requestBodyOut == null) {
            break;
          }
          if (localHttpEngine4.bufferedRequestBody != null) {
            localHttpEngine4.bufferedRequestBody.close();
          }
          for (;;)
          {
            if (!(localHttpEngine4.requestBodyOut instanceof RetryableSink)) {
              break label2544;
            }
            localHttpEngine4.transport.writeRequestBody((RetryableSink)localHttpEngine4.requestBodyOut);
            break;
            localHttpEngine4.requestBodyOut.close();
          }
          break;
          Util.closeQuietly(localHttpEngine4.cacheResponse.body);
          Response.Builder localBuilder2 = localResponse2.newBuilder();
          localBuilder2.request = localHttpEngine4.userRequest;
          localHttpEngine4.userResponse = localBuilder2.priorResponse(HttpEngine.stripBody(localHttpEngine4.priorResponse)).cacheResponse(HttpEngine.stripBody(localHttpEngine4.cacheResponse)).networkResponse(HttpEngine.stripBody(localResponse2)).build();
        } while (!HttpEngine.hasBody(localHttpEngine4.userResponse));
        InternalCache localInternalCache1 = Internal.instance.internalCache(localHttpEngine4.client);
        if (localInternalCache1 != null)
        {
          if (CacheStrategy.isCacheable(localHttpEngine4.userResponse, localHttpEngine4.networkRequest)) {
            break label2745;
          }
          String str2 = localHttpEngine4.networkRequest.method;
          if ((!str2.equals("POST")) && (!str2.equals("PATCH")) && (!str2.equals("PUT"))) {
            str2.equals("DELETE");
          }
        }
        label2711:
        CacheRequest localCacheRequest = localHttpEngine4.storeRequest;
        Object localObject = localHttpEngine4.userResponse;
        if (localCacheRequest == null) {}
        for (;;)
        {
          localHttpEngine4.userResponse = localHttpEngine4.unzip((Response)localObject);
          break;
          label2745:
          HttpEngine.stripBody(localHttpEngine4.userResponse);
          localHttpEngine4.storeRequest = localInternalCache1.put$3be241a0();
          break label2711;
          Sink localSink = localCacheRequest.body();
          if (localSink != null)
          {
            HttpEngine.2 local2 = new HttpEngine.2(localHttpEngine4, ((Response)localObject).body.source(), localCacheRequest, Okio.buffer(localSink));
            Response.Builder localBuilder3 = ((Response)localObject).newBuilder();
            localBuilder3.body = new RealResponseBody(((Response)localObject).headers, Okio.buffer(local2));
            Response localResponse3 = localBuilder3.build();
            localObject = localResponse3;
          }
        }
        label2852:
        k = 0;
        break label278;
        label2858:
        if (((localIOException instanceof SSLPeerUnverifiedException)) || (((localIOException instanceof SSLHandshakeException)) && ((localIOException.getCause() instanceof CertificateException))))
        {
          j = 0;
          break label304;
        }
        if ((localIOException instanceof ProtocolException))
        {
          j = 0;
          break label304;
        }
        Route localRoute;
        if ((localIOException instanceof SocketTimeoutException)) {
          if (localConnection1 == null)
          {
            localRoute = null;
            label2921:
            if ((localRoute == null) || (!(localRoute.inetSocketAddress.getAddress() instanceof Inet6Address))) {
              break label2983;
            }
            System.err.println("Attempting recover after: " + localIOException.getMessage());
          }
        }
        label2983:
        while (!(localIOException instanceof InterruptedIOException))
        {
          j = 1;
          break;
          localRoute = localConnection1.route;
          break label2921;
        }
        j = 0;
        break label304;
        label2997:
        Connection localConnection2 = localHttpEngine1.close();
        localHttpEngine2 = new HttpEngine(localHttpEngine1.client, localHttpEngine1.userRequest, localHttpEngine1.bufferRequestBody, localHttpEngine1.callerWritesRequestBody, localHttpEngine1.forWebSocket, localConnection2, localHttpEngine1.routeSelector, null, localHttpEngine1.priorResponse);
        break label312;
        label3054:
        throw localIOException;
        label3057:
        Proxy localProxy;
        label3156:
        Request localRequest1;
        if (localHttpEngine5.route != null)
        {
          localProxy = localHttpEngine5.route.proxy;
          switch (localHttpEngine5.userResponse.code)
          {
          default: 
            localRequest1 = null;
          }
        }
        for (;;)
        {
          if (localRequest1 != null) {
            break label3467;
          }
          this.engine.releaseConnection();
          return localResponse1;
          localProxy = localHttpEngine5.client.proxy;
          break;
          if (localProxy.type() != Proxy.Type.HTTP) {
            throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
          }
          localRequest1 = OkHeaders.processAuthHeader(localHttpEngine5.client.authenticator, localHttpEngine5.userResponse, localProxy);
          continue;
          if (((!localHttpEngine5.userRequest.method.equals("GET")) && (!localHttpEngine5.userRequest.method.equals("HEAD"))) || (!localHttpEngine5.client.followRedirects)) {
            break label3156;
          }
          String str1 = localHttpEngine5.userResponse.header("Location");
          if (str1 == null) {
            break label3156;
          }
          URL localURL = new URL(localHttpEngine5.userRequest.url(), str1);
          if (((!localURL.getProtocol().equals("https")) && (!localURL.getProtocol().equals("http"))) || ((!localURL.getProtocol().equals(localHttpEngine5.userRequest.url().getProtocol())) && (!localHttpEngine5.client.followSslRedirects))) {
            break label3156;
          }
          Request.Builder localBuilder1 = localHttpEngine5.userRequest.newBuilder();
          if (HttpMethod.permitsRequestBody(localHttpEngine5.userRequest.method))
          {
            localBuilder1.method("GET", null);
            localBuilder1.removeHeader("Transfer-Encoding");
            localBuilder1.removeHeader("Content-Length");
            localBuilder1.removeHeader("Content-Type");
          }
          if (!localHttpEngine5.sameConnection(localURL)) {
            localBuilder1.removeHeader("Authorization");
          }
          localRequest1 = localBuilder1.url(localURL).build();
        }
        i++;
        if (i > 20) {
          throw new ProtocolException("Too many follow-up requests: " + i);
        }
        if (!this.engine.sameConnection(localRequest1.url())) {
          this.engine.releaseConnection();
        }
        Connection localConnection3 = this.engine.close();
        Request localRequest2 = localRequest1;
        this.engine = new HttpEngine(this.client, localRequest2, false, false, false, localConnection3, null, null, localResponse1);
        break;
        label3565:
        l8 = 0L;
        break label1007;
        label3571:
        l7 = l6;
        break label984;
        localResponse4 = null;
        break label544;
        l3 = 0L;
        break label863;
        l6 = 0L;
        break label956;
      }
      l6 = 0L;
      break label956;
    }
  }
  
  final class ApplicationInterceptorChain
    implements Interceptor.Chain
  {
    private final int index;
    private final Request request;
    
    ApplicationInterceptorChain(int paramInt, Request paramRequest)
    {
      this.index = paramInt;
      this.request = paramRequest;
    }
    
    public final Response proceed(Request paramRequest)
      throws IOException
    {
      if (this.index < Call.this.client.interceptors.size())
      {
        ApplicationInterceptorChain localApplicationInterceptorChain = new ApplicationInterceptorChain(Call.this, 1 + this.index, paramRequest);
        return ((Interceptor)Call.this.client.interceptors.get(this.index)).intercept(localApplicationInterceptorChain);
      }
      return Call.this.getResponse$232b810d(paramRequest);
    }
    
    public final Request request()
    {
      return this.request;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Call
 * JD-Core Version:    0.7.0.1
 */