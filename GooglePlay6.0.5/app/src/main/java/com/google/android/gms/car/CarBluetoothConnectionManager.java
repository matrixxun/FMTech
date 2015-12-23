package com.google.android.gms.car;

import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;

public final class CarBluetoothConnectionManager
{
  final zza zzacZ;
  
  private static final class zza
    extends zzag.zza
  {
    final Handler mHandler;
    
    public final void onCarDelayedPairing()
      throws RemoteException
    {
      if (CarLog.isLoggable("CarBluetoothClient", 3)) {
        Log.d("CarBluetoothClient", "onCarDelayedPairing");
      }
      this.mHandler.sendMessage(this.mHandler.obtainMessage(2));
    }
    
    public final void onDisabled()
      throws RemoteException
    {
      if (CarLog.isLoggable("CarBluetoothClient", 3)) {
        Log.d("CarBluetoothClient", "onDisabled");
      }
      this.mHandler.sendMessage(this.mHandler.obtainMessage(1));
    }
    
    public final void onEnabled()
      throws RemoteException
    {
      if (CarLog.isLoggable("CarBluetoothClient", 3)) {
        Log.d("CarBluetoothClient", "onEnabled");
      }
      this.mHandler.sendMessage(this.mHandler.obtainMessage(0));
    }
    
    public final void onHfpConnected()
      throws RemoteException
    {
      if (CarLog.isLoggable("CarBluetoothClient", 3)) {
        Log.d("CarBluetoothClient", "onHfpConnected");
      }
      this.mHandler.sendMessage(this.mHandler.obtainMessage(5));
    }
    
    public final void onHfpDisconnected()
      throws RemoteException
    {
      if (CarLog.isLoggable("CarBluetoothClient", 3)) {
        Log.d("CarBluetoothClient", "onHfpDisconnected");
      }
      this.mHandler.sendMessage(this.mHandler.obtainMessage(6));
    }
    
    public final void onPaired()
      throws RemoteException
    {
      if (CarLog.isLoggable("CarBluetoothClient", 3)) {
        Log.d("CarBluetoothClient", "onPaired");
      }
      this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
    }
    
    public final void onUnpaired()
      throws RemoteException
    {
      if (CarLog.isLoggable("CarBluetoothClient", 3)) {
        Log.d("CarBluetoothClient", "onUnpaired");
      }
      this.mHandler.sendMessage(this.mHandler.obtainMessage(4));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarBluetoothConnectionManager
 * JD-Core Version:    0.7.0.1
 */