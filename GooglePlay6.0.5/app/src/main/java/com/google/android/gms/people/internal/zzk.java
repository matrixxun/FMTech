package com.google.android.gms.people.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzk
  implements Parcelable.Creator<ParcelableLoadImageOptions>
{
  static void zza$277d357a(ParcelableLoadImageOptions paramParcelableLoadImageOptions, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramParcelableLoadImageOptions.zzbsM);
    zzb.zzc(paramParcel, 1000, paramParcelableLoadImageOptions.mVersionCode);
    zzb.zzc(paramParcel, 2, paramParcelableLoadImageOptions.zzbsN);
    zzb.zza(paramParcel, 3, paramParcelableLoadImageOptions.mUseLargePictureForCp2Images);
    zzb.zzI(paramParcel, i);
  }
  
  public static ParcelableLoadImageOptions zziX(Parcel paramParcel)
  {
    boolean bool = false;
    int i = zza.zzbc(paramParcel);
    int j = 0;
    int k = 0;
    int m = 0;
    while (paramParcel.dataPosition() < i)
    {
      int n = paramParcel.readInt();
      switch (0xFFFF & n)
      {
      default: 
        zza.zzb(paramParcel, n);
        break;
      case 1: 
        k = zza.zzg(paramParcel, n);
        break;
      case 1000: 
        m = zza.zzg(paramParcel, n);
        break;
      case 2: 
        j = zza.zzg(paramParcel, n);
        break;
      case 3: 
        bool = zza.zzc(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new ParcelableLoadImageOptions(m, k, j, bool);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.internal.zzk
 * JD-Core Version:    0.7.0.1
 */