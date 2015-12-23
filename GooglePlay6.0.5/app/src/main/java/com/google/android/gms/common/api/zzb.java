package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzb
  implements Parcelable.Creator<Status>
{
  static void zza(Status paramStatus, Parcel paramParcel, int paramInt)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, 20293);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramStatus.zzakr);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1000, paramStatus.mVersionCode);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 2, paramStatus.zzanv);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$377a007(paramParcel, 3, paramStatus.mPendingIntent, paramInt);
    com.google.android.gms.common.internal.safeparcel.zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.zzb
 * JD-Core Version:    0.7.0.1
 */