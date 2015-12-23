package com.google.android.gms.ads.internal.overlay;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzb
  implements Parcelable.Creator<AdLauncherIntentInfoParcel>
{
  static void zza$7a6d83fc(AdLauncherIntentInfoParcel paramAdLauncherIntentInfoParcel, Parcel paramParcel)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, 20293);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramAdLauncherIntentInfoParcel.versionCode);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 2, paramAdLauncherIntentInfoParcel.intentAction);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 3, paramAdLauncherIntentInfoParcel.url);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 4, paramAdLauncherIntentInfoParcel.mimeType);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 5, paramAdLauncherIntentInfoParcel.packageName);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 6, paramAdLauncherIntentInfoParcel.zzDi);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 7, paramAdLauncherIntentInfoParcel.zzDj);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 8, paramAdLauncherIntentInfoParcel.zzDk);
    com.google.android.gms.common.internal.safeparcel.zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.overlay.zzb
 * JD-Core Version:    0.7.0.1
 */