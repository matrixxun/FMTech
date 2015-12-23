package com.google.android.gms.measurement.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.Clock;

public final class zzu
  extends zzl.zza
{
  private final zzt zzbkM;
  private final boolean zzbop;
  
  public zzu(zzt paramzzt)
  {
    zzx.zzC(paramzzt);
    this.zzbkM = paramzzt;
    this.zzbop = false;
  }
  
  public zzu(zzt paramzzt, byte paramByte)
  {
    zzx.zzC(paramzzt);
    this.zzbkM = paramzzt;
    this.zzbop = true;
  }
  
  private void zzeD(String paramString)
    throws SecurityException
  {
    if (TextUtils.isEmpty(paramString))
    {
      this.zzbkM.zzBh().zzbmW.zzeB("Measurement Service called without app package");
      throw new SecurityException("Measurement Service called without app package");
    }
    for (;;)
    {
      try
      {
        if (this.zzbop)
        {
          j = Process.myUid();
          if ((GooglePlayServicesUtil.zza(this.zzbkM.mContext, j, paramString)) || (GooglePlayServicesUtil.zze(this.zzbkM.mContext, j))) {
            break;
          }
          throw new SecurityException(String.format("Unknown calling package name '%s'.", new Object[] { paramString }));
        }
      }
      catch (SecurityException localSecurityException)
      {
        this.zzbkM.zzBh().zzbmW.zzm("Measurement Service called with invalid calling package", paramString);
        throw localSecurityException;
      }
      int i = Binder.getCallingUid();
      int j = i;
    }
  }
  
  public final void zza(final AppMetadata paramAppMetadata)
  {
    zzx.zzC(paramAppMetadata);
    zzeD(paramAppMetadata.packageName);
    this.zzbkM.zzBY().zzg(new Runnable()
    {
      public final void run()
      {
        zzu.this.zzeC(paramAppMetadata.zzbmf);
        zzt localzzt = zzu.zza(zzu.this);
        AppMetadata localAppMetadata = paramAppMetadata;
        localzzt.zzBY().checkOnWorkerThread();
        localzzt.zziL();
        zzx.zzC(localAppMetadata);
        zzx.zzcG(localAppMetadata.packageName);
        if (!TextUtils.isEmpty(localAppMetadata.zzbmb))
        {
          localzzt.zze(localAppMetadata);
          if (localzzt.zzCG().zzW(localAppMetadata.packageName, "_f") == null)
          {
            long l = localzzt.zzri.currentTimeMillis();
            localzzt.zzb(new UserAttributeParcel("_fot", l, Long.valueOf(3600000L * (1L + l / 3600000L)), "auto"), localAppMetadata);
            Bundle localBundle = new Bundle();
            localBundle.putLong("_c", 1L);
            localzzt.zzb(new EventParcel("_f", new EventParams(localBundle), "auto", l), localAppMetadata);
          }
        }
      }
    });
  }
  
  public final void zza(final EventParcel paramEventParcel, final AppMetadata paramAppMetadata)
  {
    zzx.zzC(paramEventParcel);
    zzx.zzC(paramAppMetadata);
    zzeD(paramAppMetadata.packageName);
    this.zzbkM.zzBY().zzg(new Runnable()
    {
      public final void run()
      {
        zzu.this.zzeC(paramAppMetadata.zzbmf);
        zzu.zza(zzu.this).zzb(paramEventParcel, paramAppMetadata);
      }
    });
  }
  
  public final void zza(final EventParcel paramEventParcel, final String paramString1, final String paramString2)
  {
    zzx.zzC(paramEventParcel);
    zzx.zzcG(paramString1);
    zzeD(paramString1);
    this.zzbkM.zzBY().zzg(new Runnable()
    {
      public final void run()
      {
        zzu.this.zzeC(paramString2);
        zzt localzzt = zzu.zza(zzu.this);
        EventParcel localEventParcel = paramEventParcel;
        String str = paramString1;
        zza localzza = localzzt.zzCG().zzey(str);
        if ((localzza == null) || (TextUtils.isEmpty(localzza.mAppVersion)))
        {
          localzzt.zzBh().zzbnd.zzm("No app data available; dropping event", str);
          return;
        }
        localzzt.zzb(localEventParcel, new AppMetadata(str, localzza.zzblT, localzza.mAppVersion, localzza.zzblX, localzza.zzblY, localzza.zzblZ, null, localzza.zzbma));
      }
    });
  }
  
  public final void zza(final UserAttributeParcel paramUserAttributeParcel, final AppMetadata paramAppMetadata)
  {
    zzx.zzC(paramUserAttributeParcel);
    zzx.zzC(paramAppMetadata);
    zzeD(paramAppMetadata.packageName);
    if (paramUserAttributeParcel.getValue() == null)
    {
      this.zzbkM.zzBY().zzg(new Runnable()
      {
        public final void run()
        {
          zzu.this.zzeC(paramAppMetadata.zzbmf);
          zzt localzzt = zzu.zza(zzu.this);
          UserAttributeParcel localUserAttributeParcel = paramUserAttributeParcel;
          AppMetadata localAppMetadata = paramAppMetadata;
          localzzt.zzBY().checkOnWorkerThread();
          localzzt.zziL();
          if (!TextUtils.isEmpty(localAppMetadata.zzbmb))
          {
            localzzt.zzBh().zzbnd.zzm("Removing user attribute", localUserAttributeParcel.name);
            localzzt.zzCG().beginTransaction();
          }
          try
          {
            localzzt.zze(localAppMetadata);
            localzzt.zzCG().zzX(localAppMetadata.packageName, localUserAttributeParcel.name);
            localzzt.zzCG().setTransactionSuccessful();
            localzzt.zzBh().zzbnd.zzm("User attribute removed", localUserAttributeParcel.name);
            return;
          }
          finally
          {
            localzzt.zzCG().endTransaction();
          }
        }
      });
      return;
    }
    this.zzbkM.zzBY().zzg(new Runnable()
    {
      public final void run()
      {
        zzu.this.zzeC(paramAppMetadata.zzbmf);
        zzu.zza(zzu.this).zzb(paramUserAttributeParcel, paramAppMetadata);
      }
    });
  }
  
  public final void zzb(final AppMetadata paramAppMetadata)
  {
    zzx.zzC(paramAppMetadata);
    zzeD(paramAppMetadata.packageName);
    this.zzbkM.zzBY().zzg(new Runnable()
    {
      public final void run()
      {
        zzu.this.zzeC(paramAppMetadata.zzbmf);
        zzt localzzt = zzu.zza(zzu.this);
        AppMetadata localAppMetadata = paramAppMetadata;
        localzzt.zzBY().checkOnWorkerThread();
        localzzt.zziL();
        zzx.zzcG(localAppMetadata.packageName);
        localzzt.zze(localAppMetadata);
      }
    });
  }
  
  final void zzeC(String paramString)
  {
    String[] arrayOfString;
    if (!TextUtils.isEmpty(paramString))
    {
      arrayOfString = paramString.split(":", 2);
      if (arrayOfString.length != 2) {}
    }
    long l;
    try
    {
      l = Long.valueOf(arrayOfString[0]).longValue();
      if (l > 0L)
      {
        this.zzbkM.zzBZ().zzbns.zzg(arrayOfString[1], l);
        return;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.zzbkM.zzBh().zzbmZ.zzm("Combining sample with a non-number weight", arrayOfString[0]);
      return;
    }
    this.zzbkM.zzBh().zzbmZ.zzm("Combining sample with a non-positive weight", Long.valueOf(l));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzu
 * JD-Core Version:    0.7.0.1
 */