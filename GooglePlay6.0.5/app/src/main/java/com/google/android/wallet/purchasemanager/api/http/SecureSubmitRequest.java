package com.google.android.wallet.purchasemanager.api.http;

import com.android.volley.Response.ErrorListener;
import com.google.android.wallet.common.api.http.ApiContext;
import com.google.android.wallet.common.api.http.SecureRequest;
import com.google.android.wallet.common.sidecar.BackgroundEventRequestResponseListener;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.common.util.ProtoUtils;
import com.google.moneta.orchestration.ui.purchasemanager.Api.SubmitRequest;
import com.google.moneta.orchestration.ui.purchasemanager.Api.SubmitResponse;
import com.google.protobuf.nano.MessageNano;
import java.util.Map;

public final class SecureSubmitRequest
  extends SecureRequest<SecureSubmitRequest, Api.SubmitResponse>
{
  public final Api.SubmitRequest mRequest;
  
  public SecureSubmitRequest(ApiContext paramApiContext, Api.SubmitRequest paramSubmitRequest, Map<String, String> paramMap, byte[] paramArrayOfByte, BackgroundEventRequestResponseListener<SecureSubmitRequest, Api.SubmitResponse> paramBackgroundEventRequestResponseListener, Response.ErrorListener paramErrorListener)
  {
    super(paramApiContext, paramMap, paramArrayOfByte, Api.SubmitResponse.class, paramBackgroundEventRequestResponseListener, paramErrorListener);
    this.mRequest = paramSubmitRequest;
  }
  
  protected final String getActionUrl()
  {
    return "purchasemanager/submit";
  }
  
  public final int getBackgroundEventReceivedType()
  {
    return 725;
  }
  
  public final int getBackgroundEventSentType()
  {
    return 724;
  }
  
  public final MessageNano getProtoRequest()
  {
    Api.SubmitRequest localSubmitRequest = (Api.SubmitRequest)ProtoUtils.copyFrom(this.mRequest);
    localSubmitRequest.context = PaymentUtils.createRequestContextForSharedLibrary$793b517b(this.mApiContext.applicationContext, this.mSessionData, null);
    return localSubmitRequest;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.purchasemanager.api.http.SecureSubmitRequest
 * JD-Core Version:    0.7.0.1
 */