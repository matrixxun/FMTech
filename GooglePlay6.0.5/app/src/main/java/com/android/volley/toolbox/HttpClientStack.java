package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpClientStack
  implements HttpStack
{
  protected final HttpClient mClient;
  
  public HttpClientStack(HttpClient paramHttpClient)
  {
    this.mClient = paramHttpClient;
  }
  
  private static void addHeaders(HttpUriRequest paramHttpUriRequest, Map<String, String> paramMap)
  {
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      paramHttpUriRequest.setHeader(str, (String)paramMap.get(str));
    }
  }
  
  private static void setEntityIfNonEmptyBody(HttpEntityEnclosingRequestBase paramHttpEntityEnclosingRequestBase, Request<?> paramRequest)
    throws AuthFailureError
  {
    byte[] arrayOfByte = paramRequest.getBody();
    if (arrayOfByte != null) {
      paramHttpEntityEnclosingRequestBase.setEntity(new ByteArrayEntity(arrayOfByte));
    }
  }
  
  public final HttpResponse performRequest(Request<?> paramRequest, Map<String, String> paramMap)
    throws IOException, AuthFailureError
  {
    Object localObject;
    switch (paramRequest.mMethod)
    {
    default: 
      throw new IllegalStateException("Unknown request method.");
    case -1: 
      byte[] arrayOfByte = paramRequest.getPostBody();
      if (arrayOfByte != null)
      {
        localObject = new HttpPost(paramRequest.getUrl());
        ((HttpPost)localObject).addHeader("Content-Type", paramRequest.getPostBodyContentType());
        ((HttpPost)localObject).setEntity(new ByteArrayEntity(arrayOfByte));
      }
      break;
    }
    for (;;)
    {
      addHeaders((HttpUriRequest)localObject, paramMap);
      addHeaders((HttpUriRequest)localObject, paramRequest.getHeaders());
      HttpParams localHttpParams = ((HttpUriRequest)localObject).getParams();
      int i = paramRequest.getTimeoutMs();
      HttpConnectionParams.setConnectionTimeout(localHttpParams, 5000);
      HttpConnectionParams.setSoTimeout(localHttpParams, i);
      return this.mClient.execute((HttpUriRequest)localObject);
      localObject = new HttpGet(paramRequest.getUrl());
      continue;
      localObject = new HttpGet(paramRequest.getUrl());
      continue;
      localObject = new HttpDelete(paramRequest.getUrl());
      continue;
      localObject = new HttpPost(paramRequest.getUrl());
      ((HttpPost)localObject).addHeader("Content-Type", paramRequest.getBodyContentType());
      setEntityIfNonEmptyBody((HttpEntityEnclosingRequestBase)localObject, paramRequest);
      continue;
      localObject = new HttpPut(paramRequest.getUrl());
      ((HttpPut)localObject).addHeader("Content-Type", paramRequest.getBodyContentType());
      setEntityIfNonEmptyBody((HttpEntityEnclosingRequestBase)localObject, paramRequest);
      continue;
      localObject = new HttpHead(paramRequest.getUrl());
      continue;
      localObject = new HttpOptions(paramRequest.getUrl());
      continue;
      localObject = new HttpTrace(paramRequest.getUrl());
      continue;
      localObject = new HttpPatch(paramRequest.getUrl());
      ((HttpPatch)localObject).addHeader("Content-Type", paramRequest.getBodyContentType());
      setEntityIfNonEmptyBody((HttpEntityEnclosingRequestBase)localObject, paramRequest);
    }
  }
  
  public static final class HttpPatch
    extends HttpEntityEnclosingRequestBase
  {
    public HttpPatch() {}
    
    public HttpPatch(String paramString)
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
 * Qualified Name:     com.android.volley.toolbox.HttpClientStack
 * JD-Core Version:    0.7.0.1
 */