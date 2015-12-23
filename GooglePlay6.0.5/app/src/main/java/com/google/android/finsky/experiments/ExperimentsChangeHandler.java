package com.google.android.finsky.experiments;

import android.content.Context;
import android.support.v4.util.LongSparseArray;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.services.DailyHygiene;
import com.google.android.finsky.utils.ApplicationDismissedDeferrer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.SelfUpdateScheduler;
import com.google.android.play.utils.config.GservicesValue;

public final class ExperimentsChangeHandler
  implements FinskyExperiments.TargetsChangeListener
{
  private final Context mApplicationContext;
  private ApplicationDismissedDeferrer mApplicationDismissedDeferrer;
  public boolean mHadStaleExperimentsInLastAppRun;
  boolean mRunOnApplicationCloseHandlerSet;
  
  public ExperimentsChangeHandler(Context paramContext)
  {
    this.mApplicationContext = paramContext;
    if (((Boolean)FinskyPreferences.hasStaleProcessStableTargets.get()).booleanValue()) {
      this.mHadStaleExperimentsInLastAppRun = true;
    }
  }
  
  final ApplicationDismissedDeferrer getApplicationDismissedDeferrer()
  {
    if (this.mApplicationDismissedDeferrer == null) {
      this.mApplicationDismissedDeferrer = new ApplicationDismissedDeferrer(this.mApplicationContext);
    }
    return this.mApplicationDismissedDeferrer;
  }
  
  public final void onProcessStableTargetsChanged()
  {
    FinskyPreferences.hasStaleProcessStableTargets.put(Boolean.valueOf(true));
    triggerRunOnApplicationClose();
  }
  
  public final void onTargetsChanged(LongSparseArray<Object> paramLongSparseArray1, LongSparseArray<Object> paramLongSparseArray2) {}
  
  final void triggerRunOnApplicationClose()
  {
    if (this.mRunOnApplicationCloseHandlerSet) {
      return;
    }
    FinskyLog.d("Application close deferrer set because of stale process stable experiments", new Object[0]);
    getApplicationDismissedDeferrer().runOnApplicationClose(new Runnable()
    {
      public final void run()
      {
        ExperimentsChangeHandler.this.mRunOnApplicationCloseHandlerSet = false;
        ExperimentsChangeHandler.access$100(ExperimentsChangeHandler.this, ((Integer)G.appInBgDurationForUpdatingExperimentsMs.get()).intValue(), ((Integer)G.appInBgCheckPollIntervalMs.get()).intValue());
      }
    }, ((Integer)G.appInBgCheckPollIntervalMs.get()).intValue());
    this.mRunOnApplicationCloseHandlerSet = true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.experiments.ExperimentsChangeHandler
 * JD-Core Version:    0.7.0.1
 */