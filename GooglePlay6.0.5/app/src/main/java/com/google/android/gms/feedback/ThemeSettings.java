package com.google.android.gms.feedback;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ThemeSettings
  implements SafeParcelable
{
  public static final Parcelable.Creator<ThemeSettings> CREATOR = new zze();
  final int mVersionCode;
  public int zzaLn;
  int zzaLo;
  
  public ThemeSettings()
  {
    this(1, 0, 0);
  }
  
  ThemeSettings(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mVersionCode = paramInt1;
    this.zzaLn = paramInt2;
    this.zzaLo = paramInt3;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zze.zza$2c435116(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.feedback.ThemeSettings
 * JD-Core Version:    0.7.0.1
 */