package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class GetServiceRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<GetServiceRequest> CREATOR = new zzi();
  final int version;
  final int zzatF;
  int zzatG;
  String zzatH;
  IBinder zzatI;
  Scope[] zzatJ;
  Bundle zzatK;
  Account zzatL;
  
  public GetServiceRequest(int paramInt)
  {
    this.version = 2;
    this.zzatG = GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    this.zzatF = paramInt;
  }
  
  GetServiceRequest(int paramInt1, int paramInt2, int paramInt3, String paramString, IBinder paramIBinder, Scope[] paramArrayOfScope, Bundle paramBundle, Account paramAccount)
  {
    this.version = paramInt1;
    this.zzatF = paramInt2;
    this.zzatG = paramInt3;
    this.zzatH = paramString;
    Account localAccount;
    if (paramInt1 < 2)
    {
      localAccount = null;
      if (paramIBinder != null) {
        localAccount = zza.zzb(zzp.zza.zzcj(paramIBinder));
      }
    }
    for (this.zzatL = localAccount;; this.zzatL = paramAccount)
    {
      this.zzatJ = paramArrayOfScope;
      this.zzatK = paramBundle;
      return;
      this.zzatI = paramIBinder;
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzi.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.GetServiceRequest
 * JD-Core Version:    0.7.0.1
 */