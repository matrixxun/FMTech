package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Network;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLProtocolException;

public final class RouteSelector
{
  final Address address;
  public final OkHttpClient client;
  List<ConnectionSpec> connectionSpecs = Collections.emptyList();
  private List<InetSocketAddress> inetSocketAddresses = Collections.emptyList();
  InetSocketAddress lastInetSocketAddress;
  Proxy lastProxy;
  private ConnectionSpec lastSpec;
  private final Network network;
  private int nextInetSocketAddressIndex;
  private int nextProxyIndex;
  int nextSpecIndex;
  private final ConnectionPool pool;
  private final List<Route> postponedRoutes = new ArrayList();
  private List<Proxy> proxies = Collections.emptyList();
  public final Request request;
  final RouteDatabase routeDatabase;
  final URI uri;
  
  public RouteSelector(Address paramAddress, URI paramURI, OkHttpClient paramOkHttpClient, Request paramRequest)
  {
    this.address = paramAddress;
    this.uri = paramURI;
    this.client = paramOkHttpClient;
    this.pool = paramOkHttpClient.connectionPool;
    this.routeDatabase = Internal.instance.routeDatabase(paramOkHttpClient);
    this.network = Internal.instance.network(paramOkHttpClient);
    this.request = paramRequest;
    Proxy localProxy = paramAddress.proxy;
    if (localProxy != null) {
      this.proxies = Collections.singletonList(localProxy);
    }
    for (;;)
    {
      this.nextProxyIndex = 0;
      return;
      this.proxies = new ArrayList();
      List localList = this.client.proxySelector.select(paramURI);
      if (localList != null) {
        this.proxies.addAll(localList);
      }
      this.proxies.removeAll(Collections.singleton(Proxy.NO_PROXY));
      this.proxies.add(Proxy.NO_PROXY);
    }
  }
  
  private void resetConnectionSpecs()
  {
    this.connectionSpecs = new ArrayList();
    List localList = this.address.connectionSpecs;
    int i = 0;
    int j = localList.size();
    while (i < j)
    {
      ConnectionSpec localConnectionSpec = (ConnectionSpec)localList.get(i);
      if (this.request.isHttps() == localConnectionSpec.tls) {
        this.connectionSpecs.add(localConnectionSpec);
      }
      i++;
    }
    this.nextSpecIndex = 0;
  }
  
  private void resetNextInetSocketAddress(Proxy paramProxy)
    throws UnknownHostException
  {
    this.inetSocketAddresses = new ArrayList();
    String str;
    int i;
    InetAddress[] arrayOfInetAddress;
    int j;
    int k;
    if ((paramProxy.type() == Proxy.Type.DIRECT) || (paramProxy.type() == Proxy.Type.SOCKS))
    {
      str = this.address.uriHost;
      i = Util.getEffectivePort(this.uri);
      arrayOfInetAddress = this.network.resolveInetAddresses(str);
      j = arrayOfInetAddress.length;
      k = 0;
    }
    while (k < j)
    {
      InetAddress localInetAddress1 = arrayOfInetAddress[k];
      this.inetSocketAddresses.add(new InetSocketAddress(localInetAddress1, i));
      k++;
      continue;
      SocketAddress localSocketAddress = paramProxy.address();
      if (!(localSocketAddress instanceof InetSocketAddress)) {
        throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + localSocketAddress.getClass());
      }
      InetSocketAddress localInetSocketAddress = (InetSocketAddress)localSocketAddress;
      InetAddress localInetAddress2 = localInetSocketAddress.getAddress();
      if (localInetAddress2 == null) {}
      for (str = localInetSocketAddress.getHostName();; str = localInetAddress2.getHostAddress())
      {
        i = localInetSocketAddress.getPort();
        break;
      }
    }
    this.nextInetSocketAddressIndex = 0;
  }
  
  private boolean shouldSendTlsFallbackIndicator(ConnectionSpec paramConnectionSpec)
  {
    Object localObject = this.connectionSpecs.get(0);
    boolean bool1 = false;
    if (paramConnectionSpec != localObject)
    {
      boolean bool2 = paramConnectionSpec.tls;
      bool1 = false;
      if (bool2) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public final void connectFailed(Connection paramConnection, IOException paramIOException)
  {
    if (Internal.instance.recycleCount(paramConnection) > 0) {}
    for (;;)
    {
      return;
      Route localRoute1 = paramConnection.route;
      if ((localRoute1.proxy.type() != Proxy.Type.DIRECT) && (this.address.proxySelector != null)) {
        this.address.proxySelector.connectFailed(this.uri, localRoute1.proxy.address(), paramIOException);
      }
      this.routeDatabase.failed(localRoute1);
      if ((!(paramIOException instanceof SSLHandshakeException)) && (!(paramIOException instanceof SSLProtocolException))) {
        while (this.nextSpecIndex < this.connectionSpecs.size())
        {
          List localList = this.connectionSpecs;
          int i = this.nextSpecIndex;
          this.nextSpecIndex = (i + 1);
          ConnectionSpec localConnectionSpec = (ConnectionSpec)localList.get(i);
          boolean bool = shouldSendTlsFallbackIndicator(localConnectionSpec);
          Route localRoute2 = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, localConnectionSpec, bool);
          this.routeDatabase.failed(localRoute2);
        }
      }
    }
  }
  
  public final boolean hasNextConnectionSpec()
  {
    return this.nextSpecIndex < this.connectionSpecs.size();
  }
  
  public final boolean hasNextInetSocketAddress()
  {
    return this.nextInetSocketAddressIndex < this.inetSocketAddresses.size();
  }
  
  public final boolean hasNextPostponed()
  {
    return !this.postponedRoutes.isEmpty();
  }
  
  public final boolean hasNextProxy()
  {
    return this.nextProxyIndex < this.proxies.size();
  }
  
  public final Connection nextUnconnected()
    throws IOException
  {
    Route localRoute;
    for (;;)
    {
      Connection localConnection = this.pool.get(this.address);
      if (localConnection != null)
      {
        if ((this.request.method.equals("GET")) || (Internal.instance.isReadable(localConnection))) {
          return localConnection;
        }
        localConnection.socket.close();
      }
      else
      {
        if (!hasNextConnectionSpec())
        {
          if (!hasNextInetSocketAddress())
          {
            if (!hasNextProxy())
            {
              if (!hasNextPostponed()) {
                throw new NoSuchElementException();
              }
              return new Connection(this.pool, (Route)this.postponedRoutes.remove(0));
            }
            if (!hasNextProxy()) {
              throw new SocketException("No route to " + this.address.uriHost + "; exhausted proxy configurations: " + this.proxies);
            }
            List localList3 = this.proxies;
            int k = this.nextProxyIndex;
            this.nextProxyIndex = (k + 1);
            Proxy localProxy = (Proxy)localList3.get(k);
            resetNextInetSocketAddress(localProxy);
            this.lastProxy = localProxy;
          }
          if (!hasNextInetSocketAddress()) {
            throw new SocketException("No route to " + this.address.uriHost + "; exhausted inet socket addresses: " + this.inetSocketAddresses);
          }
          List localList2 = this.inetSocketAddresses;
          int j = this.nextInetSocketAddressIndex;
          this.nextInetSocketAddressIndex = (j + 1);
          InetSocketAddress localInetSocketAddress = (InetSocketAddress)localList2.get(j);
          resetConnectionSpecs();
          this.lastInetSocketAddress = localInetSocketAddress;
        }
        if (!hasNextConnectionSpec()) {
          throw new SocketException("No route to " + this.address.uriHost + "; exhausted connection specs: " + this.connectionSpecs);
        }
        List localList1 = this.connectionSpecs;
        int i = this.nextSpecIndex;
        this.nextSpecIndex = (i + 1);
        this.lastSpec = ((ConnectionSpec)localList1.get(i));
        boolean bool = shouldSendTlsFallbackIndicator(this.lastSpec);
        localRoute = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, this.lastSpec, bool);
        if (!this.routeDatabase.shouldPostpone(localRoute)) {
          break;
        }
        this.postponedRoutes.add(localRoute);
      }
    }
    return new Connection(this.pool, localRoute);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.RouteSelector
 * JD-Core Version:    0.7.0.1
 */