package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;
import java.util.Set;

public class CapabilityInfoParcelable
  implements SafeParcelable
{
  public static final Parcelable.Creator<CapabilityInfoParcelable> CREATOR = new zzk();
  final String mName;
  final int mVersionCode;
  private Set<Object> zzcfs;
  final List<NodeParcelable> zzcfv;
  private final Object zzqp = new Object();
  
  CapabilityInfoParcelable(int paramInt, String paramString, List<NodeParcelable> paramList)
  {
    this.mVersionCode = paramInt;
    this.mName = paramString;
    this.zzcfv = paramList;
    this.zzcfs = null;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    CapabilityInfoParcelable localCapabilityInfoParcelable;
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      localCapabilityInfoParcelable = (CapabilityInfoParcelable)paramObject;
      if (this.mVersionCode != localCapabilityInfoParcelable.mVersionCode) {
        return false;
      }
      if (this.mName != null)
      {
        if (this.mName.equals(localCapabilityInfoParcelable.mName)) {}
      }
      else {
        while (localCapabilityInfoParcelable.mName != null) {
          return false;
        }
      }
      if (this.zzcfv == null) {
        break;
      }
    } while (this.zzcfv.equals(localCapabilityInfoParcelable.zzcfv));
    for (;;)
    {
      return false;
      if (localCapabilityInfoParcelable.zzcfv == null) {
        break;
      }
    }
  }
  
  public int hashCode()
  {
    int i = 31 * this.mVersionCode;
    if (this.mName != null) {}
    for (int j = this.mName.hashCode();; j = 0)
    {
      int k = 31 * (j + i);
      List localList = this.zzcfv;
      int m = 0;
      if (localList != null) {
        m = this.zzcfv.hashCode();
      }
      return k + m;
    }
  }
  
  public String toString()
  {
    return "CapabilityInfo{" + this.mName + ", " + this.zzcfv + "}";
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzk.zza$c097acd(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.CapabilityInfoParcelable
 * JD-Core Version:    0.7.0.1
 */