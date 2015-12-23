package com.google.android.finsky.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkStateChangedReceiver
  extends BroadcastReceiver
{
  private static NetworkInfo sCachedNetworkInfo;
  private static final Object sLock = new Object();
  
  public static void flushCachedState()
  {
    synchronized (sLock)
    {
      sCachedNetworkInfo = null;
      return;
    }
  }
  
  public static NetworkInfo getCachedNetworkInfo(Context paramContext)
  {
    synchronized (sLock)
    {
      if (sCachedNetworkInfo == null) {
        updateCachedNetworkInfo(paramContext);
      }
      NetworkInfo localNetworkInfo = sCachedNetworkInfo;
      return localNetworkInfo;
    }
  }
  
  private static void updateCachedNetworkInfo(Context paramContext)
  {
    synchronized (sLock)
    {
      sCachedNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      return;
    }
  }
  
  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    updateCachedNetworkInfo(paramContext);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.NetworkStateChangedReceiver
 * JD-Core Version:    0.7.0.1
 */