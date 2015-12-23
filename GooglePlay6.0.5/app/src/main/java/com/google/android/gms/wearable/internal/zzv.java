package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzv
  implements Parcelable.Creator<CloseChannelResponse>
{
  static void zza$71293910(CloseChannelResponse paramCloseChannelResponse, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramCloseChannelResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramCloseChannelResponse.statusCode);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzv
 * JD-Core Version:    0.7.0.1
 */