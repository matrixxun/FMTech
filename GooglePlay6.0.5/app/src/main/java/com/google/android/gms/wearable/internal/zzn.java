package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzn
  implements Parcelable.Creator<ChannelEventParcelable>
{
  static void zza(ChannelEventParcelable paramChannelEventParcelable, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramChannelEventParcelable.mVersionCode);
    zzb.zza$377a007(paramParcel, 2, paramChannelEventParcelable.zzcfC, paramInt);
    zzb.zzc(paramParcel, 3, paramChannelEventParcelable.type);
    zzb.zzc(paramParcel, 4, paramChannelEventParcelable.zzcfA);
    zzb.zzc(paramParcel, 5, paramChannelEventParcelable.zzcfB);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzn
 * JD-Core Version:    0.7.0.1
 */