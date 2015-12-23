package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.finsky.layout.FadingEdgeImageView;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.image.FifeImageView.OnLoadedListener;

public class PlayCardMerchClusterViewV2
  extends PlayCardClusterViewV2
  implements FifeImageView.OnLoadedListener
{
  private final int mContentVerticalMargin;
  private final int mContentVerticalPadding;
  public final int mFallbackMerchColor;
  public int mMerchColor;
  public View mMerchFill;
  public FadingEdgeImageView mMerchImage;
  
  public PlayCardMerchClusterViewV2(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardMerchClusterViewV2(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mContentVerticalMargin = localResources.getDimensionPixelSize(2131493431);
    this.mContentVerticalPadding = localResources.getDimensionPixelSize(2131493432);
    this.mFallbackMerchColor = paramContext.getResources().getColor(2131689644);
  }
  
  public final void configureImageFadingEdge()
  {
    this.mMerchImage.configureFadingEdges(false, true, this.mMerchImage.getMeasuredWidth() / 4, this.mMerchColor);
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 407;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mMerchFill = findViewById(2131755867);
    this.mMerchImage = ((FadingEdgeImageView)findViewById(2131755866));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getHeight();
    int k = getPaddingTop();
    if ((this.mHeader != null) && (this.mHeader.getVisibility() != 8))
    {
      this.mHeader.layout(0, k, i, k + this.mHeader.getMeasuredHeight());
      k += this.mHeader.getMeasuredHeight();
    }
    int m;
    int n;
    int i1;
    if (this.mMerchImage.getVisibility() != 8)
    {
      m = k + this.mContentVerticalMargin;
      this.mMerchFill.layout(0, m, i, m + this.mMerchFill.getMeasuredHeight());
      n = this.mMerchImage.getMeasuredWidth();
      i1 = this.mMerchImage.getMeasuredHeight();
      if (n <= 0) {
        break label216;
      }
      int i2 = i1 * 3 / 4;
      int i3 = this.mContent.getLeadingPixelGap(i, j) / 2 - i2;
      this.mMerchImage.layout(i3, m, i3 + n, m + i1);
    }
    for (;;)
    {
      k = m + this.mContentVerticalPadding;
      this.mContent.layout(0, k, i, k + this.mContent.getMeasuredHeight());
      return;
      label216:
      this.mMerchImage.layout(0, m, n, m + i1);
    }
  }
  
  public final void onLoaded(FifeImageView paramFifeImageView, Bitmap paramBitmap)
  {
    configureImageFadingEdge();
  }
  
  public final void onLoadedAndFadedIn(FifeImageView paramFifeImageView) {}
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getPaddingTop() + getPaddingBottom();
    if ((this.mHeader != null) && (this.mHeader.getVisibility() != 8))
    {
      this.mHeader.measure(paramInt1, 0);
      j += this.mHeader.getMeasuredHeight();
    }
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mContent.getLayoutParams();
    this.mContent.measure(paramInt1, 0);
    int m;
    if (this.mMerchImage.getVisibility() != 8)
    {
      m = this.mContent.getMeasuredHeight() + 2 * this.mContentVerticalPadding;
      int n = View.MeasureSpec.makeMeasureSpec(m, 1073741824);
      this.mMerchFill.measure(paramInt1, n);
      int i1 = (int)(1.777778F * m);
      this.mMerchImage.measure(View.MeasureSpec.makeMeasureSpec(i1, 1073741824), n);
      if (this.mMerchImage.isLoaded()) {
        configureImageFadingEdge();
      }
    }
    for (int k = j + (m + 2 * this.mContentVerticalMargin);; k = j + (localMarginLayoutParams.topMargin + this.mContent.getMeasuredHeight() + localMarginLayoutParams.bottomMargin))
    {
      setMeasuredDimension(i, k);
      return;
      this.mMerchImage.clearFadingEdges();
      break;
    }
  }
  
  public final void onRecycle()
  {
    super.onRecycle();
    this.mMerchImage.clearImage();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardMerchClusterViewV2
 * JD-Core Version:    0.7.0.1
 */