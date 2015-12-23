package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;

public class PlayQuickLinksBannerView
  extends PlayClusterViewContentV2
  implements PlayStoreUiElementNode
{
  public PlayStoreUiElementNode mParentNode;
  public PlayStore.PlayStoreUiElement mUiElementProto;
  
  public PlayQuickLinksBannerView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayQuickLinksBannerView(Context paramContext, AttributeSet paramAttributeSet)
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
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayQuickLinksBannerView
 * JD-Core Version:    0.7.0.1
 */