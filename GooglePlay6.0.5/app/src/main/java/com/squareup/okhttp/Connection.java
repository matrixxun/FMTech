package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpConnection;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSource;
import okio.Source;

public final class Connection
{
  boolean connected = false;
  public Handshake handshake;
  HttpConnection httpConnection;
  long idleStartTimeNs;
  private Object owner;
  final ConnectionPool pool;
  public Protocol protocol = Protocol.HTTP_1_1;
  int recycleCount;
  public final Route route;
  public Socket socket;
  SpdyConnection spdyConnection;
  
  public Connection(ConnectionPool paramConnectionPool, Route paramRoute)
  {
    this.pool = paramConnectionPool;
    this.route = paramRoute;
  }
  
  final boolean clearOwner()
  {
    synchronized (this.pool)
    {
      if (this.owner == null) {
        return false;
      }
      this.owner = null;
      return true;
    }
  }
  
  final long getIdleStartTimeNs()
  {
    if (this.spdyConnection == null) {
      return this.idleStartTimeNs;
    }
    return this.spdyConnection.getIdleStartTimeNs();
  }
  
  final boolean isAlive()
  {
    return (!this.socket.isClosed()) && (!this.socket.isInputShutdown()) && (!this.socket.isOutputShutdown());
  }
  
  final boolean isIdle()
  {
    return (this.spdyConnection == null) || (this.spdyConnection.isIdle());
  }
  
  final boolean isSpdy()
  {
    return this.spdyConnection != null;
  }
  
  final void makeTunnel(Request paramRequest, int paramInt1, int paramInt2)
    throws IOException
  {
    HttpConnection localHttpConnection = new HttpConnection(this.pool, this, this.socket);
    localHttpConnection.setTimeouts(paramInt1, paramInt2);
    URL localURL = paramRequest.url();
    String str = "CONNECT " + localURL.getHost() + ":" + localURL.getPort() + " HTTP/1.1";
    do
    {
      localHttpConnection.writeRequest(paramRequest.headers, str);
      localHttpConnection.flush();
      Response.Builder localBuilder = localHttpConnection.readResponse();
      localBuilder.request = paramRequest;
      Response localResponse = localBuilder.build();
      long l = OkHeaders.contentLength(localResponse);
      if (l == -1L) {
        l = 0L;
      }
      Source localSource = localHttpConnection.newFixedLengthSource(l);
      Util.skipAll(localSource, 2147483647, TimeUnit.MILLISECONDS);
      localSource.close();
      switch (localResponse.code)
      {
      default: 
        throw new IOException("Unexpected response code for CONNECT: " + localResponse.code);
      case 200: 
        if (localHttpConnection.source.buffer().size <= 0L) {
          break;
        }
        throw new IOException("TLS tunnel buffered too many bytes!");
      case 407: 
        paramRequest = OkHeaders.processAuthHeader(this.route.address.authenticator, localResponse, this.route.proxy);
      }
    } while (paramRequest != null);
    throw new IOException("Failed to authenticate with proxy");
  }
  
  final void setOwner(Object paramObject)
  {
    if (isSpdy()) {
      return;
    }
    synchronized (this.pool)
    {
      if (this.owner != null) {
        throw new IllegalStateException("Connection already has an owner!");
      }
    }
    this.owner = paramObject;
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Connection{").append(this.route.address.uriHost).append(":").append(this.route.address.uriPort).append(", proxy=").append(this.route.proxy).append(" hostAddress=").append(this.route.inetSocketAddress.getAddress().getHostAddress()).append(" cipherSuite=");
    if (this.handshake != null) {}
    for (String str = this.handshake.cipherSuite;; str = "none") {
      return str + " protocol=" + this.protocol + '}';
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Connection
 * JD-Core Version:    0.7.0.1
 */