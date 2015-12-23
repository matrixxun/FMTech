package com.google.android.gms.googlehelp.internal.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.googlehelp.GoogleHelp;
import com.google.android.gms.googlehelp.GoogleHelpTogglingRegister;
import com.google.android.gms.googlehelp.zza;
import com.google.android.gms.internal.zzmu.zza;

public final class zzb
  implements zza
{
  private static final Status zzbbh = new Status(13);
  
  public final PendingResult<Status> zza(GoogleApiClient paramGoogleApiClient, final Activity paramActivity)
  {
    if (GoogleHelpTogglingRegister.zzbba) {}
    for (final Bitmap localBitmap = zzd.zzu(paramActivity);; localBitmap = null) {
      paramGoogleApiClient.zza(new zzb(paramGoogleApiClient)
      {
        protected final void zza$3f77981b(zzf paramAnonymouszzf)
          throws RemoteException
        {
          try
          {
            paramAnonymouszzf.zza(localBitmap, new SimpleGoogleHelpCallbacks()
            {
              public final void onTogglingPipProcessed(TogglingData paramAnonymous2TogglingData)
              {
                if (!TextUtils.isEmpty(paramAnonymous2TogglingData.zzbbu)) {}
                for (int i = 1;; i = 0)
                {
                  if (i != 0) {
                    paramAnonymous2TogglingData.zzbbv = zzd.zzw(zzb.2.this.zzbbj);
                  }
                  Intent localIntent = new Intent("com.google.android.gms.googlehelp.HELP").setPackage("com.google.android.gms").putExtra("EXTRA_TOGGLING_DATA", paramAnonymous2TogglingData).putExtra("EXTRA_START_TICK", System.nanoTime());
                  zzb.2.this.zzbbj.startActivityForResult(localIntent, 123);
                  zzb.2.this.zza(Status.zzaoz);
                  return;
                }
              }
            });
            return;
          }
          catch (Exception localException)
          {
            Log.e("gH_GoogleHelpApiImpl", "Toggling to help failed!", localException);
            zzE(zzb.zzzr());
          }
        }
      });
    }
  }
  
  public final PendingResult<Status> zza(GoogleApiClient paramGoogleApiClient, final Activity paramActivity, final Intent paramIntent)
  {
    if (GoogleHelpTogglingRegister.zzbba) {}
    for (final Bitmap localBitmap = zzd.zzu(paramActivity);; localBitmap = null) {
      paramGoogleApiClient.zza(new zzb(paramGoogleApiClient)
      {
        protected final void zza$3f77981b(zzf paramAnonymouszzf)
          throws RemoteException
        {
          try
          {
            paramAnonymouszzf.zza((GoogleHelp)paramIntent.getParcelableExtra("EXTRA_GOOGLE_HELP"), localBitmap, new SimpleGoogleHelpCallbacks()
            {
              public final void onGoogleHelpProcessed(GoogleHelp paramAnonymous2GoogleHelp)
              {
                if (paramAnonymous2GoogleHelp.zzbaU != null) {
                  paramAnonymous2GoogleHelp.zzbaU.zzbbv = zzd.zzw(zzb.1.this.zzbbj);
                }
                zzb.1.this.zzbaX.putExtra("EXTRA_GOOGLE_HELP", paramAnonymous2GoogleHelp).putExtra("EXTRA_START_TICK", System.nanoTime());
                zzb.1.this.zzbbj.startActivityForResult(zzb.1.this.zzbaX, 123);
                zzb.1.this.zza(Status.zzaoz);
              }
            });
            return;
          }
          catch (Exception localException)
          {
            Log.e("gH_GoogleHelpApiImpl", "Starting help failed!", localException);
            zzE(zzb.zzzr());
          }
        }
      });
    }
  }
  
  public final PendingResult<Status> zzb(GoogleApiClient paramGoogleApiClient, final Activity paramActivity)
  {
    paramGoogleApiClient.zza(new zzb(paramGoogleApiClient)
    {
      protected final void zza$3f77981b(zzf paramAnonymouszzf)
        throws RemoteException
      {
        try
        {
          paramAnonymouszzf.zza(new zzg(paramActivity)
          {
            public final void onPipShown()
            {
              zzb.3.this.zza(Status.zzaoz);
            }
          });
          return;
        }
        catch (Exception localException)
        {
          Log.e("gH_GoogleHelpApiImpl", "Requesting to show Pip failed!", localException);
          zzE(zzb.zzzr());
        }
      }
    });
  }
  
  public final PendingResult<Status> zzm(GoogleApiClient paramGoogleApiClient)
  {
    paramGoogleApiClient.zza(new zzb(paramGoogleApiClient)
    {
      protected final void zza$3f77981b(zzf paramAnonymouszzf)
        throws RemoteException
      {
        try
        {
          paramAnonymouszzf.zzb(new SimpleGoogleHelpCallbacks()
          {
            public final void onPipInCallingAppHidden()
            {
              zzb.4.this.zza(Status.zzaoz);
            }
          });
          return;
        }
        catch (Exception localException)
        {
          Log.e("gH_GoogleHelpApiImpl", "Requesting to hide Pip failed!", localException);
          zzE(zzb.zzzr());
        }
      }
    });
  }
  
  public final PendingResult<Status> zzn(GoogleApiClient paramGoogleApiClient)
  {
    paramGoogleApiClient.zza(new zzb(paramGoogleApiClient)
    {
      protected final void zza$3f77981b(zzf paramAnonymouszzf)
        throws RemoteException
      {
        try
        {
          paramAnonymouszzf.zzc(new SimpleGoogleHelpCallbacks()
          {
            public final void onPipInCallingAppDisabled()
            {
              zzb.5.this.zza(Status.zzaoz);
            }
          });
          return;
        }
        catch (Exception localException)
        {
          Log.e("gH_GoogleHelpApiImpl", "Requesting to disable Pip failed!", localException);
          zzE(zzb.zzzr());
        }
      }
    });
  }
  
  static abstract class zza<R extends Result>
    extends zzmu.zza<R, zzc>
  {
    public zza(GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }
    
    protected abstract void zza$3f77981b(zzf paramzzf)
      throws RemoteException;
  }
  
  static abstract class zzb
    extends zzb.zza<Status>
  {
    public zzb(GoogleApiClient paramGoogleApiClient)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.internal.common.zzb
 * JD-Core Version:    0.7.0.1
 */