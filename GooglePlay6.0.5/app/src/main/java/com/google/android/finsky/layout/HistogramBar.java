package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

public class HistogramBar
  extends View
{
  private int mHeight;
  private double mMaxWidth;
  private double mWidthPercentage;
  
  public HistogramBar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public HistogramBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public int getBaseline()
  {
    return getMeasuredHeight();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = (int)(this.mWidthPercentage * this.mMaxWidth);
    if (i < 0) {
      return;
    }
    setMeasuredDimension(i, this.mHeight);
  }
  
  public void setBarHeight(int paramInt)
  {
    this.mHeight = paramInt;
  }
  
  public void setColor(int paramInt)
  {
    super.setBackgroundColor(getContext().getResources().getColor(paramInt));
  }
  
  public void setMaxBarWidth(double paramDouble)
  {
    this.mMaxWidth = paramDouble;
  }
  
  public void setWidthPercentage(double paramDouble)
  {
    this.mWidthPercentage = paramDouble;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.HistogramBar
 * JD-Core Version:    0.7.0.1
 */