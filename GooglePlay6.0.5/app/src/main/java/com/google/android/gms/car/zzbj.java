package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbj
  implements Parcelable.Creator<RadioStationInfo>
{
  static void zza(RadioStationInfo paramRadioStationInfo, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramRadioStationInfo.type);
    zzb.zzc(paramParcel, 1000, paramRadioStationInfo.mVersionCode);
    zzb.zzc(paramParcel, 2, paramRadioStationInfo.channel);
    zzb.zzc(paramParcel, 3, paramRadioStationInfo.subChannel);
    zzb.zza$377a007(paramParcel, 4, paramRadioStationInfo.metaData, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzbj
 * JD-Core Version:    0.7.0.1
 */