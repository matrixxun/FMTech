package com.android.ex.photo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.SimpleArrayMap;
import com.android.ex.photo.Intents.PhotoViewIntentBuilder;
import com.android.ex.photo.fragments.PhotoViewFragment;
import com.android.ex.photo.provider.PhotoContract.PhotoQuery;

public final class PhotoPagerAdapter
  extends BaseCursorPagerAdapter
{
  protected SimpleArrayMap<String, Integer> mColumnIndices = new SimpleArrayMap(PhotoContract.PhotoQuery.PROJECTION.length);
  protected boolean mDisplayThumbsFullScreen;
  protected final float mMaxScale;
  
  public PhotoPagerAdapter(Context paramContext, FragmentManager paramFragmentManager, Cursor paramCursor, float paramFloat, boolean paramBoolean)
  {
    super(paramContext, paramFragmentManager, null);
    this.mMaxScale = paramFloat;
    this.mDisplayThumbsFullScreen = paramBoolean;
  }
  
  private String getString(Cursor paramCursor, String paramString)
  {
    if (this.mColumnIndices.containsKey(paramString)) {
      return paramCursor.getString(((Integer)this.mColumnIndices.get(paramString)).intValue());
    }
    return null;
  }
  
  public final Fragment getItem$5e88f9e1(Cursor paramCursor, int paramInt)
  {
    String str1 = getString(paramCursor, "contentUri");
    String str2 = getString(paramCursor, "thumbnailUri");
    String str3 = getString(paramCursor, "loadingIndicator");
    if (str3 == null) {}
    for (boolean bool1 = false;; bool1 = Boolean.valueOf(str3).booleanValue())
    {
      boolean bool2 = false;
      if (str1 == null)
      {
        bool2 = false;
        if (bool1) {
          bool2 = true;
        }
      }
      Intents.PhotoViewIntentBuilder localPhotoViewIntentBuilder = new Intents.PhotoViewIntentBuilder(this.mContext, PhotoViewFragment.class, (byte)0);
      localPhotoViewIntentBuilder.mResolvedPhotoUri = str1;
      localPhotoViewIntentBuilder.mThumbnailUri = str2;
      localPhotoViewIntentBuilder.mDisplayFullScreenThumbs = this.mDisplayThumbsFullScreen;
      localPhotoViewIntentBuilder.setMaxInitialScale(this.mMaxScale);
      return PhotoViewFragment.newInstance(localPhotoViewIntentBuilder.build(), paramInt, bool2);
    }
  }
  
  public final String getPhotoUri(Cursor paramCursor)
  {
    return getString(paramCursor, "contentUri");
  }
  
  public final String getThumbnailUri(Cursor paramCursor)
  {
    return getString(paramCursor, "thumbnailUri");
  }
  
  public final Cursor swapCursor(Cursor paramCursor)
  {
    this.mColumnIndices.clear();
    if (paramCursor != null)
    {
      for (String str2 : PhotoContract.PhotoQuery.PROJECTION) {
        this.mColumnIndices.put(str2, Integer.valueOf(paramCursor.getColumnIndexOrThrow(str2)));
      }
      for (String str1 : PhotoContract.PhotoQuery.OPTIONAL_COLUMNS)
      {
        int n = paramCursor.getColumnIndex(str1);
        if (n != -1) {
          this.mColumnIndices.put(str1, Integer.valueOf(n));
        }
      }
    }
    return super.swapCursor(paramCursor);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.adapters.PhotoPagerAdapter
 * JD-Core Version:    0.7.0.1
 */