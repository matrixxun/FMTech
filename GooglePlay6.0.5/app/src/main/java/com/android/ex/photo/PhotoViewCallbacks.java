package com.android.ex.photo;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import com.android.ex.photo.adapters.PhotoPagerAdapter;
import com.android.ex.photo.fragments.PhotoViewFragment;
import com.android.ex.photo.loaders.PhotoBitmapLoaderInterface.BitmapResult;

public abstract interface PhotoViewCallbacks
{
  public abstract void addCursorListener(CursorChangedListener paramCursorChangedListener);
  
  public abstract void addScreenListener(int paramInt, OnScreenListener paramOnScreenListener);
  
  public abstract PhotoPagerAdapter getAdapter();
  
  public abstract boolean isFragmentActive(Fragment paramFragment);
  
  public abstract boolean isFragmentFullScreen(Fragment paramFragment);
  
  public abstract Loader<PhotoBitmapLoaderInterface.BitmapResult> onCreateBitmapLoader$21c6b1c7(int paramInt, String paramString);
  
  public abstract void onFragmentPhotoLoadComplete(PhotoViewFragment paramPhotoViewFragment, boolean paramBoolean);
  
  public abstract void removeCursorListener(CursorChangedListener paramCursorChangedListener);
  
  public abstract void removeScreenListener(int paramInt);
  
  public abstract void toggleFullScreen();
  
  public static abstract interface CursorChangedListener
  {
    public abstract void onCursorChanged(Cursor paramCursor);
  }
  
  public static abstract interface OnScreenListener
  {
    public abstract void onFullScreenChanged$1385ff();
    
    public abstract boolean onInterceptMoveLeft$2548a39();
    
    public abstract boolean onInterceptMoveRight$2548a39();
    
    public abstract void onViewActivated();
    
    public abstract void onViewUpNext();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.PhotoViewCallbacks
 * JD-Core Version:    0.7.0.1
 */