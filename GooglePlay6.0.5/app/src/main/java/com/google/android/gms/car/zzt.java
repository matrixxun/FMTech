package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzt
  implements Parcelable.Creator<CarSensorEvent>
{
  static void zza$7fb35211(CarSensorEvent paramCarSensorEvent, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramCarSensorEvent.sensorType);
    zzb.zzc(paramParcel, 1000, paramCarSensorEvent.mVersionCode);
    zzb.zza(paramParcel, 2, paramCarSensorEvent.timeStampNs);
    float[] arrayOfFloat = paramCarSensorEvent.floatValues;
    if (arrayOfFloat != null)
    {
      int j = zzb.zzH(paramParcel, 3);
      paramParcel.writeFloatArray(arrayOfFloat);
      zzb.zzI(paramParcel, j);
    }
    zzb.zza$52910762(paramParcel, 4, paramCarSensorEvent.byteValues);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzt
 * JD-Core Version:    0.7.0.1
 */