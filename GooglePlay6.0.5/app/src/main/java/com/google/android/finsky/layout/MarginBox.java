package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public class MarginBox
  extends FrameLayout
{
  protected int mEdgePadding;
  protected int mMaxContentWidth;
  
  public MarginBox(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MarginBox(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mMaxContentWidth = localResources.getDimensionPixelSize(2131492882);
    this.mEdgePadding = (localResources.getDimensionPixelSize(2131492923) - localResources.getDimensionPixelSize(2131493087));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getHeight();
    for (int k = 0; k < getChildCount(); k++)
    {
      View localView = getChildAt(k);
      int m = localView.getMeasuredWidth();
      int n = localView.getMeasuredHeight();
      int i1 = (i - m) / 2;
      int i2 = (j - n) / 2;
      localView.layout(i1, i2, i1 + m, i2 + n);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = View.MeasureSpec.makeMeasureSpec(Math.min(this.mMaxContentWidth, i - 2 * this.mEdgePadding), 1073741824);
    int m = 0;
    if (m < getChildCount())
    {
      View localView = getChildAt(m);
      if (localView.getLayoutParams().height == -1) {
        localView.measure(k, View.MeasureSpec.makeMeasureSpec(j, 1073741824));
      }
      for (;;)
      {
        m++;
        break;
        localView.measure(k, View.MeasureSpec.makeMeasureSpec(j, -2147483648));
      }
    }
    setMeasuredDimension(i, j);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MarginBox
 * JD-Core Version:    0.7.0.1
 */