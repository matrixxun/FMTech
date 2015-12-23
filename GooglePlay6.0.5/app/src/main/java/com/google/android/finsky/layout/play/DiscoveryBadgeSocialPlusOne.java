package com.google.android.finsky.layout.play;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
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
import com.google.android.play.image.BitmapLoader;

public class DiscoveryBadgeSocialPlusOne
  extends DiscoveryBadgeBase
{
  private Drawable mPlusOneBackground;
  private View mPlusOneContainer;
  
  public DiscoveryBadgeSocialPlusOne(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DiscoveryBadgeSocialPlusOne(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mPlusOneBackground = DrawableCompat.wrap(ContextCompat.getDrawable(paramContext, 2130838104).mutate());
    setWillNotDraw(false);
  }
  
  @SuppressLint({"NewApi"})
  public final void bind(Details.DiscoveryBadge paramDiscoveryBadge, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, Document paramDocument, DfeToc paramDfeToc, PackageManager paramPackageManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.bind(paramDiscoveryBadge, paramBitmapLoader, paramNavigationManager, paramDocument, paramDfeToc, paramPackageManager, paramPlayStoreUiElementNode);
    DrawableCompat.setTint(this.mPlusOneBackground, getResources().getColor(2131689539));
    if (Build.VERSION.SDK_INT >= 16) {
      this.mPlusOneContainer.setBackground(this.mPlusOneBackground);
    }
    for (;;)
    {
      findViewById(2131755443).setContentDescription(null);
      return;
      this.mPlusOneContainer.setBackgroundDrawable(this.mPlusOneBackground);
    }
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 1804;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mPlusOneContainer = findViewById(2131755442);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.DiscoveryBadgeSocialPlusOne
 * JD-Core Version:    0.7.0.1
 */