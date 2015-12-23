package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;

public class PlayCardMerchClusterViewContentV2
  extends PlayClusterViewContentV2
{
  public PlayCardMerchClusterViewContentV2(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardMerchClusterViewContentV2(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected int getLeadingItemGap()
  {
    if (this.mItemsPerPage <= 3) {
      return 1;
    }
    return 2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardMerchClusterViewContentV2
 * JD-Core Version:    0.7.0.1
 */