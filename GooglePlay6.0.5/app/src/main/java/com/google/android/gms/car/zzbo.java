package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbo
  implements Parcelable.Creator<StationPresetList>
{
  static void zza$740924ee(StationPresetList paramStationPresetList, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza$2cfb68bf(paramParcel, 1, paramStationPresetList.name);
    zzb.zzc(paramParcel, 1000, paramStationPresetList.mVersionCode);
    zzb.zza$62107c48(paramParcel, 2, paramStationPresetList.restrictedStationTypes);
    zzb.zzc$62107c48(paramParcel, 3, paramStationPresetList.presets);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzbo
 * JD-Core Version:    0.7.0.1
 */