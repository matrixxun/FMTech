package com.google.android.finsky.layout;

import android.view.View;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;

public abstract interface ClusterContentBinder<T extends View>
{
  public abstract void bindChild(T paramT, int paramInt);
  
  public abstract int getAvailableChildCount();
  
  public abstract String getChildContentSourceId();
  
  public abstract float getChildCoverAspectRatio(int paramInt);
  
  public abstract float getChildCoverAspectRatio(T paramT);
  
  public abstract int getChildCoverHeight(T paramT);
  
  public abstract int getChildCoverWidth(T paramT);
  
  public abstract int getChildLayoutId$134621();
  
  public abstract boolean isMoreDataAvailable();
  
  public abstract BitmapLoader.BitmapContainer preloadChildCoverImage(int paramInt1, int paramInt2, int paramInt3, BitmapLoader.BitmapLoadedHandler paramBitmapLoadedHandler, int[] paramArrayOfInt);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ClusterContentBinder
 * JD-Core Version:    0.7.0.1
 */