package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CarUiInfo
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarUiInfo> CREATOR = new zzu();
  final int mVersionCode;
  boolean zzaeM;
  boolean zzaeN;
  boolean zzaeO;
  boolean zzaeP;
  
  CarUiInfo(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.mVersionCode = paramInt;
    this.zzaeM = paramBoolean1;
    this.zzaeN = paramBoolean2;
    this.zzaeO = paramBoolean3;
    this.zzaeP = paramBoolean4;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = Boolean.valueOf(this.zzaeM);
    arrayOfObject[1] = Boolean.valueOf(this.zzaeN);
    arrayOfObject[2] = Boolean.valueOf(this.zzaeO);
    arrayOfObject[3] = Boolean.valueOf(this.zzaeP);
    return String.format("CarUiInfo (hasRotaryController: %b, hasTouchscreen: %b, hasSearchButton: %b, hasTouchpadForUiNavigation: %b)", arrayOfObject);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzu.zza$16269669(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarUiInfo
 * JD-Core Version:    0.7.0.1
 */