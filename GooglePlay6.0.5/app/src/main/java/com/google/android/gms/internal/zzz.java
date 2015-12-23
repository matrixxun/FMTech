package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public final class zzz
  implements zzy
{
  private final zza zzaD = null;
  private final SSLSocketFactory zzaE = null;
  
  public zzz()
  {
    this((byte)0);
  }
  
  private zzz(byte paramByte)
  {
    this(null);
  }
  
  private zzz(zza paramzza) {}
  
  private static HttpEntity zza(HttpURLConnection paramHttpURLConnection)
  {
    BasicHttpEntity localBasicHttpEntity = new BasicHttpEntity();
    try
    {
      InputStream localInputStream2 = paramHttpURLConnection.getInputStream();
      localInputStream1 = localInputStream2;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        InputStream localInputStream1 = paramHttpURLConnection.getErrorStream();
      }
    }
    localBasicHttpEntity.setContent(localInputStream1);
    localBasicHttpEntity.setContentLength(paramHttpURLConnection.getContentLength());
    localBasicHttpEntity.setContentEncoding(paramHttpURLConnection.getContentEncoding());
    localBasicHttpEntity.setContentType(paramHttpURLConnection.getContentType());
    return localBasicHttpEntity;
  }
  
  public final HttpResponse zza(zzk<?> paramzzk, Map<String, String> paramMap)
    throws IOException, zza
  {
    String str1 = paramzzk.zzE;
    HashMap localHashMap = new HashMap();
    localHashMap.putAll(paramzzk.getHeaders());
    localHashMap.putAll(paramMap);
    String str2;
    if (this.zzaD != null)
    {
      str2 = this.zzaD.zzh$16915f7f();
      if (str2 == null) {
        throw new IOException("URL blocked by rewriter: " + str1);
      }
    }
    else
    {
      str2 = str1;
    }
    URL localURL = new URL(str2);
    HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
    int i = paramzzk.zzs();
    localHttpURLConnection.setConnectTimeout(i);
    localHttpURLConnection.setReadTimeout(i);
    localHttpURLConnection.setUseCaches(false);
    localHttpURLConnection.setDoInput(true);
    if (("https".equals(localURL.getProtocol())) && (this.zzaE != null)) {
      ((HttpsURLConnection)localHttpURLConnection).setSSLSocketFactory(this.zzaE);
    }
    Iterator localIterator1 = localHashMap.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str3 = (String)localIterator1.next();
      localHttpURLConnection.addRequestProperty(str3, (String)localHashMap.get(str3));
    }
    switch (paramzzk.zzD)
    {
    default: 
      throw new IllegalStateException("Unknown method type.");
    case 0: 
      localHttpURLConnection.setRequestMethod("GET");
    }
    ProtocolVersion localProtocolVersion;
    for (;;)
    {
      localProtocolVersion = new ProtocolVersion("HTTP", 1, 1);
      if (localHttpURLConnection.getResponseCode() != -1) {
        break;
      }
      throw new IOException("Could not retrieve response code from HttpUrlConnection.");
      localHttpURLConnection.setRequestMethod("DELETE");
      continue;
      localHttpURLConnection.setRequestMethod("POST");
      continue;
      localHttpURLConnection.setRequestMethod("PUT");
      continue;
      localHttpURLConnection.setRequestMethod("HEAD");
      continue;
      localHttpURLConnection.setRequestMethod("OPTIONS");
      continue;
      localHttpURLConnection.setRequestMethod("TRACE");
      continue;
      localHttpURLConnection.setRequestMethod("PATCH");
    }
    BasicHttpResponse localBasicHttpResponse = new BasicHttpResponse(new BasicStatusLine(localProtocolVersion, localHttpURLConnection.getResponseCode(), localHttpURLConnection.getResponseMessage()));
    localBasicHttpResponse.setEntity(zza(localHttpURLConnection));
    Iterator localIterator2 = localHttpURLConnection.getHeaderFields().entrySet().iterator();
    while (localIterator2.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator2.next();
      if (localEntry.getKey() != null) {
        localBasicHttpResponse.addHeader(new BasicHeader((String)localEntry.getKey(), (String)((List)localEntry.getValue()).get(0)));
      }
    }
    return localBasicHttpResponse;
  }
  
  public static abstract interface zza
  {
    public abstract String zzh$16915f7f();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzz
 * JD-Core Version:    0.7.0.1
 */