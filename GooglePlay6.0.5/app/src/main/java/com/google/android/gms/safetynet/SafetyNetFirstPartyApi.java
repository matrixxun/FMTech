package com.google.android.gms.safetynet;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;

public abstract interface SafetyNetFirstPartyApi
{
  public abstract PendingResult<IdResult> getId(GoogleApiClient paramGoogleApiClient);
  
  public static abstract interface IdResult
    extends Result
  {
    public abstract String getId();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.safetynet.SafetyNetFirstPartyApi
 * JD-Core Version:    0.7.0.1
 */