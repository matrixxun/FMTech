package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class AttestationData
  implements SafeParcelable
{
  public static final Parcelable.Creator<AttestationData> CREATOR = new zza();
  public final int mVersionCode;
  String zzbKs;
  
  AttestationData(int paramInt, String paramString)
  {
    this.mVersionCode = paramInt;
    this.zzbKs = paramString;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza$4f44a932(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.safetynet.AttestationData
 * JD-Core Version:    0.7.0.1
 */