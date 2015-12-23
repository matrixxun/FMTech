package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzag
  implements Parcelable.Creator<DeleteDataItemsResponse>
{
  static void zza$6d27e0d4(DeleteDataItemsResponse paramDeleteDataItemsResponse, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramDeleteDataItemsResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramDeleteDataItemsResponse.statusCode);
    zzb.zzc(paramParcel, 3, paramDeleteDataItemsResponse.zzcgh);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzag
 * JD-Core Version:    0.7.0.1
 */