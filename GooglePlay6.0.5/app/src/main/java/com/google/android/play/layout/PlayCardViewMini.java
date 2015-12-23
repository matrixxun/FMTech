package com.google.android.play.layout;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.play.R.dimen;
import com.google.android.play.R.id;
import com.google.android.play.utils.PlayUtils;

public class PlayCardViewMini
  extends PlayCardViewBase
{
  protected View mAdLabelContainer;
  private int mCurrTitleMaxLines;
  private boolean mIsTallTextContent;
  private final int mLabelThreshold;
  private int mTextContentHeight = -1;
  private int mVerticalOverlap;
  
  public PlayCardViewMini(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewMini(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mTextContentHeight = localResources.getDimensionPixelSize(R.dimen.play_mini_card_content_height);
    this.mLabelThreshold = localResources.getDimensionPixelSize(R.dimen.play_mini_card_label_threshold);
    this.mCurrTitleMaxLines = 2;
  }
  
  private int getTextContentHeight()
  {
    Resources localResources;
    if (this.mTextContentHeight == -1)
    {
      localResources = getResources();
      if (!this.mIsTallTextContent) {
        break label38;
      }
    }
    label38:
    for (int i = R.dimen.play_mini_card_content_height_tall;; i = R.dimen.play_mini_card_content_height)
    {
      this.mTextContentHeight = localResources.getDimensionPixelSize(i);
      return this.mTextContentHeight;
    }
  }
  
  private boolean isSubtitleOnItsOwnLine(int paramInt)
  {
    if (this.mIsTallTextContent) {}
    for (int i = 4; paramInt <= i - 2; i = 3) {
      return true;
    }
    return false;
  }
  
  public int getCardType()
  {
    return 0;
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAdLabelContainer = findViewById(R.id.li_ad_label_container);
  }
  
  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool;
    int i;
    int j;
    int k;
    int m;
    int n;
    int i1;
    ViewGroup.MarginLayoutParams localMarginLayoutParams3;
    ViewGroup.MarginLayoutParams localMarginLayoutParams6;
    label125:
    int i15;
    int i26;
    int i27;
    if (ViewCompat.getLayoutDirection(this) == 0)
    {
      bool = true;
      i = ViewCompat.getPaddingStart(this);
      j = ViewCompat.getPaddingEnd(this);
      k = getPaddingTop();
      m = getPaddingBottom();
      n = getWidth();
      i1 = getHeight();
      ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)this.mTitle.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mSubtitle.getLayoutParams();
      localMarginLayoutParams3 = (ViewGroup.MarginLayoutParams)this.mRatingBar.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams4 = (ViewGroup.MarginLayoutParams)this.mLabel.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams5 = (ViewGroup.MarginLayoutParams)this.mOverflow.getLayoutParams();
      if (this.mAdLabelContainer == null) {
        break label805;
      }
      localMarginLayoutParams6 = (ViewGroup.MarginLayoutParams)this.mAdLabelContainer.getLayoutParams();
      int i2 = this.mThumbnail.getMeasuredWidth();
      int i3 = this.mThumbnail.getMeasuredHeight();
      int i4 = PlayUtils.getViewLeftFromParentStart(n, i2, bool, i);
      this.mThumbnail.layout(i4, k, i4 + i2, k + i3);
      if ((this.mAdLabelContainer != null) && (this.mAdLabelContainer.getVisibility() != 8))
      {
        int i30 = this.mAdLabelContainer.getMeasuredWidth();
        int i31 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams6);
        int i32 = k + i3 - this.mThumbnail.getPaddingTop() - this.mAdLabelContainer.getMeasuredHeight();
        int i33 = PlayUtils.getViewLeftFromParentStart(n, i30, bool, i31 + (i + ViewCompat.getPaddingStart(this.mThumbnail)));
        this.mAdLabelContainer.layout(i33, i32, i33 + this.mAdLabelContainer.getMeasuredWidth(), i32 + this.mAdLabelContainer.getMeasuredHeight());
      }
      int i5 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams1);
      int i6 = this.mTitle.getMeasuredWidth();
      int i7 = k + i3 + localMarginLayoutParams1.topMargin - this.mVerticalOverlap;
      int i8 = PlayUtils.getViewLeftFromParentStart(n, i6, bool, i + i5);
      this.mTitle.layout(i8, i7, i8 + i6, i7 + this.mTitle.getMeasuredHeight());
      int i9 = MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams5);
      int i10 = this.mOverflow.getMeasuredWidth();
      int i11 = i7 + localMarginLayoutParams5.topMargin;
      int i12 = PlayUtils.getViewLeftFromParentEnd(n, i10, bool, j + i9);
      this.mOverflow.layout(i12, i11, i12 + i10, i11 + this.mOverflow.getMeasuredHeight());
      int i13 = MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams4);
      int i14 = this.mLabel.getMeasuredWidth();
      i15 = i1 - m - localMarginLayoutParams4.bottomMargin + this.mVerticalOverlap;
      int i16 = PlayUtils.getViewLeftFromParentEnd(n, i14, bool, j + i13);
      this.mLabel.layout(i16, i15 - this.mLabel.getMeasuredHeight(), i16 + i14, i15);
      if (this.mSubtitle.getVisibility() == 0)
      {
        int i25 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams2);
        i26 = this.mSubtitle.getMeasuredWidth();
        i27 = PlayUtils.getViewLeftFromParentStart(n, i26, bool, i + i25);
        if (!isSubtitleOnItsOwnLine(this.mTitle.getLineCount())) {
          break label811;
        }
        int i29 = i7 + this.mTitle.getMeasuredHeight() + localMarginLayoutParams1.bottomMargin + localMarginLayoutParams2.topMargin;
        this.mSubtitle.layout(i27, i29, i27 + i26, i29 + this.mSubtitle.getMeasuredHeight());
      }
    }
    for (;;)
    {
      if (this.mRatingBar.getVisibility() == 0)
      {
        int i21 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams3);
        int i22 = this.mRatingBar.getMeasuredWidth();
        int i23 = this.mLabel.getTop() + this.mLabel.getBaseline() - this.mRatingBar.getBaseline();
        int i24 = PlayUtils.getViewLeftFromParentStart(n, i22, bool, i + i21);
        this.mRatingBar.layout(i24, i23, i24 + i22, i23 + this.mRatingBar.getMeasuredHeight());
      }
      int i17 = this.mLoadingIndicator.getMeasuredWidth();
      int i18 = this.mLoadingIndicator.getMeasuredHeight();
      int i19 = i + (n - i - j - i17) / 2;
      int i20 = k + (i1 - k - m - i18) / 2;
      this.mLoadingIndicator.layout(i19, i20, i19 + this.mLoadingIndicator.getMeasuredWidth(), i20 + this.mLoadingIndicator.getMeasuredHeight());
      recomputeOverflowAreaIfNeeded();
      return;
      bool = false;
      break;
      label805:
      localMarginLayoutParams6 = null;
      break label125;
      label811:
      int i28 = i15 - this.mLabel.getMeasuredHeight() + this.mLabel.getBaseline() - this.mSubtitle.getBaseline();
      this.mSubtitle.layout(i27, i28, i27 + i26, i28 + this.mSubtitle.getMeasuredHeight());
    }
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    measureThumbnailSpanningWidth(paramInt1);
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams();
    int i = View.MeasureSpec.getMode(paramInt2);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = localMarginLayoutParams.height + getTextContentHeight() + getPaddingTop() + getPaddingBottom();
    int m;
    int n;
    int i3;
    int i5;
    int i6;
    int i7;
    label232:
    int i8;
    if ((i == 1073741824) && (j > 0))
    {
      m = j;
      n = View.MeasureSpec.getSize(paramInt1);
      this.mVerticalOverlap = Math.max(0, (k - m) / 3);
      int i1 = getPaddingLeft();
      int i2 = getPaddingRight();
      i3 = n - i1 - i2;
      int i4 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
      this.mThumbnail.measure(i4, View.MeasureSpec.makeMeasureSpec(localMarginLayoutParams.height, 1073741824));
      i5 = PlayUtils.getSideMargins(this.mTitle);
      i6 = PlayUtils.getSideMargins(this.mSubtitle);
      i7 = PlayUtils.getSideMargins(this.mLabel);
      if (this.mAdLabelContainer != null)
      {
        if ((this.mAdLabel == null) || (this.mAdLabel.getVisibility() == 8)) {
          break label361;
        }
        this.mAdLabelContainer.setVisibility(0);
        int i18 = i3 - this.mThumbnail.getPaddingLeft() - this.mThumbnail.getPaddingRight();
        this.mAdLabelContainer.measure(View.MeasureSpec.makeMeasureSpec(i18, -2147483648), 0);
      }
      if (this.mBackendId != 3) {
        break label373;
      }
      i8 = 1;
      label243:
      if ((i8 == 0) || (this.mLabel.getVisibility() == 8)) {
        break label401;
      }
      int i17 = i3 - i7;
      this.mLabel.measure(View.MeasureSpec.makeMeasureSpec(i17, -2147483648), 0);
    }
    int i10;
    for (;;)
    {
      i10 = i7 + this.mLabel.getMeasuredWidth();
      this.mOverflow.measure(0, 0);
      int i11 = i3 - i5;
      this.mTitle.measure(View.MeasureSpec.makeMeasureSpec(i11, 1073741824), 0);
      this.mLoadingIndicator.measure(0, 0);
      setMeasuredDimension(n, m);
      if (this.mLoadingIndicator.getVisibility() != 0) {
        break label422;
      }
      return;
      m = k;
      break;
      label361:
      this.mAdLabelContainer.setVisibility(8);
      break label232;
      label373:
      if ((this.mIsItemOwned) || (n >= this.mLabelThreshold))
      {
        i8 = 1;
        break label243;
      }
      i8 = 0;
      break label243;
      label401:
      int i9 = View.MeasureSpec.makeMeasureSpec(0, 1073741824);
      this.mLabel.measure(i9, 0);
    }
    label422:
    int i12;
    label437:
    int i13;
    label452:
    int i14;
    if (this.mSubtitle.getVisibility() != 8)
    {
      i12 = 1;
      if (this.mRatingBar.getVisibility() == 8) {
        break label578;
      }
      i13 = 1;
      if ((i12 != 0) && (isSubtitleOnItsOwnLine(this.mTitle.getLineCount())))
      {
        int i16 = i3 - i6;
        this.mSubtitle.measure(View.MeasureSpec.makeMeasureSpec(i16, 1073741824), 0);
        i12 = 0;
      }
      i14 = i3 - i10;
      if (i12 == 0) {
        break label584;
      }
      int i15 = i14 - i6;
      if (i15 >= i3 * 3 / 10)
      {
        this.mSubtitle.measure(View.MeasureSpec.makeMeasureSpec(i15, 1073741824), 0);
        i12 = 0;
      }
    }
    for (;;)
    {
      if (i12 != 0) {
        this.mSubtitle.setVisibility(4);
      }
      if (i13 == 0) {
        break;
      }
      this.mRatingBar.setVisibility(4);
      return;
      i12 = 0;
      break label437;
      label578:
      i13 = 0;
      break label452;
      label584:
      if (i13 != 0)
      {
        this.mRatingBar.measure(0, 0);
        if (this.mRatingBar.getMeasuredWidth() + PlayUtils.getSideMargins(this.mRatingBar) <= i14) {
          i13 = 0;
        }
      }
    }
  }
  
  public void setTallTextContent(boolean paramBoolean)
  {
    this.mIsTallTextContent = paramBoolean;
    this.mTextContentHeight = -1;
  }
  
  public void setTitleMaxLines(int paramInt)
  {
    if (this.mCurrTitleMaxLines == paramInt) {
      return;
    }
    this.mCurrTitleMaxLines = paramInt;
    this.mTitle.setMaxLines(paramInt);
  }
  
  public final boolean supportsSubtitleAndRating()
  {
    if (isSubtitleOnItsOwnLine(this.mCurrTitleMaxLines)) {
      return true;
    }
    return super.supportsSubtitleAndRating();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayCardViewMini
 * JD-Core Version:    0.7.0.1
 */