package com.google.android.finsky.api;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.VoucherParams;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.AcceptTosResponse;
import com.google.android.finsky.protos.AckNotificationResponse;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Browse.BrowseResponse;
import com.google.android.finsky.protos.Buy.BuyResponse;
import com.google.android.finsky.protos.BuyInstruments.BillingProfileResponse;
import com.google.android.finsky.protos.BuyInstruments.CheckIabPromoResponse;
import com.google.android.finsky.protos.BuyInstruments.CheckInstrumentResponse;
import com.google.android.finsky.protos.BuyInstruments.UpdateInstrumentRequest;
import com.google.android.finsky.protos.BuyInstruments.UpdateInstrumentResponse;
import com.google.android.finsky.protos.CarrierBilling.InitiateAssociationResponse;
import com.google.android.finsky.protos.CarrierBilling.VerifyAssociationResponse;
import com.google.android.finsky.protos.ChallengeResponse;
import com.google.android.finsky.protos.CheckPromoOfferResponse;
import com.google.android.finsky.protos.ConsumePurchaseResponse;
import com.google.android.finsky.protos.ContentFilters.ContentFilterSettingsResponse;
import com.google.android.finsky.protos.ContentFlagging.FlagContentResponse;
import com.google.android.finsky.protos.DebugSettingsResponse;
import com.google.android.finsky.protos.DeliveryResponse;
import com.google.android.finsky.protos.Details.BulkDetailsRequest;
import com.google.android.finsky.protos.Details.BulkDetailsResponse;
import com.google.android.finsky.protos.Details.DetailsResponse;
import com.google.android.finsky.protos.DeviceConfiguration.DeviceConfigurationProto;
import com.google.android.finsky.protos.DismissSurveyResponse;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.EarlyUpdateRequest;
import com.google.android.finsky.protos.EarlyUpdateResponse;
import com.google.android.finsky.protos.FamilyFopRequest;
import com.google.android.finsky.protos.FamilyFopResponse;
import com.google.android.finsky.protos.FamilyPurchaseSetting;
import com.google.android.finsky.protos.GetFamilyPurchaseSettingResponse;
import com.google.android.finsky.protos.GetUserSettingsResponse;
import com.google.android.finsky.protos.LibraryReplicationRequest;
import com.google.android.finsky.protos.LibraryReplicationResponse;
import com.google.android.finsky.protos.ListResponse;
import com.google.android.finsky.protos.Log.LogRequest;
import com.google.android.finsky.protos.Log.LogResponse;
import com.google.android.finsky.protos.MarketingSettings;
import com.google.android.finsky.protos.ModifyLibrary.ModifyLibraryRequest;
import com.google.android.finsky.protos.ModifyLibrary.ModifyLibraryResponse;
import com.google.android.finsky.protos.MyAccountResponse;
import com.google.android.finsky.protos.PingResponse;
import com.google.android.finsky.protos.PlusOneResponse;
import com.google.android.finsky.protos.Preloads.PreloadsResponse;
import com.google.android.finsky.protos.PrivacySetting;
import com.google.android.finsky.protos.PromoCode.RedeemCodeRequest;
import com.google.android.finsky.protos.PromoCode.RedeemCodeResponse;
import com.google.android.finsky.protos.Purchase.AuthenticationInfo;
import com.google.android.finsky.protos.Purchase.CommitPurchaseResponse;
import com.google.android.finsky.protos.Purchase.DcbPurchaseInfo;
import com.google.android.finsky.protos.Purchase.InAppPurchaseInfo;
import com.google.android.finsky.protos.Purchase.InstantPurchaseRequest;
import com.google.android.finsky.protos.Purchase.InstantPurchaseResponse;
import com.google.android.finsky.protos.Purchase.PreparePurchaseResponse;
import com.google.android.finsky.protos.RateSuggestedContentResponse;
import com.google.android.finsky.protos.ResolveLink.ResolvedLink;
import com.google.android.finsky.protos.Restore.GetBackupDeviceChoicesResponse;
import com.google.android.finsky.protos.Restore.GetBackupDocumentChoicesResponse;
import com.google.android.finsky.protos.ReviewResponse;
import com.google.android.finsky.protos.ReviewSnippetsResponse;
import com.google.android.finsky.protos.RevokeResponse;
import com.google.android.finsky.protos.Search.SearchResponse;
import com.google.android.finsky.protos.SearchSuggest.SearchSuggestResponse;
import com.google.android.finsky.protos.SelfUpdateResponse;
import com.google.android.finsky.protos.SetFamilyPurchaseSettingRequest;
import com.google.android.finsky.protos.SetFamilyPurchaseSettingResponse;
import com.google.android.finsky.protos.SharingSetting.FamilySharingSetting;
import com.google.android.finsky.protos.SharingSetting.GetFamilySharingSettingsResponse;
import com.google.android.finsky.protos.SharingSetting.UpdateFamilySharingSettingsRequest;
import com.google.android.finsky.protos.SharingSetting.UpdateFamilySharingSettingsResponse;
import com.google.android.finsky.protos.SurveyResponse;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.protos.UpdateUserSettingRequest;
import com.google.android.finsky.protos.UpdateUserSettingResponse;
import com.google.android.finsky.protos.UploadDeviceConfig.UploadDeviceConfigRequest;
import com.google.android.finsky.protos.UploadDeviceConfig.UploadDeviceConfigResponse;
import com.google.android.finsky.protos.UserContext;
import com.google.android.finsky.protos.UserSettingsConsistencyTokens;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.gms.ads.identifier.AdIdProvider;
import com.google.android.play.utils.NetworkInfoUtil;
import com.google.android.play.utils.config.GservicesValue;
import com.google.protobuf.nano.MessageNano;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class DfeApiImpl
  implements DfeApi
{
  private static final int AGE_VERIFICATION_TIMEOUT_MS;
  private static final float BACKUP_DEVICES_BACKOFF_MULT;
  private static final int BACKUP_DEVICES_MAX_RETRIES;
  private static final int BACKUP_DEVICES_TIMEOUT_MS;
  private static final float BULK_DETAILS_BACKOFF_MULT;
  private static final int BULK_DETAILS_MAX_RETRIES;
  private static final int BULK_DETAILS_TIMEOUT_MS;
  private static final float EARLY_BACKOFF_MULT = ((Float)DfeApiConfig.earlyUpdateBackoffMultiplier.get()).floatValue();
  private static final int EARLY_MAX_RETRIES;
  private static final int EARLY_TIMEOUT_MS;
  private static final int PING_REFERRER_TIMEOUT_MS;
  private static final int PURCHASE_TIMEOUT_MS;
  private static final float REPLICATE_LIBRARY_BACKOFF_MULT;
  private static final int REPLICATE_LIBRARY_MAX_RETRIES;
  private static final int REPLICATE_LIBRARY_TIMEOUT_MS;
  private static final boolean SEND_AD_ID_IN_REQUESTS_FOR_RADS;
  private static final boolean SEND_PUBLIC_ANDROID_ID_IN_REQUESTS_FOR_RADS = ((Boolean)DfeApiConfig.sendPublicAndroidIdInRequestsForRads.get()).booleanValue();
  private static final int VERIFY_ASSOCIATION_MAX_RETRIES;
  private static final int VERIFY_ASSOCIATION_TIMEOUT_MS;
  final DfeApiContext mApiContext;
  private final RequestQueue mQueue;
  
  static
  {
    SEND_AD_ID_IN_REQUESTS_FOR_RADS = ((Boolean)DfeApiConfig.sendAdIdInRequestsForRads.get()).booleanValue();
    PURCHASE_TIMEOUT_MS = ((Integer)DfeApiConfig.purchaseStatusTimeoutMs.get()).intValue();
    AGE_VERIFICATION_TIMEOUT_MS = ((Integer)DfeApiConfig.ageVerificationTimeoutMs.get()).intValue();
    BACKUP_DEVICES_TIMEOUT_MS = ((Integer)DfeApiConfig.backupDevicesTimeoutMs.get()).intValue();
    BACKUP_DEVICES_MAX_RETRIES = ((Integer)DfeApiConfig.backupDevicesMaxRetries.get()).intValue();
    BACKUP_DEVICES_BACKOFF_MULT = ((Float)DfeApiConfig.backupDevicesBackoffMultiplier.get()).floatValue();
    PING_REFERRER_TIMEOUT_MS = ((Integer)DfeApiConfig.adClickTimeoutMs.get()).intValue();
    BULK_DETAILS_TIMEOUT_MS = ((Integer)DfeApiConfig.bulkDetailsTimeoutMs.get()).intValue();
    BULK_DETAILS_MAX_RETRIES = ((Integer)DfeApiConfig.bulkDetailsMaxRetries.get()).intValue();
    BULK_DETAILS_BACKOFF_MULT = ((Float)DfeApiConfig.bulkDetailsBackoffMultiplier.get()).floatValue();
    VERIFY_ASSOCIATION_TIMEOUT_MS = ((Integer)DfeApiConfig.verifyAssociationTimeoutMs.get()).intValue();
    VERIFY_ASSOCIATION_MAX_RETRIES = ((Integer)DfeApiConfig.verifyAssociationMaxRetries.get()).intValue();
    REPLICATE_LIBRARY_TIMEOUT_MS = ((Integer)DfeApiConfig.replicateLibraryTimeoutMs.get()).intValue();
    REPLICATE_LIBRARY_MAX_RETRIES = ((Integer)DfeApiConfig.replicateLibraryMaxRetries.get()).intValue();
    REPLICATE_LIBRARY_BACKOFF_MULT = ((Float)DfeApiConfig.replicateLibraryBackoffMultiplier.get()).floatValue();
    EARLY_TIMEOUT_MS = ((Integer)DfeApiConfig.earlyUpdateTimeoutMs.get()).intValue();
    EARLY_MAX_RETRIES = ((Integer)DfeApiConfig.earlyUpdateMaxRetries.get()).intValue();
  }
  
  public DfeApiImpl(RequestQueue paramRequestQueue, DfeApiContext paramDfeApiContext)
  {
    this.mQueue = paramRequestQueue;
    this.mApiContext = paramDfeApiContext;
  }
  
  private static void addAuthInfoToPurchaseRequest(DfeApi.DfePostRequest paramDfePostRequest, Purchase.AuthenticationInfo paramAuthenticationInfo)
  {
    paramDfePostRequest.addPostParam("pcap", String.valueOf(paramAuthenticationInfo.authenticationPreference));
    if (paramAuthenticationInfo.hasLastAuthTimestampMillis) {
      paramDfePostRequest.addPostParam("pclats", String.valueOf(paramAuthenticationInfo.lastAuthTimestampMillis));
    }
  }
  
  private void addNetworkTypeToRequest(DfeRequest<?> paramDfeRequest)
  {
    paramDfeRequest.addExtraHeader("X-DFE-Network-Type", Integer.toString(NetworkInfoUtil.getNetworkType(this.mApiContext.mContext)), false);
  }
  
  private void addPublicAndroidIdHeaderForRads(DfeRequest<?> paramDfeRequest)
  {
    DfeApiContext localDfeApiContext;
    if (SEND_PUBLIC_ANDROID_ID_IN_REQUESTS_FOR_RADS)
    {
      localDfeApiContext = this.mApiContext;
      if (localDfeApiContext.mAdIdProvider != null) {
        break label47;
      }
    }
    label47:
    for (Object localObject = null;; localObject = localDfeApiContext.mAdIdProvider.getPublicAndroidId())
    {
      if (!TextUtils.isEmpty((CharSequence)localObject)) {
        paramDfeRequest.addExtraHeader("X-Public-Android-Id", (String)localObject, false);
      }
      if (SEND_AD_ID_IN_REQUESTS_FOR_RADS) {
        paramDfeRequest.mIncludeAdIdAsHeader = true;
      }
      return;
    }
  }
  
  private static void addUserContextToRequest(DfeRequest<?> paramDfeRequest, UserContext paramUserContext)
  {
    if (paramUserContext != null) {
      paramDfeRequest.addExtraHeader("X-DFE-User-Context", DfeUtils.base64Encode(MessageNano.toByteArray(paramUserContext)), true);
    }
  }
  
  private static String getReviewsUrl(String paramString, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    Uri.Builder localBuilder = Uri.parse(paramString).buildUpon();
    if (paramBoolean) {
      localBuilder.appendQueryParameter("dfil", "1");
    }
    if (paramInt1 > 0) {
      localBuilder.appendQueryParameter("vc", Integer.toString(paramInt1));
    }
    if (paramInt2 > 0) {
      localBuilder.appendQueryParameter("rating", Integer.toString(paramInt2));
    }
    if (paramInt3 >= 0) {
      localBuilder.appendQueryParameter("sort", Integer.toString(paramInt3));
    }
    return localBuilder.toString();
  }
  
  private DfeRetryPolicy makeAgeVerificationRetryPolicy()
  {
    return new DfeRetryPolicy(AGE_VERIFICATION_TIMEOUT_MS, 0, 0.0F, this.mApiContext);
  }
  
  private DfeRetryPolicy makeEarlyUpdateRetryPolicy()
  {
    return new DfeRetryPolicy(EARLY_TIMEOUT_MS, EARLY_MAX_RETRIES, EARLY_BACKOFF_MULT, this.mApiContext);
  }
  
  private DfeRetryPolicy makePurchaseRetryPolicy()
  {
    return new DfeRetryPolicy(PURCHASE_TIMEOUT_MS, 0, 0.0F, this.mApiContext);
  }
  
  public final Request<?> acceptTos(String paramString, Boolean paramBoolean, Response.Listener<AcceptTosResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(ACCEPT_TOS_URI.toString(), this.mApiContext, AcceptTosResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.addPostParam("tost", paramString);
    if (paramBoolean != null) {
      localDfePostRequest.addPostParam("toscme", paramBoolean.toString());
    }
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> ackNotification(String paramString, Response.Listener<AckNotificationResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(ACK_NOTIFICATION_URI.toString() + "?nid=" + paramString, this.mApiContext, AckNotificationResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> addReview(String paramString1, String paramString2, String paramString3, int paramInt, boolean paramBoolean, Response.Listener<ReviewResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(ADDREVIEW_URI.toString(), this.mApiContext, ReviewResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.addPostParam("doc", paramString1);
    localDfePostRequest.addPostParam("title", paramString2);
    localDfePostRequest.addPostParam("content", paramString3);
    localDfePostRequest.addPostParam("rating", Integer.toString(paramInt));
    localDfePostRequest.addPostParam("ipr", Boolean.toString(paramBoolean));
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> addToLibrary(Collection<String> paramCollection, String paramString, Response.Listener<ModifyLibrary.ModifyLibraryResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    ModifyLibrary.ModifyLibraryRequest localModifyLibraryRequest = new ModifyLibrary.ModifyLibraryRequest();
    localModifyLibraryRequest.libraryId = paramString;
    localModifyLibraryRequest.hasLibraryId = true;
    localModifyLibraryRequest.forAddDocid = ((String[])paramCollection.toArray(new String[paramCollection.size()]));
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(MODIFY_LIBRARY_URI.toString(), localModifyLibraryRequest, this.mApiContext, ModifyLibrary.ModifyLibraryResponse.class, paramListener, paramErrorListener);
    localProtoDfeRequest.mResponseVerifier = new DfeResponseVerifierImpl(this.mApiContext.mContext);
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  public final Request<?> archiveFromLibrary(Collection<String> paramCollection, String paramString, Response.Listener<ModifyLibrary.ModifyLibraryResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    ModifyLibrary.ModifyLibraryRequest localModifyLibraryRequest = new ModifyLibrary.ModifyLibraryRequest();
    localModifyLibraryRequest.libraryId = paramString;
    localModifyLibraryRequest.hasLibraryId = true;
    localModifyLibraryRequest.forArchiveDocid = ((String[])paramCollection.toArray(new String[paramCollection.size()]));
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(MODIFY_LIBRARY_URI.toString(), localModifyLibraryRequest, this.mApiContext, ModifyLibrary.ModifyLibraryResponse.class, paramListener, paramErrorListener);
    localProtoDfeRequest.mResponseVerifier = new DfeResponseVerifierImpl(this.mApiContext.mContext);
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  public final Request<?> billingProfile(String paramString, Map<String, String> paramMap, Response.Listener<BuyInstruments.BillingProfileResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(BILLING_PROFILE_URI.toString(), this.mApiContext, BuyInstruments.BillingProfileResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.mRetryPolicy = makePurchaseRetryPolicy();
    if (paramString != null) {
      localDfePostRequest.addPostParam("pct", paramString);
    }
    if (paramMap != null)
    {
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localDfePostRequest.addPostParam((String)localEntry.getKey(), (String)localEntry.getValue());
      }
    }
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> changeFamilyInstrument(FamilyFopRequest paramFamilyFopRequest, Response.Listener<FamilyFopResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(CHANGE_FAMILY_INSTRUMENT_URI.toString(), paramFamilyFopRequest, this.mApiContext, FamilyFopResponse.class, paramListener, paramErrorListener);
    localProtoDfeRequest.mRetryPolicy = makePurchaseRetryPolicy();
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  public final Request<?> checkIabPromo(int paramInt, String paramString1, String paramString2, Response.Listener<BuyInstruments.CheckIabPromoResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(CHECK_IAB_PROMO_URI.toString(), this.mApiContext, BuyInstruments.CheckIabPromoResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.addPostParam("dt", Integer.toString(paramInt));
    localDfePostRequest.addPostParam("shpn", paramString1);
    if (!TextUtils.isEmpty(paramString2)) {
      localDfePostRequest.addPostParam("dcbch", paramString2);
    }
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> checkInstrument(Response.Listener<BuyInstruments.CheckInstrumentResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(CHECK_INSTRUMENT_URI.toString(), this.mApiContext, BuyInstruments.CheckInstrumentResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfePostRequest);
  }
  
  @SuppressLint({"NewApi"})
  public final Request<?> checkPromoOffers(Response.Listener<CheckPromoOfferResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(CHECK_PROMO_OFFER_URI.toString(), this.mApiContext, CheckPromoOfferResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.mRetryPolicy = makePurchaseRetryPolicy();
    if (Build.VERSION.SDK_INT >= 9) {
      localDfePostRequest.addExtraHeader("X-DFE-Hardware-Id", DfeApiContext.sanitizeHeaderValue(Build.SERIAL), false);
    }
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final DfeRequest<?> commitPurchase(String paramString1, Map<String, String> paramMap, Purchase.AuthenticationInfo paramAuthenticationInfo, Purchase.DcbPurchaseInfo paramDcbPurchaseInfo, String paramString2, boolean paramBoolean, UserSettingsConsistencyTokens paramUserSettingsConsistencyTokens, Response.Listener<Purchase.CommitPurchaseResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    Uri localUri;
    if (FinskyApp.get().getExperiments().isEnabled(12603788L)) {
      localUri = EES_COMMIT_PURCHASE_URI;
    }
    while (paramBoolean)
    {
      Uri.Builder localBuilder = localUri.buildUpon();
      localBuilder.appendQueryParameter("s7e_suffix", (String)DfeApiConfig.purchaseManagerEesSuffix.get());
      Set localSet = localUri.getQueryParameterNames();
      if (localSet.size() > 0)
      {
        Iterator localIterator2 = localSet.iterator();
        for (;;)
        {
          if (localIterator2.hasNext())
          {
            String str = (String)localIterator2.next();
            localBuilder.appendQueryParameter(str, localUri.getQueryParameter(str));
            continue;
            localUri = COMMIT_PURCHASE_URI;
            break;
          }
        }
      }
      localUri = localBuilder.build();
    }
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(localUri.toString(), this.mApiContext, Purchase.CommitPurchaseResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.addPostParam("pct", paramString1);
    localDfePostRequest.mRetryPolicy = makePurchaseRetryPolicy();
    if (paramMap != null)
    {
      Iterator localIterator1 = paramMap.entrySet().iterator();
      while (localIterator1.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator1.next();
        localDfePostRequest.addPostParam((String)localEntry.getKey(), (String)localEntry.getValue());
      }
    }
    if (paramDcbPurchaseInfo != null)
    {
      if (paramDcbPurchaseInfo.hasSimIdentifierHash) {
        localDfePostRequest.addPostParam("dcbch", paramDcbPurchaseInfo.simIdentifierHash);
      }
      if (paramDcbPurchaseInfo.hasPassphrase) {
        localDfePostRequest.addPostParam("dcbpassphrase", paramDcbPurchaseInfo.passphrase);
      }
    }
    addAuthInfoToPurchaseRequest(localDfePostRequest, paramAuthenticationInfo);
    if (paramString2 != null) {
      localDfePostRequest.addPostParam("chdi", paramString2);
    }
    localDfePostRequest.mUserSettingsConsistencyTokens = paramUserSettingsConsistencyTokens;
    addPublicAndroidIdHeaderForRads(localDfePostRequest);
    localDfePostRequest.mResponseVerifier = new DfeResponseVerifierImpl(this.mApiContext.mContext);
    return (DfeRequest)this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> consumePurchase$3e88e590(String paramString1, String paramString2, Response.Listener<ConsumePurchaseResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(CONSUME_PURCHASE_URI.toString(), this.mApiContext, ConsumePurchaseResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.mRetryPolicy = makePurchaseRetryPolicy();
    localDfePostRequest.addPostParam("pt", paramString1);
    localDfePostRequest.addPostParam("ot", Integer.toString(1));
    localDfePostRequest.addPostParam("shpn", paramString2);
    localDfePostRequest.mResponseVerifier = new DfeResponseVerifierImpl(this.mApiContext.mContext);
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> contentFilterSettings(Response.Listener<ContentFilters.ContentFilterSettingsResponse> paramListener, Response.ErrorListener paramErrorListener, boolean paramBoolean)
  {
    Uri.Builder localBuilder = CONTENT_FILTER_SETTINGS_URI.buildUpon();
    if (paramBoolean) {
      localBuilder.appendQueryParameter("at", Boolean.TRUE.toString());
    }
    DfeRequest localDfeRequest = new DfeRequest(localBuilder.build().toString(), this.mApiContext, ContentFilters.ContentFilterSettingsResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> debugSettings(String paramString, Response.Listener<DebugSettingsResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    Uri.Builder localBuilder = DEBUG_SETTINGS_URI.buildUpon();
    if (paramString != null) {
      localBuilder.appendQueryParameter("playCountryOverride", paramString);
    }
    DfeRequest localDfeRequest = new DfeRequest(localBuilder.toString(), this.mApiContext, DebugSettingsResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> deleteReview(String paramString, Response.Listener<ReviewResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(DELETEREVIEW_URI.toString(), this.mApiContext, ReviewResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.addPostParam("doc", paramString);
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> delivery$5df18dd4(String paramString1, byte[] paramArrayOfByte, Integer paramInteger1, Integer paramInteger2, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, Response.Listener<DeliveryResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    Uri.Builder localBuilder = DELIVERY_URI.buildUpon().appendQueryParameter("doc", paramString1).appendQueryParameter("ot", Integer.toString(1));
    if (paramArrayOfByte != null) {
      localBuilder.appendQueryParameter("st", DfeUtils.base64Encode(paramArrayOfByte));
    }
    if (paramInteger1 != null) {
      localBuilder.appendQueryParameter("vc", paramInteger1.toString());
    }
    if (paramInteger2 != null)
    {
      localBuilder.appendQueryParameter("bvc", paramInteger2.toString());
      if (paramArrayOfString != null)
      {
        int i = paramArrayOfString.length;
        for (int j = 0; j < i; j++) {
          localBuilder.appendQueryParameter("pf", paramArrayOfString[j]);
        }
      }
    }
    if (!TextUtils.isEmpty(paramString2)) {
      localBuilder.appendQueryParameter("shh", paramString2);
    }
    if (!TextUtils.isEmpty(paramString3)) {
      localBuilder.appendQueryParameter("ch", paramString3);
    }
    if (!TextUtils.isEmpty(paramString4)) {
      localBuilder.appendQueryParameter("dtok", paramString4);
    }
    DfeRequest localDfeRequest = new DfeRequest(localBuilder.build().toString(), this.mApiContext, DeliveryResponse.class, paramListener, paramErrorListener);
    localDfeRequest.mShouldCache = false;
    localDfeRequest.mIncludeCheckinConsistencyToken = true;
    localDfeRequest.mAvoidBulkCancel = true;
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> dismissSurvey(long paramLong, int paramInt, Response.Listener<DismissSurveyResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(DISMISS_SURVEY_REQUEST_URI.buildUpon().appendQueryParameter("sid", Long.toString(paramLong)).appendQueryParameter("sdrn", Integer.toString(paramInt)).toString(), this.mApiContext, DismissSurveyResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> earlyDelivery$5b28aa05(String paramString1, Integer paramInteger1, Integer paramInteger2, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, Response.Listener<DeliveryResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    Uri.Builder localBuilder = EARLY_DELIVERY_URI.buildUpon().appendQueryParameter("doc", paramString1).appendQueryParameter("ot", Integer.toString(1));
    if (paramInteger1 != null) {
      localBuilder.appendQueryParameter("vc", paramInteger1.toString());
    }
    if (paramInteger2 != null)
    {
      localBuilder.appendQueryParameter("bvc", paramInteger2.toString());
      if (paramArrayOfString != null)
      {
        int i = paramArrayOfString.length;
        for (int j = 0; j < i; j++) {
          localBuilder.appendQueryParameter("pf", paramArrayOfString[j]);
        }
      }
    }
    if (!TextUtils.isEmpty(paramString2)) {
      localBuilder.appendQueryParameter("shh", paramString2);
    }
    if (!TextUtils.isEmpty(paramString3)) {
      localBuilder.appendQueryParameter("ch", paramString3);
    }
    if (!TextUtils.isEmpty(paramString4)) {
      localBuilder.appendQueryParameter("dtok", paramString4);
    }
    DfeRequest localDfeRequest = new DfeRequest(localBuilder.build().toString(), this.mApiContext, DeliveryResponse.class, paramListener, paramErrorListener);
    localDfeRequest.mRetryPolicy = makeEarlyUpdateRetryPolicy();
    localDfeRequest.mShouldCache = false;
    localDfeRequest.mIncludeCheckinConsistencyToken = true;
    localDfeRequest.mAvoidBulkCancel = true;
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> earlyUpdate(DeviceConfiguration.DeviceConfigurationProto paramDeviceConfigurationProto, Response.Listener<EarlyUpdateResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    EarlyUpdateRequest localEarlyUpdateRequest = new EarlyUpdateRequest();
    if (paramDeviceConfigurationProto != null) {
      localEarlyUpdateRequest.deviceConfiguration = paramDeviceConfigurationProto;
    }
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(EARLY_UPDATE_URI.toString(), localEarlyUpdateRequest, this.mApiContext, EarlyUpdateResponse.class, paramListener, paramErrorListener);
    localProtoDfeRequest.mRetryPolicy = makeEarlyUpdateRetryPolicy();
    localProtoDfeRequest.mAvoidBulkCancel = false;
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  public final Request<?> flagContent(String paramString1, int paramInt, String paramString2, Response.Listener<ContentFlagging.FlagContentResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(FLAG_CONTENT_URI.toString(), this.mApiContext, ContentFlagging.FlagContentResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.addPostParam("doc", paramString1);
    localDfePostRequest.addPostParam("content", paramString2);
    localDfePostRequest.addPostParam("cft", Integer.toString(paramInt));
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Account getAccount()
  {
    return this.mApiContext.getAccount();
  }
  
  public final String getAccountName()
  {
    return this.mApiContext.getAccountName();
  }
  
  public final DfeApiContext getApiContext()
  {
    return this.mApiContext;
  }
  
  public final Request<?> getBackupDeviceChoices(Response.Listener<Restore.GetBackupDeviceChoicesResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(GET_BACKUP_DEVICE_CHOICES_URI.toString(), this.mApiContext, Restore.GetBackupDeviceChoicesResponse.class, paramListener, paramErrorListener);
    localDfeRequest.mIncludeCheckinConsistencyToken = true;
    localDfeRequest.mRetryPolicy = new DfeRetryPolicy(BACKUP_DEVICES_TIMEOUT_MS, BACKUP_DEVICES_MAX_RETRIES, BACKUP_DEVICES_BACKOFF_MULT, this.mApiContext);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> getBackupDocumentChoices(long paramLong, Response.Listener<Restore.GetBackupDocumentChoicesResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    Uri.Builder localBuilder = GET_BACKUP_DOCUMENT_CHOICES_URI.buildUpon();
    localBuilder.appendQueryParameter("raid", Long.toString(paramLong));
    DfeRequest localDfeRequest = new DfeRequest(localBuilder.toString(), this.mApiContext, Restore.GetBackupDocumentChoicesResponse.class, paramListener, paramErrorListener);
    localDfeRequest.mIncludeCheckinConsistencyToken = true;
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> getBrowseLayout(String paramString, UserContext paramUserContext, Response.Listener<Browse.BrowseResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(paramString, this.mApiContext, Browse.BrowseResponse.class, paramListener, paramErrorListener);
    addUserContextToRequest(localDfeRequest, paramUserContext);
    addNetworkTypeToRequest(localDfeRequest);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> getBulkDetails(Collection<String> paramCollection, boolean paramBoolean, Response.Listener<Details.BulkDetailsResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    return getBulkDetails(paramCollection, paramBoolean, null, false, paramListener, paramErrorListener);
  }
  
  public final Request<?> getBulkDetails(Collection<String> paramCollection, boolean paramBoolean1, String paramString, boolean paramBoolean2, Response.Listener<Details.BulkDetailsResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    Details.BulkDetailsRequest localBulkDetailsRequest = new Details.BulkDetailsRequest();
    final ArrayList localArrayList = new ArrayList(paramCollection);
    Collections.sort(localArrayList);
    localBulkDetailsRequest.docid = ((String[])localArrayList.toArray(new String[localArrayList.size()]));
    localBulkDetailsRequest.includeDetails = paramBoolean2;
    localBulkDetailsRequest.hasIncludeDetails = true;
    if (!TextUtils.isEmpty(paramString))
    {
      localBulkDetailsRequest.sourcePackageName = paramString;
      localBulkDetailsRequest.hasSourcePackageName = true;
    }
    Uri.Builder localBuilder = BULK_DETAILS_URI.buildUpon();
    if (paramBoolean1) {
      localBuilder.appendQueryParameter("au", "1");
    }
    ProtoDfeRequest local1 = new ProtoDfeRequest(localBuilder.build().toString(), localBulkDetailsRequest, this.mApiContext, Details.BulkDetailsResponse.class, paramListener, paramErrorListener)
    {
      String mCacheKey;
      
      private String computeDocumentIdHash()
      {
        long l = 0L;
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          l = 31L * l + str.hashCode();
        }
        return Long.toString(l);
      }
      
      public final String getCacheKey()
      {
        if (this.mCacheKey == null)
        {
          StringBuilder localStringBuilder = new StringBuilder(super.getCacheKey()).append("/docidhash=").append(computeDocumentIdHash());
          if (!TextUtils.isEmpty(DfeApiImpl.this.mApiContext.mNodeId)) {
            localStringBuilder.append("/nodeId=").append(DfeApiImpl.this.mApiContext.mNodeId);
          }
          this.mCacheKey = localStringBuilder.toString();
        }
        return this.mCacheKey;
      }
    };
    local1.mShouldCache = true;
    local1.mRetryPolicy = new DfeRetryPolicy(BULK_DETAILS_TIMEOUT_MS, BULK_DETAILS_MAX_RETRIES, BULK_DETAILS_BACKOFF_MULT, this.mApiContext);
    local1.mAvoidBulkCancel = false;
    return this.mQueue.add(local1);
  }
  
  public final Request<?> getDetails(String paramString, boolean paramBoolean1, boolean paramBoolean2, Collection<String> paramCollection, Response.Listener<Details.DetailsResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(paramString, this.mApiContext, Details.DetailsResponse.class, paramListener, paramErrorListener);
    if (paramBoolean1) {
      localDfeRequest.addExtraHeader("X-DFE-No-Prefetch", "true", false);
    }
    addNetworkTypeToRequest(localDfeRequest);
    localDfeRequest.mAvoidBulkCancel = paramBoolean2;
    if ((paramCollection != null) && (!paramCollection.isEmpty()) && (((Boolean)DfeApiConfig.vouchersInDetailsRequestsEnabled.get()).booleanValue()))
    {
      localDfeRequest.addExtraHeader("X-DFE-Client-Has-Vouchers", "true", false);
      if (paramCollection.size() <= ((Integer)DfeApiConfig.maxVouchersInDetailsRequest.get()).intValue())
      {
        StringBuilder localStringBuilder = new StringBuilder();
        Iterator localIterator = paramCollection.iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          if (localStringBuilder.length() > 0) {
            localStringBuilder.append(',');
          }
          localStringBuilder.append(Uri.encode(str));
        }
        localDfeRequest.addExtraHeader("X-DFE-Vouchers-Backend-Docids-CSV", localStringBuilder.toString(), false);
      }
    }
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> getFamilyPurchaseSetting(String paramString, Response.Listener<GetFamilyPurchaseSettingResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    Uri.Builder localBuilder = GET_FAMILY_PURCHASE_SETTING_URI.buildUpon();
    localBuilder.appendQueryParameter("ogi", paramString);
    DfeRequest localDfeRequest = new DfeRequest(localBuilder.build().toString(), this.mApiContext, GetFamilyPurchaseSettingResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> getFamilySharingSettings(Response.Listener<SharingSetting.GetFamilySharingSettingsResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(GET_FAMILY_SHARE_SETTINGS_URI.toString(), this.mApiContext, SharingSetting.GetFamilySharingSettingsResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final String getLibraryUrl(int paramInt1, String paramString, int paramInt2, byte[] paramArrayOfByte)
  {
    Uri.Builder localBuilder = LIBRARY_URI.buildUpon().appendQueryParameter("c", Integer.toString(paramInt1)).appendQueryParameter("dt", Integer.toString(paramInt2)).appendQueryParameter("libid", paramString);
    if (paramArrayOfByte != null) {
      localBuilder.appendQueryParameter("st", DfeUtils.base64Encode(paramArrayOfByte));
    }
    return localBuilder.toString();
  }
  
  public final Request<?> getList(String paramString, UserContext paramUserContext, Response.Listener<ListResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(paramString, this.mApiContext, ListResponse.class, paramListener, paramErrorListener);
    addUserContextToRequest(localDfeRequest, paramUserContext);
    addNetworkTypeToRequest(localDfeRequest);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> getMyAccount(Response.Listener<MyAccountResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(MY_ACCOUNT_URI.toString(), this.mApiContext, MyAccountResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> getReviews(String paramString, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, Response.Listener<ReviewResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(getReviewsUrl(paramString, paramBoolean, paramInt1, paramInt2, paramInt3), this.mApiContext, ReviewResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> getSelfUpdate(String paramString, Response.Listener<SelfUpdateResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(SELFUPDATE_URI.toString(), this.mApiContext, SelfUpdateResponse.class, paramListener, paramErrorListener);
    if (!TextUtils.isEmpty(paramString)) {
      localDfeRequest.addExtraHeader("X-DFE-Device-Config-Token", Uri.encode(paramString), false);
    }
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> getSnippets(String paramString, Response.Listener<ReviewSnippetsResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(paramString, this.mApiContext, ReviewSnippetsResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> getSurveys(Response.Listener<SurveyResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    Uri.Builder localBuilder = SURVEY_REQUEST_URI.buildUpon();
    if (((Boolean)FinskyPreferences.internalShowAllSurveys.get()).booleanValue()) {
      localBuilder.appendQueryParameter("getAll", Boolean.toString(true));
    }
    DfeRequest localDfeRequest = new DfeRequest(localBuilder.toString(), this.mApiContext, SurveyResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> getToc(boolean paramBoolean, String paramString, Response.Listener<Toc.TocResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(CHANNELS_URI.toString(), this.mApiContext, Toc.TocResponse.class, paramListener, paramErrorListener);
    localDfeRequest.mAllowMultipleResponses = paramBoolean;
    if (!TextUtils.isEmpty(paramString)) {
      localDfeRequest.addExtraHeader("X-DFE-Device-Config-Token", Uri.encode(paramString), false);
    }
    FinskyExperiments localFinskyExperiments = this.mApiContext.mExperiments;
    if ((localFinskyExperiments == null) || (!localFinskyExperiments.isEnabled(12603108L))) {
      localDfeRequest.mIncludeCheckinConsistencyToken = true;
    }
    localDfeRequest.mIncludeManagedContextFlag = true;
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> getUserSettings(UserSettingsConsistencyTokens paramUserSettingsConsistencyTokens, Response.Listener<GetUserSettingsResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(GET_USER_SETTINGS_URI.toString(), this.mApiContext, GetUserSettingsResponse.class, paramListener, paramErrorListener);
    localDfeRequest.mUserSettingsConsistencyTokens = paramUserSettingsConsistencyTokens;
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> initiateAssociation(String paramString, Response.Listener<CarrierBilling.InitiateAssociationResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(DCB_INITIATE_ASSOCIATION_URI.toString(), this.mApiContext, CarrierBilling.InitiateAssociationResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.addPostParam("dcbch", paramString);
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final DfeRequest<?> instantPurchase(Purchase.InstantPurchaseRequest paramInstantPurchaseRequest, Response.Listener<Purchase.InstantPurchaseResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(INSTANT_PURCHASE_URI.toString(), paramInstantPurchaseRequest, this.mApiContext, Purchase.InstantPurchaseResponse.class, paramListener, paramErrorListener);
    addPublicAndroidIdHeaderForRads(localProtoDfeRequest);
    localProtoDfeRequest.mRetryPolicy = makePurchaseRetryPolicy();
    localProtoDfeRequest.mResponseVerifier = new DfeResponseVerifierImpl(this.mApiContext.mContext);
    return (DfeRequest)this.mQueue.add(localProtoDfeRequest);
  }
  
  public final void invalidateDetailsCache$505cbf4b(String paramString)
  {
    DfeRequest localDfeRequest = new DfeRequest(paramString, this.mApiContext, Details.DetailsResponse.class, null, null);
    this.mQueue.add(new DfeClearCacheRequest(this.mApiContext.mCache, localDfeRequest.getCacheKey(), true));
  }
  
  public final void invalidateListCache$505cbf4b(String paramString)
  {
    DfeRequest localDfeRequest = new DfeRequest(paramString, this.mApiContext, ListResponse.class, null, null);
    this.mQueue.add(new DfeClearCacheRequest(this.mApiContext.mCache, localDfeRequest.getCacheKey(), true));
  }
  
  public final void invalidateReviewsCache$13ffb93a(String paramString, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    DfeRequest localDfeRequest = new DfeRequest(getReviewsUrl(paramString, paramBoolean, paramInt1, paramInt2, paramInt3), this.mApiContext, ReviewResponse.class, null, null);
    this.mQueue.add(new DfeClearCacheRequest(this.mApiContext.mCache, localDfeRequest.getCacheKey(), true));
  }
  
  public final void invalidateSelfUpdateCache()
  {
    DfeRequest localDfeRequest = new DfeRequest(SELFUPDATE_URI.toString(), this.mApiContext, SelfUpdateResponse.class, null, null);
    this.mQueue.add(new DfeClearCacheRequest(this.mApiContext.mCache, localDfeRequest.getCacheKey(), true));
  }
  
  public final void invalidateSurveysCache$1385ff()
  {
    Uri.Builder localBuilder = SURVEY_REQUEST_URI.buildUpon();
    if (((Boolean)FinskyPreferences.internalShowAllSurveys.get()).booleanValue()) {
      localBuilder.appendQueryParameter("getAll", Boolean.toString(true));
    }
    DfeRequest localDfeRequest = new DfeRequest(localBuilder.toString(), this.mApiContext, SurveyResponse.class, null, null);
    this.mQueue.add(new DfeClearCacheRequest(this.mApiContext.mCache, localDfeRequest.getCacheKey(), true));
  }
  
  public final void invalidateTocCache()
  {
    DfeRequest localDfeRequest = new DfeRequest(CHANNELS_URI.toString(), this.mApiContext, Toc.TocResponse.class, null, null);
    this.mQueue.add(new DfeClearCacheRequest(this.mApiContext.mCache, localDfeRequest.getCacheKey(), true));
  }
  
  public final Request<?> log(Log.LogRequest paramLogRequest, Response.Listener<Log.LogResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(LOG_URI.toString(), paramLogRequest, this.mApiContext, Log.LogResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  public final Request<?> makePurchase$668028e1(Document paramDocument, int paramInt, Response.Listener<Buy.BuyResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(PURCHASE_URI.toString(), this.mApiContext, Buy.BuyResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.mRetryPolicy = makePurchaseRetryPolicy();
    localDfePostRequest.addPostParam("doc", paramDocument.mDocument.docid);
    localDfePostRequest.addPostParam("ot", Integer.toString(paramInt));
    if (paramDocument.mDocument.docType == 1) {
      localDfePostRequest.addPostParam("vc", String.valueOf(paramDocument.getAppDetails().versionCode));
    }
    addPublicAndroidIdHeaderForRads(localDfePostRequest);
    localDfePostRequest.mResponseVerifier = new DfeResponseVerifierImpl(this.mApiContext.mContext);
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> pingReferrer(String paramString, Response.Listener<PingResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(PING_URI.toString(), this.mApiContext, PingResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.addPostParam("url", paramString);
    localDfePostRequest.mRetryPolicy = new DfeRetryPolicy(PING_REFERRER_TIMEOUT_MS, 0, 0.0F, this.mApiContext);
    localDfePostRequest.mIncludeAdIdAsHeader = true;
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> pingReferrer(String paramString1, String paramString2, Response.Listener<PingResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(PING_URI.toString(), this.mApiContext, PingResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.addPostParam("doc", paramString1);
    localDfePostRequest.addPostParam("referrer", paramString2);
    localDfePostRequest.mRetryPolicy = new DfeRetryPolicy(PING_REFERRER_TIMEOUT_MS, 0, 0.0F, this.mApiContext);
    localDfePostRequest.mIncludeAdIdAsHeader = true;
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> preloads(String paramString1, String paramString2, Response.Listener<Preloads.PreloadsResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    Uri.Builder localBuilder = PRELOADS_URI.buildUpon();
    localBuilder.appendQueryParameter("doc", paramString2);
    DfeRequest localDfeRequest = new DfeRequest(localBuilder.build().toString(), this.mApiContext, Preloads.PreloadsResponse.class, paramListener, paramErrorListener);
    if (!TextUtils.isEmpty(paramString1)) {
      localDfeRequest.addExtraHeader("X-DFE-Device-Config-Token", Uri.encode(paramString1), false);
    }
    localDfeRequest.mIncludeCheckinConsistencyToken = true;
    localDfeRequest.mIncludeManagedContextFlag = true;
    return this.mQueue.add(localDfeRequest);
  }
  
  public final DfeRequest<?> preparePurchase(String paramString1, int paramInt1, String paramString2, Purchase.InAppPurchaseInfo paramInAppPurchaseInfo, Purchase.AuthenticationInfo paramAuthenticationInfo, String paramString3, int paramInt2, VoucherParams paramVoucherParams, int paramInt3, String paramString4, Map<String, String> paramMap, Response.Listener<Purchase.PreparePurchaseResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    Uri localUri;
    DfeApi.DfePostRequest localDfePostRequest;
    FinskyExperiments localFinskyExperiments;
    Object localObject;
    if (FinskyApp.get().getExperiments().isEnabled(12603788L))
    {
      localUri = EES_PREPARE_PURCHASE_URI;
      localDfePostRequest = new DfeApi.DfePostRequest(localUri.toString(), this.mApiContext, Purchase.PreparePurchaseResponse.class, paramListener, paramErrorListener);
      localFinskyExperiments = FinskyApp.get().getExperiments();
      if (!localFinskyExperiments.isEnabled(12602810L)) {
        break label381;
      }
      localObject = new PreparePurchaseRetryPolicy(2500, this.mApiContext);
      label80:
      localDfePostRequest.mRetryPolicy = ((RetryPolicy)localObject);
      localDfePostRequest.addPostParam("doc", paramString1);
      if (!TextUtils.isEmpty(paramString2)) {
        break label480;
      }
      localDfePostRequest.addPostParam("ot", Integer.toString(paramInt1));
    }
    for (;;)
    {
      if (paramString3 != null) {
        localDfePostRequest.addPostParam("ii", paramString3);
      }
      localDfePostRequest.addPostParam("chv", String.valueOf(paramVoucherParams.hasVouchers));
      localDfePostRequest.addPostParam("aav", String.valueOf(paramVoucherParams.autoApply));
      if (!TextUtils.isEmpty(paramVoucherParams.selectedVoucherId)) {
        localDfePostRequest.addPostParam("usvid", paramVoucherParams.selectedVoucherId);
      }
      if (paramInt3 > 0) {
        localDfePostRequest.addPostParam("vc", String.valueOf(paramInt3));
      }
      if (paramInt2 != 0) {
        localDfePostRequest.addPostParam("ipt", String.valueOf(paramInt2));
      }
      if (!TextUtils.isEmpty(paramString4)) {
        localDfePostRequest.addPostParam("ipcdr", paramString4);
      }
      if (paramInAppPurchaseInfo == null) {
        break label492;
      }
      localDfePostRequest.addPostParam("bav", Integer.toString(paramInAppPurchaseInfo.billingApiVersion));
      localDfePostRequest.addPostParam("shpn", paramInAppPurchaseInfo.appPackageName);
      localDfePostRequest.addPostParam("shh", paramInAppPurchaseInfo.appSignatureHash);
      localDfePostRequest.addPostParam("shvc", Integer.toString(paramInAppPurchaseInfo.appVersionCode));
      if (paramInAppPurchaseInfo.developerPayload != null) {
        localDfePostRequest.addPostParam("payload", paramInAppPurchaseInfo.developerPayload);
      }
      if (paramInAppPurchaseInfo.oldDocid == null) {
        break label492;
      }
      String[] arrayOfString = paramInAppPurchaseInfo.oldDocid;
      int i = arrayOfString.length;
      for (int j = 0; j < i; j++) {
        localDfePostRequest.addPostParam("odid", arrayOfString[j]);
      }
      localUri = PREPARE_PURCHASE_URI;
      break;
      label381:
      if (localFinskyExperiments.isEnabled(12602812L))
      {
        localObject = new PreparePurchaseRetryPolicy(3500, this.mApiContext);
        break label80;
      }
      if (localFinskyExperiments.isEnabled(12602814L))
      {
        localObject = new PreparePurchaseRetryPolicy(5000, this.mApiContext);
        break label80;
      }
      if (localFinskyExperiments.isEnabled(12602816L))
      {
        localObject = new PreparePurchaseRetryPolicy(7000, this.mApiContext);
        break label80;
      }
      localObject = makePurchaseRetryPolicy();
      break label80;
      label480:
      localDfePostRequest.addPostParam("oi", paramString2);
    }
    label492:
    addAuthInfoToPurchaseRequest(localDfePostRequest, paramAuthenticationInfo);
    if (paramMap != null)
    {
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localDfePostRequest.addPostParam((String)localEntry.getKey(), (String)localEntry.getValue());
      }
    }
    return (DfeRequest)this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> rateReview(String paramString1, String paramString2, int paramInt, Response.Listener<ReviewResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(RATEREVIEW_URI.buildUpon().appendQueryParameter("doc", paramString1).appendQueryParameter("revId", paramString2).appendQueryParameter("rating", Integer.toString(paramInt)).build().toString(), this.mApiContext, ReviewResponse.class, paramListener, paramErrorListener);
    localDfeRequest.mShouldCache = false;
    localDfeRequest.mIncludeCheckinConsistencyToken = true;
    localDfeRequest.mAvoidBulkCancel = true;
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> rateSuggestedContent(String paramString, Response.Listener<RateSuggestedContentResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(paramString, this.mApiContext, RateSuggestedContentResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> redeemCode(PromoCode.RedeemCodeRequest paramRedeemCodeRequest, Response.Listener<PromoCode.RedeemCodeResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(REDEEM_CODE_URI.toString(), paramRedeemCodeRequest, this.mApiContext, PromoCode.RedeemCodeResponse.class, paramListener, paramErrorListener);
    localProtoDfeRequest.mResponseVerifier = new DfeResponseVerifierImpl(this.mApiContext.mContext);
    localProtoDfeRequest.mRetryPolicy = makePurchaseRetryPolicy();
    addPublicAndroidIdHeaderForRads(localProtoDfeRequest);
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  public final Request<?> removeFromLibrary(Collection<String> paramCollection, String paramString, Response.Listener<ModifyLibrary.ModifyLibraryResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    ModifyLibrary.ModifyLibraryRequest localModifyLibraryRequest = new ModifyLibrary.ModifyLibraryRequest();
    localModifyLibraryRequest.libraryId = paramString;
    localModifyLibraryRequest.hasLibraryId = true;
    localModifyLibraryRequest.forRemovalDocid = ((String[])paramCollection.toArray(new String[paramCollection.size()]));
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(MODIFY_LIBRARY_URI.toString(), localModifyLibraryRequest, this.mApiContext, ModifyLibrary.ModifyLibraryResponse.class, paramListener, paramErrorListener);
    localProtoDfeRequest.mResponseVerifier = new DfeResponseVerifierImpl(this.mApiContext.mContext);
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  public final Request<?> replicateLibrary(LibraryReplicationRequest paramLibraryReplicationRequest, Response.Listener<LibraryReplicationResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(REPLICATE_LIBRARY_URI.toString(), paramLibraryReplicationRequest, this.mApiContext, LibraryReplicationResponse.class, paramListener, paramErrorListener);
    localProtoDfeRequest.mResponseVerifier = new DfeResponseVerifierImpl(this.mApiContext.mContext);
    localProtoDfeRequest.mRetryPolicy = new DfeRetryPolicy(REPLICATE_LIBRARY_TIMEOUT_MS, REPLICATE_LIBRARY_MAX_RETRIES, REPLICATE_LIBRARY_BACKOFF_MULT, this.mApiContext);
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  public final Request<?> requestAgeVerificationForm(Response.Listener<ChallengeResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(REQUEST_AGE_VERIFICATION_FORM_URI.toString(), this.mApiContext, ChallengeResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.mRetryPolicy = makeAgeVerificationRetryPolicy();
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> resendAgeVerificationCode(String paramString, Response.Listener<ChallengeResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(paramString, this.mApiContext, ChallengeResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.mRetryPolicy = makeAgeVerificationRetryPolicy();
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> resolveLink(String paramString1, String paramString2, Response.Listener<ResolveLink.ResolvedLink> paramListener, Response.ErrorListener paramErrorListener, DfeRequest.CancelListener paramCancelListener)
  {
    Uri.Builder localBuilder = RESOLVE_LINK.buildUpon().appendQueryParameter("url", paramString1);
    if (!TextUtils.isEmpty(paramString2)) {
      localBuilder.appendQueryParameter("ref", paramString2);
    }
    DfeRequest localDfeRequest = new DfeRequest(0, localBuilder.toString(), this.mApiContext, ResolveLink.ResolvedLink.class, paramListener, paramErrorListener, paramCancelListener);
    addPublicAndroidIdHeaderForRads(localDfeRequest);
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> revoke$b40b8a6(String paramString, Response.Listener<RevokeResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(REVOKE_URI.toString(), this.mApiContext, RevokeResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.mRetryPolicy = makePurchaseRetryPolicy();
    localDfePostRequest.addPostParam("doc", paramString);
    localDfePostRequest.addPostParam("ot", Integer.toString(1));
    localDfePostRequest.mResponseVerifier = new DfeResponseVerifierImpl(this.mApiContext.mContext);
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> saveContentFilters(Response.Listener<ContentFilters.ContentFilterSettingsResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(CONTENT_FILTER_SETTINGS_URI.toString(), this.mApiContext, ContentFilters.ContentFilterSettingsResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> search(String paramString, Response.Listener<Search.SearchResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeRequest localDfeRequest = new DfeRequest(paramString, this.mApiContext, Search.SearchResponse.class, paramListener, paramErrorListener);
    localDfeRequest.mIncludeAdIdAsHeader = true;
    if (FinskyApp.get().getExperiments().isEnabled(12604087L)) {
      addUserContextToRequest(localDfeRequest, DfeApiContext.getUserContext());
    }
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> searchSuggest$1c9e90a1(String paramString, int paramInt1, int paramInt2, boolean paramBoolean, Response.Listener<SearchSuggest.SearchSuggestResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    Uri.Builder localBuilder = SEARCH_SUGGEST_URI.buildUpon().appendQueryParameter("q", paramString).appendQueryParameter("c", Integer.toString(paramInt1)).appendQueryParameter("ssis", Integer.toString(paramInt2));
    localBuilder.appendQueryParameter("sst", Integer.toString(2));
    if (paramBoolean) {
      localBuilder.appendQueryParameter("sst", Integer.toString(3));
    }
    DfeRequest localDfeRequest = new DfeRequest(localBuilder.toString(), this.mApiContext, SearchSuggest.SearchSuggestResponse.class, paramListener, paramErrorListener);
    if (FinskyApp.get().getExperiments().isEnabled(12603401L)) {
      addUserContextToRequest(localDfeRequest, DfeApiContext.getUserContext());
    }
    return this.mQueue.add(localDfeRequest);
  }
  
  public final Request<?> setPlusOne(String paramString, boolean paramBoolean, Response.Listener<PlusOneResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(PLUSONE_URI.toString(), this.mApiContext, PlusOneResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.addPostParam("doc", paramString);
    if (paramBoolean) {}
    for (int i = 1;; i = 0)
    {
      localDfePostRequest.addPostParam("rating", Integer.toString(i));
      return this.mQueue.add(localDfePostRequest);
    }
  }
  
  public final Request<?> updateFamilyPurchaseSetting(String paramString1, String paramString2, int paramInt, Response.Listener<SetFamilyPurchaseSettingResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    FamilyPurchaseSetting localFamilyPurchaseSetting = new FamilyPurchaseSetting();
    localFamilyPurchaseSetting.selectedPurchaseOptionId = paramInt;
    localFamilyPurchaseSetting.docid = paramString2;
    localFamilyPurchaseSetting.consistencyToken = paramString1;
    SetFamilyPurchaseSettingRequest localSetFamilyPurchaseSettingRequest = new SetFamilyPurchaseSettingRequest();
    localSetFamilyPurchaseSettingRequest.familyPurchaseSetting = localFamilyPurchaseSetting;
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(UPDATE_FAMILY_PURCHASE_SETTING_URI.toString(), localSetFamilyPurchaseSettingRequest, this.mApiContext, SetFamilyPurchaseSettingResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  public final Request<?> updateFamilySharingSettings(SharingSetting.FamilySharingSetting[] paramArrayOfFamilySharingSetting, Response.Listener<SharingSetting.UpdateFamilySharingSettingsResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    SharingSetting.UpdateFamilySharingSettingsRequest localUpdateFamilySharingSettingsRequest = new SharingSetting.UpdateFamilySharingSettingsRequest();
    localUpdateFamilySharingSettingsRequest.newSharingSetting = paramArrayOfFamilySharingSetting;
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(UPDATE_FAMILY_SHARE_SETTING_URI.toString(), localUpdateFamilySharingSettingsRequest, this.mApiContext, SharingSetting.UpdateFamilySharingSettingsResponse.class, paramListener, paramErrorListener);
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  @SuppressLint({"NewApi"})
  public final Request<?> updateInstrument(BuyInstruments.UpdateInstrumentRequest paramUpdateInstrumentRequest, Response.Listener<BuyInstruments.UpdateInstrumentResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(UPDATE_INSTRUMENT_URI.toString(), paramUpdateInstrumentRequest, this.mApiContext, BuyInstruments.UpdateInstrumentResponse.class, paramListener, paramErrorListener);
    localProtoDfeRequest.mRetryPolicy = makePurchaseRetryPolicy();
    if (Build.VERSION.SDK_INT >= 9) {
      localProtoDfeRequest.addExtraHeader("X-DFE-Hardware-Id", DfeApiContext.sanitizeHeaderValue(Build.SERIAL), false);
    }
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  public final Request<?> updateMarketingEmailsOptIn(boolean paramBoolean, UserSettingsConsistencyTokens paramUserSettingsConsistencyTokens, Response.Listener<UpdateUserSettingResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    MarketingSettings localMarketingSettings = new MarketingSettings();
    localMarketingSettings.marketingEmailsOptedIn = paramBoolean;
    localMarketingSettings.hasMarketingEmailsOptedIn = true;
    UpdateUserSettingRequest localUpdateUserSettingRequest = new UpdateUserSettingRequest();
    localUpdateUserSettingRequest.marketingSettings = localMarketingSettings;
    return updateUserSetting(localUpdateUserSettingRequest, paramUserSettingsConsistencyTokens, paramListener, paramErrorListener);
  }
  
  public final Request<?> updateUserPrivacySetting(int paramInt1, int paramInt2, UserSettingsConsistencyTokens paramUserSettingsConsistencyTokens, Response.Listener<UpdateUserSettingResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    PrivacySetting localPrivacySetting = new PrivacySetting();
    localPrivacySetting.type = paramInt1;
    localPrivacySetting.hasType = true;
    localPrivacySetting.currentStatus = paramInt2;
    localPrivacySetting.hasCurrentStatus = true;
    UpdateUserSettingRequest localUpdateUserSettingRequest = new UpdateUserSettingRequest();
    localUpdateUserSettingRequest.privacySetting = localPrivacySetting;
    return updateUserSetting(localUpdateUserSettingRequest, paramUserSettingsConsistencyTokens, paramListener, paramErrorListener);
  }
  
  public final Request<?> updateUserSetting(UpdateUserSettingRequest paramUpdateUserSettingRequest, UserSettingsConsistencyTokens paramUserSettingsConsistencyTokens, final Response.Listener<UpdateUserSettingResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    Response.Listener local2 = new Response.Listener() {};
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(UPDATE_USER_SETTING_URI.toString(), paramUpdateUserSettingRequest, this.mApiContext, UpdateUserSettingResponse.class, local2, paramErrorListener);
    localProtoDfeRequest.mUserSettingsConsistencyTokens = paramUserSettingsConsistencyTokens;
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  public final Request<?> uploadDeviceConfig(DeviceConfiguration.DeviceConfigurationProto paramDeviceConfigurationProto, String paramString1, String paramString2, Response.Listener<UploadDeviceConfig.UploadDeviceConfigResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    UploadDeviceConfig.UploadDeviceConfigRequest localUploadDeviceConfigRequest = new UploadDeviceConfig.UploadDeviceConfigRequest();
    if (paramDeviceConfigurationProto != null) {
      localUploadDeviceConfigRequest.deviceConfiguration = paramDeviceConfigurationProto;
    }
    if (paramString1 != null)
    {
      localUploadDeviceConfigRequest.gcmRegistrationId = paramString1;
      localUploadDeviceConfigRequest.hasGcmRegistrationId = true;
    }
    ProtoDfeRequest localProtoDfeRequest = new ProtoDfeRequest(UPLOAD_DEVICE_CONFIG_URI.toString(), localUploadDeviceConfigRequest, this.mApiContext, UploadDeviceConfig.UploadDeviceConfigResponse.class, paramListener, paramErrorListener);
    localProtoDfeRequest.mRetryPolicy = makePurchaseRetryPolicy();
    if (!TextUtils.isEmpty(paramString2)) {
      localProtoDfeRequest.addExtraHeader("X-DFE-Device-Config-Token", Uri.encode(paramString2), false);
    }
    return this.mQueue.add(localProtoDfeRequest);
  }
  
  public final Request<?> verifyAge(String paramString, Map<String, String> paramMap, Response.Listener<ChallengeResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(paramString, this.mApiContext, ChallengeResponse.class, paramListener, paramErrorListener);
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localDfePostRequest.addPostParam((String)localEntry.getKey(), (String)localEntry.getValue());
    }
    localDfePostRequest.mRetryPolicy = makeAgeVerificationRetryPolicy();
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> verifyAgeVerificationCode(String paramString1, String paramString2, String paramString3, Response.Listener<ChallengeResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(paramString1, this.mApiContext, ChallengeResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.addPostParam(paramString2, paramString3);
    localDfePostRequest.mRetryPolicy = makeAgeVerificationRetryPolicy();
    return this.mQueue.add(localDfePostRequest);
  }
  
  public final Request<?> verifyAssociation(String paramString1, String paramString2, boolean paramBoolean, Response.Listener<CarrierBilling.VerifyAssociationResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    DfeApi.DfePostRequest localDfePostRequest = new DfeApi.DfePostRequest(DCB_VERIFY_ASSOCIATION_URI.toString(), this.mApiContext, CarrierBilling.VerifyAssociationResponse.class, paramListener, paramErrorListener);
    localDfePostRequest.addPostParam("dcbch", paramString1);
    if (!TextUtils.isEmpty(paramString2)) {
      localDfePostRequest.addPostParam("dcbptosv", paramString2);
    }
    localDfePostRequest.addPostParam("dcbreqaddr", Boolean.toString(paramBoolean));
    localDfePostRequest.mRetryPolicy = new DfeRetryPolicy(VERIFY_ASSOCIATION_TIMEOUT_MS, VERIFY_ASSOCIATION_MAX_RETRIES, 0.0F, this.mApiContext);
    return this.mQueue.add(localDfePostRequest);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.DfeApiImpl
 * JD-Core Version:    0.7.0.1
 */