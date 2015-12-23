package okio;

import java.io.Closeable;
import java.io.IOException;

public abstract interface Sink
  extends Closeable
{
  public abstract void close()
    throws IOException;
  
  public abstract void flush()
    throws IOException;
  
  public abstract Timeout timeout();
  
  public abstract void write(Buffer paramBuffer, long paramLong)
    throws IOException;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.Sink
 * JD-Core Version:    0.7.0.1
 */