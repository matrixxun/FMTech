package com.google.android.play.animation;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

@TargetApi(21)
public final class PlayInterpolators
{
  private static Interpolator sFastOutSlowIn;
  
  public static Interpolator fastOutSlowIn(Context paramContext)
  {
    if (sFastOutSlowIn == null) {
      sFastOutSlowIn = AnimationUtils.loadInterpolator(paramContext.getApplicationContext(), 17563661);
    }
    return sFastOutSlowIn;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.animation.PlayInterpolators
 * JD-Core Version:    0.7.0.1
 */