package com.google.android.gms.wearable;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzb
  implements Parcelable.Creator<ConnectionConfiguration>
{
  static void zza$3e41336a(ConnectionConfiguration paramConnectionConfiguration, Parcel paramParcel)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, 20293);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramConnectionConfiguration.mVersionCode);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 2, paramConnectionConfiguration.mName);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 3, paramConnectionConfiguration.zzaLE);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 4, paramConnectionConfiguration.zzTv);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 5, paramConnectionConfiguration.zzaBG);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 6, paramConnectionConfiguration.zzces);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 7, paramConnectionConfiguration.zzQY);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 8, paramConnectionConfiguration.zzcet);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 9, paramConnectionConfiguration.zzceu);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 10, paramConnectionConfiguration.zzcev);
    com.google.android.gms.common.internal.safeparcel.zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.zzb
 * JD-Core Version:    0.7.0.1
 */