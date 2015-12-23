package com.google.android.gms.ads.internal.config;

import android.content.Context;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.zzhb;
import com.google.android.gms.internal.zzja;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@zzhb
public final class Flags
{
  public static final Flag<Boolean> adShieldInstrumentationEnabled;
  public static final Flag<Boolean> gassEnableIntSignal = Flag.zza(0, "gass:enable_int_signal", Boolean.valueOf(true));
  public static final Flag<Boolean> gassEnabled;
  public static final Flag<String> zzvJ = Flag.zzc(0, "gads:sdk_core_experiment_id");
  public static final Flag<String> zzvK = Flag.zza(0, "gads:sdk_core_location", "https://googleads.g.doubleclick.net/mads/static/mad/sdk/native/sdk-core-v40.html");
  public static final Flag<Boolean> zzvL = Flag.zza(0, "gads:request_builder:singleton_webview", Boolean.valueOf(false));
  public static final Flag<String> zzvM = Flag.zzc(0, "gads:request_builder:singleton_webview_experiment_id");
  public static final Flag<Boolean> zzvN = Flag.zza(0, "gads:sdk_use_dynamic_module", Boolean.valueOf(false));
  public static final Flag<String> zzvO = Flag.zzc(0, "gads:sdk_use_dynamic_module_experiment_id");
  public static final Flag<Boolean> zzvP = Flag.zza(0, "gads:sdk_crash_report_enabled", Boolean.valueOf(false));
  public static final Flag<Boolean> zzvQ = Flag.zza(0, "gads:sdk_crash_report_full_stacktrace", Boolean.valueOf(false));
  public static final Flag<Boolean> zzvR = Flag.zza(0, "gads:block_autoclicks", Boolean.valueOf(false));
  public static final Flag<String> zzvS = Flag.zzc(0, "gads:block_autoclicks_experiment_id");
  public static final Flag<String> zzvT = Flag.zzd$4eb20e7("gads:prefetch:experiment_id");
  public static final Flag<String> zzvU = Flag.zzc(0, "gads:spam_app_context:experiment_id");
  public static final Flag<Boolean> zzvV = Flag.zza(0, "gads:spam_app_context:enabled", Boolean.valueOf(false));
  public static final Flag<String> zzvW = Flag.zzc(0, "gads:video_stream_cache:experiment_id");
  public static final Flag<Integer> zzvX = Flag.zza(0, "gads:video_stream_cache:limit_count", 5);
  public static final Flag<Integer> zzvY = Flag.zza(0, "gads:video_stream_cache:limit_space", 8388608);
  public static final Flag<Integer> zzvZ = Flag.zza(0, "gads:video_stream_exo_cache:buffer_size", 8388608);
  public static final Flag<Long> zzwA;
  public static final Flag<String> zzwB;
  public static final Flag<String> zzwC;
  public static final Flag<Boolean> zzwD;
  public static final Flag<Boolean> zzwE;
  public static final Flag<Boolean> zzwF;
  public static final Flag<String> zzwG;
  public static final Flag<Boolean> zzwH;
  public static final Flag<Boolean> zzwI;
  public static final Flag<Integer> zzwJ;
  public static final Flag<String> zzwK;
  public static final Flag<String> zzwL;
  public static final Flag<Boolean> zzwM;
  public static final Flag<Boolean> zzwN;
  public static final Flag<String> zzwO;
  public static final Flag<Integer> zzwP;
  public static final Flag<Integer> zzwQ;
  public static final Flag<Integer> zzwR;
  public static final Flag<String> zzwS;
  public static final Flag<Boolean> zzwT;
  public static final Flag<Boolean> zzwU;
  public static final Flag<Long> zzwV;
  public static final Flag<Boolean> zzwW;
  public static final Flag<Boolean> zzwX;
  public static final Flag<Boolean> zzwY;
  public static final Flag<Boolean> zzwZ;
  public static final Flag<Long> zzwa = Flag.zza$219febed("gads:video_stream_cache:limit_time_sec", 300L);
  public static final Flag<Long> zzwb = Flag.zza$219febed("gads:video_stream_cache:notify_interval_millis", 1000L);
  public static final Flag<Integer> zzwc = Flag.zza(0, "gads:video_stream_cache:connect_timeout_millis", 10000);
  public static final Flag<Boolean> zzwd = Flag.zza(0, "gads:video:metric_reporting_enabled", Boolean.valueOf(false));
  public static final Flag<String> zzwe = Flag.zza(0, "gads:video:metric_frame_hash_times", "");
  public static final Flag<Long> zzwf = Flag.zza$219febed("gads:video:metric_frame_hash_time_leniency", 500L);
  public static final Flag<String> zzwg = Flag.zzd$4eb20e7("gads:spam_ad_id_decorator:experiment_id");
  public static final Flag<Boolean> zzwh = Flag.zza(0, "gads:spam_ad_id_decorator:enabled", Boolean.valueOf(false));
  public static final Flag<String> zzwi = Flag.zzd$4eb20e7("gads:looper_for_gms_client:experiment_id");
  public static final Flag<Boolean> zzwj = Flag.zza(0, "gads:looper_for_gms_client:enabled", Boolean.valueOf(true));
  public static final Flag<Boolean> zzwk = Flag.zza(0, "gads:sw_ad_request_service:enabled", Boolean.valueOf(true));
  public static final Flag<Boolean> zzwl = Flag.zza(0, "gads:sw_dynamite:enabled", Boolean.valueOf(true));
  public static final Flag<String> zzwm = Flag.zza(0, "gad:mraid:url_banner", "https://googleads.g.doubleclick.net/mads/static/mad/sdk/native/mraid/v2/mraid_app_banner.js");
  public static final Flag<String> zzwn = Flag.zza(0, "gad:mraid:url_expanded_banner", "https://googleads.g.doubleclick.net/mads/static/mad/sdk/native/mraid/v2/mraid_app_expanded_banner.js");
  public static final Flag<String> zzwo = Flag.zza(0, "gad:mraid:url_interstitial", "https://googleads.g.doubleclick.net/mads/static/mad/sdk/native/mraid/v2/mraid_app_interstitial.js");
  public static final Flag<Boolean> zzwp = Flag.zza(0, "gads:enabled_sdk_csi", Boolean.valueOf(false));
  public static final Flag<String> zzwq = Flag.zza(0, "gads:sdk_csi_server", "https://csi.gstatic.com/csi");
  public static final Flag<Boolean> zzwr = Flag.zza(0, "gads:sdk_csi_write_to_file", Boolean.valueOf(false));
  public static final Flag<Boolean> zzws = Flag.zza(0, "gads:enable_content_fetching", Boolean.valueOf(true));
  public static final Flag<Integer> zzwt = Flag.zza(0, "gads:content_length_weight", 1);
  public static final Flag<Integer> zzwu = Flag.zza(0, "gads:content_age_weight", 1);
  public static final Flag<Integer> zzwv = Flag.zza(0, "gads:min_content_len", 11);
  public static final Flag<Integer> zzww = Flag.zza(0, "gads:fingerprint_number", 10);
  public static final Flag<Integer> zzwx = Flag.zza(0, "gads:sleep_sec", 10);
  public static final Flag<Boolean> zzwy = Flag.zza(0, "gad:app_index_enabled", Boolean.valueOf(true));
  public static final Flag<Boolean> zzwz = Flag.zza(0, "gads:app_index:without_content_info_present:enabled", Boolean.valueOf(true));
  public static final Flag<Boolean> zzxa;
  public static final Flag<Boolean> zzxb;
  public static final Flag<Boolean> zzxc;
  public static final Flag<Long> zzxd;
  public static final Flag<Boolean> zzxe;
  public static final Flag<Long> zzxf;
  public static final Flag<Long> zzxg;
  public static final Flag<Boolean> zzxh = Flag.zza(0, "gads:adid_notification:first_party_check:enabled", Boolean.valueOf(true));
  public static final Flag<Boolean> zzxi = Flag.zza(0, "gads:edu_device_helper:enabled", Boolean.valueOf(true));
  public static final Flag<Boolean> zzxj = Flag.zza(0, "gads:support_screen_shot", Boolean.valueOf(true));
  public static final Flag<Long> zzxk = Flag.zza$219febed("gads:js_flags:update_interval", TimeUnit.HOURS.toMillis(12L));
  
  static
  {
    zzwA = Flag.zza$219febed("gads:app_index:timeout_ms", 1000L);
    zzwB = Flag.zzc(0, "gads:app_index:experiment_id");
    zzwC = Flag.zzc(0, "gads:kitkat_interstitial_workaround:experiment_id");
    zzwD = Flag.zza(0, "gads:kitkat_interstitial_workaround:enabled", Boolean.valueOf(true));
    zzwE = Flag.zza(0, "gads:interstitial_follow_url", Boolean.valueOf(true));
    zzwF = Flag.zza(0, "gads:interstitial_follow_url:register_click", Boolean.valueOf(true));
    zzwG = Flag.zzc(0, "gads:interstitial_follow_url:experiment_id");
    zzwH = Flag.zza(0, "gads:analytics_enabled", Boolean.valueOf(true));
    zzwI = Flag.zza(0, "gads:ad_key_enabled", Boolean.valueOf(false));
    zzwJ = Flag.zza(0, "gads:webview_cache_version", 0);
    zzwK = Flag.zzd$4eb20e7("gads:pan:experiment_id");
    zzwL = Flag.zza(0, "gads:native:engine_url", "//googleads.g.doubleclick.net/mads/static/mad/sdk/native/native_ads.html");
    zzwM = Flag.zza(0, "gads:ad_manager_creator:enabled", Boolean.valueOf(true));
    zzwN = Flag.zza(1, "gads:interstitial_ad_pool:enabled", Boolean.valueOf(false));
    zzwO = Flag.zza(1, "gads:interstitial_ad_pool:schema", "customTargeting");
    zzwP = Flag.zza(1, "gads:interstitial_ad_pool:max_pools", 3);
    zzwQ = Flag.zza(1, "gads:interstitial_ad_pool:max_pool_depth", 2);
    zzwR = Flag.zza(1, "gads:interstitial_ad_pool:time_limit_sec", 1200);
    zzwS = Flag.zzc(1, "gads:interstitial_ad_pool:experiment_id");
    zzwT = Flag.zza(0, "gads:log:verbose_enabled", Boolean.valueOf(false));
    zzwU = Flag.zza(0, "gads:device_info_caching:enabled", Boolean.valueOf(true));
    zzwV = Flag.zza$219febed("gads:device_info_caching_expiry_ms:expiry", 300000L);
    zzwW = Flag.zza(0, "gads:gen204_signals:enabled", Boolean.valueOf(false));
    zzwX = Flag.zza(0, "gads:webview:error_reporting_enabled", Boolean.valueOf(false));
    zzwY = Flag.zza(0, "gads:adid_reporting:enabled", Boolean.valueOf(false));
    zzwZ = Flag.zza(0, "gads:ad_settings_page_reporting:enabled", Boolean.valueOf(false));
    zzxa = Flag.zza(0, "gads:adid_info_gmscore_upgrade_reporting:enabled", Boolean.valueOf(false));
    zzxb = Flag.zza(0, "gads:request_pkg:enabled", Boolean.valueOf(true));
    zzxc = Flag.zza(0, "gads:gmsg:disable_back_button:enabled", Boolean.valueOf(false));
    zzxd = Flag.zza$219febed("gads:network:cache_prediction_duration_s", 300L);
    zzxe = Flag.zza(0, "gads:mediation:dynamite_first", Boolean.valueOf(true));
    zzxf = Flag.zza$219febed("gads:ad_loader:timeout_ms", 60000L);
    zzxg = Flag.zza$219febed("gads:rendering:timeout_ms", 60000L);
    adShieldInstrumentationEnabled = Flag.zza(0, "gads:adshield:enable_adshield_instrumentation", Boolean.valueOf(false));
    gassEnabled = Flag.zza(0, "gass:enabled", Boolean.valueOf(false));
  }
  
  public static void initialize(Context paramContext)
  {
    zzja.zzb(new Callable()
    {
      private Void zzdv()
      {
        zzf localzzf = zzp.zzbR();
        Context localContext1 = this.zzsR;
        Context localContext2;
        synchronized (localzzf.zzqp)
        {
          if (localzzf.zzqM) {
            break label74;
          }
          localContext2 = GooglePlayServicesUtil.getRemoteContext(localContext1);
          if (localContext2 != null) {}
        }
        zzp.zzbP();
        localzzf.zzvG = localContext2.getSharedPreferences("google_ads_flags", 1);
        localzzf.zzqM = true;
        label74:
        return null;
      }
    });
  }
  
  public static List<String> zzdu()
  {
    zzd localzzd = zzp.zzbQ();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localzzd.zzvE.iterator();
    while (localIterator.hasNext())
    {
      Flag localFlag = (Flag)localIterator.next();
      String str = (String)zzp.zzbR().zzd(localFlag);
      if (str != null) {
        localArrayList.add(str);
      }
    }
    return localArrayList;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.config.Flags
 * JD-Core Version:    0.7.0.1
 */