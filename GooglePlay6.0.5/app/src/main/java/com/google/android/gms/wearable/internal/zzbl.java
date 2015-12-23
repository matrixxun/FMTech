package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class zzbl<T>
{
  private final Map<T, zzcf<T>> zzaMe = new HashMap();
  
  public final void zzb(zzce paramzzce)
  {
    synchronized (this.zzaMe)
    {
      zzcd.zzw localzzw = new zzcd.zzw();
      Iterator localIterator = this.zzaMe.entrySet().iterator();
      for (;;)
      {
        if (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          zzcf localzzcf = (zzcf)localEntry.getValue();
          if (localzzcf == null) {
            continue;
          }
          zzcf.zzf(localzzcf.zzchJ);
          localzzcf.zzchJ = null;
          zzcf.zzf(localzzcf.zzchK);
          localzzcf.zzchK = null;
          zzcf.zzf(localzzcf.zzchL);
          localzzcf.zzchL = null;
          zzcf.zzf(localzzcf.zzbpK);
          localzzcf.zzbpK = null;
          zzcf.zzf(localzzcf.zzchM);
          localzzcf.zzchM = null;
          zzcf.zzf(localzzcf.zzchN);
          localzzcf.zzchN = null;
          zzcf.zzf(localzzcf.zzchO);
          localzzcf.zzchO = null;
          zzcf.zzf(localzzcf.zzchP);
          localzzcf.zzchP = null;
          zzcf.zzf(localzzcf.zzchQ);
          localzzcf.zzchQ = null;
          boolean bool = paramzzce.isConnected();
          if (!bool) {
            continue;
          }
          try
          {
            ((zzbb)paramzzce.zzqn()).zza(localzzw, new RemoveListenerRequest(localzzcf));
            if (Log.isLoggable("WearableClient", 2)) {
              Log.d("WearableClient", "disconnect: removed: " + localEntry.getKey() + "/" + localzzcf);
            }
          }
          catch (RemoteException localRemoteException)
          {
            Log.w("WearableClient", "disconnect: Didn't remove: " + localEntry.getKey() + "/" + localzzcf);
          }
        }
      }
    }
    this.zzaMe.clear();
  }
  
  public final void zzid(IBinder paramIBinder)
  {
    synchronized (this.zzaMe)
    {
      zzbb localzzbb = zzbb.zza.zzic(paramIBinder);
      zzcd.zzw localzzw = new zzcd.zzw();
      Iterator localIterator = this.zzaMe.entrySet().iterator();
      for (;;)
      {
        if (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          zzcf localzzcf = (zzcf)localEntry.getValue();
          try
          {
            localzzbb.zza(localzzw, new AddListenerRequest(localzzcf));
            if (Log.isLoggable("WearableClient", 2)) {
              Log.d("WearableClient", "onPostInitHandler: added: " + localEntry.getKey() + "/" + localzzcf);
            }
          }
          catch (RemoteException localRemoteException)
          {
            Log.d("WearableClient", "onPostInitHandler: Didn't add: " + localEntry.getKey() + "/" + localzzcf);
          }
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbl
 * JD-Core Version:    0.7.0.1
 */