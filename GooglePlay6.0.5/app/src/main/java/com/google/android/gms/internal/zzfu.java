package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.dynamic.zzg.zza;

@zzhb
public final class zzfu
  extends zzg<zzfw>
{
  private static final zzfu zzEL = new zzfu();
  
  private zzfu()
  {
    super("com.google.android.gms.ads.AdOverlayCreatorImpl");
  }
  
  public static zzfv zzb(Activity paramActivity)
  {
    Intent localIntent;
    try
    {
      localIntent = paramActivity.getIntent();
      if (!localIntent.hasExtra("com.google.android.gms.ads.internal.overlay.useClientJar")) {
        throw new zza("Ad overlay requires the useClientJar flag in intent extras.");
      }
    }
    catch (zza localzza)
    {
      zzb.w(localzza.getMessage());
      return null;
    }
    if (localIntent.getBooleanExtra("com.google.android.gms.ads.internal.overlay.useClientJar", false))
    {
      zzb.d("Using AdOverlay from the client jar.");
      return new com.google.android.gms.ads.internal.overlay.zzd(paramActivity);
    }
    zzfv localzzfv = zzEL.zzd(paramActivity);
    return localzzfv;
  }
  
  private zzfv zzd(Activity paramActivity)
  {
    try
    {
      com.google.android.gms.dynamic.zzd localzzd = zze.zzI(paramActivity);
      zzfv localzzfv = zzfv.zza.zzP(((zzfw)zzaC(paramActivity)).zzi(localzzd));
      return localzzfv;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.w("Could not create remote AdOverlay.", localRemoteException);
      return null;
    }
    catch (zzg.zza localzza)
    {
      zzb.w("Could not create remote AdOverlay.", localzza);
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
 * Qualified Name:     com.google.android.gms.internal.zzfu
 * JD-Core Version:    0.7.0.1
 */