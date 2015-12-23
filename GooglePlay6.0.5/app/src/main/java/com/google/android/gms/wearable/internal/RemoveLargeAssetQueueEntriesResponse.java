package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public final class RemoveLargeAssetQueueEntriesResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<RemoveLargeAssetQueueEntriesResponse> CREATOR = new zzbu();
  public final Status status;
  public final int versionCode;
  public final int zzcgh;
  
  RemoveLargeAssetQueueEntriesResponse(int paramInt1, Status paramStatus, int paramInt2)
  {
    this.versionCode = paramInt1;
    this.status = ((Status)zzx.zzb(paramStatus, "status"));
    this.zzcgh = paramInt2;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbu.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.RemoveLargeAssetQueueEntriesResponse
 * JD-Core Version:    0.7.0.1
 */