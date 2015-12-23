package com.google.android.finsky.utils;

import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;

public final class FinskyPreferences
{
  public static final PreferenceFile.SharedPreference<Boolean> acceptedAntiMalwareConsent;
  public static final PreferenceFile.PrefixSharedPreference<Boolean> acceptedPlusReviews;
  public static final PreferenceFile.PrefixSharedPreference<String> acceptedTosToken;
  public static final PreferenceFile.PrefixSharedPreference<Integer> accountCompletionMainMenuDotTapCount = sPrefs.prefixPreference("account-completion-main-menu-tap-count", Integer.valueOf(0));
  public static final PreferenceFile.PrefixSharedPreference<Integer> accountCompletionMyAccountLinkTapCount = sPrefs.prefixPreference("account-completion-my-account-tap-count", Integer.valueOf(0));
  public static final PreferenceFile.PrefixSharedPreference<Integer> accountCompletionPaymentsCardShowCount = sPrefs.prefixPreference("account-completion-payments-card-show-count", Integer.valueOf(0));
  public static final PreferenceFile.PrefixSharedPreference<Boolean> accountHasFop;
  public static final PreferenceFile.PrefixSharedPreference<Long> accountHasFopLastUpdateMs;
  public static final PreferenceFile.SharedPreference<Long> adIdCachedTimestampMs;
  public static final PreferenceFile.SharedPreference<String> adIdCachedValue;
  public static final PreferenceFile.SharedPreference<Boolean> adIdLimitTrackingCachedValue;
  public static final PreferenceFile.PrefixSharedPreference<String> autoAcquireTags;
  public static final PreferenceFile.SharedPreference<Boolean> autoUpdateEnabled;
  public static final PreferenceFile.SharedPreference<Boolean> autoUpdateFirstTimeForAccounts;
  public static final PreferenceFile.SharedPreference<Integer> autoUpdateMigrationDialogShownCount;
  public static final PreferenceFile.SharedPreference<Boolean> autoUpdateWifiOnly;
  public static final PreferenceFile.SharedPreference<Long> autoUpdatesDiscoveredAtMs;
  public static final PreferenceFile.SharedPreference<Integer> contentFilterLevel;
  public static final PreferenceFile.SharedPreference<String> contentFilters2;
  public static final PreferenceFile.SharedPreference<String> contentFilters2Selections;
  public static final PreferenceFile.SharedPreference<Boolean> contentFilters2VisitedOnce;
  public static final PreferenceFile.SharedPreference<String> contentPin;
  public static final PreferenceFile.SharedPreference<String> currentAccount;
  public static final PreferenceFile.SharedPreference<Integer> dailyHygieneFailedCount;
  public static final PreferenceFile.SharedPreference<Boolean> dailyHygieneHoldoffComplete;
  public static final PreferenceFile.SharedPreference<Long> dailyHygieneLastSuccessMs;
  public static PreferenceFile.SharedPreference<String> debugLocationOverride;
  public static final PreferenceFile.SharedPreference<String> deviceConfigToken;
  public static final PreferenceFile.SharedPreference<Long> deviceDataPartitionInBytes;
  public static final PreferenceFile.SharedPreference<String> dfeNotificationPendingAcks;
  public static final PreferenceFile.SharedPreference<Boolean> directedRestoreStarted;
  public static final PreferenceFile.SharedPreference<Boolean> dismissedMyAccountNewsstandMessage;
  public static PreferenceFile.PrefixSharedPreference<String> dismissedSurveyIds;
  public static final PreferenceFile.SharedPreference<Boolean> downloadManagerUsesFroyoStrings;
  public static final PreferenceFile.SharedPreference<Integer> downloadNetworkPreference;
  public static final PreferenceFile.SharedPreference<Boolean> downloadNetworkSettingsMessageShown;
  public static final PreferenceFile.PrefixSharedPreference<Boolean> earlyUpdateSkipPackage;
  public static final PreferenceFile.SharedPreference<String> gcmRegistrationIdOnServer;
  public static final PreferenceFile.PrefixSharedPreference<Integer> gmailSuggestionsEnabled;
  public static final PreferenceFile.SharedPreference<Boolean> hadPreJsAutoUpdateSettings;
  public static final PreferenceFile.SharedPreference<Boolean> hasAcceptedAutoUpdateMigrationDialog;
  public static final PreferenceFile.PrefixSharedPreference<Boolean> hasSeenPurchaseSessionMessage;
  public static final PreferenceFile.SharedPreference<Boolean> hasShownEntertainmentOnboarding;
  public static final PreferenceFile.SharedPreference<Boolean> hasStaleProcessStableTargets;
  public static final PreferenceFile.SharedPreference<Integer> installationReplicationRetries;
  public static final PreferenceFile.SharedPreference<Boolean> internalEventlogDebuggingEnabled;
  public static final PreferenceFile.SharedPreference<Boolean> internalFakeItemRaterEnabled;
  public static final PreferenceFile.SharedPreference<Boolean> internalShowAllSurveys;
  @Deprecated
  public static final PreferenceFile.PrefixSharedPreference<Boolean> isGaiaAuthOptedOut;
  public static final PreferenceFile.PrefixSharedPreference<Long> lastAutomaticHeroSequenceOnDetailsTimeShown;
  public static final PreferenceFile.SharedPreference<Long> lastDeviceFeatureLoggedTimestampMs;
  public static final PreferenceFile.PrefixSharedPreference<Long> lastGaiaAuthTimestamp;
  public static final PreferenceFile.SharedPreference<Integer> lastReplicatedDatabaseVersion;
  public static final PreferenceFile.PrefixSharedPreference<Long> lastSurveyActionMs;
  public static final PreferenceFile.SharedPreference<Long> lastTimeBackStackUpdatedMs;
  public static final PreferenceFile.SharedPreference<Long> lastUpdateAvailNotificationTimestampMs;
  public static final PreferenceFile.SharedPreference<Long> lastUpdateMigrationDialogTimeShown;
  public static final PreferenceFile.PrefixSharedPreference<String> libraryWidgetData;
  public static final PreferenceFile.PrefixSharedPreference<Integer> locationSuggestionsEnabled;
  public static PreferenceFile.SharedPreference<Long> logsFlushScheduleExpiredTimestampMs;
  public static final PreferenceFile.SharedPreference<String> myLibraryWidgetBottomAffinity;
  public static final PreferenceFile.SharedPreference<String> myLibraryWidgetLeftAffinity;
  public static final PreferenceFile.SharedPreference<String> myLibraryWidgetRightAffinity;
  public static final PreferenceFile.SharedPreference<String> myLibraryWidgetTopAffinity;
  public static final PreferenceFile.SharedPreference<Integer> nlpCleanupConfigurationId;
  public static final PreferenceFile.SharedPreference<Boolean> nlpCleanupHoldoffAfterInstall;
  public static final PreferenceFile.SharedPreference<Boolean> nlpCleanupHoldoffUntilBoot;
  public static PreferenceFile.SharedPreference<Boolean> optionalPermissionsHelpScreenShown;
  public static final PreferenceFile.SharedPreference<Long> playGamesInstallSuggestionLastTimeShown;
  public static final PreferenceFile.SharedPreference<Integer> playGamesInstallSuggestionShownCount;
  public static final PreferenceFile.SharedPreference<String> preregistrationAcknowledgedDocs;
  public static final PreferenceFile.PrefixSharedPreference<Long> preregistrationAcknowledgedMs;
  public static final PreferenceFile.PrefixSharedPreference<Boolean> promptForFopAddedFop;
  public static final PreferenceFile.PrefixSharedPreference<Long> promptForFopLastSnoozedTimestampMs;
  public static final PreferenceFile.PrefixSharedPreference<Integer> promptForFopNumDialogImpressions;
  public static final PreferenceFile.PrefixSharedPreference<Integer> promptForFopNumFopSelectorImpressions;
  public static final PreferenceFile.PrefixSharedPreference<Integer> promptForFopNumSnoozed;
  public static final PreferenceFile.PrefixSharedPreference<Integer> promptForFopNumSnoozed2;
  public static final PreferenceFile.PrefixSharedPreference<Integer> purchaseAuthType;
  public static final PreferenceFile.PrefixSharedPreference<Integer> purchaseAuthVersionCode;
  public static final PreferenceFile.PrefixSharedPreference<Boolean> receiveEmails;
  public static final PreferenceFile.PrefixSharedPreference<Integer> replicatedAccountAppsHash;
  public static final PreferenceFile.PrefixSharedPreference<Integer> replicatedSystemAppsHash;
  private static final PreferenceFile sPrefs;
  public static PreferenceFile.PrefixSharedPreference<String> serverCookies;
  public static final PreferenceFile.SharedPreference<Boolean> setupWizardStartDownloads;
  public static final PreferenceFile.SharedPreference<String> successfulUpdateNotificationAppList;
  public static final PreferenceFile.PrefixSharedPreference<String> targetList;
  public static final PreferenceFile.PrefixSharedPreference<String> targetOverrideList;
  public static PreferenceFile.PrefixSharedPreference<String> tocCookie;
  public static final PreferenceFile.PrefixSharedPreference<Boolean> useFingerprintForPurchase;
  public static final PreferenceFile.PrefixSharedPreference<Integer> userSettingNoticeSeenCount;
  public static PreferenceFile.PrefixSharedPreference<String> userSettingsCache;
  public static PreferenceFile.PrefixSharedPreference<Boolean> userSettingsCacheDirty;
  public static PreferenceFile.PrefixSharedPreference<String> userSettingsConsistencyTokens;
  public static final PreferenceFile.SharedPreference<Long> verifyInstalledPackagesLastSuccessMs;
  public static final PreferenceFile.SharedPreference<Integer> versionCode;
  public static final PreferenceFile.SharedPreference<Boolean> vpaTriggered;
  public static final PreferenceFile.SharedPreference<Boolean> warmWelcomeOwnProfileAcknowledged;
  public static PreferenceFile.PrefixSharedPreference<String> wearDeviceConfigToken;
  public static final PreferenceFile.PrefixSharedPreference<Long> wearDeviceLastSeenMs;
  public static final PreferenceFile.SharedPreference<String> wearKnownDevices;
  public static final PreferenceFile.PrefixSharedPreference<String> widgetUrlsByBackend;
  
  static
  {
    PreferenceFile localPreferenceFile = new PreferenceFile("finsky");
    sPrefs = localPreferenceFile;
    versionCode = localPreferenceFile.value("last_version_code", Integer.valueOf(-1));
    contentFilterLevel = sPrefs.value("content-filter-level", Integer.valueOf(-1));
    contentFilters2 = sPrefs.value("content-filters-2", "");
    contentFilters2Selections = sPrefs.value("content-filters-selections", null);
    contentFilters2VisitedOnce = sPrefs.value("content-filter-2-visited-once", Boolean.valueOf(false));
    currentAccount = sPrefs.value("account", null);
    contentPin = sPrefs.value("pin_code", null);
    hadPreJsAutoUpdateSettings = sPrefs.value("had-pre-js-auto-update-settings", Boolean.valueOf(false));
    hasAcceptedAutoUpdateMigrationDialog = sPrefs.value("update-migration-dialog-accepted", Boolean.valueOf(false));
    autoUpdateMigrationDialogShownCount = sPrefs.value("update-migration-dialog-times-shown-count", Integer.valueOf(0));
    lastUpdateMigrationDialogTimeShown = sPrefs.value("last-update-migration-dialog-times-shown", Long.valueOf(0L));
    lastDeviceFeatureLoggedTimestampMs = sPrefs.value("last-device-feature-logged-timestamp-ms", Long.valueOf(0L));
    playGamesInstallSuggestionShownCount = sPrefs.value("play-games-install-suggestion-shown-count", Integer.valueOf(0));
    playGamesInstallSuggestionLastTimeShown = sPrefs.value("play-games-install-suggestion-last-time-shown", Long.valueOf(0L));
    deviceConfigToken = sPrefs.value("device-config-token", null);
    gcmRegistrationIdOnServer = sPrefs.value("gcm-registration-id-on-server", null);
    dailyHygieneFailedCount = sPrefs.value("dailyhygiene-failed", Integer.valueOf(0));
    dailyHygieneLastSuccessMs = sPrefs.value("dailyhygiene-lastsuccess", Long.valueOf(0L));
    dailyHygieneHoldoffComplete = sPrefs.value("dailyhygiene-holdoff-complete", Boolean.valueOf(false));
    autoUpdateEnabled = sPrefs.value("auto_update_enabled", Boolean.valueOf(true));
    autoUpdateWifiOnly = sPrefs.value("update_over_wifi_only", Boolean.valueOf(true));
    downloadNetworkPreference = sPrefs.value("download_network_preference", Integer.valueOf(1));
    downloadNetworkSettingsMessageShown = sPrefs.value("download_network_settings_message_shown", Boolean.valueOf(false));
    installationReplicationRetries = sPrefs.value("installation-replication-retries", Integer.valueOf(0));
    dfeNotificationPendingAcks = sPrefs.value("dfe-notification-pending-acks", null);
    lastReplicatedDatabaseVersion = sPrefs.value("last-replicated-database-version", Integer.valueOf(-1));
    replicatedSystemAppsHash = sPrefs.prefixPreference("replicated-system-apps-hash", Integer.valueOf(0));
    acceptedTosToken = sPrefs.prefixPreference("last-tos-token", "");
    acceptedAntiMalwareConsent = sPrefs.value("accepted-anti-malware-consent", Boolean.valueOf(false));
    verifyInstalledPackagesLastSuccessMs = sPrefs.value("verify-installed-apps-last-success-ms", Long.valueOf(0L));
    replicatedAccountAppsHash = sPrefs.prefixPreference("replicated-account-apps-hash:", Integer.valueOf(0));
    accountHasFop = sPrefs.prefixPreference("has-fop", Boolean.valueOf(false));
    accountHasFopLastUpdateMs = sPrefs.prefixPreference("has-fop-last-update-ms", Long.valueOf(0L));
    promptForFopNumSnoozed = sPrefs.prefixPreference("prompt-for-fop-num-snoozed", Integer.valueOf(0));
    promptForFopNumSnoozed2 = sPrefs.prefixPreference("prompt-for-fop-num-snoozed-2", Integer.valueOf(0));
    promptForFopLastSnoozedTimestampMs = sPrefs.prefixPreference("prompt-for-fop-last-snooze-timestamp-ms", Long.valueOf(0L));
    promptForFopNumDialogImpressions = sPrefs.prefixPreference("prompt-for-fop-num-dialog-impressions", Integer.valueOf(0));
    promptForFopNumFopSelectorImpressions = sPrefs.prefixPreference("prompt-for-fop-num-fop-selector-impressions", Integer.valueOf(0));
    promptForFopAddedFop = sPrefs.prefixPreference("prompt-for-fop-fop-added", Boolean.valueOf(false));
    lastGaiaAuthTimestamp = sPrefs.prefixPreference("last-gaia-auth-timestamp", Long.valueOf(0L));
    isGaiaAuthOptedOut = sPrefs.prefixPreference("gaia-auth-opt-out", null);
    purchaseAuthType = sPrefs.prefixPreference("purchase-auth-type", Integer.valueOf(-1));
    purchaseAuthVersionCode = sPrefs.prefixPreference("purchase-auth-version-code", null);
    targetList = sPrefs.prefixPreference("target-list", null);
    targetOverrideList = sPrefs.prefixPreference("target-override-list", null);
    dismissedSurveyIds = sPrefs.prefixPreference("dismissed-surveys-list", null);
    autoAcquireTags = sPrefs.prefixPreference("autoAcquireTags", null);
    preregistrationAcknowledgedDocs = sPrefs.value("preregistration-released-docs", null);
    preregistrationAcknowledgedMs = sPrefs.prefixPreference("preregistration-released-ms", Long.valueOf(0L));
    myLibraryWidgetTopAffinity = sPrefs.value("my-library-widget-top-affinities", "");
    myLibraryWidgetBottomAffinity = sPrefs.value("my-library-widget-bottom-affinities", "");
    myLibraryWidgetLeftAffinity = sPrefs.value("my-library-widget-left-affinities", "");
    myLibraryWidgetRightAffinity = sPrefs.value("my-library-widget-right-affinities", "");
    downloadManagerUsesFroyoStrings = sPrefs.value("download-manager-uses-froyo-strings", Boolean.valueOf(false));
    acceptedPlusReviews = sPrefs.prefixPreference("accepted-plus-reviews", Boolean.valueOf(false));
    nlpCleanupConfigurationId = sPrefs.value("nlp-cleanup-config-id", Integer.valueOf(0));
    nlpCleanupHoldoffUntilBoot = sPrefs.value("nlp-cleanup-config-holdoff-until-boot", Boolean.valueOf(false));
    nlpCleanupHoldoffAfterInstall = sPrefs.value("nlp-cleanup-config-holdoff-after-install", Boolean.valueOf(false));
    widgetUrlsByBackend = sPrefs.prefixPreference("widgetUrl", null);
    libraryWidgetData = sPrefs.prefixPreference("libraryWidgetData", null);
    successfulUpdateNotificationAppList = sPrefs.value("successful-update-notification-app-list", "");
    lastUpdateAvailNotificationTimestampMs = sPrefs.value("last-update-avail-notification-timestamp-ms", Long.valueOf(0L));
    autoUpdatesDiscoveredAtMs = sPrefs.value("auto-update-discovered-at-ms", Long.valueOf(0L));
    warmWelcomeOwnProfileAcknowledged = sPrefs.value("warm-welcome-own-profile-acknowledged", Boolean.valueOf(false));
    internalShowAllSurveys = sPrefs.value("internal-show-all-surveys", Boolean.valueOf(false));
    internalFakeItemRaterEnabled = sPrefs.value("internal-fake-item-rater-enabled", Boolean.valueOf(false));
    internalEventlogDebuggingEnabled = sPrefs.value("internal-event-log-debugging-enabled", Boolean.valueOf(false));
    autoUpdateFirstTimeForAccounts = sPrefs.value("auto-update-first-time-for-accounts", Boolean.valueOf(true));
    hasSeenPurchaseSessionMessage = sPrefs.prefixPreference("has-seen-purchase-session-message", Boolean.valueOf(false));
    lastAutomaticHeroSequenceOnDetailsTimeShown = sPrefs.prefixPreference("last-automatic-hero-sequence-on-details-time-shown", Long.valueOf(0L));
    directedRestoreStarted = sPrefs.value("directed-restore-started", Boolean.valueOf(false));
    earlyUpdateSkipPackage = sPrefs.prefixPreference("early-update-skip-package", Boolean.valueOf(false));
    setupWizardStartDownloads = sPrefs.value("setup-wizard-start-downloads", Boolean.valueOf(false));
    vpaTriggered = sPrefs.value("vpa-triggered", Boolean.valueOf(false));
    lastTimeBackStackUpdatedMs = sPrefs.value("last-time-back-stack-updated-ms", Long.valueOf(9223372036854775807L));
    lastSurveyActionMs = sPrefs.prefixPreference("last-survey-action-ms", null);
    wearKnownDevices = sPrefs.value("wear-known-devices", null);
    wearDeviceLastSeenMs = sPrefs.prefixPreference("wear-device-last-seen", Long.valueOf(0L));
    deviceDataPartitionInBytes = sPrefs.value("device-data-partition-bytes", Long.valueOf(-1L));
    wearDeviceConfigToken = sPrefs.prefixPreference("wear-device-config-token", null);
    locationSuggestionsEnabled = sPrefs.prefixPreference("location-suggestions", Integer.valueOf(0));
    gmailSuggestionsEnabled = sPrefs.prefixPreference("gmail-suggestions", Integer.valueOf(0));
    userSettingNoticeSeenCount = sPrefs.prefixPreference("user-setting-seen-count", Integer.valueOf(0));
    receiveEmails = sPrefs.prefixPreference("receive_emails", Boolean.valueOf(false));
    tocCookie = sPrefs.prefixPreference("toc-cookie", null);
    dismissedMyAccountNewsstandMessage = sPrefs.value("dismissed-my-account-newsstand-message", Boolean.valueOf(false));
    debugLocationOverride = sPrefs.value("location-override", null);
    optionalPermissionsHelpScreenShown = sPrefs.value("optional-permissions-help-screen-shown", Boolean.valueOf(false));
    userSettingsCache = sPrefs.prefixPreference("user-settings-cache", null);
    serverCookies = sPrefs.prefixPreference("server-cookies", null);
    userSettingsCacheDirty = sPrefs.prefixPreference("user-settings-cache-dirty", Boolean.valueOf(true));
    userSettingsConsistencyTokens = sPrefs.prefixPreference("user-settings-consistency-tokens", null);
    adIdCachedValue = sPrefs.value("adid-cached-value", null);
    adIdLimitTrackingCachedValue = sPrefs.value("adid-limit-tracking-cached-value", null);
    adIdCachedTimestampMs = sPrefs.value("adid-cached-timestamp-ms", Long.valueOf(0L));
    useFingerprintForPurchase = sPrefs.prefixPreference("use-fingerprint-for-purchase", Boolean.valueOf(false));
    logsFlushScheduleExpiredTimestampMs = sPrefs.value("logs-flush-schedule-expired-timestamp", Long.valueOf(0L));
    hasShownEntertainmentOnboarding = sPrefs.value("has_shown_entertainment_onboarding", Boolean.valueOf(false));
    hasStaleProcessStableTargets = sPrefs.value("has-stale-process-stable-targets", Boolean.valueOf(false));
  }
  
  public static void clear()
  {
    sPrefs.clear();
  }
  
  public static PreferenceFile.PrefixSharedPreference<String> getLibraryServerToken(String paramString)
  {
    return sPrefs.prefixPreference("server_token:" + paramString + ":", "");
  }
  
  public static PreferenceFile getPreferencesFile()
  {
    return sPrefs;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.FinskyPreferences
 * JD-Core Version:    0.7.0.1
 */