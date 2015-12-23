package com.google.android.finsky.api;

import android.content.Context;
import android.net.NetworkInfo;
import com.google.android.finsky.receivers.NetworkStateChangedReceiver;

public final class NetworkStateInfo
  implements NetworkStateProvider
{
  private Context mContext;
  
  public NetworkStateInfo(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public final NetworkInfo getCurrentNetworkInfo()
  {
    return NetworkStateChangedReceiver.getCachedNetworkInfo(this.mContext);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.NetworkStateInfo
 * JD-Core Version:    0.7.0.1
 */