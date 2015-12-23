package com.google.android.finsky.billing.giftcard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.protos.Acquisition.PostAcquisitionPrompt;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.PromoCode.RedeemCodeRequest;
import com.google.android.finsky.protos.PromoCode.RedeemCodeResponse;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.wallet.common.pub.OrchestrationUtil;

public final class RedeemCodeSidecar
  extends SidecarFragment
{
  private DfeApi mDfeApi;
  String mErrorHtml;
  private FinskyEventLog mEventLogger;
  PromoCode.RedeemCodeResponse mLastRedeemCodeResponse;
  private final RedemptionListener mRedemptionListener = new RedemptionListener((byte)0);
  public PromoCode.RedeemCodeRequest mRequest = new PromoCode.RedeemCodeRequest();
  VolleyError mVolleyError;
  
  public final Acquisition.PostAcquisitionPrompt getPostAcquisitionPrompt()
  {
    if (this.mState != 2)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.mState);
      FinskyLog.wtf("Invalid state: %d", arrayOfObject);
      return null;
    }
    return this.mLastRedeemCodeResponse.postAcquisitionPrompt;
  }
  
  public final Document getRedeemedItemDoc()
  {
    if ((this.mLastRedeemCodeResponse == null) || (this.mLastRedeemCodeResponse.doc == null)) {
      return null;
    }
    return new Document(this.mLastRedeemCodeResponse.doc);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    String str1 = this.mArguments.getString("authAccount");
    this.mDfeApi = FinskyApp.get().getDfeApi(str1);
    this.mEventLogger = FinskyApp.get().getEventLogger(str1);
    if (paramBundle != null)
    {
      this.mRequest = ((PromoCode.RedeemCodeRequest)ParcelableProto.getProtoFromBundle(paramBundle, "RedeemCodeSidecar.request"));
      this.mLastRedeemCodeResponse = ((PromoCode.RedeemCodeResponse)ParcelableProto.getProtoFromBundle(paramBundle, "RedeemCodeSidecar.last_redeem_code_response"));
      this.mErrorHtml = paramBundle.getString("RedeemCodeSidecar.error_html");
    }
    for (;;)
    {
      this.mRequest.paymentsIntegratorClientContextToken = OrchestrationUtil.createClientToken(getActivity(), this.mArguments.getInt("RedeemCodeSidecar.im_theme_res_id"));
      this.mRequest.hasPaymentsIntegratorClientContextToken = true;
      super.onCreate(paramBundle);
      return;
      this.mRequest.redemptionContext = this.mArguments.getInt("RedeemCodeSidecar.redemption_context");
      this.mRequest.hasRedemptionContext = true;
      this.mRequest.docid = ((Common.Docid)ParcelableProto.getProtoFromBundle(this.mArguments, "RedeemCodeSidecar.docid"));
      int i = this.mArguments.getInt("RedeemCodeSidecar.offer_type");
      if (i != 0)
      {
        this.mRequest.offerType = i;
        this.mRequest.hasOfferType = true;
      }
      String str2 = this.mArguments.getString("RedeemCodeSidecar.partner_payload");
      if (str2 != null)
      {
        this.mRequest.partnerPayload = str2;
        this.mRequest.hasPartnerPayload = true;
      }
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("RedeemCodeSidecar.request", ParcelableProto.forProto(this.mRequest));
    paramBundle.putParcelable("RedeemCodeSidecar.last_redeem_code_response", ParcelableProto.forProto(this.mLastRedeemCodeResponse));
    paramBundle.putString("RedeemCodeSidecar.error_html", this.mErrorHtml);
  }
  
  public final void sendRedemptionRequest()
  {
    this.mEventLogger.logBackgroundEvent(800, null);
    if ((this.mLastRedeemCodeResponse != null) && (this.mLastRedeemCodeResponse.hasToken)) {
      this.mRequest.token = this.mLastRedeemCodeResponse.token;
    }
    for (this.mRequest.hasToken = true;; this.mRequest.hasToken = false)
    {
      this.mRequest.consumptionAppVersionCode = 0L;
      this.mRequest.hasConsumptionAppVersionCode = false;
      if ((this.mLastRedeemCodeResponse != null) && (this.mLastRedeemCodeResponse.consumptionAppDocid != null) && (this.mLastRedeemCodeResponse.consumptionAppDocid.backend == 3))
      {
        String str = this.mLastRedeemCodeResponse.consumptionAppDocid.backendDocid;
        PackageStateRepository.PackageState localPackageState = FinskyApp.get().mPackageStateRepository.get(str);
        if (localPackageState != null)
        {
          this.mRequest.consumptionAppVersionCode = localPackageState.installedVersion;
          this.mRequest.hasConsumptionAppVersionCode = true;
        }
      }
      this.mLastRedeemCodeResponse = null;
      this.mVolleyError = null;
      this.mErrorHtml = null;
      this.mDfeApi.redeemCode(this.mRequest, this.mRedemptionListener, this.mRedemptionListener);
      setState(1, 0);
      return;
      this.mRequest.token = "";
    }
  }
  
  private final class RedemptionListener
    implements Response.ErrorListener, Response.Listener<PromoCode.RedeemCodeResponse>
  {
    private RedemptionListener() {}
    
    public final void onErrorResponse(VolleyError paramVolleyError)
    {
      RedeemCodeSidecar.access$1002(RedeemCodeSidecar.this, paramVolleyError);
      RedeemCodeSidecar.this.mEventLogger.logBackgroundEvent(801, null, null, -1, paramVolleyError.getClass().getSimpleName(), null);
      RedeemCodeSidecar.access$1200$394060a6(RedeemCodeSidecar.this);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.giftcard.RedeemCodeSidecar
 * JD-Core Version:    0.7.0.1
 */