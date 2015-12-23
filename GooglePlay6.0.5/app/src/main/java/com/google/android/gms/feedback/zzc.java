package com.google.android.gms.feedback;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzc
  implements Parcelable.Creator<FileTeleporter>
{
  static void zza(FileTeleporter paramFileTeleporter, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramFileTeleporter.mVersionCode);
    zzb.zza$377a007(paramParcel, 2, paramFileTeleporter.zzHC, paramInt);
    zzb.zza$2cfb68bf(paramParcel, 3, paramFileTeleporter.mMimeType);
    zzb.zza$2cfb68bf(paramParcel, 4, paramFileTeleporter.zzaLl);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.feedback.zzc
 * JD-Core Version:    0.7.0.1
 */