package com.google.android.wallet.purchasemanager.sidecar;

import android.os.Bundle;
import com.google.android.wallet.common.sidecar.BackgroundEventRequestResponseListener;
import com.google.android.wallet.common.sidecar.BaseOrchestrationSidecar;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.purchasemanager.api.http.SecureSubmitRequest;
import com.google.moneta.orchestration.ui.purchasemanager.Api.SubmitResponse;

public final class PurchaseManagerSidecar
  extends BaseOrchestrationSidecar
{
  public Api.SubmitResponse mSubmitResponse;
  
  protected final void clearPreviousResponses()
  {
    this.mSubmitResponse = null;
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mSubmitResponse != null) {
      paramBundle.putParcelable("submitResponse", ParcelableProto.forProto(this.mSubmitResponse));
    }
  }
  
  protected final void restoreFromSavedInstanceState(Bundle paramBundle)
  {
    super.restoreFromSavedInstanceState(paramBundle);
    this.mSubmitResponse = ((Api.SubmitResponse)ParcelableProto.getProtoFromBundle(paramBundle, "submitResponse"));
  }
  
  public final class SubmitResponseListener
    extends BackgroundEventRequestResponseListener<SecureSubmitRequest, Api.SubmitResponse>
  {
    public SubmitResponseListener() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.purchasemanager.sidecar.PurchaseManagerSidecar
 * JD-Core Version:    0.7.0.1
 */