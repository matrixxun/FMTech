package com.google.android.wallet.analytics;

import java.util.List;

public final class SimpleUiNode
  implements UiNode
{
  private UiNode mParentNode;
  private final WalletUiElement mUiElement;
  
  public SimpleUiNode(int paramInt, UiNode paramUiNode)
  {
    this.mUiElement = new WalletUiElement(paramInt);
    this.mParentNode = paramUiNode;
  }
  
  public final List<UiNode> getChildren()
  {
    return null;
  }
  
  public final UiNode getParentUiNode()
  {
    return this.mParentNode;
  }
  
  public final WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final void setParentUiNode(UiNode paramUiNode)
  {
    this.mParentNode = paramUiNode;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.analytics.SimpleUiNode
 * JD-Core Version:    0.7.0.1
 */