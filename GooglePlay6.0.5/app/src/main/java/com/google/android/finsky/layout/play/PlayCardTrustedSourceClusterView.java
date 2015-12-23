package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import java.util.List;

public class PlayCardTrustedSourceClusterView
  extends PlayCardClusterView
{
  private final int mContentVerticalMargin;
  
  public PlayCardTrustedSourceClusterView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardTrustedSourceClusterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContentVerticalMargin = paramContext.getResources().getDimensionPixelSize(2131493431);
  }
  
  public final void configurePersonProfile(NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, Document paramDocument, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    PlayCardTrustedSourceClusterViewContent localPlayCardTrustedSourceClusterViewContent = (PlayCardTrustedSourceClusterViewContent)this.mContent;
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localPlayCardTrustedSourceClusterViewContent.getLayoutParams();
    localMarginLayoutParams.topMargin = this.mContentVerticalMargin;
    localMarginLayoutParams.bottomMargin = this.mContentVerticalMargin;
    localPlayCardTrustedSourceClusterViewContent.mDocument = paramDocument;
    View.OnClickListener localOnClickListener = paramNavigationManager.getClickListener(localPlayCardTrustedSourceClusterViewContent.mDocument, paramPlayStoreUiElementNode);
    localPlayCardTrustedSourceClusterViewContent.mProfileAvatarImage.bindView(localPlayCardTrustedSourceClusterViewContent.mDocument.mDocument, localOnClickListener, paramBitmapLoader);
    Common.Image localImage = (Common.Image)localPlayCardTrustedSourceClusterViewContent.mDocument.getImages(15).get(0);
    localPlayCardTrustedSourceClusterViewContent.mProfileCoverImage.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
    localPlayCardTrustedSourceClusterViewContent.mProfileCoverImage.setOnClickListener(localOnClickListener);
    localPlayCardTrustedSourceClusterViewContent.mProfileCoverImage.setContentDescription(localPlayCardTrustedSourceClusterViewContent.mProfileAvatarImage.getContentDescription());
    localPlayCardTrustedSourceClusterViewContent.mProfileTitle.setText(paramDocument.mDocument.title);
    localPlayCardTrustedSourceClusterViewContent.mProfileSubtitle.setText(paramDocument.mDocument.subtitle);
    localPlayCardTrustedSourceClusterViewContent.mCirclesButton.bind(paramDocument, FinskyApp.get().getCurrentAccountName(), paramPlayStoreUiElementNode);
    logEmptyClusterImpression();
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 411;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardTrustedSourceClusterView
 * JD-Core Version:    0.7.0.1
 */