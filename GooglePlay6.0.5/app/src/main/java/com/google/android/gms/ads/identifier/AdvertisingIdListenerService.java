package com.google.android.gms.ads.identifier;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.internal.zzbd.zza;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AdvertisingIdListenerService
  extends Service
{
  private volatile int zzpr = -1;
  private ExecutorService zzps;
  private IBinder zzpt;
  private final Object zzpu = new Object();
  private boolean zzpv;
  
  public abstract void onAdvertisingIdInfoChanged(AdvertisingIdClient.Info paramInfo);
  
  public final IBinder onBind(Intent paramIntent)
  {
    if ("com.google.android.gms.ads.identifier.BIND_LISTENER".equals(paramIntent.getAction())) {
      return this.zzpt;
    }
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.zzps = Executors.newSingleThreadExecutor();
    this.zzpt = new zza((byte)0);
  }
  
  public void onDestroy()
  {
    synchronized (this.zzpu)
    {
      this.zzpv = true;
      this.zzps.shutdown();
      super.onDestroy();
      return;
    }
  }
  
  private final class zza
    extends zzbd.zza
  {
    private zza() {}
    
    public final void zzb(final Bundle paramBundle)
    {
      synchronized (AdvertisingIdListenerService.zza(AdvertisingIdListenerService.this))
      {
        if (AdvertisingIdListenerService.zzb(AdvertisingIdListenerService.this)) {
          return;
        }
        AdvertisingIdListenerService.zzc(AdvertisingIdListenerService.this);
        AdvertisingIdListenerService.zzd(AdvertisingIdListenerService.this).execute(new Runnable()
        {
          public final void run()
          {
            AdvertisingIdListenerService.this.onAdvertisingIdInfoChanged(new AdvertisingIdClient.Info(paramBundle.getString("ad_id"), paramBundle.getBoolean("lat_enabled")));
          }
        });
        return;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.identifier.AdvertisingIdListenerService
 * JD-Core Version:    0.7.0.1
 */