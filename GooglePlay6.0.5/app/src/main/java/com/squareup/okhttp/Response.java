package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.OkHeaders;
import java.util.Collections;
import java.util.List;

public final class Response
{
  public final ResponseBody body;
  private volatile CacheControl cacheControl;
  Response cacheResponse;
  public final int code;
  public final Handshake handshake;
  public final Headers headers;
  public final String message;
  Response networkResponse;
  final Response priorResponse;
  public final Protocol protocol;
  public final Request request;
  
  private Response(Builder paramBuilder)
  {
    this.request = paramBuilder.request;
    this.protocol = paramBuilder.protocol;
    this.code = paramBuilder.code;
    this.message = paramBuilder.message;
    this.handshake = paramBuilder.handshake;
    this.headers = paramBuilder.headers.build();
    this.body = paramBuilder.body;
    this.networkResponse = paramBuilder.networkResponse;
    this.cacheResponse = paramBuilder.cacheResponse;
    this.priorResponse = paramBuilder.priorResponse;
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
  
  public final List<Challenge> challenges()
  {
    if (this.code == 401) {}
    for (String str = "WWW-Authenticate";; str = "Proxy-Authenticate")
    {
      return OkHeaders.parseChallenges(this.headers, str);
      if (this.code != 407) {
        break;
      }
    }
    return Collections.emptyList();
  }
  
  public final String header(String paramString)
  {
    String str = this.headers.get(paramString);
    if (str != null) {
      return str;
    }
    return null;
  }
  
  public final Builder newBuilder()
  {
    return new Builder(this, (byte)0);
  }
  
  public final String toString()
  {
    return "Response{protocol=" + this.protocol + ", code=" + this.code + ", message=" + this.message + ", url=" + this.request.urlString + '}';
  }
  
  public static final class Builder
  {
    public ResponseBody body;
    Response cacheResponse;
    public int code = -1;
    public Handshake handshake;
    Headers.Builder headers;
    public String message;
    Response networkResponse;
    Response priorResponse;
    public Protocol protocol;
    public Request request;
    
    public Builder()
    {
      this.headers = new Headers.Builder();
    }
    
    private Builder(Response paramResponse)
    {
      this.request = paramResponse.request;
      this.protocol = paramResponse.protocol;
      this.code = paramResponse.code;
      this.message = paramResponse.message;
      this.handshake = paramResponse.handshake;
      this.headers = paramResponse.headers.newBuilder();
      this.body = paramResponse.body;
      this.networkResponse = paramResponse.networkResponse;
      this.cacheResponse = paramResponse.cacheResponse;
      this.priorResponse = paramResponse.priorResponse;
    }
    
    private static void checkSupportResponse(String paramString, Response paramResponse)
    {
      if (paramResponse.body != null) {
        throw new IllegalArgumentException(paramString + ".body != null");
      }
      if (paramResponse.networkResponse != null) {
        throw new IllegalArgumentException(paramString + ".networkResponse != null");
      }
      if (paramResponse.cacheResponse != null) {
        throw new IllegalArgumentException(paramString + ".cacheResponse != null");
      }
      if (paramResponse.priorResponse != null) {
        throw new IllegalArgumentException(paramString + ".priorResponse != null");
      }
    }
    
    public final Builder addHeader(String paramString1, String paramString2)
    {
      this.headers.add(paramString1, paramString2);
      return this;
    }
    
    public final Response build()
    {
      if (this.request == null) {
        throw new IllegalStateException("request == null");
      }
      if (this.protocol == null) {
        throw new IllegalStateException("protocol == null");
      }
      if (this.code < 0) {
        throw new IllegalStateException("code < 0: " + this.code);
      }
      return new Response(this, (byte)0);
    }
    
    public final Builder cacheResponse(Response paramResponse)
    {
      if (paramResponse != null) {
        checkSupportResponse("cacheResponse", paramResponse);
      }
      this.cacheResponse = paramResponse;
      return this;
    }
    
    public final Builder header(String paramString1, String paramString2)
    {
      this.headers.set(paramString1, paramString2);
      return this;
    }
    
    public final Builder headers(Headers paramHeaders)
    {
      this.headers = paramHeaders.newBuilder();
      return this;
    }
    
    public final Builder networkResponse(Response paramResponse)
    {
      if (paramResponse != null) {
        checkSupportResponse("networkResponse", paramResponse);
      }
      this.networkResponse = paramResponse;
      return this;
    }
    
    public final Builder priorResponse(Response paramResponse)
    {
      if ((paramResponse != null) && (paramResponse.body != null)) {
        throw new IllegalArgumentException("priorResponse.body != null");
      }
      this.priorResponse = paramResponse;
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Response
 * JD-Core Version:    0.7.0.1
 */