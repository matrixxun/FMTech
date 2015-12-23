package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultStore;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.common.internal.zzk.zza;
import com.google.android.gms.common.internal.zzx;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public final class zznd
  extends GoogleApiClient
  implements zznj.zza
{
  private final Context mContext;
  private final Lock zzXP;
  private final Set<zznl<?>> zzadc = Collections.newSetFromMap(new WeakHashMap());
  private final int zzaol;
  private final GoogleApiAvailability zzaon;
  final Api.zza<? extends zzwz, zzxa> zzaoo;
  private final zzk zzapI;
  private zznj zzapJ = null;
  final Queue<zzmu.zza<?, ?>> zzapK = new LinkedList();
  private volatile boolean zzapL;
  private long zzapM = 120000L;
  private long zzapN = 5000L;
  private final zza zzapO;
  zzc zzapP;
  final Map<Api.zzc<?>, Api.zzb> zzapQ;
  Set<Scope> zzapR = new HashSet();
  final Set<zze<?>> zzapS = Collections.newSetFromMap(new ConcurrentHashMap(16, 0.75F, 2));
  private ResultStore zzapT;
  private final ArrayList<zzmw> zzapU;
  private Integer zzapV = null;
  private final zzd zzapW = new zzd()
  {
    public final void zzc(zznd.zze<?> paramAnonymouszze)
    {
      zznd.this.zzapS.remove(paramAnonymouszze);
      if ((paramAnonymouszze.zzoC() != null) && (zznd.zza(zznd.this) != null))
      {
        ResultStore localResultStore = zznd.zza(zznd.this);
        paramAnonymouszze.zzoC().intValue();
        localResultStore.remove$13462e();
      }
    }
  };
  private final zzk.zza zzapX = new zzk.zza()
  {
    public final boolean isConnected()
    {
      return zznd.this.isConnected();
    }
  };
  final zzf zzapu;
  final Map<Api<?>, Integer> zzapv;
  final Looper zzoD;
  
  public zznd(Context paramContext, Lock paramLock, Looper paramLooper, zzf paramzzf, GoogleApiAvailability paramGoogleApiAvailability, Api.zza<? extends zzwz, zzxa> paramzza, Map<Api<?>, Integer> paramMap, List<GoogleApiClient.ConnectionCallbacks> paramList, List<GoogleApiClient.OnConnectionFailedListener> paramList1, Map<Api.zzc<?>, Api.zzb> paramMap1, int paramInt1, int paramInt2, ArrayList<zzmw> paramArrayList)
  {
    this.mContext = paramContext;
    this.zzXP = paramLock;
    this.zzapI = new zzk(paramLooper, this.zzapX);
    this.zzoD = paramLooper;
    this.zzapO = new zza(paramLooper);
    this.zzaon = paramGoogleApiAvailability;
    this.zzaol = paramInt1;
    if (this.zzaol >= 0) {
      this.zzapV = Integer.valueOf(paramInt2);
    }
    this.zzapv = paramMap;
    this.zzapQ = paramMap1;
    this.zzapU = paramArrayList;
    Iterator localIterator1 = paramList.iterator();
    while (localIterator1.hasNext())
    {
      GoogleApiClient.ConnectionCallbacks localConnectionCallbacks = (GoogleApiClient.ConnectionCallbacks)localIterator1.next();
      this.zzapI.registerConnectionCallbacks(localConnectionCallbacks);
    }
    Iterator localIterator2 = paramList1.iterator();
    while (localIterator2.hasNext())
    {
      GoogleApiClient.OnConnectionFailedListener localOnConnectionFailedListener = (GoogleApiClient.OnConnectionFailedListener)localIterator2.next();
      this.zzapI.registerConnectionFailedListener(localOnConnectionFailedListener);
    }
    this.zzapu = paramzzf;
    this.zzaoo = paramzza;
  }
  
  public static int zza$251b1977(Iterable<Api.zzb> paramIterable)
  {
    Iterator localIterator = paramIterable.iterator();
    int i = 0;
    if (localIterator.hasNext()) {
      if (!((Api.zzb)localIterator.next()).zzkc()) {
        break label50;
      }
    }
    label50:
    for (int j = 1;; j = i)
    {
      i = j;
      break;
      if (i != 0) {
        return 1;
      }
      return 3;
    }
  }
  
  private void zzcP(int paramInt)
  {
    if (this.zzapV == null) {
      this.zzapV = Integer.valueOf(paramInt);
    }
    while (this.zzapJ != null)
    {
      return;
      if (this.zzapV.intValue() != paramInt) {
        throw new IllegalStateException("Cannot use sign-in mode: " + zzcQ(paramInt) + ". Mode was already set to " + zzcQ(this.zzapV.intValue()));
      }
    }
    Iterator localIterator = this.zzapQ.values().iterator();
    int i = 0;
    if (localIterator.hasNext()) {
      if (!((Api.zzb)localIterator.next()).zzkc()) {
        break label282;
      }
    }
    label282:
    for (int j = 1;; j = i)
    {
      i = j;
      break;
      switch (this.zzapV.intValue())
      {
      }
      do
      {
        do
        {
          this.zzapJ = new zznf(this.mContext, this, this.zzXP, this.zzoD, this.zzaon, this.zzapQ, this.zzapu, this.zzapv, this.zzaoo, this.zzapU, this);
          return;
        } while (i != 0);
        throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
      } while (i == 0);
      this.zzapJ = new zzmx(this.mContext, this, this.zzXP, this.zzoD, this.zzaon, this.zzapQ, this.zzapu, this.zzapv, this.zzaoo, this.zzapU);
      return;
    }
  }
  
  private static String zzcQ(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "UNKNOWN";
    case 3: 
      return "SIGN_IN_MODE_NONE";
    case 1: 
      return "SIGN_IN_MODE_REQUIRED";
    }
    return "SIGN_IN_MODE_OPTIONAL";
  }
  
  private void zzpf()
  {
    this.zzapI.zzauh = true;
    this.zzapJ.connect();
  }
  
  public final ConnectionResult blockingConnect()
  {
    boolean bool1 = true;
    boolean bool2;
    if (Looper.myLooper() != Looper.getMainLooper()) {
      bool2 = bool1;
    }
    for (;;)
    {
      zzx.zza(bool2, "blockingConnect must not be called on the UI thread");
      this.zzXP.lock();
      try
      {
        if (this.zzaol >= 0) {
          if (this.zzapV != null) {
            label43:
            zzx.zza(bool1, "Sign-in mode should have been set explicitly by auto-manage.");
          }
        }
        do
        {
          for (;;)
          {
            zzcP(this.zzapV.intValue());
            this.zzapI.zzauh = true;
            ConnectionResult localConnectionResult = this.zzapJ.blockingConnect();
            return localConnectionResult;
            bool2 = false;
            break;
            bool1 = false;
            break label43;
            if (this.zzapV != null) {
              break label143;
            }
            this.zzapV = Integer.valueOf(zza$251b1977(this.zzapQ.values()));
          }
        } while (this.zzapV.intValue() != 2);
      }
      finally
      {
        this.zzXP.unlock();
      }
    }
    label143:
    throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
  }
  
  public final ConnectionResult blockingConnect(long paramLong, TimeUnit paramTimeUnit)
  {
    boolean bool = true;
    if (Looper.myLooper() != Looper.getMainLooper()) {}
    for (;;)
    {
      zzx.zza(bool, "blockingConnect must not be called on the UI thread");
      zzx.zzb(paramTimeUnit, "TimeUnit must not be null");
      this.zzXP.lock();
      try
      {
        if (this.zzapV == null) {
          this.zzapV = Integer.valueOf(zza$251b1977(this.zzapQ.values()));
        }
        while (this.zzapV.intValue() != 2)
        {
          zzcP(this.zzapV.intValue());
          this.zzapI.zzauh = true;
          ConnectionResult localConnectionResult = this.zzapJ.blockingConnect(paramLong, paramTimeUnit);
          return localConnectionResult;
          bool = false;
          break;
        }
        throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
      }
      finally
      {
        this.zzXP.unlock();
      }
    }
  }
  
  public final void connect()
  {
    this.zzXP.lock();
    do
    {
      for (;;)
      {
        try
        {
          if (this.zzaol >= 0) {
            if (this.zzapV != null)
            {
              bool2 = true;
              zzx.zza(bool2, "Sign-in mode should have been set explicitly by auto-manage.");
              i = this.zzapV.intValue();
              this.zzXP.lock();
              if ((i != 3) && (i != 1))
              {
                bool1 = false;
                if (i != 2) {}
              }
              else
              {
                bool1 = true;
              }
            }
          }
        }
        finally
        {
          boolean bool2;
          int i;
          boolean bool1;
          this.zzXP.unlock();
        }
        try
        {
          zzx.zzb(bool1, "Illegal sign-in mode: " + i);
          zzcP(i);
          zzpf();
          this.zzXP.unlock();
          this.zzXP.unlock();
          return;
        }
        finally
        {
          this.zzXP.unlock();
        }
        bool2 = false;
        continue;
        if (this.zzapV != null) {
          break;
        }
        this.zzapV = Integer.valueOf(zza$251b1977(this.zzapQ.values()));
      }
    } while (this.zzapV.intValue() != 2);
    throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
  }
  
  public final void disconnect()
  {
    this.zzXP.lock();
    for (;;)
    {
      zze localzze;
      try
      {
        Iterator localIterator1 = this.zzapS.iterator();
        if (!localIterator1.hasNext()) {
          break;
        }
        localzze = (zze)localIterator1.next();
        localzze.zza(null);
        if (localzze.zzoC() == null)
        {
          localzze.cancel();
          continue;
        }
        localzze.zzoF();
      }
      finally
      {
        this.zzXP.unlock();
      }
      IBinder localIBinder = zza(localzze.zzor()).zzot();
      ResultStore localResultStore = this.zzapT;
      if (localzze.isReady())
      {
        localzze.zza(new zzb(localzze, localResultStore, localIBinder, (byte)0));
      }
      else if ((localIBinder != null) && (localIBinder.isBinderAlive()))
      {
        zzb localzzb = new zzb(localzze, localResultStore, localIBinder, (byte)0);
        localzze.zza(localzzb);
        try
        {
          localIBinder.linkToDeath(localzzb, 0);
        }
        catch (RemoteException localRemoteException)
        {
          localzze.cancel();
          localzze.zzoC().intValue();
          localResultStore.remove$13462e();
        }
      }
      else
      {
        localzze.zza(null);
        localzze.cancel();
        localzze.zzoC().intValue();
        localResultStore.remove$13462e();
      }
    }
    this.zzapS.clear();
    Iterator localIterator2 = this.zzadc.iterator();
    while (localIterator2.hasNext()) {
      ((zznl)localIterator2.next()).mListener = null;
    }
    this.zzadc.clear();
    if (this.zzapJ == null)
    {
      zzpd();
      this.zzXP.unlock();
      return;
    }
    zzpi();
    this.zzapJ.disconnect();
    this.zzapI.zzqv();
    this.zzXP.unlock();
  }
  
  public final void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    paramPrintWriter.append(paramString).append("mContext=").println(this.mContext);
    paramPrintWriter.append(paramString).append("mResuming=").print(this.zzapL);
    paramPrintWriter.append(" mWorkQueue.size()=").print(this.zzapK.size());
    paramPrintWriter.append(" mUnconsumedRunners.size()=").println(this.zzapS.size());
    if (this.zzapJ != null) {
      this.zzapJ.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    }
  }
  
  public final Looper getLooper()
  {
    return this.zzoD;
  }
  
  public final boolean isConnected()
  {
    return (this.zzapJ != null) && (this.zzapJ.isConnected());
  }
  
  public final boolean isConnecting()
  {
    return (this.zzapJ != null) && (this.zzapJ.isConnecting());
  }
  
  public final void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    this.zzapI.registerConnectionCallbacks(paramConnectionCallbacks);
  }
  
  public final void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    this.zzapI.registerConnectionFailedListener(paramOnConnectionFailedListener);
  }
  
  public final void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    zzk localzzk = this.zzapI;
    zzx.zzC(paramConnectionCallbacks);
    synchronized (localzzk.zzqp)
    {
      if (!localzzk.zzaue.remove(paramConnectionCallbacks)) {
        Log.w("GmsClientEvents", "unregisterConnectionCallbacks(): listener " + paramConnectionCallbacks + " not found");
      }
      while (!localzzk.zzauj) {
        return;
      }
      localzzk.zzauf.add(paramConnectionCallbacks);
    }
  }
  
  public final void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    zzk localzzk = this.zzapI;
    zzx.zzC(paramOnConnectionFailedListener);
    synchronized (localzzk.zzqp)
    {
      if (!localzzk.zzaug.remove(paramOnConnectionFailedListener)) {
        Log.w("GmsClientEvents", "unregisterConnectionFailedListener(): listener " + paramOnConnectionFailedListener + " not found");
      }
      return;
    }
  }
  
  public final <C extends Api.zzb> C zza(Api.zzc<C> paramzzc)
  {
    Api.zzb localzzb = (Api.zzb)this.zzapQ.get(paramzzc);
    zzx.zzb(localzzb, "Appropriate Api was not requested.");
    return localzzb;
  }
  
  public final <A extends Api.zzb, R extends Result, T extends zzmu.zza<R, A>> T zza(T paramT)
  {
    boolean bool;
    if (paramT.zzalR != null) {
      bool = true;
    }
    for (;;)
    {
      zzx.zzb(bool, "This task can not be enqueued (it's probably a Batch or malformed)");
      zzx.zzb(this.zzapQ.containsKey(paramT.zzalR), "GoogleApiClient is not configured to use the API required for this call.");
      this.zzXP.lock();
      try
      {
        if (this.zzapJ == null)
        {
          this.zzapK.add(paramT);
          return paramT;
          bool = false;
          continue;
        }
        zzmu.zza localzza = this.zzapJ.zza(paramT);
        return localzza;
      }
      finally
      {
        this.zzXP.unlock();
      }
    }
  }
  
  public final <A extends Api.zzb, T extends zzmu.zza<? extends Result, A>> T zzb(T paramT)
  {
    if (paramT.zzalR != null) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zzb(bool, "This task can not be executed (it's probably a Batch or malformed)");
      this.zzXP.lock();
      try
      {
        if (this.zzapJ != null) {
          break;
        }
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
      }
      finally
      {
        this.zzXP.unlock();
      }
    }
    if (this.zzapL)
    {
      this.zzapK.add(paramT);
      while (!this.zzapK.isEmpty())
      {
        zze localzze = (zze)this.zzapK.remove();
        zzb(localzze);
        localzze.zzE(Status.zzaoB);
      }
      this.zzXP.unlock();
      return paramT;
    }
    zzmu.zza localzza = this.zzapJ.zzb(paramT);
    this.zzXP.unlock();
    return localzza;
  }
  
  final <A extends Api.zzb> void zzb(zze<A> paramzze)
  {
    this.zzapS.add(paramzze);
    paramzze.zza(this.zzapW);
  }
  
  public final void zzcM(int paramInt)
  {
    if ((paramInt == 1) && (!this.zzapL))
    {
      this.zzapL = true;
      if (this.zzapP == null) {
        this.zzapP = ((zzc)zznh.zza$645b353c(this.mContext.getApplicationContext(), new zzc(this)));
      }
      this.zzapO.sendMessageDelayed(this.zzapO.obtainMessage(1), this.zzapM);
      this.zzapO.sendMessageDelayed(this.zzapO.obtainMessage(2), this.zzapN);
    }
    Iterator localIterator1 = this.zzapS.iterator();
    while (localIterator1.hasNext()) {
      ((zze)localIterator1.next()).zzF(new Status(8, "The connection to Google Play services was lost"));
    }
    zzk localzzk = this.zzapI;
    if (Looper.myLooper() == localzzk.mHandler.getLooper()) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zza(bool, "onUnintentionalDisconnection must only be called on the Handler thread");
      localzzk.mHandler.removeMessages(1);
      synchronized (localzzk.zzqp)
      {
        localzzk.zzauj = true;
        ArrayList localArrayList = new ArrayList(localzzk.zzaue);
        int i = localzzk.zzaui.get();
        Iterator localIterator2 = localArrayList.iterator();
        GoogleApiClient.ConnectionCallbacks localConnectionCallbacks;
        do
        {
          if (!localIterator2.hasNext()) {
            break;
          }
          localConnectionCallbacks = (GoogleApiClient.ConnectionCallbacks)localIterator2.next();
          if ((!localzzk.zzauh) || (localzzk.zzaui.get() != i)) {
            break;
          }
        } while (!localzzk.zzaue.contains(localConnectionCallbacks));
        localConnectionCallbacks.onConnectionSuspended(paramInt);
      }
    }
    localzzk.zzauf.clear();
    localzzk.zzauj = false;
    this.zzapI.zzqv();
    if (paramInt == 2) {
      zzpf();
    }
  }
  
  public final void zze(ConnectionResult paramConnectionResult)
  {
    if (!GoogleApiAvailability.isPlayServicesPossiblyUpdating(this.mContext, paramConnectionResult.zzakr)) {
      zzpi();
    }
    zzk localzzk;
    boolean bool;
    if (!this.zzapL)
    {
      localzzk = this.zzapI;
      if (Looper.myLooper() != localzzk.mHandler.getLooper()) {
        break label151;
      }
      bool = true;
      zzx.zza(bool, "onConnectionFailure must only be called on the Handler thread");
      localzzk.mHandler.removeMessages(1);
    }
    for (;;)
    {
      synchronized (localzzk.zzqp)
      {
        ArrayList localArrayList = new ArrayList(localzzk.zzaug);
        int i = localzzk.zzaui.get();
        Iterator localIterator = localArrayList.iterator();
        if (localIterator.hasNext())
        {
          GoogleApiClient.OnConnectionFailedListener localOnConnectionFailedListener = (GoogleApiClient.OnConnectionFailedListener)localIterator.next();
          if ((!localzzk.zzauh) || (localzzk.zzaui.get() != i))
          {
            this.zzapI.zzqv();
            return;
            label151:
            bool = false;
            break;
          }
          if (!localzzk.zzaug.contains(localOnConnectionFailedListener)) {
            continue;
          }
          localOnConnectionFailedListener.onConnectionFailed(paramConnectionResult);
        }
      }
    }
  }
  
  public final void zzo(Bundle paramBundle)
  {
    boolean bool1 = true;
    while (!this.zzapK.isEmpty()) {
      zzb((zzmu.zza)this.zzapK.remove());
    }
    zzk localzzk = this.zzapI;
    boolean bool2;
    if (Looper.myLooper() == localzzk.mHandler.getLooper())
    {
      bool2 = bool1;
      zzx.zza(bool2, "onConnectionSuccess must only be called on the Handler thread");
    }
    for (;;)
    {
      synchronized (localzzk.zzqp)
      {
        if (localzzk.zzauj) {
          break label233;
        }
        bool3 = bool1;
        zzx.zzaa(bool3);
        localzzk.mHandler.removeMessages(1);
        localzzk.zzauj = true;
        if (localzzk.zzauf.size() != 0) {
          break label239;
        }
        zzx.zzaa(bool1);
        ArrayList localArrayList = new ArrayList(localzzk.zzaue);
        int i = localzzk.zzaui.get();
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext()) {
          break label244;
        }
        GoogleApiClient.ConnectionCallbacks localConnectionCallbacks = (GoogleApiClient.ConnectionCallbacks)localIterator.next();
        if ((!localzzk.zzauh) || (!localzzk.zzaud.isConnected()) || (localzzk.zzaui.get() != i)) {
          break label244;
        }
        if (localzzk.zzauf.contains(localConnectionCallbacks)) {
          continue;
        }
        localConnectionCallbacks.onConnected(paramBundle);
      }
      bool2 = false;
      break;
      label233:
      boolean bool3 = false;
      continue;
      label239:
      bool1 = false;
    }
    label244:
    localzzk.zzauf.clear();
    localzzk.zzauj = false;
  }
  
  final void zzpd()
  {
    Iterator localIterator = this.zzapK.iterator();
    while (localIterator.hasNext())
    {
      zze localzze = (zze)localIterator.next();
      localzze.zza(null);
      localzze.cancel();
    }
    this.zzapK.clear();
  }
  
  final boolean zzpi()
  {
    if (!this.zzapL) {
      return false;
    }
    this.zzapL = false;
    this.zzapO.removeMessages(2);
    this.zzapO.removeMessages(1);
    if (this.zzapP != null)
    {
      this.zzapP.unregister();
      this.zzapP = null;
    }
    return true;
  }
  
  final String zzpj()
  {
    StringWriter localStringWriter = new StringWriter();
    dump("", null, new PrintWriter(localStringWriter), null);
    return localStringWriter.toString();
  }
  
  final class zza
    extends Handler
  {
    zza(Looper paramLooper)
    {
      super();
    }
    
    public final void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default: 
        Log.w("GoogleApiClientImpl", "Unknown message id: " + paramMessage.what);
        return;
      case 1: 
        zznd.zzc(zznd.this);
        return;
      }
      zznd.zzb(zznd.this);
    }
  }
  
  private static final class zzb
    implements IBinder.DeathRecipient, zznd.zzd
  {
    private final WeakReference<zznd.zze<?>> zzaqd;
    private final WeakReference<ResultStore> zzaqe;
    private final WeakReference<IBinder> zzaqf;
    
    private zzb(zznd.zze paramzze, ResultStore paramResultStore, IBinder paramIBinder)
    {
      this.zzaqe = new WeakReference(paramResultStore);
      this.zzaqd = new WeakReference(paramzze);
      this.zzaqf = new WeakReference(paramIBinder);
    }
    
    private void zzpk()
    {
      zznd.zze localzze = (zznd.zze)this.zzaqd.get();
      ResultStore localResultStore = (ResultStore)this.zzaqe.get();
      if ((localResultStore != null) && (localzze != null))
      {
        localzze.zzoC().intValue();
        localResultStore.remove$13462e();
      }
      IBinder localIBinder = (IBinder)this.zzaqf.get();
      if (this.zzaqf != null) {
        localIBinder.unlinkToDeath(this, 0);
      }
    }
    
    public final void binderDied()
    {
      zzpk();
    }
    
    public final void zzc(zznd.zze<?> paramzze)
    {
      zzpk();
    }
  }
  
  static final class zzc
    extends zznh
  {
    private WeakReference<zznd> zzaqg;
    
    zzc(zznd paramzznd)
    {
      this.zzaqg = new WeakReference(paramzznd);
    }
    
    public final void zzpl()
    {
      zznd localzznd = (zznd)this.zzaqg.get();
      if (localzznd == null) {
        return;
      }
      zznd.zzb(localzznd);
    }
  }
  
  static abstract interface zzd
  {
    public abstract void zzc(zznd.zze<?> paramzze);
  }
  
  static abstract interface zze<A extends Api.zzb>
  {
    public abstract void cancel();
    
    public abstract boolean isReady();
    
    public abstract void zzE(Status paramStatus);
    
    public abstract void zzF(Status paramStatus);
    
    public abstract void zza(zznd.zzd paramzzd);
    
    public abstract void zzb(A paramA)
      throws DeadObjectException;
    
    public abstract Integer zzoC();
    
    public abstract void zzoF();
    
    public abstract Api.zzc<A> zzor();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznd
 * JD-Core Version:    0.7.0.1
 */