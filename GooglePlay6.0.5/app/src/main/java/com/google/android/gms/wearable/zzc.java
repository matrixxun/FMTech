package com.google.android.gms.wearable;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzc
  implements Parcelable.Creator<PutDataRequest>
{
  static void zza(PutDataRequest paramPutDataRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramPutDataRequest.mVersionCode);
    zzb.zza$377a007(paramParcel, 2, paramPutDataRequest.mUri, paramInt);
    zzb.zza$f7bef55(paramParcel, 4, paramPutDataRequest.zzceF);
    zzb.zza$52910762(paramParcel, 5, paramPutDataRequest.mData);
    zzb.zza(paramParcel, 6, paramPutDataRequest.zzceG);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.zzc
 * JD-Core Version:    0.7.0.1
 */