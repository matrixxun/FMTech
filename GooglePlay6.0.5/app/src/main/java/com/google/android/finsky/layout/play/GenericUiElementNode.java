package com.google.android.finsky.layout.play;

import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElementInfo;

public class GenericUiElementNode
  implements PlayStoreUiElementNode
{
  private PlayStoreUiElementNode mParentNode = null;
  private PlayStore.PlayStoreUiElement mUiElementProto = null;
  
  public GenericUiElementNode(int paramInt, byte[] paramArrayOfByte, PlayStore.PlayStoreUiElementInfo paramPlayStoreUiElementInfo, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    reset(paramInt, paramArrayOfByte, paramPlayStoreUiElementInfo, paramPlayStoreUiElementNode);
  }
  
  public void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
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
  
  public final void reset(int paramInt, byte[] paramArrayOfByte, PlayStore.PlayStoreUiElementInfo paramPlayStoreUiElementInfo, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(paramInt);
    FinskyEventLog.setServerLogCookie(this.mUiElementProto, paramArrayOfByte);
    if (paramPlayStoreUiElementInfo != null) {
      this.mUiElementProto.clientLogsCookie = paramPlayStoreUiElementInfo;
    }
    this.mParentNode = paramPlayStoreUiElementNode;
  }
  
  public final void setServerLogsCookie(byte[] paramArrayOfByte)
  {
    FinskyEventLog.setServerLogCookie(this.mUiElementProto, paramArrayOfByte);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.GenericUiElementNode
 * JD-Core Version:    0.7.0.1
 */