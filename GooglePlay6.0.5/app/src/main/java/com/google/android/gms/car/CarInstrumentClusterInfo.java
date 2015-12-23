package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CarInstrumentClusterInfo
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarInstrumentClusterInfo> CREATOR = new zzj();
  final int mVersionCode;
  public int type;
  public int zzaea;
  public int zzaeb;
  public int zzaec;
  public int zzaed;
  
  public CarInstrumentClusterInfo()
  {
    this.mVersionCode = 1;
  }
  
  public CarInstrumentClusterInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    this.mVersionCode = paramInt1;
    this.zzaea = paramInt2;
    this.type = paramInt3;
    this.zzaeb = paramInt4;
    this.zzaec = paramInt5;
    this.zzaed = paramInt6;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzj.zza$701050d0(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarInstrumentClusterInfo
 * JD-Core Version:    0.7.0.1
 */