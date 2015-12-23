package com.google.android.volley;

import android.content.Context;
import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import android.os.Looper;
import android.util.Log;
import com.google.android.volley.elegant.ElegantPlainSocketFactory;
import com.google.android.volley.elegant.ElegantRequestDirector;
import com.google.android.volley.elegant.ElegantThreadSafeConnManager;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RequestDirector;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.params.ConnManagerParamBean;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;

public final class AndroidHttpClient
  implements HttpClient
{
  public static long DEFAULT_SYNC_MIN_GZIP_BYTES = 256L;
  private static final HttpRequestInterceptor sThreadCheckInterceptor = new HttpRequestInterceptor()
  {
    public final void process(HttpRequest paramAnonymousHttpRequest, HttpContext paramAnonymousHttpContext)
    {
      if ((Looper.myLooper() != null) && (Looper.myLooper() == Looper.getMainLooper())) {
        throw new RuntimeException("This thread forbids HTTP requests");
      }
    }
  };
  private static String[] textContentTypes = { "text/", "application/xml", "application/json" };
  volatile LoggingConfiguration curlConfiguration;
  private final HttpClient delegate;
  private RuntimeException mLeakedException = new IllegalStateException("AndroidHttpClient created and never closed");
  
  private AndroidHttpClient(ClientConnectionManager paramClientConnectionManager, HttpParams paramHttpParams)
  {
    this.delegate = new DefaultHttpClient(paramClientConnectionManager, paramHttpParams)
    {
      protected final RequestDirector createClientRequestDirector(HttpRequestExecutor paramAnonymousHttpRequestExecutor, ClientConnectionManager paramAnonymousClientConnectionManager, ConnectionReuseStrategy paramAnonymousConnectionReuseStrategy, ConnectionKeepAliveStrategy paramAnonymousConnectionKeepAliveStrategy, HttpRoutePlanner paramAnonymousHttpRoutePlanner, HttpProcessor paramAnonymousHttpProcessor, HttpRequestRetryHandler paramAnonymousHttpRequestRetryHandler, RedirectHandler paramAnonymousRedirectHandler, AuthenticationHandler paramAnonymousAuthenticationHandler1, AuthenticationHandler paramAnonymousAuthenticationHandler2, UserTokenHandler paramAnonymousUserTokenHandler, HttpParams paramAnonymousHttpParams)
      {
        return new ElegantRequestDirector(paramAnonymousHttpRequestExecutor, paramAnonymousClientConnectionManager, paramAnonymousConnectionReuseStrategy, paramAnonymousConnectionKeepAliveStrategy, paramAnonymousHttpRoutePlanner, paramAnonymousHttpProcessor, paramAnonymousHttpRequestRetryHandler, paramAnonymousRedirectHandler, paramAnonymousAuthenticationHandler1, paramAnonymousAuthenticationHandler2, paramAnonymousUserTokenHandler, paramAnonymousHttpParams);
      }
      
      protected final HttpContext createHttpContext()
      {
        BasicHttpContext localBasicHttpContext = new BasicHttpContext();
        localBasicHttpContext.setAttribute("http.authscheme-registry", getAuthSchemes());
        localBasicHttpContext.setAttribute("http.cookiespec-registry", getCookieSpecs());
        localBasicHttpContext.setAttribute("http.auth.credentials-provider", getCredentialsProvider());
        return localBasicHttpContext;
      }
      
      protected final BasicHttpProcessor createHttpProcessor()
      {
        BasicHttpProcessor localBasicHttpProcessor = super.createHttpProcessor();
        localBasicHttpProcessor.addRequestInterceptor(AndroidHttpClient.sThreadCheckInterceptor);
        localBasicHttpProcessor.addRequestInterceptor(new AndroidHttpClient.CurlLogger(AndroidHttpClient.this, (byte)0));
        return localBasicHttpProcessor;
      }
    };
  }
  
  private static org.apache.http.conn.ssl.SSLSocketFactory getSocketFactory(SSLSessionCache paramSSLSessionCache)
  {
    javax.net.ssl.SSLSocketFactory localSSLSocketFactory = SSLCertificateSocketFactory.getDefault(5000, paramSSLSessionCache);
    try
    {
      org.apache.http.conn.ssl.SSLSocketFactory localSSLSocketFactory1 = (org.apache.http.conn.ssl.SSLSocketFactory)org.apache.http.conn.ssl.SSLSocketFactory.class.getConstructor(new Class[] { javax.net.ssl.SSLSocketFactory.class }).newInstance(new Object[] { localSSLSocketFactory });
      return localSSLSocketFactory1;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      throw new IllegalStateException(localNoSuchMethodException);
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new IllegalStateException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new IllegalStateException(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new IllegalStateException(localInvocationTargetException);
    }
  }
  
  private static boolean isBinaryContent(HttpUriRequest paramHttpUriRequest)
  {
    Header[] arrayOfHeader1 = paramHttpUriRequest.getHeaders("content-encoding");
    int i1;
    if (arrayOfHeader1 != null)
    {
      int n = arrayOfHeader1.length;
      i1 = 0;
      if (i1 < n) {
        if (!"gzip".equalsIgnoreCase(arrayOfHeader1[i1].getValue())) {}
      }
    }
    for (;;)
    {
      return true;
      i1++;
      break;
      Header[] arrayOfHeader2 = paramHttpUriRequest.getHeaders("content-type");
      if (arrayOfHeader2 != null)
      {
        int i = arrayOfHeader2.length;
        for (int j = 0; j < i; j++)
        {
          Header localHeader = arrayOfHeader2[j];
          for (String str : textContentTypes) {
            if (localHeader.getValue().startsWith(str)) {
              return false;
            }
          }
        }
      }
    }
  }
  
  public static AndroidHttpClient newInstance(String paramString, Context paramContext)
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setStaleCheckingEnabled(localBasicHttpParams, false);
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 20000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 20000);
    HttpConnectionParams.setSocketBufferSize(localBasicHttpParams, 8192);
    HttpClientParams.setRedirecting(localBasicHttpParams, false);
    if (paramContext == null) {}
    for (SSLSessionCache localSSLSessionCache = null;; localSSLSessionCache = new SSLSessionCache(paramContext))
    {
      HttpProtocolParams.setUserAgent(localBasicHttpParams, paramString);
      SchemeRegistry localSchemeRegistry = new SchemeRegistry();
      localSchemeRegistry.register(new Scheme("http", ElegantPlainSocketFactory.getSocketFactory(), 80));
      localSchemeRegistry.register(new Scheme("https", getSocketFactory(localSSLSessionCache), 443));
      ConnManagerParamBean localConnManagerParamBean = new ConnManagerParamBean(localBasicHttpParams);
      localConnManagerParamBean.setConnectionsPerRoute(new ConnPerRouteBean(4));
      localConnManagerParamBean.setMaxTotalConnections(8);
      return new AndroidHttpClient(new ElegantThreadSafeConnManager(localBasicHttpParams, localSchemeRegistry), localBasicHttpParams);
    }
  }
  
  public final <T> T execute(HttpHost paramHttpHost, HttpRequest paramHttpRequest, ResponseHandler<? extends T> paramResponseHandler)
    throws IOException, ClientProtocolException
  {
    return this.delegate.execute(paramHttpHost, paramHttpRequest, paramResponseHandler);
  }
  
  public final <T> T execute(HttpHost paramHttpHost, HttpRequest paramHttpRequest, ResponseHandler<? extends T> paramResponseHandler, HttpContext paramHttpContext)
    throws IOException, ClientProtocolException
  {
    return this.delegate.execute(paramHttpHost, paramHttpRequest, paramResponseHandler, paramHttpContext);
  }
  
  public final <T> T execute(HttpUriRequest paramHttpUriRequest, ResponseHandler<? extends T> paramResponseHandler)
    throws IOException, ClientProtocolException
  {
    return this.delegate.execute(paramHttpUriRequest, paramResponseHandler);
  }
  
  public final <T> T execute(HttpUriRequest paramHttpUriRequest, ResponseHandler<? extends T> paramResponseHandler, HttpContext paramHttpContext)
    throws IOException, ClientProtocolException
  {
    return this.delegate.execute(paramHttpUriRequest, paramResponseHandler, paramHttpContext);
  }
  
  public final HttpResponse execute(HttpHost paramHttpHost, HttpRequest paramHttpRequest)
    throws IOException
  {
    return this.delegate.execute(paramHttpHost, paramHttpRequest);
  }
  
  public final HttpResponse execute(HttpHost paramHttpHost, HttpRequest paramHttpRequest, HttpContext paramHttpContext)
    throws IOException
  {
    return this.delegate.execute(paramHttpHost, paramHttpRequest, paramHttpContext);
  }
  
  public final HttpResponse execute(HttpUriRequest paramHttpUriRequest)
    throws IOException
  {
    return this.delegate.execute(paramHttpUriRequest);
  }
  
  public final HttpResponse execute(HttpUriRequest paramHttpUriRequest, HttpContext paramHttpContext)
    throws IOException
  {
    return this.delegate.execute(paramHttpUriRequest, paramHttpContext);
  }
  
  protected final void finalize()
    throws Throwable
  {
    super.finalize();
    if (this.mLeakedException != null)
    {
      Log.e("AndroidHttpClient", "Leak found", this.mLeakedException);
      this.mLeakedException = null;
    }
  }
  
  public final ClientConnectionManager getConnectionManager()
  {
    return this.delegate.getConnectionManager();
  }
  
  public final HttpParams getParams()
  {
    return this.delegate.getParams();
  }
  
  private final class CurlLogger
    implements HttpRequestInterceptor
  {
    private CurlLogger() {}
    
    public final void process(HttpRequest paramHttpRequest, HttpContext paramHttpContext)
      throws IOException
    {
      AndroidHttpClient.LoggingConfiguration localLoggingConfiguration = AndroidHttpClient.this.curlConfiguration;
      if ((localLoggingConfiguration != null) && (Log.isLoggable(localLoggingConfiguration.tag, localLoggingConfiguration.level)) && ((paramHttpRequest instanceof HttpUriRequest)))
      {
        String str = AndroidHttpClient.access$500$501fc75d((HttpUriRequest)paramHttpRequest);
        Log.println(localLoggingConfiguration.level, localLoggingConfiguration.tag, str);
      }
    }
  }
  
  private static final class LoggingConfiguration
  {
    final int level;
    final String tag;
    
    private LoggingConfiguration(String paramString, int paramInt)
    {
      this.tag = paramString;
      this.level = paramInt;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.AndroidHttpClient
 * JD-Core Version:    0.7.0.1
 */