package com.google.android.gms.wearable;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import java.util.Arrays;

public class ConnectionConfiguration
  implements SafeParcelable
{
  public static final Parcelable.Creator<ConnectionConfiguration> CREATOR = new zzb();
  final String mName;
  final int mVersionCode;
  boolean zzQY;
  final int zzTv;
  final int zzaBG;
  final String zzaLE;
  final boolean zzces;
  String zzcet;
  boolean zzceu;
  String zzcev;
  
  ConnectionConfiguration(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, String paramString3, boolean paramBoolean3, String paramString4)
  {
    this.mVersionCode = paramInt1;
    this.mName = paramString1;
    this.zzaLE = paramString2;
    this.zzTv = paramInt2;
    this.zzaBG = paramInt3;
    this.zzces = paramBoolean1;
    this.zzQY = paramBoolean2;
    this.zzcet = paramString3;
    this.zzceu = paramBoolean3;
    this.zzcev = paramString4;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ConnectionConfiguration)) {}
    ConnectionConfiguration localConnectionConfiguration;
    do
    {
      return false;
      localConnectionConfiguration = (ConnectionConfiguration)paramObject;
    } while ((!zzw.equal(Integer.valueOf(this.mVersionCode), Integer.valueOf(localConnectionConfiguration.mVersionCode))) || (!zzw.equal(this.mName, localConnectionConfiguration.mName)) || (!zzw.equal(this.zzaLE, localConnectionConfiguration.zzaLE)) || (!zzw.equal(Integer.valueOf(this.zzTv), Integer.valueOf(localConnectionConfiguration.zzTv))) || (!zzw.equal(Integer.valueOf(this.zzaBG), Integer.valueOf(localConnectionConfiguration.zzaBG))) || (!zzw.equal(Boolean.valueOf(this.zzces), Boolean.valueOf(localConnectionConfiguration.zzces))) || (!zzw.equal(Boolean.valueOf(this.zzceu), Boolean.valueOf(localConnectionConfiguration.zzceu))));
    return true;
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[7];
    arrayOfObject[0] = Integer.valueOf(this.mVersionCode);
    arrayOfObject[1] = this.mName;
    arrayOfObject[2] = this.zzaLE;
    arrayOfObject[3] = Integer.valueOf(this.zzTv);
    arrayOfObject[4] = Integer.valueOf(this.zzaBG);
    arrayOfObject[5] = Boolean.valueOf(this.zzces);
    arrayOfObject[6] = Boolean.valueOf(this.zzceu);
    return Arrays.hashCode(arrayOfObject);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("ConnectionConfiguration[ ");
    localStringBuilder.append("mName=" + this.mName);
    localStringBuilder.append(", mAddress=" + this.zzaLE);
    localStringBuilder.append(", mType=" + this.zzTv);
    localStringBuilder.append(", mRole=" + this.zzaBG);
    localStringBuilder.append(", mEnabled=" + this.zzces);
    localStringBuilder.append(", mIsConnected=" + this.zzQY);
    localStringBuilder.append(", mPeerNodeId=" + this.zzcet);
    localStringBuilder.append(", mBtlePriority=" + this.zzceu);
    localStringBuilder.append(", mNodeId=" + this.zzcev);
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza$3e41336a(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.ConnectionConfiguration
 * JD-Core Version:    0.7.0.1
 */