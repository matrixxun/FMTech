package com.google.android.finsky.layout;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewPropertyAnimator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import com.google.android.finsky.utils.FinskyLog;
import java.util.Iterator;
import java.util.List;

@TargetApi(12)
public class AddressFieldsLayoutHoneycomb
  extends AddressFieldsLayout
  implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener, View.OnFocusChangeListener
{
  private static String KEY_PARENT_STATE = "parent_instance_state";
  private static String KEY_SUPPORT_SHOWING_ONE_FIELD = "support_showing_one_field";
  private ValueAnimator mAnimator;
  private ValueAnimator mAnimator1;
  private ValueAnimator mAnimator2;
  private boolean mExpandCalledWhileContracting;
  private CharSequence mFirstFieldHint;
  private List<View> mNewFields;
  private OnHeightOffsetChangedListener mOnHeightChangedListener;
  private int mState = 0;
  private boolean mSupportShowingOneField = true;
  
  public AddressFieldsLayoutHoneycomb(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AddressFieldsLayoutHoneycomb(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public AddressFieldsLayoutHoneycomb(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void addViews(List<View> paramList)
  {
    this.mFieldContainer.removeAllViews();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      View localView = (View)localIterator.next();
      localView.setLayerType(2, null);
      LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
      this.mFieldContainer.addView(localView, localLayoutParams);
    }
  }
  
  private int getViewHeightAtTransitionStart()
  {
    if ((this.mState == 6) && (this.mFieldContainer.getChildCount() > 0)) {
      return this.mFieldContainer.getChildAt(0).getHeight();
    }
    return this.mProgressBar.getMeasuredHeight();
  }
  
  private void invokeOnHeightChanged(float paramFloat)
  {
    if (this.mOnHeightChangedListener != null) {
      this.mOnHeightChangedListener.onHeightOffsetChanged(paramFloat);
    }
  }
  
  private void makeOnlyFirstFieldVisible()
  {
    setChildrenViewVisibility(8);
    if (this.mFieldContainer.getChildCount() > 0) {
      this.mFieldContainer.getChildAt(0).setVisibility(0);
    }
  }
  
  private void setChildrenViewVisibility(int paramInt)
  {
    int i = this.mFieldContainer.getChildCount();
    for (int j = 0; j < i; j++) {
      this.mFieldContainer.getChildAt(j).setVisibility(paramInt);
    }
  }
  
  public final void disableOneFieldMode()
  {
    this.mSupportShowingOneField = false;
    switch (this.mState)
    {
    case 3: 
    case 4: 
    default: 
      return;
    case 2: 
      setChildrenViewVisibility(0);
      return;
    }
    this.mAnimator.end();
    this.mState = 1;
    setChildrenViewVisibility(0);
  }
  
  public final void hideUpperRightProgressBar()
  {
    this.mUpperRightProgressBar.animate().alpha(0.0F).setListener(new AnimatorListenerAdapter()
    {
      public final void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        AddressFieldsLayoutHoneycomb.this.mUpperRightProgressBar.setVisibility(8);
        AddressFieldsLayoutHoneycomb.this.mUpperRightProgressBar.animate().setListener(null);
      }
    });
  }
  
  public void onAnimationCancel(Animator paramAnimator) {}
  
  public void onAnimationEnd(Animator paramAnimator)
  {
    this.mAnimator.setStartDelay(0L);
    if (this.mState == 4)
    {
      this.mState = 1;
      this.mProgressBar.setVisibility(4);
      if (this.mNewFields != null)
      {
        setFields(this.mNewFields);
        this.mNewFields = null;
      }
      if ((this.mExpandCalledWhileContracting) && (this.mState == 0) && (this.mExpandCalledWhileContracting))
      {
        this.mExpandCalledWhileContracting = false;
        if (this.mAnimator != this.mAnimator1) {
          break label162;
        }
      }
    }
    label162:
    for (this.mAnimator = this.mAnimator2;; this.mAnimator = this.mAnimator1)
    {
      showFields();
      return;
      if (this.mState == 5)
      {
        this.mState = 2;
        this.mProgressBar.setVisibility(4);
        break;
      }
      if (this.mState == 3)
      {
        this.mState = 0;
        setChildrenViewVisibility(8);
        break;
      }
      if (this.mState != 6) {
        break;
      }
      this.mState = 1;
      break;
    }
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {}
  
  public void onAnimationStart(Animator paramAnimator) {}
  
  public void onAnimationUpdate(ValueAnimator paramValueAnimator)
  {
    float f = ((Float)paramValueAnimator.getAnimatedValue()).floatValue();
    int i = this.mFieldContainer.getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = this.mFieldContainer.getChildAt(j);
      if ((j != 0) || (this.mState != 6)) {
        localView.setAlpha(f);
      }
      localView.setY(f * localView.getTop());
    }
    if ((this.mState == 6) && (this.mFieldContainer.getChildCount() > 0)) {
      this.mProgressBar.setAlpha(0.0F);
    }
    for (;;)
    {
      invokeOnHeightChanged((1.0F - f) * (getViewHeightAtTransitionStart() - getMeasuredHeight()));
      return;
      this.mProgressBar.setAlpha(1.0F - f);
    }
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
    this.mAnimator = this.mAnimator1;
  }
  
  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    if ((paramBoolean) && ((this.mState == 2) || (this.mState == 5)))
    {
      paramView.setOnFocusChangeListener(null);
      this.mSupportShowingOneField = false;
      this.mState = 6;
      setChildrenViewVisibility(0);
      this.mAnimator.setFloatValues(new float[] { 0.0F, 1.0F });
      this.mAnimator.setStartDelay(200L);
      this.mAnimator.setCurrentPlayTime(0L);
      this.mAnimator.start();
      ((EditText)paramView).setHint(this.mFirstFieldHint);
      onAnimationUpdate(this.mAnimator);
    }
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      Bundle localBundle = (Bundle)paramParcelable;
      this.mSupportShowingOneField = localBundle.getBoolean(KEY_SUPPORT_SHOWING_ONE_FIELD);
      super.onRestoreInstanceState(localBundle.getParcelable(KEY_PARENT_STATE));
      return;
    }
    super.onRestoreInstanceState(paramParcelable);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable(KEY_PARENT_STATE, super.onSaveInstanceState());
    localBundle.putBoolean(KEY_SUPPORT_SHOWING_ONE_FIELD, this.mSupportShowingOneField);
    return localBundle;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    invokeOnHeightChanged((1.0F - ((Float)this.mAnimator.getAnimatedValue()).floatValue()) * (getViewHeightAtTransitionStart() - paramInt2));
  }
  
  public void setFields(List<View> paramList)
  {
    int i;
    if ((this.mSupportShowingOneField) && (paramList.size() > 1) && ((paramList.get(0) instanceof EditText)))
    {
      i = 1;
      switch (this.mState)
      {
      default: 
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(this.mState);
        FinskyLog.wtf("enum %d", arrayOfObject);
        label105:
        if (i != 0)
        {
          localEditText = (EditText)paramList.get(0);
          this.mFirstFieldHint = localEditText.getHint();
          localEditText.setHint(2131361827);
          localEditText.setOnFocusChangeListener(this);
        }
        break;
      }
    }
    while (!this.mSupportShowingOneField)
    {
      EditText localEditText;
      return;
      i = 0;
      break;
      addViews(paramList);
      break label105;
      addViews(paramList);
      makeOnlyFirstFieldVisible();
      break label105;
      this.mNewFields = paramList;
      break label105;
      addViews(paramList);
      setChildrenViewVisibility(8);
      break label105;
    }
    this.mSupportShowingOneField = false;
  }
  
  public void setOnHeightOffsetChangedListener(OnHeightOffsetChangedListener paramOnHeightOffsetChangedListener)
  {
    this.mOnHeightChangedListener = paramOnHeightOffsetChangedListener;
  }
  
  public final void showFields()
  {
    switch (this.mState)
    {
    case 1: 
    case 2: 
    default: 
      return;
    case 0: 
      if (this.mSupportShowingOneField) {
        makeOnlyFirstFieldVisible();
      }
      for (this.mState = 5;; this.mState = 4)
      {
        this.mAnimator.setFloatValues(new float[] { 0.0F, 1.0F });
        this.mAnimator.start();
        return;
        setChildrenViewVisibility(0);
      }
    }
    this.mExpandCalledWhileContracting = true;
  }
  
  public final void showProgressBar()
  {
    switch (this.mState)
    {
    case 3: 
    default: 
      return;
    case 1: 
    case 2: 
      this.mState = 3;
      this.mProgressBar.setVisibility(0);
      this.mAnimator.setFloatValues(new float[] { 1.0F, 0.0F });
      this.mAnimator.setStartDelay(200L);
      this.mAnimator.setCurrentPlayTime(0L);
      this.mAnimator.start();
      return;
    }
    this.mState = 3;
    this.mAnimator.reverse();
  }
  
  public final void showUpperRightProgressBar()
  {
    this.mUpperRightProgressBar.setVisibility(0);
    this.mUpperRightProgressBar.setAlpha(0.0F);
    this.mUpperRightProgressBar.animate().alpha(1.0F);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AddressFieldsLayoutHoneycomb
 * JD-Core Version:    0.7.0.1
 */