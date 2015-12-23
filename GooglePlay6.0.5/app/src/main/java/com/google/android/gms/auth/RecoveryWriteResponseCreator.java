package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class RecoveryWriteResponseCreator
  implements Parcelable.Creator<RecoveryWriteResponse>
{
  public static RecoveryWriteResponse createFromParcel(Parcel paramParcel)
  {
    int i = zza.zzbc(paramParcel);
    int j = 0;
    String str = null;
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
        str = zza.zzq(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new RecoveryWriteResponse(j, str);
  }
  
  static void zza$361acac(RecoveryWriteResponse paramRecoveryWriteResponse, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramRecoveryWriteResponse.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramRecoveryWriteResponse.mErrorCode);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.RecoveryWriteResponseCreator
 * JD-Core Version:    0.7.0.1
 */