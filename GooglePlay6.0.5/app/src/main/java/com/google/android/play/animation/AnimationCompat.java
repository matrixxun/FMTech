package com.google.android.play.animation;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

public final class AnimationCompat
{
  public float mAlpha = 1.0F;
  public float mScale = 1.0F;
  private float mTranslationX = 0.0F;
  private float mTranslationY = 0.0F;
  public final View mView;
  
  public AnimationCompat(View paramView)
  {
    this.mView = paramView;
  }
  
  @TargetApi(12)
  public final void animateAlpha$2549578(final float paramFloat)
  {
    if (this.mView == null) {
      return;
    }
    if (Build.VERSION.SDK_INT >= 12)
    {
      this.mView.animate().alpha(paramFloat).setDuration(200L);
      return;
    }
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(this.mAlpha, paramFloat);
    localAlphaAnimation.setDuration(200L);
    localAlphaAnimation.setFillAfter(true);
    if (Build.VERSION.SDK_INT < 12) {
      localAlphaAnimation.setAnimationListener(new Animation.AnimationListener()
      {
        public final void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          AnimationCompat.this.mAlpha = paramFloat;
        }
        
        public final void onAnimationRepeat(Animation paramAnonymousAnimation) {}
        
        public final void onAnimationStart(Animation paramAnonymousAnimation) {}
      });
    }
    this.mView.startAnimation(localAlphaAnimation);
  }
  
  @TargetApi(21)
  public final void animateZ(float paramFloat, int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      this.mView.animate().z(paramFloat).setDuration(paramInt1).setStartDelay(paramInt2);
    }
  }
  
  @TargetApi(11)
  public final float getTranslationY()
  {
    if (this.mView == null) {
      return this.mTranslationY;
    }
    if (Build.VERSION.SDK_INT >= 11) {
      return this.mView.getTranslationY();
    }
    return this.mTranslationY;
  }
  
  @TargetApi(11)
  public final void setAlpha(float paramFloat)
  {
    if (this.mView == null) {}
    do
    {
      return;
      if (Build.VERSION.SDK_INT >= 11)
      {
        this.mView.setAlpha(paramFloat);
        return;
      }
    } while (this.mAlpha == paramFloat);
    this.mAlpha = paramFloat;
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(paramFloat, paramFloat);
    localAlphaAnimation.setDuration(0L);
    localAlphaAnimation.setFillAfter(true);
    this.mView.startAnimation(localAlphaAnimation);
  }
  
  @TargetApi(11)
  public final void setTranslationY(float paramFloat)
  {
    if (this.mView == null) {
      this.mTranslationY = paramFloat;
    }
    do
    {
      return;
      if (Build.VERSION.SDK_INT >= 11)
      {
        this.mView.setTranslationY(paramFloat);
        return;
      }
    } while (this.mTranslationY == paramFloat);
    this.mTranslationY = paramFloat;
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(0.0F, 0.0F, paramFloat, paramFloat);
    localTranslateAnimation.setDuration(0L);
    localTranslateAnimation.setFillAfter(true);
    this.mView.startAnimation(localTranslateAnimation);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.animation.AnimationCompat
 * JD-Core Version:    0.7.0.1
 */