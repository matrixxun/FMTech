package com.google.android.play.drawer;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import com.google.android.play.utils.BakedBezierInterpolator;

public final class PlayDrawerArrowDrawable
  extends DrawerArrowDrawable
{
  protected static final boolean SUPPORTS_ANIMATIONS;
  private ValueAnimator mDrawerArrowAnimator;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11) {}
    for (boolean bool = true;; bool = false)
    {
      SUPPORTS_ANIMATIONS = bool;
      return;
    }
  }
  
  public PlayDrawerArrowDrawable(Context paramContext)
  {
    super(paramContext);
  }
  
  public final void setAlpha(int paramInt)
  {
    super.setAlpha(paramInt);
    invalidateSelf();
  }
  
  public final void setPosition(float paramFloat)
  {
    if (paramFloat == 1.0F) {
      setVerticalMirror(true);
    }
    for (;;)
    {
      setProgress(paramFloat);
      return;
      if (paramFloat == 0.0F) {
        setVerticalMirror(false);
      }
    }
  }
  
  @TargetApi(11)
  public final void setState(int paramInt1, int paramInt2)
  {
    if (!SUPPORTS_ANIMATIONS)
    {
      float f1 = 0.0F;
      if (paramInt1 == 0) {}
      for (;;)
      {
        setPosition(f1);
        return;
        f1 = 1.0F;
      }
    }
    switch (paramInt2)
    {
    default: 
      return;
    case 0: 
      float f5 = 0.0F;
      if (paramInt1 == 0) {}
      for (;;)
      {
        setPosition(f5);
        return;
        f5 = 1.0F;
      }
    case 1: 
      float f4 = 0.0F;
      if (paramInt1 == 0) {}
      for (;;)
      {
        setPosition(f4);
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofInt(this, "alpha", new int[] { 0, 255 }).setDuration(400L);
        localObjectAnimator.setInterpolator(BakedBezierInterpolator.INSTANCE);
        localObjectAnimator.start();
        return;
        f4 = 1.0F;
      }
    }
    if (this.mDrawerArrowAnimator != null) {
      this.mDrawerArrowAnimator.cancel();
    }
    float f2 = this.mProgress;
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = f2;
    float f3 = 0.0F;
    ValueAnimator localValueAnimator;
    if (paramInt1 == 0)
    {
      arrayOfFloat[1] = f3;
      this.mDrawerArrowAnimator = ValueAnimator.ofFloat(arrayOfFloat);
      localValueAnimator = this.mDrawerArrowAnimator;
      if (paramInt1 != 0) {
        break label251;
      }
    }
    label251:
    for (long l = (f2 * 350.0F);; l = (350.0F * (1.0F - f2)))
    {
      localValueAnimator.setDuration(l);
      this.mDrawerArrowAnimator.setInterpolator(BakedBezierInterpolator.INSTANCE);
      this.mDrawerArrowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public final void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
          PlayDrawerArrowDrawable.this.setPosition(f);
        }
      });
      this.mDrawerArrowAnimator.start();
      return;
      f3 = 1.0F;
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.drawer.PlayDrawerArrowDrawable
 * JD-Core Version:    0.7.0.1
 */