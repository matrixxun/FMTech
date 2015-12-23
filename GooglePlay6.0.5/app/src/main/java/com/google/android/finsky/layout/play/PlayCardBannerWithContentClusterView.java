package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;

public class PlayCardBannerWithContentClusterView
  extends PlayCardClusterView
{
  public PlayCardBannerWithContentClusterView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardBannerWithContentClusterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void configure(BitmapLoader paramBitmapLoader, Common.Image paramImage, String paramString, View.OnClickListener paramOnClickListener)
  {
    PlayCardBannerWithContentClusterViewContent localPlayCardBannerWithContentClusterViewContent = (PlayCardBannerWithContentClusterViewContent)this.mContent;
    int i = UiUtils.getFillColor(paramImage, localPlayCardBannerWithContentClusterViewContent.mFallbackBannerColor);
    localPlayCardBannerWithContentClusterViewContent.mBannerImage.setBackgroundColor(i);
    localPlayCardBannerWithContentClusterViewContent.mBannerImage.setImage(paramImage.imageUrl, paramImage.supportsFifeUrlOptions, paramBitmapLoader);
    localPlayCardBannerWithContentClusterViewContent.mBannerImage.setOnClickListener(paramOnClickListener);
    FifeImageView localFifeImageView1 = localPlayCardBannerWithContentClusterViewContent.mBannerImage;
    boolean bool;
    FifeImageView localFifeImageView2;
    if (paramOnClickListener != null)
    {
      bool = true;
      localFifeImageView1.setClickable(bool);
      localFifeImageView2 = localPlayCardBannerWithContentClusterViewContent.mBannerImage;
      if (paramOnClickListener == null) {
        break label127;
      }
    }
    label127:
    for (String str = paramString;; str = null)
    {
      localFifeImageView2.setContentDescription(str);
      localPlayCardBannerWithContentClusterViewContent.mTitle.setText(paramString);
      localPlayCardBannerWithContentClusterViewContent.mMoreButton.setOnClickListener(paramOnClickListener);
      return;
      bool = false;
      break;
    }
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 422;
  }
  
  public final void onRecycle()
  {
    super.onRecycle();
    ((PlayCardBannerWithContentClusterViewContent)this.mContent).onRecycle();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardBannerWithContentClusterView
 * JD-Core Version:    0.7.0.1
 */