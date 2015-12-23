package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.layout.FadingEdgeImageView;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.image.FifeImageView.OnLoadedListener;

public class PlayCardMerchClusterViewContent
  extends PlayCardClusterViewContent
  implements Recyclable, FifeImageView.OnLoadedListener
{
  final int mFallbackMerchColor;
  int mMerchColor;
  FadingEdgeImageView mMerchImage;
  int mMerchImagePosition;
  
  public PlayCardMerchClusterViewContent(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardMerchClusterViewContent(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mFallbackMerchColor = paramContext.getResources().getColor(2131689644);
  }
  
  final void configureImageFadingEdge()
  {
    boolean bool1;
    if (this.mMerchImagePosition == 1)
    {
      bool1 = true;
      if (this.mMerchImagePosition != 0) {
        break label47;
      }
    }
    label47:
    for (boolean bool2 = true;; bool2 = false)
    {
      this.mMerchImage.configureFadingEdges(bool1, bool2, this.mMerchImage.getMeasuredWidth() / 4, this.mMerchColor);
      return;
      bool1 = false;
      break;
    }
  }
  
  protected int getIndexOfFirstCard()
  {
    return 1;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mMerchImage = ((FadingEdgeImageView)findViewById(2131755866));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    layoutContent(true);
    int i = getWidth();
    int j = getHeight();
    int k = this.mMerchImage.getMeasuredWidth();
    int m = this.mMerchImage.getMeasuredHeight();
    if (k > 0)
    {
      int n = m * 3 / 4;
      int i1;
      if (this.mMerchImagePosition == 0) {
        i1 = getLeadingGap(i) / 2 - n;
      }
      for (;;)
      {
        this.mMerchImage.layout(i1, 0, i1 + k, j);
        return;
        i1 = i - getTrailingGap(i) / 2 - n;
        int i2 = i1 + k;
        if (i2 < i) {
          i1 += i - i2;
        }
      }
    }
    this.mMerchImage.layout(0, 0, k, j);
  }
  
  public final void onLoaded(FifeImageView paramFifeImageView, Bitmap paramBitmap)
  {
    configureImageFadingEdge();
  }
  
  public final void onLoadedAndFadedIn(FifeImageView paramFifeImageView) {}
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = getMeasuredHeight();
    int j = (int)(1.777778F * i);
    this.mMerchImage.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), View.MeasureSpec.makeMeasureSpec(i, 1073741824));
    if (this.mMerchImage.isLoaded())
    {
      configureImageFadingEdge();
      return;
    }
    this.mMerchImage.clearFadingEdges();
  }
  
  public final void onRecycle()
  {
    this.mMerchImage.clearImage();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardMerchClusterViewContent
 * JD-Core Version:    0.7.0.1
 */