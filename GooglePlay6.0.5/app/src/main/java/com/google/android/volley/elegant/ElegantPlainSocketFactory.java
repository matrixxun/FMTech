package com.google.android.volley.elegant;

import com.android.volley.VolleyLog;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public final class ElegantPlainSocketFactory
  implements SocketFactory
{
  private static final ElegantPlainSocketFactory DEFAULT_FACTORY = new ElegantPlainSocketFactory((byte)0);
  private final HostNameResolver nameResolver = null;
  
  private ElegantPlainSocketFactory() {}
  
  private ElegantPlainSocketFactory(byte paramByte)
  {
    this();
  }
  
  public static ElegantPlainSocketFactory getSocketFactory()
  {
    return DEFAULT_FACTORY;
  }
  
  public final Socket connectSocket(Socket paramSocket, String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2, HttpParams paramHttpParams)
    throws IOException
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Target host may not be null.");
    }
    if (paramHttpParams == null) {
      throw new IllegalArgumentException("Parameters may not be null.");
    }
    if (paramSocket == null) {
      paramSocket = createSocket();
    }
    if ((paramInetAddress != null) || (paramInt2 > 0))
    {
      if (paramInt2 < 0) {
        paramInt2 = 0;
      }
      paramSocket.bind(new InetSocketAddress(paramInetAddress, paramInt2));
    }
    int i = HttpConnectionParams.getConnectionTimeout(paramHttpParams);
    if (this.nameResolver != null) {}
    for (localInetSocketAddress = new InetSocketAddress(this.nameResolver.resolve(paramString), paramInt1);; localInetSocketAddress = new InetSocketAddress(paramString, paramInt1)) {
      try
      {
        long l1 = System.currentTimeMillis();
        paramSocket.connect(localInetSocketAddress, i);
        long l2 = System.currentTimeMillis() - l1;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramString;
        arrayOfObject[1] = Long.valueOf(l2);
        VolleyLog.v("Established connection to [host=%s] in [%s ms]", arrayOfObject);
        return paramSocket;
      }
      catch (SocketTimeoutException localSocketTimeoutException)
      {
        throw new ConnectTimeoutException("Connect to " + localInetSocketAddress + " timed out");
      }
    }
  }
  
  public final Socket createSocket()
  {
    return new Socket();
  }
  
  public final boolean equals(Object paramObject)
  {
    return paramObject == this;
  }
  
  public final int hashCode()
  {
    return ElegantPlainSocketFactory.class.hashCode();
  }
  
  public final boolean isSecure(Socket paramSocket)
    throws IllegalArgumentException
  {
    if (paramSocket == null) {
      throw new IllegalArgumentException("Socket may not be null.");
    }
    if (paramSocket.getClass() != Socket.class) {
      throw new IllegalArgumentException("Socket not created by this factory.");
    }
    if (paramSocket.isClosed()) {
      throw new IllegalArgumentException("Socket is closed.");
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.elegant.ElegantPlainSocketFactory
 * JD-Core Version:    0.7.0.1
 */