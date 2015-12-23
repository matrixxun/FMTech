package com.google.android.gms.ads.internal.overlay;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;

public final class zzf
  implements Parcelable.Creator<AdOverlayInfoParcel>
{
  static void zza(AdOverlayInfoParcel paramAdOverlayInfoParcel, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramAdOverlayInfoParcel.versionCode);
    zzb.zza$377a007(paramParcel, 2, paramAdOverlayInfoParcel.zzDX, paramInt);
    zzb.zza$cdac282(paramParcel, 3, zze.zzI(paramAdOverlayInfoParcel.zzDY).asBinder());
    zzb.zza$cdac282(paramParcel, 4, zze.zzI(paramAdOverlayInfoParcel.zzDZ).asBinder());
    zzb.zza$cdac282(paramParcel, 5, zze.zzI(paramAdOverlayInfoParcel.zzEa).asBinder());
    zzb.zza$cdac282(paramParcel, 6, zze.zzI(paramAdOverlayInfoParcel.zzEb).asBinder());
    zzb.zza$2cfb68bf(paramParcel, 7, paramAdOverlayInfoParcel.zzEc);
    zzb.zza(paramParcel, 8, paramAdOverlayInfoParcel.zzEd);
    zzb.zza$2cfb68bf(paramParcel, 9, paramAdOverlayInfoParcel.zzEe);
    zzb.zza$cdac282(paramParcel, 10, zze.zzI(paramAdOverlayInfoParcel.zzEf).asBinder());
    zzb.zzc(paramParcel, 11, paramAdOverlayInfoParcel.orientation);
    zzb.zzc(paramParcel, 12, paramAdOverlayInfoParcel.zzEg);
    zzb.zza$2cfb68bf(paramParcel, 13, paramAdOverlayInfoParcel.url);
    zzb.zza$377a007(paramParcel, 14, paramAdOverlayInfoParcel.zzrw, paramInt);
    zzb.zza$cdac282(paramParcel, 15, zze.zzI(paramAdOverlayInfoParcel.zzEh).asBinder());
    zzb.zza$377a007(paramParcel, 17, paramAdOverlayInfoParcel.zzEj, paramInt);
    zzb.zza$2cfb68bf(paramParcel, 16, paramAdOverlayInfoParcel.zzEi);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.overlay.zzf
 * JD-Core Version:    0.7.0.1
 */