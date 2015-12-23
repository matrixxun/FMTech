package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.zza;
import com.google.android.gms.common.api.Scope;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class zzj<T extends IInterface>
  implements Api.zzb, zzk.zza
{
  public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = { "service_esmobile", "service_googleme" };
  public final Context mContext;
  final Handler mHandler;
  private final Account zzRE;
  private final Set<Scope> zzXp;
  private final GoogleApiAvailability zzaon;
  private final zzf zzapu;
  private final zzl zzatM;
  private zzs zzatN;
  private GoogleApiClient.zza zzatO;
  private T zzatP;
  private final ArrayList<zzj<T>.com.google.android.gms.common.internal.zzj.com.google.android.gms.common.internal.zzj.com.google.android.gms.common.internal.zzj.zzc<?>> zzatQ = new ArrayList();
  private zzj<T>.com.google.android.gms.common.internal.zzj.com.google.android.gms.common.internal.zzj.com.google.android.gms.common.internal.zzj.zze zzatR;
  private int zzatS = 1;
  private final GoogleApiClient.ConnectionCallbacks zzatT;
  private final GoogleApiClient.OnConnectionFailedListener zzatU;
  private final int zzatV;
  protected AtomicInteger zzatW = new AtomicInteger(0);
  public final Looper zzoD;
  private final Object zzqp = new Object();
  
  public zzj(Context paramContext, Looper paramLooper, int paramInt, zzf paramzzf, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    this(paramContext, paramLooper, zzl.zzav(paramContext), GoogleApiAvailability.getInstance(), paramInt, paramzzf, (GoogleApiClient.ConnectionCallbacks)zzx.zzC(paramConnectionCallbacks), (GoogleApiClient.OnConnectionFailedListener)zzx.zzC(paramOnConnectionFailedListener));
  }
  
  private zzj(Context paramContext, Looper paramLooper, zzl paramzzl, GoogleApiAvailability paramGoogleApiAvailability, int paramInt, zzf paramzzf, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    this.mContext = ((Context)zzx.zzb(paramContext, "Context must not be null"));
    this.zzoD = ((Looper)zzx.zzb(paramLooper, "Looper must not be null"));
    this.zzatM = ((zzl)zzx.zzb(paramzzl, "Supervisor must not be null"));
    this.zzaon = ((GoogleApiAvailability)zzx.zzb(paramGoogleApiAvailability, "API availability must not be null"));
    this.mHandler = new zzb(paramLooper);
    this.zzatV = paramInt;
    this.zzapu = ((zzf)zzx.zzC(paramzzf));
    this.zzRE = paramzzf.zzRE;
    this.zzXp = zzb(paramzzf.zzatw);
    this.zzatT = paramConnectionCallbacks;
    this.zzatU = paramOnConnectionFailedListener;
  }
  
  protected static void onConnectionFailed$5d4cef71() {}
  
  private boolean zza(int paramInt1, int paramInt2, T paramT)
  {
    synchronized (this.zzqp)
    {
      if (this.zzatS != paramInt1) {
        return false;
      }
      zzb(paramInt2, paramT);
      return true;
    }
  }
  
  private static Set<Scope> zzb(Set<Scope> paramSet)
  {
    if (paramSet == null) {}
    Iterator localIterator;
    do
    {
      while (!localIterator.hasNext())
      {
        return paramSet;
        localIterator = paramSet.iterator();
      }
    } while (paramSet.contains((Scope)localIterator.next()));
    throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
  }
  
  private void zzb(int paramInt, T paramT)
  {
    boolean bool1 = true;
    boolean bool2;
    boolean bool3;
    if (paramInt == 3)
    {
      bool2 = bool1;
      if (paramT == null) {
        break label298;
      }
      bool3 = bool1;
      label17:
      if (bool2 != bool3) {
        break label304;
      }
    }
    for (;;)
    {
      zzx.zzab(bool1);
      for (;;)
      {
        synchronized (this.zzqp)
        {
          this.zzatS = paramInt;
          this.zzatP = paramT;
          switch (paramInt)
          {
          default: 
            return;
          case 2: 
            if (this.zzatR != null)
            {
              Log.e("GmsClient", "Calling connect() while still connected, missing disconnect() for " + zzgp());
              this.zzatM.zzb(zzgp(), this.zzatR, this.zzapu.zzaoh);
              this.zzatW.incrementAndGet();
            }
            this.zzatR = new zze(this.zzatW.get());
            if (this.zzatM.zza(zzgp(), this.zzatR, this.zzapu.zzaoh)) {
              continue;
            }
            Log.e("GmsClient", "unable to connect to service: " + zzgp());
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzatW.get(), 9));
          }
        }
        zzlF();
        continue;
        if (this.zzatR != null)
        {
          this.zzatM.zzb(zzgp(), this.zzatR, this.zzapu.zzaoh);
          this.zzatR = null;
        }
      }
      bool2 = false;
      break;
      label298:
      bool3 = false;
      break label17;
      label304:
      bool1 = false;
    }
  }
  
  private void zzdg$13462e()
  {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(4, this.zzatW.get(), 1));
  }
  
  public void disconnect()
  {
    this.zzatW.incrementAndGet();
    synchronized (this.zzatQ)
    {
      int i = this.zzatQ.size();
      for (int j = 0; j < i; j++) {
        ((zzc)this.zzatQ.get(j)).zzqt();
      }
      this.zzatQ.clear();
      zzb(1, null);
      return;
    }
  }
  
  public final void dump$ec96877(String paramString, PrintWriter paramPrintWriter)
  {
    IInterface localIInterface;
    for (;;)
    {
      synchronized (this.zzqp)
      {
        int i = this.zzatS;
        localIInterface = this.zzatP;
        paramPrintWriter.append(paramString).append("mConnectState=");
        switch (i)
        {
        default: 
          paramPrintWriter.print("UNKNOWN");
          paramPrintWriter.append(" mService=");
          if (localIInterface != null) {
            break label139;
          }
          paramPrintWriter.println("null");
          return;
        }
      }
      paramPrintWriter.print("CONNECTING");
      continue;
      paramPrintWriter.print("CONNECTED");
      continue;
      paramPrintWriter.print("DISCONNECTING");
      continue;
      paramPrintWriter.print("DISCONNECTED");
    }
    label139:
    paramPrintWriter.append(zzgq()).append("@").println(Integer.toHexString(System.identityHashCode(localIInterface.asBinder())));
  }
  
  public final boolean isConnected()
  {
    for (;;)
    {
      synchronized (this.zzqp)
      {
        if (this.zzatS == 3)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public final boolean isConnecting()
  {
    for (;;)
    {
      synchronized (this.zzqp)
      {
        if (this.zzatS == 2)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public void onConnectionSuspended(int paramInt) {}
  
  public void zza(int paramInt1, IBinder paramIBinder, Bundle paramBundle, int paramInt2)
  {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(1, paramInt2, -1, new zzg(paramInt1, paramIBinder, paramBundle)));
  }
  
  public void zza(GoogleApiClient.zza paramzza)
  {
    this.zzatO = ((GoogleApiClient.zza)zzx.zzb(paramzza, "Connection progress callbacks cannot be null."));
    zzb(2, null);
  }
  
  public final void zza(zzp paramzzp)
  {
    ValidateAccountRequest localValidateAccountRequest = new ValidateAccountRequest(paramzzp, (Scope[])this.zzXp.toArray(new Scope[this.zzXp.size()]), this.mContext.getPackageName(), null);
    try
    {
      this.zzatN.zza(new zzd(this, this.zzatW.get()), localValidateAccountRequest);
      return;
    }
    catch (DeadObjectException localDeadObjectException)
    {
      Log.w("GmsClient", "service died");
      zzdg$13462e();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.w("GmsClient", "Remote exception occurred", localRemoteException);
    }
  }
  
  public final void zza(zzp paramzzp, Set<Scope> paramSet)
  {
    try
    {
      Bundle localBundle = zzjM();
      GetServiceRequest localGetServiceRequest = new GetServiceRequest(this.zzatV);
      localGetServiceRequest.zzatH = this.mContext.getPackageName();
      localGetServiceRequest.zzatK = localBundle;
      if (paramSet != null) {
        localGetServiceRequest.zzatJ = ((Scope[])paramSet.toArray(new Scope[paramSet.size()]));
      }
      if (zzkc()) {
        if (this.zzRE == null) {
          break label134;
        }
      }
      label134:
      for (Account localAccount = this.zzRE;; localAccount = new Account("<<default account>>", "com.google"))
      {
        localGetServiceRequest.zzatL = localAccount;
        if (paramzzp != null) {
          localGetServiceRequest.zzatI = paramzzp.asBinder();
        }
        this.zzatN.zza(new zzd(this, this.zzatW.get()), localGetServiceRequest);
        return;
      }
      return;
    }
    catch (DeadObjectException localDeadObjectException)
    {
      Log.w("GmsClient", "service died");
      zzdg$13462e();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.w("GmsClient", "Remote exception occurred", localRemoteException);
    }
  }
  
  public abstract T zzaa(IBinder paramIBinder);
  
  public abstract String zzgp();
  
  public abstract String zzgq();
  
  public Bundle zzjM()
  {
    return new Bundle();
  }
  
  public boolean zzkc()
  {
    return false;
  }
  
  public final Intent zzks()
  {
    throw new UnsupportedOperationException("Not a sign in API");
  }
  
  public void zzlF() {}
  
  public final IBinder zzot()
  {
    if (this.zzatN == null) {
      return null;
    }
    return this.zzatN.asBinder();
  }
  
  public final void zzqk()
  {
    int i = GoogleApiAvailability.isGooglePlayServicesAvailable(this.mContext);
    if (i != 0)
    {
      zzb(1, null);
      this.zzatO = new zzf();
      this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzatW.get(), i));
      return;
    }
    zza(new zzf());
  }
  
  public final void zzqm()
  {
    if (!isConnected()) {
      throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
    }
  }
  
  public final T zzqn()
    throws DeadObjectException
  {
    synchronized (this.zzqp)
    {
      if (this.zzatS == 4) {
        throw new DeadObjectException();
      }
    }
    zzqm();
    if (this.zzatP != null) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zza(bool, "Client is connected but service is null");
      IInterface localIInterface = this.zzatP;
      return localIInterface;
    }
  }
  
  private abstract class zza
    extends zzj<T>.com.google.android.gms.common.internal.zzj.com.google.android.gms.common.internal.zzj.com.google.android.gms.common.internal.zzj.zzc<Boolean>
  {
    public final int statusCode;
    public final Bundle zzatX;
    
    protected zza(int paramInt, Bundle paramBundle)
    {
      super(Boolean.valueOf(true));
      this.statusCode = paramInt;
      this.zzatX = paramBundle;
    }
    
    protected abstract void zzk(ConnectionResult paramConnectionResult);
    
    protected abstract boolean zzqq();
  }
  
  final class zzb
    extends Handler
  {
    public zzb(Looper paramLooper)
    {
      super();
    }
    
    private static void zza(Message paramMessage)
    {
      ((zzj.zzc)paramMessage.obj).unregister();
    }
    
    private static boolean zzb(Message paramMessage)
    {
      return (paramMessage.what == 2) || (paramMessage.what == 1) || (paramMessage.what == 5) || (paramMessage.what == 6);
    }
    
    public final void handleMessage(Message paramMessage)
    {
      if (zzj.this.zzatW.get() != paramMessage.arg1)
      {
        if (zzb(paramMessage)) {
          zza(paramMessage);
        }
        return;
      }
      if (((paramMessage.what == 1) || (paramMessage.what == 5) || (paramMessage.what == 6)) && (!zzj.this.isConnecting()))
      {
        zza(paramMessage);
        return;
      }
      if (paramMessage.what == 3)
      {
        ConnectionResult localConnectionResult = new ConnectionResult(paramMessage.arg2, null);
        zzj.zza(zzj.this).zza(localConnectionResult);
        zzj.onConnectionFailed$5d4cef71();
        return;
      }
      if (paramMessage.what == 4)
      {
        zzj.zza$4495c6b5(zzj.this, 4);
        if (zzj.zzb(zzj.this) != null) {
          zzj.zzb(zzj.this).onConnectionSuspended(paramMessage.arg2);
        }
        zzj.this.onConnectionSuspended(paramMessage.arg2);
        zzj.zza(zzj.this, 4, 1, null);
        return;
      }
      if ((paramMessage.what == 2) && (!zzj.this.isConnected()))
      {
        zza(paramMessage);
        return;
      }
      if (zzb(paramMessage))
      {
        ((zzj.zzc)paramMessage.obj).zzqs();
        return;
      }
      Log.wtf("GmsClient", "Don't know how to handle message: " + paramMessage.what, new Exception());
    }
  }
  
  protected abstract class zzc<TListener>
  {
    private TListener mListener;
    private boolean zzatZ;
    
    public zzc()
    {
      Object localObject;
      this.mListener = localObject;
      this.zzatZ = false;
    }
    
    public final void unregister()
    {
      zzqt();
      synchronized (zzj.zzc(zzj.this))
      {
        zzj.zzc(zzj.this).remove(this);
        return;
      }
    }
    
    /* Error */
    public final void zzqs()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 21	com/google/android/gms/common/internal/zzj$zzc:mListener	Ljava/lang/Object;
      //   6: astore_2
      //   7: aload_0
      //   8: getfield 23	com/google/android/gms/common/internal/zzj$zzc:zzatZ	Z
      //   11: ifeq +30 -> 41
      //   14: ldc 44
      //   16: new 46	java/lang/StringBuilder
      //   19: dup
      //   20: ldc 48
      //   22: invokespecial 51	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   25: aload_0
      //   26: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   29: ldc 57
      //   31: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   34: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   37: invokestatic 70	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
      //   40: pop
      //   41: aload_0
      //   42: monitorexit
      //   43: aload_2
      //   44: ifnull +8 -> 52
      //   47: aload_0
      //   48: aload_2
      //   49: invokevirtual 74	com/google/android/gms/common/internal/zzj$zzc:zzz	(Ljava/lang/Object;)V
      //   52: aload_0
      //   53: monitorenter
      //   54: aload_0
      //   55: iconst_1
      //   56: putfield 23	com/google/android/gms/common/internal/zzj$zzc:zzatZ	Z
      //   59: aload_0
      //   60: monitorexit
      //   61: aload_0
      //   62: invokevirtual 76	com/google/android/gms/common/internal/zzj$zzc:unregister	()V
      //   65: return
      //   66: astore_1
      //   67: aload_0
      //   68: monitorexit
      //   69: aload_1
      //   70: athrow
      //   71: astore 4
      //   73: aload 4
      //   75: athrow
      //   76: astore_3
      //   77: aload_0
      //   78: monitorexit
      //   79: aload_3
      //   80: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	81	0	this	zzc
      //   66	4	1	localObject1	Object
      //   6	43	2	localObject2	Object
      //   76	4	3	localObject3	Object
      //   71	3	4	localRuntimeException	java.lang.RuntimeException
      // Exception table:
      //   from	to	target	type
      //   2	41	66	finally
      //   41	43	66	finally
      //   67	69	66	finally
      //   47	52	71	java/lang/RuntimeException
      //   54	61	76	finally
      //   77	79	76	finally
    }
    
    public final void zzqt()
    {
      try
      {
        this.mListener = null;
        return;
      }
      finally {}
    }
    
    protected abstract void zzz(TListener paramTListener);
  }
  
  public static final class zzd
    extends zzr.zza
  {
    private zzj zzaua;
    private final int zzaub;
    
    public zzd(zzj paramzzj, int paramInt)
    {
      this.zzaua = paramzzj;
      this.zzaub = paramInt;
    }
    
    public final void zza(int paramInt, Bundle paramBundle)
    {
      zzx.zzb(this.zzaua, "onAccountValidationComplete can be called only once per call to validateAccount");
      zzj localzzj = this.zzaua;
      int i = this.zzaub;
      localzzj.mHandler.sendMessage(localzzj.mHandler.obtainMessage(5, i, -1, new zzj.zzi(localzzj, paramInt, paramBundle)));
      this.zzaua = null;
    }
    
    public final void zza(int paramInt, IBinder paramIBinder, Bundle paramBundle)
    {
      zzx.zzb(this.zzaua, "onPostInitComplete can be called only once per call to getRemoteService");
      this.zzaua.zza(paramInt, paramIBinder, paramBundle, this.zzaub);
      this.zzaua = null;
    }
  }
  
  public final class zze
    implements ServiceConnection
  {
    private final int zzaub;
    
    public zze(int paramInt)
    {
      this.zzaub = paramInt;
    }
    
    public final void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      zzx.zzb(paramIBinder, "Expecting a valid IBinder");
      zzj.zza(zzj.this, zzs.zza.zzcm(paramIBinder));
      zzj localzzj = zzj.this;
      int i = this.zzaub;
      localzzj.mHandler.sendMessage(localzzj.mHandler.obtainMessage(6, i, -1, new zzj.zzh(localzzj)));
    }
    
    public final void onServiceDisconnected(ComponentName paramComponentName)
    {
      zzj.this.mHandler.sendMessage(zzj.this.mHandler.obtainMessage(4, this.zzaub, 1));
    }
  }
  
  protected final class zzf
    implements GoogleApiClient.zza
  {
    public zzf() {}
    
    public final void zza(ConnectionResult paramConnectionResult)
    {
      if (paramConnectionResult.isSuccess()) {
        zzj.this.zza(null, zzj.zzd(zzj.this));
      }
      while (zzj.zze(zzj.this) == null) {
        return;
      }
      zzj.zze(zzj.this).onConnectionFailed(paramConnectionResult);
    }
    
    public final void zzb(ConnectionResult paramConnectionResult)
    {
      throw new IllegalStateException("Legacy GmsClient received onReportAccountValidation callback.");
    }
  }
  
  protected final class zzg
    extends zzj<T>.com.google.android.gms.common.internal.zzj.com.google.android.gms.common.internal.zzj.com.google.android.gms.common.internal.zzj.zza
  {
    public final IBinder zzauc;
    
    public zzg(int paramInt, IBinder paramIBinder, Bundle paramBundle)
    {
      super(paramInt, paramBundle);
      this.zzauc = paramIBinder;
    }
    
    protected final void zzk(ConnectionResult paramConnectionResult)
    {
      if (zzj.zze(zzj.this) != null) {
        zzj.zze(zzj.this).onConnectionFailed(paramConnectionResult);
      }
      zzj.onConnectionFailed$5d4cef71();
    }
    
    protected final boolean zzqq()
    {
      IInterface localIInterface;
      do
      {
        try
        {
          String str = this.zzauc.getInterfaceDescriptor();
          if (!zzj.this.zzgq().equals(str))
          {
            Log.e("GmsClient", "service descriptor mismatch: " + zzj.this.zzgq() + " vs. " + str);
            return false;
          }
        }
        catch (RemoteException localRemoteException)
        {
          Log.w("GmsClient", "service probably died");
          return false;
        }
        localIInterface = zzj.this.zzaa(this.zzauc);
      } while ((localIInterface == null) || (!zzj.zza(zzj.this, 2, 3, localIInterface)));
      if (zzj.zzb(zzj.this) != null) {
        zzj.zzb(zzj.this).onConnected(null);
      }
      return true;
    }
  }
  
  protected final class zzh
    extends zzj<T>.com.google.android.gms.common.internal.zzj.com.google.android.gms.common.internal.zzj.com.google.android.gms.common.internal.zzj.zza
  {
    public zzh()
    {
      super(0, null);
    }
    
    protected final void zzk(ConnectionResult paramConnectionResult)
    {
      zzj.zza(zzj.this).zza(paramConnectionResult);
      zzj.onConnectionFailed$5d4cef71();
    }
    
    protected final boolean zzqq()
    {
      zzj.zza(zzj.this).zza(ConnectionResult.zzanu);
      return true;
    }
  }
  
  protected final class zzi
    extends zzj<T>.com.google.android.gms.common.internal.zzj.com.google.android.gms.common.internal.zzj.com.google.android.gms.common.internal.zzj.zza
  {
    public zzi(int paramInt, Bundle paramBundle)
    {
      super(paramInt, paramBundle);
    }
    
    protected final void zzk(ConnectionResult paramConnectionResult)
    {
      zzj.zza(zzj.this).zzb(paramConnectionResult);
      zzj.onConnectionFailed$5d4cef71();
    }
    
    protected final boolean zzqq()
    {
      zzj.zza(zzj.this).zzb(ConnectionResult.zzanu);
      return true;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzj
 * JD-Core Version:    0.7.0.1
 */