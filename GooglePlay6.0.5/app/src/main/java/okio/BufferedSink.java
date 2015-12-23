package okio;

import java.io.IOException;

public abstract interface BufferedSink
  extends Sink
{
  public abstract Buffer buffer();
  
  public abstract BufferedSink emit()
    throws IOException;
  
  public abstract BufferedSink emitCompleteSegments()
    throws IOException;
  
  public abstract BufferedSink write(ByteString paramByteString)
    throws IOException;
  
  public abstract BufferedSink write(byte[] paramArrayOfByte)
    throws IOException;
  
  public abstract BufferedSink write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException;
  
  public abstract long writeAll(Source paramSource)
    throws IOException;
  
  public abstract BufferedSink writeByte(int paramInt)
    throws IOException;
  
  public abstract BufferedSink writeInt(int paramInt)
    throws IOException;
  
  public abstract BufferedSink writeShort(int paramInt)
    throws IOException;
  
  public abstract BufferedSink writeUtf8(String paramString)
    throws IOException;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.BufferedSink
 * JD-Core Version:    0.7.0.1
 */