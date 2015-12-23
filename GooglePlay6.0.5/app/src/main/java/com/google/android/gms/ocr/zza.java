package com.google.android.gms.ocr;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zza
  implements Parcelable.Creator<CreditCardOcrResult>
{
  static void zza$55a4e943(CreditCardOcrResult paramCreditCardOcrResult, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramCreditCardOcrResult.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramCreditCardOcrResult.zzbrS);
    zzb.zzc(paramParcel, 3, paramCreditCardOcrResult.zzbrT);
    zzb.zzc(paramParcel, 4, paramCreditCardOcrResult.zzbrU);
    zzb.zzc(paramParcel, 5, paramCreditCardOcrResult.zzbrV);
    zzb.zza$2cfb68bf(paramParcel, 6, paramCreditCardOcrResult.zzbrW);
    zzb.zza$2cfb68bf(paramParcel, 7, paramCreditCardOcrResult.zzbrX);
    zzb.zza(paramParcel, 8, paramCreditCardOcrResult.zzbrY);
    zzb.zza$2cfb68bf(paramParcel, 9, paramCreditCardOcrResult.zzbrZ);
    zzb.zza$2cfb68bf(paramParcel, 10, paramCreditCardOcrResult.zzbsa);
    zzb.zza$2cfb68bf(paramParcel, 11, paramCreditCardOcrResult.zzbsb);
    zzb.zza$2cfb68bf(paramParcel, 12, paramCreditCardOcrResult.zzbsc);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ocr.zza
 * JD-Core Version:    0.7.0.1
 */