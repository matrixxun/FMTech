package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzad
  implements Parcelable.Creator<UserAttributeParcel>
{
  static void zza$2732f0dd(UserAttributeParcel paramUserAttributeParcel, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramUserAttributeParcel.versionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramUserAttributeParcel.name);
    zzb.zza(paramParcel, 3, paramUserAttributeParcel.zzboS);
    Long localLong = paramUserAttributeParcel.zzboT;
    if (localLong != null)
    {
      zzb.zzb(paramParcel, 4, 8);
      paramParcel.writeLong(localLong.longValue());
    }
    Float localFloat = paramUserAttributeParcel.zzboU;
    if (localFloat != null)
    {
      zzb.zzb(paramParcel, 5, 4);
      paramParcel.writeFloat(localFloat.floatValue());
    }
    zzb.zza$2cfb68bf(paramParcel, 6, paramUserAttributeParcel.stringValue);
    zzb.zza$2cfb68bf(paramParcel, 7, paramUserAttributeParcel.zzbmA);
    zzb.zzI(paramParcel, i);
  }
  
  public static UserAttributeParcel zzgX(Parcel paramParcel)
  {
    int i = zza.zzbc(paramParcel);
    int j = 0;
    long l = 0L;
    String str1 = null;
    String str2 = null;
    Float localFloat = null;
    Long localLong = null;
    String str3 = null;
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
        str3 = zza.zzq(paramParcel, k);
        break;
      case 3: 
        l = zza.zzi(paramParcel, k);
        break;
      case 4: 
        int n = zza.zza(paramParcel, k);
        if (n == 0)
        {
          localLong = null;
        }
        else
        {
          zza.zza$ae3cd4b(paramParcel, n, 8);
          localLong = Long.valueOf(paramParcel.readLong());
        }
        break;
      case 5: 
        int m = zza.zza(paramParcel, k);
        if (m == 0)
        {
          localFloat = null;
        }
        else
        {
          zza.zza$ae3cd4b(paramParcel, m, 4);
          localFloat = Float.valueOf(paramParcel.readFloat());
        }
        break;
      case 6: 
        str2 = zza.zzq(paramParcel, k);
        break;
      case 7: 
        str1 = zza.zzq(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new UserAttributeParcel(j, str3, l, localLong, localFloat, str2, str1);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzad
 * JD-Core Version:    0.7.0.1
 */