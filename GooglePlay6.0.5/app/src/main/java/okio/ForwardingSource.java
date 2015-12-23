package okio;

import java.io.IOException;

public abstract class ForwardingSource
  implements Source
{
  private final Source delegate;
  
  public ForwardingSource(Source paramSource)
  {
    if (paramSource == null) {
      throw new IllegalArgumentException("delegate == null");
    }
    this.delegate = paramSource;
  }
  
  public void close()
    throws IOException
  {
    this.delegate.close();
  }
  
  public long read(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    return this.delegate.read(paramBuffer, paramLong);
  }
  
  public final Timeout timeout()
  {
    return this.delegate.timeout();
  }
  
  public String toString()
  {
    return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.ForwardingSource
 * JD-Core Version:    0.7.0.1
 */