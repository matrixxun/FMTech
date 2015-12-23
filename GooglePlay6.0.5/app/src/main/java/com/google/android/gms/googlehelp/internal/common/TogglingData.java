package com.google.android.gms.googlehelp.internal.common;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class TogglingData
  implements SafeParcelable
{
  public static final Parcelable.Creator<TogglingData> CREATOR = new zzk();
  final int mVersionCode;
  String zzbaC;
  String zzbbu;
  String zzbbv;
  
  private TogglingData()
  {
    this.mVersionCode = 1;
  }
  
  TogglingData(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    this.mVersionCode = paramInt;
    this.zzbaC = paramString1;
    this.zzbbu = paramString2;
    this.zzbbv = paramString3;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzk.zza$637bf5fe(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.internal.common.TogglingData
 * JD-Core Version:    0.7.0.1
 */