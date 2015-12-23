package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNetFirstPartyApi;
import com.google.android.gms.safetynet.SafetyNetFirstPartyApi.IdResult;

public final class zzvn
  implements SafetyNetFirstPartyApi
{
  public final PendingResult<SafetyNetFirstPartyApi.IdResult> getId(GoogleApiClient paramGoogleApiClient)
  {
    paramGoogleApiClient.zza(new zzvi(paramGoogleApiClient) {});
  }
  
  private static final class zza
    extends zzvh
  {
    private final zzmu.zzb<SafetyNetFirstPartyApi.IdResult> zzaqB;
    
    public zza(zzmu.zzb<SafetyNetFirstPartyApi.IdResult> paramzzb)
    {
      this.zzaqB = paramzzb;
    }
    
    public final void onGetIdResults(String paramString)
    {
      this.zzaqB.zzu(new zzvn.zzb(Status.zzaoz, paramString));
    }
  }
  
  private static final class zzb
    implements SafetyNetFirstPartyApi.IdResult
  {
    private final Status zzUc;
    private final String zzyx;
    
    public zzb(Status paramStatus, String paramString)
    {
      this.zzUc = paramStatus;
      this.zzyx = paramString;
    }
    
    public final String getId()
    {
      return this.zzyx;
    }
    
    public final Status getStatus()
    {
      return this.zzUc;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzvn
 * JD-Core Version:    0.7.0.1
 */