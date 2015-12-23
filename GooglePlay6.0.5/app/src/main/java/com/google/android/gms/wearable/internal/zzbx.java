package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbx
  implements Parcelable.Creator<SendMessageResponse>
{
  static void zza$3305acc0(SendMessageResponse paramSendMessageResponse, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramSendMessageResponse.versionCode);
    zzb.zzc(paramParcel, 2, paramSendMessageResponse.statusCode);
    zzb.zzc(paramParcel, 3, paramSendMessageResponse.zzbcZ);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbx
 * JD-Core Version:    0.7.0.1
 */