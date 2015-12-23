package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CarMediaBrowserSourceNode
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarMediaBrowserSourceNode> CREATOR = new zzn();
  public CarMediaList[] lists;
  final int mVersionCode;
  public CarMediaBrowserRootNode.CarMediaSource mediaSource;
  public int start;
  public int total;
  
  public CarMediaBrowserSourceNode()
  {
    this.mVersionCode = 1;
    this.mediaSource = new CarMediaBrowserRootNode.CarMediaSource();
  }
  
  public CarMediaBrowserSourceNode(int paramInt1, CarMediaBrowserRootNode.CarMediaSource paramCarMediaSource, int paramInt2, int paramInt3, CarMediaList[] paramArrayOfCarMediaList)
  {
    this.mVersionCode = paramInt1;
    this.mediaSource = paramCarMediaSource;
    this.start = paramInt2;
    this.total = paramInt3;
    this.lists = paramArrayOfCarMediaList;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzn.zza(this, paramParcel, paramInt);
  }
  
  public static class CarMediaList
    implements SafeParcelable
  {
    public static final Parcelable.Creator<CarMediaList> CREATOR = new zzo();
    public byte[] albumArt;
    final int mVersionCode;
    public String name;
    public String path;
    public int type;
    
    public CarMediaList()
    {
      this.mVersionCode = 1;
    }
    
    public CarMediaList(int paramInt1, String paramString1, String paramString2, byte[] paramArrayOfByte, int paramInt2)
    {
      this.mVersionCode = paramInt1;
      this.path = paramString1;
      this.name = paramString2;
      this.albumArt = paramArrayOfByte;
      this.type = paramInt2;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzo.zza$17868e60(this, paramParcel);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarMediaBrowserSourceNode
 * JD-Core Version:    0.7.0.1
 */