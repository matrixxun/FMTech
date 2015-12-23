package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.signin.internal.zzh;
import com.google.android.gms.signin.internal.zzi;

public final class zzwx
{
  public static final Api<zzxa> API = new Api("SignIn.API", zzTR, zzTQ);
  public static final Api.zzc<zzi> zzTQ = new Api.zzc();
  public static final Api.zza<zzi, zzxa> zzTR;
  public static final Scope zzXi;
  public static final Scope zzXj;
  public static final Api<zza> zzaBf = new Api("SignIn.INTERNAL_API", zzbLL, zzaLs);
  public static final Api.zzc<zzi> zzaLs = new Api.zzc();
  static final Api.zza<zzi, zza> zzbLL;
  public static final zzwy zzbLM = new zzh();
  
  static
  {
    zzTR = new Api.zza() {};
    zzbLL = new Api.zza() {};
    zzXi = new Scope("profile");
    zzXj = new Scope("email");
  }
  
  public static final class zza
    implements Api.ApiOptions.HasOptions
  {
    final Bundle zzbLN;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzwx
 * JD-Core Version:    0.7.0.1
 */