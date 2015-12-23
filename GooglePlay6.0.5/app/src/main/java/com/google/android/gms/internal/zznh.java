package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import com.google.android.gms.common.GoogleApiAvailability;

abstract class zznh
  extends BroadcastReceiver
{
  protected Context mContext;
  
  public static <T extends zznh> T zza(Context paramContext, T paramT)
  {
    GoogleApiAvailability.getInstance();
    return zza$645b353c(paramContext, paramT);
  }
  
  public static <T extends zznh> T zza$645b353c(Context paramContext, T paramT)
  {
    IntentFilter localIntentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
    localIntentFilter.addDataScheme("package");
    paramContext.registerReceiver(paramT, localIntentFilter);
    paramT.mContext = paramContext;
    if (!GoogleApiAvailability.zzi(paramContext, "com.google.android.gms"))
    {
      paramT.zzpl();
      paramT.unregister();
      paramT = null;
    }
    return paramT;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Uri localUri = paramIntent.getData();
    String str = null;
    if (localUri != null) {
      str = localUri.getSchemeSpecificPart();
    }
    if ("com.google.android.gms".equals(str))
    {
      zzpl();
      unregister();
    }
  }
  
  public final void unregister()
  {
    try
    {
      if (this.mContext != null) {
        this.mContext.unregisterReceiver(this);
      }
      this.mContext = null;
      return;
    }
    finally {}
  }
  
  protected abstract void zzpl();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznh
 * JD-Core Version:    0.7.0.1
 */