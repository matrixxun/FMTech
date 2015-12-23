package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.google.android.finsky.layout.BucketRow;
import com.google.android.finsky.layout.ClusterContentBinder;
import com.google.android.finsky.layout.DetailsSectionStack.NoBottomSeparator;
import com.google.android.finsky.layout.DetailsSectionStack.NoTopSeparator;
import com.google.android.finsky.layout.FadingEdgeImageView;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.ForegroundRelativeLayout;

public class CreatorAvatarCardClusterModuleLayoutV2
  extends ForegroundRelativeLayout
  implements ModuleDividerItemDecoration.NoBottomDivider, ModuleDividerItemDecoration.NoTopDivider, ModuleMarginItemDecoration.EdgeToEdge, DetailsSectionStack.NoBottomSeparator, DetailsSectionStack.NoTopSeparator
{
  FifeImageView mAvatarImage;
  private final int mAvatarSize;
  private final int mBucketGap;
  private BucketRow mBucketRow;
  int mColumnCount;
  View mCoverFill;
  FadingEdgeImageView mCoverImage;
  float mCoverImageAspectRatio;
  final int mCoverImageFillColor;
  private final int mCoverImageHeight;
  private final int mCoverImageHeightGap;
  private boolean mHasNarrowCard;
  private int mHorizontalContentMargin;
  private final int mMoreButtonBottomGap;
  TextView mMoreView;
  int mNumOfShownItems;
  private final int mTextGap;
  TextView mTitle;
  private final int mVerticalPadding;
  
  public CreatorAvatarCardClusterModuleLayoutV2(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CreatorAvatarCardClusterModuleLayoutV2(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mCoverImageHeightGap = localResources.getDimensionPixelSize(2131492983);
    this.mCoverImageHeight = localResources.getDimensionPixelSize(2131492982);
    this.mAvatarSize = localResources.getDimensionPixelSize(2131492988);
    this.mBucketGap = localResources.getDimensionPixelSize(2131493259);
    this.mTextGap = localResources.getDimensionPixelSize(2131492989);
    this.mMoreButtonBottomGap = localResources.getDimensionPixelSize(2131492984);
    this.mColumnCount = UiUtils.getDetailsCardColumnCount(localResources);
    this.mHorizontalContentMargin = ModuleMarginItemDecoration.getDefaultSideMargin(localResources, localResources.getBoolean(2131427334));
    if (this.mHorizontalContentMargin > 0) {
      this.mHorizontalContentMargin -= localResources.getDimensionPixelSize(2131493385);
    }
    this.mVerticalPadding = localResources.getDimensionPixelSize(2131492981);
    this.mCoverImageFillColor = localResources.getColor(2131689624);
  }
  
  private int getLeadingContentGap(int paramInt)
  {
    int i = getPaddingLeft();
    return i + (paramInt - i - getPaddingRight() - this.mBucketRow.getPaddingLeft() - this.mBucketRow.getPaddingRight()) / this.mColumnCount * (this.mColumnCount - this.mNumOfShownItems);
  }
  
  final void bindBucketRow(ClusterContentBinder paramClusterContentBinder)
  {
    for (int i = 0; i < this.mNumOfShownItems; i++) {
      paramClusterContentBinder.bindChild(this.mBucketRow.getChildAt(i), i);
    }
  }
  
  final void computeCardContentAspectRatio(ClusterContentBinder paramClusterContentBinder)
  {
    int i = Math.min(paramClusterContentBinder.getAvailableChildCount(), this.mNumOfShownItems);
    for (int j = 0; j < i; j++) {
      if (paramClusterContentBinder.getChildCoverAspectRatio(j) == 1.441F)
      {
        this.mHasNarrowCard = true;
        return;
      }
    }
    this.mHasNarrowCard = false;
  }
  
  final void inflateCards()
  {
    int i = this.mBucketRow.getChildCount();
    if (i == this.mNumOfShownItems) {}
    int j;
    do
    {
      return;
      while (i > this.mNumOfShownItems)
      {
        this.mBucketRow.removeViewAt(i - 1);
        i--;
      }
      j = this.mNumOfShownItems - i;
    } while (j <= 0);
    Context localContext = getContext();
    LayoutInflater localLayoutInflater = LayoutInflater.from(localContext);
    Resources localResources = localContext.getResources();
    int k = 0;
    label72:
    if (k < j) {
      if (!localResources.getBoolean(2131427339)) {
        break label121;
      }
    }
    label121:
    for (int m = 2130968939;; m = 2130968954)
    {
      View localView = localLayoutInflater.inflate(m, this.mBucketRow, false);
      this.mBucketRow.addView(localView);
      k++;
      break label72;
      break;
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAvatarImage = ((FifeImageView)findViewById(2131755360));
    this.mCoverFill = findViewById(2131755358);
    this.mCoverImage = ((FadingEdgeImageView)findViewById(2131755359));
    this.mTitle = ((TextView)findViewById(2131755173));
    this.mMoreView = ((TextView)findViewById(2131755361));
    this.mBucketRow = ((BucketRow)findViewById(2131755259));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getPaddingLeft();
    int j = this.mAvatarImage.getMeasuredHeight();
    int k = this.mAvatarImage.getMeasuredWidth();
    int m = this.mTitle.getMeasuredHeight();
    int n = this.mTitle.getMeasuredWidth();
    int i1 = this.mMoreView.getMeasuredHeight();
    int i2 = this.mMoreView.getMeasuredWidth();
    int i3 = getMeasuredHeight();
    int i4 = getMeasuredWidth();
    int i5 = getLeadingContentGap(i4 - 2 * this.mHorizontalContentMargin);
    int i6 = (i5 + i) / 2;
    this.mCoverFill.layout(0, this.mVerticalPadding, i4, this.mVerticalPadding + this.mCoverFill.getMeasuredHeight());
    if (this.mNumOfShownItems > 0) {}
    for (int i7 = 0;; i7 = (i4 - this.mCoverImage.getMeasuredWidth()) / 2)
    {
      this.mCoverImage.layout(i7, this.mVerticalPadding, i7 + this.mCoverImage.getMeasuredWidth(), this.mVerticalPadding + this.mCoverImage.getMeasuredHeight());
      int i8 = (i3 - this.mBucketRow.getMeasuredHeight()) / 2;
      this.mBucketRow.layout(i5 + this.mHorizontalContentMargin, i8, i5 + this.mHorizontalContentMargin + this.mBucketRow.getMeasuredWidth(), i8 + this.mBucketRow.getMeasuredHeight());
      int i9 = i6 + this.mHorizontalContentMargin - i2 / 2;
      int i10 = i9 + i2;
      int i11 = i3 - this.mVerticalPadding - this.mMoreButtonBottomGap;
      this.mMoreView.layout(i9, i11 - i1, i10, i11);
      int i12 = i6 + this.mHorizontalContentMargin - n / 2;
      int i13 = i12 + n;
      int i14 = this.mMoreView.getTop() - this.mTextGap;
      this.mTitle.layout(i12, i14 - m, i13, i14);
      int i15 = i6 + this.mHorizontalContentMargin - k / 2;
      int i16 = i15 + k;
      int i17 = this.mTitle.getTop() - this.mTextGap;
      this.mAvatarImage.layout(i15, i17 - j, i16, i17);
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = i - 2 * this.mHorizontalContentMargin;
    int k = getPaddingLeft();
    int m = getPaddingRight();
    int n = getLeadingContentGap(j);
    int i1;
    int i3;
    label141:
    int i4;
    int i6;
    label270:
    boolean bool;
    if (this.mHasNarrowCard)
    {
      i1 = this.mCoverImageHeightGap;
      this.mTitle.measure(View.MeasureSpec.makeMeasureSpec(n - k, -2147483648), 0);
      this.mMoreView.measure(0, 0);
      this.mBucketRow.measure(View.MeasureSpec.makeMeasureSpec(j - n - m, 1073741824), 0);
      int i2 = View.MeasureSpec.makeMeasureSpec(this.mAvatarSize, 1073741824);
      this.mAvatarImage.measure(i2, i2);
      if (!this.mHasNarrowCard) {
        break label328;
      }
      i3 = this.mBucketRow.getMeasuredHeight() - i1 * 2;
      i4 = Math.max(i3, 2 * this.mTextGap + this.mMoreButtonBottomGap + this.mMoreView.getMeasuredHeight() + this.mTitle.getMeasuredHeight() + this.mAvatarImage.getMeasuredHeight());
      int i5 = Math.min((int)(i4 / this.mCoverImageAspectRatio), i);
      this.mCoverImage.measure(View.MeasureSpec.makeMeasureSpec(i5, 1073741824), View.MeasureSpec.makeMeasureSpec(i4, 1073741824));
      this.mCoverFill.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(i4, 1073741824));
      if ((this.mCoverImage.getVisibility() == 8) || (this.mCoverImage.getMeasuredWidth() >= i)) {
        break label366;
      }
      if (this.mNumOfShownItems <= 0) {
        break label354;
      }
      i6 = 1;
      int i7 = (int)(0.1F * i);
      FadingEdgeImageView localFadingEdgeImageView = this.mCoverImage;
      if (i6 != 0) {
        break label360;
      }
      bool = true;
      label293:
      localFadingEdgeImageView.configureFadingEdges(bool, true, i7, this.mCoverImageFillColor);
    }
    for (;;)
    {
      setMeasuredDimension(i, i4 + 2 * this.mVerticalPadding);
      return;
      i1 = 0;
      break;
      label328:
      i3 = Math.max(this.mCoverImageHeight, this.mBucketRow.getMeasuredHeight() + 2 * this.mBucketGap);
      break label141;
      label354:
      i6 = 0;
      break label270;
      label360:
      bool = false;
      break label293;
      label366:
      this.mCoverImage.clearFadingEdges();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.CreatorAvatarCardClusterModuleLayoutV2
 * JD-Core Version:    0.7.0.1
 */