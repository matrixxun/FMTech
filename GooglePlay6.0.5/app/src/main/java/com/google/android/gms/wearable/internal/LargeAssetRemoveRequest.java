package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Arrays;

public final class LargeAssetRemoveRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<LargeAssetRemoveRequest> CREATOR = new zzbi();
  private static final LargeAssetRemoveRequest zzcgU = new LargeAssetRemoveRequest(1, null);
  final int mVersionCode;
  public final long[] zzcgV;
  
  LargeAssetRemoveRequest(int paramInt, long[] paramArrayOfLong)
  {
    this.mVersionCode = paramInt;
    this.zzcgV = paramArrayOfLong;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final String toString()
  {
    return "LargeAssetRemoveRequest{transferIdsToRemove=" + Arrays.toString(this.zzcgV) + "}";
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbi.zza$61d0d736(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.LargeAssetRemoveRequest
 * JD-Core Version:    0.7.0.1
 */