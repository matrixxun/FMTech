package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzp
  implements Parcelable.Creator<CarMediaBrowserListNode.CarMediaSong>
{
  static void zza$4fb58b4(CarMediaBrowserListNode.CarMediaSong paramCarMediaSong, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza$2cfb68bf(paramParcel, 1, paramCarMediaSong.path);
    zzb.zzc(paramParcel, 1000, paramCarMediaSong.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramCarMediaSong.name);
    zzb.zza$2cfb68bf(paramParcel, 3, paramCarMediaSong.artist);
    zzb.zza$2cfb68bf(paramParcel, 4, paramCarMediaSong.album);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzp
 * JD-Core Version:    0.7.0.1
 */