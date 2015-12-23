package okio;

import java.io.IOException;

final class RealBufferedSink
  implements BufferedSink
{
  public final Buffer buffer;
  private boolean closed;
  public final Sink sink;
  
  public RealBufferedSink(Sink paramSink)
  {
    this(paramSink, new Buffer());
  }
  
  private RealBufferedSink(Sink paramSink, Buffer paramBuffer)
  {
    if (paramSink == null) {
      throw new IllegalArgumentException("sink == null");
    }
    this.buffer = paramBuffer;
    this.sink = paramSink;
  }
  
  public final Buffer buffer()
  {
    return this.buffer;
  }
  
  public final void close()
    throws IOException
  {
    if (this.closed) {}
    for (;;)
    {
      return;
      try
      {
        boolean bool = this.buffer.size < 0L;
        localThrowable1 = null;
        if (bool) {
          this.sink.write(this.buffer, this.buffer.size);
        }
      }
      catch (Throwable localThrowable2)
      {
        try
        {
          for (;;)
          {
            Throwable localThrowable1;
            this.sink.close();
            this.closed = true;
            if (localThrowable1 == null) {
              break;
            }
            Util.sneakyRethrow(localThrowable1);
            return;
            localThrowable2 = localThrowable2;
          }
        }
        catch (Throwable localThrowable3)
        {
          for (;;)
          {
            if (localThrowable2 == null) {
              Object localObject = localThrowable3;
            }
          }
        }
      }
    }
  }
  
  public final BufferedSink emit()
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    long l = this.buffer.size;
    if (l > 0L) {
      this.sink.write(this.buffer, l);
    }
    return this;
  }
  
  public final BufferedSink emitCompleteSegments()
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    Buffer localBuffer = this.buffer;
    long l1 = localBuffer.size;
    if (l1 == 0L) {}
    for (long l2 = 0L;; l2 = l1)
    {
      if (l2 > 0L) {
        this.sink.write(this.buffer, l2);
      }
      return this;
      Segment localSegment = localBuffer.head.prev;
      if (localSegment.limit < 2048) {
        l1 -= localSegment.limit - localSegment.pos;
      }
    }
  }
  
  public final void flush()
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    if (this.buffer.size > 0L) {
      this.sink.write(this.buffer, this.buffer.size);
    }
    this.sink.flush();
  }
  
  public final Timeout timeout()
  {
    return this.sink.timeout();
  }
  
  public final String toString()
  {
    return "buffer(" + this.sink + ")";
  }
  
  public final BufferedSink write(ByteString paramByteString)
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    this.buffer.write(paramByteString);
    return emitCompleteSegments();
  }
  
  public final BufferedSink write(byte[] paramArrayOfByte)
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    this.buffer.write(paramArrayOfByte);
    return emitCompleteSegments();
  }
  
  public final BufferedSink write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    this.buffer.write(paramArrayOfByte, paramInt1, paramInt2);
    return emitCompleteSegments();
  }
  
  public final void write(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    this.buffer.write(paramBuffer, paramLong);
    emitCompleteSegments();
  }
  
  public final long writeAll(Source paramSource)
    throws IOException
  {
    if (paramSource == null) {
      throw new IllegalArgumentException("source == null");
    }
    long l1 = 0L;
    for (;;)
    {
      long l2 = paramSource.read(this.buffer, 2048L);
      if (l2 == -1L) {
        break;
      }
      l1 += l2;
      emitCompleteSegments();
    }
    return l1;
  }
  
  public final BufferedSink writeByte(int paramInt)
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    this.buffer.writeByte(paramInt);
    return emitCompleteSegments();
  }
  
  public final BufferedSink writeInt(int paramInt)
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    this.buffer.writeInt(paramInt);
    return emitCompleteSegments();
  }
  
  public final BufferedSink writeShort(int paramInt)
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    this.buffer.writeShort(paramInt);
    return emitCompleteSegments();
  }
  
  public final BufferedSink writeUtf8(String paramString)
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    this.buffer.writeUtf8(paramString);
    return emitCompleteSegments();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.RealBufferedSink
 * JD-Core Version:    0.7.0.1
 */