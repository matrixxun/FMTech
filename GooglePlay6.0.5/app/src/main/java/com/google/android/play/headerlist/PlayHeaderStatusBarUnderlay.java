package com.google.android.play.headerlist;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

@TargetApi(21)
class PlayHeaderStatusBarUnderlay
  extends View
  implements Animator.AnimatorListener
{
  private static Interpolator sLinearInterpolator;
  private ObjectAnimator mAlphaAnimator;
  private int mDrawHeight;
  int mFadeDirection;
  private int mOverlayColor;
  private final Paint mPaint;
  boolean mProtectingControls;
  int mStatusBarHeight;
  private int mUnderlayColor;
  
  public PlayHeaderStatusBarUnderlay(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayHeaderStatusBarUnderlay(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (PlayHeaderListLayout.SUPPORT_DRAW_STATUS_BAR)
    {
      this.mPaint = new Paint();
      this.mPaint.setAntiAlias(false);
      setAlpha(0.0F);
      return;
    }
    this.mPaint = null;
  }
  
  final void fade(int paramInt, boolean paramBoolean)
  {
    float f1 = getAlpha();
    if ((this.mAlphaAnimator != null) && (this.mAlphaAnimator.isStarted()))
    {
      f1 = ((Float)this.mAlphaAnimator.getAnimatedValue()).floatValue();
      this.mAlphaAnimator.cancel();
    }
    if (paramInt == 1) {}
    for (float f2 = 1.0F; (paramBoolean) && (f1 != f2); f2 = 0.0F)
    {
      if (sLinearInterpolator == null) {
        sLinearInterpolator = new LinearInterpolator();
      }
      this.mFadeDirection = paramInt;
      this.mAlphaAnimator = ObjectAnimator.ofFloat(this, ALPHA, new float[] { f1, f2 });
      this.mAlphaAnimator.setDuration((300.0F * Math.abs(f2 - f1)));
      this.mAlphaAnimator.setInterpolator(sLinearInterpolator);
      this.mAlphaAnimator.addListener(this);
      this.mAlphaAnimator.start();
      return;
    }
    setAlpha(f2);
  }
  
  public final void initColors(int paramInt1, int paramInt2)
  {
    this.mOverlayColor = paramInt1;
    this.mUnderlayColor = paramInt2;
  }
  
  public void onAnimationCancel(Animator paramAnimator)
  {
    if (paramAnimator == this.mAlphaAnimator)
    {
      this.mFadeDirection = 0;
      this.mAlphaAnimator = null;
    }
  }
  
  public void onAnimationEnd(Animator paramAnimator)
  {
    if (paramAnimator == this.mAlphaAnimator) {
      if (this.mFadeDirection != 2) {
        break label44;
      }
    }
    label44:
    for (int i = 1;; i = 0)
    {
      this.mFadeDirection = 0;
      this.mAlphaAnimator = null;
      if (i != 0) {
        setMinimumHeight(0);
      }
      setLayerType(0, null);
      return;
    }
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {}
  
  public void onAnimationStart(Animator paramAnimator)
  {
    setLayerType(2, null);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (!PlayHeaderListLayout.SUPPORT_DRAW_STATUS_BAR) {}
    int i;
    do
    {
      return;
      i = getWidth();
      this.mPaint.setColor(this.mUnderlayColor);
      paramCanvas.drawRect(0.0F, 0.0F, i, this.mDrawHeight, this.mPaint);
    } while (this.mDrawHeight <= this.mStatusBarHeight);
    this.mPaint.setColor(this.mOverlayColor);
    paramCanvas.drawRect(0.0F, this.mStatusBarHeight, i, this.mDrawHeight, this.mPaint);
  }
  
  final void setDrawHeight(int paramInt)
  {
    if (this.mDrawHeight == paramInt) {
      return;
    }
    this.mDrawHeight = paramInt;
    invalidate();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.headerlist.PlayHeaderStatusBarUnderlay
 * JD-Core Version:    0.7.0.1
 */