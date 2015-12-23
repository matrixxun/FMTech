package com.google.android.finsky.autoupdate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.SystemClock;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.gearhead.GearheadStateMonitor;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;

public final class ReschedulerUsingBroadcast
  extends ReschedulerStrategy
{
  static void cancelAlarm()
  {
    ((AlarmManager)FinskyApp.get().getSystemService("alarm")).cancel(AutoUpdateRecheckForBroadcast.access$100(ReschedulerUsingBroadcast.AutoUpdateRecheckForBroadcast.State.UNKNOWN));
    FinskyLog.d("Canceled alarm.", new Object[0]);
  }
  
  static void disableBroadcastReceivers()
  {
    setBroadcastReceiverState(PowerBroadcastReceiver.class, false);
    setBroadcastReceiverState(WifiBroadcastReceiver.class, false);
    FinskyLog.d("Disabled broadcast receivers for wifi and power.", new Object[0]);
  }
  
  static void enableBroadcastReceivers()
  {
    boolean bool1 = shouldWaitForPower();
    boolean bool2 = shouldWaitForWifi();
    setBroadcastReceiverState(PowerBroadcastReceiver.class, shouldWaitForPower());
    setBroadcastReceiverState(WifiBroadcastReceiver.class, bool2);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Boolean.valueOf(bool1);
    arrayOfObject[1] = Boolean.valueOf(bool2);
    FinskyLog.d("Enabling receivers: power=%s wifi=%s", arrayOfObject);
  }
  
  static void setAlarmFor(ReschedulerUsingBroadcast.AutoUpdateRecheckForBroadcast.State paramState)
  {
    if (paramState.timeInterval.longValue() <= 0L)
    {
      FinskyLog.wtf("Unexpected time interval should be > 0", new Object[0]);
      return;
    }
    AlarmManager localAlarmManager = (AlarmManager)FinskyApp.get().getSystemService("alarm");
    PendingIntent localPendingIntent = AutoUpdateRecheckForBroadcast.access$100(paramState);
    localAlarmManager.set(3, SystemClock.elapsedRealtime() + paramState.timeInterval.longValue(), localPendingIntent);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramState.timeInterval;
    arrayOfObject[1] = paramState.name();
    FinskyLog.d("Scheduling recheck in %d MS for state: %s", arrayOfObject);
  }
  
  private static void setBroadcastReceiverState(Class<?> paramClass, boolean paramBoolean)
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    ComponentName localComponentName = new ComponentName(localFinskyApp, paramClass);
    PackageManager localPackageManager = localFinskyApp.getPackageManager();
    if (paramBoolean) {}
    for (int i = 1;; i = 2)
    {
      localPackageManager.setComponentEnabledSetting(localComponentName, i, 1);
      return;
    }
  }
  
  public final boolean canUpdateNow()
  {
    return (!GearheadStateMonitor.getInstance().isProjecting()) && (!shouldWaitForWifi()) && (!shouldWaitForPower());
  }
  
  public final void schedule()
  {
    cancelAlarm();
    disableBroadcastReceivers();
    if (shouldWaitForPowerOrWifi())
    {
      enableBroadcastReceivers();
      logAutoUpdateRescheduled(null, false);
    }
    while (!GearheadStateMonitor.getInstance().isProjecting()) {
      return;
    }
    setAlarmFor(ReschedulerUsingBroadcast.AutoUpdateRecheckForBroadcast.State.GEARHEAD);
    logAutoUpdateRescheduled(null, true);
  }
  
  public static class AutoUpdateRecheckForBroadcast
    extends Service
  {
    private int mServiceStartId;
    
    private void proceedToCheckGearhead(final State paramState)
    {
      ReschedulerStrategy.loadPrerequisites(new Runnable()
      {
        public final void run()
        {
          if (GearheadStateMonitor.getInstance().isProjecting())
          {
            ReschedulerUsingBroadcast.setAlarmFor(ReschedulerUsingBroadcast.AutoUpdateRecheckForBroadcast.State.GEARHEAD);
            ReschedulerStrategy.logAutoUpdateRescheduled(Integer.valueOf(paramState.ordinal()), true);
          }
          for (;;)
          {
            ReschedulerUsingBroadcast.AutoUpdateRecheckForBroadcast.this.stopSelf(ReschedulerUsingBroadcast.AutoUpdateRecheckForBroadcast.this.mServiceStartId);
            return;
            ReschedulerStrategy.continueToUpdateChecks(new UpdateChecker.AutoUpdateCompletionStatusListener()
            {
              public final void updateCheckComplete(boolean paramAnonymous2Boolean)
              {
                if (paramAnonymous2Boolean)
                {
                  FinskyLog.d("auto-updates finished successfully.", new Object[0]);
                  return;
                }
                FinskyLog.d("finished w/error. waiting for next daily hygiene.", new Object[0]);
              }
            }, true);
          }
        }
      });
    }
    
    static void start()
    {
      ReschedulerUsingBroadcast.disableBroadcastReceivers();
      ReschedulerUsingBroadcast.setAlarmFor(State.DEBOUNCE);
    }
    
    public IBinder onBind(Intent paramIntent)
    {
      return null;
    }
    
    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
    {
      this.mServiceStartId = paramInt2;
      State localState = State.valueOf(paramIntent.getStringExtra("Finksy.RecheckAutoUpdateFromAlarm"));
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localState.name();
      FinskyLog.d("Handling state : %s", arrayOfObject);
      switch (ReschedulerUsingBroadcast.1.$SwitchMap$com$google$android$finsky$autoupdate$ReschedulerUsingBroadcast$AutoUpdateRecheckForBroadcast$State[localState.ordinal()])
      {
      default: 
        FinskyLog.w("Unexpected state", new Object[0]);
      }
      for (;;)
      {
        stopSelf(this.mServiceStartId);
        return 2;
        ReschedulerStrategy.logAutoUpdateRescheduled(Integer.valueOf(State.BROADCAST.ordinal()), false);
        if (ReschedulerStrategy.shouldWaitForPowerOrWifi())
        {
          ReschedulerUsingBroadcast.enableBroadcastReceivers();
          ReschedulerUsingBroadcast.cancelAlarm();
        }
        else
        {
          ReschedulerUsingBroadcast.setAlarmFor(State.STABILIZER);
          continue;
          if (ReschedulerStrategy.shouldWaitForPowerOrWifi())
          {
            ReschedulerUsingBroadcast.setAlarmFor(State.THROTTLE);
            ReschedulerStrategy.logAutoUpdateRescheduled(Integer.valueOf(State.STABILIZER.ordinal()), false);
          }
          else
          {
            proceedToCheckGearhead(localState);
            return 2;
            if (!ReschedulerStrategy.shouldWaitForPowerOrWifi()) {
              break;
            }
            ReschedulerUsingBroadcast.enableBroadcastReceivers();
            ReschedulerUsingBroadcast.cancelAlarm();
            ReschedulerStrategy.logAutoUpdateRescheduled(Integer.valueOf(localState.ordinal()), false);
          }
        }
      }
      proceedToCheckGearhead(localState);
      return 2;
    }
    
    public static enum State
    {
      private final Long timeInterval;
      
      static
      {
        DEBOUNCE = new State("DEBOUNCE", 1, (Long)G.autoUpdateBroadcastDebouncerDelayMs.get());
        BROADCAST = new State("BROADCAST", 2, Long.valueOf(0L));
        STABILIZER = new State("STABILIZER", 3, (Long)G.autoUpdateBroadcastStabilizerDelayMs.get());
        THROTTLE = new State("THROTTLE", 4, (Long)G.autoUpdateThrottleIntervalMs.get());
        GEARHEAD = new State("GEARHEAD", 5, (Long)G.autoUpdateGearheadCheckIntervalMs.get());
        State[] arrayOfState = new State[6];
        arrayOfState[0] = UNKNOWN;
        arrayOfState[1] = DEBOUNCE;
        arrayOfState[2] = BROADCAST;
        arrayOfState[3] = STABILIZER;
        arrayOfState[4] = THROTTLE;
        arrayOfState[5] = GEARHEAD;
        $VALUES = arrayOfState;
      }
      
      private State(Long paramLong)
      {
        this.timeInterval = paramLong;
      }
    }
  }
  
  public static class PowerBroadcastReceiver
    extends BroadcastReceiver
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ("android.intent.action.ACTION_POWER_CONNECTED".equals(paramIntent.getAction())) {
        ReschedulerUsingBroadcast.AutoUpdateRecheckForBroadcast.start();
      }
    }
  }
  
  public static class WifiBroadcastReceiver
    extends BroadcastReceiver
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ("android.net.wifi.STATE_CHANGE".equals(paramIntent.getAction()))
      {
        NetworkInfo localNetworkInfo = (NetworkInfo)paramIntent.getParcelableExtra("networkInfo");
        if ((localNetworkInfo != null) && (localNetworkInfo.isConnected())) {
          ReschedulerUsingBroadcast.AutoUpdateRecheckForBroadcast.start();
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.autoupdate.ReschedulerUsingBroadcast
 * JD-Core Version:    0.7.0.1
 */