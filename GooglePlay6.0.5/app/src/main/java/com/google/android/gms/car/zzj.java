package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzj
  implements Parcelable.Creator<CarInstrumentClusterInfo>
{
  static void zza$701050d0(CarInstrumentClusterInfo paramCarInstrumentClusterInfo, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramCarInstrumentClusterInfo.zzaea);
    zzb.zzc(paramParcel, 1000, paramCarInstrumentClusterInfo.mVersionCode);
    zzb.zzc(paramParcel, 2, paramCarInstrumentClusterInfo.type);
    zzb.zzc(paramParcel, 3, paramCarInstrumentClusterInfo.zzaeb);
    zzb.zzc(paramParcel, 4, paramCarInstrumentClusterInfo.zzaec);
    zzb.zzc(paramParcel, 5, paramCarInstrumentClusterInfo.zzaed);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzj
 * JD-Core Version:    0.7.0.1
 */