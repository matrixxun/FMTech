package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbm
  implements Parcelable.Creator<RadioStationInfo.RdsData>
{
  static void zza$3345f68f(RadioStationInfo.RdsData paramRdsData, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza$62107c48(paramParcel, 1, paramRdsData.alternativeFrequencies);
    zzb.zzc(paramParcel, 1000, paramRdsData.mVersionCode);
    zzb.zzc(paramParcel, 2, paramRdsData.programId);
    zzb.zzc(paramParcel, 3, paramRdsData.musicSpeechSwitch);
    zzb.zza$2cfb68bf(paramParcel, 4, paramRdsData.programServiceName);
    zzb.zzc(paramParcel, 5, paramRdsData.programType);
    zzb.zza$2cfb68bf(paramParcel, 6, paramRdsData.programTypeName);
    zzb.zza$2cfb68bf(paramParcel, 7, paramRdsData.radioText);
    zzb.zza(paramParcel, 8, paramRdsData.trafficProgramFlag);
    zzb.zza(paramParcel, 9, paramRdsData.trafficAnnouncementFlag);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzbm
 * JD-Core Version:    0.7.0.1
 */