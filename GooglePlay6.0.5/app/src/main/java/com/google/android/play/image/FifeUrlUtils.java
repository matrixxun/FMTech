package com.google.android.play.image;

import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.play.utils.NetworkInfoUtil;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.play.utils.config.PlayG;

public final class FifeUrlUtils
{
  private static boolean WEBP_ENABLED;
  private static float sDpiScaleFactor;
  
  static
  {
    if ((((Boolean)PlayG.webpFifeImagesEnabled.get()).booleanValue()) && (Build.VERSION.SDK_INT >= 17)) {}
    for (boolean bool = true;; bool = false)
    {
      WEBP_ENABLED = bool;
      sDpiScaleFactor = 1.0F;
      return;
    }
  }
  
  public static String buildFifeUrl(String paramString, int paramInt1, int paramInt2)
  {
    if (TextUtils.isEmpty(paramString)) {}
    StringBuilder localStringBuilder;
    do
    {
      return paramString;
      localStringBuilder = new StringBuilder();
      if (WEBP_ENABLED) {
        localStringBuilder.append("rw");
      }
      if (paramInt1 > 0)
      {
        if (localStringBuilder.length() != 0) {
          localStringBuilder.append('-');
        }
        localStringBuilder.append('w').append(paramInt1);
      }
      if (paramInt2 > 0)
      {
        if (localStringBuilder.length() != 0) {
          localStringBuilder.append('-');
        }
        localStringBuilder.append('h').append(paramInt2);
      }
    } while (localStringBuilder.length() == 0);
    String str = Uri.parse(paramString).getEncodedPath();
    if ((str.length() > 1) && (str.indexOf('/', 1) > 0) && (!paramString.endsWith("?fife")))
    {
      localStringBuilder.insert(0, "/");
      return new StringBuilder(paramString).insert(paramString.lastIndexOf("/"), localStringBuilder).toString();
    }
    return paramString + "=" + localStringBuilder;
  }
  
  public static float getDpiScaleFactor()
  {
    return sDpiScaleFactor;
  }
  
  public static float getNetworkScaleFactor(Context paramContext)
  {
    switch (NetworkInfoUtil.getNetworkType(paramContext))
    {
    case 5: 
    default: 
      return ((Float)PlayG.percentOfImageSize3G.get()).floatValue();
    case 1: 
      return ((Float)PlayG.percentOfImageSize2G.get()).floatValue();
    case 2: 
      return ((Float)PlayG.percentOfImageSize3G.get()).floatValue();
    case 3: 
      return ((Float)PlayG.percentOfImageSize4G.get()).floatValue();
    }
    return ((Float)PlayG.percentOfImageSizeWifi.get()).floatValue();
  }
  
  public static void setDpiScaleFactor(float paramFloat)
  {
    if (paramFloat > 0.0F) {
      sDpiScaleFactor = paramFloat;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.image.FifeUrlUtils
 * JD-Core Version:    0.7.0.1
 */