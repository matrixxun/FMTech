package com.squareup.okhttp;

import java.io.UnsupportedEncodingException;
import okio.Base64;
import okio.ByteString;

public final class Credentials
{
  public static String basic(String paramString1, String paramString2)
  {
    try
    {
      String str1 = Base64.encode(ByteString.of((paramString1 + ":" + paramString2).getBytes("ISO-8859-1")).data);
      String str2 = "Basic " + str1;
      return str2;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new AssertionError();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Credentials
 * JD-Core Version:    0.7.0.1
 */