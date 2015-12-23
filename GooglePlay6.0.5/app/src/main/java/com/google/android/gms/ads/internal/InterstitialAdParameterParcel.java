package com.google.android.gms.ads.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class InterstitialAdParameterParcel
  implements SafeParcelable
{
  public static final zzl CREATOR = new zzl();
  public final int versionCode;
  public final boolean zzqF;
  public final boolean zzqG;
  public final String zzqH;
  public final boolean zzqI;
  public final float zzqJ;
  
  InterstitialAdParameterParcel(int paramInt, boolean paramBoolean1, boolean paramBoolean2, String paramString, boolean paramBoolean3, float paramFloat)
  {
    this.versionCode = paramInt;
    this.zzqF = paramBoolean1;
    this.zzqG = paramBoolean2;
    this.zzqH = paramString;
    this.zzqI = paramBoolean3;
    this.zzqJ = paramFloat;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzl.zza$2350bab8(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.InterstitialAdParameterParcel
 * JD-Core Version:    0.7.0.1
 */