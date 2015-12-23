package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import com.android.vending.R.styleable;

public class MaxWidthLinearLayout
  extends LinearLayout
{
  private int mMaxWidth;
  
  public MaxWidthLinearLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MaxWidthLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MaxWidthView);
    this.mMaxWidth = localTypedArray.getDimensionPixelSize(0, 0);
    localTypedArray.recycle();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    if ((this.mMaxWidth > 0) && (this.mMaxWidth < i))
    {
      int j = View.MeasureSpec.getMode(paramInt1);
      paramInt1 = View.MeasureSpec.makeMeasureSpec(this.mMaxWidth, j);
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void setMaxWidth(int paramInt)
  {
    if (paramInt != this.mMaxWidth)
    {
      this.mMaxWidth = paramInt;
      requestLayout();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MaxWidthLinearLayout
 * JD-Core Version:    0.7.0.1
 */