package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class CountryCreator
  implements Parcelable.Creator<Country>
{
  static void zza$53a79cc9(Country paramCountry, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramCountry.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramCountry.mName);
    zzb.zza$2cfb68bf(paramParcel, 3, paramCountry.mCode);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.CountryCreator
 * JD-Core Version:    0.7.0.1
 */