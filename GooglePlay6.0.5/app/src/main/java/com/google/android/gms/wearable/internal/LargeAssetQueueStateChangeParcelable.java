package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.LargeAssetApi.QueueStateChange;

public final class LargeAssetQueueStateChangeParcelable
  implements SafeParcelable, LargeAssetApi.QueueStateChange
{
  public static final Parcelable.Creator<LargeAssetQueueStateChangeParcelable> CREATOR = new zzbg();
  final int mVersionCode;
  public final zzbt zzcgM;
  final DataHolder zzcgN;
  final LargeAssetQueueStateParcelable zzcgO;
  
  LargeAssetQueueStateChangeParcelable(int paramInt, DataHolder paramDataHolder, LargeAssetQueueStateParcelable paramLargeAssetQueueStateParcelable)
  {
    this.mVersionCode = paramInt;
    this.zzcgN = ((DataHolder)zzx.zzC(paramDataHolder));
    this.zzcgM = new zzbt(paramDataHolder);
    this.zzcgO = ((LargeAssetQueueStateParcelable)zzx.zzC(paramLargeAssetQueueStateParcelable));
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void release()
  {
    this.zzcgM.release();
  }
  
  public final String toString()
  {
    return "LargeAssetQueueStateChangeParcelable{queueEntryBuffer=" + this.zzcgM + ", queueState=" + this.zzcgO + "}";
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbg.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.LargeAssetQueueStateChangeParcelable
 * JD-Core Version:    0.7.0.1
 */