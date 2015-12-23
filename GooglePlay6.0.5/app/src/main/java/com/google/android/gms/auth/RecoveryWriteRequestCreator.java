package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class RecoveryWriteRequestCreator
  implements Parcelable.Creator<RecoveryWriteRequest>
{
  public static RecoveryWriteRequest createFromParcel(Parcel paramParcel)
  {
    boolean bool = false;
    String str1 = null;
    int i = zza.zzbc(paramParcel);
    String str2 = null;
    String str3 = null;
    String str4 = null;
    int j = 0;
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
        str4 = zza.zzq(paramParcel, k);
        break;
      case 3: 
        str3 = zza.zzq(paramParcel, k);
        break;
      case 4: 
        str2 = zza.zzq(paramParcel, k);
        break;
      case 5: 
        str1 = zza.zzq(paramParcel, k);
        break;
      case 6: 
        bool = zza.zzc(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new RecoveryWriteRequest(j, str4, str3, str2, str1, bool);
  }
  
  static void zza$4479f008(RecoveryWriteRequest paramRecoveryWriteRequest, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramRecoveryWriteRequest.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramRecoveryWriteRequest.mAccount);
    zzb.zza$2cfb68bf(paramParcel, 3, paramRecoveryWriteRequest.mSecondaryEmail);
    zzb.zza$2cfb68bf(paramParcel, 4, paramRecoveryWriteRequest.mPhoneNumber);
    zzb.zza$2cfb68bf(paramParcel, 5, paramRecoveryWriteRequest.mPhoneCountryCode);
    zzb.zza(paramParcel, 6, paramRecoveryWriteRequest.mIsBroadUse);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.RecoveryWriteRequestCreator
 * JD-Core Version:    0.7.0.1
 */