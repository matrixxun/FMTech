package com.google.android.vending.verifier.api;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.google.android.finsky.config.G;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadStatsRequest;

public final class PackageVerificationStatsRequest
  extends BaseVerificationRequest<NetworkResponse, CsdClient.ClientDownloadStatsRequest>
{
  public PackageVerificationStatsRequest(String paramString, Response.ErrorListener paramErrorListener, CsdClient.ClientDownloadStatsRequest paramClientDownloadStatsRequest)
  {
    super(paramString, paramErrorListener, paramClientDownloadStatsRequest);
    this.mRetryPolicy = new DefaultRetryPolicy(((Integer)G.verifyAppsUserDecisionTimeoutMs.get()).intValue(), ((Integer)G.verifyAppsUserDecisionNumRetries.get()).intValue(), ((Float)G.verifyAppsUserDecisionBackoffMultiplier.get()).floatValue());
  }
  
  protected final Response<NetworkResponse> parseNetworkResponse(NetworkResponse paramNetworkResponse)
  {
    return Response.success(paramNetworkResponse, null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.api.PackageVerificationStatsRequest
 * JD-Core Version:    0.7.0.1
 */