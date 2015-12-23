package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.zza;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzf.zza;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzp.zza;
import com.google.android.gms.common.internal.zzt.zza;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.signin.internal.AuthAccountResult;
import com.google.android.gms.signin.internal.zzb;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class zznb
  implements zzne
{
  final Context mContext;
  final Lock zzXP;
  private final GoogleApiAvailability zzaon;
  private final Api.zza<? extends zzwz, zzxa> zzaoo;
  final zznf zzape;
  private ConnectionResult zzapg;
  private int zzaph;
  private int zzapi = 0;
  private boolean zzapj = false;
  private int zzapk;
  private final Bundle zzapl = new Bundle();
  private final Set<Api.zzc> zzapm = new HashSet();
  zzwz zzapn;
  private int zzapo;
  boolean zzapp;
  boolean zzapq;
  zzp zzapr;
  boolean zzaps;
  boolean zzapt;
  private final zzf zzapu;
  private final Map<Api<?>, Integer> zzapv;
  private ArrayList<Future<?>> zzapw = new ArrayList();
  
  public zznb(zznf paramzznf, zzf paramzzf, Map<Api<?>, Integer> paramMap, GoogleApiAvailability paramGoogleApiAvailability, Api.zza<? extends zzwz, zzxa> paramzza, Lock paramLock, Context paramContext)
  {
    this.zzape = paramzznf;
    this.zzapu = paramzzf;
    this.zzapv = paramMap;
    this.zzaon = paramGoogleApiAvailability;
    this.zzaoo = paramzza;
    this.zzXP = paramLock;
    this.mContext = paramContext;
  }
  
  private void zzZ(boolean paramBoolean)
  {
    if (this.zzapn != null)
    {
      if ((this.zzapn.isConnected()) && (paramBoolean)) {
        this.zzapn.zzIZ();
      }
      this.zzapn.disconnect();
      this.zzapr = null;
    }
  }
  
  private static String zzcO(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "UNKNOWN";
    case 0: 
      return "STEP_GETTING_SERVICE_BINDINGS";
    case 1: 
      return "STEP_VALIDATING_ACCOUNT";
    case 2: 
      return "STEP_AUTHENTICATING";
    }
    return "STEP_GETTING_REMOTE_SERVICE";
  }
  
  private void zzoY()
  {
    zznf localzznf = this.zzape;
    localzznf.zzXP.lock();
    try
    {
      localzznf.zzaoR.zzpi();
      localzznf.zzaqk = new zzna(localzznf);
      localzznf.zzaqk.begin();
      localzznf.zzaqh.signalAll();
      localzznf.zzXP.unlock();
      zzng.zzpp().execute(new Runnable()
      {
        public final void run()
        {
          GoogleApiAvailability.zzam(zznb.this.mContext);
        }
      });
      if (this.zzapn != null)
      {
        if (this.zzaps) {
          this.zzapn.zza(this.zzapr, this.zzapt);
        }
        zzZ(false);
      }
      Iterator localIterator = this.zzape.zzaqj.keySet().iterator();
      while (localIterator.hasNext())
      {
        Api.zzc localzzc = (Api.zzc)localIterator.next();
        ((Api.zzb)this.zzape.zzapQ.get(localzzc)).disconnect();
      }
      localBundle = null;
    }
    finally
    {
      localzznf.zzXP.unlock();
    }
    for (;;)
    {
      this.zzape.zzaqn.zzo(localBundle);
      if (this.zzapj)
      {
        this.zzapj = false;
        disconnect();
      }
      return;
      Bundle localBundle = this.zzapl;
    }
  }
  
  private void zzpa()
  {
    Iterator localIterator = this.zzapw.iterator();
    while (localIterator.hasNext()) {
      ((Future)localIterator.next()).cancel(true);
    }
    this.zzapw.clear();
  }
  
  public final void begin()
  {
    this.zzape.zzaqj.clear();
    this.zzapj = false;
    this.zzapp = false;
    this.zzapg = null;
    this.zzapi = 0;
    this.zzapo = 2;
    this.zzapq = false;
    this.zzaps = false;
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.zzapv.keySet().iterator();
    while (localIterator.hasNext())
    {
      Api localApi = (Api)localIterator.next();
      Api.zzb localzzb = (Api.zzb)this.zzape.zzapQ.get(localApi.zzor());
      int i = ((Integer)this.zzapv.get(localApi)).intValue();
      localApi.zzop();
      if (localzzb.zzkc())
      {
        this.zzapp = true;
        if (i < this.zzapo) {
          this.zzapo = i;
        }
        if (i != 0) {
          this.zzapm.add(localApi.zzor());
        }
      }
      localHashMap.put(localzzb, new zzd(this, localApi, i));
    }
    if (this.zzapp)
    {
      this.zzapu.zzaty = Integer.valueOf(System.identityHashCode(this.zzape.zzaoR));
      zzg localzzg = new zzg((byte)0);
      this.zzapn = ((zzwz)this.zzaoo.zza(this.mContext, this.zzape.zzaoR.zzoD, this.zzapu, this.zzapu.zzaor, localzzg, localzzg));
    }
    this.zzapk = this.zzape.zzapQ.size();
    this.zzapw.add(zzng.zzpp().submit(new zze(localHashMap)));
  }
  
  public final void connect()
  {
    this.zzapj = false;
  }
  
  public final void disconnect()
  {
    Iterator localIterator = this.zzape.zzaoR.zzapK.iterator();
    while (localIterator.hasNext())
    {
      ((zznd.zze)localIterator.next()).cancel();
      localIterator.remove();
    }
    if ((this.zzapg == null) && (!this.zzape.zzaoR.zzapK.isEmpty()))
    {
      this.zzapj = true;
      return;
    }
    zzpa();
    zzZ(true);
    this.zzape.zzj(null);
  }
  
  public final void onConnected(Bundle paramBundle)
  {
    if (!zzcN(3)) {}
    do
    {
      return;
      if (paramBundle != null) {
        this.zzapl.putAll(paramBundle);
      }
    } while (!zzoT());
    zzoY();
  }
  
  public final void onConnectionSuspended(int paramInt)
  {
    zzi(new ConnectionResult(8, null));
  }
  
  public final <A extends Api.zzb, R extends Result, T extends zzmu.zza<R, A>> T zza(T paramT)
  {
    this.zzape.zzaoR.zzapK.add(paramT);
    return paramT;
  }
  
  public final void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt)
  {
    if (!zzcN(3)) {}
    do
    {
      return;
      zzb(paramConnectionResult, paramApi, paramInt);
    } while (!zzoT());
    zzoY();
  }
  
  public final <A extends Api.zzb, T extends zzmu.zza<? extends Result, A>> T zzb(T paramT)
  {
    throw new IllegalStateException("GoogleApiClient is not connected yet.");
  }
  
  final void zzb(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt)
  {
    int i = 1;
    int j;
    if (paramInt != 2)
    {
      paramApi.zzop();
      if (paramInt == i)
      {
        if (!paramConnectionResult.hasResolution()) {
          break label88;
        }
        j = i;
        if (j == 0) {
          break label113;
        }
      }
      if ((this.zzapg != null) && (2147483647 >= this.zzaph)) {
        break label113;
      }
    }
    for (;;)
    {
      if (i != 0)
      {
        this.zzapg = paramConnectionResult;
        this.zzaph = 2147483647;
      }
      this.zzape.zzaqj.put(paramApi.zzor(), paramConnectionResult);
      return;
      label88:
      if (GoogleApiAvailability.getErrorResolutionIntent(null, paramConnectionResult.zzakr, null) != null)
      {
        j = i;
        break;
      }
      j = 0;
      break;
      label113:
      i = 0;
    }
  }
  
  final boolean zzcN(int paramInt)
  {
    if (this.zzapi != paramInt)
    {
      Log.i("GoogleApiClientConnecting", this.zzape.zzaoR.zzpj());
      Log.wtf("GoogleApiClientConnecting", "GoogleApiClient connecting is in step " + zzcO(this.zzapi) + " but received callback for step " + zzcO(paramInt), new Exception());
      zzi(new ConnectionResult(8, null));
      return false;
    }
    return true;
  }
  
  final boolean zzh(ConnectionResult paramConnectionResult)
  {
    return (this.zzapo == 2) || ((this.zzapo == 1) && (!paramConnectionResult.hasResolution()));
  }
  
  final void zzi(ConnectionResult paramConnectionResult)
  {
    zzpa();
    if (!paramConnectionResult.hasResolution()) {}
    for (boolean bool = true;; bool = false)
    {
      zzZ(bool);
      this.zzape.zzj(paramConnectionResult);
      if (!this.zzapj) {
        this.zzape.zzaqn.zze(paramConnectionResult);
      }
      this.zzapj = false;
      return;
    }
  }
  
  final boolean zzoT()
  {
    this.zzapk = (-1 + this.zzapk);
    if (this.zzapk > 0) {
      return false;
    }
    if (this.zzapk < 0)
    {
      Log.i("GoogleApiClientConnecting", this.zzape.zzaoR.zzpj());
      Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
      zzi(new ConnectionResult(8, null));
      return false;
    }
    if (this.zzapg != null)
    {
      this.zzape.zzaqm = this.zzaph;
      zzi(this.zzapg);
      return false;
    }
    return true;
  }
  
  final void zzoU()
  {
    if (this.zzapk != 0) {}
    ArrayList localArrayList;
    do
    {
      do
      {
        return;
        if (!this.zzapp) {
          break;
        }
      } while (!this.zzapq);
      localArrayList = new ArrayList();
      this.zzapi = 1;
      this.zzapk = this.zzape.zzapQ.size();
      Iterator localIterator = this.zzape.zzapQ.keySet().iterator();
      while (localIterator.hasNext())
      {
        Api.zzc localzzc = (Api.zzc)localIterator.next();
        if (this.zzape.zzaqj.containsKey(localzzc))
        {
          if (zzoT()) {
            zzoW();
          }
        }
        else {
          localArrayList.add(this.zzape.zzapQ.get(localzzc));
        }
      }
    } while (localArrayList.isEmpty());
    this.zzapw.add(zzng.zzpp().submit(new zzh(localArrayList)));
    return;
    zzoX();
  }
  
  final void zzoW()
  {
    this.zzapi = 2;
    this.zzape.zzaoR.zzapR = zzpb();
    this.zzapw.add(zzng.zzpp().submit(new zzc((byte)0)));
  }
  
  final void zzoX()
  {
    ArrayList localArrayList = new ArrayList();
    this.zzapi = 3;
    this.zzapk = this.zzape.zzapQ.size();
    Iterator localIterator = this.zzape.zzapQ.keySet().iterator();
    while (localIterator.hasNext())
    {
      Api.zzc localzzc = (Api.zzc)localIterator.next();
      if (this.zzape.zzaqj.containsKey(localzzc))
      {
        if (zzoT()) {
          zzoY();
        }
      }
      else {
        localArrayList.add(this.zzape.zzapQ.get(localzzc));
      }
    }
    if (!localArrayList.isEmpty()) {
      this.zzapw.add(zzng.zzpp().submit(new zzf(localArrayList)));
    }
  }
  
  final void zzoZ()
  {
    this.zzapp = false;
    this.zzape.zzaoR.zzapR = Collections.emptySet();
    Iterator localIterator = this.zzapm.iterator();
    while (localIterator.hasNext())
    {
      Api.zzc localzzc = (Api.zzc)localIterator.next();
      if (!this.zzape.zzaqj.containsKey(localzzc)) {
        this.zzape.zzaqj.put(localzzc, new ConnectionResult(17, null));
      }
    }
  }
  
  final Set<Scope> zzpb()
  {
    if (this.zzapu == null) {
      return Collections.emptySet();
    }
    HashSet localHashSet = new HashSet(this.zzapu.zzaod);
    Map localMap = this.zzapu.zzatx;
    Iterator localIterator = localMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Api localApi = (Api)localIterator.next();
      if (!this.zzape.zzaqj.containsKey(localApi.zzor())) {
        localHashSet.addAll(((zzf.zza)localMap.get(localApi)).zzXp);
      }
    }
    return localHashSet;
  }
  
  private static final class zza
    extends zzb
  {
    private final WeakReference<zznb> zzapy;
    
    zza(zznb paramzznb)
    {
      this.zzapy = new WeakReference(paramzznb);
    }
    
    public final void zza(final ConnectionResult paramConnectionResult, AuthAccountResult paramAuthAccountResult)
    {
      final zznb localzznb = (zznb)this.zzapy.get();
      if (localzznb == null) {
        return;
      }
      localzznb.zzape.zza(new zznf.zza(localzznb)
      {
        public final void zzoS()
        {
          zznb localzznb = localzznb;
          ConnectionResult localConnectionResult = paramConnectionResult;
          if (localzznb.zzcN(2))
          {
            if (localConnectionResult.isSuccess()) {
              localzznb.zzoX();
            }
          }
          else {
            return;
          }
          if (localzznb.zzh(localConnectionResult))
          {
            localzznb.zzoZ();
            localzznb.zzoX();
            return;
          }
          localzznb.zzi(localConnectionResult);
        }
      });
    }
  }
  
  private static final class zzb
    extends zzt.zza
  {
    private final WeakReference<zznb> zzapy;
    
    zzb(zznb paramzznb)
    {
      this.zzapy = new WeakReference(paramzznb);
    }
    
    public final void zzb(final ResolveAccountResponse paramResolveAccountResponse)
    {
      final zznb localzznb = (zznb)this.zzapy.get();
      if (localzznb == null) {
        return;
      }
      localzznb.zzape.zza(new zznf.zza(localzznb)
      {
        public final void zzoS()
        {
          zznb localzznb = localzznb;
          ResolveAccountResponse localResolveAccountResponse = paramResolveAccountResponse;
          ConnectionResult localConnectionResult;
          if (localzznb.zzcN(0))
          {
            localConnectionResult = localResolveAccountResponse.zzauE;
            if (localConnectionResult.isSuccess())
            {
              localzznb.zzapr = zzp.zza.zzcj(localResolveAccountResponse.zzasY);
              localzznb.zzapq = true;
              localzznb.zzaps = localResolveAccountResponse.zzaps;
              localzznb.zzapt = localResolveAccountResponse.zzauF;
              localzznb.zzoU();
            }
          }
          else
          {
            return;
          }
          if (localzznb.zzh(localConnectionResult))
          {
            localzznb.zzoZ();
            localzznb.zzoU();
            return;
          }
          localzznb.zzi(localConnectionResult);
        }
      });
    }
  }
  
  private final class zzc
    extends zznb.zzi
  {
    private zzc()
    {
      super((byte)0);
    }
    
    public final void zzoS()
    {
      zznb.this.zzapn.zza(zznb.this.zzapr, zznb.this.zzape.zzaoR.zzapR, new zznb.zza(zznb.this));
    }
  }
  
  private static final class zzd
    implements GoogleApiClient.zza
  {
    private final Api<?> zzaoO;
    private final int zzaoP;
    private final WeakReference<zznb> zzapy;
    
    public zzd(zznb paramzznb, Api<?> paramApi, int paramInt)
    {
      this.zzapy = new WeakReference(paramzznb);
      this.zzaoO = paramApi;
      this.zzaoP = paramInt;
    }
    
    public final void zza(ConnectionResult paramConnectionResult)
    {
      zznb localzznb = (zznb)this.zzapy.get();
      if (localzznb == null) {
        return;
      }
      Looper localLooper1 = Looper.myLooper();
      Looper localLooper2 = localzznb.zzape.zzaoR.zzoD;
      boolean bool1 = false;
      if (localLooper1 == localLooper2) {
        bool1 = true;
      }
      zzx.zza(bool1, "onReportServiceBinding must be called on the GoogleApiClient handler thread");
      localzznb.zzXP.lock();
      try
      {
        boolean bool2 = localzznb.zzcN(0);
        if (!bool2) {
          return;
        }
        if (!paramConnectionResult.isSuccess()) {
          localzznb.zzb(paramConnectionResult, this.zzaoO, this.zzaoP);
        }
        if (localzznb.zzoT()) {
          localzznb.zzoU();
        }
        return;
      }
      finally
      {
        localzznb.zzXP.unlock();
      }
    }
    
    public final void zzb(ConnectionResult paramConnectionResult)
    {
      boolean bool1 = true;
      zznb localzznb = (zznb)this.zzapy.get();
      if (localzznb == null) {
        return;
      }
      if (Looper.myLooper() == localzznb.zzape.zzaoR.zzoD) {}
      for (;;)
      {
        zzx.zza(bool1, "onReportAccountValidation must be called on the GoogleApiClient handler thread");
        localzznb.zzXP.lock();
        try
        {
          boolean bool2 = localzznb.zzcN(1);
          if (!bool2)
          {
            return;
            bool1 = false;
            continue;
          }
          if (!paramConnectionResult.isSuccess()) {
            localzznb.zzb(paramConnectionResult, this.zzaoO, this.zzaoP);
          }
          if (localzznb.zzoT()) {
            localzznb.zzoW();
          }
          return;
        }
        finally
        {
          localzznb.zzXP.unlock();
        }
      }
    }
  }
  
  private final class zze
    extends zznb.zzi
  {
    private final Map<Api.zzb, GoogleApiClient.zza> zzapE;
    
    public zze()
    {
      super((byte)0);
      Object localObject;
      this.zzapE = localObject;
    }
    
    public final void zzoS()
    {
      int i = GoogleApiAvailability.isGooglePlayServicesAvailable(zznb.this.mContext);
      if (i != 0)
      {
        final ConnectionResult localConnectionResult = new ConnectionResult(i, null);
        zznb.this.zzape.zza(new zznf.zza(zznb.this)
        {
          public final void zzoS()
          {
            zznb.this.zzi(localConnectionResult);
          }
        });
      }
      for (;;)
      {
        return;
        if (zznb.this.zzapp) {
          zznb.this.zzapn.connect();
        }
        Iterator localIterator = this.zzapE.keySet().iterator();
        while (localIterator.hasNext())
        {
          Api.zzb localzzb = (Api.zzb)localIterator.next();
          localzzb.zza((GoogleApiClient.zza)this.zzapE.get(localzzb));
        }
      }
    }
  }
  
  private final class zzf
    extends zznb.zzi
  {
    private final ArrayList<Api.zzb> zzapH;
    
    public zzf()
    {
      super((byte)0);
      Object localObject;
      this.zzapH = localObject;
    }
    
    public final void zzoS()
    {
      if (zznb.this.zzape.zzaoR.zzapR.isEmpty()) {
        zznb.this.zzape.zzaoR.zzapR = zznb.this.zzpb();
      }
      Iterator localIterator = this.zzapH.iterator();
      while (localIterator.hasNext()) {
        ((Api.zzb)localIterator.next()).zza(zznb.this.zzapr, zznb.this.zzape.zzaoR.zzapR);
      }
    }
  }
  
  private final class zzg
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
  {
    private zzg() {}
    
    public final void onConnected(Bundle paramBundle)
    {
      zznb.this.zzapn.zza(new zznb.zzb(zznb.this));
    }
    
    /* Error */
    public final void onConnectionFailed(ConnectionResult paramConnectionResult)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 14	com/google/android/gms/internal/zznb$zzg:zzapx	Lcom/google/android/gms/internal/zznb;
      //   4: getfield 43	com/google/android/gms/internal/zznb:zzXP	Ljava/util/concurrent/locks/Lock;
      //   7: invokeinterface 48 1 0
      //   12: aload_0
      //   13: getfield 14	com/google/android/gms/internal/zznb$zzg:zzapx	Lcom/google/android/gms/internal/zznb;
      //   16: aload_1
      //   17: invokevirtual 52	com/google/android/gms/internal/zznb:zzh	(Lcom/google/android/gms/common/ConnectionResult;)Z
      //   20: ifeq +30 -> 50
      //   23: aload_0
      //   24: getfield 14	com/google/android/gms/internal/zznb$zzg:zzapx	Lcom/google/android/gms/internal/zznb;
      //   27: invokevirtual 55	com/google/android/gms/internal/zznb:zzoZ	()V
      //   30: aload_0
      //   31: getfield 14	com/google/android/gms/internal/zznb$zzg:zzapx	Lcom/google/android/gms/internal/zznb;
      //   34: invokevirtual 58	com/google/android/gms/internal/zznb:zzoX	()V
      //   37: aload_0
      //   38: getfield 14	com/google/android/gms/internal/zznb$zzg:zzapx	Lcom/google/android/gms/internal/zznb;
      //   41: getfield 43	com/google/android/gms/internal/zznb:zzXP	Ljava/util/concurrent/locks/Lock;
      //   44: invokeinterface 61 1 0
      //   49: return
      //   50: aload_0
      //   51: getfield 14	com/google/android/gms/internal/zznb$zzg:zzapx	Lcom/google/android/gms/internal/zznb;
      //   54: aload_1
      //   55: invokevirtual 64	com/google/android/gms/internal/zznb:zzi	(Lcom/google/android/gms/common/ConnectionResult;)V
      //   58: goto -21 -> 37
      //   61: astore_2
      //   62: aload_0
      //   63: getfield 14	com/google/android/gms/internal/zznb$zzg:zzapx	Lcom/google/android/gms/internal/zznb;
      //   66: getfield 43	com/google/android/gms/internal/zznb:zzXP	Ljava/util/concurrent/locks/Lock;
      //   69: invokeinterface 61 1 0
      //   74: aload_2
      //   75: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	76	0	this	zzg
      //   0	76	1	paramConnectionResult	ConnectionResult
      //   61	14	2	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   12	37	61	finally
      //   50	58	61	finally
    }
    
    public final void onConnectionSuspended(int paramInt) {}
  }
  
  private final class zzh
    extends zznb.zzi
  {
    private final ArrayList<Api.zzb> zzapH;
    
    public zzh()
    {
      super((byte)0);
      Object localObject;
      this.zzapH = localObject;
    }
    
    public final void zzoS()
    {
      Iterator localIterator = this.zzapH.iterator();
      while (localIterator.hasNext()) {
        ((Api.zzb)localIterator.next()).zza(zznb.this.zzapr);
      }
    }
  }
  
  private abstract class zzi
    implements Runnable
  {
    private zzi() {}
    
    public void run()
    {
      zznb.this.zzXP.lock();
      try
      {
        boolean bool = Thread.interrupted();
        if (bool) {
          return;
        }
        zzoS();
        return;
      }
      catch (RuntimeException localRuntimeException)
      {
        zznf localzznf = zznb.this.zzape;
        Message localMessage = localzznf.zzaqi.obtainMessage(2, localRuntimeException);
        localzznf.zzaqi.sendMessage(localMessage);
        return;
      }
      finally
      {
        zznb.this.zzXP.unlock();
      }
    }
    
    protected abstract void zzoS();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznb
 * JD-Core Version:    0.7.0.1
 */