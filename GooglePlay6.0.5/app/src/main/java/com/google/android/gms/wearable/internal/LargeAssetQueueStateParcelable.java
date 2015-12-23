package com.google.android.gms.wearable.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.LargeAssetApi.QueueState;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class LargeAssetQueueStateParcelable
  implements SafeParcelable, LargeAssetApi.QueueState
{
  public static final Parcelable.Creator<LargeAssetQueueStateParcelable> CREATOR = new zzbh();
  final int mVersionCode;
  final int zzcgP;
  final String zzcgQ;
  final Map<String, Integer> zzcgR;
  final int zzcgS;
  final int zzcgT;
  
  LargeAssetQueueStateParcelable(int paramInt1, int paramInt2, String paramString, Bundle paramBundle, int paramInt3, int paramInt4)
  {
    this.mVersionCode = paramInt1;
    this.zzcgP = (paramInt2 & 0xF);
    this.zzcgQ = ((String)zzx.zzC(paramString));
    this.zzcgR = zzZ(paramBundle);
    this.zzcgS = paramInt3;
    this.zzcgT = paramInt4;
  }
  
  private static Map<String, Integer> zzZ(Bundle paramBundle)
  {
    Set localSet = paramBundle.keySet();
    ArrayMap localArrayMap = new ArrayMap(localSet.size());
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localArrayMap.put(str, Integer.valueOf(0xF & paramBundle.getInt(str)));
    }
    return localArrayMap;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    LargeAssetQueueStateParcelable localLargeAssetQueueStateParcelable;
    do
    {
      return true;
      if (!(paramObject instanceof LargeAssetQueueStateParcelable)) {
        return false;
      }
      localLargeAssetQueueStateParcelable = (LargeAssetQueueStateParcelable)paramObject;
    } while ((this.mVersionCode == localLargeAssetQueueStateParcelable.mVersionCode) && (this.zzcgP == localLargeAssetQueueStateParcelable.zzcgP) && (this.zzcgS == localLargeAssetQueueStateParcelable.zzcgS) && (this.zzcgT == localLargeAssetQueueStateParcelable.zzcgT) && (this.zzcgQ.equals(localLargeAssetQueueStateParcelable.zzcgQ)) && (this.zzcgR.equals(localLargeAssetQueueStateParcelable.zzcgR)));
    return false;
  }
  
  public final int hashCode()
  {
    return 31 * (31 * (31 * (31 * (31 * this.mVersionCode + this.zzcgP) + this.zzcgQ.hashCode()) + this.zzcgR.hashCode()) + this.zzcgS) + this.zzcgT;
  }
  
  public final String toString()
  {
    return "QueueState{localNodeFlags=" + this.zzcgP + ", localNodeId='" + this.zzcgQ + "', remoteNodeFlags=" + this.zzcgR + ", pausedCount=" + this.zzcgS + ", runningCount=" + this.zzcgT + "}";
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbh.zza$2673ddc(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.LargeAssetQueueStateParcelable
 * JD-Core Version:    0.7.0.1
 */