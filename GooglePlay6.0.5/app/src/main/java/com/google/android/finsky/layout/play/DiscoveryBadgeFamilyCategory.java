package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Details.DiscoveryBadge;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.image.AvatarCropTransformation;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;

public class DiscoveryBadgeFamilyCategory
  extends DiscoveryBadgeBase
{
  private ImageView mOverlayIcon;
  
  public DiscoveryBadgeFamilyCategory(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DiscoveryBadgeFamilyCategory(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void bind(Details.DiscoveryBadge paramDiscoveryBadge, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, Document paramDocument, DfeToc paramDfeToc, PackageManager paramPackageManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    int i = 2130837670;
    super.bind(paramDiscoveryBadge, paramBitmapLoader, paramNavigationManager, paramDocument, paramDfeToc, paramPackageManager, paramPlayStoreUiElementNode);
    ImageView localImageView = this.mOverlayIcon;
    switch (paramDocument.mDocument.backendId)
    {
    }
    for (;;)
    {
      localImageView.setImageResource(i);
      return;
      i = 2130837671;
      continue;
      i = 2130837672;
    }
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 1801;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mOverlayIcon = ((ImageView)findViewById(2131755438));
  }
  
  protected final void onPreImageLoad()
  {
    AvatarCropTransformation localAvatarCropTransformation = AvatarCropTransformation.getNoRingAvatarCrop(getResources(), this.mCurrentFillColor);
    this.mIcon.setBitmapTransformation(localAvatarCropTransformation);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.DiscoveryBadgeFamilyCategory
 * JD-Core Version:    0.7.0.1
 */