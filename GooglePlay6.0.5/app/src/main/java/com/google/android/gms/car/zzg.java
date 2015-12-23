package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzg
  implements Parcelable.Creator<CarFacet>
{
  static void zza$5d631bba(CarFacet paramCarFacet, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramCarFacet.type);
    zzb.zzc(paramParcel, 1000, paramCarFacet.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramCarFacet.packageName);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzg
 * JD-Core Version:    0.7.0.1
 */