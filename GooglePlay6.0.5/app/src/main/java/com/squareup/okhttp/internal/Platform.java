package com.squareup.okhttp.internal;

import com.squareup.okhttp.Protocol;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import okio.Buffer;

public class Platform
{
  private static final Platform PLATFORM = ;
  
  private static Platform findPlatform()
  {
    try
    {
      Class.forName("com.android.org.conscrypt.OpenSSLSocketImpl");
      localMethod1 = null;
    }
    catch (ClassNotFoundException localClassNotFoundException1)
    {
      for (;;)
      {
        try
        {
          Class localClass5 = Class.forName("android.net.TrafficStats");
          localMethod1 = localClass5.getMethod("tagSocket", new Class[] { Socket.class });
          Method localMethod3 = localClass5.getMethod("untagSocket", new Class[] { Socket.class });
          localMethod2 = localMethod3;
        }
        catch (NoSuchMethodException localNoSuchMethodException2)
        {
          Method localMethod1;
          Class localClass1;
          Class localClass2;
          Class localClass3;
          Class localClass4;
          JdkWithJettyBootPlatform localJdkWithJettyBootPlatform;
          localMethod2 = null;
          continue;
        }
        catch (ClassNotFoundException localClassNotFoundException4)
        {
          Method localMethod2 = null;
          continue;
        }
        try
        {
          return new Android(localMethod1, localMethod2, (byte)0);
        }
        catch (ClassNotFoundException localClassNotFoundException2)
        {
          try
          {
            localClass1 = Class.forName("org.eclipse.jetty.alpn.ALPN");
            localClass2 = Class.forName("org.eclipse.jetty.alpn.ALPN" + "$Provider");
            localClass3 = Class.forName("org.eclipse.jetty.alpn.ALPN" + "$ClientProvider");
            localClass4 = Class.forName("org.eclipse.jetty.alpn.ALPN" + "$ServerProvider");
            localJdkWithJettyBootPlatform = new JdkWithJettyBootPlatform(localClass1.getMethod("put", new Class[] { SSLSocket.class, localClass2 }), localClass1.getMethod("get", new Class[] { SSLSocket.class }), localClass1.getMethod("remove", new Class[] { SSLSocket.class }), localClass3, localClass4);
            return localJdkWithJettyBootPlatform;
          }
          catch (ClassNotFoundException localClassNotFoundException3)
          {
            return new Platform();
          }
          catch (NoSuchMethodException localNoSuchMethodException1)
          {
            continue;
          }
        }
        localClassNotFoundException1 = localClassNotFoundException1;
        Class.forName("org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl");
      }
    }
  }
  
  public static Platform get()
  {
    return PLATFORM;
  }
  
  public static String getPrefix()
  {
    return "OkHttp";
  }
  
  public static void logW(String paramString)
  {
    System.out.println(paramString);
  }
  
  public static URI toUriLenient(URL paramURL)
    throws URISyntaxException
  {
    return paramURL.toURI();
  }
  
  public void afterHandshake(SSLSocket paramSSLSocket) {}
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList) {}
  
  public void connectSocket(Socket paramSocket, InetSocketAddress paramInetSocketAddress, int paramInt)
    throws IOException
  {
    paramSocket.connect(paramInetSocketAddress, paramInt);
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    return null;
  }
  
  public void tagSocket(Socket paramSocket)
    throws SocketException
  {}
  
  public void untagSocket(Socket paramSocket)
    throws SocketException
  {}
  
  private static final class Android
    extends Platform
  {
    private static final OptionalMethod<Socket> GET_ALPN_SELECTED_PROTOCOL = new OptionalMethod([B.class, "getAlpnSelectedProtocol", new Class[0]);
    private static final OptionalMethod<Socket> GET_NPN_SELECTED_PROTOCOL;
    private static final OptionalMethod<Socket> SET_ALPN_PROTOCOLS = new OptionalMethod(null, "setAlpnProtocols", new Class[] { [B.class });
    private static final OptionalMethod<Socket> SET_HOSTNAME;
    private static final OptionalMethod<Socket> SET_NPN_PROTOCOLS;
    private static final OptionalMethod<Socket> SET_USE_SESSION_TICKETS;
    private final Method trafficStatsTagSocket;
    private final Method trafficStatsUntagSocket;
    
    static
    {
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = Boolean.TYPE;
      SET_USE_SESSION_TICKETS = new OptionalMethod(null, "setUseSessionTickets", arrayOfClass);
      SET_HOSTNAME = new OptionalMethod(null, "setHostname", new Class[] { String.class });
      GET_NPN_SELECTED_PROTOCOL = new OptionalMethod([B.class, "getNpnSelectedProtocol", new Class[0]);
      SET_NPN_PROTOCOLS = new OptionalMethod(null, "setNpnProtocols", new Class[] { [B.class });
    }
    
    private Android(Method paramMethod1, Method paramMethod2)
    {
      this.trafficStatsTagSocket = paramMethod1;
      this.trafficStatsUntagSocket = paramMethod2;
    }
    
    private static String getSelectedProtocol(SSLSocket paramSSLSocket, OptionalMethod<Socket> paramOptionalMethod)
    {
      if (!paramOptionalMethod.isSupported(paramSSLSocket)) {
        return null;
      }
      byte[] arrayOfByte = (byte[])paramOptionalMethod.invokeWithoutCheckedException(paramSSLSocket, new Object[0]);
      if (arrayOfByte != null) {
        return new String(arrayOfByte, Util.UTF_8);
      }
      return null;
    }
    
    public final void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList)
    {
      if (paramString != null)
      {
        OptionalMethod localOptionalMethod = SET_USE_SESSION_TICKETS;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Boolean.valueOf(true);
        localOptionalMethod.invokeOptionalWithoutCheckedException(paramSSLSocket, arrayOfObject2);
        SET_HOSTNAME.invokeOptionalWithoutCheckedException(paramSSLSocket, new Object[] { paramString });
      }
      boolean bool1 = SET_NPN_PROTOCOLS.isSupported(paramSSLSocket);
      boolean bool2 = SET_ALPN_PROTOCOLS.isSupported(paramSSLSocket);
      if ((!bool1) && (!bool2)) {}
      Object[] arrayOfObject1;
      do
      {
        return;
        arrayOfObject1 = new Object[1];
        Buffer localBuffer = new Buffer();
        int i = paramList.size();
        for (int j = 0; j < i; j++)
        {
          Protocol localProtocol = (Protocol)paramList.get(j);
          if (localProtocol != Protocol.HTTP_1_0)
          {
            localBuffer.writeByte(localProtocol.toString().length());
            localBuffer.writeUtf8(localProtocol.toString());
          }
        }
        arrayOfObject1[0] = localBuffer.readByteArray();
        if (bool1) {
          SET_NPN_PROTOCOLS.invokeWithoutCheckedException(paramSSLSocket, arrayOfObject1);
        }
      } while (!bool2);
      SET_ALPN_PROTOCOLS.invokeWithoutCheckedException(paramSSLSocket, arrayOfObject1);
    }
    
    public final void connectSocket(Socket paramSocket, InetSocketAddress paramInetSocketAddress, int paramInt)
      throws IOException
    {
      try
      {
        paramSocket.connect(paramInetSocketAddress, paramInt);
        return;
      }
      catch (SecurityException localSecurityException)
      {
        IOException localIOException = new IOException("Exception in connect");
        localIOException.initCause(localSecurityException);
        throw localIOException;
      }
    }
    
    public final String getSelectedProtocol(SSLSocket paramSSLSocket)
    {
      String str = getSelectedProtocol(paramSSLSocket, GET_NPN_SELECTED_PROTOCOL);
      if (str != null) {
        return str;
      }
      return getSelectedProtocol(paramSSLSocket, GET_ALPN_SELECTED_PROTOCOL);
    }
    
    public final void tagSocket(Socket paramSocket)
      throws SocketException
    {
      if (this.trafficStatsTagSocket == null) {
        return;
      }
      try
      {
        this.trafficStatsTagSocket.invoke(null, new Object[] { paramSocket });
        return;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new RuntimeException(localIllegalAccessException);
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new RuntimeException(localInvocationTargetException.getCause());
      }
    }
    
    public final void untagSocket(Socket paramSocket)
      throws SocketException
    {
      if (this.trafficStatsUntagSocket == null) {
        return;
      }
      try
      {
        this.trafficStatsUntagSocket.invoke(null, new Object[] { paramSocket });
        return;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new RuntimeException(localIllegalAccessException);
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new RuntimeException(localInvocationTargetException.getCause());
      }
    }
  }
  
  private static final class JdkWithJettyBootPlatform
    extends Platform
  {
    private final Class<?> clientProviderClass;
    private final Method getMethod;
    private final Method putMethod;
    private final Method removeMethod;
    private final Class<?> serverProviderClass;
    
    public JdkWithJettyBootPlatform(Method paramMethod1, Method paramMethod2, Method paramMethod3, Class<?> paramClass1, Class<?> paramClass2)
    {
      this.putMethod = paramMethod1;
      this.getMethod = paramMethod2;
      this.removeMethod = paramMethod3;
      this.clientProviderClass = paramClass1;
      this.serverProviderClass = paramClass2;
    }
    
    public final void afterHandshake(SSLSocket paramSSLSocket)
    {
      try
      {
        this.removeMethod.invoke(null, new Object[] { paramSSLSocket });
        return;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new AssertionError();
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new AssertionError();
      }
    }
    
    public final void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList)
    {
      ArrayList localArrayList = new ArrayList(paramList.size());
      int i = 0;
      int j = paramList.size();
      while (i < j)
      {
        Protocol localProtocol = (Protocol)paramList.get(i);
        if (localProtocol != Protocol.HTTP_1_0) {
          localArrayList.add(localProtocol.toString());
        }
        i++;
      }
      try
      {
        ClassLoader localClassLoader = Platform.class.getClassLoader();
        Class[] arrayOfClass = new Class[2];
        arrayOfClass[0] = this.clientProviderClass;
        arrayOfClass[1] = this.serverProviderClass;
        Object localObject = Proxy.newProxyInstance(localClassLoader, arrayOfClass, new Platform.JettyNegoProvider(localArrayList));
        this.putMethod.invoke(null, new Object[] { paramSSLSocket, localObject });
        return;
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new AssertionError(localInvocationTargetException);
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new AssertionError(localIllegalAccessException);
      }
    }
    
    public final String getSelectedProtocol(SSLSocket paramSSLSocket)
    {
      try
      {
        Platform.JettyNegoProvider localJettyNegoProvider = (Platform.JettyNegoProvider)Proxy.getInvocationHandler(this.getMethod.invoke(null, new Object[] { paramSSLSocket }));
        if ((!Platform.JettyNegoProvider.access$100(localJettyNegoProvider)) && (Platform.JettyNegoProvider.access$200(localJettyNegoProvider) == null))
        {
          Internal.logger.log(Level.INFO, "ALPN callback dropped: SPDY and HTTP/2 are disabled. Is alpn-boot on the boot class path?");
          return null;
        }
        if (!Platform.JettyNegoProvider.access$100(localJettyNegoProvider))
        {
          String str = Platform.JettyNegoProvider.access$200(localJettyNegoProvider);
          return str;
        }
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new AssertionError();
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new AssertionError();
      }
      return null;
    }
  }
  
  private static final class JettyNegoProvider
    implements InvocationHandler
  {
    private final List<String> protocols;
    private String selected;
    private boolean unsupported;
    
    public JettyNegoProvider(List<String> paramList)
    {
      this.protocols = paramList;
    }
    
    public final Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
      throws Throwable
    {
      String str1 = paramMethod.getName();
      Class localClass = paramMethod.getReturnType();
      if (paramArrayOfObject == null) {
        paramArrayOfObject = Util.EMPTY_STRING_ARRAY;
      }
      if ((str1.equals("supports")) && (Boolean.TYPE == localClass)) {
        return Boolean.valueOf(true);
      }
      if ((str1.equals("unsupported")) && (Void.TYPE == localClass))
      {
        this.unsupported = true;
        return null;
      }
      if ((str1.equals("protocols")) && (paramArrayOfObject.length == 0)) {
        return this.protocols;
      }
      if (((str1.equals("selectProtocol")) || (str1.equals("select"))) && (String.class == localClass) && (paramArrayOfObject.length == 1) && ((paramArrayOfObject[0] instanceof List)))
      {
        List localList = (List)paramArrayOfObject[0];
        int i = 0;
        int j = localList.size();
        while (i < j)
        {
          if (this.protocols.contains(localList.get(i)))
          {
            String str3 = (String)localList.get(i);
            this.selected = str3;
            return str3;
          }
          i++;
        }
        String str2 = (String)this.protocols.get(0);
        this.selected = str2;
        return str2;
      }
      if (((str1.equals("protocolSelected")) || (str1.equals("selected"))) && (paramArrayOfObject.length == 1))
      {
        this.selected = ((String)paramArrayOfObject[0]);
        return null;
      }
      return paramMethod.invoke(this, paramArrayOfObject);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.Platform
 * JD-Core Version:    0.7.0.1
 */