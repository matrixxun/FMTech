package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzas
  implements Parcelable.Creator<GetDataItemResponse>
{
  static void zza(GetDataItemResponse paramGetDataItemResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramGetDataItemResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramGetDataItemResponse.statusCode);
    zzb.zza$377a007(paramParcel, 3, paramGetDataItemResponse.zzcgs, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzas
 * JD-Core Version:    0.7.0.1
 */