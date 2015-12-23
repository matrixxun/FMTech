package com.google.android.gms.mdm;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzrr;
import com.google.android.gms.internal.zzrx;
import com.google.android.gms.internal.zzsc;

@Deprecated
public final class NetworkQualityUploader
{
  private static boolean zzbkB = true;
  private static final GoogleApiClient.OnConnectionFailedListener zzbkC = new GoogleApiClient.OnConnectionFailedListener()
  {
    public final void onConnectionFailed(ConnectionResult paramAnonymousConnectionResult)
    {
      if (zzsc.DEBUG) {
        Log.d("Herrevad", "failed to connect to network quality service");
      }
      if ((paramAnonymousConnectionResult.zzakr != 7) && (paramAnonymousConnectionResult.zzakr != 8)) {
        NetworkQualityUploader.zzav$138603();
      }
    }
  };
  
  public static void logNetworkStats$5480c1b1(Context paramContext, Long paramLong, Bundle paramBundle)
  {
    if (!zzbkB) {
      return;
    }
    GoogleApiClient localGoogleApiClient = new GoogleApiClient.Builder(paramContext).addOnConnectionFailedListener(zzbkC).addApi(zzrr.API).build();
    localGoogleApiClient.connect();
    try
    {
      zzrr.zzbby.zza(localGoogleApiClient, null, paramLong, null, null, paramBundle).setResultCallback(new ResultCallback() {});
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      Log.w("Herrevad", "Exception in logNetworkStats.  This call should always fail silently, so we will swallow this: " + localRuntimeException);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.mdm.NetworkQualityUploader
 * JD-Core Version:    0.7.0.1
 */