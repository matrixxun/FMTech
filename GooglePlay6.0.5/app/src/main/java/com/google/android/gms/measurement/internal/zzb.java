package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;

public final class zzb
  implements Parcelable.Creator<AppMetadata>
{
  static void zza$4fcb2b27(AppMetadata paramAppMetadata, Parcel paramParcel)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, 20293);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramAppMetadata.versionCode);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 2, paramAppMetadata.packageName);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 3, paramAppMetadata.zzbmb);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 4, paramAppMetadata.zzbcL);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 5, paramAppMetadata.zzbmc);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 6, paramAppMetadata.zzbmd);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 7, paramAppMetadata.zzbme);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 8, paramAppMetadata.zzbmf);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 9, paramAppMetadata.zzbmg);
    com.google.android.gms.common.internal.safeparcel.zzb.zzI(paramParcel, i);
  }
  
  public static AppMetadata zzgU(Parcel paramParcel)
  {
    long l1 = 0L;
    boolean bool = false;
    String str1 = null;
    int i = zza.zzbc(paramParcel);
    long l2 = l1;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    int j = 0;
    while (paramParcel.dataPosition() < i)
    {
      int k = paramParcel.readInt();
      switch (0xFFFF & k)
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        j = zza.zzg(paramParcel, k);
        break;
      case 2: 
        str5 = zza.zzq(paramParcel, k);
        break;
      case 3: 
        str4 = zza.zzq(paramParcel, k);
        break;
      case 4: 
        str3 = zza.zzq(paramParcel, k);
        break;
      case 5: 
        str2 = zza.zzq(paramParcel, k);
        break;
      case 6: 
        l2 = zza.zzi(paramParcel, k);
        break;
      case 7: 
        l1 = zza.zzi(paramParcel, k);
        break;
      case 8: 
        str1 = zza.zzq(paramParcel, k);
        break;
      case 9: 
        bool = zza.zzc(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new AppMetadata(j, str5, str4, str3, str2, l2, l1, str1, bool);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzb
 * JD-Core Version:    0.7.0.1
 */