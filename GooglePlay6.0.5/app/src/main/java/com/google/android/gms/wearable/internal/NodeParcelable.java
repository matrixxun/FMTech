package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class NodeParcelable
  implements SafeParcelable
{
  public static final Parcelable.Creator<NodeParcelable> CREATOR = new zzbp();
  final int mVersionCode;
  final String zzUe;
  final int zzcho;
  final boolean zzchp;
  final String zzyx;
  
  NodeParcelable(int paramInt1, String paramString1, String paramString2, int paramInt2, boolean paramBoolean)
  {
    this.mVersionCode = paramInt1;
    this.zzyx = paramString1;
    this.zzUe = paramString2;
    this.zzcho = paramInt2;
    this.zzchp = paramBoolean;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof NodeParcelable)) {
      return false;
    }
    return ((NodeParcelable)paramObject).zzyx.equals(this.zzyx);
  }
  
  public int hashCode()
  {
    return this.zzyx.hashCode();
  }
  
  public String toString()
  {
    return "Node{" + this.zzUe + ", id=" + this.zzyx + ", hops=" + this.zzcho + ", isNearby=" + this.zzchp + "}";
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbp.zza$70e0d217(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.NodeParcelable
 * JD-Core Version:    0.7.0.1
 */