package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

public class StorageInfoResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<StorageInfoResponse> CREATOR = new zzca();
  public final List<PackageStorageInfo> packageStorageInfo;
  public final int statusCode;
  public final long totalSizeBytes;
  public final int versionCode;
  
  StorageInfoResponse(int paramInt1, int paramInt2, long paramLong, List<PackageStorageInfo> paramList)
  {
    this.versionCode = paramInt1;
    this.statusCode = paramInt2;
    this.totalSizeBytes = paramLong;
    this.packageStorageInfo = paramList;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzca.zza$3afab916(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.StorageInfoResponse
 * JD-Core Version:    0.7.0.1
 */