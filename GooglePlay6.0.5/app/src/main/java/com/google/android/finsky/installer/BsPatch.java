package com.google.android.finsky.installer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public final class BsPatch
{
  public static void applyPatchInternal(RandomAccessFile paramRandomAccessFile, OutputStream paramOutputStream, InputStream paramInputStream, long paramLong)
    throws PatchFormatException, IOException
  {
    byte[] arrayOfByte1 = new byte[16];
    try
    {
      readFully$5c29caa5(paramInputStream, arrayOfByte1, 16);
      String str = new String(arrayOfByte1, 0, 16, "US-ASCII");
      if (!"ENDSLEY/BSDIFF43".equals(str)) {
        throw new PatchFormatException("bad signature");
      }
    }
    catch (IOException localIOException)
    {
      throw new PatchFormatException("truncated signature");
    }
    long l1 = paramRandomAccessFile.length();
    if (l1 > 2147483647L) {
      throw new PatchFormatException("bad oldSize");
    }
    long l2 = readBsdiffLong(paramInputStream);
    if ((l2 < 0L) || (l2 > 2147483647L)) {
      throw new PatchFormatException("bad newSize");
    }
    if (paramLong != l2) {
      throw new PatchFormatException("expectedNewSize != newSize");
    }
    byte[] arrayOfByte2 = new byte[51200];
    byte[] arrayOfByte3 = new byte[51200];
    long l3 = 0L;
    long l4 = 0L;
    while (l4 < l2)
    {
      long l5 = readBsdiffLong(paramInputStream);
      long l6 = readBsdiffLong(paramInputStream);
      long l7 = readBsdiffLong(paramInputStream);
      if ((l5 < 0L) || (l5 > 2147483647L)) {
        throw new PatchFormatException("bad diffSegmentLength");
      }
      if ((l6 < 0L) || (l6 > 2147483647L)) {
        throw new PatchFormatException("bad copySegmentLength");
      }
      if ((l7 < -2147483648L) || (l7 > 2147483647L)) {
        throw new PatchFormatException("bad offsetToNextInput");
      }
      long l8 = l6 + (l4 + l5);
      if (l8 > l2) {
        throw new PatchFormatException("expectedFinalNewDataBytesWritten too large");
      }
      long l9 = l7 + (l3 + l5);
      if (l9 > l1) {
        throw new PatchFormatException("expectedFinalOldDataOffset too large");
      }
      if (l9 < 0L) {
        throw new PatchFormatException("expectedFinalOldDataOffset is negative");
      }
      paramRandomAccessFile.seek(l3);
      if (l5 > 0L) {
        transformBytes((int)l5, paramInputStream, paramRandomAccessFile, paramOutputStream, arrayOfByte2, arrayOfByte3);
      }
      if (l6 > 0L) {
        pipe(paramInputStream, paramOutputStream, arrayOfByte2, (int)l6);
      }
      l4 = l8;
      l3 = l9;
    }
  }
  
  private static void pipe(InputStream paramInputStream, OutputStream paramOutputStream, byte[] paramArrayOfByte, int paramInt)
    throws IOException
  {
    while (paramInt > 0)
    {
      int i = Math.min(51200, paramInt);
      readFully$5c29caa5(paramInputStream, paramArrayOfByte, i);
      paramOutputStream.write(paramArrayOfByte, 0, i);
      paramInt -= i;
    }
  }
  
  private static long readBsdiffLong(InputStream paramInputStream)
    throws PatchFormatException, IOException
  {
    long l = 0L;
    for (int i = 0; i < 64; i += 8) {
      l |= paramInputStream.read() << i;
    }
    if (l == -9223372036854775808L) {
      throw new PatchFormatException("read negative zero");
    }
    if ((l & 0x0) != 0L) {
      l = -(0xFFFFFFFF & l);
    }
    return l;
  }
  
  private static void readFully$5c29caa5(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt)
    throws IOException
  {
    int i = 0;
    while (i < paramInt)
    {
      int j = paramInputStream.read(paramArrayOfByte, i + 0, paramInt - i);
      if (j == -1) {
        throw new IOException("truncated input stream");
      }
      i += j;
    }
  }
  
  private static void transformBytes(int paramInt, InputStream paramInputStream, RandomAccessFile paramRandomAccessFile, OutputStream paramOutputStream, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws IOException
  {
    int i = paramInt;
    while (i > 0)
    {
      int j = Math.min(i, 51200);
      paramRandomAccessFile.readFully(paramArrayOfByte1, 0, j);
      readFully$5c29caa5(paramInputStream, paramArrayOfByte2, j);
      for (int k = 0; k < j; k++) {
        paramArrayOfByte1[k] = ((byte)(paramArrayOfByte1[k] + paramArrayOfByte2[k]));
      }
      paramOutputStream.write(paramArrayOfByte1, 0, j);
      i -= j;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.installer.BsPatch
 * JD-Core Version:    0.7.0.1
 */