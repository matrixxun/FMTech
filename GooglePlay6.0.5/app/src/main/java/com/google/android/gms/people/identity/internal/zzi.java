package com.google.android.gms.people.identity.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzi
  implements Parcelable.Creator<ParcelableGetOptions>
{
  static void zza$4766bf38(ParcelableGetOptions paramParcelableGetOptions, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza(paramParcel, 1, paramParcelableGetOptions.zzbvc);
    zzb.zzc(paramParcel, 1000, paramParcelableGetOptions.mVersionCode);
    zzb.zza(paramParcel, 2, paramParcelableGetOptions.zzbth);
    zzb.zza$2cfb68bf(paramParcel, 3, paramParcelableGetOptions.zzbtc);
    zzb.zza(paramParcel, 4, paramParcelableGetOptions.zzbvd);
    zzb.zza$f7bef55(paramParcel, 5, paramParcelableGetOptions.zzbtd);
    zzb.zzI(paramParcel, i);
  }
  
  public static ParcelableGetOptions zzhH(Parcel paramParcel)
  {
    Bundle localBundle = null;
    boolean bool1 = false;
    int i = zza.zzbc(paramParcel);
    String str = null;
    boolean bool2 = false;
    boolean bool3 = false;
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
        bool3 = zza.zzc(paramParcel, k);
        break;
      case 1000: 
        j = zza.zzg(paramParcel, k);
        break;
      case 2: 
        bool2 = zza.zzc(paramParcel, k);
        break;
      case 3: 
        str = zza.zzq(paramParcel, k);
        break;
      case 4: 
        bool1 = zza.zzc(paramParcel, k);
        break;
      case 5: 
        localBundle = zza.zzs(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new ParcelableGetOptions(j, bool3, bool2, bool1, str, localBundle);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.identity.internal.zzi
 * JD-Core Version:    0.7.0.1
 */