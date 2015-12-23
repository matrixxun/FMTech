package com.google.android.gms.wearable;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.wearable.internal.zzbc;
import com.google.android.gms.wearable.internal.zzbm;
import com.google.android.gms.wearable.internal.zzbo;
import com.google.android.gms.wearable.internal.zzcb;
import com.google.android.gms.wearable.internal.zzce;
import com.google.android.gms.wearable.internal.zzcg;
import com.google.android.gms.wearable.internal.zze;
import com.google.android.gms.wearable.internal.zzg;
import com.google.android.gms.wearable.internal.zzj;
import com.google.android.gms.wearable.internal.zzl;
import com.google.android.gms.wearable.internal.zzw;
import com.google.android.gms.wearable.internal.zzx;

public final class Wearable
{
  public static final Api<WearableOptions> API = new Api("Wearable.API", zzTR, zzTQ);
  public static final AmsApi AmsApi;
  public static final AncsApi AncsApi;
  public static final CapabilityApi CapabilityApi;
  public static final ChannelApi ChannelApi;
  public static final ConnectionApi ConnectionApi;
  public static final DataApi DataApi = new zzx();
  public static final LargeAssetApi LargeAssetApi;
  public static final MessageApi MessageApi;
  public static final NodeApi NodeApi;
  public static final TelephonyApi TelephonyApi;
  public static final WifiApi WifiApi;
  public static final Api.zzc<zzce> zzTQ;
  private static final Api.zza<zzce, WearableOptions> zzTR;
  
  static
  {
    CapabilityApi = new zzj();
    MessageApi = new zzbm();
    NodeApi = new zzbo();
    ChannelApi = new zzl();
    LargeAssetApi = new zzbc();
    AncsApi = new zzg();
    AmsApi = new zze();
    ConnectionApi = new zzw();
    TelephonyApi = new zzcb();
    WifiApi = new zzcg();
    zzTQ = new Api.zzc();
    zzTR = new Api.zza() {};
  }
  
  public static final class WearableOptions
    implements Api.ApiOptions.Optional
  {
    public static final class Builder {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.Wearable
 * JD-Core Version:    0.7.0.1
 */