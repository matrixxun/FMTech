package com.google.android.finsky.billing.lightpurchase.billingprofile.instruments;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.carrierbilling.flow.CreateDcb3Flow;
import com.google.android.finsky.protos.CarrierBillingInstrumentStatus;
import com.google.android.finsky.utils.ParcelableProto;

public abstract class AddDcb3BaseActivity
  extends InstrumentActivity
{
  protected static void putIntentExtras(String paramString, CarrierBillingInstrumentStatus paramCarrierBillingInstrumentStatus, Intent paramIntent)
  {
    paramIntent.putExtra("authAccount", paramString);
    paramIntent.putExtra("AddDcb3Instrument.instrument_status", ParcelableProto.forProto(paramCarrierBillingInstrumentStatus));
  }
  
  protected final BillingFlow getBillingFlow()
  {
    CarrierBillingInstrumentStatus localCarrierBillingInstrumentStatus = (CarrierBillingInstrumentStatus)ParcelableProto.getProtoFromIntent(getIntent(), "AddDcb3Instrument.instrument_status");
    return new CreateDcb3Flow(this, this, FinskyApp.get().getDfeApi(this.mAccountName), localCarrierBillingInstrumentStatus, getBillingUiMode());
  }
  
  protected abstract int getPlayStoreUiElementType();
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    startOrResumeFlow(this.mSavedFlowState);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.AddDcb3BaseActivity
 * JD-Core Version:    0.7.0.1
 */