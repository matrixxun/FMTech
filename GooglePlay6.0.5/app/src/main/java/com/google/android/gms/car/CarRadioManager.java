package com.google.android.gms.car;

import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CarRadioManager
{
  final Handler mHandler;
  private final List<Object> mListeners;
  private final zzax zzaey;
  private final zza zzaez;
  
  final void zzlz()
  {
    if (CarLog.isLoggable("CAR.RADIO", 3)) {
      Log.d("CAR.RADIO", "handleCarDisconnection");
    }
    try
    {
      this.zzaey.zzb(this.zzaez);
      label30:
      Iterator localIterator = this.mListeners.iterator();
      while (localIterator.hasNext()) {
        localIterator.next();
      }
      this.mListeners.clear();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      break label30;
    }
  }
  
  private final class zza
    extends zzay.zza
  {
    private final WeakReference<CarRadioManager> zzadw;
    
    public final void onActiveRadioSelected(int paramInt1, int paramInt2, RadioStationInfo paramRadioStationInfo)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      localCarRadioManager.mHandler.sendMessage(localCarRadioManager.mHandler.obtainMessage(4, paramInt1, paramInt2, paramRadioStationInfo));
    }
    
    public final void onCancel(int paramInt1, int paramInt2)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      localCarRadioManager.mHandler.sendMessage(localCarRadioManager.mHandler.obtainMessage(6, paramInt1, paramInt2, null));
    }
    
    public final void onChannelSpacingConfig(int paramInt1, int paramInt2, int paramInt3)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      localCarRadioManager.mHandler.sendMessage(localCarRadioManager.mHandler.obtainMessage(11, paramInt1, paramInt2, Integer.valueOf(paramInt3)));
    }
    
    public final void onMute(int paramInt1, int paramInt2, boolean paramBoolean)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      localCarRadioManager.mHandler.sendMessage(localCarRadioManager.mHandler.obtainMessage(9, paramInt1, paramInt2, Boolean.valueOf(paramBoolean)));
    }
    
    public final void onProgramList(int paramInt1, int paramInt2, boolean paramBoolean, List<RadioStationInfo> paramList)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      Bundle localBundle = new Bundle();
      localBundle.putInt("status", paramInt1);
      localBundle.putInt("radioId", paramInt2);
      localBundle.putBoolean("completed", paramBoolean);
      localBundle.putParcelableArrayList("programs", new ArrayList(paramList));
      localCarRadioManager.mHandler.sendMessage(localCarRadioManager.mHandler.obtainMessage(8, localBundle));
    }
    
    public final void onRadioSource(int paramInt, boolean paramBoolean)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      Handler localHandler1 = localCarRadioManager.mHandler;
      Handler localHandler2 = localCarRadioManager.mHandler;
      if (paramBoolean) {}
      for (int i = 1;; i = 0)
      {
        localHandler1.sendMessage(localHandler2.obtainMessage(10, paramInt, i, null));
        return;
      }
    }
    
    public final void onScan(int paramInt1, int paramInt2, boolean paramBoolean)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      localCarRadioManager.mHandler.sendMessage(localCarRadioManager.mHandler.obtainMessage(3, paramInt1, paramInt2, Boolean.valueOf(paramBoolean)));
    }
    
    public final void onSeek(int paramInt1, int paramInt2)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      localCarRadioManager.mHandler.sendMessage(localCarRadioManager.mHandler.obtainMessage(1, paramInt1, paramInt2, null));
    }
    
    public final void onStationInfoUpdate(int paramInt, RadioStationInfo paramRadioStationInfo)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      localCarRadioManager.mHandler.sendMessage(localCarRadioManager.mHandler.obtainMessage(5, paramInt, -1, paramRadioStationInfo));
    }
    
    public final void onStationPresets(List<StationPresetList> paramList)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      localCarRadioManager.mHandler.sendMessage(localCarRadioManager.mHandler.obtainMessage(12, paramList));
    }
    
    public final void onStep(int paramInt1, int paramInt2)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      localCarRadioManager.mHandler.sendMessage(localCarRadioManager.mHandler.obtainMessage(0, paramInt1, paramInt2, null));
    }
    
    public final void onTrafficUpdate(int paramInt1, int paramInt2, List<TrafficIncident> paramList)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      localCarRadioManager.mHandler.sendMessage(localCarRadioManager.mHandler.obtainMessage(7, paramInt1, paramInt2, paramList));
    }
    
    public final void onTune(int paramInt1, int paramInt2)
      throws RemoteException
    {
      CarRadioManager localCarRadioManager = (CarRadioManager)this.zzadw.get();
      if (localCarRadioManager == null)
      {
        if (CarLog.isLoggable("CAR.RADIO", 3)) {
          Log.d("CAR.RADIO", "manager is null on callback");
        }
        return;
      }
      localCarRadioManager.mHandler.sendMessage(localCarRadioManager.mHandler.obtainMessage(2, paramInt1, paramInt2, null));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarRadioManager
 * JD-Core Version:    0.7.0.1
 */