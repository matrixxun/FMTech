package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.ResponseBody;
import okio.BufferedSource;

public final class RealResponseBody
  extends ResponseBody
{
  private final Headers headers;
  private final BufferedSource source;
  
  public RealResponseBody(Headers paramHeaders, BufferedSource paramBufferedSource)
  {
    this.headers = paramHeaders;
    this.source = paramBufferedSource;
  }
  
  public final long contentLength()
  {
    return OkHeaders.contentLength(this.headers);
  }
  
  public final MediaType contentType()
  {
    String str = this.headers.get("Content-Type");
    if (str != null) {
      return MediaType.parse(str);
    }
    return null;
  }
  
  public final BufferedSource source()
  {
    return this.source;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.RealResponseBody
 * JD-Core Version:    0.7.0.1
 */