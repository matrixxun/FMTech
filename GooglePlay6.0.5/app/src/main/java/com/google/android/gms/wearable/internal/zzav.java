package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzav
  implements Parcelable.Creator<GetLocalNodeResponse>
{
  static void zza(GetLocalNodeResponse paramGetLocalNodeResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramGetLocalNodeResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramGetLocalNodeResponse.statusCode);
    zzb.zza$377a007(paramParcel, 3, paramGetLocalNodeResponse.zzcgv, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzav
 * JD-Core Version:    0.7.0.1
 */