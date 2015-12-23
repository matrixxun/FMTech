package com.google.android.finsky.layout.play;

import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;

public abstract interface PlayStoreUiElementNode
{
  public abstract void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode);
  
  public abstract PlayStoreUiElementNode getParentNode();
  
  public abstract PlayStore.PlayStoreUiElement getPlayStoreUiElement();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayStoreUiElementNode
 * JD-Core Version:    0.7.0.1
 */