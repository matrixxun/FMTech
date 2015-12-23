package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzao
  implements Parcelable.Creator<GetCloudSyncSettingResponse>
{
  static void zza$1a36d5f7(GetCloudSyncSettingResponse paramGetCloudSyncSettingResponse, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramGetCloudSyncSettingResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramGetCloudSyncSettingResponse.statusCode);
    zzb.zza(paramParcel, 3, paramGetCloudSyncSettingResponse.enabled);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzao
 * JD-Core Version:    0.7.0.1
 */