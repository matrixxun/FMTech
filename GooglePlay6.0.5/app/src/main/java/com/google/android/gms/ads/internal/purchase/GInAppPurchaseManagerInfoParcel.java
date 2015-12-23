package com.google.android.gms.ads.internal.purchase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.zzd.zza;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzgc;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class GInAppPurchaseManagerInfoParcel
  implements SafeParcelable
{
  public static final zza CREATOR = new zza();
  public final int versionCode;
  public final zzgc zzES;
  public final Context zzET;
  public final zzj zzEU;
  public final zzk zzrR;
  
  GInAppPurchaseManagerInfoParcel(int paramInt, IBinder paramIBinder1, IBinder paramIBinder2, IBinder paramIBinder3, IBinder paramIBinder4)
  {
    this.versionCode = paramInt;
    this.zzrR = ((zzk)zze.zzu(zzd.zza.zzdb(paramIBinder1)));
    this.zzES = ((zzgc)zze.zzu(zzd.zza.zzdb(paramIBinder2)));
    this.zzET = ((Context)zze.zzu(zzd.zza.zzdb(paramIBinder3)));
    this.zzEU = ((zzj)zze.zzu(zzd.zza.zzdb(paramIBinder4)));
  }
  
  public static GInAppPurchaseManagerInfoParcel zzc(Intent paramIntent)
  {
    try
    {
      Bundle localBundle = paramIntent.getBundleExtra("com.google.android.gms.ads.internal.purchase.InAppPurchaseManagerInfo");
      localBundle.setClassLoader(GInAppPurchaseManagerInfoParcel.class.getClassLoader());
      GInAppPurchaseManagerInfoParcel localGInAppPurchaseManagerInfoParcel = (GInAppPurchaseManagerInfoParcel)localBundle.getParcelable("com.google.android.gms.ads.internal.purchase.InAppPurchaseManagerInfo");
      return localGInAppPurchaseManagerInfoParcel;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza$629388ef(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.purchase.GInAppPurchaseManagerInfoParcel
 * JD-Core Version:    0.7.0.1
 */