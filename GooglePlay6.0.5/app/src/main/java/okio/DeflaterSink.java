package okio;

import java.io.IOException;
import java.util.zip.Deflater;

public final class DeflaterSink
  implements Sink
{
  private boolean closed;
  private final Deflater deflater;
  private final BufferedSink sink;
  
  private DeflaterSink(BufferedSink paramBufferedSink, Deflater paramDeflater)
  {
    if (paramBufferedSink == null) {
      throw new IllegalArgumentException("source == null");
    }
    if (paramDeflater == null) {
      throw new IllegalArgumentException("inflater == null");
    }
    this.sink = paramBufferedSink;
    this.deflater = paramDeflater;
  }
  
  public DeflaterSink(Sink paramSink, Deflater paramDeflater)
  {
    this(Okio.buffer(paramSink), paramDeflater);
  }
  
  private void deflate(boolean paramBoolean)
    throws IOException
  {
    Buffer localBuffer = this.sink.buffer();
    label115:
    do
    {
      Segment localSegment = localBuffer.writableSegment(1);
      if (paramBoolean) {}
      for (int i = this.deflater.deflate(localSegment.data, localSegment.limit, 2048 - localSegment.limit, 2);; i = this.deflater.deflate(localSegment.data, localSegment.limit, 2048 - localSegment.limit))
      {
        if (i <= 0) {
          break label115;
        }
        localSegment.limit = (i + localSegment.limit);
        localBuffer.size += i;
        this.sink.emitCompleteSegments();
        break;
      }
    } while (!this.deflater.needsInput());
  }
  
  public final void close()
    throws IOException
  {
    if (this.closed) {}
    for (;;)
    {
      return;
      Throwable localThrowable1 = null;
      try
      {
        this.deflater.finish();
        deflate(false);
      }
      catch (Throwable localThrowable3)
      {
        try
        {
          this.deflater.end();
        }
        catch (Throwable localThrowable3)
        {
          try
          {
            for (;;)
            {
              this.sink.close();
              this.closed = true;
              if (localThrowable1 == null) {
                break;
              }
              Util.sneakyRethrow(localThrowable1);
              return;
              localThrowable2 = localThrowable2;
              continue;
              localThrowable3 = localThrowable3;
              if (localThrowable2 == null) {
                localObject = localThrowable3;
              }
            }
          }
          catch (Throwable localThrowable4)
          {
            for (;;)
            {
              Object localObject;
              if (localObject == null) {
                localObject = localThrowable4;
              }
            }
          }
        }
      }
    }
  }
  
  public final void flush()
    throws IOException
  {
    deflate(true);
    this.sink.flush();
  }
  
  public final Timeout timeout()
  {
    return this.sink.timeout();
  }
  
  public final String toString()
  {
    return "DeflaterSink(" + this.sink + ")";
  }
  
  public final void write(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    Util.checkOffsetAndCount(paramBuffer.size, 0L, paramLong);
    while (paramLong > 0L)
    {
      Segment localSegment = paramBuffer.head;
      int i = (int)Math.min(paramLong, localSegment.limit - localSegment.pos);
      this.deflater.setInput(localSegment.data, localSegment.pos, i);
      deflate(false);
      paramBuffer.size -= i;
      localSegment.pos = (i + localSegment.pos);
      if (localSegment.pos == localSegment.limit)
      {
        paramBuffer.head = localSegment.pop();
        SegmentPool.INSTANCE.recycle(localSegment);
      }
      paramLong -= i;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.DeflaterSink
 * JD-Core Version:    0.7.0.1
 */