package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class SafeBrowsingData
  implements SafeParcelable
{
  public static final Parcelable.Creator<SafeBrowsingData> CREATOR = new zzb();
  public final int mVersionCode;
  String zzbKt;
  
  SafeBrowsingData(int paramInt, String paramString)
  {
    this.mVersionCode = paramInt;
    this.zzbKt = paramString;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza$7e0dbe96(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.safetynet.SafeBrowsingData
 * JD-Core Version:    0.7.0.1
 */