package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzv
  implements Parcelable.Creator<RadioProperties.ChannelRange>
{
  static void zza$40752d93(RadioProperties.ChannelRange paramChannelRange, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramChannelRange.min);
    zzb.zzc(paramParcel, 1000, paramChannelRange.mVersionCode);
    zzb.zzc(paramParcel, 2, paramChannelRange.max);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzv
 * JD-Core Version:    0.7.0.1
 */