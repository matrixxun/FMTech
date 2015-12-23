package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public class LargeAssetSyncRequestPayload
  implements SafeParcelable
{
  public static final Parcelable.Creator<LargeAssetSyncRequestPayload> CREATOR = new zzbk();
  public final String path;
  final int versionCode;
  public final long zzchc;
  
  public LargeAssetSyncRequestPayload(int paramInt, String paramString, long paramLong)
  {
    this.versionCode = paramInt;
    this.path = ((String)zzx.zzb(paramString, "path"));
    this.zzchc = paramLong;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    return "LargeAssetSyncRequestPayload{path='" + this.path + "', offset=" + this.zzchc + "}";
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbk.zza$2799b5f3(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.LargeAssetSyncRequestPayload
 * JD-Core Version:    0.7.0.1
 */