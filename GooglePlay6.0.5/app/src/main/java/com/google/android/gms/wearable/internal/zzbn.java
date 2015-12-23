package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbn
  implements Parcelable.Creator<MessageEventParcelable>
{
  static void zza$6ae86e9a(MessageEventParcelable paramMessageEventParcelable, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramMessageEventParcelable.mVersionCode);
    zzb.zzc(paramParcel, 2, paramMessageEventParcelable.zzaAY);
    zzb.zza$2cfb68bf(paramParcel, 3, paramMessageEventParcelable.mPath);
    zzb.zza$52910762(paramParcel, 4, paramMessageEventParcelable.mData);
    zzb.zza$2cfb68bf(paramParcel, 5, paramMessageEventParcelable.zzbfJ);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbn
 * JD-Core Version:    0.7.0.1
 */