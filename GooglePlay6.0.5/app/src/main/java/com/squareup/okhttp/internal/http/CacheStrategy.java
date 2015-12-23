package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.util.Date;

public final class CacheStrategy
{
  public final Response cacheResponse;
  public final Request networkRequest;
  
  private CacheStrategy(Request paramRequest, Response paramResponse)
  {
    this.networkRequest = paramRequest;
    this.cacheResponse = paramResponse;
  }
  
  public static boolean isCacheable(Response paramResponse, Request paramRequest)
  {
    switch (paramResponse.code)
    {
    }
    do
    {
      return false;
    } while (((paramResponse.header("Expires") == null) && (paramResponse.cacheControl().maxAgeSeconds == -1) && (paramResponse.cacheControl().sMaxAgeSeconds == -1) && (!paramResponse.cacheControl().isPublic)) || (paramResponse.cacheControl().noStore) || (paramRequest.cacheControl().noStore));
    return true;
  }
  
  public static final class Factory
  {
    public int ageSeconds = -1;
    public final Response cacheResponse;
    public String etag;
    public Date expires;
    public Date lastModified;
    public String lastModifiedString;
    public final long nowMillis;
    public long receivedResponseMillis;
    public final Request request;
    public long sentRequestMillis;
    public Date servedDate;
    public String servedDateString;
    
    public Factory(long paramLong, Request paramRequest, Response paramResponse)
    {
      this.nowMillis = paramLong;
      this.request = paramRequest;
      this.cacheResponse = paramResponse;
      if (paramResponse != null)
      {
        Headers localHeaders = paramResponse.headers;
        int i = 0;
        int j = localHeaders.namesAndValues.length / 2;
        if (i < j)
        {
          String str1 = localHeaders.name(i);
          String str2 = localHeaders.value(i);
          if ("Date".equalsIgnoreCase(str1))
          {
            this.servedDate = HttpDate.parse(str2);
            this.servedDateString = str2;
          }
          for (;;)
          {
            i++;
            break;
            if ("Expires".equalsIgnoreCase(str1))
            {
              this.expires = HttpDate.parse(str2);
            }
            else if ("Last-Modified".equalsIgnoreCase(str1))
            {
              this.lastModified = HttpDate.parse(str2);
              this.lastModifiedString = str2;
            }
            else if ("ETag".equalsIgnoreCase(str1))
            {
              this.etag = str2;
            }
            else if ("Age".equalsIgnoreCase(str1))
            {
              this.ageSeconds = HeaderParser.parseSeconds(str2, -1);
            }
            else if (OkHeaders.SENT_MILLIS.equalsIgnoreCase(str1))
            {
              this.sentRequestMillis = Long.parseLong(str2);
            }
            else if (OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase(str1))
            {
              this.receivedResponseMillis = Long.parseLong(str2);
            }
          }
        }
      }
    }
    
    public static boolean hasConditions(Request paramRequest)
    {
      return (paramRequest.header("If-Modified-Since") != null) || (paramRequest.header("If-None-Match") != null);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.CacheStrategy
 * JD-Core Version:    0.7.0.1
 */