package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbk
  implements Parcelable.Creator<RadioStationInfo.HdData>
{
  static void zza$71826f3c(RadioStationInfo.HdData paramHdData, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1000, paramHdData.mVersionCode);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzbk
 * JD-Core Version:    0.7.0.1
 */