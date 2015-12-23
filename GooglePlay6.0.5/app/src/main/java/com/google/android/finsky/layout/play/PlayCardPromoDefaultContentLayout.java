package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import com.google.android.play.layout.CardLinearLayout;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.utils.PlayUtils;

public class PlayCardPromoDefaultContentLayout
  extends CardLinearLayout
{
  private PlayCardViewBase mCardView;
  private int mDefaultCardInset;
  private FrameLayout mPromoContentView;
  private View mSeparator;
  
  public PlayCardPromoDefaultContentLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardPromoDefaultContentLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mDefaultCardInset = paramContext.getResources().getDimensionPixelSize(2131493068);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mPromoContentView = ((FrameLayout)findViewById(2131755876));
    this.mSeparator = findViewById(2131755325);
    this.mCardView = ((PlayCardViewBase)findViewById(2131755454));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int i = getWidth();
      int j = ViewCompat.getPaddingStart(this);
      int k = MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams)this.mPromoContentView.getLayoutParams()) + getPaddingLeft();
      int m = this.mPromoContentView.getMeasuredWidth();
      int n = PlayUtils.getViewLeftFromParentStart(i, m, bool, k);
      int i1 = getPaddingTop();
      int i2 = i1 + this.mPromoContentView.getMeasuredHeight();
      int i3 = i2 - this.mDefaultCardInset;
      int i4 = this.mCardView.getMeasuredWidth();
      int i5 = PlayUtils.getViewLeftFromParentStart(i, i4, bool, j);
      this.mPromoContentView.layout(n, i1, n + m, i2);
      if (this.mSeparator.getVisibility() == 0)
      {
        this.mSeparator.layout(0, i2, getMeasuredWidth(), i2 + this.mSeparator.getMeasuredHeight());
        i3 += this.mSeparator.getMeasuredHeight();
      }
      this.mCardView.layout(i5, i3, i5 + i4, i3 + this.mCardView.getMeasuredHeight());
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = i - getPaddingLeft() - getPaddingRight();
    int k = getPaddingTop() + getPaddingBottom();
    this.mPromoContentView.getLayoutParams().height = ((int)(0.5625F * j));
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mPromoContentView.getLayoutParams();
    int m = j - localMarginLayoutParams.leftMargin - localMarginLayoutParams.rightMargin;
    this.mPromoContentView.measure(View.MeasureSpec.makeMeasureSpec(m, 1073741824), View.MeasureSpec.makeMeasureSpec(localMarginLayoutParams.height, 1073741824));
    int n = this.mSeparator.getVisibility();
    int i1 = 0;
    if (n != 8)
    {
      int i3 = View.MeasureSpec.makeMeasureSpec(this.mSeparator.getLayoutParams().height, 1073741824);
      this.mSeparator.measure(paramInt1, i3);
      i1 = this.mSeparator.getMeasuredHeight();
    }
    int i2 = this.mCardView.getLayoutParams().height;
    this.mCardView.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
    setMeasuredDimension(i, i1 + k + this.mPromoContentView.getMeasuredHeight() + this.mCardView.getMeasuredHeight() - this.mDefaultCardInset);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardPromoDefaultContentLayout
 * JD-Core Version:    0.7.0.1
 */