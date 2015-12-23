package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzr
  implements Parcelable.Creator<ChannelReceiveFileResponse>
{
  static void zza$20cd803f(ChannelReceiveFileResponse paramChannelReceiveFileResponse, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramChannelReceiveFileResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramChannelReceiveFileResponse.statusCode);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzr
 * JD-Core Version:    0.7.0.1
 */