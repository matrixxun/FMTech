package com.google.android.gms.car;

import android.os.Handler;
import java.lang.ref.WeakReference;

public final class CarMediaManager
{
  final Handler mHandler;
  final zzal zzaee;
  final zza zzaef;
  CarMediaListener zzaeg;
  final zzan zzaeh;
  final zzb zzaei;
  
  public static abstract interface CarMediaListener {}
  
  private static final class zza
    extends zzam.zza
  {
    private WeakReference<CarMediaManager> zzadw;
    
    public final void onGetNode(String paramString, int paramInt, boolean paramBoolean)
    {
      CarMediaManager localCarMediaManager = (CarMediaManager)this.zzadw.get();
      Handler localHandler1;
      Handler localHandler2;
      if (localCarMediaManager != null)
      {
        localHandler1 = localCarMediaManager.mHandler;
        localHandler2 = localCarMediaManager.mHandler;
        if (!paramBoolean) {
          break label55;
        }
      }
      label55:
      for (int i = 1;; i = 0)
      {
        localHandler1.sendMessage(localHandler2.obtainMessage(2, paramInt, i, paramString));
        return;
      }
    }
    
    public final void zzg(String paramString, int paramInt)
    {
      CarMediaManager localCarMediaManager = (CarMediaManager)this.zzadw.get();
      if (localCarMediaManager != null) {
        localCarMediaManager.mHandler.sendMessage(localCarMediaManager.mHandler.obtainMessage(1, paramInt, 0, paramString));
      }
    }
  }
  
  private static final class zzb
    extends zzao.zza
  {
    private WeakReference<CarMediaManager> zzadw;
    
    public final void zzbE(int paramInt)
    {
      CarMediaManager localCarMediaManager = (CarMediaManager)this.zzadw.get();
      if (localCarMediaManager != null) {
        localCarMediaManager.mHandler.sendMessage(localCarMediaManager.mHandler.obtainMessage(3, paramInt, 0));
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarMediaManager
 * JD-Core Version:    0.7.0.1
 */