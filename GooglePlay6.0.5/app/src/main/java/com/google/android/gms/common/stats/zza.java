package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zza
  implements Parcelable.Creator<ConnectionEvent>
{
  static void zza$151b04f0(ConnectionEvent paramConnectionEvent, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramConnectionEvent.mVersionCode);
    zzb.zza(paramParcel, 2, paramConnectionEvent.zzavM);
    zzb.zza$2cfb68bf(paramParcel, 4, paramConnectionEvent.zzavO);
    zzb.zza$2cfb68bf(paramParcel, 5, paramConnectionEvent.zzavP);
    zzb.zza$2cfb68bf(paramParcel, 6, paramConnectionEvent.zzavQ);
    zzb.zza$2cfb68bf(paramParcel, 7, paramConnectionEvent.zzavR);
    zzb.zza$2cfb68bf(paramParcel, 8, paramConnectionEvent.zzavS);
    zzb.zza(paramParcel, 10, paramConnectionEvent.zzavU);
    zzb.zza(paramParcel, 11, paramConnectionEvent.zzavV);
    zzb.zzc(paramParcel, 12, paramConnectionEvent.zzavN);
    zzb.zza$2cfb68bf(paramParcel, 13, paramConnectionEvent.zzavT);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.stats.zza
 * JD-Core Version:    0.7.0.1
 */