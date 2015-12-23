package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public final class LargeAssetEnqueueRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<LargeAssetEnqueueRequest> CREATOR = new zzbd();
  final int mVersionCode;
  public final String path;
  public final Uri zzbru;
  public final String zzcgE;
  public final String zzcgF;
  public final boolean zzcgG;
  public final boolean zzcgH;
  public final boolean zzcgI;
  
  LargeAssetEnqueueRequest(int paramInt, String paramString1, String paramString2, Uri paramUri, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.mVersionCode = paramInt;
    this.zzcgE = ((String)zzx.zzC(paramString1));
    this.path = ((String)zzx.zzC(paramString2));
    this.zzbru = ((Uri)zzx.zzC(paramUri));
    this.zzcgF = ((String)zzx.zzC(paramString3));
    this.zzcgG = paramBoolean1;
    this.zzcgH = paramBoolean2;
    this.zzcgI = paramBoolean3;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    LargeAssetEnqueueRequest localLargeAssetEnqueueRequest;
    do
    {
      return true;
      if (!(paramObject instanceof LargeAssetEnqueueRequest)) {
        return false;
      }
      localLargeAssetEnqueueRequest = (LargeAssetEnqueueRequest)paramObject;
    } while ((this.mVersionCode == localLargeAssetEnqueueRequest.mVersionCode) && (this.zzcgE.equals(localLargeAssetEnqueueRequest.zzcgE)) && (this.path.equals(localLargeAssetEnqueueRequest.path)) && (this.zzbru.equals(localLargeAssetEnqueueRequest.zzbru)) && (this.zzcgF.equals(localLargeAssetEnqueueRequest.zzcgF)) && (this.zzcgG == localLargeAssetEnqueueRequest.zzcgG) && (this.zzcgH == localLargeAssetEnqueueRequest.zzcgH) && (this.zzcgI == localLargeAssetEnqueueRequest.zzcgI));
    return false;
  }
  
  public final int hashCode()
  {
    int i = 1;
    int j = 31 * (31 * (31 * (31 * (31 * this.mVersionCode + this.zzcgE.hashCode()) + this.path.hashCode()) + this.zzbru.hashCode()) + this.zzcgF.hashCode());
    int k;
    int n;
    label81:
    int i1;
    if (this.zzcgG)
    {
      k = i;
      int m = 31 * (k + j);
      if (!this.zzcgH) {
        break label108;
      }
      n = i;
      i1 = 31 * (n + m);
      if (!this.zzcgI) {
        break label114;
      }
    }
    for (;;)
    {
      return i1 + i;
      k = 0;
      break;
      label108:
      n = 0;
      break label81;
      label114:
      i = 0;
    }
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder("LargeAssetEnqueueRequest{, nodeId='").append(this.zzcgE).append("', path='").append(this.path).append("', destinationUri='").append(this.zzbru).append("', destinationCanonicalPath='").append(this.zzcgF).append("', append=").append(this.zzcgG);
    String str1;
    StringBuilder localStringBuilder2;
    if (this.zzcgH)
    {
      str1 = ", allowedOverMetered=true";
      localStringBuilder2 = localStringBuilder1.append(str1);
      if (!this.zzcgI) {
        break label113;
      }
    }
    label113:
    for (String str2 = ", allowedWithLowBattery=true";; str2 = "")
    {
      return str2 + "}";
      str1 = "";
      break;
    }
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbd.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.LargeAssetEnqueueRequest
 * JD-Core Version:    0.7.0.1
 */