package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public final class LargeAssetQueueEntryParcelable
  implements SafeParcelable
{
  public static final Parcelable.Creator<LargeAssetQueueEntryParcelable> CREATOR = new zzbf();
  final String mPath;
  final int mState;
  final int mVersionCode;
  final boolean zzceA;
  final String zzcev;
  final Uri zzcex;
  final boolean zzcey;
  final boolean zzcez;
  final long zzcgK;
  final int zzcgL;
  
  LargeAssetQueueEntryParcelable(int paramInt1, long paramLong, int paramInt2, String paramString1, String paramString2, Uri paramUri, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.mVersionCode = paramInt1;
    this.zzcgK = paramLong;
    this.mState = paramInt2;
    this.mPath = ((String)zzx.zzb(paramString1, "path"));
    this.zzcev = ((String)zzx.zzb(paramString2, "nodeId"));
    this.zzcex = ((Uri)zzx.zzb(paramUri, "destinationUri"));
    this.zzcgL = paramInt3;
    this.zzcey = paramBoolean1;
    this.zzcez = paramBoolean2;
    this.zzceA = paramBoolean3;
  }
  
  public LargeAssetQueueEntryParcelable(long paramLong, int paramInt1, String paramString1, String paramString2, Uri paramUri, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this(1, paramLong, paramInt1, paramString1, paramString2, paramUri, paramInt2, paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    LargeAssetQueueEntryParcelable localLargeAssetQueueEntryParcelable;
    do
    {
      return true;
      if (!(paramObject instanceof LargeAssetQueueEntryParcelable)) {
        return false;
      }
      localLargeAssetQueueEntryParcelable = (LargeAssetQueueEntryParcelable)paramObject;
    } while ((this.mVersionCode == localLargeAssetQueueEntryParcelable.mVersionCode) && (this.zzcgK == localLargeAssetQueueEntryParcelable.zzcgK) && (this.mState == localLargeAssetQueueEntryParcelable.mState) && (this.mPath.equals(localLargeAssetQueueEntryParcelable.mPath)) && (this.zzcev.equals(localLargeAssetQueueEntryParcelable.zzcev)) && (this.zzcex.equals(localLargeAssetQueueEntryParcelable.zzcex)) && (this.zzcey == localLargeAssetQueueEntryParcelable.zzcey) && (this.zzcez == localLargeAssetQueueEntryParcelable.zzcez) && (this.zzceA == localLargeAssetQueueEntryParcelable.zzceA) && (this.zzcgL == localLargeAssetQueueEntryParcelable.zzcgL));
    return false;
  }
  
  public final int hashCode()
  {
    int i = 1;
    int j = 31 * (31 * (31 * (31 * (31 * (31 * this.mVersionCode + (int)(this.zzcgK ^ this.zzcgK >>> 32)) + this.mState) + this.mPath.hashCode()) + this.zzcev.hashCode()) + this.zzcex.hashCode());
    int k;
    int n;
    label95:
    int i1;
    if (this.zzcey)
    {
      k = i;
      int m = 31 * (k + j);
      if (!this.zzcez) {
        break label130;
      }
      n = i;
      i1 = 31 * (n + m);
      if (!this.zzceA) {
        break label136;
      }
    }
    for (;;)
    {
      return 31 * (i1 + i) + this.zzcgL;
      k = 0;
      break;
      label130:
      n = 0;
      break label95;
      label136:
      i = 0;
    }
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder("QueueEntry{versionCode=").append(this.mVersionCode).append(", transferId=").append(this.zzcgK).append(", state=").append(this.mState).append(", path='").append(this.mPath).append("', nodeId='").append(this.zzcev).append("', destinationUri='").append(this.zzcex).append("'");
    String str1;
    String str2;
    label109:
    StringBuilder localStringBuilder3;
    if (this.zzcey)
    {
      str1 = ", append=true";
      StringBuilder localStringBuilder2 = localStringBuilder1.append(str1);
      if (!this.zzcez) {
        break label162;
      }
      str2 = ", allowedOverMetered=true";
      localStringBuilder3 = localStringBuilder2.append(str2);
      if (!this.zzceA) {
        break label169;
      }
    }
    label162:
    label169:
    for (String str3 = ", allowedWithLowBattery=true";; str3 = "")
    {
      return str3 + ", refuseErrorCode=" + this.zzcgL + "}";
      str1 = "";
      break;
      str2 = "";
      break label109;
    }
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbf.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.LargeAssetQueueEntryParcelable
 * JD-Core Version:    0.7.0.1
 */