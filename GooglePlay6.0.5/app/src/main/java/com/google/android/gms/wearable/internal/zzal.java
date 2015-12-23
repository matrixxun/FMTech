package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzal
  implements Parcelable.Creator<GetChannelOutputStreamResponse>
{
  static void zza(GetChannelOutputStreamResponse paramGetChannelOutputStreamResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramGetChannelOutputStreamResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramGetChannelOutputStreamResponse.statusCode);
    zzb.zza$377a007(paramParcel, 3, paramGetChannelOutputStreamResponse.zzcgl, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzal
 * JD-Core Version:    0.7.0.1
 */