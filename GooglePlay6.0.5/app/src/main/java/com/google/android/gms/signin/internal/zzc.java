package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzc
  implements Parcelable.Creator<CheckServerAuthResult>
{
  static void zza$2809d959(CheckServerAuthResult paramCheckServerAuthResult, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramCheckServerAuthResult.mVersionCode);
    zzb.zza(paramParcel, 2, paramCheckServerAuthResult.zzbMb);
    zzb.zzc$62107c48(paramParcel, 3, paramCheckServerAuthResult.zzbMc);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.signin.internal.zzc
 * JD-Core Version:    0.7.0.1
 */