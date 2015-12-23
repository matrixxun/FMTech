package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.zzr;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ServerAuthCodeCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.ServerAuthCodeCallbacks.CheckResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.AuthAccountRequest;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzj.zzf;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzwz;
import com.google.android.gms.internal.zzxa;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public final class zzi
  extends zzj<zzf>
  implements zzwz
{
  private final com.google.android.gms.common.internal.zzf zzapu;
  private Integer zzaty;
  private final Bundle zzbLN;
  private final boolean zzbMe;
  
  public zzi(Context paramContext, Looper paramLooper, com.google.android.gms.common.internal.zzf paramzzf, zzxa paramzzxa, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, ExecutorService paramExecutorService)
  {
    this(paramContext, paramLooper, true, paramzzf, localBundle, paramConnectionCallbacks, paramOnConnectionFailedListener);
  }
  
  public zzi(Context paramContext, Looper paramLooper, boolean paramBoolean, com.google.android.gms.common.internal.zzf paramzzf, Bundle paramBundle, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramLooper, 44, paramzzf, paramConnectionCallbacks, paramOnConnectionFailedListener);
    this.zzbMe = paramBoolean;
    this.zzapu = paramzzf;
    this.zzbLN = paramBundle;
    this.zzaty = paramzzf.zzaty;
  }
  
  public final void connect()
  {
    zza(new zzj.zzf(this));
  }
  
  public final void zzIZ()
  {
    try
    {
      ((zzf)zzqn()).zzov(this.zzaty.intValue());
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
    }
  }
  
  public final void zza(zzp paramzzp, Set<Scope> paramSet, zze paramzze)
  {
    zzx.zzb(paramzze, "Expecting a valid ISignInCallbacks");
    try
    {
      ((zzf)zzqn()).zza(new AuthAccountRequest(paramzzp, paramSet), paramzze);
      return;
    }
    catch (RemoteException localRemoteException1)
    {
      Log.w("SignInClientImpl", "Remote service probably died when authAccount is called");
      try
      {
        paramzze.zza(new ConnectionResult(8, null), new AuthAccountResult(8));
        return;
      }
      catch (RemoteException localRemoteException2)
      {
        Log.wtf("SignInClientImpl", "ISignInCallbacks#onAuthAccount should be executed from the same process, unexpected RemoteException.", localRemoteException1);
      }
    }
  }
  
  public final void zza(zzp paramzzp, boolean paramBoolean)
  {
    try
    {
      ((zzf)zzqn()).zza(paramzzp, this.zzaty.intValue(), paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
    }
  }
  
  public final void zza(zzt paramzzt)
  {
    zzx.zzb(paramzzt, "Expecting a valid IResolveAccountCallbacks");
    try
    {
      com.google.android.gms.common.internal.zzf localzzf = this.zzapu;
      if (localzzf.zzRE != null) {}
      Account localAccount;
      for (Object localObject = localzzf.zzRE;; localObject = localAccount)
      {
        boolean bool = "<<default account>>".equals(((Account)localObject).name);
        GoogleSignInAccount localGoogleSignInAccount = null;
        if (bool)
        {
          zzr localzzr = zzr.zzaf(this.mContext);
          localGoogleSignInAccount = localzzr.zzbF(localzzr.zzbH("defaultGoogleSignInAccount"));
        }
        ResolveAccountRequest localResolveAccountRequest = new ResolveAccountRequest((Account)localObject, this.zzaty.intValue(), localGoogleSignInAccount);
        ((zzf)zzqn()).zza(localResolveAccountRequest, paramzzt);
        return;
        localAccount = new Account("<<default account>>", "com.google");
      }
      return;
    }
    catch (RemoteException localRemoteException1)
    {
      Log.w("SignInClientImpl", "Remote service probably died when resolveAccount is called");
      try
      {
        paramzzt.zzb(new ResolveAccountResponse());
        return;
      }
      catch (RemoteException localRemoteException2)
      {
        Log.wtf("SignInClientImpl", "IResolveAccountCallbacks#onAccountResolutionComplete should be executed from the same process, unexpected RemoteException.", localRemoteException1);
      }
    }
  }
  
  protected final String zzgp()
  {
    return "com.google.android.gms.signin.service.START";
  }
  
  protected final String zzgq()
  {
    return "com.google.android.gms.signin.internal.ISignInService";
  }
  
  protected final Bundle zzjM()
  {
    String str = this.zzapu.zzUb;
    if (!this.mContext.getPackageName().equals(str)) {
      this.zzbLN.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zzapu.zzUb);
    }
    return this.zzbLN;
  }
  
  public final boolean zzkc()
  {
    return this.zzbMe;
  }
  
  private static final class zza
    extends zzd.zza
  {
    private final zzxa zzaor;
    private final ExecutorService zzbMf;
    
    public zza(zzxa paramzzxa, ExecutorService paramExecutorService)
    {
      this.zzaor = paramzzxa;
      this.zzbMf = paramExecutorService;
    }
    
    public final void zza(final String paramString1, final String paramString2, final zzf paramzzf)
      throws RemoteException
    {
      this.zzbMf.submit(new Runnable()
      {
        public final void run()
        {
          try
          {
            boolean bool = zzi.zza.zza(zzi.zza.this).onUploadServerAuthCode$16da05f3();
            paramzzf.zzaU(bool);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            Log.e("SignInClientImpl", "RemoteException thrown when processing uploadServerAuthCode callback", localRemoteException);
          }
        }
      });
    }
    
    public final void zza(final String paramString, final List<Scope> paramList, final zzf paramzzf)
      throws RemoteException
    {
      this.zzbMf.submit(new Runnable()
      {
        public final void run()
        {
          try
          {
            GoogleApiClient.ServerAuthCodeCallbacks localServerAuthCodeCallbacks = zzi.zza.zza(zzi.zza.this);
            Collections.unmodifiableSet(new HashSet(paramList));
            GoogleApiClient.ServerAuthCodeCallbacks.CheckResult localCheckResult = localServerAuthCodeCallbacks.onCheckServerAuthorization$1acf187f();
            CheckServerAuthResult localCheckServerAuthResult = new CheckServerAuthResult(localCheckResult.zzaot, localCheckResult.zzXp);
            paramzzf.zza(localCheckServerAuthResult);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            Log.e("SignInClientImpl", "RemoteException thrown when processing checkServerAuthorization callback", localRemoteException);
          }
        }
      });
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.signin.internal.zzi
 * JD-Core Version:    0.7.0.1
 */