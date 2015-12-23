package com.google.android.wallet.ui.address;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import java.util.ArrayList;

@TargetApi(11)
public class AnimatedDynamicAddressFieldsLayout
  extends DynamicAddressFieldsLayout
  implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener
{
  private ValueAnimator mActiveAnimator;
  private ValueAnimator mAnimator1;
  private ValueAnimator mAnimator2;
  private boolean mExpandCalledWhileContracting;
  private ArrayList<View> mNewFields;
  private final ArrayList<View> mPendingReplaceNewViews = new ArrayList();
  private final ArrayList<View> mPendingReplaceOldViews = new ArrayList();
  private int mState = 1;
  
  public AnimatedDynamicAddressFieldsLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AnimatedDynamicAddressFieldsLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public AnimatedDynamicAddressFieldsLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private int getViewHeightAtTransitionStart()
  {
    return this.mProgressBar.getMeasuredHeight();
  }
  
  private void invokeOnHeightChanged(float paramFloat)
  {
    if (this.mOnHeightOffsetChangedListener != null) {
      this.mOnHeightOffsetChangedListener.onHeightOffsetChanged(paramFloat);
    }
  }
  
  private void setFieldsLayerType(int paramInt)
  {
    int i = 1;
    int j = this.mFieldContainer.getChildCount();
    while (i < j)
    {
      this.mFieldContainer.getChildAt(i).setLayerType(paramInt, null);
      i++;
    }
  }
  
  public void onAnimationCancel(Animator paramAnimator) {}
  
  public void onAnimationEnd(Animator paramAnimator)
  {
    setFieldsLayerType(0);
    this.mActiveAnimator.setStartDelay(0L);
    if (this.mState == 4)
    {
      this.mState = 2;
      this.mProgressBar.setVisibility(4);
    }
    for (;;)
    {
      if (this.mNewFields != null)
      {
        setFields(this.mNewFields);
        this.mNewFields = null;
      }
      int i = 0;
      int j = this.mPendingReplaceOldViews.size();
      while (i < j)
      {
        replaceView((View)this.mPendingReplaceOldViews.get(i), (View)this.mPendingReplaceNewViews.get(i));
        i++;
      }
      if (this.mState == 3)
      {
        this.mState = 1;
        setAddressFieldsVisibility(8);
      }
    }
    this.mPendingReplaceOldViews.clear();
    this.mPendingReplaceNewViews.clear();
    if ((this.mExpandCalledWhileContracting) && (this.mState == 1))
    {
      this.mExpandCalledWhileContracting = false;
      if (this.mActiveAnimator != this.mAnimator1) {
        break label181;
      }
    }
    label181:
    for (this.mActiveAnimator = this.mAnimator2;; this.mActiveAnimator = this.mAnimator1)
    {
      switchToShowingFields();
      return;
    }
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {}
  
  public void onAnimationStart(Animator paramAnimator)
  {
    setFieldsLayerType(2);
  }
  
  public void onAnimationUpdate(ValueAnimator paramValueAnimator)
  {
    float f = ((Float)paramValueAnimator.getAnimatedValue()).floatValue();
    int i = 1;
    int j = this.mFieldContainer.getChildCount();
    while (i < j)
    {
      View localView = this.mFieldContainer.getChildAt(i);
      localView.setAlpha(f);
      localView.setY(f * localView.getTop());
      i++;
    }
    this.mProgressBar.setAlpha(1.0F - f);
    invokeOnHeightChanged((1.0F - f) * (getViewHeightAtTransitionStart() - getMeasuredHeight()));
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAnimator1 = ValueAnimator.ofFloat(new float[] { 0.0F });
    this.mAnimator1.addUpdateListener(this);
    this.mAnimator1.addListener(this);
    this.mAnimator2 = ValueAnimator.ofFloat(new float[] { 0.0F });
    this.mAnimator2.addUpdateListener(this);
    this.mAnimator2.addListener(this);
    this.mActiveAnimator = this.mAnimator1;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    invokeOnHeightChanged((1.0F - ((Float)this.mActiveAnimator.getAnimatedValue()).floatValue()) * (getViewHeightAtTransitionStart() - paramInt2));
  }
  
  public final void replaceView(View paramView1, View paramView2)
  {
    if ((this.mState == 4) || (this.mState == 3))
    {
      this.mPendingReplaceOldViews.add(paramView1);
      this.mPendingReplaceNewViews.add(paramView2);
      return;
    }
    super.replaceView(paramView1, paramView2);
  }
  
  public void setFields(ArrayList<View> paramArrayList)
  {
    switch (this.mState)
    {
    default: 
    case 2: 
    case 3: 
    case 4: 
      do
      {
        return;
        addViews(paramArrayList);
        return;
        this.mNewFields = paramArrayList;
      } while (this.mPendingReplaceOldViews.isEmpty());
      this.mPendingReplaceOldViews.clear();
      this.mPendingReplaceNewViews.clear();
      return;
    }
    addViews(paramArrayList);
    setAddressFieldsVisibility(8);
  }
  
  public final void switchToShowingFields()
  {
    switch (this.mState)
    {
    case 2: 
    default: 
      return;
    case 1: 
      setAddressFieldsVisibility(0);
      this.mState = 4;
      this.mActiveAnimator.setFloatValues(new float[] { 0.0F, 1.0F });
      this.mActiveAnimator.start();
      return;
    }
    this.mExpandCalledWhileContracting = true;
  }
  
  public final void switchToShowingProgressBar()
  {
    switch (this.mState)
    {
    case 3: 
    default: 
      return;
    case 2: 
      this.mState = 3;
      this.mProgressBar.setVisibility(0);
      this.mActiveAnimator.setFloatValues(new float[] { 1.0F, 0.0F });
      this.mActiveAnimator.setStartDelay(200L);
      this.mActiveAnimator.setCurrentPlayTime(0L);
      this.mActiveAnimator.start();
      return;
    }
    this.mState = 3;
    this.mActiveAnimator.reverse();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.address.AnimatedDynamicAddressFieldsLayout
 * JD-Core Version:    0.7.0.1
 */