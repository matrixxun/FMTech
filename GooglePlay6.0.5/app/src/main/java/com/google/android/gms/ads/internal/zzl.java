package com.google.android.gms.ads.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzl
  implements Parcelable.Creator<InterstitialAdParameterParcel>
{
  static void zza$2350bab8(InterstitialAdParameterParcel paramInterstitialAdParameterParcel, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramInterstitialAdParameterParcel.versionCode);
    zzb.zza(paramParcel, 2, paramInterstitialAdParameterParcel.zzqF);
    zzb.zza(paramParcel, 3, paramInterstitialAdParameterParcel.zzqG);
    zzb.zza$2cfb68bf(paramParcel, 4, paramInterstitialAdParameterParcel.zzqH);
    zzb.zza(paramParcel, 5, paramInterstitialAdParameterParcel.zzqI);
    float f = paramInterstitialAdParameterParcel.zzqJ;
    zzb.zzb(paramParcel, 6, 4);
    paramParcel.writeFloat(f);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.zzl
 * JD-Core Version:    0.7.0.1
 */