package com.google.android.finsky.layout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class DetailsExpandedFrame
  extends ViewGroup
{
  private View mContentScroller;
  private View mLeftBar;
  private View mRightBar;
  private int mScrollerWidth;
  
  public DetailsExpandedFrame(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsExpandedFrame(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mLeftBar = findViewById(2131755578);
    this.mRightBar = findViewById(2131755585);
    this.mContentScroller = findViewById(2131755579);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getHeight();
    int k = this.mContentScroller.getMeasuredWidth();
    int m = (i - k) / 2;
    this.mContentScroller.layout(m, 0, m + k, j);
    if (this.mLeftBar.getVisibility() == 0) {
      this.mLeftBar.layout(m - this.mLeftBar.getMeasuredWidth(), 0, m, j);
    }
    if (this.mRightBar.getVisibility() == 0) {
      this.mRightBar.layout(m + k, 0, m + k + this.mRightBar.getMeasuredWidth(), j);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = Math.min(this.mScrollerWidth, i);
    this.mContentScroller.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), paramInt2);
    int k = (i - j) / 2;
    if (k == 0)
    {
      this.mLeftBar.setVisibility(8);
      this.mRightBar.setVisibility(8);
    }
    for (;;)
    {
      setMeasuredDimension(i, View.MeasureSpec.getSize(paramInt2));
      return;
      this.mLeftBar.setVisibility(0);
      this.mRightBar.setVisibility(0);
      int m = k - getPaddingLeft();
      this.mLeftBar.measure(View.MeasureSpec.makeMeasureSpec(m, 1073741824), paramInt2);
      int n = k - getPaddingRight();
      this.mRightBar.measure(View.MeasureSpec.makeMeasureSpec(n, 1073741824), paramInt2);
    }
  }
  
  public void setScrollerWidth(int paramInt)
  {
    this.mScrollerWidth = paramInt;
  }
  
  public void setSideBarProportion(float paramFloat)
  {
    int i = (getWidth() - this.mScrollerWidth) / 2;
    int j = i - (int)(paramFloat * i);
    ViewCompat.setPaddingRelative(this, j, 0, j, 0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsExpandedFrame
 * JD-Core Version:    0.7.0.1
 */