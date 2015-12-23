package com.google.android.finsky.layout;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public final class HeightAnimation
  extends Animation
{
  private boolean mCanceled;
  private int mDeltaToTargetHeight;
  private int mStartHeight;
  private final View mView;
  
  public HeightAnimation(View paramView)
  {
    this.mView = paramView;
  }
  
  protected final void applyTransformation(float paramFloat, Transformation paramTransformation)
  {
    if (this.mCanceled) {
      return;
    }
    int i = this.mStartHeight + (int)(paramFloat * this.mDeltaToTargetHeight);
    this.mView.getLayoutParams().height = i;
    this.mView.requestLayout();
  }
  
  public final void cancelForever()
  {
    super.cancel();
    this.mCanceled = true;
  }
  
  public final void setHeights(int paramInt1, int paramInt2)
  {
    this.mStartHeight = paramInt1;
    this.mDeltaToTargetHeight = (paramInt2 - paramInt1);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.HeightAnimation
 * JD-Core Version:    0.7.0.1
 */