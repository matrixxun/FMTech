package android.support.v7.widget;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.SeekBar;

final class AppCompatSeekBarHelper
  extends AppCompatProgressBarHelper
{
  private static final int[] TINT_ATTRS = { 16843074 };
  private final SeekBar mView;
  
  AppCompatSeekBarHelper(SeekBar paramSeekBar, TintManager paramTintManager)
  {
    super(paramSeekBar, paramTintManager);
    this.mView = paramSeekBar;
  }
  
  final void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    super.loadFromAttributes(paramAttributeSet, paramInt);
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes$1a6c1917(this.mView.getContext(), paramAttributeSet, TINT_ATTRS, paramInt);
    Drawable localDrawable = localTintTypedArray.getDrawableIfKnown(0);
    if (localDrawable != null) {
      this.mView.setThumb(localDrawable);
    }
    localTintTypedArray.mWrapped.recycle();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AppCompatSeekBarHelper
 * JD-Core Version:    0.7.0.1
 */