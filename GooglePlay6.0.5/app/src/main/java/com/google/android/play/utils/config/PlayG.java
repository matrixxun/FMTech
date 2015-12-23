package com.google.android.play.utils.config;

public final class PlayG
{
  public static final String[] GSERVICES_KEY_PREFIXES = { "playcommon" };
  public static final GservicesValue<Long> androidId;
  public static final GservicesValue<String> authTokenType;
  public static GservicesValue<Integer> bitmapLoaderCacheSizeOverrideMb;
  public static GservicesValue<Float> bitmapLoaderCacheSizeRatioToScreen;
  public static final GservicesValue<String> clientId;
  public static GservicesValue<Boolean> debugImageSizes;
  public static final GservicesValue<Float> dfeBackoffMultipler;
  public static final GservicesValue<Integer> dfeMaxRetries;
  public static final GservicesValue<Integer> dfeRequestTimeoutMs;
  public static final GservicesValue<String> forceDisabledExperimentIds = GservicesValue.value("playcommon.force_disabled_experiment_ids", null);
  public static final GservicesValue<String> forceEnabledExperimentIds;
  public static GservicesValue<Integer> imageCacheSizeMb;
  public static final GservicesValue<String> ipCountryOverride;
  public static final GservicesValue<String> loggingId;
  public static GservicesValue<Integer> mainCacheSizeMb;
  public static final GservicesValue<String> mccMncOverride = GservicesValue.value("playcommon.mcc_mnc_override", null);
  public static final GservicesValue<Integer> minImageSizeLimitInLRUCacheBytes;
  public static final GservicesValue<Float> percentOfImageSize2G;
  public static final GservicesValue<Float> percentOfImageSize3G;
  public static final GservicesValue<Float> percentOfImageSize4G;
  public static final GservicesValue<Float> percentOfImageSizeWifi;
  public static final GservicesValue<Float> plusProfileBgBackoffMult;
  public static final GservicesValue<Integer> plusProfileBgMaxRetries;
  public static final GservicesValue<Integer> plusProfileBgTimeoutMs;
  public static final GservicesValue<Boolean> prexDisabled;
  public static final GservicesValue<String> protoLogUrlRegexp = GservicesValue.value("playcommon.proto_log_url_regexp", ".*");
  public static final GservicesValue<Boolean> showStagingData;
  public static final GservicesValue<Boolean> skipAllCaches;
  public static final GservicesValue<Boolean> tentativeGcRunnerEnabled;
  public static final GservicesValue<Integer> volleyBufferPoolSizeKb;
  public static final GservicesValue<Boolean> webpFifeImagesEnabled;
  
  static
  {
    dfeRequestTimeoutMs = GservicesValue.value("playcommon.dfe_request_timeout_ms", Integer.valueOf(2500));
    dfeMaxRetries = GservicesValue.value("playcommon.dfe_max_retries", Integer.valueOf(1));
    dfeBackoffMultipler = GservicesValue.value("playcommon.dfe_backoff_multiplier", Float.valueOf(1.0F));
    plusProfileBgTimeoutMs = GservicesValue.value("playcommon.plus_profile_bg_timeout_ms", Integer.valueOf(8000));
    plusProfileBgMaxRetries = GservicesValue.value("playcommon.plus_profile_bg_max_retries", Integer.valueOf(0));
    plusProfileBgBackoffMult = GservicesValue.value("playcommon.plus_profile_bg_backoff_mult", Float.valueOf(1.0F));
    ipCountryOverride = GservicesValue.value("playcommon.ip_country_override", null);
    androidId = GservicesValue.value("android_id", Long.valueOf(0L));
    authTokenType = GservicesValue.value("playcommon.auth_token_type", "androidmarket");
    loggingId = GservicesValue.partnerSetting("logging_id2", "");
    clientId = GservicesValue.partnerSetting("market_client_id", "am-google");
    skipAllCaches = GservicesValue.value("playcommon.skip_all_caches", Boolean.valueOf(false));
    showStagingData = GservicesValue.value("playcommon.show_staging_data", Boolean.valueOf(false));
    prexDisabled = GservicesValue.value("playcommon.prex_disabled", Boolean.valueOf(false));
    tentativeGcRunnerEnabled = GservicesValue.value("playcommon.tentative_gc_runner_enabled", Boolean.valueOf(true));
    bitmapLoaderCacheSizeOverrideMb = GservicesValue.value("playcommon.bitmap_loader_cache_size_mb", Integer.valueOf(-1));
    bitmapLoaderCacheSizeRatioToScreen = GservicesValue.value("playcommon.bitmap_loader_cache_size_ratio_to_screen", Float.valueOf(1.5F));
    minImageSizeLimitInLRUCacheBytes = GservicesValue.value("playcommon.min_image_size_limit_in_lru_cache_bytes", Integer.valueOf(524288));
    debugImageSizes = GservicesValue.value("playcommon.debug_display_image_sizes", Boolean.valueOf(false));
    webpFifeImagesEnabled = GservicesValue.value("playcommon.webp_fife_images_enabled", Boolean.valueOf(true));
    percentOfImageSizeWifi = GservicesValue.value("playcommon.percent_of_image_size_wifi", Float.valueOf(1.0F));
    percentOfImageSize4G = GservicesValue.value("playcommon.percent_of_image_size_4g", Float.valueOf(0.9F));
    percentOfImageSize3G = GservicesValue.value("playcommon.percent_of_image_size_3g", Float.valueOf(0.75F));
    percentOfImageSize2G = GservicesValue.value("playcommon.percent_of_image_size_2g", Float.valueOf(0.45F));
    mainCacheSizeMb = GservicesValue.value("playcommon.main_cache_size_mb", Integer.valueOf(1));
    imageCacheSizeMb = GservicesValue.value("playcommon.image_cache_size_mb", Integer.valueOf(4));
    volleyBufferPoolSizeKb = GservicesValue.value("playcommon.volley_buffer_pool_size_kb", Integer.valueOf(256));
    forceEnabledExperimentIds = GservicesValue.value("playcommon.force_enabled_experiment_ids", null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.utils.config.PlayG
 * JD-Core Version:    0.7.0.1
 */