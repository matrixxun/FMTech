package android.support.v7.widget;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

final class AppCompatImageHelper
{
  private static final int[] VIEW_ATTRS = { 16843033 };
  private final TintManager mTintManager;
  private final ImageView mView;
  
  AppCompatImageHelper(ImageView paramImageView, TintManager paramTintManager)
  {
    this.mView = paramImageView;
    this.mTintManager = paramTintManager;
  }
  
  final void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes$1a6c1917(this.mView.getContext(), paramAttributeSet, VIEW_ATTRS, paramInt);
    try
    {
      if (localTintTypedArray.hasValue(0)) {
        this.mView.setImageDrawable(localTintTypedArray.getDrawable(0));
      }
      return;
    }
    finally
    {
      localTintTypedArray.mWrapped.recycle();
    }
  }
  
  final void setImageResource(int paramInt)
  {
    ImageView localImageView = this.mView;
    if (this.mTintManager != null) {}
    for (Drawable localDrawable = this.mTintManager.getDrawable(paramInt, false);; localDrawable = ContextCompat.getDrawable(this.mView.getContext(), paramInt))
    {
      localImageView.setImageDrawable(localDrawable);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AppCompatImageHelper
 * JD-Core Version:    0.7.0.1
 */