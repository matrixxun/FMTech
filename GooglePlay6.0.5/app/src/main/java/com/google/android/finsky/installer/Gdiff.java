package com.google.android.finsky.installer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public final class Gdiff
{
  private static void copyFromOriginal(byte[] paramArrayOfByte, RandomAccessFile paramRandomAccessFile, OutputStream paramOutputStream, long paramLong, int paramInt)
    throws IOException
  {
    if (paramInt < 0) {
      throw new IOException("copyLength negative");
    }
    if (paramLong < 0L) {
      throw new IOException("inputOffset negative");
    }
    for (;;)
    {
      try
      {
        paramRandomAccessFile.seek(paramLong);
      }
      catch (EOFException localEOFException)
      {
        throw new IOException("patch underrun");
      }
      paramRandomAccessFile.readFully(paramArrayOfByte, 0, i);
      paramOutputStream.write(paramArrayOfByte, 0, i);
      paramInt -= i;
      break label86;
      int i = 16384;
      label86:
      while (paramInt <= 0) {
        return;
      }
      if (paramInt < 16384) {
        i = paramInt;
      }
    }
  }
  
  private static void copyFromPatch(byte[] paramArrayOfByte, DataInputStream paramDataInputStream, OutputStream paramOutputStream, int paramInt)
    throws IOException
  {
    if (paramInt < 0) {
      throw new IOException("copyLength negative");
    }
    if (paramInt > 0)
    {
      if (paramInt < 16384) {}
      for (int i = paramInt;; i = 16384) {
        try
        {
          paramDataInputStream.readFully(paramArrayOfByte, 0, i);
          paramOutputStream.write(paramArrayOfByte, 0, i);
          paramInt -= i;
        }
        catch (EOFException localEOFException)
        {
          throw new IOException("patch underrun");
        }
      }
    }
  }
  
  public static long patch(RandomAccessFile paramRandomAccessFile, InputStream paramInputStream, OutputStream paramOutputStream, long paramLong)
    throws IOException
  {
    byte[] arrayOfByte = new byte[16384];
    long l1 = 0L;
    DataInputStream localDataInputStream = new DataInputStream(new BufferedInputStream(paramInputStream, 4096));
    int i = localDataInputStream.readInt();
    if (i != -771763713)
    {
      StringBuilder localStringBuilder = new StringBuilder("Unexpected magic=");
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i);
      throw new PatchFormatException(String.format("%x", arrayOfObject));
    }
    int j = localDataInputStream.read();
    if (j != 4) {
      throw new PatchFormatException("Unexpected version=" + j);
    }
    for (;;)
    {
      try
      {
        int k = localDataInputStream.read();
        switch (k)
        {
        default: 
          m = k;
          copyFromPatch(arrayOfByte, localDataInputStream, paramOutputStream, m);
          l1 += m;
          if (l1 <= paramLong) {
            continue;
          }
          throw new IOException("Output length overrun");
        }
      }
      finally
      {
        paramOutputStream.flush();
      }
      throw new IOException("Patch file overrun");
      paramOutputStream.flush();
      return l1;
      int m = localDataInputStream.readUnsignedShort();
      copyFromPatch(arrayOfByte, localDataInputStream, paramOutputStream, m);
      continue;
      m = localDataInputStream.readInt();
      copyFromPatch(arrayOfByte, localDataInputStream, paramOutputStream, m);
      continue;
      long l8 = localDataInputStream.readUnsignedShort();
      m = localDataInputStream.read();
      if (m == -1) {
        throw new IOException("Unexpected end of patch");
      }
      copyFromOriginal(arrayOfByte, paramRandomAccessFile, paramOutputStream, l8, m);
      continue;
      long l7 = localDataInputStream.readUnsignedShort();
      m = localDataInputStream.readUnsignedShort();
      copyFromOriginal(arrayOfByte, paramRandomAccessFile, paramOutputStream, l7, m);
      continue;
      long l6 = localDataInputStream.readUnsignedShort();
      m = localDataInputStream.readInt();
      copyFromOriginal(arrayOfByte, paramRandomAccessFile, paramOutputStream, l6, m);
      continue;
      long l5 = localDataInputStream.readInt();
      m = localDataInputStream.read();
      if (m == -1) {
        throw new IOException("Unexpected end of patch");
      }
      copyFromOriginal(arrayOfByte, paramRandomAccessFile, paramOutputStream, l5, m);
      continue;
      long l4 = localDataInputStream.readInt();
      m = localDataInputStream.readUnsignedShort();
      copyFromOriginal(arrayOfByte, paramRandomAccessFile, paramOutputStream, l4, m);
      continue;
      long l3 = localDataInputStream.readInt();
      m = localDataInputStream.readInt();
      copyFromOriginal(arrayOfByte, paramRandomAccessFile, paramOutputStream, l3, m);
      continue;
      long l2 = localDataInputStream.readLong();
      m = localDataInputStream.readInt();
      copyFromOriginal(arrayOfByte, paramRandomAccessFile, paramOutputStream, l2, m);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.installer.Gdiff
 * JD-Core Version:    0.7.0.1
 */