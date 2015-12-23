package com.google.android.finsky.utils;

import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Md5Util
{
  public static String secureHash(byte[] paramArrayOfByte)
  {
    return Base64.encodeToString(secureHashBytes(paramArrayOfByte), 11);
  }
  
  public static byte[] secureHashBytes(byte[] paramArrayOfByte)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      return localMessageDigest.digest();
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.Md5Util
 * JD-Core Version:    0.7.0.1
 */