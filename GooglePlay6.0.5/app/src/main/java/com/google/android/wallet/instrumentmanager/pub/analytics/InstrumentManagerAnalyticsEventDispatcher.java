package com.google.android.wallet.instrumentmanager.pub.analytics;

import android.util.Log;
import com.google.android.wallet.analytics.events.WalletBackgroundEvent;

public final class InstrumentManagerAnalyticsEventDispatcher
{
  public static InstrumentManagerAnalyticsEventListener sListener;
  
  public static void sendBackgroundEvent(WalletBackgroundEvent paramWalletBackgroundEvent)
  {
    if (sListener != null) {
      sListener.onBackgroundEvent(paramWalletBackgroundEvent);
    }
    while (!Log.isLoggable("ImAnalyticsDispatcher", 3)) {
      return;
    }
    Log.d("ImAnalyticsDispatcher", "No listener found for sending background event of type " + paramWalletBackgroundEvent.backgroundEventType);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.pub.analytics.InstrumentManagerAnalyticsEventDispatcher
 * JD-Core Version:    0.7.0.1
 */