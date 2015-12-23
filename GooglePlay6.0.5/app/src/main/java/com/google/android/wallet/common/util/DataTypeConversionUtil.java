package com.google.android.wallet.common.util;

public final class DataTypeConversionUtil
{
  private static final char[] hexArray = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
  
  public static String byteArrayToAscii(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    int j = paramArrayOfByte.length;
    while (i < j)
    {
      localStringBuilder.append((char)(0xFF & paramArrayOfByte[i]));
      i++;
    }
    return localStringBuilder.toString();
  }
  
  public static String byteArrayToHexString(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    char[] arrayOfChar = new char[2 * (i + 0)];
    for (int j = 0; j < i + 0; j++)
    {
      int k = 0xFF & paramArrayOfByte[(j + 0)];
      arrayOfChar[(j * 2)] = hexArray[(k >> 4)];
      arrayOfChar[(1 + j * 2)] = hexArray[(k & 0xF)];
    }
    return new String(arrayOfChar);
  }
  
  public static byte[] hexStringToByteArray(String paramString)
  {
    int i = paramString.length();
    if (i % 2 != 0)
    {
      paramString = String.format("0%s", new Object[] { paramString });
      i++;
    }
    byte[] arrayOfByte = new byte[i / 2];
    for (int j = 0; j < i; j += 2) {
      arrayOfByte[(j / 2)] = ((byte)(Character.digit(paramString.charAt(j), 16) << 4 | Character.digit(paramString.charAt(j + 1), 16)));
    }
    return arrayOfByte;
  }
  
  public static int unsignedByteToInt(byte paramByte)
  {
    return paramByte & 0xFF;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.DataTypeConversionUtil
 * JD-Core Version:    0.7.0.1
 */