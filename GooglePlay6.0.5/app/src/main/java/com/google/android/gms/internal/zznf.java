package com.google.android.gms.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzf;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class zznf
  implements zznj
{
  final Context mContext;
  final Lock zzXP;
  final zznd zzaoR;
  final GoogleApiAvailability zzaon;
  final Api.zza<? extends zzwz, zzxa> zzaoo;
  final Map<Api.zzc<?>, Api.zzb> zzapQ;
  final zzf zzapu;
  final Map<Api<?>, Integer> zzapv;
  final Condition zzaqh;
  final zzb zzaqi;
  final Map<Api.zzc<?>, ConnectionResult> zzaqj = new HashMap();
  volatile zzne zzaqk;
  private ConnectionResult zzaql = null;
  int zzaqm;
  final zznj.zza zzaqn;
  
  public zznf(Context paramContext, zznd paramzznd, Lock paramLock, Looper paramLooper, GoogleApiAvailability paramGoogleApiAvailability, Map<Api.zzc<?>, Api.zzb> paramMap, zzf paramzzf, Map<Api<?>, Integer> paramMap1, Api.zza<? extends zzwz, zzxa> paramzza, ArrayList<zzmw> paramArrayList, zznj.zza paramzza1)
  {
    this.mContext = paramContext;
    this.zzXP = paramLock;
    this.zzaon = paramGoogleApiAvailability;
    this.zzapQ = paramMap;
    this.zzapu = paramzzf;
    this.zzapv = paramMap1;
    this.zzaoo = paramzza;
    this.zzaoR = paramzznd;
    this.zzaqn = paramzza1;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext()) {
      ((zzmw)localIterator.next()).zzaoQ = this;
    }
    this.zzaqi = new zzb(paramLooper);
    this.zzaqh = paramLock.newCondition();
    this.zzaqk = new zznc(this);
  }
  
  public final ConnectionResult blockingConnect()
  {
    connect();
    while (isConnecting()) {
      try
      {
        this.zzaqh.await();
      }
      catch (InterruptedException localInterruptedException)
      {
        Thread.currentThread().interrupt();
        return new ConnectionResult(15, null);
      }
    }
    if (isConnected()) {
      return ConnectionResult.zzanu;
    }
    if (this.zzaql != null) {
      return this.zzaql;
    }
    return new ConnectionResult(13, null);
  }
  
  public final ConnectionResult blockingConnect(long paramLong, TimeUnit paramTimeUnit)
  {
    connect();
    for (long l1 = paramTimeUnit.toNanos(paramLong); isConnecting(); l1 = l2)
    {
      if (l1 <= 0L) {}
      try
      {
        return new ConnectionResult(14, null);
      }
      catch (InterruptedException localInterruptedException)
      {
        long l2;
        Thread.currentThread().interrupt();
        return new ConnectionResult(15, null);
      }
      l2 = this.zzaqh.awaitNanos(l1);
    }
    if (isConnected()) {
      return ConnectionResult.zzanu;
    }
    if (this.zzaql != null) {
      return this.zzaql;
    }
    return new ConnectionResult(13, null);
  }
  
  public final void connect()
  {
    this.zzaqk.connect();
  }
  
  public final void disconnect()
  {
    this.zzaqj.clear();
    this.zzaqk.disconnect();
  }
  
  public final void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    String str = paramString + "  ";
    Iterator localIterator = this.zzapv.keySet().iterator();
    while (localIterator.hasNext())
    {
      Api localApi = (Api)localIterator.next();
      paramPrintWriter.append(paramString).append(localApi.mName).println(":");
      ((Api.zzb)this.zzapQ.get(localApi.zzor())).dump$ec96877(str, paramPrintWriter);
    }
  }
  
  public final boolean isConnected()
  {
    return this.zzaqk instanceof zzna;
  }
  
  public final boolean isConnecting()
  {
    return this.zzaqk instanceof zznb;
  }
  
  public final void onConnectionSuspended(int paramInt)
  {
    this.zzXP.lock();
    try
    {
      this.zzaqk.onConnectionSuspended(paramInt);
      return;
    }
    finally
    {
      this.zzXP.unlock();
    }
  }
  
  public final <A extends Api.zzb, R extends Result, T extends zzmu.zza<R, A>> T zza(T paramT)
  {
    return this.zzaqk.zza(paramT);
  }
  
  final void zza(zza paramzza)
  {
    Message localMessage = this.zzaqi.obtainMessage(1, paramzza);
    this.zzaqi.sendMessage(localMessage);
  }
  
  public final <A extends Api.zzb, T extends zzmu.zza<? extends Result, A>> T zzb(T paramT)
  {
    return this.zzaqk.zzb(paramT);
  }
  
  final void zzj(ConnectionResult paramConnectionResult)
  {
    this.zzXP.lock();
    try
    {
      this.zzaql = paramConnectionResult;
      this.zzaqk = new zznc(this);
      this.zzaqk.begin();
      this.zzaqh.signalAll();
      return;
    }
    finally
    {
      this.zzXP.unlock();
    }
  }
  
  static abstract class zza
  {
    private final zzne zzaqo;
    
    protected zza(zzne paramzzne)
    {
      this.zzaqo = paramzzne;
    }
    
    public final void zzd(zznf paramzznf)
    {
      paramzznf.zzXP.lock();
      try
      {
        zzne localzzne1 = paramzznf.zzaqk;
        zzne localzzne2 = this.zzaqo;
        if (localzzne1 != localzzne2) {
          return;
        }
        zzoS();
        return;
      }
      finally
      {
        paramzznf.zzXP.unlock();
      }
    }
    
    protected abstract void zzoS();
  }
  
  final class zzb
    extends Handler
  {
    zzb(Looper paramLooper)
    {
      super();
    }
    
    public final void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default: 
        Log.w("GACStateManager", "Unknown message id: " + paramMessage.what);
        return;
      case 1: 
        ((zznf.zza)paramMessage.obj).zzd(zznf.this);
        return;
      }
      throw ((RuntimeException)paramMessage.obj);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznf
 * JD-Core Version:    0.7.0.1
 */