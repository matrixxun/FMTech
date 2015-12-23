package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzb
  implements Parcelable.Creator<SafeBrowsingData>
{
  static void zza$7e0dbe96(SafeBrowsingData paramSafeBrowsingData, Parcel paramParcel)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, 20293);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramSafeBrowsingData.mVersionCode);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 2, paramSafeBrowsingData.zzbKt);
    com.google.android.gms.common.internal.safeparcel.zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.safetynet.zzb
 * JD-Core Version:    0.7.0.1
 */