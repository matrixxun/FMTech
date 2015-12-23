package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CarMediaBrowserRootNode
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarMediaBrowserRootNode> CREATOR = new zzl();
  final int mVersionCode;
  public CarMediaSource[] mediaSources;
  public String path;
  
  public CarMediaBrowserRootNode()
  {
    this.mVersionCode = 1;
  }
  
  public CarMediaBrowserRootNode(int paramInt, String paramString, CarMediaSource[] paramArrayOfCarMediaSource)
  {
    this.mVersionCode = paramInt;
    this.path = paramString;
    this.mediaSources = paramArrayOfCarMediaSource;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzl.zza(this, paramParcel, paramInt);
  }
  
  public static class CarMediaSource
    implements SafeParcelable
  {
    public static final Parcelable.Creator<CarMediaSource> CREATOR = new zzq();
    public byte[] albumArt;
    final int mVersionCode;
    public String name;
    public String sourcePath;
    
    public CarMediaSource()
    {
      this.mVersionCode = 1;
    }
    
    public CarMediaSource(int paramInt, String paramString1, String paramString2, byte[] paramArrayOfByte)
    {
      this.mVersionCode = paramInt;
      this.sourcePath = paramString1;
      this.name = paramString2;
      this.albumArt = paramArrayOfByte;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzq.zza$5f9abf6(this, paramParcel);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarMediaBrowserRootNode
 * JD-Core Version:    0.7.0.1
 */