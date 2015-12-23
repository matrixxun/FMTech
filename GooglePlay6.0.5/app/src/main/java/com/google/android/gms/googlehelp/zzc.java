package com.google.android.gms.googlehelp;

import android.os.AsyncTask;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.googlehelp.internal.common.zzb;

public final class zzc
{
  public static final Api<Object> API = new Api("Help.API", zzTR, zzTQ);
  public static final Api.zzc<com.google.android.gms.googlehelp.internal.common.zzc> zzTQ = new Api.zzc();
  public static final Api.zza<com.google.android.gms.googlehelp.internal.common.zzc, Object> zzTR = new Api.zza() {};
  public static final zza zzbbc = new zzb();
  
  public static void zza(GoogleApiClient paramGoogleApiClient, final zza paramzza)
  {
    new AsyncTask() {}.execute(new Void[0]);
  }
  
  public static abstract interface zza
  {
    public abstract PendingResult<Status> zzo$3946365();
    
    public abstract void zzzq();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.zzc
 * JD-Core Version:    0.7.0.1
 */