package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class RecoveryReadResponseCreator
  implements Parcelable.Creator<RecoveryReadResponse>
{
  public static RecoveryReadResponse createFromParcel(Parcel paramParcel)
  {
    String str1 = null;
    int i = zza.zzbc(paramParcel);
    int j = 0;
    String str2 = null;
    String str3 = null;
    ArrayList localArrayList = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    while (paramParcel.dataPosition() < i)
    {
      int k = paramParcel.readInt();
      switch (0xFFFF & k)
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        j = zza.zzg(paramParcel, k);
        break;
      case 2: 
        str6 = zza.zzq(paramParcel, k);
        break;
      case 3: 
        str5 = zza.zzq(paramParcel, k);
        break;
      case 4: 
        str4 = zza.zzq(paramParcel, k);
        break;
      case 5: 
        localArrayList = zza.zzc(paramParcel, k, Country.CREATOR);
        break;
      case 6: 
        str3 = zza.zzq(paramParcel, k);
        break;
      case 7: 
        str2 = zza.zzq(paramParcel, k);
        break;
      case 8: 
        str1 = zza.zzq(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new RecoveryReadResponse(j, str6, str5, str4, localArrayList, str3, str2, str1);
  }
  
  static void zza$d57022f(RecoveryReadResponse paramRecoveryReadResponse, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramRecoveryReadResponse.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramRecoveryReadResponse.mSecondaryEmail);
    zzb.zza$2cfb68bf(paramParcel, 3, paramRecoveryReadResponse.mPhoneNumber);
    zzb.zza$2cfb68bf(paramParcel, 4, paramRecoveryReadResponse.mPhoneCountryCode);
    zzb.zzc$62107c48(paramParcel, 5, paramRecoveryReadResponse.mCountryList);
    zzb.zza$2cfb68bf(paramParcel, 6, paramRecoveryReadResponse.mError);
    zzb.zza$2cfb68bf(paramParcel, 7, paramRecoveryReadResponse.mAction);
    zzb.zza$2cfb68bf(paramParcel, 8, paramRecoveryReadResponse.mAllowedOptions);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.RecoveryReadResponseCreator
 * JD-Core Version:    0.7.0.1
 */