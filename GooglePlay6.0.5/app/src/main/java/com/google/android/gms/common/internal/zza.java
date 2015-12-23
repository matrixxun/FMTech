package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;

public final class zza
  extends zzp.zza
{
  private Context mContext;
  private Account zzRE;
  int zzpr;
  
  public static Account zzb(zzp paramzzp)
  {
    Object localObject1 = null;
    long l;
    if (paramzzp != null) {
      l = Binder.clearCallingIdentity();
    }
    try
    {
      Account localAccount = paramzzp.getAccount();
      localObject1 = localAccount;
      return localObject1;
    }
    catch (RemoteException localRemoteException)
    {
      Log.w("AccountAccessor", "Remote account accessor probably died");
      return null;
    }
    finally
    {
      Binder.restoreCallingIdentity(l);
    }
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof zza)) {
      return false;
    }
    return this.zzRE.equals(((zza)paramObject).zzRE);
  }
  
  public final Account getAccount()
  {
    int i = Binder.getCallingUid();
    if (i == this.zzpr) {
      return this.zzRE;
    }
    if (GooglePlayServicesUtil.zze(this.mContext, i))
    {
      this.zzpr = i;
      return this.zzRE;
    }
    throw new SecurityException("Caller is not GooglePlayServices");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zza
 * JD-Core Version:    0.7.0.1
 */