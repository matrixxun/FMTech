package com.google.android.gms.people.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class ParcelableAvatarReference
  implements Parcelable.Creator<AvatarReference>
{
  public static AvatarReference createFromParcel(Parcel paramParcel)
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
        i = zza.zzg(paramParcel, m);
        break;
      case 1000: 
        k = zza.zzg(paramParcel, m);
        break;
      case 2: 
        str = zza.zzq(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new AvatarReference(k, i, str);
  }
  
  static void zza$f2d72cc(AvatarReference paramAvatarReference, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramAvatarReference.zzvA);
    zzb.zzc(paramParcel, 1000, paramAvatarReference.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramAvatarReference.zzbwK);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.model.ParcelableAvatarReference
 * JD-Core Version:    0.7.0.1
 */