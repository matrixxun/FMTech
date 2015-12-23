package com.google.android.gms.internal;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;

@zzhb
public final class zzbt
  implements Application.ActivityLifecycleCallbacks
{
  Activity mActivity;
  Context mContext;
  private final Object zzqp;
  
  private void setActivity(Activity paramActivity)
  {
    synchronized (this.zzqp)
    {
      if (!paramActivity.getClass().getName().startsWith("com.google.android.gms.ads")) {
        this.mActivity = paramActivity;
      }
      return;
    }
  }
  
  public final void onActivityCreated(Activity paramActivity, Bundle paramBundle) {}
  
  public final void onActivityDestroyed(Activity paramActivity)
  {
    synchronized (this.zzqp)
    {
      if (this.mActivity == null) {
        return;
      }
      if (this.mActivity.equals(paramActivity)) {
        this.mActivity = null;
      }
      return;
    }
  }
  
  public final void onActivityPaused(Activity paramActivity)
  {
    setActivity(paramActivity);
  }
  
  public final void onActivityResumed(Activity paramActivity)
  {
    setActivity(paramActivity);
  }
  
  public final void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
  
  public final void onActivityStarted(Activity paramActivity)
  {
    setActivity(paramActivity);
  }
  
  public final void onActivityStopped(Activity paramActivity) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzbt
 * JD-Core Version:    0.7.0.1
 */