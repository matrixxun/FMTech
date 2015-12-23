package com.google.android.wallet.instrumentmanager.sidecar;

import android.accounts.Account;
import android.os.Bundle;
import com.android.volley.VolleyError;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.common.analytics.util.AnalyticsUtil;
import com.google.android.wallet.common.sidecar.BackgroundEventRequestResponseListener;
import com.google.android.wallet.common.sidecar.BaseOrchestrationSidecar;
import com.google.android.wallet.common.sidecar.BaseOrchestrationSidecar.OrchestrationErrorListener;
import com.google.android.wallet.common.sidecar.SidecarFragment;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.common.util.SmsSender;
import com.google.android.wallet.common.util.SmsSender.SmsSendListener;
import com.google.android.wallet.config.G.dcb;
import com.google.android.wallet.instrumentmanager.api.http.SecureRefreshPageRequest;
import com.google.android.wallet.instrumentmanager.api.http.SecureSavePageRequest;
import com.google.commerce.payments.orchestration.proto.ui.common.ResponseContextOuterClass.ResponseContext;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.RefreshPageResponse;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.SavePageRequest;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.SavePageResponse;
import com.google.moneta.orchestration.ui.common.ClientEnvironmentConfig.AndroidEnvironmentConfig;
import java.util.Map;

public final class InstrumentManagerSidecar
  extends BaseOrchestrationSidecar
{
  public static final Object REQUEST_DEPENDENT_ON_SMS_TAG = new Object();
  public Api.RefreshPageResponse mRefreshPageResponse;
  public Api.SavePageResponse mSavePageResponse;
  public boolean mSendingRequestAfterSms;
  
  public static InstrumentManagerSidecar newInstance(Account paramAccount, ClientEnvironmentConfig.AndroidEnvironmentConfig paramAndroidEnvironmentConfig)
  {
    InstrumentManagerSidecar localInstrumentManagerSidecar = new InstrumentManagerSidecar();
    localInstrumentManagerSidecar.setArguments(createArgs(paramAccount, paramAndroidEnvironmentConfig));
    return localInstrumentManagerSidecar;
  }
  
  protected final void clearPreviousResponses()
  {
    this.mSavePageResponse = null;
    this.mRefreshPageResponse = null;
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mSavePageResponse != null) {
      paramBundle.putParcelable("savePageResponse", ParcelableProto.forProto(this.mSavePageResponse));
    }
    if (this.mRefreshPageResponse != null) {
      paramBundle.putParcelable("refreshPageResponse", ParcelableProto.forProto(this.mRefreshPageResponse));
    }
    paramBundle.putBoolean("sendingRequestAfterSms", this.mSendingRequestAfterSms);
  }
  
  protected final void restoreFromSavedInstanceState(Bundle paramBundle)
  {
    this.mSavePageResponse = ((Api.SavePageResponse)ParcelableProto.getProtoFromBundle(paramBundle, "savePageResponse"));
    this.mRefreshPageResponse = ((Api.RefreshPageResponse)ParcelableProto.getProtoFromBundle(paramBundle, "refreshPageResponse"));
    this.mSendingRequestAfterSms = paramBundle.getBoolean("sendingRequestAfterSms");
    super.restoreFromSavedInstanceState(paramBundle);
  }
  
  public final void savePage(Api.SavePageRequest paramSavePageRequest, ResponseContextOuterClass.ResponseContext paramResponseContext, Map<String, String> paramMap, SavePageResponseListener paramSavePageResponseListener, BaseOrchestrationSidecar.OrchestrationErrorListener paramOrchestrationErrorListener, Object paramObject)
  {
    if (paramSavePageRequest.context != null) {
      throw new IllegalArgumentException("SavePageRequest's RequestContext should not be set by the caller");
    }
    SecureSavePageRequest localSecureSavePageRequest = new SecureSavePageRequest(this.mApiContext, paramSavePageRequest, paramMap, paramResponseContext.sessionData, paramSavePageResponseListener, paramOrchestrationErrorListener);
    localSecureSavePageRequest.mTag = paramObject;
    paramOrchestrationErrorListener.mRequest = localSecureSavePageRequest;
    super.sendRequest(localSecureSavePageRequest, true);
    AnalyticsUtil.createAndSendRequestSentBackgroundEvent(720, 1, paramResponseContext.logToken);
    this.mSendingRequestAfterSms = false;
  }
  
  protected final boolean shouldRetryOnNoConnectionErrors(int paramInt)
  {
    return (this.mSendingRequestAfterSms) && (paramInt - 1 < ((Integer)G.dcb.verifyAssociationRetries.get()).intValue());
  }
  
  public final class InstrumentManagerSmsSendListener
    implements SmsSender.SmsSendListener
  {
    private final byte[] mPreviousResponseLogToken;
    
    public InstrumentManagerSmsSendListener(byte[] paramArrayOfByte)
    {
      this.mPreviousResponseLogToken = paramArrayOfByte;
    }
    
    public final void onResult(int paramInt)
    {
      if (paramInt != 1)
      {
        InstrumentManagerSidecar.access$400(InstrumentManagerSidecar.this, InstrumentManagerSidecar.REQUEST_DEPENDENT_ON_SMS_TAG);
        InstrumentManagerSidecar.access$500$4136cb84(InstrumentManagerSidecar.this);
      }
      int i;
      switch (paramInt)
      {
      case 2: 
      default: 
        i = 4;
      }
      for (;;)
      {
        AnalyticsUtil.createAndSendResponseReceivedBackgroundEvent(771, i, this.mPreviousResponseLogToken);
        return;
        i = 0;
        continue;
        i = 31;
        continue;
        i = 30;
        continue;
        i = 32;
      }
    }
  }
  
  public final class RefreshPageResponseListener
    extends BackgroundEventRequestResponseListener<SecureRefreshPageRequest, Api.RefreshPageResponse>
  {
    public RefreshPageResponseListener() {}
  }
  
  public class SavePageResponseListener
    extends BackgroundEventRequestResponseListener<SecureSavePageRequest, Api.SavePageResponse>
  {
    public SavePageResponseListener() {}
    
    public void handleResponse(SecureSavePageRequest paramSecureSavePageRequest, Api.SavePageResponse paramSavePageResponse)
    {
      InstrumentManagerSidecar.access$002(InstrumentManagerSidecar.this, paramSavePageResponse);
      InstrumentManagerSidecar.this.updateStateAndSendAnalyticsEvent(paramSecureSavePageRequest, paramSavePageResponse.context, paramSavePageResponse.error);
    }
  }
  
  public final class SendSmsAndSavePageErrorListener
    extends BaseOrchestrationSidecar.OrchestrationErrorListener
  {
    private final SmsSender mSmsSender;
    
    public SendSmsAndSavePageErrorListener(byte[] paramArrayOfByte, SmsSender paramSmsSender)
    {
      super(paramArrayOfByte);
      this.mSmsSender = paramSmsSender;
    }
    
    public final void onErrorResponse(VolleyError paramVolleyError)
    {
      super.onErrorResponse(paramVolleyError);
      if (InstrumentManagerSidecar.this.mState == 3) {
        this.mSmsSender.mListener = null;
      }
    }
  }
  
  public final class SendSmsAndSavePageResponseListener
    extends InstrumentManagerSidecar.SavePageResponseListener
  {
    private final SmsSender mSmsSender;
    
    public SendSmsAndSavePageResponseListener(SmsSender paramSmsSender)
    {
      super();
      this.mSmsSender = paramSmsSender;
    }
    
    public final void handleResponse(SecureSavePageRequest paramSecureSavePageRequest, Api.SavePageResponse paramSavePageResponse)
    {
      super.handleResponse(paramSecureSavePageRequest, paramSavePageResponse);
      this.mSmsSender.mListener = null;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.sidecar.InstrumentManagerSidecar
 * JD-Core Version:    0.7.0.1
 */