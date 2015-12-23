package com.google.android.gms.car;

import android.os.Handler;
import android.util.Log;
import java.lang.ref.WeakReference;

public final class CarNavigationStatusManager
{
  final Handler mHandler;
  final zzar zzaep;
  zza zzaeq;
  CarNavigationStatusListener zzaer;
  int zzaes;
  int zzaet;
  int zzaeu;
  int zzaev;
  int zzaew;
  
  public static abstract interface CarNavigationStatusListener {}
  
  private static final class zza
    extends zzas.zza
  {
    private final WeakReference<CarNavigationStatusManager> zzadw;
    
    public final void onStart(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      CarNavigationStatusManager localCarNavigationStatusManager = (CarNavigationStatusManager)this.zzadw.get();
      if (localCarNavigationStatusManager != null)
      {
        if (CarLog.isLoggable("CAR.SENSOR", 3)) {
          Log.d("CAR.SENSOR", "onStart(" + paramInt1 + ", " + paramInt2 + ", " + paramInt3 + ", " + paramInt4 + ", " + paramInt5 + ")");
        }
        localCarNavigationStatusManager.zzaes = paramInt1;
        localCarNavigationStatusManager.zzaet = paramInt2;
        localCarNavigationStatusManager.zzaeu = paramInt3;
        localCarNavigationStatusManager.zzaev = paramInt4;
        localCarNavigationStatusManager.zzaew = paramInt5;
        localCarNavigationStatusManager.mHandler.sendMessage(localCarNavigationStatusManager.mHandler.obtainMessage(1));
      }
    }
    
    public final void onStop()
    {
      CarNavigationStatusManager localCarNavigationStatusManager = (CarNavigationStatusManager)this.zzadw.get();
      if (localCarNavigationStatusManager != null) {
        localCarNavigationStatusManager.mHandler.sendMessage(localCarNavigationStatusManager.mHandler.obtainMessage(2));
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarNavigationStatusManager
 * JD-Core Version:    0.7.0.1
 */