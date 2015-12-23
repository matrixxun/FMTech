package com.google.android.finsky.auth;

import java.util.Map;

public final class AuthTokenUtils
{
  public static void putGoogleLoginAuthTokenInHeader(Map<String, String> paramMap, String paramString)
  {
    paramMap.put("Authorization", "GoogleLogin auth=" + paramString);
  }
  
  public static void putOauth2AuthTokenInHeader(Map<String, String> paramMap, String paramString)
  {
    paramMap.put("Authorization", "Bearer " + paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.auth.AuthTokenUtils
 * JD-Core Version:    0.7.0.1
 */