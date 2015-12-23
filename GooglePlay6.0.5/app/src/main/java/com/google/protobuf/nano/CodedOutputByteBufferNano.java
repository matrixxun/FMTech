package com.google.protobuf.nano;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

public final class CodedOutputByteBufferNano
{
  final ByteBuffer buffer;
  
  private CodedOutputByteBufferNano(ByteBuffer paramByteBuffer)
  {
    this.buffer = paramByteBuffer;
    this.buffer.order(ByteOrder.LITTLE_ENDIAN);
  }
  
  CodedOutputByteBufferNano(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this(ByteBuffer.wrap(paramArrayOfByte, 0, paramInt2));
  }
  
  public static int computeBytesSize(int paramInt, byte[] paramArrayOfByte)
  {
    return computeTagSize(paramInt) + computeBytesSizeNoTag(paramArrayOfByte);
  }
  
  public static int computeBytesSizeNoTag(byte[] paramArrayOfByte)
  {
    return computeRawVarint32Size(paramArrayOfByte.length) + paramArrayOfByte.length;
  }
  
  public static int computeGroupSize(int paramInt, MessageNano paramMessageNano)
  {
    return 2 * computeTagSize(paramInt) + paramMessageNano.getSerializedSize();
  }
  
  public static int computeInt32Size(int paramInt1, int paramInt2)
  {
    return computeTagSize(paramInt1) + computeInt32SizeNoTag(paramInt2);
  }
  
  public static int computeInt32SizeNoTag(int paramInt)
  {
    if (paramInt >= 0) {
      return computeRawVarint32Size(paramInt);
    }
    return 10;
  }
  
  public static int computeInt64Size(int paramInt, long paramLong)
  {
    return computeTagSize(paramInt) + computeRawVarint64Size(paramLong);
  }
  
  public static int computeMessageSize(int paramInt, MessageNano paramMessageNano)
  {
    int i = computeTagSize(paramInt);
    int j = paramMessageNano.getSerializedSize();
    return i + (j + computeRawVarint32Size(j));
  }
  
  public static int computeRawVarint32Size(int paramInt)
  {
    if ((paramInt & 0xFFFFFF80) == 0) {
      return 1;
    }
    if ((paramInt & 0xFFFFC000) == 0) {
      return 2;
    }
    if ((0xFFE00000 & paramInt) == 0) {
      return 3;
    }
    if ((0xF0000000 & paramInt) == 0) {
      return 4;
    }
    return 5;
  }
  
  public static int computeRawVarint64Size(long paramLong)
  {
    if ((0xFFFFFF80 & paramLong) == 0L) {
      return 1;
    }
    if ((0xFFFFC000 & paramLong) == 0L) {
      return 2;
    }
    if ((0xFFE00000 & paramLong) == 0L) {
      return 3;
    }
    if ((0xF0000000 & paramLong) == 0L) {
      return 4;
    }
    if ((0x0 & paramLong) == 0L) {
      return 5;
    }
    if ((0x0 & paramLong) == 0L) {
      return 6;
    }
    if ((0x0 & paramLong) == 0L) {
      return 7;
    }
    if ((0x0 & paramLong) == 0L) {
      return 8;
    }
    if ((0x0 & paramLong) == 0L) {
      return 9;
    }
    return 10;
  }
  
  public static int computeStringSize(int paramInt, String paramString)
  {
    return computeTagSize(paramInt) + computeStringSizeNoTag(paramString);
  }
  
  public static int computeStringSizeNoTag(String paramString)
  {
    int i = encodedLength(paramString);
    return i + computeRawVarint32Size(i);
  }
  
  public static int computeTagSize(int paramInt)
  {
    return computeRawVarint32Size(WireFormatNano.makeTag(paramInt, 0));
  }
  
  public static int computeUInt32Size(int paramInt1, int paramInt2)
  {
    return computeTagSize(paramInt1) + computeRawVarint32Size(paramInt2);
  }
  
  public static int computeUInt64Size(int paramInt, long paramLong)
  {
    return computeTagSize(paramInt) + computeRawVarint64Size(paramLong);
  }
  
  private static int encode(CharSequence paramCharSequence, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramCharSequence.length();
    int j = 0;
    int k = paramInt1 + paramInt2;
    while ((j < i) && (j + paramInt1 < k))
    {
      int i11 = paramCharSequence.charAt(j);
      if (i11 >= 128) {
        break;
      }
      paramArrayOfByte[(paramInt1 + j)] = ((byte)i11);
      j++;
    }
    if (j == i) {
      return paramInt1 + i;
    }
    int m = paramInt1 + j;
    if (j < i)
    {
      int n = paramCharSequence.charAt(j);
      int i6;
      if ((n < 128) && (m < k))
      {
        i6 = m + 1;
        paramArrayOfByte[m] = ((byte)n);
      }
      for (;;)
      {
        j++;
        m = i6;
        break;
        if ((n < 2048) && (m <= k - 2))
        {
          int i9 = m + 1;
          paramArrayOfByte[m] = ((byte)(0x3C0 | n >>> 6));
          int i10 = i9 + 1;
          paramArrayOfByte[i9] = ((byte)(0x80 | n & 0x3F));
          i6 = i10;
        }
        else if (((n < 55296) || (57343 < n)) && (m <= k - 3))
        {
          int i7 = m + 1;
          paramArrayOfByte[m] = ((byte)(0x1E0 | n >>> 12));
          int i8 = i7 + 1;
          paramArrayOfByte[i7] = ((byte)(0x80 | 0x3F & n >>> 6));
          i6 = i8 + 1;
          paramArrayOfByte[i8] = ((byte)(0x80 | n & 0x3F));
        }
        else
        {
          if (m > k - 4) {
            break label465;
          }
          char c;
          if (j + 1 != paramCharSequence.length())
          {
            j++;
            c = paramCharSequence.charAt(j);
            if (Character.isSurrogatePair(n, c)) {}
          }
          else
          {
            throw new IllegalArgumentException("Unpaired surrogate at index " + (j - 1));
          }
          int i1 = Character.toCodePoint(n, c);
          int i2 = m + 1;
          paramArrayOfByte[m] = ((byte)(0xF0 | i1 >>> 18));
          int i3 = i2 + 1;
          paramArrayOfByte[i2] = ((byte)(0x80 | 0x3F & i1 >>> 12));
          int i4 = i3 + 1;
          paramArrayOfByte[i3] = ((byte)(0x80 | 0x3F & i1 >>> 6));
          int i5 = i4 + 1;
          paramArrayOfByte[i4] = ((byte)(0x80 | i1 & 0x3F));
          i6 = i5;
        }
      }
      label465:
      if ((55296 <= n) && (n <= 57343) && ((j + 1 == paramCharSequence.length()) || (!Character.isSurrogatePair(n, paramCharSequence.charAt(j + 1))))) {
        throw new IllegalArgumentException("Unpaired surrogate at index " + j);
      }
      throw new ArrayIndexOutOfBoundsException("Failed writing " + n + " at index " + m);
    }
    return m;
  }
  
  private static void encode(CharSequence paramCharSequence, ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer.isReadOnly()) {
      throw new ReadOnlyBufferException();
    }
    if (paramByteBuffer.hasArray()) {
      try
      {
        paramByteBuffer.position(encode(paramCharSequence, paramByteBuffer.array(), paramByteBuffer.arrayOffset() + paramByteBuffer.position(), paramByteBuffer.remaining()) - paramByteBuffer.arrayOffset());
        return;
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
      {
        BufferOverflowException localBufferOverflowException = new BufferOverflowException();
        localBufferOverflowException.initCause(localArrayIndexOutOfBoundsException);
        throw localBufferOverflowException;
      }
    }
    encodeDirect(paramCharSequence, paramByteBuffer);
  }
  
  private static void encodeDirect(CharSequence paramCharSequence, ByteBuffer paramByteBuffer)
  {
    int i = paramCharSequence.length();
    int j = 0;
    if (j < i)
    {
      int k = paramCharSequence.charAt(j);
      if (k < 128) {
        paramByteBuffer.put((byte)k);
      }
      for (;;)
      {
        j++;
        break;
        if (k < 2048)
        {
          paramByteBuffer.put((byte)(0x3C0 | k >>> 6));
          paramByteBuffer.put((byte)(0x80 | k & 0x3F));
        }
        else if ((k < 55296) || (57343 < k))
        {
          paramByteBuffer.put((byte)(0x1E0 | k >>> 12));
          paramByteBuffer.put((byte)(0x80 | 0x3F & k >>> 6));
          paramByteBuffer.put((byte)(0x80 | k & 0x3F));
        }
        else
        {
          char c;
          if (j + 1 != paramCharSequence.length())
          {
            j++;
            c = paramCharSequence.charAt(j);
            if (Character.isSurrogatePair(k, c)) {}
          }
          else
          {
            throw new IllegalArgumentException("Unpaired surrogate at index " + (j - 1));
          }
          int m = Character.toCodePoint(k, c);
          paramByteBuffer.put((byte)(0xF0 | m >>> 18));
          paramByteBuffer.put((byte)(0x80 | 0x3F & m >>> 12));
          paramByteBuffer.put((byte)(0x80 | 0x3F & m >>> 6));
          paramByteBuffer.put((byte)(0x80 | m & 0x3F));
        }
      }
    }
  }
  
  public static long encodeZigZag64(long paramLong)
  {
    return paramLong << 1 ^ paramLong >> 63;
  }
  
  private static int encodedLength(CharSequence paramCharSequence)
  {
    int i = paramCharSequence.length();
    int j = i;
    for (int k = 0; (k < i) && (paramCharSequence.charAt(k) < ''); k++) {}
    while (k < i)
    {
      int m = paramCharSequence.charAt(k);
      if (m < 2048)
      {
        j += (127 - m >>> 31);
        k++;
      }
      else
      {
        int n = paramCharSequence.length();
        int i1 = 0;
        int i2 = k;
        if (i2 < n)
        {
          int i3 = paramCharSequence.charAt(i2);
          if (i3 < 2048) {
            i1 += (127 - i3 >>> 31);
          }
          for (;;)
          {
            i2++;
            break;
            i1 += 2;
            if ((55296 <= i3) && (i3 <= 57343))
            {
              if (Character.codePointAt(paramCharSequence, i2) < 65536) {
                throw new IllegalArgumentException("Unpaired surrogate at index " + i2);
              }
              i2++;
            }
          }
        }
        j += i1;
      }
    }
    if (j < i) {
      throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (4294967296L + j));
    }
    return j;
  }
  
  private void writeRawByte(int paramInt)
    throws IOException
  {
    byte b = (byte)paramInt;
    if (!this.buffer.hasRemaining()) {
      throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }
    this.buffer.put(b);
  }
  
  private void writeRawLittleEndian64(long paramLong)
    throws IOException
  {
    if (this.buffer.remaining() < 8) {
      throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }
    this.buffer.putLong(paramLong);
  }
  
  public final void writeBool(int paramInt, boolean paramBoolean)
    throws IOException
  {
    writeTag(paramInt, 0);
    int i = 0;
    if (paramBoolean) {
      i = 1;
    }
    writeRawByte(i);
  }
  
  public final void writeBytes(int paramInt, byte[] paramArrayOfByte)
    throws IOException
  {
    writeTag(paramInt, 2);
    writeRawVarint32(paramArrayOfByte.length);
    int i = paramArrayOfByte.length;
    if (this.buffer.remaining() >= i)
    {
      this.buffer.put(paramArrayOfByte, 0, i);
      return;
    }
    throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
  }
  
  public final void writeDouble(int paramInt, double paramDouble)
    throws IOException
  {
    writeTag(paramInt, 1);
    writeRawLittleEndian64(Double.doubleToLongBits(paramDouble));
  }
  
  public final void writeFixed64(int paramInt, long paramLong)
    throws IOException
  {
    writeTag(paramInt, 1);
    writeRawLittleEndian64(paramLong);
  }
  
  public final void writeFloat(int paramInt, float paramFloat)
    throws IOException
  {
    writeTag(paramInt, 5);
    int i = Float.floatToIntBits(paramFloat);
    if (this.buffer.remaining() < 4) {
      throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }
    this.buffer.putInt(i);
  }
  
  public final void writeGroup(int paramInt, MessageNano paramMessageNano)
    throws IOException
  {
    writeTag(paramInt, 3);
    paramMessageNano.writeTo(this);
    writeTag(paramInt, 4);
  }
  
  public final void writeInt32(int paramInt1, int paramInt2)
    throws IOException
  {
    writeTag(paramInt1, 0);
    writeInt32NoTag(paramInt2);
  }
  
  public final void writeInt32NoTag(int paramInt)
    throws IOException
  {
    if (paramInt >= 0)
    {
      writeRawVarint32(paramInt);
      return;
    }
    writeRawVarint64(paramInt);
  }
  
  public final void writeInt64(int paramInt, long paramLong)
    throws IOException
  {
    writeTag(paramInt, 0);
    writeRawVarint64(paramLong);
  }
  
  public final void writeMessage(int paramInt, MessageNano paramMessageNano)
    throws IOException
  {
    writeTag(paramInt, 2);
    writeRawVarint32(paramMessageNano.getCachedSize());
    paramMessageNano.writeTo(this);
  }
  
  public final void writeRawVarint32(int paramInt)
    throws IOException
  {
    for (;;)
    {
      if ((paramInt & 0xFFFFFF80) == 0)
      {
        writeRawByte(paramInt);
        return;
      }
      writeRawByte(0x80 | paramInt & 0x7F);
      paramInt >>>= 7;
    }
  }
  
  public final void writeRawVarint64(long paramLong)
    throws IOException
  {
    for (;;)
    {
      if ((0xFFFFFF80 & paramLong) == 0L)
      {
        writeRawByte((int)paramLong);
        return;
      }
      writeRawByte(0x80 | 0x7F & (int)paramLong);
      paramLong >>>= 7;
    }
  }
  
  public final void writeString(int paramInt, String paramString)
    throws IOException
  {
    writeTag(paramInt, 2);
    int i;
    int j;
    try
    {
      i = computeRawVarint32Size(paramString.length());
      if (i != computeRawVarint32Size(3 * paramString.length())) {
        break label167;
      }
      j = this.buffer.position();
      if (this.buffer.remaining() < i) {
        throw new OutOfSpaceException(i + j, this.buffer.limit());
      }
    }
    catch (BufferOverflowException localBufferOverflowException)
    {
      OutOfSpaceException localOutOfSpaceException = new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
      localOutOfSpaceException.initCause(localBufferOverflowException);
      throw localOutOfSpaceException;
    }
    this.buffer.position(j + i);
    encode(paramString, this.buffer);
    int k = this.buffer.position();
    this.buffer.position(j);
    writeRawVarint32(k - j - i);
    this.buffer.position(k);
    return;
    label167:
    writeRawVarint32(encodedLength(paramString));
    encode(paramString, this.buffer);
  }
  
  public final void writeTag(int paramInt1, int paramInt2)
    throws IOException
  {
    writeRawVarint32(WireFormatNano.makeTag(paramInt1, paramInt2));
  }
  
  public final void writeUInt32(int paramInt1, int paramInt2)
    throws IOException
  {
    writeTag(paramInt1, 0);
    writeRawVarint32(paramInt2);
  }
  
  public final void writeUInt64(int paramInt, long paramLong)
    throws IOException
  {
    writeTag(paramInt, 0);
    writeRawVarint64(paramLong);
  }
  
  public static final class OutOfSpaceException
    extends IOException
  {
    OutOfSpaceException(int paramInt1, int paramInt2)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.protobuf.nano.CodedOutputByteBufferNano
 * JD-Core Version:    0.7.0.1
 */