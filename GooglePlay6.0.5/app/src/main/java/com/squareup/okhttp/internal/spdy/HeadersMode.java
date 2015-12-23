package com.squareup.okhttp.internal.spdy;

public enum HeadersMode
{
  static
  {
    SPDY_REPLY = new HeadersMode("SPDY_REPLY", 1);
    SPDY_HEADERS = new HeadersMode("SPDY_HEADERS", 2);
    HTTP_20_HEADERS = new HeadersMode("HTTP_20_HEADERS", 3);
    HeadersMode[] arrayOfHeadersMode = new HeadersMode[4];
    arrayOfHeadersMode[0] = SPDY_SYN_STREAM;
    arrayOfHeadersMode[1] = SPDY_REPLY;
    arrayOfHeadersMode[2] = SPDY_HEADERS;
    arrayOfHeadersMode[3] = HTTP_20_HEADERS;
    $VALUES = arrayOfHeadersMode;
  }
  
  private HeadersMode() {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.HeadersMode
 * JD-Core Version:    0.7.0.1
 */