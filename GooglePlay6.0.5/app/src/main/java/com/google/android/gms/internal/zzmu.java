package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzx;
import java.util.concurrent.atomic.AtomicReference;

public final class zzmu
{
  public static abstract class zza<R extends Result, A extends Api.zzb>
    extends zzmv<R>
    implements zzmu.zzb<R>, zznd.zze<A>
  {
    final Api.zzc<A> zzalR;
    private AtomicReference<zznd.zzd> zzaoE = new AtomicReference();
    
    public zza(Api.zzc<A> paramzzc, GoogleApiClient paramGoogleApiClient)
    {
      super();
      this.zzalR = ((Api.zzc)zzx.zzC(paramzzc));
    }
    
    private void zzc(RemoteException paramRemoteException)
    {
      zzE(new Status(8, paramRemoteException.getLocalizedMessage(), null));
    }
    
    public final void zzE(Status paramStatus)
    {
      if (!paramStatus.isSuccess()) {}
      for (boolean bool = true;; bool = false)
      {
        zzx.zzb(bool, "Failed result must not be success");
        zza(zzb(paramStatus));
        return;
      }
    }
    
    public abstract void zza(A paramA)
      throws RemoteException;
    
    public final void zza(zznd.zzd paramzzd)
    {
      this.zzaoE.set(paramzzd);
    }
    
    public final void zzb(A paramA)
      throws DeadObjectException
    {
      try
      {
        zza(paramA);
        return;
      }
      catch (DeadObjectException localDeadObjectException)
      {
        zzc(localDeadObjectException);
        throw localDeadObjectException;
      }
      catch (RemoteException localRemoteException)
      {
        zzc(localRemoteException);
      }
    }
    
    public final void zzoF()
    {
      setResultCallback(null);
    }
    
    protected final void zzoH()
    {
      zznd.zzd localzzd = (zznd.zzd)this.zzaoE.getAndSet(null);
      if (localzzd != null) {
        localzzd.zzc(this);
      }
    }
    
    public final Api.zzc<A> zzor()
    {
      return this.zzalR;
    }
  }
  
  public static abstract interface zzb<R>
  {
    public abstract void zzu(R paramR);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzmu
 * JD-Core Version:    0.7.0.1
 */