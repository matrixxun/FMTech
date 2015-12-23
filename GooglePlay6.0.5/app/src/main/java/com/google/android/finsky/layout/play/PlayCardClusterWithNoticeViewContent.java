package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.play.image.FifeImageView;

public class PlayCardClusterWithNoticeViewContent
  extends PlayCardClusterViewContent
{
  TextView mActionButton;
  private float mCoverCardsOverlap;
  FifeImageView mCoverImage;
  private FrameLayout mCoverImageFrame;
  private View mProfileInfoBlock;
  private boolean mShouldLayoutVertically;
  TextView mSubtitle;
  TextView mTitle;
  
  public PlayCardClusterWithNoticeViewContent(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardClusterWithNoticeViewContent(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mShouldLayoutVertically = paramContext.getResources().getBoolean(2131427331);
  }
  
  protected int getIndexOfFirstCard()
  {
    return 2;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mCoverImageFrame = ((FrameLayout)findViewById(2131755852));
    this.mCoverImage = ((FifeImageView)findViewById(2131755853));
    this.mProfileInfoBlock = findViewById(2131755854);
    this.mTitle = ((TextView)this.mProfileInfoBlock.findViewById(2131755173));
    this.mSubtitle = ((TextView)this.mProfileInfoBlock.findViewById(2131755291));
    this.mActionButton = ((TextView)this.mProfileInfoBlock.findViewById(2131755213));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    int i = getWidth();
    this.mCoverImageFrame.layout(0, 0, i, this.mCoverImageFrame.getMeasuredHeight());
    int j = this.mProfileInfoBlock.getMeasuredWidth();
    int k = this.mProfileInfoBlock.getMeasuredHeight();
    boolean bool = this.mShouldLayoutVertically;
    int m = 0;
    if (bool) {}
    for (;;)
    {
      this.mProfileInfoBlock.layout(m, this.mCardContentPaddingTop, m + j, k + this.mCardContentPaddingTop);
      return;
      m = this.mCardContentHorizontalPadding;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getMeasuredHeight();
    int k;
    int n;
    int i3;
    if (j == 0)
    {
      k = 0;
      int m = k;
      n = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
      if (!this.mShouldLayoutVertically) {
        break label144;
      }
      this.mProfileInfoBlock.measure(n, 0);
      int i4 = this.mProfileInfoBlock.getMeasuredHeight();
      int i5 = i4 + (int)(k * this.mCoverCardsOverlap);
      this.mCoverImageFrame.measure(n, View.MeasureSpec.makeMeasureSpec(i5, 1073741824));
      i3 = m + i4;
      if (k > 0) {
        i3 += this.mCardContentPaddingTop + this.mCardContentPaddingBottom;
      }
    }
    for (;;)
    {
      setMeasuredDimension(i, i3);
      return;
      k = j - this.mCardContentPaddingTop - this.mCardContentPaddingBottom;
      break;
      label144:
      int i1 = View.MeasureSpec.makeMeasureSpec(getLeadingGap(i) + (int)(getExtraColumnOffset() * getCellSize(i)), 1073741824);
      this.mProfileInfoBlock.measure(i1, 0);
      if (k > 0) {
        k += this.mCardContentPaddingTop + this.mCardContentPaddingBottom;
      }
      int i2 = (int)(k * this.mCoverCardsOverlap);
      this.mCoverImageFrame.measure(n, View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
      this.mProfileInfoBlock.measure(i1, View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
      i3 = i2 + (int)(k * (1.0F - this.mCoverCardsOverlap));
    }
  }
  
  public final void onRecycle()
  {
    super.onRecycle();
    this.mCoverImage.clearImage();
  }
  
  public final void setMetadata(PlayCardClusterMetadata paramPlayCardClusterMetadata, ClientMutationCache paramClientMutationCache)
  {
    super.setMetadata(paramPlayCardClusterMetadata, paramClientMutationCache);
    int i = Math.min(this.mMetadata.getTileCount(), getDocCount());
    boolean bool;
    if ((this.mShouldLayoutVertically) && (i == 3))
    {
      bool = true;
      this.mShouldLayoutVertically = bool;
      if (!this.mShouldLayoutVertically) {
        break label80;
      }
    }
    label80:
    for (float f = 0.85F;; f = 1.0F)
    {
      this.mCoverCardsOverlap = f;
      if (this.mCoverCardsOverlap < 1.0F) {
        this.mCardContentPaddingBottom = 0;
      }
      return;
      bool = false;
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardClusterWithNoticeViewContent
 * JD-Core Version:    0.7.0.1
 */