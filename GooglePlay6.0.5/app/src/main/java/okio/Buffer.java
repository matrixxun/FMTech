package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Buffer
  implements Cloneable, BufferedSink, BufferedSource
{
  Segment head;
  public long size;
  
  private void readFully(byte[] paramArrayOfByte)
    throws EOFException
  {
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      int j = read(paramArrayOfByte, i, paramArrayOfByte.length - i);
      if (j == -1) {
        throw new EOFException();
      }
      i += j;
    }
  }
  
  private String readUtf8(long paramLong)
    throws EOFException
  {
    Charset localCharset = Util.UTF_8;
    Util.checkOffsetAndCount(this.size, 0L, paramLong);
    if (localCharset == null) {
      throw new IllegalArgumentException("charset == null");
    }
    if (paramLong > 2147483647L) {
      throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + paramLong);
    }
    String str;
    if (paramLong == 0L) {
      str = "";
    }
    Segment localSegment;
    do
    {
      return str;
      localSegment = this.head;
      if (paramLong + localSegment.pos > localSegment.limit) {
        return new String(readByteArray(paramLong), localCharset);
      }
      str = new String(localSegment.data, localSegment.pos, (int)paramLong, localCharset);
      localSegment.pos = ((int)(paramLong + localSegment.pos));
      this.size -= paramLong;
    } while (localSegment.pos != localSegment.limit);
    this.head = localSegment.pop();
    SegmentPool.INSTANCE.recycle(localSegment);
    return str;
  }
  
  public final Buffer buffer()
  {
    return this;
  }
  
  public final void clear()
  {
    try
    {
      skip(this.size);
      return;
    }
    catch (EOFException localEOFException)
    {
      throw new AssertionError(localEOFException);
    }
  }
  
  public final Buffer clone()
  {
    Buffer localBuffer = new Buffer();
    if (this.size == 0L) {}
    for (;;)
    {
      return localBuffer;
      localBuffer.write(this.head.data, this.head.pos, this.head.limit - this.head.pos);
      for (Segment localSegment = this.head.next; localSegment != this.head; localSegment = localSegment.next) {
        localBuffer.write(localSegment.data, localSegment.pos, localSegment.limit - localSegment.pos);
      }
    }
  }
  
  public final void close() {}
  
  public final Buffer copyTo(OutputStream paramOutputStream, long paramLong1, long paramLong2)
    throws IOException
  {
    Util.checkOffsetAndCount(this.size, 0L, paramLong2);
    if (paramLong2 == 0L) {}
    for (;;)
    {
      return this;
      for (Segment localSegment = this.head; paramLong1 >= localSegment.limit - localSegment.pos; localSegment = localSegment.next) {
        paramLong1 -= localSegment.limit - localSegment.pos;
      }
      while (paramLong2 > 0L)
      {
        int i = (int)(paramLong1 + localSegment.pos);
        int j = (int)Math.min(localSegment.limit - i, paramLong2);
        paramOutputStream.write(localSegment.data, i, j);
        paramLong2 -= j;
        paramLong1 = 0L;
        localSegment = localSegment.next;
      }
    }
  }
  
  public final Buffer copyTo(Buffer paramBuffer, long paramLong1, long paramLong2)
  {
    if (paramBuffer == null) {
      throw new IllegalArgumentException("out == null");
    }
    Util.checkOffsetAndCount(this.size, paramLong1, paramLong2);
    if (paramLong2 == 0L) {}
    for (;;)
    {
      return this;
      Segment localSegment1 = this.head;
      Segment localSegment2 = paramBuffer.writableSegment(1);
      paramBuffer.size = (paramLong2 + paramBuffer.size);
      while (paramLong2 > 0L)
      {
        while (paramLong1 >= localSegment1.limit - localSegment1.pos)
        {
          paramLong1 -= localSegment1.limit - localSegment1.pos;
          localSegment1 = localSegment1.next;
        }
        if (localSegment2.limit == 2048) {
          localSegment2 = localSegment2.push(SegmentPool.INSTANCE.take());
        }
        int i = (int)Math.min(Math.min(localSegment1.limit - (paramLong1 + localSegment1.pos), paramLong2), 2048 - localSegment2.limit);
        System.arraycopy(localSegment1.data, localSegment1.pos + (int)paramLong1, localSegment2.data, localSegment2.limit, i);
        paramLong1 += i;
        localSegment2.limit = (i + localSegment2.limit);
        paramLong2 -= i;
      }
    }
  }
  
  public final BufferedSink emit()
    throws IOException
  {
    return this;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Buffer)) {
      return false;
    }
    Buffer localBuffer = (Buffer)paramObject;
    if (this.size != localBuffer.size) {
      return false;
    }
    if (this.size == 0L) {
      return true;
    }
    Segment localSegment1 = this.head;
    Segment localSegment2 = localBuffer.head;
    int i = localSegment1.pos;
    int j = localSegment2.pos;
    long l1 = 0L;
    long l2;
    int m;
    int n;
    if (l1 < this.size)
    {
      l2 = Math.min(localSegment1.limit - i, localSegment2.limit - j);
      int k = 0;
      m = j;
      int i1;
      for (n = i; k < l2; n = i1)
      {
        byte[] arrayOfByte1 = localSegment1.data;
        i1 = n + 1;
        int i2 = arrayOfByte1[n];
        byte[] arrayOfByte2 = localSegment2.data;
        int i3 = m + 1;
        if (i2 != arrayOfByte2[m]) {
          return false;
        }
        k++;
        m = i3;
      }
      if (n != localSegment1.limit) {
        break label245;
      }
      localSegment1 = localSegment1.next;
    }
    label245:
    for (i = localSegment1.pos;; i = n)
    {
      if (m == localSegment2.limit) {
        localSegment2 = localSegment2.next;
      }
      for (j = localSegment2.pos;; j = m)
      {
        l1 += l2;
        break;
        return true;
      }
    }
  }
  
  public final boolean exhausted()
  {
    return this.size == 0L;
  }
  
  public final void flush() {}
  
  public final byte getByte(long paramLong)
  {
    Util.checkOffsetAndCount(this.size, paramLong, 1L);
    for (Segment localSegment = this.head;; localSegment = localSegment.next)
    {
      int i = localSegment.limit - localSegment.pos;
      if (paramLong < i) {
        return localSegment.data[(localSegment.pos + (int)paramLong)];
      }
      paramLong -= i;
    }
  }
  
  public final int hashCode()
  {
    Segment localSegment = this.head;
    if (localSegment == null) {
      return 0;
    }
    int i = 1;
    do
    {
      int j = localSegment.pos;
      int k = localSegment.limit;
      while (j < k)
      {
        i = i * 31 + localSegment.data[j];
        j++;
      }
      localSegment = localSegment.next;
    } while (localSegment != this.head);
    return i;
  }
  
  public final long indexOf(byte paramByte)
  {
    return indexOf(paramByte, 0L);
  }
  
  public final long indexOf(byte paramByte, long paramLong)
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("fromIndex < 0");
    }
    Segment localSegment = this.head;
    if (localSegment == null) {
      return -1L;
    }
    long l1 = 0L;
    int i = localSegment.limit - localSegment.pos;
    if (paramLong >= i) {}
    for (paramLong -= i;; paramLong = 0L)
    {
      l1 += i;
      localSegment = localSegment.next;
      if (localSegment != this.head) {
        break;
      }
      return -1L;
      byte[] arrayOfByte = localSegment.data;
      long l2 = paramLong + localSegment.pos;
      long l3 = localSegment.limit;
      while (l2 < l3)
      {
        if (arrayOfByte[((int)l2)] == paramByte) {
          return l1 + l2 - localSegment.pos;
        }
        l2 += 1L;
      }
    }
  }
  
  public final InputStream inputStream()
  {
    new InputStream()
    {
      public final int available()
      {
        return (int)Math.min(Buffer.this.size, 2147483647L);
      }
      
      public final void close() {}
      
      public final int read()
      {
        if (Buffer.this.size > 0L) {
          return 0xFF & Buffer.this.readByte();
        }
        return -1;
      }
      
      public final int read(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return Buffer.this.read(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
      
      public final String toString()
      {
        return Buffer.this + ".inputStream()";
      }
    };
  }
  
  public final int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    Segment localSegment = this.head;
    int i;
    if (localSegment == null) {
      i = -1;
    }
    do
    {
      return i;
      i = Math.min(paramInt2, localSegment.limit - localSegment.pos);
      System.arraycopy(localSegment.data, localSegment.pos, paramArrayOfByte, paramInt1, i);
      localSegment.pos = (i + localSegment.pos);
      this.size -= i;
    } while (localSegment.pos != localSegment.limit);
    this.head = localSegment.pop();
    SegmentPool.INSTANCE.recycle(localSegment);
    return i;
  }
  
  public final long read(Buffer paramBuffer, long paramLong)
  {
    if (paramBuffer == null) {
      throw new IllegalArgumentException("sink == null");
    }
    if (paramLong < 0L) {
      throw new IllegalArgumentException("byteCount < 0: " + paramLong);
    }
    if (this.size == 0L) {
      return -1L;
    }
    if (paramLong > this.size) {
      paramLong = this.size;
    }
    paramBuffer.write(this, paramLong);
    return paramLong;
  }
  
  public final byte readByte()
  {
    if (this.size == 0L) {
      throw new IllegalStateException("size == 0");
    }
    Segment localSegment = this.head;
    int i = localSegment.pos;
    int j = localSegment.limit;
    byte[] arrayOfByte = localSegment.data;
    int k = i + 1;
    byte b = arrayOfByte[i];
    this.size -= 1L;
    if (k == j)
    {
      this.head = localSegment.pop();
      SegmentPool.INSTANCE.recycle(localSegment);
      return b;
    }
    localSegment.pos = k;
    return b;
  }
  
  public final byte[] readByteArray()
  {
    try
    {
      byte[] arrayOfByte = readByteArray(this.size);
      return arrayOfByte;
    }
    catch (EOFException localEOFException)
    {
      throw new AssertionError(localEOFException);
    }
  }
  
  public final byte[] readByteArray(long paramLong)
    throws EOFException
  {
    Util.checkOffsetAndCount(this.size, 0L, paramLong);
    if (paramLong > 2147483647L) {
      throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + paramLong);
    }
    byte[] arrayOfByte = new byte[(int)paramLong];
    readFully(arrayOfByte);
    return arrayOfByte;
  }
  
  public final ByteString readByteString()
  {
    return new ByteString(readByteArray());
  }
  
  public final ByteString readByteString(long paramLong)
    throws EOFException
  {
    return new ByteString(readByteArray(paramLong));
  }
  
  public final int readInt()
  {
    if (this.size < 4L) {
      throw new IllegalStateException("size < 4: " + this.size);
    }
    Segment localSegment = this.head;
    int i = localSegment.pos;
    int j = localSegment.limit;
    if (j - i < 4) {
      return (0xFF & readByte()) << 24 | (0xFF & readByte()) << 16 | (0xFF & readByte()) << 8 | 0xFF & readByte();
    }
    byte[] arrayOfByte = localSegment.data;
    int k = i + 1;
    int m = (0xFF & arrayOfByte[i]) << 24;
    int n = k + 1;
    int i1 = m | (0xFF & arrayOfByte[k]) << 16;
    int i2 = n + 1;
    int i3 = i1 | (0xFF & arrayOfByte[n]) << 8;
    int i4 = i2 + 1;
    int i5 = i3 | 0xFF & arrayOfByte[i2];
    this.size -= 4L;
    if (i4 == j)
    {
      this.head = localSegment.pop();
      SegmentPool.INSTANCE.recycle(localSegment);
      return i5;
    }
    localSegment.pos = i4;
    return i5;
  }
  
  public final int readIntLe()
  {
    return Util.reverseBytesInt(readInt());
  }
  
  public final short readShort()
  {
    if (this.size < 2L) {
      throw new IllegalStateException("size < 2: " + this.size);
    }
    Segment localSegment = this.head;
    int i = localSegment.pos;
    int j = localSegment.limit;
    if (j - i < 2) {
      return (short)((0xFF & readByte()) << 8 | 0xFF & readByte());
    }
    byte[] arrayOfByte = localSegment.data;
    int k = i + 1;
    int m = (0xFF & arrayOfByte[i]) << 8;
    int n = k + 1;
    int i1 = m | 0xFF & arrayOfByte[k];
    this.size -= 2L;
    if (n == j)
    {
      this.head = localSegment.pop();
      SegmentPool.INSTANCE.recycle(localSegment);
    }
    for (;;)
    {
      return (short)i1;
      localSegment.pos = n;
    }
  }
  
  public final short readShortLe()
  {
    return Util.reverseBytesShort(readShort());
  }
  
  final String readUtf8Line(long paramLong)
    throws EOFException
  {
    if ((paramLong > 0L) && (getByte(paramLong - 1L) == 13))
    {
      String str2 = readUtf8(paramLong - 1L);
      skip(2L);
      return str2;
    }
    String str1 = readUtf8(paramLong);
    skip(1L);
    return str1;
  }
  
  public final String readUtf8LineStrict()
    throws EOFException
  {
    long l = indexOf((byte)10, 0L);
    if (l == -1L)
    {
      Buffer localBuffer = new Buffer();
      copyTo(localBuffer, 0L, Math.min(32L, this.size));
      throw new EOFException("\\n not found: size=" + this.size + " content=" + localBuffer.readByteString().hex() + "...");
    }
    return readUtf8Line(l);
  }
  
  public final void require(long paramLong)
    throws EOFException
  {
    if (this.size < paramLong) {
      throw new EOFException();
    }
  }
  
  public final void skip(long paramLong)
    throws EOFException
  {
    while (paramLong > 0L)
    {
      if (this.head == null) {
        throw new EOFException();
      }
      int i = (int)Math.min(paramLong, this.head.limit - this.head.pos);
      this.size -= i;
      paramLong -= i;
      Segment localSegment1 = this.head;
      localSegment1.pos = (i + localSegment1.pos);
      if (this.head.pos == this.head.limit)
      {
        Segment localSegment2 = this.head;
        this.head = localSegment2.pop();
        SegmentPool.INSTANCE.recycle(localSegment2);
      }
    }
  }
  
  public final Timeout timeout()
  {
    return Timeout.NONE;
  }
  
  public final String toString()
  {
    if (this.size == 0L) {
      return "Buffer[size=0]";
    }
    if (this.size <= 16L)
    {
      ByteString localByteString = clone().readByteString();
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Long.valueOf(this.size);
      arrayOfObject2[1] = localByteString.hex();
      return String.format("Buffer[size=%s data=%s]", arrayOfObject2);
    }
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
      for (Segment localSegment = this.head.next; localSegment != this.head; localSegment = localSegment.next) {
        localMessageDigest.update(localSegment.data, localSegment.pos, localSegment.limit - localSegment.pos);
      }
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = Long.valueOf(this.size);
      arrayOfObject1[1] = ByteString.of(localMessageDigest.digest()).hex();
      String str = String.format("Buffer[size=%s md5=%s]", arrayOfObject1);
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new AssertionError();
    }
  }
  
  final Segment writableSegment(int paramInt)
  {
    if ((paramInt <= 0) || (paramInt > 2048)) {
      throw new IllegalArgumentException();
    }
    Segment localSegment1;
    if (this.head == null)
    {
      this.head = SegmentPool.INSTANCE.take();
      Segment localSegment2 = this.head;
      Segment localSegment3 = this.head;
      localSegment1 = this.head;
      localSegment3.prev = localSegment1;
      localSegment2.next = localSegment1;
    }
    do
    {
      return localSegment1;
      localSegment1 = this.head.prev;
    } while (paramInt + localSegment1.limit <= 2048);
    return localSegment1.push(SegmentPool.INSTANCE.take());
  }
  
  public final Buffer write(ByteString paramByteString)
  {
    if (paramByteString == null) {
      throw new IllegalArgumentException("byteString == null");
    }
    return write(paramByteString.data, 0, paramByteString.data.length);
  }
  
  public final Buffer write(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      throw new IllegalArgumentException("source == null");
    }
    return write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public final Buffer write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramArrayOfByte == null) {
      throw new IllegalArgumentException("source == null");
    }
    Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    int i = paramInt1 + paramInt2;
    while (paramInt1 < i)
    {
      Segment localSegment = writableSegment(1);
      int j = Math.min(i - paramInt1, 2048 - localSegment.limit);
      System.arraycopy(paramArrayOfByte, paramInt1, localSegment.data, localSegment.limit, j);
      paramInt1 += j;
      localSegment.limit = (j + localSegment.limit);
    }
    this.size += paramInt2;
    return this;
  }
  
  public final void write(Buffer paramBuffer, long paramLong)
  {
    if (paramBuffer == null) {
      throw new IllegalArgumentException("source == null");
    }
    if (paramBuffer == this) {
      throw new IllegalArgumentException("source == this");
    }
    Util.checkOffsetAndCount(paramBuffer.size, 0L, paramLong);
    Segment localSegment6;
    Segment localSegment7;
    int i;
    int j;
    Segment localSegment9;
    label236:
    Segment localSegment1;
    long l;
    if (paramLong > 0L) {
      if (paramLong < paramBuffer.head.limit - paramBuffer.head.pos)
      {
        if (this.head != null) {}
        for (localSegment6 = this.head.prev;; localSegment6 = null)
        {
          if ((localSegment6 != null) && (paramLong + (localSegment6.limit - localSegment6.pos) <= 2048L)) {
            break label423;
          }
          localSegment7 = paramBuffer.head;
          i = (int)paramLong;
          j = localSegment7.limit - localSegment7.pos - i;
          if ((i > 0) && (j > 0)) {
            break;
          }
          throw new IllegalArgumentException();
        }
        if (i < j)
        {
          localSegment9 = SegmentPool.INSTANCE.take();
          System.arraycopy(localSegment7.data, localSegment7.pos, localSegment9.data, localSegment9.pos, i);
          localSegment7.pos = (i + localSegment7.pos);
          localSegment9.limit = (i + localSegment9.limit);
          localSegment7.prev.push(localSegment9);
          paramBuffer.head = localSegment9;
        }
      }
      else
      {
        localSegment1 = paramBuffer.head;
        l = localSegment1.limit - localSegment1.pos;
        paramBuffer.head = localSegment1.pop();
        if (this.head != null) {
          break label455;
        }
        this.head = localSegment1;
        Segment localSegment3 = this.head;
        Segment localSegment4 = this.head;
        Segment localSegment5 = this.head;
        localSegment4.prev = localSegment5;
        localSegment3.next = localSegment5;
      }
    }
    for (;;)
    {
      paramBuffer.size -= l;
      this.size = (l + this.size);
      paramLong -= l;
      break;
      Segment localSegment8 = SegmentPool.INSTANCE.take();
      System.arraycopy(localSegment7.data, i + localSegment7.pos, localSegment8.data, localSegment8.pos, j);
      localSegment7.limit -= j;
      localSegment8.limit = (j + localSegment8.limit);
      localSegment7.push(localSegment8);
      localSegment9 = localSegment7;
      break label236;
      label423:
      paramBuffer.head.writeTo(localSegment6, (int)paramLong);
      paramBuffer.size -= paramLong;
      this.size = (paramLong + this.size);
      return;
      label455:
      Segment localSegment2 = this.head.prev.push(localSegment1);
      if (localSegment2.prev == localSegment2) {
        throw new IllegalStateException();
      }
      if (localSegment2.prev.limit - localSegment2.prev.pos + (localSegment2.limit - localSegment2.pos) <= 2048)
      {
        localSegment2.writeTo(localSegment2.prev, localSegment2.limit - localSegment2.pos);
        localSegment2.pop();
        SegmentPool.INSTANCE.recycle(localSegment2);
      }
    }
  }
  
  public final long writeAll(Source paramSource)
    throws IOException
  {
    if (paramSource == null) {
      throw new IllegalArgumentException("source == null");
    }
    long l2;
    for (long l1 = 0L;; l1 += l2)
    {
      l2 = paramSource.read(this, 2048L);
      if (l2 == -1L) {
        break;
      }
    }
    return l1;
  }
  
  public final Buffer writeByte(int paramInt)
  {
    Segment localSegment = writableSegment(1);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    localSegment.limit = (i + 1);
    arrayOfByte[i] = ((byte)paramInt);
    this.size = (1L + this.size);
    return this;
  }
  
  public final Buffer writeInt(int paramInt)
  {
    Segment localSegment = writableSegment(4);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(0xFF & paramInt >>> 24));
    int k = j + 1;
    arrayOfByte[j] = ((byte)(0xFF & paramInt >>> 16));
    int m = k + 1;
    arrayOfByte[k] = ((byte)(0xFF & paramInt >>> 8));
    int n = m + 1;
    arrayOfByte[m] = ((byte)(paramInt & 0xFF));
    localSegment.limit = n;
    this.size = (4L + this.size);
    return this;
  }
  
  public final Buffer writeShort(int paramInt)
  {
    Segment localSegment = writableSegment(2);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(0xFF & paramInt >>> 8));
    int k = j + 1;
    arrayOfByte[j] = ((byte)(paramInt & 0xFF));
    localSegment.limit = k;
    this.size = (2L + this.size);
    return this;
  }
  
  public final Buffer writeTo(OutputStream paramOutputStream, long paramLong)
    throws IOException
  {
    Util.checkOffsetAndCount(this.size, 0L, paramLong);
    Segment localSegment1 = this.head;
    while (paramLong > 0L)
    {
      int i = (int)Math.min(paramLong, localSegment1.limit - localSegment1.pos);
      paramOutputStream.write(localSegment1.data, localSegment1.pos, i);
      localSegment1.pos = (i + localSegment1.pos);
      this.size -= i;
      paramLong -= i;
      if (localSegment1.pos == localSegment1.limit)
      {
        Segment localSegment2 = localSegment1;
        localSegment1 = localSegment2.pop();
        this.head = localSegment1;
        SegmentPool.INSTANCE.recycle(localSegment2);
      }
    }
    return this;
  }
  
  public final Buffer writeUtf8(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("string == null");
    }
    int i = paramString.length();
    int j = 0;
    while (j < i)
    {
      int k = paramString.charAt(j);
      if (k < 128)
      {
        Segment localSegment = writableSegment(1);
        byte[] arrayOfByte = localSegment.data;
        int i1 = localSegment.limit - j;
        int i2 = Math.min(i, 2048 - i1);
        int i3 = j + 1;
        arrayOfByte[(i1 + j)] = ((byte)k);
        int i6;
        for (j = i3; j < i2; j = i6)
        {
          int i5 = paramString.charAt(j);
          if (i5 >= 128) {
            break;
          }
          i6 = j + 1;
          arrayOfByte[(i1 + j)] = ((byte)i5);
        }
        int i4 = j + i1 - localSegment.limit;
        localSegment.limit = (i4 + localSegment.limit);
        this.size += i4;
      }
      else if (k < 2048)
      {
        writeByte(0xC0 | k >> 6);
        writeByte(0x80 | k & 0x3F);
        j++;
      }
      else if ((k < 55296) || (k > 57343))
      {
        writeByte(0xE0 | k >> 12);
        writeByte(0x80 | 0x3F & k >> 6);
        writeByte(0x80 | k & 0x3F);
        j++;
      }
      else
      {
        if (j + 1 < i) {}
        for (int m = paramString.charAt(j + 1);; m = 0)
        {
          if ((k <= 56319) && (m >= 56320) && (m <= 57343)) {
            break label345;
          }
          writeByte(63);
          j++;
          break;
        }
        label345:
        int n = 65536 + ((0xFFFF27FF & k) << 10 | 0xFFFF23FF & m);
        writeByte(0xF0 | n >> 18);
        writeByte(0x80 | 0x3F & n >> 12);
        writeByte(0x80 | 0x3F & n >> 6);
        writeByte(0x80 | n & 0x3F);
        j += 2;
      }
    }
    return this;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.Buffer
 * JD-Core Version:    0.7.0.1
 */