package com.google.android.play.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.google.android.play.R.style;
import com.google.android.play.R.styleable;

public class PlayEduCardButtonPanel
  extends ViewGroup
{
  private static final int COMPAT_MEASURED_STATE_TOO_SMALL;
  private int mSpacing;
  private boolean mUseVerticalLayout;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11) {}
    for (int i = 16777216;; i = 0)
    {
      COMPAT_MEASURED_STATE_TOO_SMALL = i;
      return;
    }
  }
  
  public PlayEduCardButtonPanel(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public PlayEduCardButtonPanel(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlayEduCardButtonPanel(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    loadAttributes(paramContext, paramAttributeSet, paramInt, R.style.PlayEduCardButtonPanel);
  }
  
  @TargetApi(21)
  public PlayEduCardButtonPanel(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    loadAttributes(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  private void loadAttributes(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayEduCardButtonPanel, paramInt1, paramInt2);
    this.mSpacing = localTypedArray.getDimensionPixelOffset(R.styleable.PlayEduCardButtonPanel_playSpacing, 0);
    localTypedArray.recycle();
  }
  
  private static int makeChildMeasureSpec(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 == 0) {
      return View.MeasureSpec.makeMeasureSpec(0, 0);
    }
    return View.MeasureSpec.makeMeasureSpec(Math.max(0, paramInt2 - paramInt3), -2147483648);
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return generateDefaultLayoutParams();
  }
  
  public int getSpacing()
  {
    return this.mSpacing;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    if (i == 0) {
      return;
    }
    int j = getPaddingLeft();
    int k = paramInt3 - paramInt1 - getPaddingRight();
    int m = getPaddingTop();
    int n;
    label46:
    int i1;
    label49:
    int i2;
    label67:
    View localView;
    int i3;
    int i4;
    if (ViewCompat.getLayoutDirection(this) == 1)
    {
      n = 1;
      i1 = 0;
      if (i1 < i)
      {
        if (!this.mUseVerticalLayout) {
          break label144;
        }
        i2 = i1;
        localView = getChildAt(i2);
        i3 = localView.getMeasuredWidth();
        i4 = localView.getMeasuredHeight();
        if (n == 0) {
          break label156;
        }
        localView.layout(j, m, j + i3, m + i4);
        label113:
        if (!this.mUseVerticalLayout) {
          break label178;
        }
        m += i4 + this.mSpacing;
      }
    }
    for (;;)
    {
      i1++;
      break label49;
      break;
      n = 0;
      break label46;
      label144:
      i2 = i - 1 - i1;
      break label67;
      label156:
      localView.layout(k - i3, m, k, m + i4);
      break label113;
      label178:
      if (n != 0) {
        j += i3 + this.mSpacing;
      } else {
        k -= i3 + this.mSpacing;
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = getChildCount();
    if (i == 0)
    {
      super.onMeasure(paramInt1, paramInt2);
      return;
    }
    int j = View.MeasureSpec.getSize(paramInt1);
    int k = View.MeasureSpec.getMode(paramInt1);
    int m = View.MeasureSpec.getSize(paramInt2);
    int n = View.MeasureSpec.getMode(paramInt2);
    int i1 = getPaddingLeft() + getPaddingRight();
    int i2 = getPaddingTop() + getPaddingBottom();
    int i3 = this.mSpacing * (i - 1);
    int i4 = 0;
    int i5 = i3;
    int i6 = 0;
    int i7 = makeChildMeasureSpec(k, j, i1);
    int i8 = makeChildMeasureSpec(n, m, i2);
    int i9 = 0;
    int i10 = 0;
    int i11 = 0;
    if (i11 < i)
    {
      View localView = getChildAt(i11);
      localView.measure(i7, i8);
      int i17 = ViewCompat.getMeasuredWidthAndState(localView);
      int i18 = i17 & 0xFFFFFF;
      i3 += i18;
      i4 = Math.max(i4, i18);
      if ((i9 != 0) || ((0x1000000 & i17) != 0))
      {
        i9 = 1;
        label182:
        int i19 = ViewCompat.getMeasuredHeightAndState(localView);
        int i20 = i19 & 0xFFFFFF;
        i5 += i20;
        i6 = Math.max(i6, i20);
        if ((i10 == 0) && ((0x1000000 & i19) == 0)) {
          break label240;
        }
      }
      label240:
      for (i10 = 1;; i10 = 0)
      {
        i11++;
        break;
        i9 = 0;
        break label182;
      }
    }
    int i12 = i3 + i1;
    int i13 = i4 + i1;
    int i14;
    label294:
    int i15;
    int i16;
    if (k == 0)
    {
      i14 = i12;
      this.mUseVerticalLayout = false;
      if (i9 != 0) {
        i14 |= COMPAT_MEASURED_STATE_TOO_SMALL;
      }
      if (!this.mUseVerticalLayout) {
        break label414;
      }
      i15 = i5 + i2;
      if (n != 0) {
        break label421;
      }
      i16 = i15;
    }
    for (;;)
    {
      if (i10 != 0) {
        i16 |= COMPAT_MEASURED_STATE_TOO_SMALL;
      }
      setMeasuredDimension(i14, i16);
      return;
      if (i12 <= j)
      {
        if (k == 1073741824) {}
        for (i14 = j;; i14 = i12)
        {
          this.mUseVerticalLayout = false;
          break;
        }
      }
      if (i13 <= j)
      {
        if (k == 1073741824) {}
        for (i14 = j;; i14 = i13)
        {
          this.mUseVerticalLayout = true;
          break;
        }
      }
      i14 = j | COMPAT_MEASURED_STATE_TOO_SMALL;
      this.mUseVerticalLayout = true;
      break;
      label414:
      i5 = i6;
      break label294;
      label421:
      if (i15 <= m)
      {
        if (n == 1073741824) {}
        for (i16 = m;; i16 = i15) {
          break;
        }
      }
      i16 = m | COMPAT_MEASURED_STATE_TOO_SMALL;
    }
  }
  
  public void setSpacing(int paramInt)
  {
    if (this.mSpacing != paramInt)
    {
      this.mSpacing = paramInt;
      requestLayout();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayEduCardButtonPanel
 * JD-Core Version:    0.7.0.1
 */