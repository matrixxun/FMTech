package com.google.android.gms.car;

import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import java.lang.ref.WeakReference;

public final class CarRetailModeManager
{
  final Handler mHandler;
  ICarRetailMode zzaeB;
  zza zzaeC;
  
  public static final class zza
    extends ICarRetailModeListener.zza
  {
    private final WeakReference<CarRetailModeManager> zzadw;
    
    public final void onShowcaseActivated()
      throws RemoteException
    {
      CarRetailModeManager localCarRetailModeManager = (CarRetailModeManager)this.zzadw.get();
      if (localCarRetailModeManager != null)
      {
        if (CarLog.isLoggable("CAR.RETAIL", 3)) {
          Log.d("CAR.RETAIL", "CarRetailModeManager#onShowcaseActivated");
        }
        localCarRetailModeManager.mHandler.sendMessage(localCarRetailModeManager.mHandler.obtainMessage(0));
      }
    }
    
    public final void onShowcaseDeactivated()
      throws RemoteException
    {
      CarRetailModeManager localCarRetailModeManager = (CarRetailModeManager)this.zzadw.get();
      if (localCarRetailModeManager != null)
      {
        if (CarLog.isLoggable("CAR.RETAIL", 3)) {
          Log.d("CAR.RETAIL", "CarRetailModeManager#onShowcaseDeactivated");
        }
        localCarRetailModeManager.mHandler.sendMessage(localCarRetailModeManager.mHandler.obtainMessage(1));
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarRetailModeManager
 * JD-Core Version:    0.7.0.1
 */