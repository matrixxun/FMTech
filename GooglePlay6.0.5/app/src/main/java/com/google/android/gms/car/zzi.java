package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzi
  implements Parcelable.Creator<CarInfo>
{
  static void zza$cbf78dd(CarInfo paramCarInfo, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza$2cfb68bf(paramParcel, 1, paramCarInfo.manufacturer);
    zzb.zza$2cfb68bf(paramParcel, 2, paramCarInfo.model);
    zzb.zza$2cfb68bf(paramParcel, 3, paramCarInfo.modelYear);
    zzb.zza$2cfb68bf(paramParcel, 4, paramCarInfo.vehicleId);
    zzb.zzc(paramParcel, 5, paramCarInfo.headUnitProtocolMajorVersionNumber);
    zzb.zzc(paramParcel, 6, paramCarInfo.headUnitProtocolMinorVersionNumber);
    zzb.zza(paramParcel, 7, paramCarInfo.hideProjectedClock);
    zzb.zzc(paramParcel, 8, paramCarInfo.driverPosition);
    zzb.zza$2cfb68bf(paramParcel, 9, paramCarInfo.headUnitMake);
    zzb.zza$2cfb68bf(paramParcel, 10, paramCarInfo.headUnitModel);
    zzb.zza$2cfb68bf(paramParcel, 11, paramCarInfo.headUnitSoftwareBuild);
    zzb.zza$2cfb68bf(paramParcel, 12, paramCarInfo.headUnitSoftwareVersion);
    zzb.zza(paramParcel, 13, paramCarInfo.canPlayNativeMediaDuringVr);
    zzb.zza(paramParcel, 14, paramCarInfo.hidePhoneSignal);
    zzb.zza(paramParcel, 15, paramCarInfo.hideBatteryLevel);
    zzb.zza$2cfb68bf(paramParcel, 16, paramCarInfo.nickname);
    zzb.zzc(paramParcel, 1000, paramCarInfo.mVersionCode);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzi
 * JD-Core Version:    0.7.0.1
 */