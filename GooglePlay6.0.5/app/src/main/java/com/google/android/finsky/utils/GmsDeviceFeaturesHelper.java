package com.google.android.finsky.utils;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.deviceconnection.DeviceConnections;
import com.google.android.gms.deviceconnection.DeviceConnections.GetDeviceFeaturesResult;

public final class GmsDeviceFeaturesHelper
  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
  public final Context mContext;
  public GoogleApiClient mGoogleApiClient;
  
  public GmsDeviceFeaturesHelper(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public final void onConnected(Bundle paramBundle)
  {
    DeviceConnections.getDeviceFeaturesRestricted(this.mGoogleApiClient).setResultCallback(new ResultCallback() {});
  }
  
  public final void onConnectionFailed(ConnectionResult paramConnectionResult)
  {
    int i = paramConnectionResult.zzakr;
    if ((i == 1) || (i == 2) || (i == 3)) {
      return;
    }
    FinskyLog.w("onConnectionFailed result: %s", new Object[] { paramConnectionResult });
  }
  
  public final void onConnectionSuspended(int paramInt) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.GmsDeviceFeaturesHelper
 * JD-Core Version:    0.7.0.1
 */