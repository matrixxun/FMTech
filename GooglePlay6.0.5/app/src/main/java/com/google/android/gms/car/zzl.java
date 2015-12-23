package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzl
  implements Parcelable.Creator<CarMediaBrowserRootNode>
{
  static void zza(CarMediaBrowserRootNode paramCarMediaBrowserRootNode, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zza$2cfb68bf(paramParcel, 1, paramCarMediaBrowserRootNode.path);
    zzb.zzc(paramParcel, 1000, paramCarMediaBrowserRootNode.mVersionCode);
    zzb.zza$2d7953c6(paramParcel, 2, paramCarMediaBrowserRootNode.mediaSources, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzl
 * JD-Core Version:    0.7.0.1
 */