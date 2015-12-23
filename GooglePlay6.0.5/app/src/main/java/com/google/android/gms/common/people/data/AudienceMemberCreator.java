package com.google.android.gms.common.people.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class AudienceMemberCreator
  implements Parcelable.Creator<AudienceMember>
{
  static void zza$4b7b3a63(AudienceMember paramAudienceMember, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramAudienceMember.zzTv);
    zzb.zzc(paramParcel, 1000, paramAudienceMember.mVersionCode);
    zzb.zzc(paramParcel, 2, paramAudienceMember.zzauS);
    zzb.zza$2cfb68bf(paramParcel, 3, paramAudienceMember.zzauT);
    zzb.zza$2cfb68bf(paramParcel, 4, paramAudienceMember.zzauU);
    zzb.zza$2cfb68bf(paramParcel, 5, paramAudienceMember.zzUe);
    zzb.zza$2cfb68bf(paramParcel, 6, paramAudienceMember.zzauV);
    zzb.zza$f7bef55(paramParcel, 7, paramAudienceMember.zzarz);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.people.data.AudienceMemberCreator
 * JD-Core Version:    0.7.0.1
 */