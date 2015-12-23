package com.google.android.gms.safetynet;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.PendingResult;
import java.util.concurrent.TimeUnit;

public final class SafetyNetFirstPartyClient
{
  public static String getId(Context paramContext)
  {
    GoogleApiClient localGoogleApiClient = new GoogleApiClient.Builder(paramContext).addApi(SafetyNet.API).build();
    String str;
    if (!localGoogleApiClient.blockingConnect(15000L, TimeUnit.MILLISECONDS).isSuccess()) {
      str = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
    }
    for (;;)
    {
      return str;
      try
      {
        SafetyNetFirstPartyApi.IdResult localIdResult = (SafetyNetFirstPartyApi.IdResult)SafetyNet.SafetyNetFirstPartyApi.getId(localGoogleApiClient).await(15000L, TimeUnit.MILLISECONDS);
        localGoogleApiClient.disconnect();
        str = localIdResult.getId();
        if (str != null) {
          continue;
        }
        return "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
      }
      finally
      {
        localGoogleApiClient.disconnect();
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.safetynet.SafetyNetFirstPartyClient
 * JD-Core Version:    0.7.0.1
 */