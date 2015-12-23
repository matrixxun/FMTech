package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbw
  implements Parcelable.Creator<RemoveLocalCapabilityResponse>
{
  static void zza$4c197820(RemoveLocalCapabilityResponse paramRemoveLocalCapabilityResponse, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramRemoveLocalCapabilityResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramRemoveLocalCapabilityResponse.statusCode);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbw
 * JD-Core Version:    0.7.0.1
 */