package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbn
  implements Parcelable.Creator<StationPreset>
{
  static void zza$69af1994(StationPreset paramStationPreset, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramStationPreset.type);
    zzb.zzc(paramParcel, 1000, paramStationPreset.mVersionCode);
    zzb.zzc(paramParcel, 2, paramStationPreset.channel);
    zzb.zzc(paramParcel, 3, paramStationPreset.subChannel);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzbn
 * JD-Core Version:    0.7.0.1
 */