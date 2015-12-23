package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class HttpEngine
{
  public static final ResponseBody EMPTY_BODY = new ResponseBody()
  {
    public final long contentLength()
    {
      return 0L;
    }
    
    public final MediaType contentType()
    {
      return null;
    }
    
    public final BufferedSource source()
    {
      return new Buffer();
    }
  };
  public final boolean bufferRequestBody;
  public BufferedSink bufferedRequestBody;
  public Response cacheResponse;
  public CacheStrategy cacheStrategy;
  public final boolean callerWritesRequestBody;
  public final OkHttpClient client;
  public Connection connection;
  public final boolean forWebSocket;
  public Request networkRequest;
  public final Response priorResponse;
  public Sink requestBodyOut;
  public Route route;
  public RouteSelector routeSelector;
  public long sentRequestMillis = -1L;
  public CacheRequest storeRequest;
  public boolean transparentGzip;
  public Transport transport;
  public final Request userRequest;
  public Response userResponse;
  
  public HttpEngine(OkHttpClient paramOkHttpClient, Request paramRequest, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Connection paramConnection, RouteSelector paramRouteSelector, RetryableSink paramRetryableSink, Response paramResponse)
  {
    this.client = paramOkHttpClient;
    this.userRequest = paramRequest;
    this.bufferRequestBody = paramBoolean1;
    this.callerWritesRequestBody = paramBoolean2;
    this.forWebSocket = paramBoolean3;
    this.connection = paramConnection;
    this.routeSelector = paramRouteSelector;
    this.requestBodyOut = null;
    this.priorResponse = paramResponse;
    if (paramConnection != null)
    {
      Internal.instance.setOwner(paramConnection, this);
      this.route = paramConnection.route;
      return;
    }
    this.route = null;
  }
  
  public static Headers combine(Headers paramHeaders1, Headers paramHeaders2)
    throws IOException
  {
    Headers.Builder localBuilder = new Headers.Builder();
    int i = 0;
    int j = paramHeaders1.namesAndValues.length / 2;
    while (i < j)
    {
      String str2 = paramHeaders1.name(i);
      String str3 = paramHeaders1.value(i);
      if (((!"Warning".equalsIgnoreCase(str2)) || (!str3.startsWith("1"))) && ((!OkHeaders.isEndToEnd(str2)) || (paramHeaders2.get(str2) == null))) {
        localBuilder.add(str2, str3);
      }
      i++;
    }
    int k = 0;
    int m = paramHeaders2.namesAndValues.length / 2;
    while (k < m)
    {
      String str1 = paramHeaders2.name(k);
      if ((!"Content-Length".equalsIgnoreCase(str1)) && (OkHeaders.isEndToEnd(str1))) {
        localBuilder.add(str1, paramHeaders2.value(k));
      }
      k++;
    }
    return localBuilder.build();
  }
  
  public static boolean hasBody(Response paramResponse)
  {
    if (paramResponse.request.method.equals("HEAD")) {}
    do
    {
      return false;
      int i = paramResponse.code;
      if (((i < 100) || (i >= 200)) && (i != 204) && (i != 304)) {
        return true;
      }
    } while ((OkHeaders.contentLength(paramResponse) == -1L) && (!"chunked".equalsIgnoreCase(paramResponse.header("Transfer-Encoding"))));
    return true;
  }
  
  public static String hostHeader(URL paramURL)
  {
    if (Util.getEffectivePort(paramURL) != Util.getDefaultPort(paramURL.getProtocol())) {
      return paramURL.getHost() + ":" + paramURL.getPort();
    }
    return paramURL.getHost();
  }
  
  public static Response stripBody(Response paramResponse)
  {
    if ((paramResponse != null) && (paramResponse.body != null))
    {
      Response.Builder localBuilder = paramResponse.newBuilder();
      localBuilder.body = null;
      paramResponse = localBuilder.build();
    }
    return paramResponse;
  }
  
  public static boolean validate(Response paramResponse1, Response paramResponse2)
  {
    if (paramResponse2.code == 304) {}
    Date localDate1;
    Date localDate2;
    do
    {
      return true;
      localDate1 = paramResponse1.headers.getDate("Last-Modified");
      if (localDate1 == null) {
        break;
      }
      localDate2 = paramResponse2.headers.getDate("Last-Modified");
    } while ((localDate2 != null) && (localDate2.getTime() < localDate1.getTime()));
    return false;
  }
  
  public final Connection close()
  {
    if (this.bufferedRequestBody != null) {
      Util.closeQuietly(this.bufferedRequestBody);
    }
    while (this.userResponse == null)
    {
      if (this.connection != null) {
        Util.closeQuietly(this.connection.socket);
      }
      this.connection = null;
      return null;
      if (this.requestBodyOut != null) {
        Util.closeQuietly(this.requestBodyOut);
      }
    }
    Util.closeQuietly(this.userResponse.body);
    if ((this.transport != null) && (this.connection != null) && (!this.transport.canReuseConnection()))
    {
      Util.closeQuietly(this.connection.socket);
      this.connection = null;
      return null;
    }
    if ((this.connection != null) && (!Internal.instance.clearOwner(this.connection))) {
      this.connection = null;
    }
    Connection localConnection = this.connection;
    this.connection = null;
    return localConnection;
  }
  
  public final Response getResponse()
  {
    if (this.userResponse == null) {
      throw new IllegalStateException();
    }
    return this.userResponse;
  }
  
  public final boolean permitsRequestBody()
  {
    return HttpMethod.permitsRequestBody(this.userRequest.method);
  }
  
  public final Response readNetworkResponse()
    throws IOException
  {
    this.transport.finishRequest();
    Response.Builder localBuilder1 = this.transport.readResponseHeaders();
    localBuilder1.request = this.networkRequest;
    localBuilder1.handshake = this.connection.handshake;
    Response localResponse = localBuilder1.header(OkHeaders.SENT_MILLIS, Long.toString(this.sentRequestMillis)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
    if (!this.forWebSocket)
    {
      Response.Builder localBuilder2 = localResponse.newBuilder();
      localBuilder2.body = this.transport.openResponseBody(localResponse);
      localResponse = localBuilder2.build();
    }
    Internal.instance.setProtocol(this.connection, localResponse.protocol);
    return localResponse;
  }
  
  public final void receiveHeaders(Headers paramHeaders)
    throws IOException
  {
    CookieHandler localCookieHandler = this.client.cookieHandler;
    if (localCookieHandler != null) {
      localCookieHandler.put(this.userRequest.uri(), OkHeaders.toMultimap$29224fb9(paramHeaders));
    }
  }
  
  public final void releaseConnection()
    throws IOException
  {
    if ((this.transport != null) && (this.connection != null)) {
      this.transport.releaseConnectionOnIdle();
    }
    this.connection = null;
  }
  
  public final boolean sameConnection(URL paramURL)
  {
    URL localURL = this.userRequest.url();
    return (localURL.getHost().equals(paramURL.getHost())) && (Util.getEffectivePort(localURL) == Util.getEffectivePort(paramURL)) && (localURL.getProtocol().equals(paramURL.getProtocol()));
  }
  
  public final Response unzip(Response paramResponse)
    throws IOException
  {
    if ((!this.transparentGzip) || (!"gzip".equalsIgnoreCase(this.userResponse.header("Content-Encoding")))) {}
    while (paramResponse.body == null) {
      return paramResponse;
    }
    GzipSource localGzipSource = new GzipSource(paramResponse.body.source());
    Headers localHeaders = paramResponse.headers.newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build();
    Response.Builder localBuilder = paramResponse.newBuilder().headers(localHeaders);
    localBuilder.body = new RealResponseBody(localHeaders, Okio.buffer(localGzipSource));
    return localBuilder.build();
  }
  
  public final void writingRequestHeaders()
  {
    if (this.sentRequestMillis != -1L) {
      throw new IllegalStateException();
    }
    this.sentRequestMillis = System.currentTimeMillis();
  }
  
  public final class NetworkInterceptorChain
    implements Interceptor.Chain
  {
    private int calls;
    private final int index;
    private final Request request;
    
    public NetworkInterceptorChain(int paramInt, Request paramRequest)
    {
      this.index = paramInt;
      this.request = paramRequest;
    }
    
    public final Response proceed(Request paramRequest)
      throws IOException
    {
      this.calls = (1 + this.calls);
      if (this.index > 0)
      {
        Interceptor localInterceptor2 = (Interceptor)HttpEngine.this.client.networkInterceptors.get(-1 + this.index);
        Address localAddress = HttpEngine.this.connection.route.address;
        if ((!paramRequest.url().getHost().equals(localAddress.uriHost)) || (Util.getEffectivePort(paramRequest.url()) != localAddress.uriPort)) {
          throw new IllegalStateException("network interceptor " + localInterceptor2 + " must retain the same host and port");
        }
        if (this.calls > 1) {
          throw new IllegalStateException("network interceptor " + localInterceptor2 + " must call proceed() exactly once");
        }
      }
      Response localResponse;
      if (this.index < HttpEngine.this.client.networkInterceptors.size())
      {
        NetworkInterceptorChain localNetworkInterceptorChain = new NetworkInterceptorChain(HttpEngine.this, 1 + this.index, paramRequest);
        Interceptor localInterceptor1 = (Interceptor)HttpEngine.this.client.networkInterceptors.get(this.index);
        localResponse = localInterceptor1.intercept(localNetworkInterceptorChain);
        if (localNetworkInterceptorChain.calls != 1) {
          throw new IllegalStateException("network interceptor " + localInterceptor1 + " must call proceed() exactly once");
        }
      }
      else
      {
        HttpEngine.this.transport.writeRequestHeaders(paramRequest);
        if ((HttpEngine.this.permitsRequestBody()) && (paramRequest.body != null))
        {
          BufferedSink localBufferedSink = Okio.buffer(HttpEngine.this.transport.createRequestBody(paramRequest, paramRequest.body.contentLength()));
          paramRequest.body.writeTo(localBufferedSink);
          localBufferedSink.close();
        }
        localResponse = HttpEngine.this.readNetworkResponse();
      }
      return localResponse;
    }
    
    public final Request request()
    {
      return this.request;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.HttpEngine
 * JD-Core Version:    0.7.0.1
 */