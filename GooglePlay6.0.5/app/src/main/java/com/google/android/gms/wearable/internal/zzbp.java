package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbp
  implements Parcelable.Creator<NodeParcelable>
{
  static void zza$70e0d217(NodeParcelable paramNodeParcelable, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramNodeParcelable.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramNodeParcelable.zzyx);
    zzb.zza$2cfb68bf(paramParcel, 3, paramNodeParcelable.zzUe);
    zzb.zzc(paramParcel, 4, paramNodeParcelable.zzcho);
    zzb.zza(paramParcel, 5, paramNodeParcelable.zzchp);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbp
 * JD-Core Version:    0.7.0.1
 */