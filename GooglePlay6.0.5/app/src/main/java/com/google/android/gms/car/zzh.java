package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzh
  implements Parcelable.Creator<CarFrxEvent>
{
  static void zza$7583a3d(CarFrxEvent paramCarFrxEvent, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramCarFrxEvent.fromState);
    zzb.zzc(paramParcel, 1000, paramCarFrxEvent.mVersionCode);
    zzb.zzc(paramParcel, 2, paramCarFrxEvent.toState);
    zzb.zzc(paramParcel, 3, paramCarFrxEvent.event);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzh
 * JD-Core Version:    0.7.0.1
 */