package com.google.android.gms.ocr;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class CreditCardOcrResult
  implements SafeParcelable
{
  public static final Parcelable.Creator<CreditCardOcrResult> CREATOR = new zza();
  final int mVersionCode;
  public String zzbrS;
  public int zzbrT;
  public int zzbrU;
  int zzbrV;
  String zzbrW;
  String zzbrX;
  boolean zzbrY;
  String zzbrZ;
  String zzbsa;
  String zzbsb;
  String zzbsc;
  
  CreditCardOcrResult(int paramInt1, String paramString1, int paramInt2, int paramInt3, int paramInt4, String paramString2, String paramString3, boolean paramBoolean, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.mVersionCode = paramInt1;
    this.zzbrS = paramString1;
    this.zzbrT = paramInt2;
    this.zzbrU = paramInt3;
    this.zzbrV = paramInt4;
    this.zzbrW = paramString2;
    this.zzbrX = paramString3;
    this.zzbrY = paramBoolean;
    this.zzbrZ = paramString4;
    this.zzbsa = paramString5;
    this.zzbsb = paramString6;
    this.zzbsc = paramString7;
  }
  
  public static CreditCardOcrResult fromIntent(Intent paramIntent)
  {
    if ((paramIntent == null) || (!paramIntent.hasExtra("com.google.android.gms.ocr.CREDIT_CARD_OCR_RESULT"))) {
      return null;
    }
    paramIntent.setExtrasClassLoader(CreditCardOcrResult.class.getClassLoader());
    return (CreditCardOcrResult)paramIntent.getParcelableExtra("com.google.android.gms.ocr.CREDIT_CARD_OCR_RESULT");
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza$55a4e943(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ocr.CreditCardOcrResult
 * JD-Core Version:    0.7.0.1
 */