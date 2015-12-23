package com.google.android.gms.feedback;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zze
  implements Parcelable.Creator<ThemeSettings>
{
  static void zza$2c435116(ThemeSettings paramThemeSettings, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramThemeSettings.mVersionCode);
    zzb.zzc(paramParcel, 2, paramThemeSettings.zzaLn);
    zzb.zzc(paramParcel, 3, paramThemeSettings.zzaLo);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.feedback.zze
 * JD-Core Version:    0.7.0.1
 */