package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzh
  implements Parcelable.Creator<AncsNotificationParcelable>
{
  static void zza$4e9d8cf1(AncsNotificationParcelable paramAncsNotificationParcelable, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramAncsNotificationParcelable.mVersionCode);
    zzb.zzc(paramParcel, 2, paramAncsNotificationParcelable.mId);
    zzb.zza$2cfb68bf(paramParcel, 3, paramAncsNotificationParcelable.zzbkS);
    zzb.zza$2cfb68bf(paramParcel, 4, paramAncsNotificationParcelable.zzcfi);
    zzb.zza$2cfb68bf(paramParcel, 5, paramAncsNotificationParcelable.zzahX);
    zzb.zza$2cfb68bf(paramParcel, 6, paramAncsNotificationParcelable.zzaBy);
    zzb.zza$2cfb68bf(paramParcel, 7, paramAncsNotificationParcelable.zzaSe);
    if (paramAncsNotificationParcelable.zzUe == null) {}
    for (String str = paramAncsNotificationParcelable.zzbkS;; str = paramAncsNotificationParcelable.zzUe)
    {
      zzb.zza$2cfb68bf(paramParcel, 8, str);
      zzb.zza(paramParcel, 9, paramAncsNotificationParcelable.zzcfj);
      zzb.zza(paramParcel, 10, paramAncsNotificationParcelable.zzcfk);
      zzb.zza(paramParcel, 11, paramAncsNotificationParcelable.zzcfl);
      zzb.zza(paramParcel, 12, paramAncsNotificationParcelable.zzcfm);
      zzb.zzI(paramParcel, i);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzh
 * JD-Core Version:    0.7.0.1
 */