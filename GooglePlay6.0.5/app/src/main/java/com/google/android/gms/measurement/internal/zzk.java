package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zznx;

public final class zzk
{
  public static zza<Boolean> zzbmC = zza.zzl$311217ad("measurement.service_enabled");
  public static zza<Boolean> zzbmD = zza.zzl$311217ad("measurement.service_client_enabled");
  public static zza<String> zzbmE = zza.zzh("measurement.log_tag", "GMPM", "GMPM-SVC");
  public static zza<Long> zzbmF = zza.zzf("measurement.ad_id_cache_time", 10000L);
  public static zza<Long> zzbmG = zza.zzf("measurement.monitoring.sample_period_millis", 86400000L);
  public static zza<Integer> zzbmH = zza.zzz("measurement.upload.max_bundles", 100);
  public static zza<Integer> zzbmI = zza.zzz("measurement.upload.max_batch_size", 65536);
  public static zza<String> zzbmJ = zza.zzh("measurement.upload.url", "https://app-measurement.com/a", "https://app-measurement.com/a");
  public static zza<Long> zzbmK = zza.zzf("measurement.upload.backoff_period", 43200000L);
  public static zza<Long> zzbmL = zza.zzf("measurement.upload.window_interval", 3600000L);
  public static zza<Long> zzbmM = zza.zzf("measurement.upload.interval", 3600000L);
  public static zza<Long> zzbmN = zza.zzf("measurement.upload.stale_data_deletion_interval", 86400000L);
  public static zza<Long> zzbmO = zza.zzf("measurement.upload.initial_upload_delay_time", 15000L);
  public static zza<Long> zzbmP = zza.zzf("measurement.upload.retry_time", 1800000L);
  public static zza<Integer> zzbmQ = zza.zzz("measurement.upload.retry_count", 6);
  public static zza<Long> zzbmR = zza.zzf("measurement.upload.max_queue_time", 2419200000L);
  public static zza<Long> zzbmS = zza.zzf("measurement.service_client.idle_disconnect_millis", 5000L);
  
  public static final class zza<V>
  {
    private final V zzQB;
    private final zznx<V> zzQC;
    private V zzQD;
    
    private zza(zznx<V> paramzznx, V paramV)
    {
      zzx.zzC(paramzznx);
      this.zzQC = paramzznx;
      this.zzQB = paramV;
    }
    
    static zza<Long> zzf(String paramString, long paramLong)
    {
      return new zza(zznx.zza(paramString, Long.valueOf(paramLong)), Long.valueOf(paramLong));
    }
    
    static zza<String> zzh(String paramString1, String paramString2, String paramString3)
    {
      return new zza(zznx.zzE(paramString1, paramString3), paramString2);
    }
    
    static zza<Boolean> zzl$311217ad(String paramString)
    {
      return new zza(zznx.zzj(paramString, true), Boolean.valueOf(true));
    }
    
    static zza<Integer> zzz(String paramString, int paramInt)
    {
      return new zza(zznx.zza(paramString, Integer.valueOf(paramInt)), Integer.valueOf(paramInt));
    }
    
    public final V get()
    {
      if (this.zzQD != null) {
        return this.zzQD;
      }
      if ((zzd.zzasZ) && (zznx.isInitialized())) {
        return this.zzQC.getBinderSafe();
      }
      return this.zzQB;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzk
 * JD-Core Version:    0.7.0.1
 */