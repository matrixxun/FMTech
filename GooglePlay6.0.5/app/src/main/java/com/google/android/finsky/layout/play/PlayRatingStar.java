package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.caverock.androidsvg.SVG;
import com.google.android.finsky.utils.UiUtils;

public class PlayRatingStar
  extends ImageView
{
  int mFilledColorId;
  int mFilledFocusedResourceId;
  int mFilledResourceId;
  int mFilledSvgResourceId;
  int mFocusedResourceId;
  int mFocusedSvgResourceId;
  int mIndex;
  private boolean mIsFilled;
  private boolean mIsFocused;
  int mNormalColorId;
  int mNormalResourceId;
  int mNormalSvgResourceId;
  private OnPressStateChangeListener mOnPressStateChangeListener;
  private boolean mWasPressed;
  
  public PlayRatingStar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayRatingStar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public int getIndex()
  {
    return this.mIndex;
  }
  
  public void refreshDrawableState()
  {
    super.refreshDrawableState();
    boolean bool = isPressed();
    if (this.mWasPressed != bool)
    {
      if (this.mOnPressStateChangeListener != null) {
        this.mOnPressStateChangeListener.onPressStateChanged(this, bool);
      }
      this.mWasPressed = bool;
    }
  }
  
  public void setFilled(boolean paramBoolean)
  {
    this.mIsFilled = paramBoolean;
    syncState();
  }
  
  public void setFocused(boolean paramBoolean)
  {
    this.mIsFocused = paramBoolean;
    syncState();
  }
  
  public void setOnPressStateChangeListener(OnPressStateChangeListener paramOnPressStateChangeListener)
  {
    this.mOnPressStateChangeListener = paramOnPressStateChangeListener;
  }
  
  final void syncState()
  {
    if (UiUtils.isSvgExperimentEnabled())
    {
      Resources localResources = getContext().getResources();
      if (this.mIsFilled) {}
      for (Drawable localDrawable = SVG.getDrawableFromResource(localResources, this.mFilledSvgResourceId, this.mFilledColorId); this.mIsFocused; localDrawable = SVG.getDrawableFromResource(localResources, this.mNormalSvgResourceId, this.mNormalColorId))
      {
        setImageDrawable(new LayerDrawable(new Drawable[] { localDrawable, localResources.getDrawable(this.mFocusedSvgResourceId) }));
        return;
      }
      setImageDrawable(localDrawable);
      return;
    }
    if (this.mIsFilled)
    {
      if (this.mIsFocused) {}
      for (int j = this.mFilledFocusedResourceId;; j = this.mFilledResourceId)
      {
        setImageResource(j);
        return;
      }
    }
    if (this.mIsFocused) {}
    for (int i = this.mFocusedResourceId;; i = this.mNormalResourceId)
    {
      setImageResource(i);
      return;
    }
  }
  
  public static abstract interface OnPressStateChangeListener
  {
    public abstract void onPressStateChanged(View paramView, boolean paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayRatingStar
 * JD-Core Version:    0.7.0.1
 */