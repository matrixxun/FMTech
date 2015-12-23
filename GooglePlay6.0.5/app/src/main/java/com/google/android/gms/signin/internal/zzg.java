package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzg
  implements Parcelable.Creator<RecordConsentRequest>
{
  static void zza(RecordConsentRequest paramRecordConsentRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramRecordConsentRequest.mVersionCode);
    zzb.zza$377a007(paramParcel, 2, paramRecordConsentRequest.zzRE, paramInt);
    zzb.zza$2d7953c6(paramParcel, 3, paramRecordConsentRequest.zzbMd, paramInt);
    zzb.zza$2cfb68bf(paramParcel, 4, paramRecordConsentRequest.zzXo);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.signin.internal.zzg
 * JD-Core Version:    0.7.0.1
 */