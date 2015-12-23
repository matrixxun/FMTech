package com.squareup.okhttp;

import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Network;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpConnection;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpTransport;
import com.squareup.okhttp.internal.http.SpdyTransport;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.spdy.FrameWriter;
import com.squareup.okhttp.internal.spdy.Settings;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import com.squareup.okhttp.internal.spdy.SpdyConnection.Builder;
import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class OkHttpClient
  implements Cloneable
{
  static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS;
  static final List<Protocol> DEFAULT_PROTOCOLS;
  private static SSLSocketFactory defaultSslSocketFactory;
  public Authenticator authenticator;
  private Cache cache;
  public CertificatePinner certificatePinner;
  public int connectTimeout;
  public ConnectionPool connectionPool;
  public List<ConnectionSpec> connectionSpecs;
  public CookieHandler cookieHandler;
  Dispatcher dispatcher;
  public boolean followRedirects = true;
  public boolean followSslRedirects = true;
  public HostnameVerifier hostnameVerifier;
  final List<Interceptor> interceptors = new ArrayList();
  InternalCache internalCache;
  Network network;
  public final List<Interceptor> networkInterceptors = new ArrayList();
  public List<Protocol> protocols;
  public Proxy proxy;
  public ProxySelector proxySelector;
  public int readTimeout;
  public boolean retryOnConnectionFailure = true;
  final RouteDatabase routeDatabase;
  public SocketFactory socketFactory;
  public SSLSocketFactory sslSocketFactory;
  public int writeTimeout;
  
  static
  {
    Protocol[] arrayOfProtocol = new Protocol[3];
    arrayOfProtocol[0] = Protocol.HTTP_2;
    arrayOfProtocol[1] = Protocol.SPDY_3;
    arrayOfProtocol[2] = Protocol.HTTP_1_1;
    DEFAULT_PROTOCOLS = Util.immutableList(arrayOfProtocol);
    ConnectionSpec[] arrayOfConnectionSpec = new ConnectionSpec[3];
    arrayOfConnectionSpec[0] = ConnectionSpec.MODERN_TLS;
    arrayOfConnectionSpec[1] = ConnectionSpec.COMPATIBLE_TLS;
    arrayOfConnectionSpec[2] = ConnectionSpec.CLEARTEXT;
    DEFAULT_CONNECTION_SPECS = Util.immutableList(arrayOfConnectionSpec);
    Internal.instance = new Internal()
    {
      public final void addLine(Headers.Builder paramAnonymousBuilder, String paramAnonymousString)
      {
        int i = paramAnonymousString.indexOf(":", 1);
        if (i != -1)
        {
          paramAnonymousBuilder.addLenient(paramAnonymousString.substring(0, i), paramAnonymousString.substring(i + 1));
          return;
        }
        if (paramAnonymousString.startsWith(":"))
        {
          paramAnonymousBuilder.addLenient("", paramAnonymousString.substring(1));
          return;
        }
        paramAnonymousBuilder.addLenient("", paramAnonymousString);
      }
      
      public final boolean clearOwner(Connection paramAnonymousConnection)
      {
        return paramAnonymousConnection.clearOwner();
      }
      
      public final void connectAndSetOwner(OkHttpClient paramAnonymousOkHttpClient, Connection paramAnonymousConnection, HttpEngine paramAnonymousHttpEngine, Request paramAnonymousRequest)
        throws IOException
      {
        paramAnonymousConnection.setOwner(paramAnonymousHttpEngine);
        label258:
        Platform localPlatform1;
        SSLSocket localSSLSocket;
        Route localRoute2;
        ConnectionSpec localConnectionSpec2;
        String[] arrayOfString1;
        String[] arrayOfString2;
        if (!paramAnonymousConnection.connected)
        {
          Route localRoute1 = paramAnonymousConnection.route;
          if ((localRoute1.address.sslSocketFactory != null) && (localRoute1.proxy.type() == Proxy.Type.HTTP)) {}
          Request localRequest;
          int n;
          int i1;
          int i2;
          for (int k = 1; k == 0; k = 0)
          {
            localRequest = null;
            n = paramAnonymousOkHttpClient.connectTimeout;
            i1 = paramAnonymousOkHttpClient.readTimeout;
            i2 = paramAnonymousOkHttpClient.writeTimeout;
            if (!paramAnonymousConnection.connected) {
              break label258;
            }
            throw new IllegalStateException("already connected");
          }
          String str1 = paramAnonymousRequest.url().getHost();
          int m = Util.getEffectivePort(paramAnonymousRequest.url());
          if (m == Util.getDefaultPort("https")) {}
          for (String str2 = str1;; str2 = str1 + ":" + m)
          {
            Request.Builder localBuilder = new Request.Builder().url(new URL("https", str1, m, "/")).header("Host", str2).header("Proxy-Connection", "Keep-Alive");
            String str3 = paramAnonymousRequest.header("User-Agent");
            if (str3 != null) {
              localBuilder.header("User-Agent", str3);
            }
            String str4 = paramAnonymousRequest.header("Proxy-Authorization");
            if (str4 != null) {
              localBuilder.header("Proxy-Authorization", str4);
            }
            localRequest = localBuilder.build();
            break;
          }
          if ((paramAnonymousConnection.route.proxy.type() == Proxy.Type.DIRECT) || (paramAnonymousConnection.route.proxy.type() == Proxy.Type.HTTP))
          {
            paramAnonymousConnection.socket = paramAnonymousConnection.route.address.socketFactory.createSocket();
            paramAnonymousConnection.socket.setSoTimeout(i1);
            Platform.get().connectSocket(paramAnonymousConnection.socket, paramAnonymousConnection.route.inetSocketAddress, n);
            if (paramAnonymousConnection.route.address.sslSocketFactory == null) {
              break label1120;
            }
            localPlatform1 = Platform.get();
            if (localRequest != null) {
              paramAnonymousConnection.makeTunnel(localRequest, i1, i2);
            }
            paramAnonymousConnection.socket = paramAnonymousConnection.route.address.sslSocketFactory.createSocket(paramAnonymousConnection.socket, paramAnonymousConnection.route.address.uriHost, paramAnonymousConnection.route.address.uriPort, true);
            localSSLSocket = (SSLSocket)paramAnonymousConnection.socket;
            ConnectionSpec localConnectionSpec1 = paramAnonymousConnection.route.connectionSpec;
            localRoute2 = paramAnonymousConnection.route;
            localConnectionSpec2 = localConnectionSpec1.supportedSpec;
            if (localConnectionSpec2 == null)
            {
              List localList1 = Util.intersect(localConnectionSpec1.cipherSuites, localSSLSocket.getSupportedCipherSuites());
              List localList2 = Util.intersect(localConnectionSpec1.tlsVersions, localSSLSocket.getSupportedProtocols());
              ConnectionSpec.Builder localBuilder2 = new ConnectionSpec.Builder(localConnectionSpec1);
              localBuilder2.cipherSuites = ((String[])localList1.toArray(new String[localList1.size()]));
              localBuilder2.tlsVersions = ((String[])localList2.toArray(new String[localList2.size()]));
              localConnectionSpec2 = localBuilder2.build();
              localConnectionSpec1.supportedSpec = localConnectionSpec2;
            }
            localSSLSocket.setEnabledProtocols(localConnectionSpec2.tlsVersions);
            arrayOfString1 = localConnectionSpec2.cipherSuites;
            if ((!localRoute2.shouldSendTlsFallbackIndicator) || (!Arrays.asList(localSSLSocket.getSupportedCipherSuites()).contains("TLS_FALLBACK_SCSV"))) {
              break label1239;
            }
            arrayOfString2 = new String[1 + arrayOfString1.length];
            System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, arrayOfString1.length);
            arrayOfString2[(-1 + arrayOfString2.length)] = "TLS_FALLBACK_SCSV";
          }
        }
        for (;;)
        {
          localSSLSocket.setEnabledCipherSuites(arrayOfString2);
          Platform localPlatform2 = Platform.get();
          if (localConnectionSpec2.supportsTlsExtensions) {
            localPlatform2.configureTlsExtensions(localSSLSocket, localRoute2.address.uriHost, localRoute2.address.protocols);
          }
          try
          {
            localSSLSocket.startHandshake();
            if (paramAnonymousConnection.route.connectionSpec.supportsTlsExtensions)
            {
              String str5 = localPlatform1.getSelectedProtocol(localSSLSocket);
              if (str5 != null) {
                paramAnonymousConnection.protocol = Protocol.get(str5);
              }
            }
            localPlatform1.afterHandshake(localSSLSocket);
            paramAnonymousConnection.handshake = Handshake.get(localSSLSocket.getSession());
            if (paramAnonymousConnection.route.address.hostnameVerifier.verify(paramAnonymousConnection.route.address.uriHost, localSSLSocket.getSession())) {
              break label895;
            }
            X509Certificate localX509Certificate = (X509Certificate)localSSLSocket.getSession().getPeerCertificates()[0];
            throw new IOException("Hostname " + paramAnonymousConnection.route.address.uriHost + " not verified:\n    certificate: " + CertificatePinner.pin(localX509Certificate) + "\n    DN: " + localX509Certificate.getSubjectDN().getName() + "\n    subjectAltNames: " + OkHostnameVerifier.allSubjectAltNames(localX509Certificate));
          }
          finally
          {
            localPlatform1.afterHandshake(localSSLSocket);
          }
          paramAnonymousConnection.socket = new Socket(paramAnonymousConnection.route.proxy);
          break;
          label895:
          paramAnonymousConnection.route.address.certificatePinner.check(paramAnonymousConnection.route.address.uriHost, paramAnonymousConnection.handshake.peerCertificates);
          if ((paramAnonymousConnection.protocol == Protocol.SPDY_3) || (paramAnonymousConnection.protocol == Protocol.HTTP_2))
          {
            localSSLSocket.setSoTimeout(0);
            SpdyConnection.Builder localBuilder1 = new SpdyConnection.Builder(paramAnonymousConnection.route.address.uriHost, paramAnonymousConnection.socket);
            localBuilder1.protocol = paramAnonymousConnection.protocol;
            paramAnonymousConnection.spdyConnection = new SpdyConnection(localBuilder1, (byte)0);
            SpdyConnection localSpdyConnection = paramAnonymousConnection.spdyConnection;
            localSpdyConnection.frameWriter.connectionPreface();
            localSpdyConnection.frameWriter.settings(localSpdyConnection.okHttpSettings);
            int i3 = localSpdyConnection.okHttpSettings.getInitialWindowSize$134621();
            if (i3 != 65536) {
              localSpdyConnection.frameWriter.windowUpdate(0, i3 - 65536);
            }
          }
          ConnectionPool localConnectionPool;
          for (;;)
          {
            paramAnonymousConnection.connected = true;
            if (!paramAnonymousConnection.isSpdy()) {
              break label1162;
            }
            localConnectionPool = paramAnonymousOkHttpClient.connectionPool;
            if (paramAnonymousConnection.isSpdy()) {
              break;
            }
            throw new IllegalArgumentException();
            paramAnonymousConnection.httpConnection = new HttpConnection(paramAnonymousConnection.pool, paramAnonymousConnection, paramAnonymousConnection.socket);
            continue;
            label1120:
            paramAnonymousConnection.httpConnection = new HttpConnection(paramAnonymousConnection.pool, paramAnonymousConnection, paramAnonymousConnection.socket);
          }
          if (paramAnonymousConnection.isAlive()) {}
          label1162:
          int i;
          int j;
          try
          {
            localConnectionPool.addConnection(paramAnonymousConnection);
            paramAnonymousOkHttpClient.routeDatabase.connected(paramAnonymousConnection.route);
            i = paramAnonymousOkHttpClient.readTimeout;
            j = paramAnonymousOkHttpClient.writeTimeout;
            if (!paramAnonymousConnection.connected) {
              throw new IllegalStateException("setTimeouts - not connected");
            }
          }
          finally {}
          if (paramAnonymousConnection.httpConnection != null)
          {
            paramAnonymousConnection.socket.setSoTimeout(i);
            paramAnonymousConnection.httpConnection.setTimeouts(i, j);
          }
          return;
          label1239:
          arrayOfString2 = arrayOfString1;
        }
      }
      
      public final InternalCache internalCache(OkHttpClient paramAnonymousOkHttpClient)
      {
        return paramAnonymousOkHttpClient.internalCache;
      }
      
      public final boolean isReadable(Connection paramAnonymousConnection)
      {
        if (paramAnonymousConnection.httpConnection != null) {
          return paramAnonymousConnection.httpConnection.isReadable();
        }
        return true;
      }
      
      public final Network network(OkHttpClient paramAnonymousOkHttpClient)
      {
        return paramAnonymousOkHttpClient.network;
      }
      
      public final Transport newTransport(Connection paramAnonymousConnection, HttpEngine paramAnonymousHttpEngine)
        throws IOException
      {
        if (paramAnonymousConnection.spdyConnection != null) {
          return new SpdyTransport(paramAnonymousHttpEngine, paramAnonymousConnection.spdyConnection);
        }
        return new HttpTransport(paramAnonymousHttpEngine, paramAnonymousConnection.httpConnection);
      }
      
      public final void recycle(ConnectionPool paramAnonymousConnectionPool, Connection paramAnonymousConnection)
      {
        if ((!paramAnonymousConnection.isSpdy()) && (paramAnonymousConnection.clearOwner()))
        {
          if (!paramAnonymousConnection.isAlive()) {
            Util.closeQuietly(paramAnonymousConnection.socket);
          }
        }
        else {
          return;
        }
        try
        {
          Platform.get().untagSocket(paramAnonymousConnection.socket);
          try
          {
            paramAnonymousConnectionPool.addConnection(paramAnonymousConnection);
            paramAnonymousConnection.recycleCount = (1 + paramAnonymousConnection.recycleCount);
            if (paramAnonymousConnection.spdyConnection != null) {
              throw new IllegalStateException("spdyConnection != null");
            }
          }
          finally {}
          paramAnonymousConnection.idleStartTimeNs = System.nanoTime();
        }
        catch (SocketException localSocketException)
        {
          Platform.get();
          Platform.logW("Unable to untagSocket(): " + localSocketException);
          Util.closeQuietly(paramAnonymousConnection.socket);
          return;
        }
      }
      
      public final int recycleCount(Connection paramAnonymousConnection)
      {
        return paramAnonymousConnection.recycleCount;
      }
      
      public final RouteDatabase routeDatabase(OkHttpClient paramAnonymousOkHttpClient)
      {
        return paramAnonymousOkHttpClient.routeDatabase;
      }
      
      public final void setOwner(Connection paramAnonymousConnection, HttpEngine paramAnonymousHttpEngine)
      {
        paramAnonymousConnection.setOwner(paramAnonymousHttpEngine);
      }
      
      public final void setProtocol(Connection paramAnonymousConnection, Protocol paramAnonymousProtocol)
      {
        if (paramAnonymousProtocol == null) {
          throw new IllegalArgumentException("protocol == null");
        }
        paramAnonymousConnection.protocol = paramAnonymousProtocol;
      }
    };
  }
  
  public OkHttpClient()
  {
    this.routeDatabase = new RouteDatabase();
    this.dispatcher = new Dispatcher();
  }
  
  OkHttpClient(OkHttpClient paramOkHttpClient)
  {
    this.routeDatabase = paramOkHttpClient.routeDatabase;
    this.dispatcher = paramOkHttpClient.dispatcher;
    this.proxy = paramOkHttpClient.proxy;
    this.protocols = paramOkHttpClient.protocols;
    this.connectionSpecs = paramOkHttpClient.connectionSpecs;
    this.interceptors.addAll(paramOkHttpClient.interceptors);
    this.networkInterceptors.addAll(paramOkHttpClient.networkInterceptors);
    this.proxySelector = paramOkHttpClient.proxySelector;
    this.cookieHandler = paramOkHttpClient.cookieHandler;
    this.cache = paramOkHttpClient.cache;
    if (this.cache != null) {}
    for (InternalCache localInternalCache = this.cache.internalCache;; localInternalCache = paramOkHttpClient.internalCache)
    {
      this.internalCache = localInternalCache;
      this.socketFactory = paramOkHttpClient.socketFactory;
      this.sslSocketFactory = paramOkHttpClient.sslSocketFactory;
      this.hostnameVerifier = paramOkHttpClient.hostnameVerifier;
      this.certificatePinner = paramOkHttpClient.certificatePinner;
      this.authenticator = paramOkHttpClient.authenticator;
      this.connectionPool = paramOkHttpClient.connectionPool;
      this.network = paramOkHttpClient.network;
      this.followSslRedirects = paramOkHttpClient.followSslRedirects;
      this.followRedirects = paramOkHttpClient.followRedirects;
      this.retryOnConnectionFailure = paramOkHttpClient.retryOnConnectionFailure;
      this.connectTimeout = paramOkHttpClient.connectTimeout;
      this.readTimeout = paramOkHttpClient.readTimeout;
      this.writeTimeout = paramOkHttpClient.writeTimeout;
      return;
    }
  }
  
  public final OkHttpClient clone()
  {
    try
    {
      OkHttpClient localOkHttpClient = (OkHttpClient)super.clone();
      return localOkHttpClient;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      throw new AssertionError();
    }
  }
  
  /* Error */
  final SSLSocketFactory getDefaultSSLSocketFactory()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: getstatic 188	com/squareup/okhttp/OkHttpClient:defaultSslSocketFactory	Ljavax/net/ssl/SSLSocketFactory;
    //   5: astore_2
    //   6: aload_2
    //   7: ifnonnull +26 -> 33
    //   10: ldc 190
    //   12: invokestatic 196	javax/net/ssl/SSLContext:getInstance	(Ljava/lang/String;)Ljavax/net/ssl/SSLContext;
    //   15: astore 5
    //   17: aload 5
    //   19: aconst_null
    //   20: aconst_null
    //   21: aconst_null
    //   22: invokevirtual 200	javax/net/ssl/SSLContext:init	([Ljavax/net/ssl/KeyManager;[Ljavax/net/ssl/TrustManager;Ljava/security/SecureRandom;)V
    //   25: aload 5
    //   27: invokevirtual 203	javax/net/ssl/SSLContext:getSocketFactory	()Ljavax/net/ssl/SSLSocketFactory;
    //   30: putstatic 188	com/squareup/okhttp/OkHttpClient:defaultSslSocketFactory	Ljavax/net/ssl/SSLSocketFactory;
    //   33: getstatic 188	com/squareup/okhttp/OkHttpClient:defaultSslSocketFactory	Ljavax/net/ssl/SSLSocketFactory;
    //   36: astore_3
    //   37: aload_0
    //   38: monitorexit
    //   39: aload_3
    //   40: areturn
    //   41: astore 4
    //   43: new 179	java/lang/AssertionError
    //   46: dup
    //   47: invokespecial 180	java/lang/AssertionError:<init>	()V
    //   50: athrow
    //   51: astore_1
    //   52: aload_0
    //   53: monitorexit
    //   54: aload_1
    //   55: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	OkHttpClient
    //   51	4	1	localObject	Object
    //   5	2	2	localSSLSocketFactory1	SSLSocketFactory
    //   36	4	3	localSSLSocketFactory2	SSLSocketFactory
    //   41	1	4	localGeneralSecurityException	java.security.GeneralSecurityException
    //   15	11	5	localSSLContext	javax.net.ssl.SSLContext
    // Exception table:
    //   from	to	target	type
    //   10	33	41	java/security/GeneralSecurityException
    //   2	6	51	finally
    //   10	33	51	finally
    //   33	37	51	finally
    //   43	51	51	finally
  }
  
  public final Call newCall(Request paramRequest)
  {
    return new Call(this, paramRequest);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.OkHttpClient
 * JD-Core Version:    0.7.0.1
 */