package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.GoogleApiClient.ServerAuthCodeCallbacks;

public final class zzxa
  implements Api.ApiOptions.Optional
{
  public static final zzxa zzbLO;
  public final boolean zzXl;
  public final boolean zzXn;
  public final String zzXo;
  public final boolean zzbLP;
  public final GoogleApiClient.ServerAuthCodeCallbacks zzbLQ;
  public final boolean zzbLR;
  public final boolean zzbLS;
  
  static
  {
    zza localzza = new zza();
    zzbLO = new zzxa(localzza.zzbLT, localzza.zzbLU, localzza.zzbDW, localzza.zzbLV, localzza.zzbLW, localzza.zzbLX, localzza.zzbLY, (byte)0);
  }
  
  private zzxa(boolean paramBoolean1, boolean paramBoolean2, String paramString, GoogleApiClient.ServerAuthCodeCallbacks paramServerAuthCodeCallbacks, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
  {
    this.zzbLP = paramBoolean1;
    this.zzXl = paramBoolean2;
    this.zzXo = paramString;
    this.zzbLQ = paramServerAuthCodeCallbacks;
    this.zzbLR = paramBoolean3;
    this.zzXn = paramBoolean4;
    this.zzbLS = paramBoolean5;
  }
  
  public static final class zza
  {
    String zzbDW;
    boolean zzbLT;
    boolean zzbLU;
    GoogleApiClient.ServerAuthCodeCallbacks zzbLV;
    boolean zzbLW;
    boolean zzbLX;
    boolean zzbLY;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzxa
 * JD-Core Version:    0.7.0.1
 */