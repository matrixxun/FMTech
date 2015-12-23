package com.google.android.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GCMBroadcastReceiver
  extends BroadcastReceiver
{
  private static boolean mReceiverSet = false;
  
  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    Log.v("GCMBroadcastReceiver", "onReceive: " + paramIntent.getAction());
    if (!mReceiverSet)
    {
      mReceiverSet = true;
      String str2 = getClass().getName();
      if (!str2.equals(GCMBroadcastReceiver.class.getName()))
      {
        Log.v("GCMRegistrar", "Setting the name of retry receiver class to " + str2);
        GCMRegistrar.sRetryReceiverClassName = str2;
      }
    }
    String str1 = paramContext.getPackageName() + ".GCMIntentService";
    Log.v("GCMBroadcastReceiver", "GCM IntentService class: " + str1);
    GCMBaseIntentService.runIntentInService(paramContext, paramIntent, str1);
    setResult(-1, null, null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gcm.GCMBroadcastReceiver
 * JD-Core Version:    0.7.0.1
 */