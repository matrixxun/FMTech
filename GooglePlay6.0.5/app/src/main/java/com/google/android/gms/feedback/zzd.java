package com.google.android.gms.feedback;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzd
  implements Parcelable.Creator<LogOptions>
{
  static void zza$4f524a20(LogOptions paramLogOptions, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramLogOptions.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramLogOptions.mLogFilter);
    zzb.zza(paramParcel, 3, paramLogOptions.mIncludeRadioLogs);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.feedback.zzd
 * JD-Core Version:    0.7.0.1
 */