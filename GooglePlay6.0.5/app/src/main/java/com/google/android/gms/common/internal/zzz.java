package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzz
  implements Parcelable.Creator<ResolveAccountResponse>
{
  static void zza(ResolveAccountResponse paramResolveAccountResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramResolveAccountResponse.mVersionCode);
    zzb.zza$cdac282(paramParcel, 2, paramResolveAccountResponse.zzasY);
    zzb.zza$377a007(paramParcel, 3, paramResolveAccountResponse.zzauE, paramInt);
    zzb.zza(paramParcel, 4, paramResolveAccountResponse.zzaps);
    zzb.zza(paramParcel, 5, paramResolveAccountResponse.zzauF);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzz
 * JD-Core Version:    0.7.0.1
 */