package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import java.io.IOException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class HttpTransport
  implements Transport
{
  private final HttpConnection httpConnection;
  private final HttpEngine httpEngine;
  
  public HttpTransport(HttpEngine paramHttpEngine, HttpConnection paramHttpConnection)
  {
    this.httpEngine = paramHttpEngine;
    this.httpConnection = paramHttpConnection;
  }
  
  public final boolean canReuseConnection()
  {
    if ("close".equalsIgnoreCase(this.httpEngine.userRequest.header("Connection"))) {}
    for (;;)
    {
      return false;
      if (!"close".equalsIgnoreCase(this.httpEngine.getResponse().header("Connection")))
      {
        if (this.httpConnection.state == 6) {}
        for (int i = 1; i == 0; i = 0) {
          return true;
        }
      }
    }
  }
  
  public final Sink createRequestBody(Request paramRequest, long paramLong)
    throws IOException
  {
    if ("chunked".equalsIgnoreCase(paramRequest.header("Transfer-Encoding")))
    {
      HttpConnection localHttpConnection2 = this.httpConnection;
      if (localHttpConnection2.state != 1) {
        throw new IllegalStateException("state: " + localHttpConnection2.state);
      }
      localHttpConnection2.state = 2;
      return new HttpConnection.ChunkedSink(localHttpConnection2, (byte)0);
    }
    if (paramLong != -1L)
    {
      HttpConnection localHttpConnection1 = this.httpConnection;
      if (localHttpConnection1.state != 1) {
        throw new IllegalStateException("state: " + localHttpConnection1.state);
      }
      localHttpConnection1.state = 2;
      return new HttpConnection.FixedLengthSink(localHttpConnection1, paramLong, (byte)0);
    }
    throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
  }
  
  public final void finishRequest()
    throws IOException
  {
    this.httpConnection.flush();
  }
  
  public final ResponseBody openResponseBody(Response paramResponse)
    throws IOException
  {
    Object localObject;
    if (!HttpEngine.hasBody(paramResponse)) {
      localObject = this.httpConnection.newFixedLengthSource(0L);
    }
    for (;;)
    {
      return new RealResponseBody(paramResponse.headers, Okio.buffer((Source)localObject));
      if ("chunked".equalsIgnoreCase(paramResponse.header("Transfer-Encoding")))
      {
        HttpConnection localHttpConnection2 = this.httpConnection;
        HttpEngine localHttpEngine = this.httpEngine;
        if (localHttpConnection2.state != 4) {
          throw new IllegalStateException("state: " + localHttpConnection2.state);
        }
        localHttpConnection2.state = 5;
        localObject = new HttpConnection.ChunkedSource(localHttpConnection2, localHttpEngine);
      }
      else
      {
        long l = OkHeaders.contentLength(paramResponse);
        if (l != -1L)
        {
          localObject = this.httpConnection.newFixedLengthSource(l);
        }
        else
        {
          HttpConnection localHttpConnection1 = this.httpConnection;
          if (localHttpConnection1.state != 4) {
            throw new IllegalStateException("state: " + localHttpConnection1.state);
          }
          localHttpConnection1.state = 5;
          localObject = new HttpConnection.UnknownLengthSource(localHttpConnection1, (byte)0);
        }
      }
    }
  }
  
  public final Response.Builder readResponseHeaders()
    throws IOException
  {
    return this.httpConnection.readResponse();
  }
  
  public final void releaseConnectionOnIdle()
    throws IOException
  {
    if (canReuseConnection())
    {
      HttpConnection localHttpConnection2 = this.httpConnection;
      localHttpConnection2.onIdle = 1;
      if (localHttpConnection2.state == 0)
      {
        localHttpConnection2.onIdle = 0;
        Internal.instance.recycle(localHttpConnection2.pool, localHttpConnection2.connection);
      }
    }
    HttpConnection localHttpConnection1;
    do
    {
      return;
      localHttpConnection1 = this.httpConnection;
      localHttpConnection1.onIdle = 2;
    } while (localHttpConnection1.state != 0);
    localHttpConnection1.state = 6;
    localHttpConnection1.connection.socket.close();
  }
  
  public final void writeRequestBody(RetryableSink paramRetryableSink)
    throws IOException
  {
    HttpConnection localHttpConnection = this.httpConnection;
    if (localHttpConnection.state != 1) {
      throw new IllegalStateException("state: " + localHttpConnection.state);
    }
    localHttpConnection.state = 3;
    paramRetryableSink.writeToSocket(localHttpConnection.sink);
  }
  
  public final void writeRequestHeaders(Request paramRequest)
    throws IOException
  {
    this.httpEngine.writingRequestHeaders();
    Proxy.Type localType = this.httpEngine.connection.route.proxy.type();
    Protocol localProtocol = this.httpEngine.connection.protocol;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramRequest.method);
    localStringBuilder.append(' ');
    int i;
    if ((!paramRequest.isHttps()) && (localType == Proxy.Type.HTTP))
    {
      i = 1;
      if (i == 0) {
        break label139;
      }
      localStringBuilder.append(paramRequest.url());
    }
    for (;;)
    {
      localStringBuilder.append(' ');
      localStringBuilder.append(RequestLine.version(localProtocol));
      String str = localStringBuilder.toString();
      this.httpConnection.writeRequest(paramRequest.headers, str);
      return;
      i = 0;
      break;
      label139:
      localStringBuilder.append(RequestLine.requestPath(paramRequest.url()));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.HttpTransport
 * JD-Core Version:    0.7.0.1
 */