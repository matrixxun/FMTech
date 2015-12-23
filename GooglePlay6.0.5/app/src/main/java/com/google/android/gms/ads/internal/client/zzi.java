package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzi
  implements Parcelable.Creator<AdSizeParcel>
{
  static void zza(AdSizeParcel paramAdSizeParcel, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramAdSizeParcel.versionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramAdSizeParcel.zzuA);
    zzb.zzc(paramParcel, 3, paramAdSizeParcel.height);
    zzb.zzc(paramParcel, 4, paramAdSizeParcel.heightPixels);
    zzb.zza(paramParcel, 5, paramAdSizeParcel.zzuB);
    zzb.zzc(paramParcel, 6, paramAdSizeParcel.width);
    zzb.zzc(paramParcel, 7, paramAdSizeParcel.widthPixels);
    zzb.zza$2d7953c6(paramParcel, 8, paramAdSizeParcel.zzuC, paramInt);
    zzb.zza(paramParcel, 9, paramAdSizeParcel.zzuD);
    zzb.zza(paramParcel, 10, paramAdSizeParcel.zzuE);
    zzb.zza(paramParcel, 11, paramAdSizeParcel.zzuF);
    zzb.zzI(paramParcel, i);
  }
  
  public static AdSizeParcel zzd(Parcel paramParcel)
  {
    AdSizeParcel[] arrayOfAdSizeParcel = null;
    boolean bool1 = false;
    int i = zza.zzbc(paramParcel);
    boolean bool2 = false;
    boolean bool3 = false;
    int j = 0;
    int k = 0;
    boolean bool4 = false;
    int m = 0;
    int n = 0;
    String str = null;
    int i1 = 0;
    while (paramParcel.dataPosition() < i)
    {
      int i2 = paramParcel.readInt();
      switch (0xFFFF & i2)
      {
      default: 
        zza.zzb(paramParcel, i2);
        break;
      case 1: 
        i1 = zza.zzg(paramParcel, i2);
        break;
      case 2: 
        str = zza.zzq(paramParcel, i2);
        break;
      case 3: 
        n = zza.zzg(paramParcel, i2);
        break;
      case 4: 
        m = zza.zzg(paramParcel, i2);
        break;
      case 5: 
        bool4 = zza.zzc(paramParcel, i2);
        break;
      case 6: 
        k = zza.zzg(paramParcel, i2);
        break;
      case 7: 
        j = zza.zzg(paramParcel, i2);
        break;
      case 8: 
        arrayOfAdSizeParcel = (AdSizeParcel[])zza.zzb(paramParcel, i2, AdSizeParcel.CREATOR);
        break;
      case 9: 
        bool3 = zza.zzc(paramParcel, i2);
        break;
      case 10: 
        bool2 = zza.zzc(paramParcel, i2);
        break;
      case 11: 
        bool1 = zza.zzc(paramParcel, i2);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new AdSizeParcel(i1, str, n, m, bool4, k, j, arrayOfAdSizeParcel, bool3, bool2, bool1);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.client.zzi
 * JD-Core Version:    0.7.0.1
 */