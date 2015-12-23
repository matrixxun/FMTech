package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzi
  implements Parcelable.Creator<GetServiceRequest>
{
  static void zza(GetServiceRequest paramGetServiceRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramGetServiceRequest.version);
    zzb.zzc(paramParcel, 2, paramGetServiceRequest.zzatF);
    zzb.zzc(paramParcel, 3, paramGetServiceRequest.zzatG);
    zzb.zza$2cfb68bf(paramParcel, 4, paramGetServiceRequest.zzatH);
    zzb.zza$cdac282(paramParcel, 5, paramGetServiceRequest.zzatI);
    zzb.zza$2d7953c6(paramParcel, 6, paramGetServiceRequest.zzatJ, paramInt);
    zzb.zza$f7bef55(paramParcel, 7, paramGetServiceRequest.zzatK);
    zzb.zza$377a007(paramParcel, 8, paramGetServiceRequest.zzatL, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzi
 * JD-Core Version:    0.7.0.1
 */