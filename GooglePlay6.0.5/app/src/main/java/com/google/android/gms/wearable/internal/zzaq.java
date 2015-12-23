package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzaq
  implements Parcelable.Creator<GetConfigsResponse>
{
  static void zza(GetConfigsResponse paramGetConfigsResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramGetConfigsResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramGetConfigsResponse.statusCode);
    zzb.zza$2d7953c6(paramParcel, 3, paramGetConfigsResponse.zzcgq, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzaq
 * JD-Core Version:    0.7.0.1
 */