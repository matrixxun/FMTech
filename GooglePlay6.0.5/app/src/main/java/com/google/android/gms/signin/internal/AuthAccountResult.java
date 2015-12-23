package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class AuthAccountResult
  implements Result, SafeParcelable
{
  public static final Parcelable.Creator<AuthAccountResult> CREATOR = new zza();
  final int mVersionCode;
  int zzbLZ;
  Intent zzbMa;
  
  public AuthAccountResult()
  {
    this(0);
  }
  
  public AuthAccountResult(int paramInt)
  {
    this(2, paramInt, null);
  }
  
  AuthAccountResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    this.mVersionCode = paramInt1;
    this.zzbLZ = paramInt2;
    this.zzbMa = paramIntent;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public final Status getStatus()
  {
    if (this.zzbLZ == 0) {
      return Status.zzaoz;
    }
    return Status.zzaoD;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.signin.internal.AuthAccountResult
 * JD-Core Version:    0.7.0.1
 */