package com.google.android.gms.ads.internal.reward.mediation.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzc
  implements Parcelable.Creator<RewardItemParcel>
{
  static void zza$41e7b039(RewardItemParcel paramRewardItemParcel, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramRewardItemParcel.versionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramRewardItemParcel.type);
    zzb.zzc(paramParcel, 3, paramRewardItemParcel.zzJW);
    zzb.zzI(paramParcel, i);
  }
  
  public static RewardItemParcel zzp(Parcel paramParcel)
  {
    int i = 0;
    int j = zza.zzbc(paramParcel);
    String str = null;
    int k = 0;
    while (paramParcel.dataPosition() < j)
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
        str = zza.zzq(paramParcel, m);
        break;
      case 3: 
        i = zza.zzg(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new RewardItemParcel(k, str, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.reward.mediation.client.zzc
 * JD-Core Version:    0.7.0.1
 */