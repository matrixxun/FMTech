package com.google.android.gms.feedback;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class LogOptions
  implements SafeParcelable
{
  public static final Parcelable.Creator<LogOptions> CREATOR = new zzd();
  public boolean mIncludeRadioLogs;
  public String mLogFilter;
  public final int mVersionCode;
  
  LogOptions(int paramInt, String paramString, boolean paramBoolean)
  {
    this.mVersionCode = paramInt;
    this.mLogFilter = paramString;
    this.mIncludeRadioLogs = paramBoolean;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzd.zza$4f524a20(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.feedback.LogOptions
 * JD-Core Version:    0.7.0.1
 */