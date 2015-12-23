package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzr
  implements Parcelable.Creator<CarPhoneStatus.CarCall>
{
  static void zza$31dce7c3(CarPhoneStatus.CarCall paramCarCall, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramCarCall.state);
    zzb.zzc(paramParcel, 1000, paramCarCall.mVersionCode);
    zzb.zzc(paramParcel, 2, paramCarCall.callDurationSeconds);
    zzb.zza$2cfb68bf(paramParcel, 3, paramCarCall.callerNumber);
    zzb.zza$2cfb68bf(paramParcel, 4, paramCarCall.callerId);
    zzb.zza$2cfb68bf(paramParcel, 5, paramCarCall.callerNumberType);
    zzb.zza$52910762(paramParcel, 6, paramCarCall.callerThumbnail);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzr
 * JD-Core Version:    0.7.0.1
 */