package com.google.android.instrumentedzip;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public final class Utils
{
  public static void readFully(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramInt2 == 0) {
      return;
    }
    if (paramInputStream == null) {
      throw new NullPointerException("in == null");
    }
    if (paramArrayOfByte == null) {
      throw new NullPointerException("dst == null");
    }
    int i = paramArrayOfByte.length;
    if (((paramInt2 | 0x0) < 0) || (i < 0) || (i + 0 < paramInt2)) {
      throw new ArrayIndexOutOfBoundsException();
    }
    int j;
    do
    {
      paramInt1 += j;
      paramInt2 -= j;
      if (paramInt2 <= 0) {
        break;
      }
      j = paramInputStream.read(paramArrayOfByte, paramInt1, paramInt2);
    } while (j >= 0);
    throw new EOFException();
  }
  
  public static void skipFully(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    if (paramInt == 0) {
      return;
    }
    long l;
    do
    {
      Object localObject;
      paramInt = (int)(paramInt - localObject);
      if (paramInt <= 0) {
        break;
      }
      l = paramInputStream.skip(paramInt);
    } while (l >= 0L);
    throw new EOFException();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.instrumentedzip.Utils
 * JD-Core Version:    0.7.0.1
 */