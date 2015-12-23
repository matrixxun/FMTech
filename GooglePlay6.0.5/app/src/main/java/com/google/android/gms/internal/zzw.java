package com.google.android.gms.internal;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public final class zzw
  implements zzy
{
  protected final HttpClient zzaC;
  
  public zzw(HttpClient paramHttpClient)
  {
    this.zzaC = paramHttpClient;
  }
  
  private static void zza(HttpUriRequest paramHttpUriRequest, Map<String, String> paramMap)
  {
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      paramHttpUriRequest.setHeader(str, (String)paramMap.get(str));
    }
  }
  
  public final HttpResponse zza(zzk<?> paramzzk, Map<String, String> paramMap)
    throws IOException, zza
  {
    Object localObject;
    switch (paramzzk.zzD)
    {
    default: 
      throw new IllegalStateException("Unknown request method.");
    case -1: 
      localObject = new HttpGet(paramzzk.zzE);
    }
    for (;;)
    {
      zza((HttpUriRequest)localObject, paramMap);
      zza((HttpUriRequest)localObject, paramzzk.getHeaders());
      HttpParams localHttpParams = ((HttpUriRequest)localObject).getParams();
      int i = paramzzk.zzs();
      HttpConnectionParams.setConnectionTimeout(localHttpParams, 5000);
      HttpConnectionParams.setSoTimeout(localHttpParams, i);
      return this.zzaC.execute((HttpUriRequest)localObject);
      localObject = new HttpGet(paramzzk.zzE);
      continue;
      localObject = new HttpDelete(paramzzk.zzE);
      continue;
      localObject = new HttpPost(paramzzk.zzE);
      ((HttpPost)localObject).addHeader("Content-Type", zzk.zzo());
      continue;
      localObject = new HttpPut(paramzzk.zzE);
      ((HttpPut)localObject).addHeader("Content-Type", zzk.zzo());
      continue;
      localObject = new HttpHead(paramzzk.zzE);
      continue;
      localObject = new HttpOptions(paramzzk.zzE);
      continue;
      localObject = new HttpTrace(paramzzk.zzE);
      continue;
      localObject = new zza(paramzzk.zzE);
      ((zza)localObject).addHeader("Content-Type", zzk.zzo());
    }
  }
  
  public static final class zza
    extends HttpEntityEnclosingRequestBase
  {
    public zza() {}
    
    public zza(String paramString)
    {
      setURI(URI.create(paramString));
    }
    
    public final String getMethod()
    {
      return "PATCH";
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzw
 * JD-Core Version:    0.7.0.1
 */