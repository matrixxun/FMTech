package com.google.android.wallet.instrumentmanager.pub.analytics.events;

import com.google.android.wallet.analytics.WalletUiElement;
import java.util.List;

public final class InstrumentManagerImpressionEvent
{
  public final WalletUiElement impressionTree;
  
  public InstrumentManagerImpressionEvent(WalletUiElement paramWalletUiElement)
  {
    this.impressionTree = paramWalletUiElement;
  }
  
  private static void printTree(WalletUiElement paramWalletUiElement, StringBuilder paramStringBuilder, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < paramInt; i++) {
      localStringBuilder.append("| ");
    }
    paramStringBuilder.append(localStringBuilder);
    paramStringBuilder.append("|-");
    paramStringBuilder.append(paramWalletUiElement.elementId);
    paramStringBuilder.append(" tokenLen=").append(paramWalletUiElement.integratorLogToken.length);
    paramStringBuilder.append('\n');
    if (paramWalletUiElement.children != null)
    {
      int j = paramInt + 1;
      int k = 0;
      int m = paramWalletUiElement.children.size();
      while (k < m)
      {
        printTree((WalletUiElement)paramWalletUiElement.children.get(k), paramStringBuilder, j);
        k++;
      }
    }
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(32);
    localStringBuilder.append('\n');
    printTree(this.impressionTree, localStringBuilder, 0);
    return localStringBuilder.toString();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.pub.analytics.events.InstrumentManagerImpressionEvent
 * JD-Core Version:    0.7.0.1
 */