package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzd
  implements Parcelable.Creator<CarCall>
{
  static void zza(CarCall paramCarCall, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramCarCall.id);
    zzb.zzc(paramParcel, 1000, paramCarCall.mVersionCode);
    zzb.zza$377a007(paramParcel, 2, paramCarCall.parent, paramInt);
    zzb.zzb$62107c48(paramParcel, 3, paramCarCall.cannedTextResponses);
    zzb.zza$2cfb68bf(paramParcel, 4, paramCarCall.remainingPostDialSequence);
    zzb.zzc(paramParcel, 5, paramCarCall.state);
    zzb.zza$377a007(paramParcel, 6, paramCarCall.details, paramInt);
    zzb.zza(paramParcel, 7, paramCarCall.hasChildren);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzd
 * JD-Core Version:    0.7.0.1
 */