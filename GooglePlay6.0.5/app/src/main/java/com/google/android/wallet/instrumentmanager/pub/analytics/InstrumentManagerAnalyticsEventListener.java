package com.google.android.wallet.instrumentmanager.pub.analytics;

import com.google.android.wallet.analytics.events.WalletBackgroundEvent;
import com.google.android.wallet.instrumentmanager.pub.analytics.events.InstrumentManagerClickEvent;
import com.google.android.wallet.instrumentmanager.pub.analytics.events.InstrumentManagerImpressionEvent;

public abstract interface InstrumentManagerAnalyticsEventListener
{
  public abstract void onBackgroundEvent(WalletBackgroundEvent paramWalletBackgroundEvent);
  
  public abstract void onClickEvent(InstrumentManagerClickEvent paramInstrumentManagerClickEvent);
  
  public abstract void onImpressionEvent(InstrumentManagerImpressionEvent paramInstrumentManagerImpressionEvent);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.pub.analytics.InstrumentManagerAnalyticsEventListener
 * JD-Core Version:    0.7.0.1
 */