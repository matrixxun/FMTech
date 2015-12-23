package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.dynamic.zzg.zza;

@zzhb
public final class zzgj
  extends zzg<zzgf>
{
  private static final zzgj zzFw = new zzgj();
  
  private zzgj()
  {
    super("com.google.android.gms.ads.InAppPurchaseManagerCreatorImpl");
  }
  
  public static zzge zze(Activity paramActivity)
  {
    Intent localIntent;
    try
    {
      localIntent = paramActivity.getIntent();
      if (!localIntent.hasExtra("com.google.android.gms.ads.internal.purchase.useClientJar")) {
        throw new zza("InAppPurchaseManager requires the useClientJar flag in intent extras.");
      }
    }
    catch (zza localzza)
    {
      zzb.w(localzza.getMessage());
      return null;
    }
    if (localIntent.getBooleanExtra("com.google.android.gms.ads.internal.purchase.useClientJar", false))
    {
      zzb.d("Using AdOverlay from the client jar.");
      return new com.google.android.gms.ads.internal.purchase.zze(paramActivity);
    }
    zzge localzzge = zzFw.zzf(paramActivity);
    return localzzge;
  }
  
  private zzge zzf(Activity paramActivity)
  {
    try
    {
      zzd localzzd = com.google.android.gms.dynamic.zze.zzI(paramActivity);
      zzge localzzge = zzge.zza.zzU(((zzgf)zzaC(paramActivity)).zzj(localzzd));
      return localzzge;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.w("Could not create remote InAppPurchaseManager.", localRemoteException);
      return null;
    }
    catch (zzg.zza localzza)
    {
      zzb.w("Could not create remote InAppPurchaseManager.", localzza);
    }
    return null;
  }
  
  private static final class zza
    extends Exception
  {
    public zza(String paramString)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzgj
 * JD-Core Version:    0.7.0.1
 */