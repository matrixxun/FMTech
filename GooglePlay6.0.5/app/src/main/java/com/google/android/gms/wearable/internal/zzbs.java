package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbs
  implements Parcelable.Creator<PutDataResponse>
{
  static void zza(PutDataResponse paramPutDataResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramPutDataResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramPutDataResponse.statusCode);
    zzb.zza$377a007(paramParcel, 3, paramPutDataResponse.zzcgs, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbs
 * JD-Core Version:    0.7.0.1
 */