package com.google.android.play.utils;

import android.content.Context;
import android.net.NetworkInfo;

public final class NetworkInfoUtil
{
  public static NetworkInfoCache sNetworkInfoCacheInstance;
  
  public static int getNetworkType(Context paramContext)
  {
    if (sNetworkInfoCacheInstance == null) {
      PlayCommonLog.wtf("NetworkInfoCache instance is not set in NetworkInfoUtil", new Object[0]);
    }
    for (NetworkInfo localNetworkInfo = null;; localNetworkInfo = sNetworkInfoCacheInstance.getNetworkInfo(paramContext)) {
      return getNetworkTypeFromNetworkInfo(localNetworkInfo);
    }
  }
  
  public static int getNetworkTypeFromNetworkInfo(NetworkInfo paramNetworkInfo)
  {
    if (paramNetworkInfo == null) {}
    do
    {
      return 0;
      if (paramNetworkInfo.getType() == 1) {
        return 4;
      }
      if (paramNetworkInfo.getType() == 6) {
        return 3;
      }
      if (paramNetworkInfo.getType() == 0)
      {
        switch (paramNetworkInfo.getSubtype())
        {
        default: 
          return 5;
        case 1: 
        case 2: 
        case 4: 
        case 7: 
        case 11: 
          return 1;
        case 3: 
        case 5: 
        case 6: 
        case 8: 
        case 9: 
        case 10: 
        case 12: 
        case 14: 
        case 15: 
          return 2;
        }
        return 3;
      }
      if (paramNetworkInfo.getType() == 9) {
        return 6;
      }
    } while (paramNetworkInfo.getType() != 7);
    return 7;
  }
  
  public static abstract interface NetworkInfoCache
  {
    public abstract NetworkInfo getNetworkInfo(Context paramContext);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.utils.NetworkInfoUtil
 * JD-Core Version:    0.7.0.1
 */