package com.google.android.gms.ads.internal.client;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzg
  implements Parcelable.Creator<AdRequestParcel>
{
  static void zza(AdRequestParcel paramAdRequestParcel, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramAdRequestParcel.versionCode);
    zzb.zza(paramParcel, 2, paramAdRequestParcel.zztV);
    zzb.zza$f7bef55(paramParcel, 3, paramAdRequestParcel.extras);
    zzb.zzc(paramParcel, 4, paramAdRequestParcel.zztW);
    zzb.zzb$62107c48(paramParcel, 5, paramAdRequestParcel.zztX);
    zzb.zza(paramParcel, 6, paramAdRequestParcel.zztY);
    zzb.zzc(paramParcel, 7, paramAdRequestParcel.zztZ);
    zzb.zza(paramParcel, 8, paramAdRequestParcel.zzua);
    zzb.zza$2cfb68bf(paramParcel, 9, paramAdRequestParcel.zzub);
    zzb.zza$377a007(paramParcel, 10, paramAdRequestParcel.zzuc, paramInt);
    zzb.zza$377a007(paramParcel, 11, paramAdRequestParcel.zzud, paramInt);
    zzb.zza$2cfb68bf(paramParcel, 12, paramAdRequestParcel.zzue);
    zzb.zza$f7bef55(paramParcel, 13, paramAdRequestParcel.zzuf);
    zzb.zza$f7bef55(paramParcel, 14, paramAdRequestParcel.zzug);
    zzb.zzb$62107c48(paramParcel, 15, paramAdRequestParcel.zzuh);
    zzb.zza$2cfb68bf(paramParcel, 17, paramAdRequestParcel.zzuj);
    zzb.zza$2cfb68bf(paramParcel, 16, paramAdRequestParcel.zzui);
    zzb.zza(paramParcel, 18, paramAdRequestParcel.zzuk);
    zzb.zzI(paramParcel, i);
  }
  
  public static AdRequestParcel zzc(Parcel paramParcel)
  {
    int i = zza.zzbc(paramParcel);
    int j = 0;
    long l = 0L;
    Bundle localBundle1 = null;
    int k = 0;
    ArrayList localArrayList1 = null;
    boolean bool1 = false;
    int m = 0;
    boolean bool2 = false;
    String str1 = null;
    SearchAdRequestParcel localSearchAdRequestParcel = null;
    Location localLocation = null;
    String str2 = null;
    Bundle localBundle2 = null;
    Bundle localBundle3 = null;
    ArrayList localArrayList2 = null;
    String str3 = null;
    String str4 = null;
    boolean bool3 = false;
    while (paramParcel.dataPosition() < i)
    {
      int n = paramParcel.readInt();
      switch (0xFFFF & n)
      {
      default: 
        zza.zzb(paramParcel, n);
        break;
      case 1: 
        j = zza.zzg(paramParcel, n);
        break;
      case 2: 
        l = zza.zzi(paramParcel, n);
        break;
      case 3: 
        localBundle1 = zza.zzs(paramParcel, n);
        break;
      case 4: 
        k = zza.zzg(paramParcel, n);
        break;
      case 5: 
        localArrayList1 = zza.zzE(paramParcel, n);
        break;
      case 6: 
        bool1 = zza.zzc(paramParcel, n);
        break;
      case 7: 
        m = zza.zzg(paramParcel, n);
        break;
      case 8: 
        bool2 = zza.zzc(paramParcel, n);
        break;
      case 9: 
        str1 = zza.zzq(paramParcel, n);
        break;
      case 10: 
        localSearchAdRequestParcel = (SearchAdRequestParcel)zza.zza(paramParcel, n, SearchAdRequestParcel.CREATOR);
        break;
      case 11: 
        localLocation = (Location)zza.zza(paramParcel, n, Location.CREATOR);
        break;
      case 12: 
        str2 = zza.zzq(paramParcel, n);
        break;
      case 13: 
        localBundle2 = zza.zzs(paramParcel, n);
        break;
      case 14: 
        localBundle3 = zza.zzs(paramParcel, n);
        break;
      case 15: 
        localArrayList2 = zza.zzE(paramParcel, n);
        break;
      case 17: 
        str4 = zza.zzq(paramParcel, n);
        break;
      case 16: 
        str3 = zza.zzq(paramParcel, n);
        break;
      case 18: 
        bool3 = zza.zzc(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new AdRequestParcel(j, l, localBundle1, k, localArrayList1, bool1, m, bool2, str1, localSearchAdRequestParcel, localLocation, str2, localBundle2, localBundle3, localArrayList2, str3, str4, bool3);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.client.zzg
 * JD-Core Version:    0.7.0.1
 */