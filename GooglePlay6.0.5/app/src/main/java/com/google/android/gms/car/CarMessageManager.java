package com.google.android.gms.car;

import android.os.Handler;
import java.lang.ref.WeakReference;

public final class CarMessageManager
{
  final Handler mHandler;
  final zzap zzaek;
  final zza zzael;
  volatile CarMessageListener zzaem;
  
  public static abstract interface CarMessageListener {}
  
  private static final class zza
    extends zzaq.zza
  {
    private final WeakReference<CarMessageManager> zzadw;
    
    public final void onIntegerMessage(int paramInt1, int paramInt2, int paramInt3)
    {
      CarMessageManager localCarMessageManager = (CarMessageManager)this.zzadw.get();
      if (localCarMessageManager == null) {
        return;
      }
      localCarMessageManager.mHandler.sendMessage(localCarMessageManager.mHandler.obtainMessage(1, paramInt1, paramInt2, new Integer(paramInt3)));
    }
    
    public final void onOwnershipLost(int paramInt)
    {
      CarMessageManager localCarMessageManager = (CarMessageManager)this.zzadw.get();
      if (localCarMessageManager == null) {
        return;
      }
      localCarMessageManager.mHandler.sendMessage(localCarMessageManager.mHandler.obtainMessage(2, paramInt, 0));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarMessageManager
 * JD-Core Version:    0.7.0.1
 */