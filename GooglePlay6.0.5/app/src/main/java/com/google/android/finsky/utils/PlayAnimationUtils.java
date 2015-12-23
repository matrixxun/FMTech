package com.google.android.finsky.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public final class PlayAnimationUtils
{
  @TargetApi(11)
  public static Animator getFadeAnimator$57852d9d(View paramView, float paramFloat, long paramLong)
  {
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView, "alpha", new float[] { paramFloat, 1.0F });
    localObjectAnimator.setStartDelay(0L);
    localObjectAnimator.setDuration(paramLong);
    localObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    return localObjectAnimator;
  }
  
  public static Animation getFadeInAnimation(Context paramContext, long paramLong, Animation.AnimationListener paramAnimationListener)
  {
    Animation localAnimation = AnimationUtils.loadAnimation(paramContext, 2131034129);
    localAnimation.setStartOffset(0L);
    localAnimation.setDuration(paramLong);
    if (paramAnimationListener != null) {
      localAnimation.setAnimationListener(paramAnimationListener);
    }
    return localAnimation;
  }
  
  public static Animation getFadeOutAnimation(Context paramContext, long paramLong, Animation.AnimationListener paramAnimationListener)
  {
    Animation localAnimation = AnimationUtils.loadAnimation(paramContext, 2131034127);
    localAnimation.setStartOffset(0L);
    localAnimation.setDuration(paramLong);
    if (paramAnimationListener != null) {
      localAnimation.setAnimationListener(paramAnimationListener);
    }
    return localAnimation;
  }
  
  public static class AnimationListenerAdapter
    implements Animation.AnimationListener
  {
    public void onAnimationEnd(Animation paramAnimation) {}
    
    public void onAnimationRepeat(Animation paramAnimation) {}
    
    public void onAnimationStart(Animation paramAnimation) {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PlayAnimationUtils
 * JD-Core Version:    0.7.0.1
 */