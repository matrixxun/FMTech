package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.layout.CardLinearLayout;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.utils.PlayUtils;

public class PlayCardPromoWideContentLayout
  extends CardLinearLayout
{
  private PlayCardViewBase mCardView;
  private int mColumnCount;
  private int mDefaultCardInset;
  private FrameLayout mPromoContentView;
  private View mSeparator;
  
  public PlayCardPromoWideContentLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardPromoWideContentLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mDefaultCardInset = localResources.getDimensionPixelSize(2131493068);
    this.mColumnCount = UiUtils.getFeaturedGridColumnCount(localResources, 1.0D);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mPromoContentView = ((FrameLayout)findViewById(2131755876));
    this.mCardView = ((PlayCardViewBase)findViewById(2131755454));
    this.mSeparator = findViewById(2131755325);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int i = getWidth();
      int j = ViewCompat.getPaddingStart(this);
      int k = ViewCompat.getPaddingEnd(this);
      int m = this.mPromoContentView.getMeasuredWidth();
      int n = getPaddingTop() + this.mDefaultCardInset;
      int i1 = j + this.mDefaultCardInset;
      int i2 = PlayUtils.getViewLeftFromParentStart(i, m, bool, i1);
      int i3 = n + this.mPromoContentView.getMeasuredHeight();
      int i4 = this.mCardView.getMeasuredWidth();
      int i5 = n - this.mDefaultCardInset;
      int i6 = PlayUtils.getViewLeftFromParentEnd(i, i4, bool, k);
      this.mPromoContentView.layout(i2, n, i2 + m, i3);
      if (this.mSeparator.getVisibility() == 0)
      {
        int i7 = i1 + m;
        int i8 = this.mSeparator.getMeasuredWidth();
        int i9 = PlayUtils.getViewLeftFromParentStart(i, i8, bool, i7);
        this.mSeparator.layout(i9, n, i9 + i8, i3);
      }
      this.mCardView.layout(i6, i5, i6 + i4, i5 + this.mCardView.getMeasuredHeight());
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1) - getPaddingLeft() - getPaddingRight();
    int j = i / this.mColumnCount;
    int k = View.MeasureSpec.makeMeasureSpec(j, 1073741824);
    this.mCardView.measure(k, 0);
    int m = this.mCardView.getMeasuredHeight() - 2 * this.mDefaultCardInset;
    int n = i - j;
    if (this.mSeparator.getVisibility() != 8)
    {
      int i3 = View.MeasureSpec.makeMeasureSpec(this.mSeparator.getLayoutParams().width, 1073741824);
      int i4 = View.MeasureSpec.makeMeasureSpec(m, 1073741824);
      this.mSeparator.measure(i3, i4);
      n -= this.mSeparator.getMeasuredWidth();
    }
    int i1 = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
    int i2 = View.MeasureSpec.makeMeasureSpec(m, 1073741824);
    this.mPromoContentView.measure(i1, i2);
    setMeasuredDimension(i, this.mCardView.getMeasuredHeight());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardPromoWideContentLayout
 * JD-Core Version:    0.7.0.1
 */