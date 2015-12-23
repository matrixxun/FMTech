package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.Authenticator.RequestorType;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.List;

public final class AuthenticatorAdapter
  implements com.squareup.okhttp.Authenticator
{
  public static final com.squareup.okhttp.Authenticator INSTANCE = new AuthenticatorAdapter();
  
  private static InetAddress getConnectToInetAddress(Proxy paramProxy, URL paramURL)
    throws IOException
  {
    if ((paramProxy != null) && (paramProxy.type() != Proxy.Type.DIRECT)) {
      return ((InetSocketAddress)paramProxy.address()).getAddress();
    }
    return InetAddress.getByName(paramURL.getHost());
  }
  
  public final Request authenticate(Proxy paramProxy, Response paramResponse)
    throws IOException
  {
    List localList = paramResponse.challenges();
    Request localRequest = paramResponse.request;
    URL localURL = localRequest.url();
    int i = 0;
    int j = localList.size();
    while (i < j)
    {
      Challenge localChallenge = (Challenge)localList.get(i);
      if ("Basic".equalsIgnoreCase(localChallenge.scheme))
      {
        PasswordAuthentication localPasswordAuthentication = java.net.Authenticator.requestPasswordAuthentication(localURL.getHost(), getConnectToInetAddress(paramProxy, localURL), localURL.getPort(), localURL.getProtocol(), localChallenge.realm, localChallenge.scheme, localURL, Authenticator.RequestorType.SERVER);
        if (localPasswordAuthentication != null)
        {
          String str = Credentials.basic(localPasswordAuthentication.getUserName(), new String(localPasswordAuthentication.getPassword()));
          return localRequest.newBuilder().header("Authorization", str).build();
        }
      }
      i++;
    }
    return null;
  }
  
  public final Request authenticateProxy(Proxy paramProxy, Response paramResponse)
    throws IOException
  {
    List localList = paramResponse.challenges();
    Request localRequest = paramResponse.request;
    URL localURL = localRequest.url();
    int i = 0;
    int j = localList.size();
    while (i < j)
    {
      Challenge localChallenge = (Challenge)localList.get(i);
      if ("Basic".equalsIgnoreCase(localChallenge.scheme))
      {
        InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramProxy.address();
        PasswordAuthentication localPasswordAuthentication = java.net.Authenticator.requestPasswordAuthentication(localInetSocketAddress.getHostName(), getConnectToInetAddress(paramProxy, localURL), localInetSocketAddress.getPort(), localURL.getProtocol(), localChallenge.realm, localChallenge.scheme, localURL, Authenticator.RequestorType.PROXY);
        if (localPasswordAuthentication != null)
        {
          String str = Credentials.basic(localPasswordAuthentication.getUserName(), new String(localPasswordAuthentication.getPassword()));
          return localRequest.newBuilder().header("Proxy-Authorization", str).build();
        }
      }
      i++;
    }
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.AuthenticatorAdapter
 * JD-Core Version:    0.7.0.1
 */