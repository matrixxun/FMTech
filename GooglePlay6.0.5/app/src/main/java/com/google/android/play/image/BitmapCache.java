package com.google.android.play.image;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import java.util.ArrayList;

public final class BitmapCache
{
  BitmapListLruCache mLruCache;
  
  public BitmapCache(int paramInt)
  {
    this.mLruCache = new BitmapListLruCache(paramInt);
  }
  
  public static final class BitmapCacheEntry
  {
    public Bitmap bitmap;
    public int requestedHeight;
    public int requestedWidth;
    
    public BitmapCacheEntry(Bitmap paramBitmap, int paramInt1, int paramInt2)
    {
      this.bitmap = paramBitmap;
      this.requestedWidth = paramInt1;
      this.requestedHeight = paramInt2;
    }
  }
  
  private final class BitmapListLruCache
    extends LruCache<String, ArrayList<BitmapCache.BitmapCacheEntry>>
  {
    public BitmapListLruCache(int paramInt)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.image.BitmapCache
 * JD-Core Version:    0.7.0.1
 */