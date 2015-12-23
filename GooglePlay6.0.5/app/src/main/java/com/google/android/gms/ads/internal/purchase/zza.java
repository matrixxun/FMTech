package com.google.android.gms.ads.internal.purchase;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;

public final class zza
  implements Parcelable.Creator<GInAppPurchaseManagerInfoParcel>
{
  static void zza$629388ef(GInAppPurchaseManagerInfoParcel paramGInAppPurchaseManagerInfoParcel, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramGInAppPurchaseManagerInfoParcel.versionCode);
    zzb.zza$cdac282(paramParcel, 3, zze.zzI(paramGInAppPurchaseManagerInfoParcel.zzrR).asBinder());
    zzb.zza$cdac282(paramParcel, 4, zze.zzI(paramGInAppPurchaseManagerInfoParcel.zzES).asBinder());
    zzb.zza$cdac282(paramParcel, 5, zze.zzI(paramGInAppPurchaseManagerInfoParcel.zzET).asBinder());
    zzb.zza$cdac282(paramParcel, 6, zze.zzI(paramGInAppPurchaseManagerInfoParcel.zzEU).asBinder());
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.purchase.zza
 * JD-Core Version:    0.7.0.1
 */