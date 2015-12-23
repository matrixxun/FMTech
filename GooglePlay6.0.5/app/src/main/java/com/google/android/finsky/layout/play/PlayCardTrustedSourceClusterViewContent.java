package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.play.image.FifeImageView;

public class PlayCardTrustedSourceClusterViewContent
  extends PlayCardClusterViewContent
{
  PlayCirclesButton mCirclesButton;
  Document mDocument;
  private boolean mHasTallCover;
  PersonAvatarView mProfileAvatarImage;
  FifeImageView mProfileCoverImage;
  private FrameLayout mProfileCoverImageFrame;
  private View mProfileInfoBlock;
  TextView mProfileSubtitle;
  TextView mProfileTitle;
  private final boolean mShouldLayoutVertically;
  
  public PlayCardTrustedSourceClusterViewContent(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardTrustedSourceClusterViewContent(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mShouldLayoutVertically = paramContext.getResources().getBoolean(2131427341);
  }
  
  protected int getIndexOfFirstCard()
  {
    return 2;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mProfileCoverImageFrame = ((FrameLayout)findViewById(2131755843));
    this.mProfileCoverImage = ((FifeImageView)findViewById(2131755844));
    this.mProfileInfoBlock = findViewById(2131755845);
    this.mProfileAvatarImage = ((PersonAvatarView)this.mProfileInfoBlock.findViewById(2131755888));
    this.mProfileTitle = ((TextView)this.mProfileInfoBlock.findViewById(2131755847));
    this.mProfileSubtitle = ((TextView)this.mProfileInfoBlock.findViewById(2131755848));
    this.mCirclesButton = ((PlayCirclesButton)this.mProfileInfoBlock.findViewById(2131755213));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    int i = getWidth();
    int j = this.mProfileInfoBlock.getMeasuredWidth();
    int k = this.mProfileInfoBlock.getMeasuredHeight();
    int m = this.mProfileAvatarImage.getMeasuredWidth();
    int n = this.mCardContentPaddingTop + (int)(0.5F * m);
    this.mProfileCoverImageFrame.layout(0, n, i, n + this.mProfileCoverImageFrame.getMeasuredHeight());
    boolean bool = this.mShouldLayoutVertically;
    int i1 = 0;
    if (bool) {}
    for (;;)
    {
      this.mProfileInfoBlock.layout(i1, this.mCardContentPaddingTop, i1 + j, k + this.mCardContentPaddingTop);
      return;
      i1 = this.mCardContentHorizontalPadding;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getMeasuredHeight();
    if (j == 0) {}
    int n;
    int i7;
    for (int k = 0;; k = j - this.mCardContentPaddingTop - this.mCardContentPaddingBottom)
    {
      int m = k;
      n = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
      if (!this.mShouldLayoutVertically) {
        break;
      }
      this.mProfileInfoBlock.measure(n, 0);
      int i8 = this.mProfileInfoBlock.getMeasuredHeight();
      int i9 = i8 - (int)(0.5F * this.mProfileAvatarImage.getMeasuredHeight()) + (int)(0.85F * k);
      this.mProfileCoverImageFrame.measure(n, View.MeasureSpec.makeMeasureSpec(i9, 1073741824));
      i7 = m + i8;
      if (k > 0) {
        i7 += this.mCardContentPaddingTop + this.mCardContentPaddingBottom;
      }
      setMeasuredDimension(i, i7);
      return;
    }
    int i1 = View.MeasureSpec.makeMeasureSpec(getLeadingGap(i) + (int)(getExtraColumnOffset() * getCellSize(i)), 1073741824);
    this.mProfileInfoBlock.measure(i1, 0);
    int i2 = this.mProfileAvatarImage.getMeasuredHeight();
    int i3 = this.mProfileInfoBlock.getMeasuredHeight() - (int)(0.5F * i2);
    if (this.mHasTallCover) {}
    for (int i4 = (int)(0.85F * k) - (int)(0.5F * i2);; i4 = (int)(0.0F * i2) + (int)(0.85F * k))
    {
      int i5 = Math.max(i3, i4);
      this.mProfileCoverImageFrame.measure(n, View.MeasureSpec.makeMeasureSpec(i5, 1073741824));
      int i6 = i5 + (int)(0.5F * i2);
      this.mProfileInfoBlock.measure(i1, View.MeasureSpec.makeMeasureSpec(i6, 1073741824));
      i7 = i6 + (int)(0.15F * k);
      break;
    }
  }
  
  public final void onRecycle()
  {
    super.onRecycle();
    this.mProfileAvatarImage.clearImage();
    this.mProfileCoverImage.clearImage();
  }
  
  public final void setMetadata(PlayCardClusterMetadata paramPlayCardClusterMetadata, ClientMutationCache paramClientMutationCache)
  {
    super.setMetadata(paramPlayCardClusterMetadata, paramClientMutationCache);
    int i = Math.min(this.mMetadata.getTileCount(), getDocCount());
    this.mHasTallCover = false;
    for (int j = 0;; j++) {
      if (j < i)
      {
        if (PlayCardClusterMetadata.getAspectRatio(getDoc(j).mDocument.docType) == 1.441F) {
          this.mHasTallCover = true;
        }
      }
      else {
        return;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardTrustedSourceClusterViewContent
 * JD-Core Version:    0.7.0.1
 */