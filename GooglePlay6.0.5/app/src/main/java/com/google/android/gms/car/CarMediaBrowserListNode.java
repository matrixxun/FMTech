package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CarMediaBrowserListNode
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarMediaBrowserListNode> CREATOR = new zzk();
  public CarMediaBrowserSourceNode.CarMediaList list = new CarMediaBrowserSourceNode.CarMediaList();
  final int mVersionCode;
  public CarMediaSong[] songs;
  public int start;
  public int total;
  
  public CarMediaBrowserListNode()
  {
    this.mVersionCode = 1;
  }
  
  public CarMediaBrowserListNode(int paramInt1, CarMediaBrowserSourceNode.CarMediaList paramCarMediaList, int paramInt2, int paramInt3, CarMediaSong[] paramArrayOfCarMediaSong)
  {
    this.mVersionCode = paramInt1;
    this.list = paramCarMediaList;
    this.start = paramInt2;
    this.total = paramInt3;
    this.songs = paramArrayOfCarMediaSong;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzk.zza(this, paramParcel, paramInt);
  }
  
  public static class CarMediaSong
    implements SafeParcelable
  {
    public static final Parcelable.Creator<CarMediaSong> CREATOR = new zzp();
    public String album;
    public String artist;
    final int mVersionCode;
    public String name;
    public String path;
    
    public CarMediaSong()
    {
      this.mVersionCode = 1;
    }
    
    public CarMediaSong(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4)
    {
      this.mVersionCode = paramInt;
      this.path = paramString1;
      this.name = paramString2;
      this.artist = paramString3;
      this.album = paramString4;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzp.zza$4fb58b4(this, paramParcel);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarMediaBrowserListNode
 * JD-Core Version:    0.7.0.1
 */