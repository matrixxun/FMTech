package okio;

import java.io.UnsupportedEncodingException;

public final class Base64
{
  private static final byte[] MAP = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
  
  public static String encode(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = new byte[4 * (2 + paramArrayOfByte.length) / 3];
    int i = paramArrayOfByte.length - paramArrayOfByte.length % 3;
    int j = 0;
    int k = 0;
    while (j < i)
    {
      int i7 = k + 1;
      arrayOfByte[k] = MAP[((0xFF & paramArrayOfByte[j]) >> 2)];
      int i8 = i7 + 1;
      arrayOfByte[i7] = MAP[((0x3 & paramArrayOfByte[j]) << 4 | (0xFF & paramArrayOfByte[(j + 1)]) >> 4)];
      int i9 = i8 + 1;
      arrayOfByte[i8] = MAP[((0xF & paramArrayOfByte[(j + 1)]) << 2 | (0xFF & paramArrayOfByte[(j + 2)]) >> 6)];
      k = i9 + 1;
      arrayOfByte[i9] = MAP[(0x3F & paramArrayOfByte[(j + 2)])];
      j += 3;
    }
    switch (paramArrayOfByte.length % 3)
    {
    }
    for (;;)
    {
      for (int i2 = k;; i2 = i6)
      {
        try
        {
          String str = new String(arrayOfByte, 0, i2, "US-ASCII");
          return str;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          int i3;
          int i4;
          int i5;
          int i6;
          int m;
          int n;
          int i1;
          throw new AssertionError(localUnsupportedEncodingException);
        }
        i3 = k + 1;
        arrayOfByte[k] = MAP[((0xFF & paramArrayOfByte[i]) >> 2)];
        i4 = i3 + 1;
        arrayOfByte[i3] = MAP[((0x3 & paramArrayOfByte[i]) << 4)];
        i5 = i4 + 1;
        arrayOfByte[i4] = 61;
        i6 = i5 + 1;
        arrayOfByte[i5] = 61;
      }
      m = k + 1;
      arrayOfByte[k] = MAP[((0xFF & paramArrayOfByte[i]) >> 2)];
      n = m + 1;
      arrayOfByte[m] = MAP[((0x3 & paramArrayOfByte[i]) << 4 | (0xFF & paramArrayOfByte[(i + 1)]) >> 4)];
      i1 = n + 1;
      arrayOfByte[n] = MAP[((0xF & paramArrayOfByte[(i + 1)]) << 2)];
      k = i1 + 1;
      arrayOfByte[i1] = 61;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.Base64
 * JD-Core Version:    0.7.0.1
 */