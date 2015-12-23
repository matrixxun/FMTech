package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzai
  implements Parcelable.Creator<GetAllCapabilitiesResponse>
{
  static void zza$ef4d026(GetAllCapabilitiesResponse paramGetAllCapabilitiesResponse, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramGetAllCapabilitiesResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramGetAllCapabilitiesResponse.statusCode);
    zzb.zzc$62107c48(paramParcel, 3, paramGetAllCapabilitiesResponse.zzcgj);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzai
 * JD-Core Version:    0.7.0.1
 */