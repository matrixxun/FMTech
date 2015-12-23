package com.google.android.wallet.instrumentmanager.api.http;

import com.android.volley.Response.ErrorListener;
import com.google.android.wallet.common.api.http.ApiContext;
import com.google.android.wallet.common.api.http.SecureRequest;
import com.google.android.wallet.common.sidecar.BackgroundEventRequestResponseListener;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.common.util.ProtoUtils;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.SavePageRequest;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.SavePageResponse;
import com.google.protobuf.nano.MessageNano;
import java.util.Map;

public final class SecureSavePageRequest
  extends SecureRequest<SecureSavePageRequest, Api.SavePageResponse>
{
  public final Api.SavePageRequest mRequest;
  
  public SecureSavePageRequest(ApiContext paramApiContext, Api.SavePageRequest paramSavePageRequest, Map<String, String> paramMap, byte[] paramArrayOfByte, BackgroundEventRequestResponseListener<SecureSavePageRequest, Api.SavePageResponse> paramBackgroundEventRequestResponseListener, Response.ErrorListener paramErrorListener)
  {
    super(paramApiContext, paramMap, paramArrayOfByte, Api.SavePageResponse.class, paramBackgroundEventRequestResponseListener, paramErrorListener);
    this.mRequest = paramSavePageRequest;
  }
  
  protected final String getActionUrl()
  {
    return "instrumentmanager/submit";
  }
  
  public final int getBackgroundEventReceivedType()
  {
    return 721;
  }
  
  public final int getBackgroundEventSentType()
  {
    return 720;
  }
  
  public final MessageNano getProtoRequest()
  {
    Api.SavePageRequest localSavePageRequest = (Api.SavePageRequest)ProtoUtils.copyFrom(this.mRequest);
    localSavePageRequest.context = PaymentUtils.createRequestContextForSharedLibrary$793b517b(this.mApiContext.applicationContext, this.mSessionData, null);
    return localSavePageRequest;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.api.http.SecureSavePageRequest
 * JD-Core Version:    0.7.0.1
 */