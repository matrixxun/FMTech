package com.google.android.gms.ads.internal.util.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzc
  implements Parcelable.Creator<VersionInfoParcel>
{
  static void zza$1ad68b2d(VersionInfoParcel paramVersionInfoParcel, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramVersionInfoParcel.versionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramVersionInfoParcel.afmaVersion);
    zzb.zzc(paramParcel, 3, paramVersionInfoParcel.zzLY);
    zzb.zzc(paramParcel, 4, paramVersionInfoParcel.zzLZ);
    zzb.zza(paramParcel, 5, paramVersionInfoParcel.zzMa);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.util.client.zzc
 * JD-Core Version:    0.7.0.1
 */