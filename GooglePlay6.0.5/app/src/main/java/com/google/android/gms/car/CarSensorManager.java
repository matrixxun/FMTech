package com.google.android.gms.car;

import android.os.Handler;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public final class CarSensorManager
{
  final Handler mHandler;
  zza zzaeH;
  final HashMap<Integer, Object> zzaeI;
  
  private static final class zza
    extends zzba.zza
  {
    private final WeakReference<CarSensorManager> zzadw;
    
    public final void onSensorChanged(CarSensorEvent paramCarSensorEvent)
    {
      CarSensorManager localCarSensorManager = (CarSensorManager)this.zzadw.get();
      if (localCarSensorManager != null) {
        localCarSensorManager.mHandler.sendMessage(localCarSensorManager.mHandler.obtainMessage(0, paramCarSensorEvent));
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarSensorManager
 * JD-Core Version:    0.7.0.1
 */