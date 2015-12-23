package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.Channel;

public class ChannelImpl
  implements SafeParcelable, Channel
{
  public static final Parcelable.Creator<ChannelImpl> CREATOR = new zzo();
  final String mPath;
  final int mVersionCode;
  final String zzVB;
  final String zzcev;
  
  ChannelImpl(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    this.mVersionCode = paramInt;
    this.zzVB = ((String)zzx.zzC(paramString1));
    this.zzcev = ((String)zzx.zzC(paramString2));
    this.mPath = ((String)zzx.zzC(paramString3));
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    ChannelImpl localChannelImpl;
    do
    {
      return true;
      if (!(paramObject instanceof ChannelImpl)) {
        return false;
      }
      localChannelImpl = (ChannelImpl)paramObject;
    } while ((this.zzVB.equals(localChannelImpl.zzVB)) && (zzw.equal(localChannelImpl.zzcev, this.zzcev)) && (zzw.equal(localChannelImpl.mPath, this.mPath)) && (localChannelImpl.mVersionCode == this.mVersionCode));
    return false;
  }
  
  public int hashCode()
  {
    return this.zzVB.hashCode();
  }
  
  public String toString()
  {
    return "ChannelImpl{versionCode=" + this.mVersionCode + ", token='" + this.zzVB + '\'' + ", nodeId='" + this.zzcev + '\'' + ", path='" + this.mPath + '\'' + "}";
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzo.zza$2d2555a3(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.ChannelImpl
 * JD-Core Version:    0.7.0.1
 */