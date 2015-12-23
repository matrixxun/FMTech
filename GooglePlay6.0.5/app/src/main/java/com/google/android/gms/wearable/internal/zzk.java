package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzk
  implements Parcelable.Creator<CapabilityInfoParcelable>
{
  static void zza$c097acd(CapabilityInfoParcelable paramCapabilityInfoParcelable, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramCapabilityInfoParcelable.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramCapabilityInfoParcelable.mName);
    zzb.zzc$62107c48(paramParcel, 3, paramCapabilityInfoParcelable.zzcfv);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzk
 * JD-Core Version:    0.7.0.1
 */