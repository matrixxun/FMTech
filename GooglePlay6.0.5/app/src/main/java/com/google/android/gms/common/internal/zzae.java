package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzae
  implements Parcelable.Creator<ValidateAccountRequest>
{
  static void zza(ValidateAccountRequest paramValidateAccountRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramValidateAccountRequest.mVersionCode);
    zzb.zzc(paramParcel, 2, paramValidateAccountRequest.zzauK);
    zzb.zza$cdac282(paramParcel, 3, paramValidateAccountRequest.zzasY);
    zzb.zza$2d7953c6(paramParcel, 4, paramValidateAccountRequest.zzanP, paramInt);
    zzb.zza$f7bef55(paramParcel, 5, paramValidateAccountRequest.zzauL);
    zzb.zza$2cfb68bf(paramParcel, 6, paramValidateAccountRequest.zzWd);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzae
 * JD-Core Version:    0.7.0.1
 */