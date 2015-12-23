package com.google.android.gms.herrevad;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzb
  implements Parcelable.Creator<PredictedNetworkQuality>
{
  static void zza$5858cc2d(PredictedNetworkQuality paramPredictedNetworkQuality, Parcel paramParcel)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, 20293);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramPredictedNetworkQuality.mVersionCode);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 2, paramPredictedNetworkQuality.networkType);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 3, paramPredictedNetworkQuality.predictedLatencyMicros);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 4, paramPredictedNetworkQuality.predictedDownThroughputBps);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 5, paramPredictedNetworkQuality.predictedUpThroughputBps);
    com.google.android.gms.common.internal.safeparcel.zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.herrevad.zzb
 * JD-Core Version:    0.7.0.1
 */