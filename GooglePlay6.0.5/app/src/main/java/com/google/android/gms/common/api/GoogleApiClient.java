package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.util.SparseArray;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.zzad;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzf.zza;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzmu.zza;
import com.google.android.gms.internal.zzmw;
import com.google.android.gms.internal.zznd;
import com.google.android.gms.internal.zzns;
import com.google.android.gms.internal.zzns.zza;
import com.google.android.gms.internal.zzwx;
import com.google.android.gms.internal.zzwz;
import com.google.android.gms.internal.zzxa;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GoogleApiClient
{
  private static final Set<GoogleApiClient> zzaoc = Collections.newSetFromMap(new WeakHashMap());
  
  public abstract ConnectionResult blockingConnect();
  
  public abstract ConnectionResult blockingConnect(long paramLong, TimeUnit paramTimeUnit);
  
  public abstract void connect();
  
  public abstract void disconnect();
  
  public abstract void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString);
  
  public Looper getLooper()
  {
    throw new UnsupportedOperationException();
  }
  
  public abstract boolean isConnected();
  
  public abstract boolean isConnecting();
  
  public abstract void registerConnectionCallbacks(ConnectionCallbacks paramConnectionCallbacks);
  
  public abstract void registerConnectionFailedListener(OnConnectionFailedListener paramOnConnectionFailedListener);
  
  public abstract void unregisterConnectionCallbacks(ConnectionCallbacks paramConnectionCallbacks);
  
  public abstract void unregisterConnectionFailedListener(OnConnectionFailedListener paramOnConnectionFailedListener);
  
  public <C extends Api.zzb> C zza(Api.zzc<C> paramzzc)
  {
    throw new UnsupportedOperationException();
  }
  
  public <A extends Api.zzb, R extends Result, T extends zzmu.zza<R, A>> T zza(T paramT)
  {
    throw new UnsupportedOperationException();
  }
  
  public <A extends Api.zzb, T extends zzmu.zza<? extends Result, A>> T zzb(T paramT)
  {
    throw new UnsupportedOperationException();
  }
  
  public static final class Builder
  {
    private final Context mContext;
    private Account zzRE;
    private String zzUb;
    private final Set<Scope> zzaod = new HashSet();
    private final Set<Scope> zzaoe = new HashSet();
    private int zzaof;
    private View zzaog;
    private String zzaoh;
    private final Map<Api<?>, zzf.zza> zzaoi = new ArrayMap();
    private final Map<Api<?>, Api.ApiOptions> zzaoj = new ArrayMap();
    FragmentActivity zzaok;
    private int zzaol = -1;
    private GoogleApiClient.OnConnectionFailedListener zzaom;
    private GoogleApiAvailability zzaon = GoogleApiAvailability.getInstance();
    private Api.zza<? extends zzwz, zzxa> zzaoo = zzwx.zzTR;
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> zzaop = new ArrayList();
    private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zzaoq = new ArrayList();
    private zzxa zzaor;
    private Looper zzoD;
    
    public Builder(Context paramContext)
    {
      this.mContext = paramContext;
      this.zzoD = paramContext.getMainLooper();
      this.zzUb = paramContext.getPackageName();
      this.zzaoh = paramContext.getClass().getName();
    }
    
    public Builder(Context paramContext, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      this(paramContext);
      zzx.zzb(paramConnectionCallbacks, "Must provide a connected listener");
      this.zzaop.add(paramConnectionCallbacks);
      zzx.zzb(paramOnConnectionFailedListener, "Must provide a connection failed listener");
      this.zzaoq.add(paramOnConnectionFailedListener);
    }
    
    public final Builder addApi(Api<? extends Api.ApiOptions.NotRequiredOptions> paramApi)
    {
      zzx.zzb(paramApi, "Api must not be null");
      this.zzaoj.put(paramApi, null);
      paramApi.zzop();
      List localList = Collections.emptyList();
      this.zzaoe.addAll(localList);
      this.zzaod.addAll(localList);
      return this;
    }
    
    public final <O extends Api.ApiOptions.HasOptions> Builder addApi(Api<O> paramApi, O paramO)
    {
      zzx.zzb(paramApi, "Api must not be null");
      zzx.zzb(paramO, "Null options are not permitted for this Api");
      this.zzaoj.put(paramApi, paramO);
      paramApi.zzop();
      List localList = Collections.emptyList();
      this.zzaoe.addAll(localList);
      this.zzaod.addAll(localList);
      return this;
    }
    
    public final Builder addConnectionCallbacks(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
    {
      zzx.zzb(paramConnectionCallbacks, "Listener must not be null");
      this.zzaop.add(paramConnectionCallbacks);
      return this;
    }
    
    public final Builder addOnConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      zzx.zzb(paramOnConnectionFailedListener, "Listener must not be null");
      this.zzaoq.add(paramOnConnectionFailedListener);
      return this;
    }
    
    public final GoogleApiClient build()
    {
      boolean bool1;
      zzf localzzf;
      ArrayMap localArrayMap1;
      ArrayMap localArrayMap2;
      ArrayList localArrayList;
      label73:
      Api localApi;
      Object localObject2;
      int j;
      label146:
      zzmw localzzmw;
      int k;
      label193:
      boolean bool2;
      label209:
      Api.zze localzze;
      if (!this.zzaoj.isEmpty())
      {
        bool1 = true;
        zzx.zzb(bool1, "must call addApi() to add at least one API");
        localzzf = zzoy();
        Map localMap = localzzf.zzatx;
        localArrayMap1 = new ArrayMap();
        localArrayMap2 = new ArrayMap();
        localArrayList = new ArrayList();
        Iterator localIterator = this.zzaoj.keySet().iterator();
        if (!localIterator.hasNext()) {
          break label328;
        }
        localApi = (Api)localIterator.next();
        localObject2 = this.zzaoj.get(localApi);
        Object localObject3 = localMap.get(localApi);
        j = 0;
        if (localObject3 != null)
        {
          if (!((zzf.zza)localMap.get(localApi)).zzatz) {
            break label282;
          }
          j = 1;
        }
        localArrayMap1.put(localApi, Integer.valueOf(j));
        localzzmw = new zzmw(localApi, j);
        localArrayList.add(localzzmw);
        if (localApi.zzanU == null) {
          break label288;
        }
        k = 1;
        if (k == 0) {
          break label300;
        }
        if (localApi.zzanT == null) {
          break label294;
        }
        bool2 = true;
        zzx.zza(bool2, "This API was constructed with a ClientBuilder. Use getClientBuilder");
        localzze = localApi.zzanT;
      }
      label282:
      label288:
      label294:
      label300:
      for (Object localObject4 = new zzad(this.mContext, this.zzoD, localzze.zzou(), localzzmw, localzzmw, localzzf, localzze.zzs$55e35557());; localObject4 = localApi.zzop().zza(this.mContext, this.zzoD, localzzf, localObject2, localzzmw, localzzmw))
      {
        localArrayMap2.put(localApi.zzor(), localObject4);
        break label73;
        bool1 = false;
        break;
        j = 2;
        break label146;
        k = 0;
        break label193;
        bool2 = false;
        break label209;
      }
      label328:
      int i = zznd.zza$251b1977(localArrayMap2.values());
      final zznd localzznd = new zznd(this.mContext, new ReentrantLock(), this.zzoD, localzzf, this.zzaon, this.zzaoo, localArrayMap1, this.zzaop, this.zzaoq, localArrayMap2, this.zzaol, i, localArrayList);
      zzns localzzns;
      synchronized (GoogleApiClient.zzox())
      {
        GoogleApiClient.zzox().add(localzznd);
        if (this.zzaol >= 0)
        {
          localzzns = zzns.zzb(this.zzaok);
          if (localzzns == null) {
            new Handler(this.mContext.getMainLooper()).post(new Runnable()
            {
              public final void run()
              {
                if ((GoogleApiClient.Builder.this.zzaok.isFinishing()) || (GoogleApiClient.Builder.this.zzaok.getSupportFragmentManager().isDestroyed())) {
                  return;
                }
                GoogleApiClient.Builder.this.zza(zzns.zzc(GoogleApiClient.Builder.this.zzaok), localzznd);
              }
            });
          }
        }
        else
        {
          return localzznd;
        }
      }
      zza(localzzns, localzznd);
      return localzznd;
    }
    
    final void zza(zzns paramzzns, GoogleApiClient paramGoogleApiClient)
    {
      int i = this.zzaol;
      GoogleApiClient.OnConnectionFailedListener localOnConnectionFailedListener = this.zzaom;
      zzx.zzb(paramGoogleApiClient, "GoogleApiClient instance cannot be null");
      if (paramzzns.zzaqI.indexOfKey(i) < 0) {}
      for (boolean bool = true;; bool = false)
      {
        zzx.zza(bool, "Already managing a GoogleApiClient with id " + i);
        zzns.zza localzza = new zzns.zza(paramzzns, i, paramGoogleApiClient, localOnConnectionFailedListener);
        paramzzns.zzaqI.put(i, localzza);
        if ((paramzzns.mStarted) && (!paramzzns.zzaqD)) {
          paramGoogleApiClient.connect();
        }
        return;
      }
    }
    
    public final zzf zzoy()
    {
      boolean bool;
      Account localAccount;
      Set localSet;
      Map localMap;
      int i;
      View localView;
      String str1;
      String str2;
      if (this.zzaoj.containsKey(zzwx.API))
      {
        if (this.zzaor == null)
        {
          bool = true;
          zzx.zza(bool, "SignIn.API can't be used in conjunction with requestServerAuthCode.");
          this.zzaor = ((zzxa)this.zzaoj.get(zzwx.API));
        }
      }
      else
      {
        localAccount = this.zzRE;
        localSet = this.zzaod;
        localMap = this.zzaoi;
        i = this.zzaof;
        localView = this.zzaog;
        str1 = this.zzUb;
        str2 = this.zzaoh;
        if (this.zzaor == null) {
          break label131;
        }
      }
      label131:
      for (zzxa localzzxa = this.zzaor;; localzzxa = zzxa.zzbLO)
      {
        return new zzf(localAccount, localSet, localMap, i, localView, str1, str2, localzzxa);
        bool = false;
        break;
      }
    }
  }
  
  public static abstract interface ConnectionCallbacks
  {
    public abstract void onConnected(Bundle paramBundle);
    
    public abstract void onConnectionSuspended(int paramInt);
  }
  
  public static abstract interface OnConnectionFailedListener
  {
    public abstract void onConnectionFailed(ConnectionResult paramConnectionResult);
  }
  
  public static abstract interface ServerAuthCodeCallbacks
  {
    public abstract CheckResult onCheckServerAuthorization$1acf187f();
    
    public abstract boolean onUploadServerAuthCode$16da05f3();
    
    public static final class CheckResult
    {
      public Set<Scope> zzXp;
      public boolean zzaot;
    }
  }
  
  public static abstract interface zza
  {
    public abstract void zza(ConnectionResult paramConnectionResult);
    
    public abstract void zzb(ConnectionResult paramConnectionResult);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.GoogleApiClient
 * JD-Core Version:    0.7.0.1
 */