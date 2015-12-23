package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

final class RealBufferedSource
  implements BufferedSource
{
  public final Buffer buffer;
  boolean closed;
  public final Source source;
  
  public RealBufferedSource(Source paramSource)
  {
    this(paramSource, new Buffer());
  }
  
  private RealBufferedSource(Source paramSource, Buffer paramBuffer)
  {
    if (paramSource == null) {
      throw new IllegalArgumentException("source == null");
    }
    this.buffer = paramBuffer;
    this.source = paramSource;
  }
  
  public final Buffer buffer()
  {
    return this.buffer;
  }
  
  public final void close()
    throws IOException
  {
    if (this.closed) {
      return;
    }
    this.closed = true;
    this.source.close();
    this.buffer.clear();
  }
  
  public final boolean exhausted()
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    return (this.buffer.exhausted()) && (this.source.read(this.buffer, 2048L) == -1L);
  }
  
  public final long indexOf(byte paramByte)
    throws IOException
  {
    long l1 = 0L;
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    long l2;
    while (l1 >= this.buffer.size) {
      if (this.source.read(this.buffer, 2048L) == -1L)
      {
        l2 = -1L;
        return l2;
      }
    }
    do
    {
      l2 = this.buffer.indexOf(paramByte, l1);
      if (l2 != -1L) {
        break;
      }
      l1 = this.buffer.size;
    } while (this.source.read(this.buffer, 2048L) != -1L);
    return -1L;
  }
  
  public final InputStream inputStream()
  {
    new InputStream()
    {
      public final int available()
        throws IOException
      {
        if (RealBufferedSource.this.closed) {
          throw new IOException("closed");
        }
        return (int)Math.min(RealBufferedSource.this.buffer.size, 2147483647L);
      }
      
      public final void close()
        throws IOException
      {
        RealBufferedSource.this.close();
      }
      
      public final int read()
        throws IOException
      {
        if (RealBufferedSource.this.closed) {
          throw new IOException("closed");
        }
        if ((RealBufferedSource.this.buffer.size == 0L) && (RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 2048L) == -1L)) {
          return -1;
        }
        return 0xFF & RealBufferedSource.this.buffer.readByte();
      }
      
      public final int read(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
        throws IOException
      {
        if (RealBufferedSource.this.closed) {
          throw new IOException("closed");
        }
        Util.checkOffsetAndCount(paramAnonymousArrayOfByte.length, paramAnonymousInt1, paramAnonymousInt2);
        if ((RealBufferedSource.this.buffer.size == 0L) && (RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 2048L) == -1L)) {
          return -1;
        }
        return RealBufferedSource.this.buffer.read(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
      
      public final String toString()
      {
        return RealBufferedSource.this + ".inputStream()";
      }
    };
  }
  
  public final long read(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    if (paramBuffer == null) {
      throw new IllegalArgumentException("sink == null");
    }
    if (paramLong < 0L) {
      throw new IllegalArgumentException("byteCount < 0: " + paramLong);
    }
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    if ((this.buffer.size == 0L) && (this.source.read(this.buffer, 2048L) == -1L)) {
      return -1L;
    }
    long l = Math.min(paramLong, this.buffer.size);
    return this.buffer.read(paramBuffer, l);
  }
  
  public final byte readByte()
    throws IOException
  {
    require(1L);
    return this.buffer.readByte();
  }
  
  public final byte[] readByteArray(long paramLong)
    throws IOException
  {
    require(paramLong);
    return this.buffer.readByteArray(paramLong);
  }
  
  public final ByteString readByteString(long paramLong)
    throws IOException
  {
    require(paramLong);
    return this.buffer.readByteString(paramLong);
  }
  
  public final int readInt()
    throws IOException
  {
    require(4L);
    return this.buffer.readInt();
  }
  
  public final int readIntLe()
    throws IOException
  {
    require(4L);
    return Util.reverseBytesInt(this.buffer.readInt());
  }
  
  public final short readShort()
    throws IOException
  {
    require(2L);
    return this.buffer.readShort();
  }
  
  public final short readShortLe()
    throws IOException
  {
    require(2L);
    return Util.reverseBytesShort(this.buffer.readShort());
  }
  
  public final String readUtf8LineStrict()
    throws IOException
  {
    long l = indexOf((byte)10);
    if (l == -1L)
    {
      Buffer localBuffer = new Buffer();
      this.buffer.copyTo(localBuffer, 0L, Math.min(32L, this.buffer.size));
      throw new EOFException("\\n not found: size=" + this.buffer.size + " content=" + localBuffer.readByteString().hex() + "...");
    }
    return this.buffer.readUtf8Line(l);
  }
  
  public final void require(long paramLong)
    throws IOException
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("byteCount < 0: " + paramLong);
    }
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    do
    {
      if (this.buffer.size >= paramLong) {
        break;
      }
    } while (this.source.read(this.buffer, 2048L) != -1L);
    for (int i = 0; i == 0; i = 1) {
      throw new EOFException();
    }
  }
  
  public final void skip(long paramLong)
    throws IOException
  {
    if (this.closed) {
      throw new IllegalStateException("closed");
    }
    do
    {
      long l = Math.min(paramLong, this.buffer.size);
      this.buffer.skip(l);
      paramLong -= l;
      if (paramLong <= 0L) {
        break;
      }
    } while ((this.buffer.size != 0L) || (this.source.read(this.buffer, 2048L) != -1L));
    throw new EOFException();
  }
  
  public final Timeout timeout()
  {
    return this.source.timeout();
  }
  
  public final String toString()
  {
    return "buffer(" + this.source + ")";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.RealBufferedSource
 * JD-Core Version:    0.7.0.1
 */