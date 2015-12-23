package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import java.util.Arrays;

public class Asset
  implements SafeParcelable
{
  public static final Parcelable.Creator<Asset> CREATOR = new zza();
  public byte[] mData;
  final int mVersionCode;
  public Uri uri;
  public String zzcem;
  public ParcelFileDescriptor zzcen;
  
  Asset(int paramInt, byte[] paramArrayOfByte, String paramString, ParcelFileDescriptor paramParcelFileDescriptor, Uri paramUri)
  {
    this.mVersionCode = paramInt;
    this.mData = paramArrayOfByte;
    this.zzcem = paramString;
    this.zzcen = paramParcelFileDescriptor;
    this.uri = paramUri;
  }
  
  public static Asset createFromFd(ParcelFileDescriptor paramParcelFileDescriptor)
  {
    if (paramParcelFileDescriptor == null) {
      throw new IllegalArgumentException("Asset fd cannot be null");
    }
    return new Asset(1, null, null, paramParcelFileDescriptor, null);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    Asset localAsset;
    do
    {
      return true;
      if (!(paramObject instanceof Asset)) {
        return false;
      }
      localAsset = (Asset)paramObject;
    } while ((zzw.equal(this.mData, localAsset.mData)) && (zzw.equal(this.zzcem, localAsset.zzcem)) && (zzw.equal(this.zzcen, localAsset.zzcen)) && (zzw.equal(this.uri, localAsset.uri)));
    return false;
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = this.mData;
    arrayOfObject[1] = this.zzcem;
    arrayOfObject[2] = this.zzcen;
    arrayOfObject[3] = this.uri;
    return Arrays.hashCode(arrayOfObject);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Asset[@");
    localStringBuilder.append(Integer.toHexString(hashCode()));
    if (this.zzcem == null) {
      localStringBuilder.append(", nodigest");
    }
    for (;;)
    {
      if (this.mData != null)
      {
        localStringBuilder.append(", size=");
        localStringBuilder.append(this.mData.length);
      }
      if (this.zzcen != null)
      {
        localStringBuilder.append(", fd=");
        localStringBuilder.append(this.zzcen);
      }
      if (this.uri != null)
      {
        localStringBuilder.append(", uri=");
        localStringBuilder.append(this.uri);
      }
      localStringBuilder.append("]");
      return localStringBuilder.toString();
      localStringBuilder.append(", ");
      localStringBuilder.append(this.zzcem);
    }
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt | 0x1);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.Asset
 * JD-Core Version:    0.7.0.1
 */