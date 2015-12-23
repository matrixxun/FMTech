package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CarInfo
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarInfo> CREATOR = new zzi();
  public boolean canPlayNativeMediaDuringVr;
  public int driverPosition;
  public String headUnitMake;
  public String headUnitModel;
  public int headUnitProtocolMajorVersionNumber;
  public int headUnitProtocolMinorVersionNumber;
  public String headUnitSoftwareBuild;
  public String headUnitSoftwareVersion;
  public boolean hideBatteryLevel;
  public boolean hidePhoneSignal;
  public boolean hideProjectedClock;
  final int mVersionCode;
  public String manufacturer;
  public String model;
  public String modelYear;
  public String nickname;
  public String vehicleId;
  
  public CarInfo()
  {
    this.mVersionCode = 5;
  }
  
  public CarInfo(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2, int paramInt3, boolean paramBoolean1, int paramInt4, String paramString5, String paramString6, String paramString7, String paramString8, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, String paramString9)
  {
    this.mVersionCode = paramInt1;
    this.manufacturer = paramString1;
    this.model = paramString2;
    this.modelYear = paramString3;
    this.vehicleId = paramString4;
    this.headUnitProtocolMajorVersionNumber = paramInt2;
    this.headUnitProtocolMinorVersionNumber = paramInt3;
    this.hideProjectedClock = paramBoolean1;
    this.driverPosition = paramInt4;
    this.headUnitMake = paramString5;
    this.headUnitModel = paramString6;
    this.headUnitSoftwareBuild = paramString7;
    this.headUnitSoftwareVersion = paramString8;
    this.canPlayNativeMediaDuringVr = paramBoolean2;
    this.hidePhoneSignal = paramBoolean3;
    this.hideBatteryLevel = paramBoolean4;
    this.nickname = paramString9;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.modelYear);
    localStringBuilder.append(" ");
    localStringBuilder.append(this.manufacturer);
    localStringBuilder.append(" ");
    localStringBuilder.append(this.model);
    localStringBuilder.append(" ");
    localStringBuilder.append(this.vehicleId);
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzi.zza$cbf78dd(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarInfo
 * JD-Core Version:    0.7.0.1
 */