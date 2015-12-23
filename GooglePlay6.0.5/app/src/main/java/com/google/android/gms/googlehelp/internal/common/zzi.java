package com.google.android.gms.googlehelp.internal.common;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.googlehelp.zzc;

public final class zzi
  implements Application.ActivityLifecycleCallbacks
{
  public final GoogleApiClient mApiClient;
  
  public zzi(Context paramContext)
  {
    this.mApiClient = new GoogleApiClient.Builder(paramContext).addApi(zzc.API).build();
  }
  
  public final void onActivityCreated(Activity paramActivity, Bundle paramBundle) {}
  
  public final void onActivityDestroyed(Activity paramActivity) {}
  
  public final void onActivityPaused(Activity paramActivity)
  {
    if ((paramActivity instanceof zza)) {
      return;
    }
    zzc.zza(this.mApiClient, new zzj()
    {
      public final PendingResult<Status> zzo$3946365()
      {
        return zzc.zzbbc.zzm(zzi.zza(zzi.this));
      }
    });
  }
  
  public final void onActivityResumed(final Activity paramActivity)
  {
    if ((paramActivity instanceof zza)) {
      return;
    }
    zzc.zza(this.mApiClient, new zzj()
    {
      public final PendingResult<Status> zzo$3946365()
      {
        return zzc.zzbbc.zzb(zzi.zza(zzi.this), paramActivity);
      }
    });
  }
  
  public final void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
  
  public final void onActivityStarted(Activity paramActivity) {}
  
  public final void onActivityStopped(Activity paramActivity) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.internal.common.zzi
 * JD-Core Version:    0.7.0.1
 */