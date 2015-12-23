package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class LargeAssetQuery
  implements SafeParcelable
{
  public static final Parcelable.Creator<LargeAssetQuery> CREATOR = new zzbe();
  final int mVersionCode;
  public final Uri zzbru;
  public final int zzcgJ;
  
  LargeAssetQuery(int paramInt1, int paramInt2, Uri paramUri)
  {
    this.mVersionCode = paramInt1;
    this.zzcgJ = paramInt2;
    this.zzbru = paramUri;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final String toString()
  {
    return "LargeAssetQuery{stateFlags=" + this.zzcgJ + ", destinationUri=" + this.zzbru + "}";
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbe.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.LargeAssetQuery
 * JD-Core Version:    0.7.0.1
 */