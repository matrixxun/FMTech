package com.android.ex.photo.loaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public abstract interface PhotoBitmapLoaderInterface
{
  public abstract void forceLoad();
  
  public abstract void setPhotoUri(String paramString);
  
  public static final class BitmapResult
  {
    public Bitmap bitmap;
    public Drawable drawable;
    public int status;
    
    public final Drawable getDrawable(Resources paramResources)
    {
      if (paramResources == null) {
        throw new IllegalArgumentException("resources can not be null!");
      }
      if (this.drawable != null) {
        return this.drawable;
      }
      if (this.bitmap == null) {
        return null;
      }
      return new BitmapDrawable(paramResources, this.bitmap);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.loaders.PhotoBitmapLoaderInterface
 * JD-Core Version:    0.7.0.1
 */