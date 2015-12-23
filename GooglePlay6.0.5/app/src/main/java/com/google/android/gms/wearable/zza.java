package com.google.android.gms.wearable;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zza
  implements Parcelable.Creator<Asset>
{
  static void zza(Asset paramAsset, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramAsset.mVersionCode);
    zzb.zza$52910762(paramParcel, 2, paramAsset.mData);
    zzb.zza$2cfb68bf(paramParcel, 3, paramAsset.zzcem);
    zzb.zza$377a007(paramParcel, 4, paramAsset.zzcen, paramInt);
    zzb.zza$377a007(paramParcel, 5, paramAsset.uri, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.zza
 * JD-Core Version:    0.7.0.1
 */