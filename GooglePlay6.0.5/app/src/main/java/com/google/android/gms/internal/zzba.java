package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.dynamic.zzg.zza;

public final class zzba
  extends zzg<zzbc>
{
  private static final zzba zzoY = new zzba();
  
  private zzba()
  {
    super("com.google.android.gms.ads.adshield.AdShieldCreatorImpl");
  }
  
  public static zzbb zzb(String paramString, Context paramContext, boolean paramBoolean)
  {
    GoogleApiAvailability.getInstance();
    Object localObject;
    if (GoogleApiAvailability.isGooglePlayServicesAvailable(paramContext) == 0)
    {
      localObject = zzoY.zzc(paramString, paramContext, false);
      if (localObject != null) {}
    }
    else
    {
      localObject = new zzaz(paramString, paramContext, false);
    }
    return localObject;
  }
  
  private zzbb zzc(String paramString, Context paramContext, boolean paramBoolean)
  {
    zzd localzzd = zze.zzI(paramContext);
    if (paramBoolean) {}
    for (;;)
    {
      try
      {
        localObject = ((zzbc)zzaC(paramContext)).zza(paramString, localzzd);
        return zzbb.zza.zze((IBinder)localObject);
      }
      catch (zzg.zza localzza)
      {
        Object localObject;
        IBinder localIBinder;
        return null;
      }
      catch (RemoteException localRemoteException)
      {
        continue;
      }
      localIBinder = ((zzbc)zzaC(paramContext)).zzb(paramString, localzzd);
      localObject = localIBinder;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzba
 * JD-Core Version:    0.7.0.1
 */