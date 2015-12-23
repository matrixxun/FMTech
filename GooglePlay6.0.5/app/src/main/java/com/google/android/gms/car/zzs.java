package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzs
  implements Parcelable.Creator<CarPhoneStatus>
{
  static void zza(CarPhoneStatus paramCarPhoneStatus, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza$2d7953c6(paramParcel, 1, paramCarPhoneStatus.calls, paramInt);
    zzb.zzc(paramParcel, 1000, paramCarPhoneStatus.mVersionCode);
    zzb.zzc(paramParcel, 2, paramCarPhoneStatus.signalStrength);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzs
 * JD-Core Version:    0.7.0.1
 */