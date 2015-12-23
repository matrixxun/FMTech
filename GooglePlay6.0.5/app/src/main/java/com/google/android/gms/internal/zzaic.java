package com.google.android.gms.internal;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

public final class zzaic
{
  final ByteBuffer zzciZ;
  
  private zzaic(ByteBuffer paramByteBuffer)
  {
    this.zzciZ = paramByteBuffer;
    this.zzciZ.order(ByteOrder.LITTLE_ENDIAN);
  }
  
  private zzaic(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this(ByteBuffer.wrap(paramArrayOfByte, paramInt1, paramInt2));
  }
  
  public static int zzW(byte[] paramArrayOfByte)
  {
    return zzsx(paramArrayOfByte.length) + paramArrayOfByte.length;
  }
  
  public static int zzY(int paramInt1, int paramInt2)
  {
    return zzsv(paramInt1) + zzss(paramInt2);
  }
  
  private static int zza(CharSequence paramCharSequence, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramCharSequence.length();
    int j = 0;
    int k = paramInt1 + paramInt2;
    while ((j < i) && (j + paramInt1 < k))
    {
      int i9 = paramCharSequence.charAt(j);
      if (i9 >= 128) {
        break;
      }
      paramArrayOfByte[(paramInt1 + j)] = ((byte)i9);
      j++;
    }
    if (j == i) {
      return paramInt1 + i;
    }
    int m = paramInt1 + j;
    if (j < i)
    {
      int n = paramCharSequence.charAt(j);
      int i5;
      if ((n < 128) && (m < k))
      {
        i5 = m + 1;
        paramArrayOfByte[m] = ((byte)n);
      }
      for (;;)
      {
        j++;
        m = i5;
        break;
        if ((n < 2048) && (m <= k - 2))
        {
          int i8 = m + 1;
          paramArrayOfByte[m] = ((byte)(0x3C0 | n >>> 6));
          i5 = i8 + 1;
          paramArrayOfByte[i8] = ((byte)(0x80 | n & 0x3F));
        }
        else if (((n < 55296) || (57343 < n)) && (m <= k - 3))
        {
          int i6 = m + 1;
          paramArrayOfByte[m] = ((byte)(0x1E0 | n >>> 12));
          int i7 = i6 + 1;
          paramArrayOfByte[i6] = ((byte)(0x80 | 0x3F & n >>> 6));
          i5 = i7 + 1;
          paramArrayOfByte[i7] = ((byte)(0x80 | n & 0x3F));
        }
        else
        {
          if (m > k - 4) {
            break label457;
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
          i5 = i4 + 1;
          paramArrayOfByte[i4] = ((byte)(0x80 | i1 & 0x3F));
        }
      }
      label457:
      if ((55296 <= n) && (n <= 57343) && ((j + 1 == paramCharSequence.length()) || (!Character.isSurrogatePair(n, paramCharSequence.charAt(j + 1))))) {
        throw new IllegalArgumentException("Unpaired surrogate at index " + j);
      }
      throw new ArrayIndexOutOfBoundsException("Failed writing " + n + " at index " + m);
    }
    return m;
  }
  
  private static void zza(CharSequence paramCharSequence, ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer.isReadOnly()) {
      throw new ReadOnlyBufferException();
    }
    if (paramByteBuffer.hasArray()) {
      try
      {
        paramByteBuffer.position(zza(paramCharSequence, paramByteBuffer.array(), paramByteBuffer.arrayOffset() + paramByteBuffer.position(), paramByteBuffer.remaining()) - paramByteBuffer.arrayOffset());
        return;
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
      {
        BufferOverflowException localBufferOverflowException = new BufferOverflowException();
        localBufferOverflowException.initCause(localArrayIndexOutOfBoundsException);
        throw localBufferOverflowException;
      }
    }
    zzb(paramCharSequence, paramByteBuffer);
  }
  
  public static int zzaI(long paramLong)
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
  
  public static long zzaK(long paramLong)
  {
    return paramLong << 1 ^ paramLong >> 63;
  }
  
  public static int zzb(int paramInt, byte[] paramArrayOfByte)
  {
    return zzsv(paramInt) + zzW(paramArrayOfByte);
  }
  
  public static zzaic zzb(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return new zzaic(paramArrayOfByte, 0, paramInt2);
  }
  
  private static void zzb(CharSequence paramCharSequence, ByteBuffer paramByteBuffer)
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
  
  public static int zzc(int paramInt, zzaik paramzzaik)
  {
    int i = zzsv(paramInt);
    int j = paramzzaik.getSerializedSize();
    return i + (j + zzsx(j));
  }
  
  private static int zzc(CharSequence paramCharSequence)
  {
    int i = 0;
    int j = paramCharSequence.length();
    for (int k = 0; (k < j) && (paramCharSequence.charAt(k) < ''); k++) {}
    for (;;)
    {
      if (k < j)
      {
        int i1 = paramCharSequence.charAt(k);
        if (i1 < 2048)
        {
          m += (127 - i1 >>> 31);
          k++;
          continue;
        }
        int i2 = paramCharSequence.length();
        if (k < i2)
        {
          int i3 = paramCharSequence.charAt(k);
          if (i3 < 2048) {
            i += (127 - i3 >>> 31);
          }
          for (;;)
          {
            k++;
            break;
            i += 2;
            if ((55296 <= i3) && (i3 <= 57343))
            {
              if (Character.codePointAt(paramCharSequence, k) < 65536) {
                throw new IllegalArgumentException("Unpaired surrogate at index " + k);
              }
              k++;
            }
          }
        }
      }
      for (int n = m + i;; n = m)
      {
        if (n < j) {
          throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (4294967296L + n));
        }
        return n;
      }
      int m = j;
    }
  }
  
  public static int zzi(int paramInt, long paramLong)
  {
    return zzsv(paramInt) + zzaI(paramLong);
  }
  
  public static int zzjw(String paramString)
  {
    int i = zzc(paramString);
    return i + zzsx(i);
  }
  
  public static int zzq(int paramInt, String paramString)
  {
    return zzsv(paramInt) + zzjw(paramString);
  }
  
  public static int zzss(int paramInt)
  {
    if (paramInt >= 0) {
      return zzsx(paramInt);
    }
    return 10;
  }
  
  private void zzsu(int paramInt)
    throws IOException
  {
    byte b = (byte)paramInt;
    if (!this.zzciZ.hasRemaining()) {
      throw new zza(this.zzciZ.position(), this.zzciZ.limit());
    }
    this.zzciZ.put(b);
  }
  
  public static int zzsv(int paramInt)
  {
    return zzsx(zzain.zzab(paramInt, 0));
  }
  
  public static int zzsx(int paramInt)
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
  
  public static int zzsz(int paramInt)
  {
    return paramInt << 1 ^ paramInt >> 31;
  }
  
  public final void zzOW()
  {
    if (this.zzciZ.remaining() != 0) {
      throw new IllegalStateException("Did not write as much data as expected.");
    }
  }
  
  public final void zzW(int paramInt1, int paramInt2)
    throws IOException
  {
    zzaa(paramInt1, 0);
    if (paramInt2 >= 0)
    {
      zzsw(paramInt2);
      return;
    }
    zzaH(paramInt2);
  }
  
  public final void zzX(byte[] paramArrayOfByte)
    throws IOException
  {
    int i = paramArrayOfByte.length;
    if (this.zzciZ.remaining() >= i)
    {
      this.zzciZ.put(paramArrayOfByte, 0, i);
      return;
    }
    throw new zza(this.zzciZ.position(), this.zzciZ.limit());
  }
  
  public final void zza(int paramInt, zzaik paramzzaik)
    throws IOException
  {
    zzaa(paramInt, 2);
    zzc(paramzzaik);
  }
  
  public final void zza(int paramInt, byte[] paramArrayOfByte)
    throws IOException
  {
    zzaa(paramInt, 2);
    zzsw(paramArrayOfByte.length);
    zzX(paramArrayOfByte);
  }
  
  public final void zzaH(long paramLong)
    throws IOException
  {
    for (;;)
    {
      if ((0xFFFFFF80 & paramLong) == 0L)
      {
        zzsu((int)paramLong);
        return;
      }
      zzsu(0x80 | 0x7F & (int)paramLong);
      paramLong >>>= 7;
    }
  }
  
  public final void zzaa(int paramInt1, int paramInt2)
    throws IOException
  {
    zzsw(zzain.zzab(paramInt1, paramInt2));
  }
  
  public final void zzb(int paramInt, float paramFloat)
    throws IOException
  {
    zzaa(paramInt, 5);
    int i = Float.floatToIntBits(paramFloat);
    if (this.zzciZ.remaining() < 4) {
      throw new zza(this.zzciZ.position(), this.zzciZ.limit());
    }
    this.zzciZ.putInt(i);
  }
  
  public final void zzb(int paramInt, long paramLong)
    throws IOException
  {
    zzaa(paramInt, 0);
    zzaH(paramLong);
  }
  
  public final void zzb(int paramInt, String paramString)
    throws IOException
  {
    zzaa(paramInt, 2);
    int i;
    int j;
    try
    {
      i = zzsx(paramString.length());
      if (i != zzsx(3 * paramString.length())) {
        break label167;
      }
      j = this.zzciZ.position();
      if (this.zzciZ.remaining() < i) {
        throw new zza(i + j, this.zzciZ.limit());
      }
    }
    catch (BufferOverflowException localBufferOverflowException)
    {
      zza localzza = new zza(this.zzciZ.position(), this.zzciZ.limit());
      localzza.initCause(localBufferOverflowException);
      throw localzza;
    }
    this.zzciZ.position(j + i);
    zza(paramString, this.zzciZ);
    int k = this.zzciZ.position();
    this.zzciZ.position(j);
    zzsw(k - j - i);
    this.zzciZ.position(k);
    return;
    label167:
    zzsw(zzc(paramString));
    zza(paramString, this.zzciZ);
  }
  
  public final void zzc(zzaik paramzzaik)
    throws IOException
  {
    if (paramzzaik.zzcjk < 0) {
      paramzzaik.getSerializedSize();
    }
    zzsw(paramzzaik.zzcjk);
    paramzzaik.writeTo(this);
  }
  
  public final void zze(int paramInt, boolean paramBoolean)
    throws IOException
  {
    zzaa(paramInt, 0);
    int i = 0;
    if (paramBoolean) {
      i = 1;
    }
    zzsu(i);
  }
  
  public final void zzsw(int paramInt)
    throws IOException
  {
    for (;;)
    {
      if ((paramInt & 0xFFFFFF80) == 0)
      {
        zzsu(paramInt);
        return;
      }
      zzsu(0x80 | paramInt & 0x7F);
      paramInt >>>= 7;
    }
  }
  
  public static final class zza
    extends IOException
  {
    zza(int paramInt1, int paramInt2)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaic
 * JD-Core Version:    0.7.0.1
 */