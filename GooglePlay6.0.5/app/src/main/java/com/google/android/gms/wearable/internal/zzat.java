package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzat
  implements Parcelable.Creator<GetFdForAssetResponse>
{
  static void zza(GetFdForAssetResponse paramGetFdForAssetResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramGetFdForAssetResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramGetFdForAssetResponse.statusCode);
    zzb.zza$377a007(paramParcel, 3, paramGetFdForAssetResponse.zzcgt, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzat
 * JD-Core Version:    0.7.0.1
 */