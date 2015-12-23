package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class AmsEntityUpdateParcelable
  implements SafeParcelable
{
  public static final Parcelable.Creator<AmsEntityUpdateParcelable> CREATOR = new zzf();
  final String mValue;
  final int mVersionCode;
  byte zzcfc;
  final byte zzcfd;
  
  AmsEntityUpdateParcelable(int paramInt, byte paramByte1, byte paramByte2, String paramString)
  {
    this.zzcfc = paramByte1;
    this.mVersionCode = paramInt;
    this.zzcfd = paramByte2;
    this.mValue = paramString;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    AmsEntityUpdateParcelable localAmsEntityUpdateParcelable;
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      localAmsEntityUpdateParcelable = (AmsEntityUpdateParcelable)paramObject;
      if (this.zzcfc != localAmsEntityUpdateParcelable.zzcfc) {
        return false;
      }
      if (this.mVersionCode != localAmsEntityUpdateParcelable.mVersionCode) {
        return false;
      }
      if (this.zzcfd != localAmsEntityUpdateParcelable.zzcfd) {
        return false;
      }
    } while (this.mValue.equals(localAmsEntityUpdateParcelable.mValue));
    return false;
  }
  
  public int hashCode()
  {
    return 31 * (31 * (31 * this.mVersionCode + this.zzcfc) + this.zzcfd) + this.mValue.hashCode();
  }
  
  public String toString()
  {
    return "AmsEntityUpdateParcelable{mVersionCode=" + this.mVersionCode + ", mEntityId=" + this.zzcfc + ", mAttributeId=" + this.zzcfd + ", mValue='" + this.mValue + '\'' + '}';
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzf.zza$60aac80a(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.AmsEntityUpdateParcelable
 * JD-Core Version:    0.7.0.1
 */