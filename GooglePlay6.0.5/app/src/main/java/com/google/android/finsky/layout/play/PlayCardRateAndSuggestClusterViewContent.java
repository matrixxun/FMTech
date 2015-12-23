package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayCardViewBase;

public class PlayCardRateAndSuggestClusterViewContent
  extends PlayCardClusterViewContent
{
  int mState = 0;
  
  public PlayCardRateAndSuggestClusterViewContent(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardRateAndSuggestClusterViewContent(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void createContent(DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, PlayCardDismissListener paramPlayCardDismissListener, PlayCardHeap paramPlayCardHeap, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.createContent(paramDfeApi, paramNavigationManager, paramBitmapLoader, paramPlayCardDismissListener, paramPlayCardHeap, paramPlayStoreUiElementNode);
    refreshCards(false);
  }
  
  protected int getNumberOfTilesToBind()
  {
    if (this.mState == 0) {
      return 1;
    }
    return super.getNumberOfTilesToBind();
  }
  
  final void refreshCards(boolean paramBoolean)
  {
    if (getCardChildCount() == 0) {
      return;
    }
    for (int i = 0; i < this.mMetadata.getTileCount(); i++) {
      getCardChildAt(i).setVisibility(0);
    }
    int j = this.mState;
    int k = 0;
    if (j == 2) {
      k = 1;
    }
    int m = 1;
    label55:
    PendingStateHandler localPendingStateHandler;
    if (m < this.mMetadata.getTileCount())
    {
      PlayCardViewBase localPlayCardViewBase = getCardChildAt(m);
      if (localPlayCardViewBase.getData() == null) {
        localPlayCardViewBase.bindLoading();
      }
      localPendingStateHandler = (PendingStateHandler)localPlayCardViewBase;
      if (k == 0) {
        break label114;
      }
      localPendingStateHandler.exitPendingStateIfNecessary(paramBoolean);
    }
    for (;;)
    {
      m++;
      break label55;
      break;
      label114:
      localPendingStateHandler.enterPendingStateIfNecessary(paramBoolean);
    }
  }
  
  public static abstract interface PendingStateHandler
  {
    public abstract void enterPendingStateIfNecessary(boolean paramBoolean);
    
    public abstract void exitPendingStateIfNecessary(boolean paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardRateAndSuggestClusterViewContent
 * JD-Core Version:    0.7.0.1
 */