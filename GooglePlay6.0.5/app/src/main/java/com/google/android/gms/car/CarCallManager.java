package com.google.android.gms.car;

import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

public final class CarCallManager
{
  final List<CarCallListener> mListeners;
  final zzah zzadf;
  final zzat zzadg;
  zza zzadh;
  final zzb zzadi;
  
  private static final class zza
    extends zzai.zza
  {
    private final WeakReference<CarCallManager> zzadw;
    
    public final void dispatchPhoneKeyEvent(KeyEvent paramKeyEvent)
    {
      CarCallManager localCarCallManager = (CarCallManager)this.zzadw.get();
      if (localCarCallManager != null)
      {
        if (CarLog.isLoggable("CAR.TEL.Manager", 2)) {
          Log.v("CAR.TEL.Manager", "dispatchPhoneKeyEvent");
        }
        synchronized (localCarCallManager.mListeners)
        {
          Iterator localIterator = localCarCallManager.mListeners.iterator();
          if (localIterator.hasNext())
          {
            CarCallManager.1 local1 = new CarCallManager.1(localCarCallManager, (CarCallListener)localIterator.next(), paramKeyEvent);
            zzbf.zza(Looper.getMainLooper(), local1);
          }
        }
      }
    }
    
    public final void onAudioStateChanged(boolean paramBoolean, int paramInt1, int paramInt2)
    {
      CarCallManager localCarCallManager = (CarCallManager)this.zzadw.get();
      if (localCarCallManager != null)
      {
        if (CarLog.isLoggable("CAR.TEL.Manager", 2))
        {
          Object[] arrayOfObject = new Object[3];
          arrayOfObject[0] = Boolean.valueOf(paramBoolean);
          arrayOfObject[1] = Integer.valueOf(paramInt1);
          arrayOfObject[2] = Integer.valueOf(paramInt2);
          Log.v("CAR.TEL.Manager", String.format("onAudioStateChanged isMuted=%b\troute=%d\tsupportedRoutes=%d", arrayOfObject));
        }
        synchronized (localCarCallManager.mListeners)
        {
          Iterator localIterator = localCarCallManager.mListeners.iterator();
          if (localIterator.hasNext())
          {
            CarCallManager.5 local5 = new CarCallManager.5(localCarCallManager, (CarCallListener)localIterator.next(), paramBoolean, paramInt1, paramInt2);
            zzbf.zza(Looper.getMainLooper(), local5);
          }
        }
      }
    }
    
    public final void onCallAdded(CarCall paramCarCall)
    {
      CarCallManager localCarCallManager = (CarCallManager)this.zzadw.get();
      if (localCarCallManager != null)
      {
        if (CarLog.isLoggable("CAR.TEL.Manager", 2)) {
          Log.v("CAR.TEL.Manager", "onCallAdded " + paramCarCall);
        }
        synchronized (localCarCallManager.mListeners)
        {
          Iterator localIterator = localCarCallManager.mListeners.iterator();
          if (localIterator.hasNext())
          {
            CarCallManager.6 local6 = new CarCallManager.6(localCarCallManager, (CarCallListener)localIterator.next(), paramCarCall);
            zzbf.zza(Looper.getMainLooper(), local6);
          }
        }
      }
    }
    
    public final void onCallDestroyed(CarCall paramCarCall)
    {
      CarCallManager localCarCallManager = (CarCallManager)this.zzadw.get();
      if (localCarCallManager != null)
      {
        if (CarLog.isLoggable("CAR.TEL.Manager", 2)) {
          Log.v("CAR.TEL.Manager", "onCallDestroyed");
        }
        synchronized (localCarCallManager.mListeners)
        {
          Iterator localIterator = localCarCallManager.mListeners.iterator();
          if (localIterator.hasNext())
          {
            CarCallManager.3 local3 = new CarCallManager.3(localCarCallManager, (CarCallListener)localIterator.next(), paramCarCall);
            zzbf.zza(Looper.getMainLooper(), local3);
          }
        }
      }
    }
    
    public final void onCallRemoved(CarCall paramCarCall)
    {
      CarCallManager localCarCallManager = (CarCallManager)this.zzadw.get();
      if (localCarCallManager != null)
      {
        if (CarLog.isLoggable("CAR.TEL.Manager", 2)) {
          Log.v("CAR.TEL.Manager", "onCallRemoved " + paramCarCall);
        }
        synchronized (localCarCallManager.mListeners)
        {
          Iterator localIterator = localCarCallManager.mListeners.iterator();
          if (localIterator.hasNext())
          {
            CarCallManager.7 local7 = new CarCallManager.7(localCarCallManager, (CarCallListener)localIterator.next(), paramCarCall);
            zzbf.zza(Looper.getMainLooper(), local7);
          }
        }
      }
    }
    
    public final void onCannedTextResponsesLoaded(CarCall paramCarCall, List<String> paramList)
    {
      CarCallManager localCarCallManager = (CarCallManager)this.zzadw.get();
      if (localCarCallManager != null)
      {
        if (CarLog.isLoggable("CAR.TEL.Manager", 2)) {
          Log.v("CAR.TEL.Manager", "onCannedTextResponsesLoaded");
        }
        synchronized (localCarCallManager.mListeners)
        {
          Iterator localIterator = localCarCallManager.mListeners.iterator();
          if (localIterator.hasNext())
          {
            CarCallManager.12 local12 = new CarCallManager.12(localCarCallManager, (CarCallListener)localIterator.next(), paramCarCall, paramList);
            zzbf.zza(Looper.getMainLooper(), local12);
          }
        }
      }
    }
    
    public final void onChildrenChanged(CarCall paramCarCall, List<CarCall> paramList)
    {
      CarCallManager localCarCallManager = (CarCallManager)this.zzadw.get();
      if (localCarCallManager != null)
      {
        if (CarLog.isLoggable("CAR.TEL.Manager", 2)) {
          Log.v("CAR.TEL.Manager", "onChildrenChanged");
        }
        synchronized (localCarCallManager.mListeners)
        {
          Iterator localIterator = localCarCallManager.mListeners.iterator();
          if (localIterator.hasNext())
          {
            CarCallManager.10 local10 = new CarCallManager.10(localCarCallManager, (CarCallListener)localIterator.next(), paramCarCall, paramList);
            zzbf.zza(Looper.getMainLooper(), local10);
          }
        }
      }
    }
    
    public final void onConferenceableCallsChanged(CarCall paramCarCall, List<CarCall> paramList)
    {
      CarCallManager localCarCallManager = (CarCallManager)this.zzadw.get();
      if (localCarCallManager != null)
      {
        if (CarLog.isLoggable("CAR.TEL.Manager", 2)) {
          Log.v("CAR.TEL.Manager", "onConferenceableCallsChanged");
        }
        synchronized (localCarCallManager.mListeners)
        {
          Iterator localIterator = localCarCallManager.mListeners.iterator();
          if (localIterator.hasNext())
          {
            CarCallManager.4 local4 = new CarCallManager.4(localCarCallManager, (CarCallListener)localIterator.next(), paramCarCall, paramList);
            zzbf.zza(Looper.getMainLooper(), local4);
          }
        }
      }
    }
    
    public final void onDetailsChanged(CarCall paramCarCall, CarCall.Details paramDetails)
    {
      CarCallManager localCarCallManager = (CarCallManager)this.zzadw.get();
      if (localCarCallManager != null)
      {
        if (CarLog.isLoggable("CAR.TEL.Manager", 2)) {
          Log.v("CAR.TEL.Manager", "onDetailsChanged");
        }
        synchronized (localCarCallManager.mListeners)
        {
          Iterator localIterator = localCarCallManager.mListeners.iterator();
          if (localIterator.hasNext())
          {
            CarCallManager.11 local11 = new CarCallManager.11(localCarCallManager, (CarCallListener)localIterator.next(), paramCarCall, paramDetails);
            zzbf.zza(Looper.getMainLooper(), local11);
          }
        }
      }
    }
    
    public final void onParentChanged(CarCall paramCarCall1, CarCall paramCarCall2)
    {
      CarCallManager localCarCallManager = (CarCallManager)this.zzadw.get();
      if (localCarCallManager != null)
      {
        if (CarLog.isLoggable("CAR.TEL.Manager", 2)) {
          Log.v("CAR.TEL.Manager", "onParentChanged");
        }
        synchronized (localCarCallManager.mListeners)
        {
          Iterator localIterator = localCarCallManager.mListeners.iterator();
          if (localIterator.hasNext())
          {
            CarCallManager.9 local9 = new CarCallManager.9(localCarCallManager, (CarCallListener)localIterator.next(), paramCarCall1, paramCarCall2);
            zzbf.zza(Looper.getMainLooper(), local9);
          }
        }
      }
    }
    
    public final void onPostDialWait(CarCall paramCarCall, String paramString)
    {
      CarCallManager localCarCallManager = (CarCallManager)this.zzadw.get();
      if (localCarCallManager != null)
      {
        if (CarLog.isLoggable("CAR.TEL.Manager", 2)) {
          Log.v("CAR.TEL.Manager", "onPostDialWait");
        }
        synchronized (localCarCallManager.mListeners)
        {
          Iterator localIterator = localCarCallManager.mListeners.iterator();
          if (localIterator.hasNext())
          {
            CarCallManager.2 local2 = new CarCallManager.2(localCarCallManager, (CarCallListener)localIterator.next(), paramCarCall, paramString);
            zzbf.zza(Looper.getMainLooper(), local2);
          }
        }
      }
    }
    
    public final void onStateChanged(CarCall paramCarCall, int paramInt)
    {
      CarCallManager localCarCallManager = (CarCallManager)this.zzadw.get();
      if (localCarCallManager != null)
      {
        if (CarLog.isLoggable("CAR.TEL.Manager", 2)) {
          Log.v("CAR.TEL.Manager", "onStateChanged " + paramCarCall);
        }
        synchronized (localCarCallManager.mListeners)
        {
          Iterator localIterator = localCarCallManager.mListeners.iterator();
          if (localIterator.hasNext())
          {
            CarCallManager.8 local8 = new CarCallManager.8(localCarCallManager, (CarCallListener)localIterator.next(), paramCarCall, paramInt);
            zzbf.zza(Looper.getMainLooper(), local8);
          }
        }
      }
    }
  }
  
  private static final class zzb
    extends zzau.zza
  {
    private final WeakReference<CarCallManager> zzadw;
    
    public final void zzd(String paramString1, String paramString2, int paramInt)
    {
      if ((CarCallManager)this.zzadw.get() != null) {
        Log.d("CAR.TEL.Manager", "Action: " + paramInt + " with number: " + paramString1 + " id: " + paramString2);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarCallManager
 * JD-Core Version:    0.7.0.1
 */