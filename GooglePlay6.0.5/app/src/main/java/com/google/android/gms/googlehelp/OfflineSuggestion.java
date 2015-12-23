package com.google.android.gms.googlehelp;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class OfflineSuggestion
  implements SafeParcelable
{
  public static final Parcelable.Creator<OfflineSuggestion> CREATOR = new zzd();
  final int mVersionCode;
  final String zzaBy;
  final String zzbbg;
  final String zzya;
  final String zzyx;
  
  OfflineSuggestion(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.mVersionCode = paramInt;
    this.zzyx = paramString1;
    this.zzaBy = paramString2;
    this.zzya = paramString3;
    this.zzbbg = paramString4;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzd.zza$15961926(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.OfflineSuggestion
 * JD-Core Version:    0.7.0.1
 */