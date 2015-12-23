package com.google.android.finsky.gearhead;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.gms.car.Car;
import com.google.android.gms.car.Car.CarApi;
import com.google.android.gms.car.Car.CarConnectionListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public final class GearheadStateMonitor
{
  private static final boolean GEARHEAD_SUPPORTED;
  private static GearheadStateMonitor sInstance;
  private GoogleApiClient mApiClient = null;
  private CountDownLatch mConnectionLatch = new CountDownLatch(1);
  private volatile boolean mHasInitialized = false;
  private volatile boolean mIsProjecting = false;
  private LinkedList<Runnable> mOnReadyRunnables = new LinkedList();
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {}
    for (boolean bool = true;; bool = false)
    {
      GEARHEAD_SUPPORTED = bool;
      return;
    }
  }
  
  public static GearheadStateMonitor getInstance()
  {
    try
    {
      if (sInstance == null)
      {
        GearheadStateMonitor localGearheadStateMonitor1 = new GearheadStateMonitor();
        sInstance = localGearheadStateMonitor1;
        localGearheadStateMonitor1.initialize(null);
      }
      GearheadStateMonitor localGearheadStateMonitor2 = sInstance;
      return localGearheadStateMonitor2;
    }
    finally {}
  }
  
  public final void initialize(Runnable paramRunnable)
  {
    for (;;)
    {
      try
      {
        if (!GEARHEAD_SUPPORTED)
        {
          if (paramRunnable != null) {
            paramRunnable.run();
          }
          return;
        }
        if (this.mConnectionLatch.getCount() == 0L)
        {
          if (paramRunnable == null) {
            continue;
          }
          paramRunnable.run();
          continue;
        }
        if (paramRunnable == null) {}
      }
      finally {}
      synchronized (this.mOnReadyRunnables)
      {
        this.mOnReadyRunnables.add(paramRunnable);
        if (this.mHasInitialized) {
          continue;
        }
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("com.google.android.gms.car.CONNECTED");
        localIntentFilter.addAction("com.google.android.gms.car.DISCONNECTED");
        FinskyApp localFinskyApp = FinskyApp.get();
        localFinskyApp.registerReceiver(new BroadcastReceiver()
        {
          public final void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
          {
            if ("com.google.android.gms.car.CONNECTED".equals(paramAnonymousIntent.getAction()))
            {
              GearheadStateMonitor.access$002(GearheadStateMonitor.this, true);
              GearheadStateMonitor.access$100(GearheadStateMonitor.this);
              return;
            }
            GearheadStateMonitor.access$002(GearheadStateMonitor.this, false);
            GearheadStateMonitor.access$100(GearheadStateMonitor.this);
          }
        }, localIntentFilter, "com.google.android.gms.permission.CAR", null);
        this.mApiClient = Car.buildGoogleApiClientForCar(localFinskyApp, new GoogleApiClient.ConnectionCallbacks()new GoogleApiClient.OnConnectionFailedListener
        {
          public final void onConnected(Bundle paramAnonymousBundle)
          {
            GearheadStateMonitor.access$002(GearheadStateMonitor.this, Car.CarApi.isConnectedToCar(GearheadStateMonitor.this.mApiClient));
            GearheadStateMonitor.access$100(GearheadStateMonitor.this);
            GearheadStateMonitor.this.mApiClient.disconnect();
          }
          
          public final void onConnectionSuspended(int paramAnonymousInt)
          {
            GearheadStateMonitor.access$100(GearheadStateMonitor.this);
          }
        }, new GoogleApiClient.OnConnectionFailedListener()new Car.CarConnectionListener
        {
          public final void onConnectionFailed(ConnectionResult paramAnonymousConnectionResult)
          {
            FinskyLog.w("Could not connect to GMS for Auto connection state: %s", new Object[] { paramAnonymousConnectionResult });
            GearheadStateMonitor.access$100(GearheadStateMonitor.this);
          }
        }, new Car.CarConnectionListener()
        {
          public final void onConnected$13462e()
          {
            GearheadStateMonitor.access$002(GearheadStateMonitor.this, true);
            GearheadStateMonitor.access$100(GearheadStateMonitor.this);
          }
          
          public final void onDisconnected()
          {
            GearheadStateMonitor.access$002(GearheadStateMonitor.this, false);
            GearheadStateMonitor.access$100(GearheadStateMonitor.this);
          }
        });
        this.mHasInitialized = true;
        this.mApiClient.connect();
      }
    }
  }
  
  public final boolean isProjecting()
  {
    if (!GEARHEAD_SUPPORTED) {
      return false;
    }
    try
    {
      this.mConnectionLatch.await();
      return this.mIsProjecting;
    }
    catch (InterruptedException localInterruptedException)
    {
      FinskyLog.w("Interrupted while awaiting projection result!", new Object[0]);
    }
    return false;
  }
  
  public final boolean isReady()
  {
    if (!GEARHEAD_SUPPORTED) {}
    while (this.mConnectionLatch.getCount() <= 0L) {
      return true;
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.gearhead.GearheadStateMonitor
 * JD-Core Version:    0.7.0.1
 */