package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.android.vending.R.styleable;

public class ProportionalOuterFrame
  extends ViewGroup
{
  private float mProportion;
  private int mTitleHeight;
  
  public ProportionalOuterFrame(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ProportionalOuterFrame(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ProportionalOuterFrame(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ProportionalOuterFrame);
    this.mProportion = localTypedArray.getFraction(0, 1, 1, 1.0F);
    localTypedArray.recycle();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    this.mTitleHeight = 0;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (getChildCount() > 0)
    {
      View localView = getChildAt(0);
      int i = (getWidth() - localView.getMeasuredWidth()) / 2;
      localView.layout(i, 0, i + localView.getMeasuredWidth(), getHeight());
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    float f = getContext().getResources().getFraction(2131493154, 1, 1);
    this.mTitleHeight = Math.max(this.mTitleHeight, (int)(f * j));
    if (getChildCount() > 0) {
      measureChild(getChildAt(0), View.MeasureSpec.makeMeasureSpec((int)(i * this.mProportion), 1073741824), paramInt2);
    }
    View localView = findViewById(2131755173);
    if (localView != null) {
      localView.setMinimumHeight(this.mTitleHeight);
    }
    setMeasuredDimension(i, j);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ProportionalOuterFrame
 * JD-Core Version:    0.7.0.1
 */