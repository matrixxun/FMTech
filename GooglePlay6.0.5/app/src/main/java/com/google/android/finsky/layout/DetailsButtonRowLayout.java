package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import com.google.android.play.utils.PlayUtils;

public class DetailsButtonRowLayout
  extends LinearLayout
{
  private final int mChildGap;
  private final boolean mIsWideLayout;
  
  public DetailsButtonRowLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsButtonRowLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mChildGap = (2 * localResources.getDimensionPixelSize(2131492993) / 3);
    this.mIsWideLayout = localResources.getBoolean(2131427334);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int i = getWidth();
      int j = 0;
      int k = getChildCount();
      int m = getPaddingTop();
      for (int n = k - 1; n >= 0; n--)
      {
        View localView = getChildAt(n);
        if (localView.getVisibility() == 0)
        {
          int i1 = localView.getMeasuredWidth();
          int i2 = PlayUtils.getViewLeftFromParentEnd(i, i1, bool, j);
          localView.layout(i2, m, i2 + i1, m + localView.getMeasuredHeight());
          j += i1 + this.mChildGap;
        }
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = getChildCount();
    int j = 0;
    int k = 0;
    int m = 0;
    int n = getPaddingTop() + getPaddingBottom();
    for (int i1 = 0; i1 < i; i1++)
    {
      View localView2 = getChildAt(i1);
      if (localView2.getVisibility() == 0)
      {
        j++;
        localView2.measure(0, 0);
        k += localView2.getMeasuredWidth();
        m = Math.max(m, localView2.getMeasuredHeight());
      }
    }
    if (j > 0) {
      k += this.mChildGap * (j - 1);
    }
    int i2 = View.MeasureSpec.getMode(paramInt1);
    int i3 = View.MeasureSpec.getSize(paramInt1);
    if ((i2 == -2147483648) && (k <= i3))
    {
      setMeasuredDimension(k, m + n);
      return;
    }
    int i4 = i3 - k;
    if ((i4 > 0) && ((j == 1) || (this.mIsWideLayout))) {
      i4 = 0;
    }
    if (i4 != 0)
    {
      m = 0;
      for (int i5 = 0; i5 < i; i5++)
      {
        View localView1 = getChildAt(i5);
        if (localView1.getVisibility() == 0)
        {
          int i6 = i4 / j;
          localView1.measure(View.MeasureSpec.makeMeasureSpec(i6 + localView1.getMeasuredWidth(), 1073741824), 0);
          m = Math.max(m, localView1.getMeasuredHeight());
          i4 -= i6;
          j--;
        }
      }
    }
    setMeasuredDimension(i3, m + n);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsButtonRowLayout
 * JD-Core Version:    0.7.0.1
 */