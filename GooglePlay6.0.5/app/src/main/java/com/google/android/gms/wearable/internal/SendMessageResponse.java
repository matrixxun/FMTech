package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class SendMessageResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<SendMessageResponse> CREATOR = new zzbx();
  public final int statusCode;
  public final int versionCode;
  public final int zzbcZ;
  
  SendMessageResponse(int paramInt1, int paramInt2, int paramInt3)
  {
    this.versionCode = paramInt1;
    this.statusCode = paramInt2;
    this.zzbcZ = paramInt3;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbx.zza$3305acc0(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.SendMessageResponse
 * JD-Core Version:    0.7.0.1
 */