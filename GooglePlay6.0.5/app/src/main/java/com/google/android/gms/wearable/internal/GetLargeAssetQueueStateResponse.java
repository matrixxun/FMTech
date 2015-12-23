package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public final class GetLargeAssetQueueStateResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<GetLargeAssetQueueStateResponse> CREATOR = new zzau();
  final int mVersionCode;
  public final Status status;
  public final LargeAssetQueueStateParcelable zzcgu;
  
  GetLargeAssetQueueStateResponse(int paramInt, Status paramStatus, LargeAssetQueueStateParcelable paramLargeAssetQueueStateParcelable)
  {
    this.mVersionCode = paramInt;
    this.status = paramStatus;
    if (paramStatus.isSuccess()) {
      zzx.zzC(paramLargeAssetQueueStateParcelable);
    }
    this.zzcgu = paramLargeAssetQueueStateParcelable;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzau.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.GetLargeAssetQueueStateResponse
 * JD-Core Version:    0.7.0.1
 */