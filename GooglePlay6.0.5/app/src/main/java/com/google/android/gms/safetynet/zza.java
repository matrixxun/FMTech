package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zza
  implements Parcelable.Creator<AttestationData>
{
  static void zza$4f44a932(AttestationData paramAttestationData, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramAttestationData.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramAttestationData.zzbKs);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.safetynet.zza
 * JD-Core Version:    0.7.0.1
 */