package com.google.android.finsky.layout.play;

import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;

public final class SelectableUiElementNode
  extends GenericUiElementNode
{
  private boolean mSelected = false;
  
  public SelectableUiElementNode(int paramInt, byte[] paramArrayOfByte, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super(paramInt, paramArrayOfByte, null, paramPlayStoreUiElementNode);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    if (this.mSelected)
    {
      super.childImpression(paramPlayStoreUiElementNode);
      return;
    }
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = paramPlayStoreUiElementNode.getPlayStoreUiElement();
    if (localPlayStoreUiElement == null) {
      throw new IllegalArgumentException("childNode has null element");
    }
    FinskyEventLog.findOrAddChild(this, localPlayStoreUiElement);
  }
  
  public final void setNodeSelected(boolean paramBoolean)
  {
    PlayStoreUiElementNode localPlayStoreUiElementNode = getParentNode();
    if (paramBoolean)
    {
      FinskyEventLog.findOrAddChild(localPlayStoreUiElementNode, getPlayStoreUiElement());
      if (getPlayStoreUiElement().child.length > 0) {
        localPlayStoreUiElementNode.childImpression(this);
      }
    }
    for (;;)
    {
      this.mSelected = paramBoolean;
      return;
      FinskyEventLog.flushImpressionAtRoot(localPlayStoreUiElementNode);
      FinskyEventLog.removeChild(localPlayStoreUiElementNode.getPlayStoreUiElement(), getPlayStoreUiElement());
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.SelectableUiElementNode
 * JD-Core Version:    0.7.0.1
 */