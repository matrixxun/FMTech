package com.google.android.vending.verifier.api;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadResponse;
import com.google.android.vending.verifier.protos.CsdClient.ClientMultiDownloadRequest;
import com.google.android.vending.verifier.protos.CsdClient.ClientMultiDownloadResponse;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.io.UnsupportedEncodingException;

public final class PackageVerificationMultiRequest
  extends BaseVerificationRequestWithResult<PackageVerificationResult[], CsdClient.ClientMultiDownloadRequest>
{
  public PackageVerificationMultiRequest(String paramString, Response.Listener<PackageVerificationResult[]> paramListener, Response.ErrorListener paramErrorListener, CsdClient.ClientMultiDownloadRequest paramClientMultiDownloadRequest)
  {
    super(paramString, paramListener, paramErrorListener, paramClientMultiDownloadRequest);
    this.mRetryPolicy = new DefaultRetryPolicy(((Integer)G.verifyInstalledPackagesTimeoutMs.get()).intValue(), ((Integer)G.verifyInstalledPackagesNumRetries.get()).intValue(), ((Float)G.verifyInstalledPackagesBackoffMultiplier.get()).floatValue());
  }
  
  protected final Response<PackageVerificationResult[]> parseNetworkResponse(NetworkResponse paramNetworkResponse)
  {
    PackageVerificationResult[] arrayOfPackageVerificationResult;
    int i;
    int j;
    for (;;)
    {
      try
      {
        byte[] arrayOfByte = paramNetworkResponse.data;
        arrayOfClientDownloadResponse = ((CsdClient.ClientMultiDownloadResponse)MessageNano.mergeFrom$1ec43da(new CsdClient.ClientMultiDownloadResponse(), arrayOfByte, arrayOfByte.length)).responses;
        arrayOfPackageVerificationResult = new PackageVerificationResult[((CsdClient.ClientMultiDownloadRequest)this.mRequest).requests.length];
        i = 0;
        j = 0;
        k = 0;
        if (k >= arrayOfClientDownloadResponse.length) {
          break;
        }
        if (!arrayOfClientDownloadResponse[k].hasRequestId) {
          break label146;
        }
      }
      catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
      {
        CsdClient.ClientDownloadResponse[] arrayOfClientDownloadResponse;
        int k;
        int m;
        return Response.error(new VolleyError(localInvalidProtocolBufferNanoException));
      }
      try
      {
        m = Integer.parseInt(new String(arrayOfClientDownloadResponse[k].requestId, "UTF-8"), 16);
        arrayOfPackageVerificationResult[m] = PackageVerificationResult.fromResponse(arrayOfClientDownloadResponse[k]);
        k++;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        i++;
        continue;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        throw new RuntimeException(localUnsupportedEncodingException);
      }
      continue;
      label146:
      j++;
    }
    if (i > 0) {
      FinskyLog.d("Got %d responses with an invalid request id", new Object[0]);
    }
    if (j > 0) {
      FinskyLog.d("Got %d responses with a blank request id", new Object[0]);
    }
    return Response.success(arrayOfPackageVerificationResult, null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.api.PackageVerificationMultiRequest
 * JD-Core Version:    0.7.0.1
 */