package com.google.android.gms.ads.internal.overlay;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.ads.internal.InterstitialAdParameterParcel;
import com.google.android.gms.ads.internal.client.zza;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.zzd.zza;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzdi;
import com.google.android.gms.internal.zzdo;
import com.google.android.gms.internal.zzhb;
import com.google.android.gms.internal.zzjo;

@zzhb
public final class AdOverlayInfoParcel
  implements SafeParcelable
{
  public static final zzf CREATOR = new zzf();
  public final int orientation;
  public final String url;
  public final int versionCode;
  public final AdLauncherIntentInfoParcel zzDX;
  public final zza zzDY;
  public final zzg zzDZ;
  public final zzjo zzEa;
  public final zzdi zzEb;
  public final String zzEc;
  public final boolean zzEd;
  public final String zzEe;
  public final zzn zzEf;
  public final int zzEg;
  public final zzdo zzEh;
  public final String zzEi;
  public final InterstitialAdParameterParcel zzEj;
  public final VersionInfoParcel zzrw;
  
  AdOverlayInfoParcel(int paramInt1, AdLauncherIntentInfoParcel paramAdLauncherIntentInfoParcel, IBinder paramIBinder1, IBinder paramIBinder2, IBinder paramIBinder3, IBinder paramIBinder4, String paramString1, boolean paramBoolean, String paramString2, IBinder paramIBinder5, int paramInt2, int paramInt3, String paramString3, VersionInfoParcel paramVersionInfoParcel, IBinder paramIBinder6, String paramString4, InterstitialAdParameterParcel paramInterstitialAdParameterParcel)
  {
    this.versionCode = paramInt1;
    this.zzDX = paramAdLauncherIntentInfoParcel;
    this.zzDY = ((zza)zze.zzu(zzd.zza.zzdb(paramIBinder1)));
    this.zzDZ = ((zzg)zze.zzu(zzd.zza.zzdb(paramIBinder2)));
    this.zzEa = ((zzjo)zze.zzu(zzd.zza.zzdb(paramIBinder3)));
    this.zzEb = ((zzdi)zze.zzu(zzd.zza.zzdb(paramIBinder4)));
    this.zzEc = paramString1;
    this.zzEd = paramBoolean;
    this.zzEe = paramString2;
    this.zzEf = ((zzn)zze.zzu(zzd.zza.zzdb(paramIBinder5)));
    this.orientation = paramInt2;
    this.zzEg = paramInt3;
    this.url = paramString3;
    this.zzrw = paramVersionInfoParcel;
    this.zzEh = ((zzdo)zze.zzu(zzd.zza.zzdb(paramIBinder6)));
    this.zzEi = paramString4;
    this.zzEj = paramInterstitialAdParameterParcel;
  }
  
  public AdOverlayInfoParcel(zza paramzza, zzg paramzzg, zzn paramzzn, zzjo paramzzjo, boolean paramBoolean, int paramInt, VersionInfoParcel paramVersionInfoParcel)
  {
    this.versionCode = 4;
    this.zzDX = null;
    this.zzDY = paramzza;
    this.zzDZ = paramzzg;
    this.zzEa = paramzzjo;
    this.zzEb = null;
    this.zzEc = null;
    this.zzEd = paramBoolean;
    this.zzEe = null;
    this.zzEf = paramzzn;
    this.orientation = paramInt;
    this.zzEg = 2;
    this.url = null;
    this.zzrw = paramVersionInfoParcel;
    this.zzEh = null;
    this.zzEi = null;
    this.zzEj = null;
  }
  
  public AdOverlayInfoParcel(zza paramzza, zzg paramzzg, zzdi paramzzdi, zzn paramzzn, zzjo paramzzjo, boolean paramBoolean, int paramInt, String paramString, VersionInfoParcel paramVersionInfoParcel, zzdo paramzzdo)
  {
    this.versionCode = 4;
    this.zzDX = null;
    this.zzDY = paramzza;
    this.zzDZ = paramzzg;
    this.zzEa = paramzzjo;
    this.zzEb = paramzzdi;
    this.zzEc = null;
    this.zzEd = paramBoolean;
    this.zzEe = null;
    this.zzEf = paramzzn;
    this.orientation = paramInt;
    this.zzEg = 3;
    this.url = paramString;
    this.zzrw = paramVersionInfoParcel;
    this.zzEh = paramzzdo;
    this.zzEi = null;
    this.zzEj = null;
  }
  
  public AdOverlayInfoParcel(zza paramzza, zzg paramzzg, zzdi paramzzdi, zzn paramzzn, zzjo paramzzjo, boolean paramBoolean, int paramInt, String paramString1, String paramString2, VersionInfoParcel paramVersionInfoParcel, zzdo paramzzdo)
  {
    this.versionCode = 4;
    this.zzDX = null;
    this.zzDY = paramzza;
    this.zzDZ = paramzzg;
    this.zzEa = paramzzjo;
    this.zzEb = paramzzdi;
    this.zzEc = paramString2;
    this.zzEd = paramBoolean;
    this.zzEe = paramString1;
    this.zzEf = paramzzn;
    this.orientation = paramInt;
    this.zzEg = 3;
    this.url = null;
    this.zzrw = paramVersionInfoParcel;
    this.zzEh = paramzzdo;
    this.zzEi = null;
    this.zzEj = null;
  }
  
  public AdOverlayInfoParcel(AdLauncherIntentInfoParcel paramAdLauncherIntentInfoParcel, zza paramzza, zzg paramzzg, zzn paramzzn, VersionInfoParcel paramVersionInfoParcel)
  {
    this.versionCode = 4;
    this.zzDX = paramAdLauncherIntentInfoParcel;
    this.zzDY = paramzza;
    this.zzDZ = paramzzg;
    this.zzEa = null;
    this.zzEb = null;
    this.zzEc = null;
    this.zzEd = false;
    this.zzEe = null;
    this.zzEf = paramzzn;
    this.orientation = -1;
    this.zzEg = 4;
    this.url = null;
    this.zzrw = paramVersionInfoParcel;
    this.zzEh = null;
    this.zzEi = null;
    this.zzEj = null;
  }
  
  public static void zza(Intent paramIntent, AdOverlayInfoParcel paramAdOverlayInfoParcel)
  {
    Bundle localBundle = new Bundle(1);
    localBundle.putParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", paramAdOverlayInfoParcel);
    paramIntent.putExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", localBundle);
  }
  
  public static AdOverlayInfoParcel zzb(Intent paramIntent)
  {
    try
    {
      Bundle localBundle = paramIntent.getBundleExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
      localBundle.setClassLoader(AdOverlayInfoParcel.class.getClassLoader());
      AdOverlayInfoParcel localAdOverlayInfoParcel = (AdOverlayInfoParcel)localBundle.getParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
      return localAdOverlayInfoParcel;
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
    zzf.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel
 * JD-Core Version:    0.7.0.1
 */