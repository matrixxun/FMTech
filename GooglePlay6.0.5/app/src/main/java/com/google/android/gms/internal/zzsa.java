package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

@Deprecated
public final class zzsa
  implements zzrx
{
  private final Api.zzc<zzry> zzbbB;
  
  public zzsa(Api.zzc<zzry> paramzzc)
  {
    this.zzbbB = paramzzc;
  }
  
  public final PendingResult<Status> zza(GoogleApiClient paramGoogleApiClient, Integer paramInteger1, final Long paramLong, Integer paramInteger2, Integer paramInteger3, final Bundle paramBundle)
  {
    paramGoogleApiClient.zza(new zza(this.zzbbB, paramGoogleApiClient) {});
  }
  
  private static abstract class zza
    extends zzmu.zza<Status, zzry>
  {
    public zza(Api.zzc<zzry> paramzzc, GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzsa
 * JD-Core Version:    0.7.0.1
 */