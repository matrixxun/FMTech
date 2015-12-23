package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.android.vending.R.styleable;
import com.google.android.play.utils.PlayUtils;

public class SingleLineContainer
  extends LinearLayout
{
  private View mFlexibleChild;
  private final int mFlexibleChildId;
  
  public SingleLineContainer(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SingleLineContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SingleLineContainer);
    this.mFlexibleChildId = localTypedArray.getResourceId(0, 0);
    localTypedArray.recycle();
  }
  
  private static boolean isCenteredVertically(View paramView)
  {
    if (paramView.getBaseline() == -1) {
      return true;
    }
    return (0x70 & ((LinearLayout.LayoutParams)paramView.getLayoutParams()).gravity) == 16;
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    if (this.mFlexibleChildId != 0) {
      this.mFlexibleChild = findViewById(this.mFlexibleChildId);
    }
  }
  
  protected final void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    int i;
    int j;
    int k;
    int m;
    int n;
    int i1;
    for (boolean bool = true;; bool = false)
    {
      i = getPaddingTop();
      j = getPaddingBottom();
      k = getHeight();
      m = getWidth();
      n = getChildCount();
      i1 = 0;
      for (int i2 = 0; i2 < n; i2++)
      {
        View localView2 = getChildAt(i2);
        if (localView2.getVisibility() != 8) {
          i1 = Math.max(i1, localView2.getBaseline());
        }
      }
    }
    int i3 = ViewCompat.getPaddingStart(this);
    int i4 = 0;
    if (i4 < n)
    {
      View localView1 = getChildAt(i4);
      int i5;
      int i6;
      ViewGroup.MarginLayoutParams localMarginLayoutParams;
      int i7;
      if (localView1.getVisibility() != 8)
      {
        i5 = localView1.getMeasuredWidth();
        i6 = localView1.getMeasuredHeight();
        localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localView1.getLayoutParams();
        i7 = i3 + MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams);
        if (!isCenteredVertically(localView1)) {
          break label243;
        }
      }
      label243:
      for (int i8 = i + (k - i - j - i6) / 2;; i8 = i + i1 - localView1.getBaseline())
      {
        int i9 = PlayUtils.getViewLeftFromParentStart(m, i5, bool, i7);
        localView1.layout(i9, i8, i9 + i5, i8 + localView1.getMeasuredHeight());
        i3 = i7 + (i5 + MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams));
        i4++;
        break;
      }
    }
  }
  
  protected final void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = i - ViewCompat.getPaddingStart(this) - ViewCompat.getPaddingEnd(this);
    int k = getChildCount();
    int m = 0;
    for (int n = 0; n < k; n++)
    {
      View localView2 = getChildAt(n);
      if (localView2.getVisibility() != 8)
      {
        localView2.measure(0, 0);
        if (!isCenteredVertically(localView2)) {
          m = Math.max(m, localView2.getBaseline());
        }
      }
    }
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    if (i4 < k)
    {
      View localView1 = getChildAt(i4);
      int i7;
      if (localView1.getVisibility() != 8)
      {
        i7 = localView1.getMeasuredHeight();
        if (!isCenteredVertically(localView1)) {
          break label187;
        }
        i3 = Math.max(i3, i7);
      }
      for (;;)
      {
        LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)localView1.getLayoutParams();
        i2 += MarginLayoutParamsCompat.getMarginStart(localLayoutParams) + localView1.getMeasuredWidth() + MarginLayoutParamsCompat.getMarginEnd(localLayoutParams);
        i4++;
        break;
        label187:
        i1 = Math.max(i1, i7 - localView1.getBaseline());
      }
    }
    int i5 = Math.max(m + i1, i3) + (getPaddingTop() + getPaddingBottom());
    if ((i2 > j) && (this.mFlexibleChild != null) && (this.mFlexibleChild.getVisibility() != 8))
    {
      int i6 = this.mFlexibleChild.getMeasuredWidth() - (i2 - j);
      this.mFlexibleChild.measure(View.MeasureSpec.makeMeasureSpec(i6, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mFlexibleChild.getMeasuredHeight(), 1073741824));
    }
    setMeasuredDimension(i, i5);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.SingleLineContainer
 * JD-Core Version:    0.7.0.1
 */