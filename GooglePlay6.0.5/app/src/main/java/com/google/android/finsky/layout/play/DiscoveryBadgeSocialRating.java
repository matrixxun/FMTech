package com.google.android.finsky.layout.play;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Details.DiscoveryBadge;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.StarRatingBar;

public class DiscoveryBadgeSocialRating
  extends DiscoveryBadgeBase
{
  private StarRatingBar mRatingBar;
  private View mRatingBarContainer;
  private Drawable mRatingBarContainerBackground;
  
  public DiscoveryBadgeSocialRating(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DiscoveryBadgeSocialRating(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mRatingBarContainerBackground = DrawableCompat.wrap(ContextCompat.getDrawable(paramContext, 2130838105).mutate());
    setWillNotDraw(false);
  }
  
  @TargetApi(16)
  public final void bind(Details.DiscoveryBadge paramDiscoveryBadge, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, Document paramDocument, DfeToc paramDfeToc, PackageManager paramPackageManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.bind(paramDiscoveryBadge, paramBitmapLoader, paramNavigationManager, paramDocument, paramDfeToc, paramPackageManager, paramPlayStoreUiElementNode);
    this.mRatingBar.setRating(paramDiscoveryBadge.userStarRating);
    DrawableCompat.setTint(this.mRatingBarContainerBackground, CorpusResourceUtils.getPrimaryColor(getContext(), paramDocument.mDocument.backendId));
    if (Build.VERSION.SDK_INT >= 16) {
      this.mRatingBarContainer.setBackground(this.mRatingBarContainerBackground);
    }
    for (;;)
    {
      this.mRatingBar.setContentDescription(null);
      return;
      this.mRatingBarContainer.setBackgroundDrawable(this.mRatingBarContainerBackground);
    }
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 1803;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mRatingBar = ((StarRatingBar)findViewById(2131755445));
    this.mRatingBarContainer = findViewById(2131755444);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.DiscoveryBadgeSocialRating
 * JD-Core Version:    0.7.0.1
 */