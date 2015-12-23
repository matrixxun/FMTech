package com.google.android.wallet.analytics;

import java.util.List;

public abstract interface UiNode
{
  public abstract List<UiNode> getChildren();
  
  public abstract UiNode getParentUiNode();
  
  public abstract WalletUiElement getUiElement();
  
  public abstract void setParentUiNode(UiNode paramUiNode);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.analytics.UiNode
 * JD-Core Version:    0.7.0.1
 */