package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbr
  implements Parcelable.Creator<PackageStorageInfo>
{
  static void zza$7f7ed7c7(PackageStorageInfo paramPackageStorageInfo, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramPackageStorageInfo.versionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramPackageStorageInfo.packageName);
    zzb.zza$2cfb68bf(paramParcel, 3, paramPackageStorageInfo.label);
    zzb.zza(paramParcel, 4, paramPackageStorageInfo.totalSizeBytes);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbr
 * JD-Core Version:    0.7.0.1
 */