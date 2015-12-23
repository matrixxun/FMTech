package com.google.android.wallet.instrumentmanager.pub.analytics.events;

import com.google.android.wallet.analytics.WalletUiElement;
import java.util.List;

public final class InstrumentManagerClickEvent
{
  public final List<WalletUiElement> clickPath;
  
  public InstrumentManagerClickEvent(List<WalletUiElement> paramList)
  {
    this.clickPath = paramList;
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    int j = this.clickPath.size();
    while (i < j)
    {
      if (localStringBuilder.length() > 0) {
        localStringBuilder.append(" -> ");
      }
      localStringBuilder.append(((WalletUiElement)this.clickPath.get(i)).elementId);
      i++;
    }
    return localStringBuilder.toString();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.pub.analytics.events.InstrumentManagerClickEvent
 * JD-Core Version:    0.7.0.1
 */