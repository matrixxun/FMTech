package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ResolveAccountResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<ResolveAccountResponse> CREATOR = new zzz();
  final int mVersionCode;
  public boolean zzaps;
  public IBinder zzasY;
  public ConnectionResult zzauE;
  public boolean zzauF;
  
  public ResolveAccountResponse()
  {
    this(new ConnectionResult(8, null));
  }
  
  ResolveAccountResponse(int paramInt, IBinder paramIBinder, ConnectionResult paramConnectionResult, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mVersionCode = paramInt;
    this.zzasY = paramIBinder;
    this.zzauE = paramConnectionResult;
    this.zzaps = paramBoolean1;
    this.zzauF = paramBoolean2;
  }
  
  private ResolveAccountResponse(ConnectionResult paramConnectionResult)
  {
    this(1, null, paramConnectionResult, false, false);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    ResolveAccountResponse localResolveAccountResponse;
    do
    {
      return true;
      if (!(paramObject instanceof ResolveAccountResponse)) {
        return false;
      }
      localResolveAccountResponse = (ResolveAccountResponse)paramObject;
    } while ((this.zzauE.equals(localResolveAccountResponse.zzauE)) && (zzp.zza.zzcj(this.zzasY).equals(zzp.zza.zzcj(localResolveAccountResponse.zzasY))));
    return false;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzz.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.ResolveAccountResponse
 * JD-Core Version:    0.7.0.1
 */