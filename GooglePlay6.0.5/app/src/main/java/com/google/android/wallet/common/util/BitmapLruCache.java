package com.google.android.wallet.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.android.volley.toolbox.ImageLoader.ImageCache;

public final class BitmapLruCache
  extends LruCache<String, Bitmap>
  implements ImageLoader.ImageCache
{
  public BitmapLruCache(Context paramContext, int paramInt)
  {
    super(Math.min(i / 8, j * 4 / 1024));
  }
  
  public final Bitmap getBitmap(String paramString)
  {
    return (Bitmap)get(paramString);
  }
  
  public final void putBitmap(String paramString, Bitmap paramBitmap)
  {
    put(paramString, paramBitmap);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.BitmapLruCache
 * JD-Core Version:    0.7.0.1
 */