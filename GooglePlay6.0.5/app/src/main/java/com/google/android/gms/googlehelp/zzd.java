package com.google.android.gms.googlehelp;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzd
  implements Parcelable.Creator<OfflineSuggestion>
{
  static void zza$15961926(OfflineSuggestion paramOfflineSuggestion, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramOfflineSuggestion.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramOfflineSuggestion.zzyx);
    zzb.zza$2cfb68bf(paramParcel, 3, paramOfflineSuggestion.zzaBy);
    zzb.zza$2cfb68bf(paramParcel, 4, paramOfflineSuggestion.zzya);
    zzb.zza$2cfb68bf(paramParcel, 5, paramOfflineSuggestion.zzbbg);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.zzd
 * JD-Core Version:    0.7.0.1
 */