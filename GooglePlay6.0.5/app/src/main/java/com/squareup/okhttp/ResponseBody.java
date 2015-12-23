package com.squareup.okhttp;

import java.io.Closeable;
import java.io.IOException;
import okio.BufferedSource;

public abstract class ResponseBody
  implements Closeable
{
  public void close()
    throws IOException
  {
    source().close();
  }
  
  public abstract long contentLength();
  
  public abstract MediaType contentType();
  
  public abstract BufferedSource source();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.ResponseBody
 * JD-Core Version:    0.7.0.1
 */