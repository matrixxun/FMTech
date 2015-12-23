package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.google.android.play.utils.PlayUtils;

public class DetailsTextIconContainer
  extends ViewGroup
{
  private final int mChildGap;
  
  public DetailsTextIconContainer(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsTextIconContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mChildGap = paramContext.getResources().getDimensionPixelSize(2131493287);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getHeight();
    int k = getChildCount();
    int m = ViewCompat.getPaddingStart(this);
    int n = ViewCompat.getPaddingEnd(this);
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    int i1;
    int i2;
    for (boolean bool = true;; bool = false)
    {
      i1 = 0;
      i2 = 0;
      for (int i3 = 0; i3 < k; i3++)
      {
        View localView2 = getChildAt(i3);
        if (localView2.getVisibility() == 0)
        {
          i1++;
          i2 += localView2.getMeasuredWidth();
        }
      }
    }
    if (i1 > 1) {
      i2 += this.mChildGap * (i1 - 1);
    }
    int i4 = m + (i - i2 - m - n) / 2;
    int i5 = getPaddingTop();
    int i6 = getPaddingBottom();
    int i7 = j - i5 - i6;
    for (int i8 = 0; i8 < k; i8++)
    {
      View localView1 = getChildAt(i8);
      if (localView1.getVisibility() == 0)
      {
        int i9 = localView1.getMeasuredWidth();
        int i10 = localView1.getMeasuredHeight();
        int i11 = i5 + (i7 - i10) / 2;
        int i12 = PlayUtils.getViewLeftFromParentStart(i, i9, bool, i4);
        localView1.layout(i12, i11, i12 + i9, i11 + i10);
        i4 += i9 + this.mChildGap;
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = ViewCompat.getPaddingStart(this);
    int k = ViewCompat.getPaddingEnd(this);
    int m = (int)(0.75F * View.MeasureSpec.getSize(paramInt1)) - j - k;
    int n = 0;
    int i1 = m;
    int i2 = getChildCount();
    int i3 = 0;
    if (i3 < i2)
    {
      View localView = getChildAt(i3);
      ViewGroup.LayoutParams localLayoutParams = localView.getLayoutParams();
      if (localLayoutParams.width <= i1)
      {
        localView.setVisibility(0);
        localView.measure(View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, 1073741824), View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824));
        i1 -= localLayoutParams.width + this.mChildGap;
        i += localLayoutParams.width + this.mChildGap;
        n = Math.max(n, localLayoutParams.height);
      }
      for (;;)
      {
        i3++;
        break;
        localView.setVisibility(8);
      }
    }
    setMeasuredDimension(k + (i + j), n + getPaddingTop() + getPaddingBottom());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsTextIconContainer
 * JD-Core Version:    0.7.0.1
 */