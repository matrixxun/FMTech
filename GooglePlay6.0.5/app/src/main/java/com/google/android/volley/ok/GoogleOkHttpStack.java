package com.google.android.volley.ok;

import android.content.Context;
import android.os.SystemClock;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpStack;
import com.google.android.gsf.Gservices;
import com.google.android.volley.ok.interceptors.CurlLoggingInterceptor;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.CipherSuite;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.ConnectionSpec.Builder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okio.BufferedSource;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public final class GoogleOkHttpStack
  implements HttpStack
{
  private static final ProtocolVersion PROTOCOL_HTTP_1_0 = new ProtocolVersion("HTTP", 1, 0);
  private static final ProtocolVersion PROTOCOL_HTTP_1_1 = new ProtocolVersion("HTTP", 1, 1);
  private static final ProtocolVersion PROTOCOL_HTTP_2 = new ProtocolVersion("HTTP", 2, 0);
  private static final ProtocolVersion PROTOCOL_SPDY_3_1 = new ProtocolVersion("SPD", 3, 1);
  private static final List<ConnectionSpec> SUPPORTED_CONNECTION_SPECS;
  private static final ConnectionSpec SUPPORTED_SSL_SPEC;
  private StatRecorderFactory mNetworkStatCollector;
  private ClientPool mPool;
  private boolean mShouldCollectStats;
  private UrlRewriter mUrlRewriter;
  
  static
  {
    ConnectionSpec.Builder localBuilder = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS);
    CipherSuite[] arrayOfCipherSuite = new CipherSuite[16];
    arrayOfCipherSuite[0] = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256;
    arrayOfCipherSuite[1] = CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256;
    arrayOfCipherSuite[2] = CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256;
    arrayOfCipherSuite[3] = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA;
    arrayOfCipherSuite[4] = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[5] = CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[6] = CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA;
    arrayOfCipherSuite[7] = CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA;
    arrayOfCipherSuite[8] = CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA;
    arrayOfCipherSuite[9] = CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[10] = CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[11] = CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA;
    arrayOfCipherSuite[12] = CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256;
    arrayOfCipherSuite[13] = CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[14] = CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA;
    arrayOfCipherSuite[15] = CipherSuite.TLS_RSA_WITH_RC4_128_SHA;
    SUPPORTED_SSL_SPEC = localBuilder.cipherSuites(arrayOfCipherSuite).build();
    ConnectionSpec[] arrayOfConnectionSpec = new ConnectionSpec[2];
    arrayOfConnectionSpec[0] = SUPPORTED_SSL_SPEC;
    arrayOfConnectionSpec[1] = ConnectionSpec.CLEARTEXT;
    SUPPORTED_CONNECTION_SPECS = Arrays.asList(arrayOfConnectionSpec);
  }
  
  public GoogleOkHttpStack(Context paramContext, OkHttpClient paramOkHttpClient, UrlRewriter paramUrlRewriter, StatRecorderFactory paramStatRecorderFactory, boolean paramBoolean)
  {
    this.mShouldCollectStats = Gservices.getBoolean(paramContext.getContentResolver(), "http_stats", false);
    this.mUrlRewriter = paramUrlRewriter;
    if (this.mShouldCollectStats) {
      paramStatRecorderFactory = new BasicRecorderFactory();
    }
    this.mNetworkStatCollector = paramStatRecorderFactory;
    if (paramBoolean) {
      paramOkHttpClient.networkInterceptors.add(new CurlLoggingInterceptor());
    }
    paramOkHttpClient.connectionSpecs = Util.immutableList(SUPPORTED_CONNECTION_SPECS);
    this.mPool = new ClientPool(paramOkHttpClient, 10);
  }
  
  private static InterruptedIOException convertToSocketTimeoutIfNecessary(InterruptedIOException paramInterruptedIOException)
  {
    if ("timeout".equals(paramInterruptedIOException.getMessage())) {
      paramInterruptedIOException = new SocketTimeoutException();
    }
    return paramInterruptedIOException;
  }
  
  public final HttpResponse performRequest(Request<?> paramRequest, Map<String, String> paramMap)
    throws IOException, AuthFailureError
  {
    OkHttpClient localOkHttpClient = this.mPool.getClient(paramRequest.getTimeoutMs());
    int i = paramRequest.mMethod;
    byte[] arrayOfByte = paramRequest.getBody();
    String str1;
    String str2;
    switch (i)
    {
    default: 
      throw new IllegalArgumentException("Unknown HTTP Method value: " + i);
    case -1: 
      if (arrayOfByte == null)
      {
        str1 = "GET";
        str2 = paramRequest.getBodyContentType();
        if (arrayOfByte != null) {
          switch (i)
          {
          }
        }
      }
      break;
    }
    String str3;
    Request.Builder localBuilder1;
    for (RequestBody localRequestBody = null;; localRequestBody = RequestBody.create(MediaType.parse(str2), arrayOfByte))
    {
      str3 = paramRequest.getUrl();
      if (this.mUrlRewriter != null) {
        str3 = this.mUrlRewriter.rewriteUrl(str3);
      }
      localBuilder1 = new Request.Builder();
      if (str3 != null) {
        break label312;
      }
      throw new IllegalArgumentException("url == null");
      str1 = "POST";
      break;
      str1 = "GET";
      break;
      str1 = "POST";
      break;
      str1 = "PUT";
      break;
      str1 = "PATCH";
      break;
      str1 = "HEAD";
      break;
      str1 = "TRACE";
      break;
      str1 = "DELETE";
      break;
      str1 = "OPTIONS";
      break;
    }
    label312:
    localBuilder1.urlString = str3;
    Request.Builder localBuilder2 = localBuilder1.method(str1, localRequestBody);
    Iterator localIterator1 = paramRequest.getHeaders().entrySet().iterator();
    while (localIterator1.hasNext())
    {
      Map.Entry localEntry2 = (Map.Entry)localIterator1.next();
      localBuilder2.addHeader((String)localEntry2.getKey(), (String)localEntry2.getValue());
    }
    if (!paramMap.isEmpty())
    {
      Iterator localIterator2 = paramMap.entrySet().iterator();
      while (localIterator2.hasNext())
      {
        Map.Entry localEntry1 = (Map.Entry)localIterator2.next();
        localBuilder2.addHeader((String)localEntry1.getKey(), (String)localEntry1.getValue());
      }
    }
    Call localCall = localOkHttpClient.newCall(localBuilder2.build());
    boolean bool = this.mShouldCollectStats;
    StatRecorderFactory.StatRecorder localStatRecorder = null;
    if (bool) {
      localStatRecorder = this.mNetworkStatCollector.createStatRecorder$6c5cba1d();
    }
    Response localResponse;
    Object localObject;
    int j;
    try
    {
      localResponse = localCall.execute();
      localObject = new BasicHttpEntity();
      ((BasicHttpEntity)localObject).setContent(new WrappedInputStream(localResponse.body.source().inputStream()));
      ((BasicHttpEntity)localObject).setContentLength(localResponse.body.contentLength());
      ((BasicHttpEntity)localObject).setContentEncoding(localResponse.header("Content-Encoding"));
      MediaType localMediaType = localResponse.body.contentType();
      if (localMediaType != null) {
        ((BasicHttpEntity)localObject).setContentType(localMediaType.toString());
      }
      j = localResponse.code;
      if (this.mShouldCollectStats) {
        localObject = localStatRecorder.recordStats$58b453ee((String)paramRequest.getHeaders().get("User-Agent"), (HttpEntity)localObject);
      }
      Protocol localProtocol = localResponse.protocol;
      switch (1.$SwitchMap$com$squareup$okhttp$Protocol[localProtocol.ordinal()])
      {
      default: 
        throw new IllegalArgumentException(String.format("Unknown protocol: %s", new Object[] { localProtocol }));
      }
    }
    catch (InterruptedIOException localInterruptedIOException)
    {
      throw convertToSocketTimeoutIfNecessary(localInterruptedIOException);
    }
    ProtocolVersion localProtocolVersion = PROTOCOL_HTTP_1_0;
    BasicHttpResponse localBasicHttpResponse;
    for (;;)
    {
      localBasicHttpResponse = new BasicHttpResponse(new BasicStatusLine(localProtocolVersion, j, localResponse.message));
      localBasicHttpResponse.setEntity((HttpEntity)localObject);
      Headers localHeaders = localResponse.headers;
      int k = localHeaders.namesAndValues.length / 2;
      for (int m = 0; m < k; m++) {
        localBasicHttpResponse.addHeader(localHeaders.name(m), localHeaders.value(m));
      }
      localProtocolVersion = PROTOCOL_HTTP_1_1;
      continue;
      localProtocolVersion = PROTOCOL_HTTP_2;
      continue;
      localProtocolVersion = PROTOCOL_SPDY_3_1;
    }
    return localBasicHttpResponse;
  }
  
  private static final class ClientPool
  {
    private int mMaxPoolSize;
    private Map<Integer, CachedClient> mPool;
    private final OkHttpClient mPrototype;
    
    ClientPool(OkHttpClient paramOkHttpClient, int paramInt)
    {
      this.mPrototype = paramOkHttpClient;
      this.mMaxPoolSize = 10;
      this.mPool = new HashMap(10);
    }
    
    public final OkHttpClient getClient(int paramInt)
    {
      for (;;)
      {
        OkHttpClient localOkHttpClient;
        long l2;
        TimeUnit localTimeUnit1;
        try
        {
          CachedClient localCachedClient = (CachedClient)this.mPool.get(Integer.valueOf(paramInt));
          if (localCachedClient != null)
          {
            localCachedClient.mLastUsed = SystemClock.elapsedRealtime();
            localOkHttpClient = localCachedClient.mClient;
            return localOkHttpClient;
          }
          if (this.mPool.size() >= this.mMaxPoolSize)
          {
            long l1 = 9223372036854775807L;
            Integer localInteger = null;
            Iterator localIterator = this.mPool.entrySet().iterator();
            if (localIterator.hasNext())
            {
              Map.Entry localEntry = (Map.Entry)localIterator.next();
              if (((CachedClient)localEntry.getValue()).mLastUsed >= l1) {
                continue;
              }
              l1 = ((CachedClient)localEntry.getValue()).mLastUsed;
              localInteger = (Integer)localEntry.getKey();
              continue;
            }
            this.mPool.remove(localInteger);
          }
          localOkHttpClient = this.mPrototype.clone();
          l2 = paramInt;
          localTimeUnit1 = TimeUnit.MILLISECONDS;
          if (l2 < 0L) {
            throw new IllegalArgumentException("timeout < 0");
          }
        }
        finally {}
        if (localTimeUnit1 == null) {
          throw new IllegalArgumentException("unit == null");
        }
        long l3 = localTimeUnit1.toMillis(l2);
        if (l3 > 2147483647L) {
          throw new IllegalArgumentException("Timeout too large.");
        }
        localOkHttpClient.writeTimeout = ((int)l3);
        long l4 = paramInt;
        TimeUnit localTimeUnit2 = TimeUnit.MILLISECONDS;
        if (l4 < 0L) {
          throw new IllegalArgumentException("timeout < 0");
        }
        if (localTimeUnit2 == null) {
          throw new IllegalArgumentException("unit == null");
        }
        long l5 = localTimeUnit2.toMillis(l4);
        if (l5 > 2147483647L) {
          throw new IllegalArgumentException("Timeout too large.");
        }
        localOkHttpClient.readTimeout = ((int)l5);
        TimeUnit localTimeUnit3 = TimeUnit.MILLISECONDS;
        if (5000L < 0L) {
          throw new IllegalArgumentException("timeout < 0");
        }
        if (localTimeUnit3 == null) {
          throw new IllegalArgumentException("unit == null");
        }
        long l6 = localTimeUnit3.toMillis(5000L);
        if (l6 > 2147483647L) {
          throw new IllegalArgumentException("Timeout too large.");
        }
        localOkHttpClient.connectTimeout = ((int)l6);
        this.mPool.put(Integer.valueOf(paramInt), new CachedClient(localOkHttpClient));
      }
    }
    
    private static final class CachedClient
    {
      OkHttpClient mClient;
      long mLastUsed;
      
      public CachedClient(OkHttpClient paramOkHttpClient)
      {
        this.mClient = paramOkHttpClient;
        this.mLastUsed = SystemClock.elapsedRealtime();
      }
    }
  }
  
  private final class WrappedInputStream
    extends InputStream
  {
    private InputStream inputStream;
    
    public WrappedInputStream(InputStream paramInputStream)
    {
      this.inputStream = paramInputStream;
    }
    
    public final int available()
      throws IOException
    {
      return this.inputStream.available();
    }
    
    public final void close()
      throws IOException
    {
      this.inputStream.close();
    }
    
    public final void mark(int paramInt)
    {
      this.inputStream.mark(paramInt);
    }
    
    public final boolean markSupported()
    {
      return this.inputStream.markSupported();
    }
    
    public final int read()
      throws IOException
    {
      try
      {
        int i = this.inputStream.read();
        return i;
      }
      catch (InterruptedIOException localInterruptedIOException)
      {
        throw GoogleOkHttpStack.access$000$4fc2f67c(localInterruptedIOException);
      }
    }
    
    public final int read(byte[] paramArrayOfByte)
      throws IOException
    {
      try
      {
        int i = this.inputStream.read(paramArrayOfByte);
        return i;
      }
      catch (InterruptedIOException localInterruptedIOException)
      {
        throw GoogleOkHttpStack.access$000$4fc2f67c(localInterruptedIOException);
      }
    }
    
    public final int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      try
      {
        int i = this.inputStream.read(paramArrayOfByte, paramInt1, paramInt2);
        return i;
      }
      catch (InterruptedIOException localInterruptedIOException)
      {
        throw GoogleOkHttpStack.access$000$4fc2f67c(localInterruptedIOException);
      }
    }
    
    public final void reset()
      throws IOException
    {
      try
      {
        this.inputStream.reset();
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public final long skip(long paramLong)
      throws IOException
    {
      return this.inputStream.skip(paramLong);
    }
    
    public final String toString()
    {
      return this.inputStream.toString();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.ok.GoogleOkHttpStack
 * JD-Core Version:    0.7.0.1
 */