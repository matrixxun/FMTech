package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.android.vending.R.styleable;
import com.google.android.play.utils.PlayUtils;

public class DetailsTitleFlowLayout
  extends ViewGroup
{
  private final int mBulletSeparatorGap;
  private final Paint mBulletSeparatorPaint;
  private final int mBulletSeparatorSize;
  private final int mGravity;
  private int[] mLineWidths;
  
  public DetailsTitleFlowLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsTitleFlowLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.DetailsTitleFlowLayout);
    this.mBulletSeparatorGap = localTypedArray.getDimensionPixelSize(1, 0);
    this.mBulletSeparatorSize = localTypedArray.getDimensionPixelSize(2, 0);
    this.mGravity = localTypedArray.getInt(0, 8388611);
    localTypedArray.recycle();
    this.mBulletSeparatorPaint = new Paint();
    this.mBulletSeparatorPaint.setAntiAlias(true);
    this.mBulletSeparatorPaint.setColor(localResources.getColor(2131689625));
    this.mBulletSeparatorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    setWillNotDraw(false);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mBulletSeparatorSize <= 0) {
      return;
    }
    int i;
    label22:
    int k;
    int n;
    label47:
    View localView;
    if (ViewCompat.getLayoutDirection(this) == 0)
    {
      i = 1;
      int j = getPaddingLeft();
      k = getWidth() - getPaddingRight();
      int m = getChildCount();
      n = 0;
      if (n < m)
      {
        localView = getChildAt(n);
        if (localView.getVisibility() != 8)
        {
          if (i == 0) {
            break label156;
          }
          int i4 = localView.getLeft();
          if (i4 > j)
          {
            int i5 = (localView.getTop() + localView.getBottom()) / 2;
            int i6 = i4 - this.mBulletSeparatorGap / 2;
            paramCanvas.save();
            paramCanvas.drawCircle(i6, i5, this.mBulletSeparatorSize / 2, this.mBulletSeparatorPaint);
            paramCanvas.restore();
          }
        }
      }
    }
    for (;;)
    {
      n++;
      break label47;
      break;
      i = 0;
      break label22;
      label156:
      int i1 = localView.getRight();
      if (i1 < k)
      {
        int i2 = (localView.getTop() + localView.getBottom()) / 2;
        int i3 = i1 + this.mBulletSeparatorGap / 2;
        paramCanvas.save();
        paramCanvas.drawCircle(i3, i2, this.mBulletSeparatorSize / 2, this.mBulletSeparatorPaint);
        paramCanvas.restore();
      }
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = ViewCompat.getPaddingStart(this);
    int k = ViewCompat.getPaddingEnd(this);
    int m = i - j - k;
    int n = getPaddingTop();
    int i1 = 0;
    int i2 = getChildCount();
    boolean bool;
    int i3;
    int i4;
    int i5;
    label62:
    View localView;
    int i6;
    int i7;
    int i8;
    if (ViewCompat.getLayoutDirection(this) == 0)
    {
      bool = true;
      i3 = 0;
      i4 = 0;
      i5 = 0;
      if (i5 >= i2) {
        return;
      }
      localView = getChildAt(i5);
      i6 = localView.getMeasuredWidth();
      i7 = localView.getMeasuredHeight();
      if (i1 + i6 > m)
      {
        n += i3;
        i3 = 0;
        i1 = 0;
        i4++;
      }
      switch (GravityCompat.getAbsoluteGravity(this.mGravity, ViewCompat.getLayoutDirection(this)))
      {
      default: 
        i8 = i1 + j;
      }
    }
    for (;;)
    {
      int i9 = PlayUtils.getViewLeftFromParentStart(i, i6, bool, i8);
      int i10 = i9 + i6;
      int i11 = n + i7;
      localView.layout(i9, n, i10, i11);
      i3 = Math.max(i3, i7);
      i1 += i6 + this.mBulletSeparatorGap;
      i5++;
      break label62;
      bool = false;
      break;
      i8 = i1 + i - this.mLineWidths[i4] - k;
      continue;
      i8 = i1 + (i - this.mLineWidths[i4]) / 2;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = ViewCompat.getPaddingStart(this);
    int j = ViewCompat.getPaddingEnd(this);
    int k = View.MeasureSpec.getMode(paramInt1);
    int m = Math.max(View.MeasureSpec.getSize(paramInt1) - i - j, 0);
    if (k == 1073741824) {}
    int i1;
    int i6;
    int i7;
    for (int n = 1;; n = 0)
    {
      i1 = getPaddingTop() + getPaddingBottom();
      int i2 = getChildCount();
      int i3 = View.MeasureSpec.makeMeasureSpec(m, -2147483648);
      int i4 = 0;
      int i5 = 0;
      i6 = 0;
      i7 = 0;
      int i8 = 0;
      this.mLineWidths = new int[i2];
      for (int i9 = 0; i9 < i2; i9++)
      {
        View localView = getChildAt(i9);
        localView.measure(i3, 0);
        int i12 = localView.getMeasuredWidth();
        int i13 = localView.getMeasuredHeight();
        if (i4 + i12 > m)
        {
          i1 += i6;
          i6 = 0;
          i5 = 0;
          i4 = 0;
          if (i9 > 0) {
            i8++;
          }
        }
        i4 += i12 + this.mBulletSeparatorGap;
        i5 += i12 + this.mBulletSeparatorGap;
        i6 = Math.max(i6, i13);
        i7 = Math.max(i5, i7);
        this.mLineWidths[i8] = i5;
      }
    }
    int i10 = i1 + i6;
    if (n != 0) {}
    for (int i11 = m;; i11 = i7 - this.mBulletSeparatorGap)
    {
      setMeasuredDimension(j + (i11 + i), i10);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsTitleFlowLayout
 * JD-Core Version:    0.7.0.1
 */