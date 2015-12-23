package com.google.android.gms.common.api;

public final class CommonStatusCodes
{
  public static String getStatusCodeString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "unknown status code: " + paramInt;
    case -1: 
      return "SUCCESS_CACHE";
    case 0: 
      return "SUCCESS";
    case 1: 
      return "SERVICE_MISSING";
    case 2: 
      return "SERVICE_VERSION_UPDATE_REQUIRED";
    case 3: 
      return "SERVICE_DISABLED";
    case 4: 
      return "SIGN_IN_REQUIRED";
    case 5: 
      return "INVALID_ACCOUNT";
    case 6: 
      return "RESOLUTION_REQUIRED";
    case 7: 
      return "NETWORK_ERROR";
    case 8: 
      return "INTERNAL_ERROR";
    case 9: 
      return "SERVICE_INVALID";
    case 10: 
      return "DEVELOPER_ERROR";
    case 11: 
      return "LICENSE_CHECK_FAILED";
    case 13: 
      return "ERROR";
    case 14: 
      return "INTERRUPTED";
    case 15: 
      return "TIMEOUT";
    case 16: 
      return "CANCELED";
    case 17: 
      return "API_NOT_CONNECTED";
    case 3000: 
      return "AUTH_API_INVALID_CREDENTIALS";
    case 3001: 
      return "AUTH_API_ACCESS_FORBIDDEN";
    case 3002: 
      return "AUTH_API_CLIENT_ERROR";
    case 3003: 
      return "AUTH_API_SERVER_ERROR";
    case 3004: 
      return "AUTH_TOKEN_ERROR";
    }
    return "AUTH_URL_RESOLUTION";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.CommonStatusCodes
 * JD-Core Version:    0.7.0.1
 */