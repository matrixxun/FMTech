package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import com.google.android.gms.common.stats.zzb;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class zzm
  extends zzl
  implements Handler.Callback
{
  private final Handler mHandler;
  private final HashMap<zza, zzb> zzaum = new HashMap();
  private final zzb zzaun;
  private final long zzauo;
  private final Context zzsn;
  
  zzm(Context paramContext)
  {
    this.zzsn = paramContext.getApplicationContext();
    this.mHandler = new Handler(paramContext.getMainLooper(), this);
    this.zzaun = zzb.zzrf();
    this.zzauo = 5000L;
  }
  
  private boolean zza(zza paramzza, ServiceConnection paramServiceConnection, String paramString)
  {
    zzx.zzb(paramServiceConnection, "ServiceConnection must not be null");
    for (;;)
    {
      zzb localzzb;
      synchronized (this.zzaum)
      {
        localzzb = (zzb)this.zzaum.get(paramzza);
        if (localzzb == null)
        {
          localzzb = new zzb(paramzza);
          localzzb.zza(paramServiceConnection, paramString);
          localzzb.zzcB(paramString);
          this.zzaum.put(paramzza, localzzb);
          boolean bool = localzzb.zzaur;
          return bool;
        }
        this.mHandler.removeMessages(0, localzzb);
        if (localzzb.zza(paramServiceConnection)) {
          throw new IllegalStateException("Trying to bind a GmsServiceConnection that was already connected before.  config=" + paramzza);
        }
      }
      localzzb.zza(paramServiceConnection, paramString);
      switch (localzzb.mState)
      {
      case 1: 
        paramServiceConnection.onServiceConnected(localzzb.zzace, localzzb.zzpt);
        break;
      case 2: 
        localzzb.zzcB(paramString);
      }
    }
  }
  
  private void zzb$37710f84(zza paramzza, ServiceConnection paramServiceConnection)
  {
    zzx.zzb(paramServiceConnection, "ServiceConnection must not be null");
    zzb localzzb;
    synchronized (this.zzaum)
    {
      localzzb = (zzb)this.zzaum.get(paramzza);
      if (localzzb == null) {
        throw new IllegalStateException("Nonexistent connection status for service config: " + paramzza);
      }
    }
    if (!localzzb.zza(paramServiceConnection)) {
      throw new IllegalStateException("Trying to unbind a GmsServiceConnection  that was not bound before.  config=" + paramzza);
    }
    localzzb.zzaut.zzaun.zza(localzzb.zzaut.zzsn, zzb.zzb(paramServiceConnection), null, null, 4);
    localzzb.zzauq.remove(paramServiceConnection);
    if (localzzb.zzqy())
    {
      Message localMessage = this.mHandler.obtainMessage(0, localzzb);
      this.mHandler.sendMessageDelayed(localMessage, this.zzauo);
    }
  }
  
  public final boolean handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default: 
      return false;
    }
    zzb localzzb = (zzb)paramMessage.obj;
    synchronized (this.zzaum)
    {
      if (localzzb.zzqy())
      {
        if (localzzb.zzaur)
        {
          localzzb.zzaut.zzaun.zza(localzzb.zzaut.zzsn, localzzb.zzaup);
          localzzb.zzaur = false;
          localzzb.mState = 2;
        }
        this.zzaum.remove(localzzb.zzaus);
      }
      return true;
    }
  }
  
  public final boolean zza(ComponentName paramComponentName, ServiceConnection paramServiceConnection, String paramString)
  {
    return zza(new zza(paramComponentName), paramServiceConnection, paramString);
  }
  
  public final boolean zza(String paramString1, ServiceConnection paramServiceConnection, String paramString2)
  {
    return zza(new zza(paramString1), paramServiceConnection, paramString2);
  }
  
  public final void zzb(ComponentName paramComponentName, ServiceConnection paramServiceConnection, String paramString)
  {
    zzb$37710f84(new zza(paramComponentName), paramServiceConnection);
  }
  
  public final void zzb(String paramString1, ServiceConnection paramServiceConnection, String paramString2)
  {
    zzb$37710f84(new zza(paramString1), paramServiceConnection);
  }
  
  private static final class zza
  {
    private final String mAction;
    private final ComponentName zzace;
    
    public zza(ComponentName paramComponentName)
    {
      this.mAction = null;
      this.zzace = ((ComponentName)zzx.zzC(paramComponentName));
    }
    
    public zza(String paramString)
    {
      this.mAction = zzx.zzcG(paramString);
      this.zzace = null;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      zza localzza;
      do
      {
        return true;
        if (!(paramObject instanceof zza)) {
          return false;
        }
        localzza = (zza)paramObject;
      } while ((zzw.equal(this.mAction, localzza.mAction)) && (zzw.equal(this.zzace, localzza.zzace)));
      return false;
    }
    
    public final int hashCode()
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.mAction;
      arrayOfObject[1] = this.zzace;
      return Arrays.hashCode(arrayOfObject);
    }
    
    public final String toString()
    {
      if (this.mAction == null) {
        return this.zzace.flattenToString();
      }
      return this.mAction;
    }
    
    public final Intent zzqx()
    {
      if (this.mAction != null) {
        return new Intent(this.mAction).setPackage("com.google.android.gms");
      }
      return new Intent().setComponent(this.zzace);
    }
  }
  
  private final class zzb
  {
    int mState;
    ComponentName zzace;
    final zza zzaup;
    final Set<ServiceConnection> zzauq;
    boolean zzaur;
    final zzm.zza zzaus;
    IBinder zzpt;
    
    public zzb(zzm.zza paramzza)
    {
      this.zzaus = paramzza;
      this.zzaup = new zza();
      this.zzauq = new HashSet();
      this.mState = 2;
    }
    
    public final void zza(ServiceConnection paramServiceConnection, String paramString)
    {
      zzb localzzb = zzm.zzc(zzm.this);
      Context localContext = zzm.zzb(zzm.this);
      Intent localIntent = this.zzaus.zzqx();
      localzzb.zza(localContext, zzb.zzb(paramServiceConnection), paramString, localIntent, 3);
      this.zzauq.add(paramServiceConnection);
    }
    
    public final boolean zza(ServiceConnection paramServiceConnection)
    {
      return this.zzauq.contains(paramServiceConnection);
    }
    
    public final void zzcB(String paramString)
    {
      this.mState = 3;
      this.zzaur = zzm.zzc(zzm.this).zza(zzm.zzb(zzm.this), paramString, this.zzaus.zzqx(), this.zzaup, 129);
      if (!this.zzaur) {
        this.mState = 2;
      }
      try
      {
        zzm.zzc(zzm.this).zza(zzm.zzb(zzm.this), this.zzaup);
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException) {}
    }
    
    public final boolean zzqy()
    {
      return this.zzauq.isEmpty();
    }
    
    public final class zza
      implements ServiceConnection
    {
      public zza() {}
      
      public final void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
      {
        synchronized (zzm.zza(zzm.this))
        {
          zzm.zzb.this.zzpt = paramIBinder;
          zzm.zzb.this.zzace = paramComponentName;
          Iterator localIterator = zzm.zzb.this.zzauq.iterator();
          if (localIterator.hasNext()) {
            ((ServiceConnection)localIterator.next()).onServiceConnected(paramComponentName, paramIBinder);
          }
        }
        zzm.zzb.this.mState = 1;
      }
      
      public final void onServiceDisconnected(ComponentName paramComponentName)
      {
        synchronized (zzm.zza(zzm.this))
        {
          zzm.zzb.this.zzpt = null;
          zzm.zzb.this.zzace = paramComponentName;
          Iterator localIterator = zzm.zzb.this.zzauq.iterator();
          if (localIterator.hasNext()) {
            ((ServiceConnection)localIterator.next()).onServiceDisconnected(paramComponentName);
          }
        }
        zzm.zzb.this.mState = 2;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzm
 * JD-Core Version:    0.7.0.1
 */