package com.google.android.wallet.instrumentmanager.api.http;

import com.android.volley.Response.ErrorListener;
import com.google.android.wallet.common.api.http.ApiContext;
import com.google.android.wallet.common.api.http.SecureRequest;
import com.google.android.wallet.common.sidecar.BackgroundEventRequestResponseListener;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.common.util.ProtoUtils;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.RefreshPageRequest;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.RefreshPageResponse;
import com.google.protobuf.nano.MessageNano;
import java.util.Map;

public final class SecureRefreshPageRequest
  extends SecureRequest<SecureRefreshPageRequest, Api.RefreshPageResponse>
{
  public final Api.RefreshPageRequest mRequest;
  
  public SecureRefreshPageRequest(ApiContext paramApiContext, Api.RefreshPageRequest paramRefreshPageRequest, Map<String, String> paramMap, byte[] paramArrayOfByte, BackgroundEventRequestResponseListener<SecureRefreshPageRequest, Api.RefreshPageResponse> paramBackgroundEventRequestResponseListener, Response.ErrorListener paramErrorListener)
  {
    super(paramApiContext, paramMap, paramArrayOfByte, Api.RefreshPageResponse.class, paramBackgroundEventRequestResponseListener, paramErrorListener);
    this.mRequest = paramRefreshPageRequest;
  }
  
  protected final String getActionUrl()
  {
    return "instrumentmanager/refresh";
  }
  
  public final int getBackgroundEventReceivedType()
  {
    return 723;
  }
  
  public final int getBackgroundEventSentType()
  {
    return 722;
  }
  
  protected final MessageNano getProtoRequest()
  {
    Api.RefreshPageRequest localRefreshPageRequest = (Api.RefreshPageRequest)ProtoUtils.copyFrom(this.mRequest);
    localRefreshPageRequest.context = PaymentUtils.createRequestContextForSharedLibrary$793b517b(this.mApiContext.applicationContext, this.mSessionData, null);
    return localRefreshPageRequest;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.api.http.SecureRefreshPageRequest
 * JD-Core Version:    0.7.0.1
 */