package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.protos.Browse.QuickLink;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.AvatarCropTransformation;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;

public class PlayQuickLinksBannerCircleItemViewLarge
  extends PlayQuickLinksBannerItemBaseView
  implements Recyclable
{
  public PlayQuickLinksBannerCircleItemViewLarge(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayQuickLinksBannerCircleItemViewLarge(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlayQuickLinksBannerCircleItemViewLarge(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public final void bindData(Browse.QuickLink paramQuickLink, BitmapLoader paramBitmapLoader, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.bindData(paramQuickLink, paramBitmapLoader, paramPlayStoreUiElementNode);
    this.mFillColor = UiUtils.getFillColor(paramQuickLink.image, this.mFillColor);
    AvatarCropTransformation localAvatarCropTransformation = AvatarCropTransformation.getFullAvatarCrop(getResources(), this.mFillColor);
    this.mIconView.setBitmapTransformation(localAvatarCropTransformation);
  }
  
  public final void onRecycle()
  {
    this.mIconView.clearImage();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayQuickLinksBannerCircleItemViewLarge
 * JD-Core Version:    0.7.0.1
 */