package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zze
  implements Parcelable.Creator<CarCall.Details>
{
  static void zza(CarCall.Details paramDetails, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza$377a007(paramParcel, 1, paramDetails.handle, paramInt);
    zzb.zzc(paramParcel, 1000, paramDetails.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramDetails.callerDisplayName);
    zzb.zza$2cfb68bf(paramParcel, 3, paramDetails.disconnectCause);
    zzb.zza(paramParcel, 4, paramDetails.connectTimeMillis);
    zzb.zza$377a007(paramParcel, 5, paramDetails.gatewayInfoOriginalAddress, paramInt);
    zzb.zza$377a007(paramParcel, 6, paramDetails.gatewayInfoGatewayAddress, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zze
 * JD-Core Version:    0.7.0.1
 */