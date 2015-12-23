package com.google.android.finsky.autoupdate;

import android.annotation.TargetApi;
import android.app.job.JobInfo.Builder;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.gearhead.GearheadStateMonitor;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.play.utils.config.GservicesValue;

@TargetApi(21)
public final class ReschedulerUsingJobScheduler
  extends ReschedulerStrategy
{
  private final Context mContext;
  
  public ReschedulerUsingJobScheduler(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public final boolean canUpdateNow()
  {
    return false;
  }
  
  public final void schedule()
  {
    ((JobScheduler)FinskyApp.get().getSystemService("jobscheduler")).cancel(821848294);
    JobInfo.Builder localBuilder = new JobInfo.Builder(821848294, new ComponentName(this.mContext, CheckPreconditionsAndAutoUpdateJobService.class));
    if (GearheadStateMonitor.getInstance().isProjecting()) {}
    for (int i = 2; (i & 0x2) != 0; i = 1)
    {
      long l3 = ((Long)G.autoUpdateGearheadCheckIntervalMs.get()).longValue();
      long l4 = ((Long)G.autoUpdateJobSchedulerGearheadTimeoutMs.get()).longValue();
      localBuilder.setMinimumLatency(l3).setBackoffCriteria(l3, 1).setOverrideDeadline(l4);
      logAutoUpdateRescheduled(true);
      PersistableBundle localPersistableBundle = new PersistableBundle();
      localPersistableBundle.putInt("Finksy.AutoUpdateRescheduleReason", i);
      localBuilder.setExtras(localPersistableBundle);
      FinskyLog.d("Scheduling auto-update check using JobScheduler.", new Object[0]);
      ((JobScheduler)this.mContext.getSystemService("jobscheduler")).schedule(localBuilder.build());
      return;
    }
    long l1 = ((Long)G.autoUpdateJobSchedulerMinTimeoutMs.get()).longValue();
    long l2 = ((Long)G.autoUpdateJobSchedulerMaxTimeoutMs.get()).longValue();
    if (((Boolean)FinskyPreferences.autoUpdateWifiOnly.get()).booleanValue()) {}
    for (int j = 2;; j = 1)
    {
      localBuilder.setMinimumLatency(l1).setRequiredNetworkType(j).setRequiresCharging(true).setOverrideDeadline(l2);
      logAutoUpdateRescheduled(false);
      break;
    }
  }
  
  public static class CheckPreconditionsAndAutoUpdateJobService
    extends JobService
    implements UpdateChecker.AutoUpdateCompletionStatusListener
  {
    private JobParameters mCurrentJobParameters;
    
    public boolean onStartJob(JobParameters paramJobParameters)
    {
      this.mCurrentJobParameters = paramJobParameters;
      PersistableBundle localPersistableBundle = this.mCurrentJobParameters.getExtras();
      if (localPersistableBundle != null) {}
      final boolean bool;
      for (final int i = localPersistableBundle.getInt("Finksy.AutoUpdateRescheduleReason", 0);; i = 0)
      {
        bool = this.mCurrentJobParameters.isOverrideDeadlineExpired();
        if ((!bool) || ((i & 0x1) == 0)) {
          break;
        }
        FinskyLog.d("Timed out waiting for job to be scheduled.", new Object[0]);
        jobFinished(this.mCurrentJobParameters, true);
        return false;
      }
      FinskyLog.d("JobScheduler invoked, loading libraries.", new Object[0]);
      ReschedulerStrategy.loadPrerequisites(new Runnable()
      {
        public final void run()
        {
          boolean bool = true;
          if (ReschedulerUsingJobScheduler.CheckPreconditionsAndAutoUpdateJobService.this.mCurrentJobParameters == null) {
            return;
          }
          if ((!bool) && ((0x2 & i) != 0) && (GearheadStateMonitor.getInstance().isProjecting()))
          {
            ReschedulerUsingJobScheduler.CheckPreconditionsAndAutoUpdateJobService.this.jobFinished(ReschedulerUsingJobScheduler.CheckPreconditionsAndAutoUpdateJobService.this.mCurrentJobParameters, bool);
            ReschedulerStrategy.logAutoUpdateRescheduled(bool);
            return;
          }
          if ((!GearheadStateMonitor.getInstance().isProjecting()) && (!ReschedulerStrategy.shouldWaitForPowerOrWifi())) {}
          for (;;)
          {
            ReschedulerStrategy.continueToUpdateChecks(ReschedulerUsingJobScheduler.CheckPreconditionsAndAutoUpdateJobService.this, bool);
            return;
            bool = false;
          }
        }
      });
      return true;
    }
    
    public boolean onStopJob(JobParameters paramJobParameters)
    {
      this.mCurrentJobParameters = null;
      return false;
    }
    
    public final void updateCheckComplete(boolean paramBoolean)
    {
      if (this.mCurrentJobParameters != null) {
        jobFinished(this.mCurrentJobParameters, false);
      }
      if (paramBoolean) {}
      for (String str = "auto-updates finished successfully.";; str = "finished w/error. waiting for next daily hygiene.")
      {
        FinskyLog.d(str, new Object[0]);
        return;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.autoupdate.ReschedulerUsingJobScheduler
 * JD-Core Version:    0.7.0.1
 */