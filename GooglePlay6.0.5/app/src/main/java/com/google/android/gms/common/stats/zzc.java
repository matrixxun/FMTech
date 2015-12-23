package com.google.android.gms.common.stats;

import com.google.android.gms.internal.zznx;

public final class zzc
{
  public static zznx<Integer> zzawf = zznx.zza("gms:common:stats:max_num_of_events", Integer.valueOf(100));
  public static zznx<Integer> zzawg = zznx.zza("gms:common:stats:max_chunk_size", Integer.valueOf(100));
  
  public static final class zza
  {
    public static zznx<Integer> zzawh = zznx.zza("gms:common:stats:connections:level", Integer.valueOf(zzd.LOG_LEVEL_OFF));
    public static zznx<String> zzawi = zznx.zzE("gms:common:stats:connections:ignored_calling_processes", "");
    public static zznx<String> zzawj = zznx.zzE("gms:common:stats:connections:ignored_calling_services", "");
    public static zznx<String> zzawk = zznx.zzE("gms:common:stats:connections:ignored_target_processes", "");
    public static zznx<String> zzawl = zznx.zzE("gms:common:stats:connections:ignored_target_services", "com.google.android.gms.auth.GetToken");
    public static zznx<Long> zzawm = zznx.zza("gms:common:stats:connections:time_out_duration", Long.valueOf(600000L));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.stats.zzc
 * JD-Core Version:    0.7.0.1
 */