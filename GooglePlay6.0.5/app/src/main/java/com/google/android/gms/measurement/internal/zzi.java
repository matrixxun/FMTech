package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzi
  implements Parcelable.Creator<EventParams>
{
  static void zza$789d8329(EventParams paramEventParams, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramEventParams.versionCode);
    zzb.zza$f7bef55(paramParcel, 2, paramEventParams.zzCk());
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzi
 * JD-Core Version:    0.7.0.1
 */