package com.google.android.vending.verifier.api;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.config.G;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadRequest;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadResponse;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;

public final class PackageVerificationRequest
  extends BaseVerificationRequestWithResult<PackageVerificationResult, CsdClient.ClientDownloadRequest>
{
  public PackageVerificationRequest(String paramString, Response.Listener<PackageVerificationResult> paramListener, Response.ErrorListener paramErrorListener, CsdClient.ClientDownloadRequest paramClientDownloadRequest)
  {
    super(paramString, paramListener, paramErrorListener, paramClientDownloadRequest);
    this.mRetryPolicy = new DefaultRetryPolicy(((Integer)G.verifyAppsTimeoutMs.get()).intValue(), ((Integer)G.verifyAppsNumRetries.get()).intValue(), ((Float)G.verifyAppsBackoffMultiplier.get()).floatValue());
  }
  
  protected final Response<PackageVerificationResult> parseNetworkResponse(NetworkResponse paramNetworkResponse)
  {
    try
    {
      byte[] arrayOfByte = paramNetworkResponse.data;
      CsdClient.ClientDownloadResponse localClientDownloadResponse = (CsdClient.ClientDownloadResponse)MessageNano.mergeFrom$1ec43da(new CsdClient.ClientDownloadResponse(), arrayOfByte, arrayOfByte.length);
      return Response.success(PackageVerificationResult.fromResponse(localClientDownloadResponse), null);
    }
    catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
    {
      return Response.error(new VolleyError(localInvalidProtocolBufferNanoException));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.api.PackageVerificationRequest
 * JD-Core Version:    0.7.0.1
 */