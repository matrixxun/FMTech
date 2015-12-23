package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzc
  implements Parcelable.Creator<AddListenerRequest>
{
  static void zza(AddListenerRequest paramAddListenerRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramAddListenerRequest.mVersionCode);
    if (paramAddListenerRequest.zzceU == null) {}
    for (IBinder localIBinder = null;; localIBinder = paramAddListenerRequest.zzceU.asBinder())
    {
      zzb.zza$cdac282(paramParcel, 2, localIBinder);
      zzb.zza$2d7953c6(paramParcel, 3, paramAddListenerRequest.zzceV, paramInt);
      zzb.zza$2cfb68bf(paramParcel, 4, paramAddListenerRequest.zzceW);
      zzb.zza$2cfb68bf(paramParcel, 5, paramAddListenerRequest.zzceX);
      zzb.zzI(paramParcel, i);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzc
 * JD-Core Version:    0.7.0.1
 */