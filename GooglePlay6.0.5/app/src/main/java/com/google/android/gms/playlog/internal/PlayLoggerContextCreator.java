package com.google.android.gms.playlog.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class PlayLoggerContextCreator
  implements Parcelable.Creator<PlayLoggerContext>
{
  static void zza$495264e0(PlayLoggerContext paramPlayLoggerContext, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramPlayLoggerContext.versionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramPlayLoggerContext.packageName);
    zzb.zzc(paramParcel, 3, paramPlayLoggerContext.packageVersionCode);
    zzb.zzc(paramParcel, 4, paramPlayLoggerContext.logSource);
    zzb.zza$2cfb68bf(paramParcel, 5, paramPlayLoggerContext.uploadAccountName);
    zzb.zza$2cfb68bf(paramParcel, 6, paramPlayLoggerContext.loggingId);
    zzb.zza(paramParcel, 7, paramPlayLoggerContext.logAndroidId);
    zzb.zza$2cfb68bf(paramParcel, 8, paramPlayLoggerContext.logSourceName);
    zzb.zza(paramParcel, 9, paramPlayLoggerContext.isAnonymous);
    zzb.zzc(paramParcel, 10, paramPlayLoggerContext.qosTier);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.playlog.internal.PlayLoggerContextCreator
 * JD-Core Version:    0.7.0.1
 */