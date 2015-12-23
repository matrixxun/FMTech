package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.view.View;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzxa;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zzf
{
  public final Account zzRE;
  public final String zzUb;
  public final Set<Scope> zzaod;
  private final int zzaof;
  private final View zzaog;
  final String zzaoh;
  public final zzxa zzaor;
  final Set<Scope> zzatw;
  public final Map<Api<?>, zza> zzatx;
  public Integer zzaty;
  
  public zzf(Account paramAccount, Set<Scope> paramSet, Map<Api<?>, zza> paramMap, int paramInt, View paramView, String paramString1, String paramString2, zzxa paramzzxa)
  {
    this.zzRE = paramAccount;
    if (paramSet == null) {}
    HashSet localHashSet;
    for (Set localSet = Collections.EMPTY_SET;; localSet = Collections.unmodifiableSet(paramSet))
    {
      this.zzaod = localSet;
      if (paramMap == null) {
        paramMap = Collections.EMPTY_MAP;
      }
      this.zzatx = paramMap;
      this.zzaog = paramView;
      this.zzaof = paramInt;
      this.zzUb = paramString1;
      this.zzaoh = paramString2;
      this.zzaor = paramzzxa;
      localHashSet = new HashSet(this.zzaod);
      Iterator localIterator = this.zzatx.values().iterator();
      while (localIterator.hasNext()) {
        localHashSet.addAll(((zza)localIterator.next()).zzXp);
      }
    }
    this.zzatw = Collections.unmodifiableSet(localHashSet);
  }
  
  public static final class zza
  {
    public final Set<Scope> zzXp;
    public final boolean zzatz;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzf
 * JD-Core Version:    0.7.0.1
 */