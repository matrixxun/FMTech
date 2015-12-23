package com.google.android.wallet.nfc;

import android.text.TextUtils;

public final class CreditCardNfcResult
{
  public final String cardHolderName;
  public final String cardNumber;
  public final int expMonth;
  public final int expYear;
  
  public CreditCardNfcResult(String paramString1, int paramInt1, int paramInt2, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      paramString1 = "";
    }
    this.cardNumber = paramString1;
    this.expMonth = paramInt1;
    this.expYear = paramInt2;
    if (TextUtils.isEmpty(paramString2)) {
      paramString2 = "";
    }
    this.cardHolderName = paramString2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.CreditCardNfcResult
 * JD-Core Version:    0.7.0.1
 */