package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CarPhoneStatus
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarPhoneStatus> CREATOR = new zzs();
  public CarCall[] calls;
  final int mVersionCode;
  public int signalStrength;
  
  public CarPhoneStatus()
  {
    this.mVersionCode = 1;
  }
  
  public CarPhoneStatus(int paramInt1, CarCall[] paramArrayOfCarCall, int paramInt2)
  {
    this.mVersionCode = paramInt1;
    this.calls = paramArrayOfCarCall;
    this.signalStrength = paramInt2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzs.zza(this, paramParcel, paramInt);
  }
  
  public static class CarCall
    implements SafeParcelable
  {
    public static final Parcelable.Creator<CarCall> CREATOR = new zzr();
    public int callDurationSeconds;
    public String callerId;
    public String callerNumber;
    public String callerNumberType;
    public byte[] callerThumbnail;
    final int mVersionCode;
    public int state;
    
    public CarCall()
    {
      this.mVersionCode = 1;
    }
    
    public CarCall(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, String paramString3, byte[] paramArrayOfByte)
    {
      this.mVersionCode = paramInt1;
      this.state = paramInt2;
      this.callerNumber = paramString1;
      this.callDurationSeconds = paramInt3;
      this.callerId = paramString2;
      this.callerNumberType = paramString3;
      this.callerThumbnail = paramArrayOfByte;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzr.zza$31dce7c3(this, paramParcel);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarPhoneStatus
 * JD-Core Version:    0.7.0.1
 */