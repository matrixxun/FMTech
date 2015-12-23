package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzam
  implements Parcelable.Creator<GetCloudSyncOptInOutDoneResponse>
{
  static void zza$28711177(GetCloudSyncOptInOutDoneResponse paramGetCloudSyncOptInOutDoneResponse, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramGetCloudSyncOptInOutDoneResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramGetCloudSyncOptInOutDoneResponse.statusCode);
    zzb.zza(paramParcel, 3, paramGetCloudSyncOptInOutDoneResponse.zzcgm);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzam
 * JD-Core Version:    0.7.0.1
 */