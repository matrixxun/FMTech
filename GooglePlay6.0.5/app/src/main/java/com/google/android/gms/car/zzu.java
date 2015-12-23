package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzu
  implements Parcelable.Creator<CarUiInfo>
{
  static void zza$16269669(CarUiInfo paramCarUiInfo, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza(paramParcel, 1, paramCarUiInfo.zzaeM);
    zzb.zzc(paramParcel, 1000, paramCarUiInfo.mVersionCode);
    zzb.zza(paramParcel, 2, paramCarUiInfo.zzaeN);
    zzb.zza(paramParcel, 3, paramCarUiInfo.zzaeO);
    zzb.zza(paramParcel, 4, paramCarUiInfo.zzaeP);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzu
 * JD-Core Version:    0.7.0.1
 */