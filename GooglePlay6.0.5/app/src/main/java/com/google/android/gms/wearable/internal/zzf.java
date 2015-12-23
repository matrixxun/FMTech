package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzf
  implements Parcelable.Creator<AmsEntityUpdateParcelable>
{
  static void zza$60aac80a(AmsEntityUpdateParcelable paramAmsEntityUpdateParcelable, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramAmsEntityUpdateParcelable.mVersionCode);
    zzb.zza(paramParcel, 2, paramAmsEntityUpdateParcelable.zzcfc);
    zzb.zza(paramParcel, 3, paramAmsEntityUpdateParcelable.zzcfd);
    zzb.zza$2cfb68bf(paramParcel, 4, paramAmsEntityUpdateParcelable.mValue);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzf
 * JD-Core Version:    0.7.0.1
 */