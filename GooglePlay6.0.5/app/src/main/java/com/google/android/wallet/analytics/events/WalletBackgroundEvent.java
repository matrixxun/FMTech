package com.google.android.wallet.analytics.events;

import com.google.android.wallet.analytics.CreditCardEntryAction;
import com.google.android.wallet.analytics.WebViewPageLoadEvent;

public final class WalletBackgroundEvent
{
  public final int attempts;
  public final int backgroundEventType;
  public final long clientLatencyMs;
  public final CreditCardEntryAction creditCardEntryAction;
  public final String exceptionType;
  public final byte[] integratorLogToken;
  public final int resultCode;
  public final long serverLatencyMs;
  public final WebViewPageLoadEvent webViewPageLoadEvent;
  
  public WalletBackgroundEvent(int paramInt1, int paramInt2, String paramString, long paramLong1, long paramLong2, int paramInt3, byte[] paramArrayOfByte)
  {
    this.backgroundEventType = paramInt1;
    this.resultCode = paramInt2;
    this.exceptionType = paramString;
    this.clientLatencyMs = paramLong1;
    this.serverLatencyMs = paramLong2;
    this.attempts = paramInt3;
    this.integratorLogToken = paramArrayOfByte;
    this.creditCardEntryAction = null;
    this.webViewPageLoadEvent = null;
  }
  
  public WalletBackgroundEvent(int paramInt, WebViewPageLoadEvent paramWebViewPageLoadEvent, byte[] paramArrayOfByte)
  {
    this.backgroundEventType = 772;
    this.resultCode = paramInt;
    this.exceptionType = null;
    this.clientLatencyMs = -1L;
    this.serverLatencyMs = -1L;
    this.integratorLogToken = paramArrayOfByte;
    this.creditCardEntryAction = null;
    this.attempts = 0;
    this.webViewPageLoadEvent = paramWebViewPageLoadEvent;
  }
  
  public WalletBackgroundEvent(CreditCardEntryAction paramCreditCardEntryAction, byte[] paramArrayOfByte)
  {
    this.backgroundEventType = 770;
    this.resultCode = 0;
    this.exceptionType = null;
    this.clientLatencyMs = -1L;
    this.serverLatencyMs = -1L;
    this.integratorLogToken = paramArrayOfByte;
    this.creditCardEntryAction = paramCreditCardEntryAction;
    this.attempts = 0;
    this.webViewPageLoadEvent = null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.analytics.events.WalletBackgroundEvent
 * JD-Core Version:    0.7.0.1
 */