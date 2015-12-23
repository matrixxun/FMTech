package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.android.vending.R.styleable;

public class MaxSizeLinearLayout
  extends MaxWidthLinearLayout
{
  private int mMaxHeight;
  
  public MaxSizeLinearLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MaxSizeLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MaxSizeLayoutHeight);
    this.mMaxHeight = localTypedArray.getDimensionPixelSize(0, 0);
    localTypedArray.recycle();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt2);
    if ((this.mMaxHeight > 0) && (this.mMaxHeight < i))
    {
      int j = View.MeasureSpec.getMode(paramInt2);
      paramInt2 = View.MeasureSpec.makeMeasureSpec(this.mMaxHeight, j);
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void setMaxHeight(int paramInt)
  {
    if (paramInt != this.mMaxHeight)
    {
      this.mMaxHeight = paramInt;
      requestLayout();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MaxSizeLinearLayout
 * JD-Core Version:    0.7.0.1
 */