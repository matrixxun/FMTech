package com.google.android.wallet.common.api;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.google.android.gsf.GservicesValue;
import com.google.android.volley.GoogleHttpClientStack;
import com.google.android.wallet.config.G;
import com.google.android.wallet.config.G.images;
import java.io.File;
import java.util.Locale;

public final class WalletRequestQueue
{
  public static final String USER_AGENT_VALUE;
  private static RequestQueue sImageInstance;
  private static RequestQueue sInstance;
  
  static
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Build.DEVICE;
    arrayOfObject[1] = Build.ID;
    USER_AGENT_VALUE = String.format(localLocale, "unused/0 (%s %s); gzip", arrayOfObject);
  }
  
  private static Network createNetwork(Context paramContext)
  {
    return new BasicNetwork(new GoogleHttpClientStack(paramContext, ((Boolean)G.allowPiiLogging.get()).booleanValue()));
  }
  
  public static RequestQueue getApiRequestQueue(Context paramContext)
  {
    try
    {
      if (sInstance == null)
      {
        RequestQueue localRequestQueue1 = new RequestQueue(new DiskBasedCache(new File(paramContext.getCacheDir(), "wallet_im_volley_api_cache"), 1048576), createNetwork(paramContext), 2);
        sInstance = localRequestQueue1;
        localRequestQueue1.start();
      }
      RequestQueue localRequestQueue2 = sInstance;
      return localRequestQueue2;
    }
    finally {}
  }
  
  public static RequestQueue getImageRequestQueue(Context paramContext)
  {
    try
    {
      if (sImageInstance == null)
      {
        RequestQueue localRequestQueue1 = new RequestQueue(new DiskBasedCache(new File(paramContext.getCacheDir(), "wallet_im_volley_image_cache"), ((Integer)G.images.diskCacheSizeBytes.get()).intValue()), createNetwork(paramContext), 6);
        sImageInstance = localRequestQueue1;
        localRequestQueue1.start();
      }
      RequestQueue localRequestQueue2 = sImageInstance;
      return localRequestQueue2;
    }
    finally {}
  }
  
  public static void setApiRequestQueue(RequestQueue paramRequestQueue)
  {
    for (;;)
    {
      try
      {
        RequestQueue localRequestQueue = sInstance;
        if (paramRequestQueue == localRequestQueue) {
          return;
        }
        if ((sInstance == null) || (paramRequestQueue == null)) {
          sInstance = paramRequestQueue;
        } else {
          Log.e("WalletRequestQueue", "Trying to set request queue when one already exists");
        }
      }
      finally {}
    }
  }
  
  public static void setImageRequestQueue(RequestQueue paramRequestQueue)
  {
    for (;;)
    {
      try
      {
        RequestQueue localRequestQueue = sImageInstance;
        if (paramRequestQueue == localRequestQueue) {
          return;
        }
        if ((sImageInstance == null) || (paramRequestQueue == null)) {
          sImageInstance = paramRequestQueue;
        } else {
          Log.e("WalletRequestQueue", "Trying to set image request queue when one already exists");
        }
      }
      finally {}
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.api.WalletRequestQueue
 * JD-Core Version:    0.7.0.1
 */