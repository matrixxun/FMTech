package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import com.android.vending.R.styleable;
import com.google.android.finsky.layout.play.Identifiable;
import com.google.android.play.utils.PlayUtils;

public class BucketRow
  extends IdentifiableLinearLayout
  implements Identifiable
{
  private boolean mSameChildHeight;
  
  public BucketRow(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BucketRow(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setOrientation(0);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.BucketRow);
    this.mSameChildHeight = localTypedArray.getBoolean(0, false);
    localTypedArray.recycle();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = ViewCompat.getPaddingStart(this);
    int j = getWidth();
    int k = getHeight() - getPaddingBottom();
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int m = getChildCount();
      int n = 0;
      for (int i1 = 0; i1 < m; i1++)
      {
        View localView = getChildAt(i1);
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localView.getLayoutParams();
        int i2 = localView.getMeasuredWidth();
        int i3 = PlayUtils.getViewLeftFromParentStart(j, i2, bool, i + n);
        int i4 = i3 + i2;
        localView.layout(i3, k - localView.getMeasuredHeight() - localMarginLayoutParams.bottomMargin, i4, k - localMarginLayoutParams.bottomMargin);
        n += i2;
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getChildCount();
    int k = 0;
    if (j > 0)
    {
      int m = View.MeasureSpec.makeMeasureSpec((i - getPaddingLeft() - getPaddingRight()) / j, 1073741824);
      for (int n = 0; n < j; n++)
      {
        View localView2 = getChildAt(n);
        localView2.measure(m, getChildMeasureSpec(0, 0, localView2.getLayoutParams().height));
        k = Math.max(k, localView2.getMeasuredHeight());
      }
      if (this.mSameChildHeight)
      {
        int i1 = View.MeasureSpec.makeMeasureSpec(k, 1073741824);
        for (int i2 = 0; i2 < j; i2++)
        {
          View localView1 = getChildAt(i2);
          if (localView1.getMeasuredHeight() != k) {
            localView1.measure(View.MeasureSpec.makeMeasureSpec(localView1.getMeasuredWidth(), 1073741824), i1);
          }
        }
      }
    }
    setMeasuredDimension(i, k + (getPaddingTop() + getPaddingBottom()));
  }
  
  public void setContentHorizontalPadding(int paramInt)
  {
    ViewCompat.setPaddingRelative(this, paramInt, getPaddingTop(), paramInt, getPaddingBottom());
  }
  
  public void setSameChildHeight(boolean paramBoolean)
  {
    this.mSameChildHeight = paramBoolean;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.BucketRow
 * JD-Core Version:    0.7.0.1
 */