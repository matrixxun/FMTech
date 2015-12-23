package com.squareup.okhttp;

import java.io.IOException;
import okio.BufferedSink;

public abstract class RequestBody
{
  public static RequestBody create(MediaType paramMediaType, final byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      throw new NullPointerException("content == null");
    }
    new RequestBody()
    {
      public final long contentLength()
      {
        return paramArrayOfByte.length;
      }
      
      public final MediaType contentType()
      {
        return this.val$contentType;
      }
      
      public final void writeTo(BufferedSink paramAnonymousBufferedSink)
        throws IOException
      {
        paramAnonymousBufferedSink.write(paramArrayOfByte);
      }
    };
  }
  
  public long contentLength()
    throws IOException
  {
    return -1L;
  }
  
  public abstract MediaType contentType();
  
  public abstract void writeTo(BufferedSink paramBufferedSink)
    throws IOException;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.RequestBody
 * JD-Core Version:    0.7.0.1
 */