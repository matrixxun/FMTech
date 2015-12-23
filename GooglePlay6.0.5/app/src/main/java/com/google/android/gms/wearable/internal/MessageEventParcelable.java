package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class MessageEventParcelable
  implements SafeParcelable
{
  public static final Parcelable.Creator<MessageEventParcelable> CREATOR = new zzbn();
  final byte[] mData;
  final String mPath;
  final int mVersionCode;
  final int zzaAY;
  final String zzbfJ;
  
  MessageEventParcelable(int paramInt1, int paramInt2, String paramString1, byte[] paramArrayOfByte, String paramString2)
  {
    this.mVersionCode = paramInt1;
    this.zzaAY = paramInt2;
    this.mPath = paramString1;
    this.mData = paramArrayOfByte;
    this.zzbfJ = paramString2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("MessageEventParcelable[").append(this.zzaAY).append(",").append(this.mPath).append(", size=");
    if (this.mData == null) {}
    for (Object localObject = "null";; localObject = Integer.valueOf(this.mData.length)) {
      return localObject + "]";
    }
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbn.zza$6ae86e9a(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.MessageEventParcelable
 * JD-Core Version:    0.7.0.1
 */