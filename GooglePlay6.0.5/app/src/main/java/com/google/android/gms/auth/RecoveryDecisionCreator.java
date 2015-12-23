package com.google.android.gms.auth;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class RecoveryDecisionCreator
  implements Parcelable.Creator<RecoveryDecision>
{
  public static RecoveryDecision createFromParcel(Parcel paramParcel)
  {
    PendingIntent localPendingIntent1 = null;
    boolean bool1 = false;
    int i = zza.zzbc(paramParcel);
    boolean bool2 = false;
    boolean bool3 = false;
    PendingIntent localPendingIntent2 = null;
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
        localPendingIntent2 = (PendingIntent)zza.zza(paramParcel, k, PendingIntent.CREATOR);
        break;
      case 3: 
        bool3 = zza.zzc(paramParcel, k);
        break;
      case 4: 
        bool2 = zza.zzc(paramParcel, k);
        break;
      case 5: 
        bool1 = zza.zzc(paramParcel, k);
        break;
      case 6: 
        localPendingIntent1 = (PendingIntent)zza.zza(paramParcel, k, PendingIntent.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new RecoveryDecision(j, localPendingIntent2, bool3, bool2, bool1, localPendingIntent1);
  }
  
  static void zza(RecoveryDecision paramRecoveryDecision, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramRecoveryDecision.mVersionCode);
    zzb.zza$377a007(paramParcel, 2, paramRecoveryDecision.recoveryIntent, paramInt);
    zzb.zza(paramParcel, 3, paramRecoveryDecision.showRecoveryInterstitial);
    zzb.zza(paramParcel, 4, paramRecoveryDecision.isRecoveryInfoNeeded);
    zzb.zza(paramParcel, 5, paramRecoveryDecision.isRecoveryInterstitialAllowed);
    zzb.zza$377a007(paramParcel, 6, paramRecoveryDecision.recoveryIntentWithoutIntro, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.RecoveryDecisionCreator
 * JD-Core Version:    0.7.0.1
 */