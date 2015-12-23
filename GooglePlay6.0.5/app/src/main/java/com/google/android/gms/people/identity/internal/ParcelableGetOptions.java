package com.google.android.gms.people.identity.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzw.zza;

public final class ParcelableGetOptions
  implements SafeParcelable
{
  public static final zzi CREATOR = new zzi();
  final int mVersionCode;
  final String zzbtc;
  final Bundle zzbtd;
  final boolean zzbth;
  final boolean zzbvc;
  final boolean zzbvd;
  
  ParcelableGetOptions(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, String paramString, Bundle paramBundle)
  {
    this.mVersionCode = paramInt;
    this.zzbvc = paramBoolean1;
    this.zzbth = paramBoolean2;
    this.zzbtc = paramString;
    this.zzbvd = paramBoolean3;
    if (paramBundle == null) {
      paramBundle = new Bundle();
    }
    this.zzbtd = paramBundle;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final String toString()
  {
    return zzw.zzB(this).zzh("useOfflineDatabase", Boolean.valueOf(this.zzbvc)).zzh("useWebData", Boolean.valueOf(this.zzbth)).zzh("endpoint", this.zzbtc).toString();
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzi.zza$4766bf38(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.identity.internal.ParcelableGetOptions
 * JD-Core Version:    0.7.0.1
 */