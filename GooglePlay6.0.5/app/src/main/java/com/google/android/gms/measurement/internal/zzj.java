package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzj
  implements Parcelable.Creator<EventParcel>
{
  static void zza(EventParcel paramEventParcel, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramEventParcel.versionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramEventParcel.name);
    zzb.zza$377a007(paramParcel, 3, paramEventParcel.zzbmz, paramInt);
    zzb.zza$2cfb68bf(paramParcel, 4, paramEventParcel.zzbmA);
    zzb.zza(paramParcel, 5, paramEventParcel.zzbmB);
    zzb.zzI(paramParcel, i);
  }
  
  public static EventParcel zzgW(Parcel paramParcel)
  {
    String str1 = null;
    int i = zza.zzbc(paramParcel);
    int j = 0;
    long l = 0L;
    EventParams localEventParams = null;
    String str2 = null;
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
        str2 = zza.zzq(paramParcel, k);
        break;
      case 3: 
        localEventParams = (EventParams)zza.zza(paramParcel, k, EventParams.CREATOR);
        break;
      case 4: 
        str1 = zza.zzq(paramParcel, k);
        break;
      case 5: 
        l = zza.zzi(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new EventParcel(j, str2, localEventParams, str1, l);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzj
 * JD-Core Version:    0.7.0.1
 */