package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CarFacet
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarFacet> CREATOR = new zzg();
  final int mVersionCode;
  public String packageName;
  public int type;
  
  public CarFacet(int paramInt1, int paramInt2, String paramString)
  {
    this.mVersionCode = paramInt1;
    this.type = paramInt2;
    this.packageName = paramString;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzg.zza$5d631bba(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarFacet
 * JD-Core Version:    0.7.0.1
 */