package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.signin.internal.zze;
import java.util.Set;

public abstract interface zzwz
  extends Api.zzb
{
  public abstract void connect();
  
  public abstract void zzIZ();
  
  public abstract void zza(zzp paramzzp, Set<Scope> paramSet, zze paramzze);
  
  public abstract void zza(zzp paramzzp, boolean paramBoolean);
  
  public abstract void zza(zzt paramzzt);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzwz
 * JD-Core Version:    0.7.0.1
 */