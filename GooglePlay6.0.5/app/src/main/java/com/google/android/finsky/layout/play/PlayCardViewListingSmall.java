package com.google.android.finsky.layout.play;

import android.content.Context;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.play.layout.PlayCardLabelView;
import com.google.android.play.layout.PlayCardSnippet;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.layout.StarRatingBar;
import com.google.android.play.utils.PlayUtils;

public class PlayCardViewListingSmall
  extends PlayCardViewBase
{
  private TextView mAppSize;
  private View mRatingBadge;
  
  public PlayCardViewListingSmall(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewListingSmall(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void bindLoading()
  {
    super.bindLoading();
    this.mRatingBadge.setVisibility(8);
    this.mAppSize.setVisibility(8);
  }
  
  public TextView getAppSize()
  {
    return this.mAppSize;
  }
  
  public int getCardType()
  {
    return 4;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mRatingBadge = findViewById(2131755884);
    this.mAppSize = ((TextView)findViewById(2131755870));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool;
    int i;
    int j;
    int k;
    int m;
    int n;
    int i1;
    ViewGroup.MarginLayoutParams localMarginLayoutParams6;
    ViewGroup.MarginLayoutParams localMarginLayoutParams8;
    int i5;
    int i57;
    label549:
    int i18;
    int i20;
    int i21;
    int i41;
    if (ViewCompat.getLayoutDirection(this) == 0)
    {
      bool = true;
      i = ViewCompat.getPaddingStart(this);
      j = ViewCompat.getPaddingEnd(this);
      k = getPaddingTop();
      m = getPaddingBottom();
      n = getWidth();
      i1 = getHeight();
      ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mTitle.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams3 = (ViewGroup.MarginLayoutParams)this.mSubtitle.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams4 = (ViewGroup.MarginLayoutParams)this.mRatingBadge.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams5 = (ViewGroup.MarginLayoutParams)this.mLabel.getLayoutParams();
      localMarginLayoutParams6 = (ViewGroup.MarginLayoutParams)this.mAppSize.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams7 = (ViewGroup.MarginLayoutParams)this.mOverflow.getLayoutParams();
      localMarginLayoutParams8 = (ViewGroup.MarginLayoutParams)this.mSnippet2.getLayoutParams();
      int i2 = this.mThumbnail.getMeasuredWidth();
      int i3 = this.mThumbnail.getMeasuredHeight();
      int i4 = PlayUtils.getViewLeftFromParentStart(n, i2, bool, i);
      this.mThumbnail.layout(i4, k, i4 + i2, k + i3);
      i5 = i + localMarginLayoutParams1.width + MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams1);
      int i6 = this.mTitle.getMeasuredWidth();
      int i7 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams2);
      int i8 = k + localMarginLayoutParams2.topMargin;
      int i9 = i5 + i7;
      int i10 = PlayUtils.getViewLeftFromParentStart(n, i6, bool, i9);
      this.mTitle.layout(i10, i8, i10 + i6, i8 + this.mTitle.getMeasuredHeight());
      int i11 = i8 + this.mTitle.getMeasuredHeight() + localMarginLayoutParams2.bottomMargin;
      int i12 = this.mOverflow.getMeasuredWidth();
      int i13 = MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams7);
      int i14 = i8 + localMarginLayoutParams7.topMargin;
      int i15 = j + i13;
      int i16 = PlayUtils.getViewLeftFromParentEnd(n, i12, bool, i15);
      this.mOverflow.layout(i16, i14, i16 + i12, i14 + this.mOverflow.getMeasuredHeight());
      TextView localTextView = this.mAdLabel;
      int i17 = 0;
      if (localTextView != null)
      {
        int i61 = this.mAdLabel.getVisibility();
        i17 = 0;
        if (i61 != 8)
        {
          ViewGroup.MarginLayoutParams localMarginLayoutParams11 = (ViewGroup.MarginLayoutParams)this.mAdLabel.getLayoutParams();
          i17 = this.mAdLabel.getMeasuredWidth() + localMarginLayoutParams11.rightMargin;
          int i62 = this.mAdLabel.getMeasuredWidth();
          int i63 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams11);
          int i64 = i11 + localMarginLayoutParams11.topMargin;
          int i65 = i5 + i63;
          int i66 = PlayUtils.getViewLeftFromParentStart(n, i62, bool, i65);
          this.mAdLabel.layout(i66, i64, i66 + i62, i64 + this.mAdLabel.getMeasuredHeight());
        }
      }
      if (this.mSubtitle.getVisibility() != 8)
      {
        int i55 = this.mSubtitle.getMeasuredWidth();
        int i56 = i11 + localMarginLayoutParams3.topMargin;
        if (i17 == 0) {
          break label1386;
        }
        i57 = this.mAdLabel.getPaddingTop();
        int i58 = i56 + i57;
        int i59 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams3) + (i17 + i5);
        int i60 = PlayUtils.getViewLeftFromParentStart(n, i55, bool, i59);
        this.mSubtitle.layout(i60, i58, i60 + i55, i58 + this.mSubtitle.getMeasuredHeight());
        i11 = i58 + this.mSubtitle.getMeasuredHeight() + localMarginLayoutParams3.bottomMargin;
      }
      if (this.mSubtitle2.getVisibility() != 8)
      {
        ViewGroup.MarginLayoutParams localMarginLayoutParams10 = (ViewGroup.MarginLayoutParams)this.mSubtitle2.getLayoutParams();
        int i51 = this.mSubtitle2.getMeasuredWidth();
        int i52 = i11 + localMarginLayoutParams10.topMargin;
        int i53 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams10) + (i17 + i5);
        int i54 = PlayUtils.getViewLeftFromParentStart(n, i51, bool, i53);
        this.mSubtitle2.layout(i54, i52, i54 + i51, i52 + this.mSubtitle2.getMeasuredHeight());
        i11 = i52 + this.mSubtitle2.getMeasuredHeight() + localMarginLayoutParams10.bottomMargin;
      }
      if (this.mAdCreative.getVisibility() != 8)
      {
        ViewGroup.MarginLayoutParams localMarginLayoutParams9 = (ViewGroup.MarginLayoutParams)this.mAdCreative.getLayoutParams();
        int i46 = this.mAdCreative.getMeasuredWidth();
        int i47 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams9);
        int i48 = i11 + localMarginLayoutParams9.topMargin;
        int i49 = i5 + i47;
        int i50 = PlayUtils.getViewLeftFromParentStart(n, i46, bool, i49);
        this.mAdCreative.layout(i50, i48, i50 + i46, i48 + this.mAdCreative.getMeasuredHeight());
        i11 = i48 + this.mAdCreative.getMeasuredHeight() + localMarginLayoutParams9.bottomMargin;
      }
      i18 = this.mLabel.getMeasuredWidth();
      int i19 = j + MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams5);
      i20 = PlayUtils.getViewLeftFromParentEnd(n, i18, bool, i19);
      i21 = i1 - m - localMarginLayoutParams5.bottomMargin;
      i22 = i21 - this.mLabel.getMeasuredHeight();
      if (this.mRatingBadge.getVisibility() != 8)
      {
        int i39 = this.mRatingBadge.getMeasuredWidth();
        int i40 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams4);
        if (this.mSubtitle2.getVisibility() != 8) {
          break label1392;
        }
        i41 = i11 + localMarginLayoutParams4.topMargin;
        label960:
        int i42 = i5 + i40;
        int i43 = PlayUtils.getViewLeftFromParentStart(n, i39, bool, i42);
        View localView = this.mRatingBadge;
        int i44 = i43 + i39;
        int i45 = i41 + this.mRatingBadge.getMeasuredHeight();
        localView.layout(i43, i41, i44, i45);
        this.mRatingBadge.getMeasuredHeight();
      }
      if (this.mSnippet2.getVisibility() != 8) {
        if (this.mSubtitle.getVisibility() == 8) {
          break label1415;
        }
      }
    }
    label1415:
    for (int i22 = this.mSubtitle.getTop();; i22 = this.mRatingBadge.getTop() + this.mRatingBadge.getBaseline() - this.mLabel.getBaseline())
    {
      i21 = i22 + this.mLabel.getMeasuredHeight();
      int i34 = this.mSnippet2.getMeasuredWidth();
      int i35 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams8);
      int i36 = i1 - m - localMarginLayoutParams8.bottomMargin;
      int i37 = i5 + i35;
      int i38 = PlayUtils.getViewLeftFromParentStart(n, i34, bool, i37);
      this.mSnippet2.layout(i38, i36 - this.mSnippet2.getMeasuredHeight(), i38 + i34, i36);
      PlayCardLabelView localPlayCardLabelView = this.mLabel;
      int i23 = i20 + i18;
      localPlayCardLabelView.layout(i20, i22, i23, i21);
      if (this.mAppSize.getVisibility() != 8)
      {
        int i28 = this.mAppSize.getMeasuredWidth();
        int i29 = this.mAppSize.getMeasuredHeight();
        int i30 = j + MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams6);
        int i31 = PlayUtils.getViewLeftFromParentEnd(n, i28, bool, i30);
        int i32 = this.mRatingBadge.getTop() + Math.max(this.mRatingBar.getHeight(), this.mItemBadge.getHeight()) / 2 - i29 / 2;
        int i33 = i32 + i29;
        this.mAppSize.layout(i31, i32, i31 + i28, i33);
      }
      int i24 = this.mLoadingIndicator.getMeasuredWidth();
      int i25 = this.mLoadingIndicator.getMeasuredHeight();
      int i26 = i + (n - i - j - i24) / 2;
      int i27 = k + (i1 - k - m - i25) / 2;
      this.mLoadingIndicator.layout(i26, i27, i26 + this.mLoadingIndicator.getMeasuredWidth(), i27 + this.mLoadingIndicator.getMeasuredHeight());
      recomputeOverflowAreaIfNeeded();
      return;
      bool = false;
      break;
      label1386:
      i57 = 0;
      break label549;
      label1392:
      i41 = i22 + this.mLabel.getBaseline() - this.mRatingBadge.getBaseline();
      break label960;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    measureThumbnailSpanningHeight(paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams();
    ViewGroup.MarginLayoutParams localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mTitle.getLayoutParams();
    ViewGroup.MarginLayoutParams localMarginLayoutParams3 = (ViewGroup.MarginLayoutParams)this.mSubtitle.getLayoutParams();
    ViewGroup.MarginLayoutParams localMarginLayoutParams4 = (ViewGroup.MarginLayoutParams)this.mRatingBadge.getLayoutParams();
    ViewGroup.MarginLayoutParams localMarginLayoutParams5 = (ViewGroup.MarginLayoutParams)this.mLabel.getLayoutParams();
    this.mAppSize.getLayoutParams();
    this.mSnippet2.getLayoutParams();
    int k = getPaddingLeft();
    int m = getPaddingRight();
    int n = getPaddingTop();
    int i1 = getPaddingBottom();
    int i2 = j - n - i1;
    int i3 = Math.min(localMarginLayoutParams1.height, i2);
    localMarginLayoutParams1.width = i3;
    localMarginLayoutParams1.height = i3;
    int i4 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
    this.mThumbnail.measure(i4, i4);
    this.mOverflow.measure(0, 0);
    int i5 = i - (k + localMarginLayoutParams1.width + localMarginLayoutParams1.rightMargin) - m;
    int i6 = i5 - localMarginLayoutParams2.leftMargin - localMarginLayoutParams2.rightMargin;
    this.mTitle.measure(View.MeasureSpec.makeMeasureSpec(i6, 1073741824), 0);
    TextView localTextView = this.mAdLabel;
    int i7 = 0;
    if (localTextView != null)
    {
      int i15 = this.mAdLabel.getVisibility();
      i7 = 0;
      if (i15 != 8)
      {
        ViewGroup.MarginLayoutParams localMarginLayoutParams7 = (ViewGroup.MarginLayoutParams)this.mAdLabel.getLayoutParams();
        this.mAdLabel.measure(0, 0);
        i7 = this.mAdLabel.getMeasuredWidth() + localMarginLayoutParams7.rightMargin;
      }
    }
    int i8 = i5 - i7 - localMarginLayoutParams3.leftMargin - localMarginLayoutParams3.rightMargin;
    this.mSubtitle.measure(View.MeasureSpec.makeMeasureSpec(i8, -2147483648), 0);
    if (this.mSubtitle2.getVisibility() != 8)
    {
      int i14 = i5 - PlayUtils.getSideMargins(this.mSubtitle2);
      this.mSubtitle2.measure(View.MeasureSpec.makeMeasureSpec(i14, -2147483648), 0);
    }
    this.mLabel.measure(View.MeasureSpec.makeMeasureSpec(i - k - m, -2147483648), 0);
    if (this.mAppSize.getVisibility() != 8) {
      this.mAppSize.measure(View.MeasureSpec.makeMeasureSpec(i - k - m, -2147483648), 0);
    }
    int i9 = i5 - localMarginLayoutParams4.leftMargin - localMarginLayoutParams4.rightMargin;
    this.mRatingBadge.measure(View.MeasureSpec.makeMeasureSpec(i9, 1073741824), 0);
    if (n + localMarginLayoutParams2.topMargin + this.mTitle.getMeasuredHeight() + localMarginLayoutParams2.bottomMargin + localMarginLayoutParams3.topMargin + this.mSubtitle.getMeasuredHeight() + localMarginLayoutParams3.bottomMargin + localMarginLayoutParams4.topMargin + this.mRatingBadge.getMeasuredHeight() > getMeasuredHeight() - i1 - localMarginLayoutParams5.bottomMargin - this.mLabel.getMeasuredHeight())
    {
      int i11 = i - m - localMarginLayoutParams5.leftMargin - this.mLabel.getMeasuredWidth() - localMarginLayoutParams5.rightMargin;
      int i12 = k + localMarginLayoutParams1.leftMargin + this.mThumbnail.getMeasuredWidth() + localMarginLayoutParams1.rightMargin + localMarginLayoutParams4.leftMargin;
      if (i12 + this.mRatingBadge.getMeasuredWidth() + localMarginLayoutParams4.rightMargin > i11)
      {
        int i13 = i11 - i12 - localMarginLayoutParams4.rightMargin;
        this.mRatingBadge.measure(View.MeasureSpec.makeMeasureSpec(i13, 1073741824), 0);
      }
    }
    if (this.mSnippet2.getVisibility() != 8) {
      this.mSnippet2.measure(View.MeasureSpec.makeMeasureSpec(i5, 1073741824), 0);
    }
    if ((this.mAdCreative != null) && (this.mAdCreative.getVisibility() != 8))
    {
      ViewGroup.MarginLayoutParams localMarginLayoutParams6 = (ViewGroup.MarginLayoutParams)this.mAdCreative.getLayoutParams();
      int i10 = i5 - this.mLabel.getMeasuredWidth() - localMarginLayoutParams6.leftMargin - localMarginLayoutParams6.rightMargin;
      this.mAdCreative.measure(View.MeasureSpec.makeMeasureSpec(i10, 1073741824), 0);
    }
    this.mLoadingIndicator.measure(0, 0);
    setMeasuredDimension(i, j);
  }
  
  public void setSnippetVisible(boolean paramBoolean)
  {
    PlayCardSnippet localPlayCardSnippet = this.mSnippet2;
    if (paramBoolean) {}
    for (int i = 0;; i = 8)
    {
      localPlayCardSnippet.setVisibility(i);
      boolean bool = false;
      if (!paramBoolean) {
        bool = true;
      }
      this.mSupportsSubtitleAndRating = bool;
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardViewListingSmall
 * JD-Core Version:    0.7.0.1
 */