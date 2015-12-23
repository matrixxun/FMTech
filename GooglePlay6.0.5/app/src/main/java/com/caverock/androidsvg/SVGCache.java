package com.caverock.androidsvg;

import android.support.v4.util.LruCache;
import java.util.ArrayList;

public final class SVGCache
{
  private final String RES_ID_PREFIX = "res";
  private final int SIZE = 10;
  SVGListLruCache mLruCache = new SVGListLruCache();
  
  static String getCacheKey(String paramString, float paramFloat)
  {
    StringBuilder localStringBuilder = new StringBuilder().append(paramString);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Float.valueOf(paramFloat);
    return String.format("%.4f", arrayOfObject);
  }
  
  private final class SVGListLruCache
    extends LruCache<String, ArrayList<SVG>>
  {
    public SVGListLruCache()
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.SVGCache
 * JD-Core Version:    0.7.0.1
 */