package com.google.android.wallet.config;

import com.google.android.gsf.GservicesValue;

public final class G
{
  public static final GservicesValue<Boolean> allowPiiLogging;
  public static final GservicesValue<Long> androidId = GservicesValue.value("android_id", Long.valueOf(0L));
  public static final GservicesValue<String> ccNfcInstructionImageFifeUrl = GservicesValue.value("wallet.cc.nfc_instruction_image_fife_url", "http://lh3.googleusercontent.com/dIbnZiUGsjCyFvM3jBxzTNypwyNrgu3V4Ov29pKjwG-wPLT9EOxMpzs1y6wVp-JDGczRRA3D-w");
  public static final GservicesValue<Integer> ccNfcReadingTimeoutMs;
  public static final GservicesValue<Integer> deviceAddressSourceThresholdDefault;
  public static final GservicesValue<Integer> existingProfileAddressSourceThresholdDefault;
  public static final GservicesValue<String> marketClientId = GservicesValue.partnerSetting("market_client_id", "am-google");
  public static final GservicesValue<String> mccMncOverride = GservicesValue.value("wallet.mcc_mnc_override", null);
  public static final GservicesValue<Integer> minApiLevelToShowAutocompleteForAccessibility;
  public static final GservicesValue<Integer> pageImpressionDelayBeforeTrackingMs;
  public static final GservicesValue<Integer> volleyApiRequestDefaultTimeoutMs;
  
  static
  {
    deviceAddressSourceThresholdDefault = GservicesValue.value("wallet.device_addresses.threshold_default", Integer.valueOf(1));
    existingProfileAddressSourceThresholdDefault = GservicesValue.value("wallet.existing_profile.threshold_default", Integer.valueOf(0));
    minApiLevelToShowAutocompleteForAccessibility = GservicesValue.value("wallet.accessibility.min_api_level_to_show_autocomplete", Integer.valueOf(2147483647));
    allowPiiLogging = GservicesValue.value("wallet.allow_pii_logging", false);
    volleyApiRequestDefaultTimeoutMs = GservicesValue.value("wallet.volley_api_request_default_timeout", Integer.valueOf(10000));
    pageImpressionDelayBeforeTrackingMs = GservicesValue.value("wallet.page_impression_delay_before_tracking_ms", Integer.valueOf(100));
    ccNfcReadingTimeoutMs = GservicesValue.value("wallet.cc.nfc_reading_timeout_ms", Integer.valueOf(2500));
  }
  
  public static final class dcb
  {
    public static final GservicesValue<Integer> verifyAssociationRetries = GservicesValue.value("wallet.dcb.verify_association_retries", Integer.valueOf(0));
    public static final GservicesValue<Integer> verifyAssociationRetryDelayMs = GservicesValue.value("wallet.dcb.verify_association_retry_delay_ms", Integer.valueOf(3000));
  }
  
  public static final class googleplaces
  {
    public static final GservicesValue<String> supportedCountries = GservicesValue.value("wallet.google_places_autocomplete_supported_countries", "CA,FR,DE,US");
    public static final GservicesValue<Integer> thresholdAddressLine1 = GservicesValue.value("wallet.google_places_autocomplete_threshold_address_line_1", Integer.valueOf(4));
    public static final GservicesValue<Integer> thresholdDefault = GservicesValue.value("wallet.google_places_autocomplete_threshold_default", Integer.valueOf(2));
  }
  
  public static final class images
  {
    public static final GservicesValue<Integer> diskCacheSizeBytes = GservicesValue.value("wallet.images.disk_cache_size_bytes", Integer.valueOf(2097152));
    public static final GservicesValue<Integer> inMemoryCacheSizeDp = GservicesValue.value("wallet.images.in_memory_cache_size_dp", Integer.valueOf(9600));
    public static final GservicesValue<Boolean> useWebPForFife = GservicesValue.value("wallet.images.use_webp_for_fife", true);
  }
  
  public static final class redirectwebviewlogs
  {
    public static final GservicesValue<Boolean> logResourceRequests = GservicesValue.value("wallet.redirect.log_resource_requests", true);
    public static final GservicesValue<Boolean> loggingEnabled = GservicesValue.value("wallet.redirect.logging_enabled", true);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.config.G
 * JD-Core Version:    0.7.0.1
 */