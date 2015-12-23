package com.google.android.finsky.layout;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Details.DiscoveryBadge;
import com.google.android.play.image.BitmapLoader;

public class DiscoveryBar
  extends LinearLayout
  implements DetailsSectionStack.NeedsTrailingSeparator, PlayStoreUiElementNode
{
  public ViewGroup mBadgeContainer;
  public BitmapLoader mBitmapLoader;
  public DfeToc mDfeToc;
  public Details.DiscoveryBadge[] mDiscoveryBadges;
  public Document mDoc;
  public boolean mHasConfigured = false;
  public NavigationManager mNavigationManager;
  public boolean mNeedsToRestoreScrollPosition;
  public PackageManager mPackageManager;
  public PlayStoreUiElementNode mParentNode;
  public int mRestoreScrollPosition;
  private HorizontalScrollView mScrollBar;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(1800);
  
  public DiscoveryBar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DiscoveryBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public int getScrollPosition()
  {
    return this.mScrollBar.getScrollX();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mScrollBar = ((HorizontalScrollView)findViewById(2131755447));
    this.mBadgeContainer = ((ViewGroup)findViewById(2131755448));
  }
  
  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mNeedsToRestoreScrollPosition)
    {
      this.mNeedsToRestoreScrollPosition = false;
      this.mScrollBar.scrollTo(this.mRestoreScrollPosition, 0);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DiscoveryBar
 * JD-Core Version:    0.7.0.1
 */