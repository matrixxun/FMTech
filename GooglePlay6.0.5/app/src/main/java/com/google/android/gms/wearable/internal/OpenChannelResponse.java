package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class OpenChannelResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<OpenChannelResponse> CREATOR = new zzbq();
  public final int statusCode;
  public final int versionCode;
  public final ChannelImpl zzcfC;
  
  OpenChannelResponse(int paramInt1, int paramInt2, ChannelImpl paramChannelImpl)
  {
    this.versionCode = paramInt1;
    this.statusCode = paramInt2;
    this.zzcfC = paramChannelImpl;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbq.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.OpenChannelResponse
 * JD-Core Version:    0.7.0.1
 */