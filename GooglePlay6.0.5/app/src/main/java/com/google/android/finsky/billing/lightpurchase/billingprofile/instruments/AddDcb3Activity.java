package com.google.android.finsky.billing.lightpurchase.billingprofile.instruments;

import android.content.Intent;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.protos.CarrierBillingInstrumentStatus;

public class AddDcb3Activity
  extends AddDcb3BaseActivity
{
  public static Intent createIntent(String paramString, CarrierBillingInstrumentStatus paramCarrierBillingInstrumentStatus)
  {
    Intent localIntent = new Intent(FinskyApp.get(), AddDcb3Activity.class);
    putIntentExtras(paramString, paramCarrierBillingInstrumentStatus, localIntent);
    return localIntent;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 841;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.AddDcb3Activity
 * JD-Core Version:    0.7.0.1
 */