package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.ProtocolException;
import okio.Buffer;
import okio.Sink;
import okio.Timeout;

public final class RetryableSink
  implements Sink
{
  private boolean closed;
  public final Buffer content = new Buffer();
  private final int limit;
  
  public RetryableSink()
  {
    this(-1);
  }
  
  public RetryableSink(int paramInt)
  {
    this.limit = paramInt;
  }
  
  public final void close()
    throws IOException
  {
    if (this.closed) {}
    do
    {
      return;
      this.closed = true;
    } while (this.content.size >= this.limit);
    throw new ProtocolException("content-length promised " + this.limit + " bytes, but received " + this.content.size);
  }
  
  public final void flush()
    throws IOException
  {}
  
  public final Timeout timeout()
  {
    return Timeout.NONE;
  }
  
  public final void write(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    Util.checkOffsetAndCount$487762af(paramBuffer.size, paramLong);
    if ((this.limit != -1) && (this.content.size > this.limit - paramLong)) {
      throw new ProtocolException("exceeded content-length limit of " + this.limit + " bytes");
    }
    this.content.write(paramBuffer, paramLong);
  }
  
  public final void writeToSocket(Sink paramSink)
    throws IOException
  {
    Buffer localBuffer = this.content.clone();
    paramSink.write(localBuffer, localBuffer.size);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.RetryableSink
 * JD-Core Version:    0.7.0.1
 */