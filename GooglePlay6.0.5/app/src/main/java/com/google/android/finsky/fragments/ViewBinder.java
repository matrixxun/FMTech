package com.google.android.finsky.fragments;

import android.content.Context;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.play.image.BitmapLoader;

public abstract class ViewBinder<T>
{
  protected BitmapLoader mBitmapLoader;
  protected Context mContext;
  protected T mData;
  protected NavigationManager mNavManager;
  
  public final void init(Context paramContext, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader)
  {
    if (this.mContext == paramContext) {
      return;
    }
    this.mContext = paramContext;
    this.mNavManager = paramNavigationManager;
    this.mBitmapLoader = paramBitmapLoader;
    this.mData = null;
  }
  
  public void setData(T paramT)
  {
    this.mData = paramT;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.fragments.ViewBinder
 * JD-Core Version:    0.7.0.1
 */