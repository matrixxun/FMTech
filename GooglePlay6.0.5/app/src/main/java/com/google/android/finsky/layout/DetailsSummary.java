package com.google.android.finsky.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.PaintDrawable;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.finsky.detailspage.ModuleDividerItemDecoration.NoTopDivider;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.utils.PlayTouchDelegate;
import com.google.android.play.utils.PlayUtils;
import java.util.List;

public class DetailsSummary
  extends ViewGroup
  implements ModuleDividerItemDecoration.NoTopDivider, DetailsPartialFadeSection, DetailsPeekingSection, DetailsSectionStack.NoTopSeparator
{
  private View mAppSizeRatingLine;
  private View mBackgroundLayer;
  private View mBylines;
  private final int mContentMargin;
  private View mContentRatingAppSize;
  private View mContentRatingPanel;
  private View mCreatorBlock;
  private View mCreatorPanel;
  private View mDetailsSummaryDynamic;
  private View mExtraLabels;
  private View mExtraLabelsBottom;
  private boolean mIsExtraLabelsInCompactMode;
  private final Rect mOldWishlistArea;
  private PlayCardThumbnail mThumbnailFrame;
  private int mThumbnailMode;
  private int mThumbnailVerticalPeek;
  private View mTipperSticker;
  private View mTitle;
  private final int mTitleOffset;
  private final Rect mWishlistArea;
  private View mWishlistButton;
  private final int mWishlistTouchExtend;
  
  public DetailsSummary(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsSummary(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mContentMargin = localResources.getDimensionPixelSize(2131492993);
    this.mThumbnailVerticalPeek = localResources.getDimensionPixelSize(2131493525);
    this.mTitleOffset = localResources.getDimensionPixelSize(2131493291);
    this.mWishlistTouchExtend = localResources.getDimensionPixelSize(2131493526);
    this.mWishlistArea = new Rect();
    this.mOldWishlistArea = new Rect();
    this.mThumbnailMode = 0;
  }
  
  public final void addParticipatingChildViewIds(List<Integer> paramList)
  {
    paramList.add(Integer.valueOf(2131755262));
    paramList.add(Integer.valueOf(2131755394));
    paramList.add(Integer.valueOf(2131755395));
    paramList.add(Integer.valueOf(2131755427));
    paramList.add(Integer.valueOf(2131755397));
    paramList.add(Integer.valueOf(2131755400));
    paramList.add(Integer.valueOf(2131755401));
    paramList.add(Integer.valueOf(2131755402));
    paramList.add(Integer.valueOf(2131755403));
    paramList.add(Integer.valueOf(2131755404));
    paramList.add(Integer.valueOf(2131755409));
    paramList.add(Integer.valueOf(2131755405));
  }
  
  public final void addParticipatingChildViews(List<View> paramList)
  {
    paramList.add(this.mTitle);
    paramList.add(this.mWishlistButton);
    paramList.add(this.mCreatorPanel);
    paramList.add(this.mCreatorBlock);
    paramList.add(this.mContentRatingAppSize);
    paramList.add(this.mContentRatingPanel);
    paramList.add(this.mAppSizeRatingLine);
    paramList.add(this.mTipperSticker);
    paramList.add(this.mBylines);
    paramList.add(this.mExtraLabels);
    paramList.add(this.mExtraLabelsBottom);
    paramList.add(this.mDetailsSummaryDynamic);
  }
  
  public Drawable getBackground()
  {
    if (this.mBackgroundLayer == null) {
      return null;
    }
    return this.mBackgroundLayer.getBackground();
  }
  
  public int getTopPeekAmount()
  {
    if (this.mThumbnailMode == 1) {
      return this.mThumbnailVerticalPeek;
    }
    return 0;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mBackgroundLayer = findViewById(2131755334);
    this.mThumbnailFrame = ((PlayCardThumbnail)findViewById(2131755412));
    this.mTitle = findViewById(2131755262);
    this.mWishlistButton = findViewById(2131755394);
    this.mCreatorPanel = findViewById(2131755395);
    this.mCreatorBlock = findViewById(2131755427);
    this.mContentRatingAppSize = findViewById(2131755397);
    this.mContentRatingPanel = findViewById(2131755398);
    this.mAppSizeRatingLine = findViewById(2131755401);
    this.mTipperSticker = findViewById(2131755402);
    this.mBylines = findViewById(2131755403);
    this.mExtraLabels = findViewById(2131755404);
    this.mExtraLabelsBottom = findViewById(2131755409);
    this.mDetailsSummaryDynamic = findViewById(2131755405);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool;
    int i;
    int j;
    int i41;
    label89:
    int i4;
    label318:
    int i5;
    label822:
    int i6;
    int i17;
    if (ViewCompat.getLayoutDirection(this) == 0)
    {
      bool = true;
      i = getWidth();
      j = getHeight();
      int k = this.mContentMargin;
      int m = getPaddingTop();
      if (this.mThumbnailMode == 2) {
        m += this.mContentMargin;
      }
      if ((this.mThumbnailFrame != null) && (this.mThumbnailFrame.getVisibility() != 8))
      {
        int i40 = this.mThumbnailFrame.getMeasuredWidth();
        if (this.mThumbnailMode != 0) {
          break label1089;
        }
        i41 = 0;
        int i42 = PlayUtils.getViewLeftFromParentStart(i, i40, bool, i41);
        PlayCardThumbnail localPlayCardThumbnail = this.mThumbnailFrame;
        int i43 = i42 + i40;
        int i44 = m + this.mThumbnailFrame.getMeasuredHeight();
        localPlayCardThumbnail.layout(i42, m, i43, i44);
        k += i41 + i40;
      }
      int n = m + getTopPeekAmount();
      if (this.mThumbnailMode != 2) {
        n += this.mContentMargin;
      }
      if (this.mWishlistButton.getVisibility() == 8) {
        break label1098;
      }
      int i36 = this.mWishlistButton.getMeasuredWidth();
      int i37 = this.mContentMargin;
      int i38 = PlayUtils.getViewLeftFromParentEnd(i, i36, bool, i37);
      int i39 = n;
      this.mWishlistButton.layout(i38, i39, i38 + i36, i39 + this.mWishlistButton.getMeasuredHeight());
      this.mWishlistButton.getHitRect(this.mWishlistArea);
      this.mWishlistArea.inset(-this.mWishlistTouchExtend, -this.mWishlistTouchExtend);
      if (!this.mWishlistArea.equals(this.mOldWishlistArea))
      {
        setTouchDelegate(new PlayTouchDelegate(this.mWishlistArea, this.mWishlistButton));
        this.mOldWishlistArea.set(this.mWishlistArea);
      }
      int i1 = n - this.mTitleOffset;
      int i2 = this.mTitle.getMeasuredWidth();
      int i3 = PlayUtils.getViewLeftFromParentStart(i, i2, bool, k);
      this.mTitle.layout(i3, i1, i3 + i2, i1 + this.mTitle.getMeasuredHeight());
      i4 = i1 + this.mTitle.getMeasuredHeight();
      if (this.mCreatorPanel.getVisibility() != 8)
      {
        int i32 = this.mCreatorPanel.getMeasuredWidth();
        int i33 = PlayUtils.getViewLeftFromParentStart(i, i32, bool, k);
        View localView5 = this.mCreatorPanel;
        int i34 = i33 + i32;
        int i35 = i4 + this.mCreatorPanel.getMeasuredHeight();
        localView5.layout(i33, i4, i34, i35);
        i4 += this.mCreatorPanel.getMeasuredHeight();
      }
      if (this.mBylines.getVisibility() == 0)
      {
        int i28 = this.mBylines.getMeasuredWidth();
        int i29 = PlayUtils.getViewLeftFromParentStart(i, i28, bool, k);
        View localView4 = this.mBylines;
        int i30 = i29 + i28;
        int i31 = i4 + this.mBylines.getMeasuredHeight();
        localView4.layout(i29, i4, i30, i31);
        i4 += this.mBylines.getMeasuredHeight();
      }
      if (this.mContentRatingAppSize.getVisibility() != 8)
      {
        int i25 = PlayUtils.getViewLeftFromParentStart(i, this.mContentRatingAppSize.getMeasuredWidth(), bool, k);
        View localView3 = this.mContentRatingAppSize;
        int i26 = i25 + this.mContentRatingAppSize.getMeasuredWidth();
        int i27 = i4 + this.mContentRatingAppSize.getMeasuredHeight();
        localView3.layout(i25, i4, i26, i27);
        i4 += this.mContentRatingAppSize.getMeasuredHeight();
      }
      if (this.mTipperSticker.getVisibility() == 0)
      {
        int i21 = this.mTipperSticker.getMeasuredWidth();
        int i22 = PlayUtils.getViewLeftFromParentStart(i, i21, bool, k);
        View localView2 = this.mTipperSticker;
        int i23 = i22 + i21;
        int i24 = i4 + this.mTipperSticker.getMeasuredHeight();
        localView2.layout(i22, i4, i23, i24);
        i4 += this.mTipperSticker.getMeasuredHeight();
      }
      if (this.mCreatorBlock.getVisibility() != 8)
      {
        int i18 = this.mCreatorBlock.getMeasuredWidth();
        int i19 = PlayUtils.getViewLeftFromParentStart(i, i18, bool, k);
        int i20 = i4 + this.mContentMargin / 2;
        this.mCreatorBlock.layout(i19, i20, i19 + i18, i20 + this.mCreatorBlock.getMeasuredHeight());
        i4 = i20 + this.mCreatorBlock.getMeasuredHeight();
      }
      if (!this.mIsExtraLabelsInCompactMode) {
        break label1113;
      }
      i5 = i4 - this.mExtraLabels.getMeasuredHeight();
      i6 = j - getPaddingBottom();
      if (this.mExtraLabelsBottom.getVisibility() == 8) {
        break label1120;
      }
      i17 = i6 - this.mExtraLabelsBottom.getPaddingTop();
      this.mExtraLabelsBottom.layout(0, i17 - this.mExtraLabelsBottom.getMeasuredHeight(), this.mExtraLabelsBottom.getMeasuredWidth(), i17);
    }
    label1089:
    label1098:
    label1113:
    label1120:
    for (int i7 = i17 - this.mExtraLabelsBottom.getMeasuredHeight();; i7 = i6 - this.mContentMargin)
    {
      if (this.mDetailsSummaryDynamic.getVisibility() != 8)
      {
        int i13 = this.mDetailsSummaryDynamic.getMeasuredHeight();
        int i14 = this.mDetailsSummaryDynamic.getMeasuredWidth();
        int i15 = this.mContentMargin;
        int i16 = PlayUtils.getViewLeftFromParentEnd(i, i14, bool, i15);
        this.mDetailsSummaryDynamic.layout(i16, i7 - i13, i16 + i14, i7);
        i5 = i7 - i13 - this.mExtraLabels.getMeasuredHeight();
      }
      if (this.mExtraLabels.getVisibility() != 8)
      {
        int i8 = this.mExtraLabels.getMeasuredWidth();
        int i9 = this.mContentMargin;
        int i10 = PlayUtils.getViewLeftFromParentEnd(i, i8, bool, i9);
        View localView1 = this.mExtraLabels;
        int i11 = i10 + i8;
        int i12 = i5 + this.mExtraLabels.getMeasuredHeight();
        localView1.layout(i10, i5, i11, i12);
      }
      if (this.mBackgroundLayer.getVisibility() != 8) {
        this.mBackgroundLayer.layout(0, 0, i, j);
      }
      return;
      bool = false;
      break;
      i41 = this.mContentMargin;
      break label89;
      this.mOldWishlistArea.setEmpty();
      setTouchDelegate(null);
      break label318;
      i5 = i4;
      break label822;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = i - 2 * this.mContentMargin;
    int k = getTopPeekAmount() + this.mContentMargin;
    PlayCardThumbnail localPlayCardThumbnail = this.mThumbnailFrame;
    int m = 0;
    if (localPlayCardThumbnail != null)
    {
      int i15 = this.mThumbnailFrame.getVisibility();
      m = 0;
      if (i15 != 8)
      {
        ViewGroup.LayoutParams localLayoutParams = this.mThumbnailFrame.getLayoutParams();
        this.mThumbnailFrame.measure(View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, 1073741824), View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824));
        j -= this.mThumbnailFrame.getMeasuredWidth();
        m = localLayoutParams.height;
        if (this.mThumbnailMode != 0) {
          j -= this.mContentMargin;
        }
        if (this.mThumbnailMode == 2) {
          m += this.mContentMargin;
        }
      }
    }
    int n = j;
    if (this.mWishlistButton.getVisibility() != 8)
    {
      this.mWishlistButton.measure(0, 0);
      n -= this.mWishlistButton.getMeasuredWidth();
    }
    this.mTitle.measure(View.MeasureSpec.makeMeasureSpec(n, 1073741824), 0);
    int i1 = k + (this.mTitle.getMeasuredHeight() - this.mTitleOffset);
    View localView1 = this.mTitle;
    if (this.mCreatorPanel.getVisibility() != 8)
    {
      this.mCreatorPanel.measure(View.MeasureSpec.makeMeasureSpec(j, -2147483648), 0);
      i1 += this.mCreatorPanel.getMeasuredHeight();
      localView1 = this.mCreatorPanel;
    }
    if (this.mBylines.getVisibility() == 0)
    {
      this.mBylines.measure(View.MeasureSpec.makeMeasureSpec(j, -2147483648), 0);
      i1 += this.mBylines.getMeasuredHeight();
      localView1 = this.mBylines;
    }
    if (this.mContentRatingAppSize.getVisibility() != 8)
    {
      View localView2 = this.mContentRatingAppSize.findViewById(2131755398);
      if (localView2.findViewById(2131755399).getVisibility() == 8)
      {
        View localView3 = localView2.findViewById(2131755400);
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localView3.getLayoutParams();
        MarginLayoutParamsCompat.setMarginStart(localMarginLayoutParams, 0);
        localView3.setLayoutParams(localMarginLayoutParams);
      }
      this.mContentRatingAppSize.measure(View.MeasureSpec.makeMeasureSpec(j, -2147483648), 0);
      i1 += this.mContentRatingAppSize.getMeasuredHeight();
      localView1 = this.mContentRatingAppSize;
    }
    if (this.mTipperSticker.getVisibility() != 8)
    {
      this.mTipperSticker.measure(View.MeasureSpec.makeMeasureSpec(j, -2147483648), 0);
      i1 += this.mTipperSticker.getMeasuredHeight();
      localView1 = this.mTipperSticker;
    }
    if (this.mCreatorBlock.getVisibility() != 8)
    {
      this.mCreatorBlock.measure(View.MeasureSpec.makeMeasureSpec(j, -2147483648), 0);
      i1 += this.mCreatorBlock.getMeasuredHeight() + this.mContentMargin / 2;
      localView1 = this.mCreatorBlock;
    }
    boolean bool;
    label594:
    int i5;
    int i6;
    int i2;
    if (this.mExtraLabels.getVisibility() == 0)
    {
      this.mExtraLabels.measure(View.MeasureSpec.makeMeasureSpec(j, -2147483648), 0);
      if (localView1.getMeasuredWidth() + this.mExtraLabels.getMeasuredWidth() <= j)
      {
        bool = true;
        this.mIsExtraLabelsInCompactMode = bool;
        if (!this.mIsExtraLabelsInCompactMode) {
          break label812;
        }
        i1 += this.mExtraLabels.getMeasuredHeight() - localView1.getMeasuredHeight();
      }
    }
    else
    {
      if (this.mDetailsSummaryDynamic.getVisibility() == 8) {
        break label1013;
      }
      i5 = i - 2 * this.mContentMargin;
      this.mDetailsSummaryDynamic.measure(View.MeasureSpec.makeMeasureSpec(i5, -2147483648), 0);
      i6 = this.mDetailsSummaryDynamic.getMeasuredWidth();
      int i7 = this.mDetailsSummaryDynamic.getMeasuredHeight();
      int i8 = m;
      if (this.mThumbnailMode != 0) {
        i8 -= this.mContentMargin;
      }
      if ((i6 > j) || (i1 + i7 > i8)) {
        break label827;
      }
      this.mDetailsSummaryDynamic.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), 0);
      i2 = m;
      label706:
      if (this.mExtraLabelsBottom.getVisibility() == 8) {
        break label1025;
      }
      this.mExtraLabelsBottom.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), 0);
    }
    label1025:
    for (int i3 = i2 + this.mExtraLabelsBottom.getMeasuredHeight() + this.mExtraLabelsBottom.getPaddingTop();; i3 = i2 + this.mContentMargin)
    {
      int i4 = i3 + (getPaddingTop() + getPaddingBottom());
      if (this.mBackgroundLayer.getVisibility() != 8) {
        this.mBackgroundLayer.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(i4, 1073741824));
      }
      setMeasuredDimension(i, i4);
      return;
      bool = false;
      break;
      label812:
      i1 += this.mExtraLabels.getMeasuredHeight();
      break label594;
      label827:
      int i9 = 1;
      int i10 = this.mCreatorBlock.getVisibility();
      i2 = 0;
      if (i10 == 0)
      {
        int i11 = this.mBylines.getVisibility();
        i2 = 0;
        if (i11 == 8)
        {
          int i12 = this.mExtraLabels.getVisibility();
          i2 = 0;
          if (i12 == 8)
          {
            int i13 = this.mCreatorBlock.getMeasuredWidth();
            int i14 = i13 + i6;
            i2 = 0;
            if (i14 <= j)
            {
              this.mDetailsSummaryDynamic.measure(View.MeasureSpec.makeMeasureSpec(j - i13, 1073741824), 0);
              i2 = Math.max(i1, m);
              i9 = 0;
            }
          }
        }
      }
      if (i9 == 0) {
        break label706;
      }
      this.mDetailsSummaryDynamic.measure(View.MeasureSpec.makeMeasureSpec(i5, 1073741824), 0);
      i2 = Math.max(i1, m) + this.mContentMargin + this.mDetailsSummaryDynamic.getMeasuredHeight();
      if ((this.mThumbnailFrame == null) || (this.mThumbnailMode != 2)) {
        break label706;
      }
      i2 -= this.mThumbnailFrame.getPaddingBottom();
      break label706;
      label1013:
      i2 = Math.max(i1, m);
      break label706;
    }
  }
  
  @TargetApi(16)
  public void setBackground(Drawable paramDrawable)
  {
    if (this.mBackgroundLayer != null) {
      this.mBackgroundLayer.setBackground(paramDrawable);
    }
  }
  
  public void setBackgroundColor(int paramInt)
  {
    if (this.mBackgroundLayer != null) {
      this.mBackgroundLayer.setBackgroundColor(paramInt);
    }
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    if (this.mBackgroundLayer != null) {
      this.mBackgroundLayer.setBackgroundDrawable(paramDrawable);
    }
  }
  
  public void setBackgroundResource(int paramInt)
  {
    if (this.mBackgroundLayer != null) {
      this.mBackgroundLayer.setBackgroundResource(paramInt);
    }
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mBackgroundLayer != null) {
      this.mBackgroundLayer.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public void setPaddingRelative(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mBackgroundLayer != null) {
      ViewCompat.setPaddingRelative(this.mBackgroundLayer, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public void setThumbnailMode(int paramInt)
  {
    int i = 1;
    int j;
    if (this.mThumbnailMode != paramInt)
    {
      this.mThumbnailMode = paramInt;
      j = getResources().getColor(2131689753);
      if (this.mThumbnailMode != i) {
        break label77;
      }
      if (i == 0) {
        break label82;
      }
      setBackgroundDrawable(new InsetDrawable(new PaintDrawable(j), 0, this.mThumbnailVerticalPeek, 0, 0));
    }
    for (;;)
    {
      ViewCompat.setPaddingRelative(this, 0, 0, 0, 0);
      requestLayout();
      return;
      label77:
      i = 0;
      break;
      label82:
      setBackgroundColor(j);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsSummary
 * JD-Core Version:    0.7.0.1
 */