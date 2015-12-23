package com.google.android.gms.ads.internal.reward.mediation.client;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class RewardItemParcel
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  public final String type;
  public final int versionCode;
  public final int zzJW;
  
  public RewardItemParcel(int paramInt1, String paramString, int paramInt2)
  {
    this.versionCode = paramInt1;
    this.type = paramString;
    this.zzJW = paramInt2;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza$41e7b039(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel
 * JD-Core Version:    0.7.0.1
 */