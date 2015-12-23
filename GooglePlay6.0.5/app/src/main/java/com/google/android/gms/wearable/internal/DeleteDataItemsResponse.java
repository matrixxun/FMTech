package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class DeleteDataItemsResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<DeleteDataItemsResponse> CREATOR = new zzag();
  public final int statusCode;
  public final int versionCode;
  public final int zzcgh;
  
  DeleteDataItemsResponse(int paramInt1, int paramInt2, int paramInt3)
  {
    this.versionCode = paramInt1;
    this.statusCode = paramInt2;
    this.zzcgh = paramInt3;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzag.zza$6d27e0d4(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.DeleteDataItemsResponse
 * JD-Core Version:    0.7.0.1
 */