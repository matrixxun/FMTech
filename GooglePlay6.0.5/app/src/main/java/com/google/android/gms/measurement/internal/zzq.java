package com.google.android.gms.measurement.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.internal.zzx;

class zzq
  extends BroadcastReceiver
{
  static final String zzQW = zzq.class.getName();
  boolean zzQX;
  boolean zzQY;
  final zzt zzbkM;
  
  zzq(zzt paramzzt)
  {
    zzx.zzC(paramzzt);
    this.zzbkM = paramzzt;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    this.zzbkM.zziL();
    String str = paramIntent.getAction();
    this.zzbkM.zzBh().zzbne.zzm("NetworkBroadcastReceiver received action", str);
    if ("android.net.conn.CONNECTIVITY_CHANGE".equals(str))
    {
      final boolean bool = this.zzbkM.zzCH().isNetworkConnected();
      if (this.zzQY != bool)
      {
        this.zzQY = bool;
        this.zzbkM.zzBY().zzg(new Runnable()
        {
          public final void run()
          {
            zzq.zza(zzq.this).zzCO();
          }
        });
      }
      return;
    }
    this.zzbkM.zzBh().zzbmZ.zzm("NetworkBroadcastReceiver received unknown action", str);
  }
  
  public final void unregister()
  {
    this.zzbkM.zziL();
    this.zzbkM.zzBY().checkOnWorkerThread();
    this.zzbkM.zzBY().checkOnWorkerThread();
    if (!this.zzQX) {
      return;
    }
    this.zzbkM.zzBh().zzbne.zzeB("Unregistering connectivity change receiver");
    this.zzQX = false;
    this.zzQY = false;
    Context localContext = this.zzbkM.mContext;
    try
    {
      localContext.unregisterReceiver(this);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      this.zzbkM.zzBh().zzbmW.zzm("Failed to unregister the network broadcast receiver", localIllegalArgumentException);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzq
 * JD-Core Version:    0.7.0.1
 */