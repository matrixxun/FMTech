package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CarMediaBrowserSongNode
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarMediaBrowserSongNode> CREATOR = new zzm();
  public byte[] albumArt;
  public int durationSeconds;
  final int mVersionCode;
  public CarMediaBrowserListNode.CarMediaSong song;
  
  public CarMediaBrowserSongNode()
  {
    this.mVersionCode = 1;
    this.song = new CarMediaBrowserListNode.CarMediaSong();
  }
  
  public CarMediaBrowserSongNode(int paramInt1, CarMediaBrowserListNode.CarMediaSong paramCarMediaSong, byte[] paramArrayOfByte, int paramInt2)
  {
    this.mVersionCode = paramInt1;
    this.song = paramCarMediaSong;
    this.albumArt = paramArrayOfByte;
    this.durationSeconds = paramInt2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzm.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarMediaBrowserSongNode
 * JD-Core Version:    0.7.0.1
 */