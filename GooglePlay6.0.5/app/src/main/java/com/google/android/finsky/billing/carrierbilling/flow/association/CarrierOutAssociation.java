package com.google.android.finsky.billing.carrierbilling.flow.association;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.android.volley.NoConnectionError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.config.G;
import com.google.android.finsky.protos.CarrierBilling.InitiateAssociationResponse;
import com.google.android.finsky.protos.CarrierBilling.VerifyAssociationResponse;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;

public final class CarrierOutAssociation
  implements Response.ErrorListener, Response.Listener<CarrierBilling.InitiateAssociationResponse>, AssociationAction, SmsSender.SmsSendListener
{
  private final String mAcceptedPiiTosVersion;
  private final DfeApi mDfeApi;
  private Response.ErrorListener mErrorListener;
  private final boolean mGetSubscriberAddress;
  private Response.Listener<CarrierBilling.VerifyAssociationResponse> mListener;
  private String mSmsAddress;
  private String mSmsPrefix;
  private int mState = 0;
  private int mVerificationRetries;
  
  public CarrierOutAssociation(DfeApi paramDfeApi, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    this.mDfeApi = paramDfeApi;
    this.mSmsAddress = paramString1;
    this.mSmsPrefix = paramString2;
    this.mAcceptedPiiTosVersion = paramString3;
    this.mGetSubscriberAddress = paramBoolean;
  }
  
  private void dispatchError()
  {
    CarrierBilling.VerifyAssociationResponse localVerifyAssociationResponse = new CarrierBilling.VerifyAssociationResponse();
    localVerifyAssociationResponse.status = 4;
    localVerifyAssociationResponse.hasStatus = true;
    dispatch(localVerifyAssociationResponse);
  }
  
  private void initiateAssociation()
  {
    this.mState = 0;
    this.mDfeApi.initiateAssociation(BillingLocator.getSimIdentifier(), this, this);
  }
  
  public final void cancel()
  {
    this.mListener = null;
    this.mErrorListener = null;
  }
  
  final void dispatch(CarrierBilling.VerifyAssociationResponse paramVerifyAssociationResponse)
  {
    if (this.mListener != null) {
      this.mListener.onResponse(paramVerifyAssociationResponse);
    }
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    int i;
    if ((this.mVerificationRetries < ((Integer)G.dcb3VerifyAssociationRetries.get()).intValue()) && ((paramVolleyError instanceof NoConnectionError)) && (this.mState == 1))
    {
      i = 1;
      if (i == 0) {
        break label81;
      }
      this.mVerificationRetries = (1 + this.mVerificationRetries);
      new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
      {
        public final void run()
        {
          CarrierOutAssociation.this.verifyAssociation();
        }
      }, 3000L);
    }
    label81:
    do
    {
      return;
      i = 0;
      break;
      FinskyLog.w("Error while receiving Volley response in state " + this.mState, new Object[0]);
    } while (this.mErrorListener == null);
    this.mErrorListener.onErrorResponse(paramVolleyError);
  }
  
  public final void onResult(int paramInt)
  {
    if (paramInt == 1)
    {
      FinskyLog.w("Sending Sms Failed", new Object[0]);
      dispatchError();
      cancel();
    }
  }
  
  public final void resumeState(Bundle paramBundle, Response.Listener<CarrierBilling.VerifyAssociationResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    this.mState = paramBundle.getInt("association_state");
    this.mVerificationRetries = paramBundle.getInt("association_retries");
    this.mListener = paramListener;
    this.mErrorListener = paramErrorListener;
    if (this.mState == 1)
    {
      verifyAssociation();
      return;
    }
    initiateAssociation();
  }
  
  public final void saveState(Bundle paramBundle)
  {
    paramBundle.putInt("association_state", this.mState);
    paramBundle.putInt("association_retries", this.mVerificationRetries);
    cancel();
  }
  
  public final void setListener(Response.Listener<CarrierBilling.VerifyAssociationResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    this.mListener = paramListener;
    this.mErrorListener = paramErrorListener;
  }
  
  public final void start(Response.Listener<CarrierBilling.VerifyAssociationResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    this.mListener = paramListener;
    this.mErrorListener = paramErrorListener;
    initiateAssociation();
  }
  
  final void verifyAssociation()
  {
    this.mState = 1;
    if (this.mListener == null) {
      return;
    }
    this.mDfeApi.verifyAssociation(BillingLocator.getSimIdentifier(), this.mAcceptedPiiTosVersion, this.mGetSubscriberAddress, new Response.Listener() {}, this);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.flow.association.CarrierOutAssociation
 * JD-Core Version:    0.7.0.1
 */