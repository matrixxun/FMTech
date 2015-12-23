package com.google.android.finsky.layout.play;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public final class FinskyViewPagerScroller
  extends Scroller
{
  private final int mDuration = 500;
  
  public FinskyViewPagerScroller(Context paramContext, Interpolator paramInterpolator, int paramInt)
  {
    super(paramContext, paramInterpolator);
  }
  
  public final void startScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.startScroll(paramInt1, paramInt2, paramInt3, paramInt4, this.mDuration);
  }
  
  public final void startScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    super.startScroll(paramInt1, paramInt2, paramInt3, paramInt4, this.mDuration);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.FinskyViewPagerScroller
 * JD-Core Version:    0.7.0.1
 */