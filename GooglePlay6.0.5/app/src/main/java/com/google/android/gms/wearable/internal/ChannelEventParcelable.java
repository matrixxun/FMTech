package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ChannelEventParcelable
  implements SafeParcelable
{
  public static final Parcelable.Creator<ChannelEventParcelable> CREATOR = new zzn();
  final int mVersionCode;
  final int type;
  final int zzcfA;
  final int zzcfB;
  final ChannelImpl zzcfC;
  
  ChannelEventParcelable(int paramInt1, ChannelImpl paramChannelImpl, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mVersionCode = paramInt1;
    this.zzcfC = paramChannelImpl;
    this.type = paramInt2;
    this.zzcfA = paramInt3;
    this.zzcfB = paramInt4;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder("ChannelEventParcelable[versionCode=").append(this.mVersionCode).append(", channel=").append(this.zzcfC).append(", type=");
    int i = this.type;
    String str1;
    StringBuilder localStringBuilder2;
    String str2;
    switch (i)
    {
    default: 
      str1 = Integer.toString(i);
      localStringBuilder2 = localStringBuilder1.append(str1).append(", closeReason=");
      int j = this.zzcfA;
      switch (j)
      {
      default: 
        str2 = Integer.toString(j);
      }
      break;
    }
    for (;;)
    {
      return str2 + ", appErrorCode=" + this.zzcfB + "]";
      str1 = "CHANNEL_OPENED";
      break;
      str1 = "CHANNEL_CLOSED";
      break;
      str1 = "OUTPUT_CLOSED";
      break;
      str1 = "INPUT_CLOSED";
      break;
      str2 = "CLOSE_REASON_DISCONNECTED";
      continue;
      str2 = "CLOSE_REASON_REMOTE_CLOSE";
      continue;
      str2 = "CLOSE_REASON_LOCAL_CLOSE";
      continue;
      str2 = "CLOSE_REASON_NORMAL";
    }
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzn.zza(this, paramParcel, paramInt);
  }
  
  public final void zza$34052072()
  {
    switch (this.type)
    {
    default: 
      Log.w("ChannelEventParcelable", "Unknown type: " + this.type);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.ChannelEventParcelable
 * JD-Core Version:    0.7.0.1
 */