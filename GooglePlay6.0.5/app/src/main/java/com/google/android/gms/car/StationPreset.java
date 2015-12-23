package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class StationPreset
  implements SafeParcelable
{
  public static final Parcelable.Creator<StationPreset> CREATOR = new zzbn();
  public int channel;
  final int mVersionCode;
  public int subChannel;
  public int type;
  
  public StationPreset(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mVersionCode = paramInt1;
    this.type = paramInt2;
    this.channel = paramInt3;
    this.subChannel = paramInt4;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbn.zza$69af1994(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.StationPreset
 * JD-Core Version:    0.7.0.1
 */