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

public class PlayCardViewSmall
  extends PlayCardViewBase
{
  protected View mAdLabelContainer;
  protected View mRatingBadgeContainer;
  private int mTextContentFlags = 1;
  private int mTextContentHeight = -1;
  private int mVerticalBump;
  private final int mVerticalBumpLimit;
  
  public PlayCardViewSmall(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewSmall(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mTextContentHeight = localResources.getDimensionPixelSize(R.dimen.play_small_card_content_min_height);
    this.mVerticalBumpLimit = localResources.getDimensionPixelSize(R.dimen.play_card_extra_vspace);
  }
  
  private int getTextContentHeight()
  {
    Resources localResources;
    if (this.mTextContentHeight == -1)
    {
      localResources = getResources();
      if (!hasTextContentFlag(8)) {
        break label40;
      }
    }
    label40:
    for (int i = R.dimen.play_small_card_content_min_height_tall;; i = R.dimen.play_small_card_content_min_height)
    {
      this.mTextContentHeight = localResources.getDimensionPixelSize(i);
      return this.mTextContentHeight;
    }
  }
  
  private boolean hasTextContentFlag(int paramInt)
  {
    return (paramInt & this.mTextContentFlags) != 0;
  }
  
  public int getCardType()
  {
    return 1;
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    this.mRatingBadgeContainer = findViewById(R.id.rating_badge_container);
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
    ViewGroup.MarginLayoutParams localMarginLayoutParams2;
    ViewGroup.MarginLayoutParams localMarginLayoutParams3;
    ViewGroup.MarginLayoutParams localMarginLayoutParams4;
    ViewGroup.MarginLayoutParams localMarginLayoutParams5;
    ViewGroup.MarginLayoutParams localMarginLayoutParams6;
    label125:
    ViewGroup.MarginLayoutParams localMarginLayoutParams7;
    int i8;
    int i10;
    int i11;
    int i12;
    label504:
    int i15;
    label548:
    int i31;
    int i32;
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
      localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mTitle.getLayoutParams();
      localMarginLayoutParams3 = (ViewGroup.MarginLayoutParams)this.mSubtitle.getLayoutParams();
      localMarginLayoutParams4 = (ViewGroup.MarginLayoutParams)this.mRatingBadgeContainer.getLayoutParams();
      localMarginLayoutParams5 = (ViewGroup.MarginLayoutParams)this.mLabel.getLayoutParams();
      if (this.mOverflow == null) {
        break label988;
      }
      localMarginLayoutParams6 = (ViewGroup.MarginLayoutParams)this.mOverflow.getLayoutParams();
      localMarginLayoutParams7 = (ViewGroup.MarginLayoutParams)this.mSnippet2.getLayoutParams();
      int i2 = this.mThumbnail.getMeasuredWidth();
      int i3 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams1);
      int i4 = this.mThumbnail.getMeasuredHeight();
      int i5 = PlayUtils.getViewLeftFromParentStart(n, i2, bool, i + i3);
      this.mThumbnail.layout(i5, k + localMarginLayoutParams1.topMargin, i5 + i2, i4 + (k + localMarginLayoutParams1.topMargin));
      if ((this.mAdLabelContainer != null) && (this.mAdLabelContainer.getVisibility() != 8))
      {
        int i41 = this.mAdLabelContainer.getMeasuredWidth();
        int i42 = ViewCompat.getPaddingStart(this.mThumbnail);
        int i43 = k + i4 - this.mThumbnail.getPaddingTop() - this.mAdLabelContainer.getMeasuredHeight();
        int i44 = PlayUtils.getViewLeftFromParentStart(n, i41, bool, i3 + (i + i42));
        this.mAdLabelContainer.layout(i44, i43, i44 + i41, i43 + this.mAdLabelContainer.getMeasuredHeight());
      }
      int i6 = this.mTitle.getMeasuredWidth();
      int i7 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams2);
      i8 = k + i4 + localMarginLayoutParams2.topMargin + this.mVerticalBump;
      int i9 = PlayUtils.getViewLeftFromParentStart(n, i6, bool, i + i7);
      i10 = this.mTitle.getMeasuredHeight();
      this.mTitle.layout(i9, i8, i9 + i6, i8 + i10);
      if ((this.mOverflow != null) && (this.mOverflow.getVisibility() != 8))
      {
        int i37 = this.mOverflow.getMeasuredWidth();
        int i38 = MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams6);
        int i39 = i8 + localMarginLayoutParams6.topMargin;
        int i40 = PlayUtils.getViewLeftFromParentEnd(n, i37, bool, j + i38);
        this.mOverflow.layout(i40, i39, i40 + i37, i39 + this.mOverflow.getMeasuredHeight());
      }
      i11 = Math.max(this.mVerticalBump, 0);
      if (hasTextContentFlag(2)) {
        break label994;
      }
      i12 = 1;
      int i13 = this.mLabel.getMeasuredWidth();
      int i14 = this.mLabel.getMeasuredHeight();
      if (i12 == 0) {
        break label1000;
      }
      i15 = i1 - m - localMarginLayoutParams5.bottomMargin - i14 - this.mVerticalBump;
      int i16 = PlayUtils.getViewLeftFromParentEnd(n, i13, bool, j + MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams5));
      PlayCardLabelView localPlayCardLabelView = this.mLabel;
      int i17 = i16 + i13;
      int i18 = i15 + i14;
      localPlayCardLabelView.layout(i16, i15, i17, i18);
      if (this.mSubtitle.getVisibility() != 8)
      {
        i31 = this.mSubtitle.getMeasuredWidth();
        i32 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams3);
        if (i12 == 0) {
          break label1021;
        }
      }
    }
    label988:
    label994:
    label1000:
    label1021:
    for (int i33 = i11 + (i8 + i10 + localMarginLayoutParams2.bottomMargin + localMarginLayoutParams3.topMargin);; i33 = i15 + this.mLabel.getBaseline() - this.mSubtitle.getBaseline())
    {
      int i34 = PlayUtils.getViewLeftFromParentStart(n, i31, bool, i + i32);
      PlayTextView localPlayTextView = this.mSubtitle;
      int i35 = i34 + i31;
      int i36 = i33 + this.mSubtitle.getMeasuredHeight();
      localPlayTextView.layout(i34, i33, i35, i36);
      if (this.mRatingBadgeContainer.getVisibility() != 8)
      {
        int i27 = this.mRatingBadgeContainer.getMeasuredWidth();
        int i28 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams4);
        int i29 = i15 + this.mLabel.getBaseline() - this.mRatingBadgeContainer.getBaseline();
        int i30 = PlayUtils.getViewLeftFromParentStart(n, i27, bool, i + i28);
        this.mRatingBadgeContainer.layout(i30, i29, i30 + this.mRatingBadgeContainer.getMeasuredWidth(), i29 + this.mRatingBadgeContainer.getMeasuredHeight());
      }
      if (this.mSnippet2.getVisibility() != 8)
      {
        int i23 = this.mSnippet2.getMeasuredWidth();
        int i24 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams7);
        int i25 = i1 - m - localMarginLayoutParams7.bottomMargin - this.mVerticalBump;
        int i26 = PlayUtils.getViewLeftFromParentStart(n, i23, bool, i + i24);
        this.mSnippet2.layout(i26, i25 - this.mSnippet2.getMeasuredHeight(), i26 + i23, i25);
      }
      int i19 = this.mLoadingIndicator.getMeasuredWidth();
      int i20 = this.mLoadingIndicator.getMeasuredHeight();
      int i21 = i + (n - i - j - i19) / 2;
      int i22 = k + (i1 - k - m - i20) / 2;
      this.mLoadingIndicator.layout(i21, i22, i21 + this.mLoadingIndicator.getMeasuredWidth(), i22 + this.mLoadingIndicator.getMeasuredHeight());
      recomputeOverflowAreaIfNeeded();
      return;
      bool = false;
      break;
      localMarginLayoutParams6 = null;
      break label125;
      i12 = 0;
      break label504;
      i15 = i11 + (this.mTitle.getBottom() + localMarginLayoutParams5.topMargin);
      break label548;
    }
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    measureThumbnailSpanningWidth(paramInt1);
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getPaddingLeft();
    int k = getPaddingRight();
    int m = getPaddingTop();
    int n = getPaddingBottom();
    int i1 = i - j - k;
    ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams();
    int i2 = View.MeasureSpec.getMode(paramInt2);
    int i3 = View.MeasureSpec.getSize(paramInt2);
    int i4 = n + (m + localMarginLayoutParams1.height + Math.max(getTextContentHeight(), i1 / 2));
    int i5;
    ViewGroup.MarginLayoutParams localMarginLayoutParams3;
    ViewGroup.MarginLayoutParams localMarginLayoutParams6;
    label281:
    int i8;
    int i11;
    label442:
    int i16;
    label475:
    int i12;
    label636:
    int i13;
    int i14;
    if ((i2 == 1073741824) && (i3 > 0))
    {
      i5 = i3;
      int i6 = i1 - localMarginLayoutParams1.leftMargin - localMarginLayoutParams1.rightMargin;
      this.mThumbnail.measure(View.MeasureSpec.makeMeasureSpec(i6, 1073741824), View.MeasureSpec.makeMeasureSpec(localMarginLayoutParams1.height, 1073741824));
      ViewGroup.MarginLayoutParams localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mTitle.getLayoutParams();
      localMarginLayoutParams3 = (ViewGroup.MarginLayoutParams)this.mSubtitle.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams4 = (ViewGroup.MarginLayoutParams)this.mLabel.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams5 = (ViewGroup.MarginLayoutParams)this.mRatingBadgeContainer.getLayoutParams();
      localMarginLayoutParams6 = (ViewGroup.MarginLayoutParams)this.mSnippet2.getLayoutParams();
      if (this.mAdLabelContainer != null)
      {
        if ((this.mAdLabel == null) || (this.mAdLabel.getVisibility() == 8)) {
          break label696;
        }
        this.mAdLabelContainer.setVisibility(0);
        int i17 = i6 - this.mThumbnail.getPaddingLeft() - this.mThumbnail.getPaddingRight();
        this.mAdLabelContainer.measure(View.MeasureSpec.makeMeasureSpec(i17, -2147483648), 0);
      }
      int i7 = i1 - localMarginLayoutParams4.leftMargin - localMarginLayoutParams4.rightMargin;
      this.mLabel.measure(View.MeasureSpec.makeMeasureSpec(i7, -2147483648), 0);
      i8 = this.mLabel.getMeasuredWidth() + localMarginLayoutParams4.leftMargin + localMarginLayoutParams4.rightMargin;
      if (this.mOverflow != null) {
        this.mOverflow.measure(0, 0);
      }
      int i9 = i1 - localMarginLayoutParams2.leftMargin - localMarginLayoutParams2.rightMargin;
      this.mTitle.measure(View.MeasureSpec.makeMeasureSpec(i9, 1073741824), 0);
      if (this.mLoadingIndicator.getVisibility() == 0) {
        break label785;
      }
      int i10 = Math.max(0, i1 - i8 - localMarginLayoutParams5.leftMargin - localMarginLayoutParams5.rightMargin);
      this.mRatingBadgeContainer.measure(View.MeasureSpec.makeMeasureSpec(i10, -2147483648), 0);
      if (hasTextContentFlag(2)) {
        break label708;
      }
      i11 = 1;
      if (this.mSubtitle.getVisibility() != 8)
      {
        if (i11 == 0) {
          break label714;
        }
        i16 = i1 - localMarginLayoutParams3.leftMargin - localMarginLayoutParams3.rightMargin;
        if (i16 < 0) {
          i16 = 0;
        }
        this.mSubtitle.measure(0, 0);
        if (this.mSubtitle.getMeasuredWidth() > i16) {
          this.mSubtitle.measure(View.MeasureSpec.makeMeasureSpec(i16, 1073741824), 0);
        }
      }
      if (this.mSnippet2.getVisibility() != 8)
      {
        int i15 = i1 - localMarginLayoutParams6.leftMargin - localMarginLayoutParams6.rightMargin;
        this.mSnippet2.measure(View.MeasureSpec.makeMeasureSpec(i15, 1073741824), 1073741824);
      }
      i12 = localMarginLayoutParams2.topMargin + this.mTitle.getMeasuredHeight() + localMarginLayoutParams2.bottomMargin + localMarginLayoutParams3.topMargin + this.mSubtitle.getMeasuredHeight() + localMarginLayoutParams3.bottomMargin;
      if (i11 == 0) {
        break label730;
      }
      i12 += localMarginLayoutParams4.topMargin + this.mLabel.getMeasuredHeight() + localMarginLayoutParams4.bottomMargin;
      i13 = i5 - m - n - localMarginLayoutParams1.height - i12;
      if (i13 > 0) {
        break label769;
      }
      i14 = i13 / 2;
    }
    label666:
    for (this.mVerticalBump = i14;; this.mVerticalBump = 0)
    {
      this.mLoadingIndicator.measure(0, 0);
      setMeasuredDimension(i, i5);
      return;
      i5 = i4;
      break;
      label696:
      this.mAdLabelContainer.setVisibility(8);
      break label281;
      label708:
      i11 = 0;
      break label442;
      label714:
      i16 = i1 - localMarginLayoutParams3.leftMargin - i8;
      break label475;
      label730:
      if (this.mSnippet2.getVisibility() == 8) {
        break label636;
      }
      i12 += localMarginLayoutParams6.topMargin + this.mSnippet2.getMeasuredHeight() + localMarginLayoutParams6.bottomMargin;
      break label636;
      i14 = Math.min(i13 / 4, this.mVerticalBumpLimit);
      break label666;
    }
  }
  
  public void setTextContentFlags(int paramInt)
  {
    int i = 2;
    int j = 1;
    if (paramInt == this.mTextContentFlags) {
      return;
    }
    this.mTextContentFlags = paramInt;
    boolean bool = hasTextContentFlag(i);
    label46:
    TextView localTextView;
    if (!bool)
    {
      int m = j;
      this.mSupportsSubtitleAndRating = m;
      if (!bool) {
        break label95;
      }
      i = j;
      if (hasTextContentFlag(8)) {
        i++;
      }
      localTextView = this.mTitle;
      if (i != j) {
        break label108;
      }
    }
    for (;;)
    {
      localTextView.setSingleLine(j);
      this.mTitle.setMaxLines(i);
      this.mTextContentHeight = -1;
      return;
      int n = 0;
      break;
      label95:
      if (!hasTextContentFlag(4)) {
        break label46;
      }
      i = 3;
      break label46;
      label108:
      int k = 0;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayCardViewSmall
 * JD-Core Version:    0.7.0.1
 */