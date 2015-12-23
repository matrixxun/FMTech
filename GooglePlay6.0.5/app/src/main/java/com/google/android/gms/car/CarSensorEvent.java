package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CarSensorEvent
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarSensorEvent> CREATOR = new zzt();
  public final byte[] byteValues;
  public final float[] floatValues;
  final int mVersionCode;
  public int sensorType;
  public long timeStampNs;
  
  public CarSensorEvent(int paramInt1, int paramInt2, long paramLong, float[] paramArrayOfFloat, byte[] paramArrayOfByte)
  {
    this.mVersionCode = paramInt1;
    this.sensorType = paramInt2;
    this.timeStampNs = paramLong;
    this.floatValues = paramArrayOfFloat;
    this.byteValues = paramArrayOfByte;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    int i = 0;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getName() + "[");
    localStringBuilder.append("type:" + Integer.toHexString(this.sensorType));
    if ((this.floatValues != null) && (this.floatValues.length > 0))
    {
      localStringBuilder.append(" float values:");
      for (float f : this.floatValues) {
        localStringBuilder.append(" " + f);
      }
    }
    if ((this.byteValues != null) && (this.byteValues.length > 0))
    {
      localStringBuilder.append(" byte values:");
      byte[] arrayOfByte = this.byteValues;
      int j = arrayOfByte.length;
      while (i < j)
      {
        int k = arrayOfByte[i];
        localStringBuilder.append(" " + k);
        i++;
      }
    }
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzt.zza$7fb35211(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarSensorEvent
 * JD-Core Version:    0.7.0.1
 */