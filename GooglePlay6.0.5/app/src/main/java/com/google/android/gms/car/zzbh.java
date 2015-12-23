package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbh
  implements Parcelable.Creator<RadioProperties>
{
  static void zza$7bbcad71(RadioProperties paramRadioProperties, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramRadioProperties.radioId);
    zzb.zzc(paramParcel, 1000, paramRadioProperties.mVersionCode);
    zzb.zzc(paramParcel, 2, paramRadioProperties.radioType);
    zzb.zzc$62107c48(paramParcel, 3, paramRadioProperties.channelRangeList);
    zzb.zza$62107c48(paramParcel, 4, paramRadioProperties.channelSpacingList);
    zzb.zzc(paramParcel, 5, paramRadioProperties.channelSpacing);
    zzb.zza(paramParcel, 6, paramRadioProperties.backgroundTuner);
    zzb.zzc(paramParcel, 7, paramRadioProperties.region);
    zzb.zzc(paramParcel, 8, paramRadioProperties.rds);
    zzb.zza(paramParcel, 9, paramRadioProperties.alternativeFrequencySwitch);
    zzb.zza(paramParcel, 10, paramRadioProperties.trafficAnnouncement);
    zzb.zzc(paramParcel, 11, paramRadioProperties.trafficService);
    zzb.zza(paramParcel, 12, paramRadioProperties.audioLoopback);
    zzb.zza(paramParcel, 13, paramRadioProperties.radioOnlyMuteCapability);
    zzb.zzc(paramParcel, 14, paramRadioProperties.stationPresetsAccess);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzbh
 * JD-Core Version:    0.7.0.1
 */