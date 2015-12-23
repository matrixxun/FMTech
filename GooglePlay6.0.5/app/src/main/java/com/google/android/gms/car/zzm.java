package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzm
  implements Parcelable.Creator<CarMediaBrowserSongNode>
{
  static void zza(CarMediaBrowserSongNode paramCarMediaBrowserSongNode, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza$377a007(paramParcel, 1, paramCarMediaBrowserSongNode.song, paramInt);
    zzb.zzc(paramParcel, 1000, paramCarMediaBrowserSongNode.mVersionCode);
    zzb.zza$52910762(paramParcel, 2, paramCarMediaBrowserSongNode.albumArt);
    zzb.zzc(paramParcel, 3, paramCarMediaBrowserSongNode.durationSeconds);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzm
 * JD-Core Version:    0.7.0.1
 */