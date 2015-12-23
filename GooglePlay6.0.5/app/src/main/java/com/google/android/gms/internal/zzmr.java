package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;

public final class zzmr
  extends zzj<zzmt>
{
  public zzmr(Context paramContext, Looper paramLooper, zzf paramzzf, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramLooper, 40, paramzzf, paramConnectionCallbacks, paramOnConnectionFailedListener);
  }
  
  protected final String zzgp()
  {
    return "com.google.android.gms.clearcut.service.START";
  }
  
  protected final String zzgq()
  {
    return "com.google.android.gms.clearcut.internal.IClearcutLoggerService";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzmr
 * JD-Core Version:    0.7.0.1
 */