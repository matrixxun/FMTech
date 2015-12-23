package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzo
  implements Parcelable.Creator<ChannelImpl>
{
  static void zza$2d2555a3(ChannelImpl paramChannelImpl, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramChannelImpl.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramChannelImpl.zzVB);
    zzb.zza$2cfb68bf(paramParcel, 3, paramChannelImpl.zzcev);
    zzb.zza$2cfb68bf(paramParcel, 4, paramChannelImpl.mPath);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzo
 * JD-Core Version:    0.7.0.1
 */