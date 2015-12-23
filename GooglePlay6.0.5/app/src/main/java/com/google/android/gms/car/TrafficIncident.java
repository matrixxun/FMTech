package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class TrafficIncident
  implements SafeParcelable
{
  public static final Parcelable.Creator<TrafficIncident> CREATOR = new zzbq();
  public int eventCode;
  public int expectedIncidentDuration;
  public double latitude;
  public double longitude;
  final int mVersionCode;
  
  public TrafficIncident(int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2)
  {
    this.mVersionCode = paramInt1;
    this.eventCode = paramInt2;
    this.expectedIncidentDuration = paramInt3;
    this.longitude = paramDouble2;
    this.latitude = paramDouble1;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbq.zza$43bc91f0(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.TrafficIncident
 * JD-Core Version:    0.7.0.1
 */