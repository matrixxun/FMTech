package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzo
  implements Parcelable.Creator<CarMediaBrowserSourceNode.CarMediaList>
{
  static void zza$17868e60(CarMediaBrowserSourceNode.CarMediaList paramCarMediaList, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza$2cfb68bf(paramParcel, 1, paramCarMediaList.path);
    zzb.zzc(paramParcel, 1000, paramCarMediaList.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramCarMediaList.name);
    zzb.zza$52910762(paramParcel, 3, paramCarMediaList.albumArt);
    zzb.zzc(paramParcel, 4, paramCarMediaList.type);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzo
 * JD-Core Version:    0.7.0.1
 */