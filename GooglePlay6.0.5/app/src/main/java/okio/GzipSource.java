package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

public final class GzipSource
  implements Source
{
  private final CRC32 crc = new CRC32();
  private final Inflater inflater;
  private final InflaterSource inflaterSource;
  private int section = 0;
  private final BufferedSource source;
  
  public GzipSource(Source paramSource)
  {
    if (paramSource == null) {
      throw new IllegalArgumentException("source == null");
    }
    this.inflater = new Inflater(true);
    this.source = Okio.buffer(paramSource);
    this.inflaterSource = new InflaterSource(this.source, this.inflater);
  }
  
  private static void checkEqual(String paramString, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramInt2 != paramInt1)
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = paramString;
      arrayOfObject[1] = Integer.valueOf(paramInt2);
      arrayOfObject[2] = Integer.valueOf(paramInt1);
      throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", arrayOfObject));
    }
  }
  
  private void updateCrc(Buffer paramBuffer, long paramLong1, long paramLong2)
  {
    for (Segment localSegment = paramBuffer.head; paramLong1 >= localSegment.limit - localSegment.pos; localSegment = localSegment.next) {
      paramLong1 -= localSegment.limit - localSegment.pos;
    }
    while (paramLong2 > 0L)
    {
      int i = (int)(paramLong1 + localSegment.pos);
      int j = (int)Math.min(localSegment.limit - i, paramLong2);
      this.crc.update(localSegment.data, i, j);
      paramLong2 -= j;
      paramLong1 = 0L;
      localSegment = localSegment.next;
    }
  }
  
  public final void close()
    throws IOException
  {
    this.inflaterSource.close();
  }
  
  public final long read(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("byteCount < 0: " + paramLong);
    }
    if (paramLong == 0L) {
      return 0L;
    }
    if (this.section == 0)
    {
      this.source.require(10L);
      int i = this.source.buffer().getByte(3L);
      if ((0x1 & i >> 1) == 1) {}
      long l4;
      for (int j = 1;; j = 0)
      {
        if (j != 0) {
          updateCrc(this.source.buffer(), 0L, 10L);
        }
        checkEqual("ID1ID2", 8075, this.source.readShort());
        this.source.skip(8L);
        if ((0x1 & i >> 2) == 1)
        {
          this.source.require(2L);
          if (j != 0) {
            updateCrc(this.source.buffer(), 0L, 2L);
          }
          int k = this.source.buffer().readShortLe();
          this.source.require(k);
          if (j != 0) {
            updateCrc(this.source.buffer(), 0L, k);
          }
          this.source.skip(k);
        }
        if ((0x1 & i >> 3) != 1) {
          break label323;
        }
        l4 = this.source.indexOf((byte)0);
        if (l4 != -1L) {
          break;
        }
        throw new EOFException();
      }
      if (j != 0) {
        updateCrc(this.source.buffer(), 0L, l4 + 1L);
      }
      this.source.skip(l4 + 1L);
      label323:
      if ((0x1 & i >> 4) == 1)
      {
        long l3 = this.source.indexOf((byte)0);
        if (l3 == -1L) {
          throw new EOFException();
        }
        if (j != 0) {
          updateCrc(this.source.buffer(), 0L, l3 + 1L);
        }
        this.source.skip(l3 + 1L);
      }
      if (j != 0)
      {
        checkEqual("FHCRC", this.source.readShortLe(), (short)(int)this.crc.getValue());
        this.crc.reset();
      }
      this.section = 1;
    }
    if (this.section == 1)
    {
      long l1 = paramBuffer.size;
      long l2 = this.inflaterSource.read(paramBuffer, paramLong);
      if (l2 != -1L)
      {
        updateCrc(paramBuffer, l1, l2);
        return l2;
      }
      this.section = 2;
    }
    if (this.section == 2)
    {
      checkEqual("CRC", this.source.readIntLe(), (int)this.crc.getValue());
      checkEqual("ISIZE", this.source.readIntLe(), this.inflater.getTotalOut());
      this.section = 3;
      if (!this.source.exhausted()) {
        throw new IOException("gzip finished without exhausting source");
      }
    }
    return -1L;
  }
  
  public final Timeout timeout()
  {
    return this.source.timeout();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.GzipSource
 * JD-Core Version:    0.7.0.1
 */