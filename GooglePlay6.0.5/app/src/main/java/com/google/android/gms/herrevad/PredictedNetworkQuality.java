package com.google.android.gms.herrevad;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class PredictedNetworkQuality
  implements SafeParcelable
{
  public static final Parcelable.Creator<PredictedNetworkQuality> CREATOR = new zzb();
  final int mVersionCode;
  public int networkType;
  public long predictedDownThroughputBps;
  public int predictedLatencyMicros;
  public long predictedUpThroughputBps;
  
  public PredictedNetworkQuality()
  {
    this.mVersionCode = 1;
    this.predictedLatencyMicros = -1;
    this.predictedDownThroughputBps = -1L;
    this.predictedUpThroughputBps = -1L;
  }
  
  PredictedNetworkQuality(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2)
  {
    this.mVersionCode = paramInt1;
    this.networkType = paramInt2;
    this.predictedLatencyMicros = paramInt3;
    this.predictedDownThroughputBps = paramLong1;
    this.predictedUpThroughputBps = paramLong2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getName()).append("[\n");
    localStringBuilder.append("networkType: ").append(this.networkType).append("\n");
    localStringBuilder.append("predictedLatencyMicros: ").append(this.predictedLatencyMicros).append("\n");
    localStringBuilder.append("predictedDownThroughputBps: ").append(this.predictedDownThroughputBps).append("\n");
    localStringBuilder.append("predictedUpThroughputBps: ").append(this.predictedUpThroughputBps).append("\n");
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza$5858cc2d(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.herrevad.PredictedNetworkQuality
 * JD-Core Version:    0.7.0.1
 */