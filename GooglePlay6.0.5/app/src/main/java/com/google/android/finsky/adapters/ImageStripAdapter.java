package com.google.android.finsky.adapters;

import android.database.DataSetObservable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.protos.Common.Image.Dimension;

public final class ImageStripAdapter
{
  public final DataSetObservable mDataSetObservable;
  OnImageChildViewTapListener mImageChildTappedListener;
  public final int mImageCount;
  public final Common.Image.Dimension[] mImageDimensions;
  public final Drawable[] mImages;
  
  public ImageStripAdapter(int paramInt, OnImageChildViewTapListener paramOnImageChildViewTapListener)
  {
    this.mImageCount = paramInt;
    this.mImages = new Drawable[this.mImageCount];
    this.mImageDimensions = new Common.Image.Dimension[this.mImageCount];
    this.mDataSetObservable = new DataSetObservable();
    this.mImageChildTappedListener = paramOnImageChildViewTapListener;
  }
  
  public final void getImageDimensionAt(int paramInt, Common.Image.Dimension paramDimension, float paramFloat)
  {
    Drawable localDrawable = this.mImages[paramInt];
    if (localDrawable != null)
    {
      paramDimension.width = ((int)(paramFloat * localDrawable.getIntrinsicWidth()));
      paramDimension.height = ((int)(paramFloat * localDrawable.getIntrinsicHeight()));
    }
    for (;;)
    {
      paramDimension.hasWidth = true;
      paramDimension.hasHeight = true;
      return;
      if (this.mImageDimensions[paramInt] != null)
      {
        paramDimension.width = this.mImageDimensions[paramInt].width;
        paramDimension.height = this.mImageDimensions[paramInt].height;
      }
      else
      {
        paramDimension.width = 0;
        paramDimension.height = 0;
      }
    }
  }
  
  public final void setImageAt(int paramInt, Drawable paramDrawable)
  {
    this.mImages[paramInt] = paramDrawable;
    this.mDataSetObservable.notifyChanged();
  }
  
  public static abstract interface OnImageChildViewTapListener
  {
    public abstract void onImageChildViewTap(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.ImageStripAdapter
 * JD-Core Version:    0.7.0.1
 */