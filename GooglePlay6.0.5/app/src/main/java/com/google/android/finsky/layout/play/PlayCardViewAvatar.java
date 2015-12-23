package com.google.android.finsky.layout.play;

import android.content.Context;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import com.google.android.play.cardview.CardViewGroupDelegate;
import com.google.android.play.cardview.CardViewGroupDelegates;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.utils.PlayUtils;

public class PlayCardViewAvatar
  extends PlayCardViewBase
{
  public PlayCardViewAvatar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewAvatar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public int getCardType()
  {
    return 16;
  }
  
  public CardViewGroupDelegate getCardViewGroupDelegate()
  {
    return CardViewGroupDelegates.NO_CARD_BG_IMPL;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int i = ViewCompat.getPaddingStart(this);
      int j = ViewCompat.getPaddingEnd(this);
      int k = getPaddingTop();
      int m = getPaddingBottom();
      int n = getWidth();
      int i1 = getHeight();
      ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams();
      int i2 = this.mThumbnail.getMeasuredWidth();
      int i3 = this.mThumbnail.getMeasuredHeight();
      int i4 = PlayUtils.getViewLeftFromParentStart(n, i2, bool, i + MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams1));
      this.mThumbnail.layout(i4, k + localMarginLayoutParams1.topMargin, i4 + i2, i3 + (k + localMarginLayoutParams1.topMargin));
      ViewGroup.MarginLayoutParams localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mTitle.getLayoutParams();
      int i5 = k + i3 + localMarginLayoutParams2.topMargin;
      int i6 = i + MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams2);
      int i7 = this.mTitle.getMeasuredWidth();
      int i8 = this.mTitle.getMeasuredHeight();
      int i9 = PlayUtils.getViewLeftFromParentStart(n, i7, bool, i6);
      this.mTitle.layout(i9, i5, i9 + i7, i5 + i8);
      int i10 = this.mLoadingIndicator.getMeasuredWidth();
      int i11 = this.mLoadingIndicator.getMeasuredHeight();
      int i12 = i + (n - i - j - i10) / 2;
      int i13 = k + (i1 - k - m - i11) / 2;
      int i14 = PlayUtils.getViewLeftFromParentStart(n, i10, bool, i12);
      this.mLoadingIndicator.layout(i14, i13, i14 + i10, i13 + this.mLoadingIndicator.getMeasuredHeight());
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getPaddingLeft();
    int k = getPaddingRight();
    int m = getPaddingTop();
    int n = getPaddingBottom();
    int i1 = i - j - k;
    ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams();
    int i2 = View.MeasureSpec.makeMeasureSpec(i1 - localMarginLayoutParams1.leftMargin - localMarginLayoutParams1.rightMargin, 1073741824);
    this.mThumbnail.measure(i2, i2);
    ViewGroup.MarginLayoutParams localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mTitle.getLayoutParams();
    int i3 = i1 - localMarginLayoutParams2.leftMargin - localMarginLayoutParams2.rightMargin;
    this.mTitle.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), 0);
    int i4 = localMarginLayoutParams1.topMargin + this.mThumbnail.getMeasuredHeight() + localMarginLayoutParams1.bottomMargin + localMarginLayoutParams2.topMargin + this.mTitle.getMeasuredHeight() + localMarginLayoutParams2.bottomMargin;
    this.mLoadingIndicator.measure(0, 0);
    setMeasuredDimension(i, n + (m + i4));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardViewAvatar
 * JD-Core Version:    0.7.0.1
 */