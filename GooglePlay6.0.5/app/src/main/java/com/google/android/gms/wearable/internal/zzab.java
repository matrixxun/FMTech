package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzab
  implements Parcelable.Creator<DataItemAssetParcelable>
{
  static void zza$4a7bd096(DataItemAssetParcelable paramDataItemAssetParcelable, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramDataItemAssetParcelable.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramDataItemAssetParcelable.zzyx);
    zzb.zza$2cfb68bf(paramParcel, 3, paramDataItemAssetParcelable.zzvB);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzab
 * JD-Core Version:    0.7.0.1
 */