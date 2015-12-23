package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Protocol;
import java.net.URL;

public final class RequestLine
{
  public static String requestPath(URL paramURL)
  {
    String str = paramURL.getFile();
    if (str == null) {
      str = "/";
    }
    while (str.startsWith("/")) {
      return str;
    }
    return "/" + str;
  }
  
  public static String version(Protocol paramProtocol)
  {
    if (paramProtocol == Protocol.HTTP_1_0) {
      return "HTTP/1.0";
    }
    return "HTTP/1.1";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.RequestLine
 * JD-Core Version:    0.7.0.1
 */