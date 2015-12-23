package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.finsky.layout.FadingEdgeImageView;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;

public class PlayCardMerchClusterView
  extends PlayCardClusterView
{
  private final int mContentVerticalMargin;
  private final int mContentVerticalPadding;
  
  public PlayCardMerchClusterView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardMerchClusterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mContentVerticalMargin = localResources.getDimensionPixelSize(2131493431);
    this.mContentVerticalPadding = localResources.getDimensionPixelSize(2131493432);
  }
  
  public final void configureMerch(BitmapLoader paramBitmapLoader, int paramInt, Common.Image paramImage, String paramString, View.OnClickListener paramOnClickListener)
  {
    int i = 1;
    PlayCardMerchClusterViewContent localPlayCardMerchClusterViewContent = (PlayCardMerchClusterViewContent)this.mContent;
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localPlayCardMerchClusterViewContent.getLayoutParams();
    localMarginLayoutParams.topMargin = this.mContentVerticalMargin;
    localMarginLayoutParams.bottomMargin = this.mContentVerticalMargin;
    localPlayCardMerchClusterViewContent.setCardContentVerticalPadding(this.mContentVerticalPadding);
    if ((paramInt != 0) && (paramInt != i)) {
      throw new IllegalArgumentException("Merch image position " + paramInt + " is not supported");
    }
    localPlayCardMerchClusterViewContent.mMerchColor = UiUtils.getFillColor(paramImage, localPlayCardMerchClusterViewContent.mFallbackMerchColor);
    localPlayCardMerchClusterViewContent.mMerchImagePosition = paramInt;
    localPlayCardMerchClusterViewContent.mMerchImage.setOnLoadedListener(localPlayCardMerchClusterViewContent);
    localPlayCardMerchClusterViewContent.mMerchImage.setImage(paramImage.imageUrl, paramImage.supportsFifeUrlOptions, paramBitmapLoader);
    label173:
    FadingEdgeImageView localFadingEdgeImageView2;
    if (localPlayCardMerchClusterViewContent.mMerchImage.getDrawable() != null)
    {
      localPlayCardMerchClusterViewContent.configureImageFadingEdge();
      localPlayCardMerchClusterViewContent.mMerchImage.setOnClickListener(paramOnClickListener);
      FadingEdgeImageView localFadingEdgeImageView1 = localPlayCardMerchClusterViewContent.mMerchImage;
      if (paramOnClickListener == null) {
        break label221;
      }
      localFadingEdgeImageView1.setClickable(i);
      localFadingEdgeImageView2 = localPlayCardMerchClusterViewContent.mMerchImage;
      if (paramOnClickListener == null) {
        break label227;
      }
    }
    for (;;)
    {
      localFadingEdgeImageView2.setContentDescription(paramString);
      localPlayCardMerchClusterViewContent.setBackgroundColor(localPlayCardMerchClusterViewContent.mMerchColor);
      return;
      localPlayCardMerchClusterViewContent.mMerchImage.clearFadingEdges();
      break;
      label221:
      i = 0;
      break label173;
      label227:
      paramString = null;
    }
  }
  
  public final void configureNoMerch()
  {
    PlayCardMerchClusterViewContent localPlayCardMerchClusterViewContent = (PlayCardMerchClusterViewContent)this.mContent;
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localPlayCardMerchClusterViewContent.getLayoutParams();
    localMarginLayoutParams.topMargin = 0;
    localMarginLayoutParams.bottomMargin = 0;
    localPlayCardMerchClusterViewContent.setCardContentVerticalPadding(0);
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 407;
  }
  
  public final void onRecycle()
  {
    super.onRecycle();
    ((PlayCardMerchClusterViewContent)this.mContent).onRecycle();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardMerchClusterView
 * JD-Core Version:    0.7.0.1
 */