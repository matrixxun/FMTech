package com.google.android.finsky.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.play.animation.PlayInterpolators;

public class ControlsContainerBackground
  extends FrameLayout
{
  private static final boolean CAN_USE_CIRCULAR_REVEAL;
  private View mDrawableContainer;
  HeightAnimation mHeightAnimation;
  int mMaxHeight;
  int mMinHeight;
  boolean mUseMaxHeight;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {}
    for (boolean bool = true;; bool = false)
    {
      CAN_USE_CIRCULAR_REVEAL = bool;
      return;
    }
  }
  
  public ControlsContainerBackground(Context paramContext)
  {
    super(paramContext);
  }
  
  public ControlsContainerBackground(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ControlsContainerBackground(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public ControlsContainerBackground(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  @TargetApi(21)
  public final void setBackgroundDrawable(Drawable paramDrawable, int paramInt, boolean paramBoolean)
  {
    View localView1 = LayoutInflater.from(getContext()).inflate(2130968672, this, false);
    addView(localView1);
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView1.getLayoutParams();
    localLayoutParams.height = this.mMaxHeight;
    localView1.setLayoutParams(localLayoutParams);
    localView1.setBackgroundDrawable(paramDrawable);
    final View localView2 = this.mDrawableContainer;
    this.mDrawableContainer = localView1;
    if ((!paramBoolean) || (!CAN_USE_CIRCULAR_REVEAL) || (localView2 == null))
    {
      removeView(localView2);
      return;
    }
    int[] arrayOfInt = new int[2];
    getLocationInWindow(arrayOfInt);
    int i = getHeight();
    int j;
    Animator localAnimator;
    if (arrayOfInt[1] < 0)
    {
      j = arrayOfInt[1];
      int k = j + i - this.mMinHeight / 2;
      int m = Math.max(paramInt, getWidth() - paramInt);
      localAnimator = ViewAnimationUtils.createCircularReveal(localView1, paramInt, this.mMaxHeight - this.mMinHeight / 2, 0.0F, (float)Math.sqrt(m * m + k * k)).setDuration(400L);
      localAnimator.addListener(new AnimatorListenerAdapter()
      {
        public final void onAnimationCancel(Animator paramAnonymousAnimator)
        {
          ControlsContainerBackground.this.removeView(localView2);
        }
        
        public final void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          ControlsContainerBackground.this.removeView(localView2);
        }
      });
      if (Build.VERSION.SDK_INT < 21) {
        break label240;
      }
    }
    label240:
    for (Object localObject = PlayInterpolators.fastOutSlowIn(getContext());; localObject = new FastOutSlowInInterpolator())
    {
      localAnimator.setInterpolator((TimeInterpolator)localObject);
      localAnimator.start();
      return;
      j = 0;
      break;
    }
  }
  
  public final boolean setHeights(int paramInt1, int paramInt2)
  {
    if ((this.mMinHeight == paramInt1) && (this.mMaxHeight == paramInt2)) {
      return false;
    }
    ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
    if (this.mUseMaxHeight) {}
    for (int i = paramInt2;; i = paramInt1)
    {
      localLayoutParams.height = i;
      setLayoutParams(localLayoutParams);
      this.mMinHeight = paramInt1;
      this.mMaxHeight = paramInt2;
      if (this.mHeightAnimation != null)
      {
        this.mHeightAnimation.cancelForever();
        if (this.mHeightAnimation == getAnimation()) {
          clearAnimation();
        }
        this.mHeightAnimation = null;
      }
      if (this.mDrawableContainer != null) {
        setBackgroundDrawable(this.mDrawableContainer.getBackground(), 0, false);
      }
      return true;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ControlsContainerBackground
 * JD-Core Version:    0.7.0.1
 */