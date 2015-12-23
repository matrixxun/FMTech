package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.dynamic.zzg.zza;

@zzhb
public final class zzjy
  extends zzg<zzka>
{
  private static final zzjy zzNo = new zzjy();
  
  private zzjy()
  {
    super("com.google.android.gms.ads.measurement.GmpMeasurementReporterCreatorImpl");
  }
  
  public static zzjz zzaa(Context paramContext)
    throws GooglePlayServicesNotAvailableException
  {
    GoogleApiAvailability.getInstance();
    int i = GoogleApiAvailability.isGooglePlayServicesAvailable(paramContext);
    if (i == 2) {
      try
      {
        PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo("com.google.android.gms", 64);
        if (com.google.android.gms.common.util.zzd.zzdA(localPackageInfo.versionCode) < 8200000) {
          throw new GooglePlayServicesNotAvailableException(2);
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        throw new GooglePlayServicesNotAvailableException(1);
      }
    }
    zzjz localzzjz;
    if ((i == 0) || (i == 2))
    {
      localzzjz = zzNo.zzab(paramContext);
      if (localzzjz == null) {
        throw new GooglePlayServicesNotAvailableException(8);
      }
    }
    else
    {
      throw new GooglePlayServicesNotAvailableException(1);
    }
    return localzzjz;
  }
  
  private zzjz zzab(Context paramContext)
  {
    try
    {
      com.google.android.gms.dynamic.zzd localzzd = zze.zzI(paramContext);
      zzjz localzzjz = zzjz.zza.zzak(((zzka)zzaC(paramContext)).zzr(localzzd));
      return localzzjz;
    }
    catch (zzg.zza localzza)
    {
      zzb.d("Could not create remote GmpMeasurementReporter.", localzza);
      return null;
    }
    catch (RemoteException localRemoteException)
    {
      label28:
      break label28;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzjy
 * JD-Core Version:    0.7.0.1
 */