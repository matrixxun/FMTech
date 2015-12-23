package okio;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public final class ByteString
  implements Serializable
{
  public static final ByteString EMPTY = of(new byte[0]);
  private static final char[] HEX_DIGITS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  public final byte[] data;
  private transient int hashCode;
  private transient String utf8;
  
  ByteString(byte[] paramArrayOfByte)
  {
    this.data = paramArrayOfByte;
  }
  
  public static ByteString encodeUtf8(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("s == null");
    }
    ByteString localByteString = new ByteString(paramString.getBytes(Util.UTF_8));
    localByteString.utf8 = paramString;
    return localByteString;
  }
  
  public static ByteString of(byte... paramVarArgs)
  {
    if (paramVarArgs == null) {
      throw new IllegalArgumentException("data == null");
    }
    return new ByteString((byte[])paramVarArgs.clone());
  }
  
  public final boolean equals(Object paramObject)
  {
    return (paramObject == this) || (((paramObject instanceof ByteString)) && (Arrays.equals(((ByteString)paramObject).data, this.data)));
  }
  
  public final int hashCode()
  {
    int i = this.hashCode;
    if (i != 0) {
      return i;
    }
    int j = Arrays.hashCode(this.data);
    this.hashCode = j;
    return j;
  }
  
  public final String hex()
  {
    char[] arrayOfChar = new char[2 * this.data.length];
    byte[] arrayOfByte = this.data;
    int i = arrayOfByte.length;
    int j = 0;
    int k = 0;
    while (j < i)
    {
      int m = arrayOfByte[j];
      int n = k + 1;
      arrayOfChar[k] = HEX_DIGITS[(0xF & m >> 4)];
      k = n + 1;
      arrayOfChar[n] = HEX_DIGITS[(m & 0xF)];
      j++;
    }
    return new String(arrayOfChar);
  }
  
  public final ByteString toAsciiLowercase()
  {
    for (int i = 0;; i++) {
      if (i < this.data.length)
      {
        int j = this.data[i];
        if ((j >= 65) && (j <= 90))
        {
          byte[] arrayOfByte = (byte[])this.data.clone();
          int k = i + 1;
          arrayOfByte[i] = ((byte)(j + 32));
          for (int m = k; m < arrayOfByte.length; m++)
          {
            int n = arrayOfByte[m];
            if ((n >= 65) && (n <= 90)) {
              arrayOfByte[m] = ((byte)(n + 32));
            }
          }
          this = new ByteString(arrayOfByte);
        }
      }
      else
      {
        return this;
      }
    }
  }
  
  public final byte[] toByteArray()
  {
    return (byte[])this.data.clone();
  }
  
  public final String toString()
  {
    if (this.data.length == 0) {
      return "ByteString[size=0]";
    }
    if (this.data.length <= 16)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(this.data.length);
      arrayOfObject2[1] = hex();
      return String.format("ByteString[size=%s data=%s]", arrayOfObject2);
    }
    try
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = Integer.valueOf(this.data.length);
      arrayOfObject1[1] = of(MessageDigest.getInstance("MD5").digest(this.data)).hex();
      String str = String.format("ByteString[size=%s md5=%s]", arrayOfObject1);
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new AssertionError();
    }
  }
  
  public final String utf8()
  {
    String str1 = this.utf8;
    if (str1 != null) {
      return str1;
    }
    String str2 = new String(this.data, Util.UTF_8);
    this.utf8 = str2;
    return str2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.ByteString
 * JD-Core Version:    0.7.0.1
 */