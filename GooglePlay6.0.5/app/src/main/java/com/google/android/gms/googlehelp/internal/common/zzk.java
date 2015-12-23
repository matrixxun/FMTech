package com.google.android.gms.googlehelp.internal.common;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzk
  implements Parcelable.Creator<TogglingData>
{
  static void zza$637bf5fe(TogglingData paramTogglingData, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramTogglingData.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramTogglingData.zzbaC);
    zzb.zza$2cfb68bf(paramParcel, 3, paramTogglingData.zzbbu);
    zzb.zza$2cfb68bf(paramParcel, 4, paramTogglingData.zzbbv);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.internal.common.zzk
 * JD-Core Version:    0.7.0.1
 */