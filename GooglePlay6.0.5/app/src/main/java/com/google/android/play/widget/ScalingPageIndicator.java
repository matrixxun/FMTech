package com.google.android.play.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import com.google.android.play.R.color;
import com.google.android.play.R.dimen;
import com.google.android.play.animation.PlayInterpolators;
import java.util.List;

public class ScalingPageIndicator
  extends PageIndicator
{
  private SparseArray<Animator> mDotStateAnimators = new SparseArray();
  public List<Animator> mDotWaitingAnimators;
  public Animation mWaitingAnimation;
  public int mWaitingAnimationCurrentCycle;
  public Animator mWaitingAnimator;
  
  public ScalingPageIndicator(Context paramContext)
  {
    super(paramContext);
  }
  
  public ScalingPageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ScalingPageIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  @TargetApi(14)
  private void cancelAnimatorsForDot(int paramInt)
  {
    if ((this.mDotWaitingAnimators != null) && (this.mDotWaitingAnimators.size() > 1 + paramInt * 2))
    {
      ((Animator)this.mDotWaitingAnimators.get(paramInt * 2)).cancel();
      ((Animator)this.mDotWaitingAnimators.get(1 + paramInt * 2)).cancel();
      this.mDotWaitingAnimators.clear();
    }
  }
  
  private void setDotColor(ImageView paramImageView)
  {
    ((GradientDrawable)paramImageView.getDrawable()).setColor(getResources().getColor(R.color.play_onboard__page_indicator_dot_active));
  }
  
  @TargetApi(14)
  public final Animator createScaleAnimator(final View paramView, float paramFloat1, float paramFloat2, long paramLong)
  {
    ValueAnimator localValueAnimator = ValueAnimator.ofFloat(new float[] { paramFloat1, paramFloat2 });
    localValueAnimator.setDuration(paramLong);
    if (Build.VERSION.SDK_INT >= 21) {
      localValueAnimator.setInterpolator(PlayInterpolators.fastOutSlowIn(paramView.getContext()));
    }
    for (;;)
    {
      localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public final void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
          paramView.setScaleX(f);
          paramView.setScaleY(f);
        }
      });
      return localValueAnimator;
      localValueAnimator.setInterpolator(new FastOutSlowInInterpolator());
    }
  }
  
  public final void ensureCurrentPageSelected()
  {
    int i = getSelectedPage();
    if ((i >= 0) && (i < getPageCount()))
    {
      if (Build.VERSION.SDK_INT >= 14) {
        cancelAnimatorsForDot(i);
      }
      setDotState((ImageView)getChildAt(i), true, true, i);
    }
  }
  
  protected int getDotHorizontalMargin()
  {
    if (Build.VERSION.SDK_INT < 14) {
      return super.getDotHorizontalMargin();
    }
    return (int)(0.6F * getResources().getDimensionPixelSize(R.dimen.play_onboard__page_indicator_dot_diameter)) / 4;
  }
  
  protected final ImageView setDotState(ImageView paramImageView, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    if (Build.VERSION.SDK_INT < 14) {
      return super.setDotState(paramImageView, paramBoolean1, paramBoolean2, paramInt);
    }
    float f;
    if (paramBoolean1)
    {
      f = 1.0F;
      paramImageView.setTag(Integer.valueOf(paramInt));
      if (!paramBoolean2) {
        break label133;
      }
      setDotColor(paramImageView);
      cancelAnimatorsForDot(paramInt);
      Animator localAnimator1 = (Animator)this.mDotStateAnimators.get(paramInt);
      if (localAnimator1 != null) {
        localAnimator1.cancel();
      }
      if (f != 1.0F) {
        break label125;
      }
    }
    label125:
    for (long l = 200L;; l = 500L)
    {
      Animator localAnimator2 = createScaleAnimator(paramImageView, paramImageView.getScaleX(), f, l);
      localAnimator2.start();
      this.mDotStateAnimators.put(paramInt, localAnimator2);
      return paramImageView;
      f = 0.6F;
      break;
    }
    label133:
    setDotColor(paramImageView);
    paramImageView.setScaleX(f);
    paramImageView.setScaleY(f);
    return paramImageView;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.widget.ScalingPageIndicator
 * JD-Core Version:    0.7.0.1
 */