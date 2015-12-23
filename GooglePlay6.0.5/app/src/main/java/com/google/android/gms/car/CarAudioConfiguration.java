package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CarAudioConfiguration
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarAudioConfiguration> CREATOR = new zza();
  public int audioFormat;
  public int channelConfig;
  final int mVersionCode;
  public int sampleRate;
  
  public CarAudioConfiguration(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mVersionCode = paramInt1;
    this.sampleRate = paramInt2;
    this.channelConfig = paramInt3;
    this.audioFormat = paramInt4;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    return getClass().getName() + "[sampleRate=" + this.sampleRate + ",channelConfig=" + this.channelConfig + ",audioFormat=" + this.audioFormat + "]";
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza$56713635(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarAudioConfiguration
 * JD-Core Version:    0.7.0.1
 */