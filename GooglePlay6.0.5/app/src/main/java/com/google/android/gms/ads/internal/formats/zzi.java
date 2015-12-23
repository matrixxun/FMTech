package com.google.android.gms.ads.internal.formats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzi
  implements Parcelable.Creator<NativeAdOptionsParcel>
{
  static void zza$47e8eeb1(NativeAdOptionsParcel paramNativeAdOptionsParcel, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramNativeAdOptionsParcel.versionCode);
    zzb.zza(paramParcel, 2, paramNativeAdOptionsParcel.zzyC);
    zzb.zzc(paramParcel, 3, paramNativeAdOptionsParcel.zzyD);
    zzb.zza(paramParcel, 4, paramNativeAdOptionsParcel.zzyE);
    zzb.zzI(paramParcel, i);
  }
  
  public static NativeAdOptionsParcel zzf(Parcel paramParcel)
  {
    boolean bool1 = false;
    int i = zza.zzbc(paramParcel);
    int j = 0;
    boolean bool2 = false;
    int k = 0;
    while (paramParcel.dataPosition() < i)
    {
      int m = paramParcel.readInt();
      switch (0xFFFF & m)
      {
      default: 
        zza.zzb(paramParcel, m);
        break;
      case 1: 
        k = zza.zzg(paramParcel, m);
        break;
      case 2: 
        bool2 = zza.zzc(paramParcel, m);
        break;
      case 3: 
        j = zza.zzg(paramParcel, m);
        break;
      case 4: 
        bool1 = zza.zzc(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new NativeAdOptionsParcel(k, bool2, j, bool1);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.formats.zzi
 * JD-Core Version:    0.7.0.1
 */