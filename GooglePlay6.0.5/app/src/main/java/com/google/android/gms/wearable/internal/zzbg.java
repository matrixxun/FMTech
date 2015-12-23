package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbg
  implements Parcelable.Creator<LargeAssetQueueStateChangeParcelable>
{
  static void zza(LargeAssetQueueStateChangeParcelable paramLargeAssetQueueStateChangeParcelable, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramLargeAssetQueueStateChangeParcelable.mVersionCode);
    zzb.zza$377a007(paramParcel, 4, paramLargeAssetQueueStateChangeParcelable.zzcgN, paramInt);
    zzb.zza$377a007(paramParcel, 5, paramLargeAssetQueueStateChangeParcelable.zzcgO, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbg
 * JD-Core Version:    0.7.0.1
 */