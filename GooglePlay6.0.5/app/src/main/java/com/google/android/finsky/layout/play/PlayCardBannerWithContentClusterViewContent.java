package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.play.image.FifeImageView;

public class PlayCardBannerWithContentClusterViewContent
  extends PlayCardClusterViewContent
  implements Recyclable
{
  FifeImageView mBannerImage;
  private int mControlsBottomPadding;
  private int mControlsSidePadding;
  final int mFallbackBannerColor;
  private int mImageHeight;
  TextView mMoreButton;
  TextView mTitle;
  
  public PlayCardBannerWithContentClusterViewContent(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardBannerWithContentClusterViewContent(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mFallbackBannerColor = paramContext.getResources().getColor(2131689644);
    Resources localResources = getResources();
    this.mImageHeight = localResources.getDimensionPixelSize(2131492982);
    this.mControlsBottomPadding = this.mCardContentPaddingTop;
    this.mControlsSidePadding = localResources.getDimensionPixelSize(2131493080);
    this.mCardContentPaddingTop = (2 * this.mImageHeight / 3);
  }
  
  protected int getIndexOfFirstCard()
  {
    return 3;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mBannerImage = ((FifeImageView)findViewById(2131755849));
    this.mTitle = ((TextView)findViewById(2131755173));
    this.mMoreButton = ((TextView)findViewById(2131755361));
    this.mMoreButton.setText(getResources().getString(2131362331).toUpperCase());
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mBannerImage.layout(0, 0, this.mBannerImage.getMeasuredWidth(), this.mBannerImage.getMeasuredHeight());
    int i = this.mCardContentHorizontalPadding + this.mControlsSidePadding;
    int j = this.mCardContentPaddingTop - this.mControlsBottomPadding;
    int k = getWidth() - i;
    this.mMoreButton.layout(k - this.mMoreButton.getMeasuredWidth(), j - this.mMoreButton.getMeasuredHeight(), k, j);
    this.mTitle.layout(i, j - this.mTitle.getMeasuredHeight(), i + this.mTitle.getMeasuredWidth(), j);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = this.mCardContentHorizontalPadding + this.mControlsSidePadding;
    this.mBannerImage.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mImageHeight, 1073741824));
    this.mMoreButton.measure(0, 0);
    int k = i - j * 2 - this.mMoreButton.getMeasuredWidth();
    this.mTitle.measure(View.MeasureSpec.makeMeasureSpec(k, -2147483648), 0);
  }
  
  public final void onRecycle()
  {
    this.mBannerImage.clearImage();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardBannerWithContentClusterViewContent
 * JD-Core Version:    0.7.0.1
 */