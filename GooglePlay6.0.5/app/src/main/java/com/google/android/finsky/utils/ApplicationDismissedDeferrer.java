package com.google.android.finsky.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import java.util.List;

public final class ApplicationDismissedDeferrer
{
  private final Context mContext;
  
  public ApplicationDismissedDeferrer(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public final boolean isContextPackageInBackground()
  {
    List localList = ((ActivityManager)this.mContext.getSystemService("activity")).getRecentTasks(1, 0);
    if (localList.size() == 0) {
      return true;
    }
    ActivityManager.RecentTaskInfo localRecentTaskInfo = (ActivityManager.RecentTaskInfo)localList.get(0);
    return (localRecentTaskInfo.baseIntent != null) && (localRecentTaskInfo.baseIntent.getComponent() != null) && (!this.mContext.getPackageName().equals(localRecentTaskInfo.baseIntent.getComponent().getPackageName()));
  }
  
  public final void runOnApplicationClose(final Runnable paramRunnable, final int paramInt)
  {
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
    {
      public final void run()
      {
        if (ApplicationDismissedDeferrer.this.isContextPackageInBackground())
        {
          paramRunnable.run();
          return;
        }
        ApplicationDismissedDeferrer.this.runOnApplicationClose(paramRunnable, paramInt);
      }
    }, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.ApplicationDismissedDeferrer
 * JD-Core Version:    0.7.0.1
 */