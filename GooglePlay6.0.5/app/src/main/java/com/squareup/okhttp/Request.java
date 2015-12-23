package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpMethod;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public final class Request
{
  public final RequestBody body;
  private volatile CacheControl cacheControl;
  public final Headers headers;
  public final String method;
  final Object tag;
  private volatile URI uri;
  volatile URL url;
  final String urlString;
  
  private Request(Builder paramBuilder)
  {
    this.urlString = paramBuilder.urlString;
    this.method = paramBuilder.method;
    this.headers = paramBuilder.headers.build();
    this.body = paramBuilder.body;
    if (paramBuilder.tag != null) {}
    for (Object localObject = paramBuilder.tag;; localObject = this)
    {
      this.tag = localObject;
      this.url = paramBuilder.url;
      return;
    }
  }
  
  public final CacheControl cacheControl()
  {
    CacheControl localCacheControl1 = this.cacheControl;
    if (localCacheControl1 != null) {
      return localCacheControl1;
    }
    CacheControl localCacheControl2 = CacheControl.parse(this.headers);
    this.cacheControl = localCacheControl2;
    return localCacheControl2;
  }
  
  public final String header(String paramString)
  {
    return this.headers.get(paramString);
  }
  
  public final boolean isHttps()
  {
    return url().getProtocol().equals("https");
  }
  
  public final Builder newBuilder()
  {
    return new Builder(this, (byte)0);
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Request{method=").append(this.method).append(", url=").append(this.urlString).append(", tag=");
    if (this.tag != this) {}
    for (Object localObject = this.tag;; localObject = null) {
      return localObject + '}';
    }
  }
  
  public final URI uri()
    throws IOException
  {
    try
    {
      URI localURI1 = this.uri;
      if (localURI1 != null) {
        return localURI1;
      }
      Platform.get();
      URI localURI2 = Platform.toUriLenient(url());
      this.uri = localURI2;
      return localURI2;
    }
    catch (URISyntaxException localURISyntaxException)
    {
      throw new IOException(localURISyntaxException.getMessage());
    }
  }
  
  public final URL url()
  {
    try
    {
      URL localURL1 = this.url;
      if (localURL1 != null) {
        return localURL1;
      }
      URL localURL2 = new URL(this.urlString);
      this.url = localURL2;
      return localURL2;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      throw new RuntimeException("Malformed URL: " + this.urlString, localMalformedURLException);
    }
  }
  
  public static final class Builder
  {
    RequestBody body;
    Headers.Builder headers;
    String method;
    Object tag;
    URL url;
    public String urlString;
    
    public Builder()
    {
      this.method = "GET";
      this.headers = new Headers.Builder();
    }
    
    private Builder(Request paramRequest)
    {
      this.urlString = paramRequest.urlString;
      this.url = paramRequest.url;
      this.method = paramRequest.method;
      this.body = paramRequest.body;
      this.tag = paramRequest.tag;
      this.headers = paramRequest.headers.newBuilder();
    }
    
    public final Builder addHeader(String paramString1, String paramString2)
    {
      this.headers.add(paramString1, paramString2);
      return this;
    }
    
    public final Request build()
    {
      if (this.urlString == null) {
        throw new IllegalStateException("url == null");
      }
      return new Request(this, (byte)0);
    }
    
    public final Builder header(String paramString1, String paramString2)
    {
      this.headers.set(paramString1, paramString2);
      return this;
    }
    
    public final Builder method(String paramString, RequestBody paramRequestBody)
    {
      if (paramString.length() == 0) {
        throw new IllegalArgumentException("method == null || method.length() == 0");
      }
      if ((paramRequestBody != null) && (!HttpMethod.permitsRequestBody(paramString))) {
        throw new IllegalArgumentException("method " + paramString + " must not have a request body.");
      }
      if ((paramRequestBody == null) && (HttpMethod.permitsRequestBody(paramString))) {
        paramRequestBody = RequestBody.create(null, Util.EMPTY_BYTE_ARRAY);
      }
      this.method = paramString;
      this.body = paramRequestBody;
      return this;
    }
    
    public final Builder removeHeader(String paramString)
    {
      this.headers.removeAll(paramString);
      return this;
    }
    
    public final Builder url(URL paramURL)
    {
      this.url = paramURL;
      this.urlString = paramURL.toString();
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Request
 * JD-Core Version:    0.7.0.1
 */