package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CarFrxEvent
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarFrxEvent> CREATOR = new zzh();
  public int event;
  public int fromState;
  final int mVersionCode;
  public int toState;
  
  public CarFrxEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mVersionCode = paramInt1;
    this.fromState = paramInt2;
    this.toState = paramInt3;
    this.event = paramInt4;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzh.zza$7583a3d(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarFrxEvent
 * JD-Core Version:    0.7.0.1
 */