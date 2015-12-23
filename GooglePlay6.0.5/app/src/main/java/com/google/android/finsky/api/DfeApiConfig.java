package com.google.android.finsky.api;

import com.google.android.play.utils.config.GservicesValue;

public final class DfeApiConfig
{
  public static final GservicesValue<Integer> adClickTimeoutMs;
  public static final GservicesValue<Integer> ageVerificationTimeoutMs;
  public static final GservicesValue<Long> androidId;
  public static final GservicesValue<String> authTokenType;
  public static final GservicesValue<Float> backupDevicesBackoffMultiplier;
  public static final GservicesValue<Integer> backupDevicesMaxRetries;
  public static final GservicesValue<Integer> backupDevicesTimeoutMs;
  public static final GservicesValue<Float> bulkDetailsBackoffMultiplier;
  public static final GservicesValue<Integer> bulkDetailsMaxRetries;
  public static final GservicesValue<Integer> bulkDetailsTimeoutMs;
  public static final GservicesValue<String> clientId;
  public static final GservicesValue<Boolean> consistencyTokenEnabled = GservicesValue.value("finsky.consistency_token_enabled", Boolean.valueOf(true));
  public static final GservicesValue<Long> contentFilterSettingsResponseTtlMs = GservicesValue.value("finsky.content_filter_settings_response_ttl_ms", Long.valueOf(604800000L));
  public static final GservicesValue<Float> dfeBackoffMultipler;
  public static final GservicesValue<Integer> dfeMaxRetries;
  public static final GservicesValue<Integer> dfeRequestTimeout2GMs;
  public static final GservicesValue<Integer> dfeRequestTimeout3GMs;
  public static final GservicesValue<Integer> dfeRequestTimeout4GMs;
  public static final GservicesValue<Integer> dfeRequestTimeoutMs;
  public static final GservicesValue<Integer> dfeRequestTimeoutWifiMs;
  public static final GservicesValue<Float> earlyUpdateBackoffMultiplier;
  public static final GservicesValue<Integer> earlyUpdateMaxRetries;
  public static final GservicesValue<Integer> earlyUpdateTimeoutMs;
  public static final GservicesValue<String> ipCountryOverride;
  public static final GservicesValue<Integer> logReferrerTimoutMs;
  public static final GservicesValue<String> loggingId;
  public static GservicesValue<Integer> maxVouchersInDetailsRequest;
  public static final GservicesValue<String> mccMncOverride = GservicesValue.value("finsky.mcc_mnc_override", null);
  public static final GservicesValue<Float> plusProfileBgBackoffMult;
  public static final GservicesValue<Integer> plusProfileBgMaxRetries;
  public static final GservicesValue<Integer> plusProfileBgTimeoutMs;
  public static final GservicesValue<Boolean> prexDisabled;
  public static final GservicesValue<String> protoLogUrlRegexp = GservicesValue.value("finsky.proto_log_url_regexp", ".*");
  public static GservicesValue<String> purchaseManagerEesSuffix = GservicesValue.value("finsky.purchase_manager_ees_suffix", "PhoneskyPM");
  public static final GservicesValue<Integer> purchaseStatusTimeoutMs;
  public static final GservicesValue<Float> replicateLibraryBackoffMultiplier;
  public static final GservicesValue<Integer> replicateLibraryMaxRetries;
  public static final GservicesValue<Integer> replicateLibraryTimeoutMs;
  public static final GservicesValue<Boolean> sendAdIdInRequestsForRads;
  public static final GservicesValue<Boolean> sendPublicAndroidIdInRequestsForRads = GservicesValue.value("finsky.send_public_android_id_in_requests_for_rads", Boolean.valueOf(true));
  public static final GservicesValue<Boolean> showStagingData;
  public static final GservicesValue<Boolean> skipAllCaches;
  public static final GservicesValue<Integer> verifyAssociationMaxRetries;
  public static final GservicesValue<Integer> verifyAssociationTimeoutMs;
  public static GservicesValue<Boolean> vouchersInDetailsRequestsEnabled;
  
  static
  {
    sendAdIdInRequestsForRads = GservicesValue.value("finsky.send_ad_id_in_requests_for_rads", Boolean.valueOf(true));
    dfeRequestTimeoutMs = GservicesValue.value("finsky.dfe_request_timeout_ms", Integer.valueOf(2500));
    dfeRequestTimeout2GMs = GservicesValue.value("finsky.dfe_request_timeout_2g_ms", Integer.valueOf(18000));
    dfeRequestTimeout3GMs = GservicesValue.value("finsky.dfe_request_timeout_3g_ms", Integer.valueOf(8000));
    dfeRequestTimeout4GMs = GservicesValue.value("finsky.dfe_request_timeout_4g_ms", Integer.valueOf(4000));
    dfeRequestTimeoutWifiMs = GservicesValue.value("finsky.dfe_request_timeout_wifi_ms", Integer.valueOf(4000));
    dfeMaxRetries = GservicesValue.value("finsky.dfe_max_retries", Integer.valueOf(1));
    dfeBackoffMultipler = GservicesValue.value("finsky.dfe_backoff_multiplier", Float.valueOf(1.0F));
    plusProfileBgTimeoutMs = GservicesValue.value("finsky.plus_profile_bg_timeout_ms", Integer.valueOf(8000));
    plusProfileBgMaxRetries = GservicesValue.value("finsky.plus_profile_bg_max_retries", Integer.valueOf(0));
    plusProfileBgBackoffMult = GservicesValue.value("finsky.plus_profile_bg_backoff_mult", Float.valueOf(1.0F));
    ipCountryOverride = GservicesValue.value("finsky.ip_country_override", null);
    androidId = GservicesValue.value("android_id", Long.valueOf(0L));
    authTokenType = GservicesValue.value("finsky.auth_token_type", "androidmarket");
    loggingId = GservicesValue.partnerSetting("logging_id2", "");
    clientId = GservicesValue.partnerSetting("market_client_id", "am-google");
    purchaseStatusTimeoutMs = GservicesValue.value("finsky.purchase_status_timeout_ms", Integer.valueOf(35000));
    ageVerificationTimeoutMs = GservicesValue.value("finsky.age_verification_timeout_ms", Integer.valueOf(35000));
    backupDevicesTimeoutMs = GservicesValue.value("finsky.backup_devices_timeout_ms", Integer.valueOf(15000));
    backupDevicesMaxRetries = GservicesValue.value("finsky.backup_devices_max_retries", Integer.valueOf(1));
    backupDevicesBackoffMultiplier = GservicesValue.value("finsky.backup_devices_backoff_multiplier", Float.valueOf(1.0F));
    bulkDetailsTimeoutMs = GservicesValue.value("finsky.bulk_details_timeout_ms", Integer.valueOf(30000));
    bulkDetailsMaxRetries = GservicesValue.value("finsky.bulk_details_max_retries", Integer.valueOf(1));
    bulkDetailsBackoffMultiplier = GservicesValue.value("finsky.bulk_details_backoff_multiplier", Float.valueOf(1.0F));
    verifyAssociationTimeoutMs = GservicesValue.value("finsky.verify_association_timeout_ms", Integer.valueOf(35000));
    verifyAssociationMaxRetries = GservicesValue.value("finsky.verify_association_max_retries", Integer.valueOf(0));
    replicateLibraryTimeoutMs = GservicesValue.value("finsky.replicate_library_timeout_ms", Integer.valueOf(30000));
    replicateLibraryMaxRetries = GservicesValue.value("finsky.replicate_library_max_retries", Integer.valueOf(0));
    replicateLibraryBackoffMultiplier = GservicesValue.value("finsky.replicate_library_backoff_multiplier", Float.valueOf(1.0F));
    earlyUpdateTimeoutMs = GservicesValue.value("finsky.early_update_timeout_ms", Integer.valueOf(2500));
    earlyUpdateMaxRetries = GservicesValue.value("finsky.early_update_max_retries", Integer.valueOf(1));
    earlyUpdateBackoffMultiplier = GservicesValue.value("finsky.early_update_backoff_multiplier", Float.valueOf(1.0F));
    adClickTimeoutMs = GservicesValue.value("finsky.ad_click_timeout_ms", Integer.valueOf(10000));
    logReferrerTimoutMs = GservicesValue.value("finsky.log_referrer_timeout_ms", Integer.valueOf(10000));
    skipAllCaches = GservicesValue.value("finsky.skip_all_caches", Boolean.valueOf(false));
    showStagingData = GservicesValue.value("finsky.show_staging_data", Boolean.valueOf(false));
    prexDisabled = GservicesValue.value("finsky.prex_disabled", Boolean.valueOf(false));
    vouchersInDetailsRequestsEnabled = GservicesValue.value("finsky.vouchers_in_details_requests_enabled", Boolean.valueOf(true));
    maxVouchersInDetailsRequest = GservicesValue.value("finsky.max_vouchers_in_details_request", Integer.valueOf(25));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.DfeApiConfig
 * JD-Core Version:    0.7.0.1
 */