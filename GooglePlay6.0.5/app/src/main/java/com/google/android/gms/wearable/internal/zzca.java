package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzca
  implements Parcelable.Creator<StorageInfoResponse>
{
  static void zza$3afab916(StorageInfoResponse paramStorageInfoResponse, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramStorageInfoResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramStorageInfoResponse.statusCode);
    zzb.zza(paramParcel, 3, paramStorageInfoResponse.totalSizeBytes);
    zzb.zzc$62107c48(paramParcel, 4, paramStorageInfoResponse.packageStorageInfo);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzca
 * JD-Core Version:    0.7.0.1
 */