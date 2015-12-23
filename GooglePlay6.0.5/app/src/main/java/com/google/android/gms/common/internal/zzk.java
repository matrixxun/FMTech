package com.google.android.gms.common.internal;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzk
  implements Handler.Callback
{
  public final Handler mHandler;
  public final zza zzaud;
  public final ArrayList<GoogleApiClient.ConnectionCallbacks> zzaue = new ArrayList();
  public final ArrayList<GoogleApiClient.ConnectionCallbacks> zzauf = new ArrayList();
  public final ArrayList<GoogleApiClient.OnConnectionFailedListener> zzaug = new ArrayList();
  public volatile boolean zzauh = false;
  public final AtomicInteger zzaui = new AtomicInteger(0);
  public boolean zzauj = false;
  public final Object zzqp = new Object();
  
  public zzk(Looper paramLooper, zza paramzza)
  {
    this.zzaud = paramzza;
    this.mHandler = new Handler(paramLooper, this);
  }
  
  public final boolean handleMessage(Message paramMessage)
  {
    if (paramMessage.what == 1)
    {
      GoogleApiClient.ConnectionCallbacks localConnectionCallbacks = (GoogleApiClient.ConnectionCallbacks)paramMessage.obj;
      synchronized (this.zzqp)
      {
        if ((this.zzauh) && (this.zzaud.isConnected()) && (this.zzaue.contains(localConnectionCallbacks))) {
          localConnectionCallbacks.onConnected(null);
        }
        return true;
      }
    }
    Log.wtf("GmsClientEvents", "Don't know how to handle message: " + paramMessage.what, new Exception());
    return false;
  }
  
  public final void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    zzx.zzC(paramConnectionCallbacks);
    synchronized (this.zzqp)
    {
      if (this.zzaue.contains(paramConnectionCallbacks))
      {
        Log.w("GmsClientEvents", "registerConnectionCallbacks(): listener " + paramConnectionCallbacks + " is already registered");
        if (this.zzaud.isConnected()) {
          this.mHandler.sendMessage(this.mHandler.obtainMessage(1, paramConnectionCallbacks));
        }
        return;
      }
      this.zzaue.add(paramConnectionCallbacks);
    }
  }
  
  public final void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    zzx.zzC(paramOnConnectionFailedListener);
    synchronized (this.zzqp)
    {
      if (this.zzaug.contains(paramOnConnectionFailedListener))
      {
        Log.w("GmsClientEvents", "registerConnectionFailedListener(): listener " + paramOnConnectionFailedListener + " is already registered");
        return;
      }
      this.zzaug.add(paramOnConnectionFailedListener);
    }
  }
  
  public final void zzqv()
  {
    this.zzauh = false;
    this.zzaui.incrementAndGet();
  }
  
  public static abstract interface zza
  {
    public abstract boolean isConnected();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzk
 * JD-Core Version:    0.7.0.1
 */