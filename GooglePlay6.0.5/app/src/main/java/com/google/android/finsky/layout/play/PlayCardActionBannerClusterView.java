package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import java.util.List;

public class PlayCardActionBannerClusterView
  extends PlayCardClusterView
{
  private final int mContentVerticalMargin;
  
  public PlayCardActionBannerClusterView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardActionBannerClusterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContentVerticalMargin = paramContext.getResources().getDimensionPixelSize(2131493431);
  }
  
  public final void configureExtraContent(NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, Document paramDocument, DocV2 paramDocV2, DocV2[] paramArrayOfDocV2, String paramString, PlayStoreUiElementNode paramPlayStoreUiElementNode, View.OnClickListener paramOnClickListener)
  {
    PlayCardActionBannerClusterViewContent localPlayCardActionBannerClusterViewContent = (PlayCardActionBannerClusterViewContent)this.mContent;
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localPlayCardActionBannerClusterViewContent.getLayoutParams();
    localMarginLayoutParams.topMargin = this.mContentVerticalMargin;
    localMarginLayoutParams.bottomMargin = this.mContentVerticalMargin;
    localPlayCardActionBannerClusterViewContent.mDocument = paramDocument;
    localPlayCardActionBannerClusterViewContent.mAvatarPack.setData(paramDocV2, paramArrayOfDocV2, paramNavigationManager, paramPlayStoreUiElementNode);
    Common.Image localImage = (Common.Image)localPlayCardActionBannerClusterViewContent.mDocument.getImages(14).get(0);
    localPlayCardActionBannerClusterViewContent.mProfileCoverImage.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
    localPlayCardActionBannerClusterViewContent.mProfileCoverImage.setOnClickListener(paramOnClickListener);
    localPlayCardActionBannerClusterViewContent.mProfileCoverImage.setContentDescription(paramString);
    ViewCompat.setImportantForAccessibility(localPlayCardActionBannerClusterViewContent.mProfileCoverImage, 2);
    localPlayCardActionBannerClusterViewContent.mProfileTitle.setText(paramDocument.mDocument.title);
    localPlayCardActionBannerClusterViewContent.mProfileSubtitle.setText(paramDocument.mDocument.subtitle);
    if (paramString != null)
    {
      localPlayCardActionBannerClusterViewContent.mExploreButton.setVisibility(0);
      localPlayCardActionBannerClusterViewContent.mExploreButton.setText(paramString);
      localPlayCardActionBannerClusterViewContent.mExploreButton.setOnClickListener(paramOnClickListener);
    }
    for (;;)
    {
      logEmptyClusterImpression();
      return;
      localPlayCardActionBannerClusterViewContent.mExploreButton.setVisibility(8);
    }
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 414;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardActionBannerClusterView
 * JD-Core Version:    0.7.0.1
 */