package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzq
  implements Parcelable.Creator<CarMediaBrowserRootNode.CarMediaSource>
{
  static void zza$5f9abf6(CarMediaBrowserRootNode.CarMediaSource paramCarMediaSource, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza$2cfb68bf(paramParcel, 1, paramCarMediaSource.sourcePath);
    zzb.zzc(paramParcel, 1000, paramCarMediaSource.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramCarMediaSource.name);
    zzb.zza$52910762(paramParcel, 3, paramCarMediaSource.albumArt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzq
 * JD-Core Version:    0.7.0.1
 */