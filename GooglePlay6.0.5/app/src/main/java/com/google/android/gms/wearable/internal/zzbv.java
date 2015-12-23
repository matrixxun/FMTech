package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbv
  implements Parcelable.Creator<RemoveListenerRequest>
{
  static void zza$1160eef7(RemoveListenerRequest paramRemoveListenerRequest, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramRemoveListenerRequest.mVersionCode);
    if (paramRemoveListenerRequest.zzceU == null) {}
    for (IBinder localIBinder = null;; localIBinder = paramRemoveListenerRequest.zzceU.asBinder())
    {
      zzb.zza$cdac282(paramParcel, 2, localIBinder);
      zzb.zzI(paramParcel, i);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbv
 * JD-Core Version:    0.7.0.1
 */