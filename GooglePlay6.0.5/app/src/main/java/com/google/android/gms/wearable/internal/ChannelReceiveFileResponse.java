package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ChannelReceiveFileResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<ChannelReceiveFileResponse> CREATOR = new zzr();
  public final int statusCode;
  public final int versionCode;
  
  ChannelReceiveFileResponse(int paramInt1, int paramInt2)
  {
    this.versionCode = paramInt1;
    this.statusCode = paramInt2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzr.zza$20cd803f(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.ChannelReceiveFileResponse
 * JD-Core Version:    0.7.0.1
 */