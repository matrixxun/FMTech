package com.google.android.gms.googlehelp.internal.common;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzh
  implements Parcelable.Creator<OverflowMenuItem>
{
  static void zza(OverflowMenuItem paramOverflowMenuItem, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramOverflowMenuItem.mVersionCode);
    zzb.zzc(paramParcel, 2, paramOverflowMenuItem.mId);
    zzb.zza$2cfb68bf(paramParcel, 3, paramOverflowMenuItem.zzaBy);
    zzb.zza$377a007(paramParcel, 4, paramOverflowMenuItem.mIntent, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.internal.common.zzh
 * JD-Core Version:    0.7.0.1
 */