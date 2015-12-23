package com.google.android.finsky.layout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.google.android.play.headerlist.PlayHeaderListTabStrip;

public class FinskyTabStrip
  extends PlayHeaderListTabStrip
{
  private ControlsContainerBackgroundCoordinator mControlsContainerBackgroundCoordinator;
  public boolean mEnableFixedTabs;
  private ViewGroup mTabContainerViewGroup;
  public boolean mUseFixedTabWideLayout;
  
  public FinskyTabStrip(Context paramContext)
  {
    super(paramContext);
  }
  
  public FinskyTabStrip(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public FinskyTabStrip(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void setChildWidths(int paramInt)
  {
    if (this.mTabContainerViewGroup == null) {}
    for (;;)
    {
      return;
      int i = this.mTabContainerViewGroup.getChildCount();
      int j = paramInt / i;
      for (int k = 0; k < i; k++)
      {
        TextView localTextView = (TextView)this.mTabContainerViewGroup.getChildAt(k);
        ViewGroup.LayoutParams localLayoutParams = localTextView.getLayoutParams();
        localTextView.setMaxWidth(j);
        if ((!this.mUseFixedTabWideLayout) && (j != localLayoutParams.width))
        {
          localLayoutParams.width = j;
          localTextView.setLayoutParams(localLayoutParams);
        }
      }
    }
  }
  
  protected final View makeTabView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, int paramInt)
  {
    if (!this.mEnableFixedTabs) {
      return super.makeTabView(paramLayoutInflater, paramViewGroup, paramInt);
    }
    this.mTabContainerViewGroup = paramViewGroup;
    return paramLayoutInflater.inflate(2130968749, paramViewGroup, false);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    super.onFinishInflate();
    ControlsContainerBackgroundCoordinator localControlsContainerBackgroundCoordinator = this.mControlsContainerBackgroundCoordinator;
    localControlsContainerBackgroundCoordinator.detach();
    localControlsContainerBackgroundCoordinator.mTabStrip = this;
    localControlsContainerBackgroundCoordinator.mTabStrip.getViewTreeObserver().addOnPreDrawListener(localControlsContainerBackgroundCoordinator.mOnPreDrawListener);
    for (View localView = (View)localControlsContainerBackgroundCoordinator.mTabStrip.getParent();; localView = (View)localView.getParent()) {
      if (localView.getId() == 2131755913)
      {
        localControlsContainerBackgroundCoordinator.mControlsContainer = ((ViewGroup)localView);
        localControlsContainerBackgroundCoordinator.mHeroContainer = localControlsContainerBackgroundCoordinator.mControlsContainer.findViewById(2131755914);
        localControlsContainerBackgroundCoordinator.mBackground = ((ControlsContainerBackground)LayoutInflater.from(localControlsContainerBackgroundCoordinator.mTabStrip.getContext()).inflate(2130968671, localControlsContainerBackgroundCoordinator.mControlsContainer, false));
        if (localControlsContainerBackgroundCoordinator.mQueuedBackgroundDrawable != null)
        {
          localControlsContainerBackgroundCoordinator.mBackground.setBackgroundDrawable(localControlsContainerBackgroundCoordinator.mQueuedBackgroundDrawable, 0, false);
          localControlsContainerBackgroundCoordinator.mQueuedBackgroundDrawable = null;
        }
        localControlsContainerBackgroundCoordinator.mControlsContainer.addView(localControlsContainerBackgroundCoordinator.mBackground, 0);
        if (ControlsContainerBackgroundCoordinator.SUPPORT_ELEVATION) {
          localControlsContainerBackgroundCoordinator.mControlsContainer.setOutlineProvider(new ControlsContainerBackgroundCoordinator.2(localControlsContainerBackgroundCoordinator));
        }
        return;
      }
    }
  }
  
  protected void onFinishInflate()
  {
    ViewCompat.setImportantForAccessibility(this, 1);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    this.mControlsContainerBackgroundCoordinator.mLastTouchX = ((int)paramMotionEvent.getX());
    return super.onInterceptTouchEvent(paramMotionEvent);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (!this.mEnableFixedTabs)
    {
      super.onMeasure(paramInt1, paramInt2);
      return;
    }
    setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    if (this.mTabContainerViewGroup != null) {
      this.mTabContainerViewGroup.setPadding(0, 0, 0, 0);
    }
    setChildWidths(getMeasuredWidth());
    measureChildren(paramInt1, paramInt2);
    this.mControlsContainerBackgroundCoordinator.updateBackgroundHeightAndFades();
  }
  
  public void setControlsContainerBackgroundCoordinator(ControlsContainerBackgroundCoordinator paramControlsContainerBackgroundCoordinator)
  {
    this.mControlsContainerBackgroundCoordinator = paramControlsContainerBackgroundCoordinator;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.FinskyTabStrip
 * JD-Core Version:    0.7.0.1
 */