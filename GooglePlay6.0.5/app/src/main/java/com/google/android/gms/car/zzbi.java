package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbi
  implements Parcelable.Creator<RadioState>
{
  static void zza(RadioState paramRadioState, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza(paramParcel, 1, paramRadioState.radioSourceEnabled);
    zzb.zzc(paramParcel, 1000, paramRadioState.mVersionCode);
    zzb.zza(paramParcel, 2, paramRadioState.radioMuted);
    zzb.zzc(paramParcel, 3, paramRadioState.activeRadioId);
    zzb.zza$377a007(paramParcel, 4, paramRadioState.stationInfo, paramInt);
    zzb.zzc$62107c48(paramParcel, 5, paramRadioState.programList);
    zzb.zzc$62107c48(paramParcel, 6, paramRadioState.stationPresetLists);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzbi
 * JD-Core Version:    0.7.0.1
 */