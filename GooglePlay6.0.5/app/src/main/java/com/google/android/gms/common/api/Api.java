package com.google.android.gms.common.api;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzx;
import java.io.PrintWriter;
import java.util.Set;

public final class Api<O extends ApiOptions>
{
  public final String mName;
  private final zzc<?> zzalR;
  private final zza<?, O> zzanS;
  final zze<?, O> zzanT;
  final zzf<?> zzanU;
  
  public <C extends zzb> Api(String paramString, zza<C, O> paramzza, zzc<C> paramzzc)
  {
    zzx.zzb(paramzza, "Cannot construct an Api with a null ClientBuilder");
    zzx.zzb(paramzzc, "Cannot construct an Api with a null ClientKey");
    this.mName = paramString;
    this.zzanS = paramzza;
    this.zzanT = null;
    this.zzalR = paramzzc;
    this.zzanU = null;
  }
  
  public final zza<?, O> zzop()
  {
    if (this.zzanS != null) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zza(bool, "This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
      return this.zzanS;
    }
  }
  
  public final zzc<?> zzor()
  {
    if (this.zzalR != null) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zza(bool, "This API was constructed with a SimpleClientKey. Use getSimpleClientKey");
      return this.zzalR;
    }
  }
  
  public static abstract interface ApiOptions
  {
    public static abstract interface HasOptions
      extends Api.ApiOptions
    {}
    
    public static abstract interface NotRequiredOptions
      extends Api.ApiOptions
    {}
    
    public static abstract interface Optional
      extends Api.ApiOptions.HasOptions, Api.ApiOptions.NotRequiredOptions
    {}
  }
  
  public static abstract class zza<T extends Api.zzb, O>
  {
    public abstract T zza(Context paramContext, Looper paramLooper, zzf paramzzf, O paramO, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener);
  }
  
  public static abstract interface zzb
  {
    public abstract void disconnect();
    
    public abstract void dump$ec96877(String paramString, PrintWriter paramPrintWriter);
    
    public abstract boolean isConnected();
    
    public abstract void zza(GoogleApiClient.zza paramzza);
    
    public abstract void zza(zzp paramzzp);
    
    public abstract void zza(zzp paramzzp, Set<Scope> paramSet);
    
    public abstract boolean zzkc();
    
    public abstract Intent zzks();
    
    public abstract IBinder zzot();
  }
  
  public static final class zzc<C extends Api.zzb> {}
  
  public static abstract interface zzd<T extends IInterface>
  {
    public abstract T zzaa$13514312();
    
    public abstract String zzgp();
    
    public abstract String zzgq();
  }
  
  public static abstract interface zze<T extends Api.zzd, O>
  {
    public abstract int zzou();
    
    public abstract T zzs$55e35557();
  }
  
  public static final class zzf<C extends Api.zzd> {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.Api
 * JD-Core Version:    0.7.0.1
 */