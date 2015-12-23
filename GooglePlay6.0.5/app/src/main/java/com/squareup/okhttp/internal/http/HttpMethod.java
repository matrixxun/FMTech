package com.squareup.okhttp.internal.http;

public final class HttpMethod
{
  public static boolean permitsRequestBody(String paramString)
  {
    if ((paramString.equals("POST")) || (paramString.equals("PUT")) || (paramString.equals("PATCH"))) {}
    for (int i = 1;; i = 0)
    {
      boolean bool1;
      if (i == 0)
      {
        boolean bool2 = paramString.equals("DELETE");
        bool1 = false;
        if (!bool2) {}
      }
      else
      {
        bool1 = true;
      }
      return bool1;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.HttpMethod
 * JD-Core Version:    0.7.0.1
 */