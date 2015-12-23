package com.google.android.finsky.layout.play;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.AttributeSet;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Details.DiscoveryBadge;
import com.google.android.play.image.AvatarCropTransformation;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;

public class DiscoveryBadgeFamilyAgeRange
  extends DiscoveryBadgeBase
{
  private FifeImageView mOverlayIcon;
  
  public DiscoveryBadgeFamilyAgeRange(Context paramContext)
  {
    super(paramContext, null);
  }
  
  public DiscoveryBadgeFamilyAgeRange(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mBadgeRadius = paramContext.getResources().getDimensionPixelSize(2131493005);
  }
  
  @SuppressLint({"NewApi"})
  public final void bind(Details.DiscoveryBadge paramDiscoveryBadge, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, Document paramDocument, DfeToc paramDfeToc, PackageManager paramPackageManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.bind(paramDiscoveryBadge, paramBitmapLoader, paramNavigationManager, paramDocument, paramDfeToc, paramPackageManager, paramPlayStoreUiElementNode);
    this.mOverlayIcon.setImage(paramDiscoveryBadge.image.imageUrl, paramDiscoveryBadge.image.supportsFifeUrlOptions, paramBitmapLoader);
  }
  
  protected final void bindIconImage(Details.DiscoveryBadge paramDiscoveryBadge, BitmapLoader paramBitmapLoader)
  {
    AvatarCropTransformation localAvatarCropTransformation = AvatarCropTransformation.getNoRingAvatarCrop(getResources(), this.mCurrentFillColor);
    this.mIcon.setBitmapTransformation(localAvatarCropTransformation);
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 1801;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mOverlayIcon = ((FifeImageView)findViewById(2131755438));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.DiscoveryBadgeFamilyAgeRange
 * JD-Core Version:    0.7.0.1
 */