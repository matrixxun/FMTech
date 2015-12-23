package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbq
  implements Parcelable.Creator<TrafficIncident>
{
  static void zza$43bc91f0(TrafficIncident paramTrafficIncident, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramTrafficIncident.eventCode);
    zzb.zzc(paramParcel, 1000, paramTrafficIncident.mVersionCode);
    zzb.zzc(paramParcel, 2, paramTrafficIncident.expectedIncidentDuration);
    zzb.zza(paramParcel, 3, paramTrafficIncident.latitude);
    zzb.zza(paramParcel, 4, paramTrafficIncident.longitude);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzbq
 * JD-Core Version:    0.7.0.1
 */