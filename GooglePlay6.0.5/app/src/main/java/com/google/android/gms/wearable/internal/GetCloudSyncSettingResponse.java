package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class GetCloudSyncSettingResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<GetCloudSyncSettingResponse> CREATOR = new zzao();
  public final boolean enabled;
  public final int statusCode;
  public final int versionCode;
  
  GetCloudSyncSettingResponse(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.versionCode = paramInt1;
    this.statusCode = paramInt2;
    this.enabled = paramBoolean;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzao.zza$1a36d5f7(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.GetCloudSyncSettingResponse
 * JD-Core Version:    0.7.0.1
 */