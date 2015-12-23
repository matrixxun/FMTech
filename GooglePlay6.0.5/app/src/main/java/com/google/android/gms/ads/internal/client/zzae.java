package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzae
  implements Parcelable.Creator<SearchAdRequestParcel>
{
  static void zza$a7ba428(SearchAdRequestParcel paramSearchAdRequestParcel, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramSearchAdRequestParcel.versionCode);
    zzb.zzc(paramParcel, 2, paramSearchAdRequestParcel.zzvn);
    zzb.zzc(paramParcel, 3, paramSearchAdRequestParcel.backgroundColor);
    zzb.zzc(paramParcel, 4, paramSearchAdRequestParcel.zzvo);
    zzb.zzc(paramParcel, 5, paramSearchAdRequestParcel.zzvp);
    zzb.zzc(paramParcel, 6, paramSearchAdRequestParcel.zzvq);
    zzb.zzc(paramParcel, 7, paramSearchAdRequestParcel.zzvr);
    zzb.zzc(paramParcel, 8, paramSearchAdRequestParcel.zzvs);
    zzb.zzc(paramParcel, 9, paramSearchAdRequestParcel.zzvt);
    zzb.zza$2cfb68bf(paramParcel, 10, paramSearchAdRequestParcel.zzvu);
    zzb.zzc(paramParcel, 11, paramSearchAdRequestParcel.zzvv);
    zzb.zza$2cfb68bf(paramParcel, 12, paramSearchAdRequestParcel.zzvw);
    zzb.zzc(paramParcel, 13, paramSearchAdRequestParcel.zzvx);
    zzb.zzc(paramParcel, 14, paramSearchAdRequestParcel.zzvy);
    zzb.zza$2cfb68bf(paramParcel, 15, paramSearchAdRequestParcel.query);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.client.zzae
 * JD-Core Version:    0.7.0.1
 */