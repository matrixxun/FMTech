package com.google.android.finsky.api;

import android.accounts.Account;
import android.net.Uri;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.VoucherParams;
import com.google.android.finsky.protos.AcceptTosResponse;
import com.google.android.finsky.protos.AckNotificationResponse;
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
import com.google.android.finsky.protos.Details.BulkDetailsResponse;
import com.google.android.finsky.protos.Details.DetailsResponse;
import com.google.android.finsky.protos.DeviceConfiguration.DeviceConfigurationProto;
import com.google.android.finsky.protos.DismissSurveyResponse;
import com.google.android.finsky.protos.EarlyUpdateResponse;
import com.google.android.finsky.protos.FamilyFopRequest;
import com.google.android.finsky.protos.FamilyFopResponse;
import com.google.android.finsky.protos.GetFamilyPurchaseSettingResponse;
import com.google.android.finsky.protos.GetUserSettingsResponse;
import com.google.android.finsky.protos.LibraryReplicationRequest;
import com.google.android.finsky.protos.LibraryReplicationResponse;
import com.google.android.finsky.protos.ListResponse;
import com.google.android.finsky.protos.Log.LogRequest;
import com.google.android.finsky.protos.Log.LogResponse;
import com.google.android.finsky.protos.ModifyLibrary.ModifyLibraryResponse;
import com.google.android.finsky.protos.MyAccountResponse;
import com.google.android.finsky.protos.PingResponse;
import com.google.android.finsky.protos.PlusOneResponse;
import com.google.android.finsky.protos.Preloads.PreloadsResponse;
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
import com.google.android.finsky.protos.SetFamilyPurchaseSettingResponse;
import com.google.android.finsky.protos.SharingSetting.FamilySharingSetting;
import com.google.android.finsky.protos.SharingSetting.GetFamilySharingSettingsResponse;
import com.google.android.finsky.protos.SharingSetting.UpdateFamilySharingSettingsResponse;
import com.google.android.finsky.protos.SurveyResponse;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.protos.UpdateUserSettingRequest;
import com.google.android.finsky.protos.UpdateUserSettingResponse;
import com.google.android.finsky.protos.UploadDeviceConfig.UploadDeviceConfigResponse;
import com.google.android.finsky.protos.UserContext;
import com.google.android.finsky.protos.UserSettingsConsistencyTokens;
import com.google.protobuf.nano.MessageNano;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract interface DfeApi
{
  public static final Uri ACCEPT_TOS_URI;
  public static final Uri ACK_NOTIFICATION_URI;
  public static final Uri ADDREVIEW_URI;
  public static final Uri BASE_URI = Uri.parse("https://android.clients.google.com/fdfe/");
  public static final Uri BILLING_PROFILE_URI;
  public static final Uri BULK_DETAILS_URI;
  public static final Uri CHANGE_FAMILY_INSTRUMENT_URI;
  public static final Uri CHANNELS_URI = Uri.parse("toc");
  public static final Uri CHECK_IAB_PROMO_URI;
  public static final Uri CHECK_INSTRUMENT_URI;
  public static final Uri CHECK_PROMO_OFFER_URI;
  public static final Uri COMMIT_PURCHASE_URI;
  public static final Uri CONSUME_PURCHASE_URI;
  public static final Uri CONTENT_FILTER_SETTINGS_URI;
  public static final Uri DCB_INITIATE_ASSOCIATION_URI;
  public static final Uri DCB_VERIFY_ASSOCIATION_URI;
  public static final Uri DEBUG_SETTINGS_URI;
  public static final Uri DELETEREVIEW_URI;
  public static final Uri DELIVERY_URI;
  public static final Uri DISMISS_SURVEY_REQUEST_URI;
  public static final Uri DOCUMENT_SHARING_STATE_URI;
  public static final Uri EARLY_DELIVERY_URI;
  public static final Uri EARLY_UPDATE_URI;
  public static final Uri EES_COMMIT_PURCHASE_URI;
  public static final Uri EES_PREPARE_PURCHASE_URI;
  public static final Uri FLAG_CONTENT_URI;
  public static final Uri GET_BACKUP_DEVICE_CHOICES_URI;
  public static final Uri GET_BACKUP_DOCUMENT_CHOICES_URI;
  public static final Uri GET_FAMILY_PURCHASE_SETTING_URI = Uri.parse("getFamilyPurchaseSetting");
  public static final Uri GET_FAMILY_SHARE_SETTINGS_URI;
  public static final Uri GET_USER_SETTINGS_URI;
  public static final Uri INSTANT_PURCHASE_URI;
  public static final Uri LIBRARY_URI;
  public static final Uri LOG_URI;
  public static final Uri MODIFY_LIBRARY_URI;
  public static final Uri MY_ACCOUNT_URI;
  public static final Uri PING_URI;
  public static final Uri PLUSONE_URI;
  public static final Uri PRELOADS_URI;
  public static final Uri PREPARE_PURCHASE_URI;
  public static final Uri PURCHASE_URI;
  public static final Uri RATEREVIEW_URI;
  public static final Uri REDEEM_CODE_URI;
  public static final Uri REPLICATE_LIBRARY_URI;
  public static final Uri REQUEST_AGE_VERIFICATION_FORM_URI;
  public static final Uri RESOLVE_LINK;
  public static final Uri REVOKE_URI;
  public static final Uri SEARCH_CHANNEL_URI;
  public static final Uri SEARCH_SUGGEST_URI;
  public static final Uri SELFUPDATE_URI = Uri.parse("selfUpdate");
  public static final Uri SURVEY_REQUEST_URI;
  public static final Uri UPDATE_FAMILY_PURCHASE_SETTING_URI;
  public static final Uri UPDATE_FAMILY_SHARE_SETTING_URI;
  public static final Uri UPDATE_INSTRUMENT_URI;
  public static final Uri UPDATE_USER_SETTING_URI;
  public static final Uri UPLOAD_DEVICE_CONFIG_URI;
  
  static
  {
    SEARCH_CHANNEL_URI = Uri.parse("search");
    SEARCH_SUGGEST_URI = Uri.parse("searchSuggest");
    DEBUG_SETTINGS_URI = Uri.parse("debugSettings");
    MY_ACCOUNT_URI = Uri.parse("myAccount");
    ADDREVIEW_URI = Uri.parse("addReview");
    DELETEREVIEW_URI = Uri.parse("deleteReview");
    RATEREVIEW_URI = Uri.parse("rateReview");
    PURCHASE_URI = Uri.parse("purchase");
    PREPARE_PURCHASE_URI = Uri.parse("preparePurchase");
    COMMIT_PURCHASE_URI = Uri.parse("commitPurchase");
    INSTANT_PURCHASE_URI = Uri.parse("instantPurchase");
    EES_PREPARE_PURCHASE_URI = Uri.parse("ees/preparePurchase");
    EES_COMMIT_PURCHASE_URI = Uri.parse("ees/commitPurchase");
    REVOKE_URI = Uri.parse("revoke");
    UPDATE_INSTRUMENT_URI = Uri.parse("updateInstrument");
    CHANGE_FAMILY_INSTRUMENT_URI = Uri.parse("familyFop");
    CHECK_INSTRUMENT_URI = Uri.parse("checkInstrument");
    GET_BACKUP_DEVICE_CHOICES_URI = Uri.parse("getBackupDeviceChoices");
    GET_BACKUP_DOCUMENT_CHOICES_URI = Uri.parse("getBackupDocumentChoices");
    CHECK_PROMO_OFFER_URI = Uri.parse("checkPromoOffer");
    CHECK_IAB_PROMO_URI = Uri.parse("checkIabPromo");
    BILLING_PROFILE_URI = Uri.parse("billingProfile");
    LOG_URI = Uri.parse("log");
    FLAG_CONTENT_URI = Uri.parse("flagContent");
    PLUSONE_URI = Uri.parse("plusOne");
    ACK_NOTIFICATION_URI = Uri.parse("ack");
    ACCEPT_TOS_URI = Uri.parse("acceptTos");
    LIBRARY_URI = Uri.parse("library");
    BULK_DETAILS_URI = Uri.parse("bulkDetails");
    RESOLVE_LINK = Uri.parse("resolveLink");
    DCB_INITIATE_ASSOCIATION_URI = Uri.parse("dcbInitiateAssociation");
    DCB_VERIFY_ASSOCIATION_URI = Uri.parse("dcbVerifyAssociation");
    REPLICATE_LIBRARY_URI = Uri.parse("replicateLibrary");
    DELIVERY_URI = Uri.parse("delivery");
    MODIFY_LIBRARY_URI = Uri.parse("modifyLibrary");
    CONSUME_PURCHASE_URI = Uri.parse("consumePurchase");
    UPLOAD_DEVICE_CONFIG_URI = Uri.parse("uploadDeviceConfig");
    EARLY_UPDATE_URI = Uri.parse("earlyUpdate");
    EARLY_DELIVERY_URI = Uri.parse("earlyDelivery");
    PRELOADS_URI = Uri.parse("preloads");
    REDEEM_CODE_URI = Uri.parse("redeemCode");
    CONTENT_FILTER_SETTINGS_URI = Uri.parse("contentFilterSettings");
    REQUEST_AGE_VERIFICATION_FORM_URI = Uri.parse("verifyAge");
    PING_URI = Uri.parse("ping");
    SURVEY_REQUEST_URI = Uri.parse("survey");
    DISMISS_SURVEY_REQUEST_URI = Uri.parse("dismissSurvey");
    UPDATE_USER_SETTING_URI = Uri.parse("userSetting");
    GET_USER_SETTINGS_URI = Uri.parse("userSettings");
    DOCUMENT_SHARING_STATE_URI = Uri.parse("documentSharingState");
    UPDATE_FAMILY_SHARE_SETTING_URI = Uri.parse("updateFamilySharingSettings");
    GET_FAMILY_SHARE_SETTINGS_URI = Uri.parse("getFamilySharingSettings");
    UPDATE_FAMILY_PURCHASE_SETTING_URI = Uri.parse("setFamilyPurchaseSetting");
  }
  
  public abstract Request<?> acceptTos(String paramString, Boolean paramBoolean, Response.Listener<AcceptTosResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> ackNotification(String paramString, Response.Listener<AckNotificationResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> addReview(String paramString1, String paramString2, String paramString3, int paramInt, boolean paramBoolean, Response.Listener<ReviewResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> addToLibrary(Collection<String> paramCollection, String paramString, Response.Listener<ModifyLibrary.ModifyLibraryResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> archiveFromLibrary(Collection<String> paramCollection, String paramString, Response.Listener<ModifyLibrary.ModifyLibraryResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> billingProfile(String paramString, Map<String, String> paramMap, Response.Listener<BuyInstruments.BillingProfileResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> changeFamilyInstrument(FamilyFopRequest paramFamilyFopRequest, Response.Listener<FamilyFopResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> checkIabPromo(int paramInt, String paramString1, String paramString2, Response.Listener<BuyInstruments.CheckIabPromoResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> checkInstrument(Response.Listener<BuyInstruments.CheckInstrumentResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> checkPromoOffers(Response.Listener<CheckPromoOfferResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract DfeRequest<?> commitPurchase(String paramString1, Map<String, String> paramMap, Purchase.AuthenticationInfo paramAuthenticationInfo, Purchase.DcbPurchaseInfo paramDcbPurchaseInfo, String paramString2, boolean paramBoolean, UserSettingsConsistencyTokens paramUserSettingsConsistencyTokens, Response.Listener<Purchase.CommitPurchaseResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> consumePurchase$3e88e590(String paramString1, String paramString2, Response.Listener<ConsumePurchaseResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> contentFilterSettings(Response.Listener<ContentFilters.ContentFilterSettingsResponse> paramListener, Response.ErrorListener paramErrorListener, boolean paramBoolean);
  
  public abstract Request<?> debugSettings(String paramString, Response.Listener<DebugSettingsResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> deleteReview(String paramString, Response.Listener<ReviewResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> delivery$5df18dd4(String paramString1, byte[] paramArrayOfByte, Integer paramInteger1, Integer paramInteger2, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, Response.Listener<DeliveryResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> dismissSurvey(long paramLong, int paramInt, Response.Listener<DismissSurveyResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> earlyDelivery$5b28aa05(String paramString1, Integer paramInteger1, Integer paramInteger2, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, Response.Listener<DeliveryResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> earlyUpdate(DeviceConfiguration.DeviceConfigurationProto paramDeviceConfigurationProto, Response.Listener<EarlyUpdateResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> flagContent(String paramString1, int paramInt, String paramString2, Response.Listener<ContentFlagging.FlagContentResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Account getAccount();
  
  public abstract String getAccountName();
  
  public abstract DfeApiContext getApiContext();
  
  public abstract Request<?> getBackupDeviceChoices(Response.Listener<Restore.GetBackupDeviceChoicesResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getBackupDocumentChoices(long paramLong, Response.Listener<Restore.GetBackupDocumentChoicesResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getBrowseLayout(String paramString, UserContext paramUserContext, Response.Listener<Browse.BrowseResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getBulkDetails(Collection<String> paramCollection, boolean paramBoolean, Response.Listener<Details.BulkDetailsResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getBulkDetails(Collection<String> paramCollection, boolean paramBoolean1, String paramString, boolean paramBoolean2, Response.Listener<Details.BulkDetailsResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getDetails(String paramString, boolean paramBoolean1, boolean paramBoolean2, Collection<String> paramCollection, Response.Listener<Details.DetailsResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getFamilyPurchaseSetting(String paramString, Response.Listener<GetFamilyPurchaseSettingResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getFamilySharingSettings(Response.Listener<SharingSetting.GetFamilySharingSettingsResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract String getLibraryUrl(int paramInt1, String paramString, int paramInt2, byte[] paramArrayOfByte);
  
  public abstract Request<?> getList(String paramString, UserContext paramUserContext, Response.Listener<ListResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getMyAccount(Response.Listener<MyAccountResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getReviews(String paramString, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, Response.Listener<ReviewResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getSelfUpdate(String paramString, Response.Listener<SelfUpdateResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getSnippets(String paramString, Response.Listener<ReviewSnippetsResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getSurveys(Response.Listener<SurveyResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getToc(boolean paramBoolean, String paramString, Response.Listener<Toc.TocResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> getUserSettings(UserSettingsConsistencyTokens paramUserSettingsConsistencyTokens, Response.Listener<GetUserSettingsResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> initiateAssociation(String paramString, Response.Listener<CarrierBilling.InitiateAssociationResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract DfeRequest<?> instantPurchase(Purchase.InstantPurchaseRequest paramInstantPurchaseRequest, Response.Listener<Purchase.InstantPurchaseResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract void invalidateDetailsCache$505cbf4b(String paramString);
  
  public abstract void invalidateListCache$505cbf4b(String paramString);
  
  public abstract void invalidateReviewsCache$13ffb93a(String paramString, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void invalidateSelfUpdateCache();
  
  public abstract void invalidateSurveysCache$1385ff();
  
  public abstract void invalidateTocCache();
  
  public abstract Request<?> log(Log.LogRequest paramLogRequest, Response.Listener<Log.LogResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> makePurchase$668028e1(Document paramDocument, int paramInt, Response.Listener<Buy.BuyResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> pingReferrer(String paramString, Response.Listener<PingResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> pingReferrer(String paramString1, String paramString2, Response.Listener<PingResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> preloads(String paramString1, String paramString2, Response.Listener<Preloads.PreloadsResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract DfeRequest<?> preparePurchase(String paramString1, int paramInt1, String paramString2, Purchase.InAppPurchaseInfo paramInAppPurchaseInfo, Purchase.AuthenticationInfo paramAuthenticationInfo, String paramString3, int paramInt2, VoucherParams paramVoucherParams, int paramInt3, String paramString4, Map<String, String> paramMap, Response.Listener<Purchase.PreparePurchaseResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> rateReview(String paramString1, String paramString2, int paramInt, Response.Listener<ReviewResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> rateSuggestedContent(String paramString, Response.Listener<RateSuggestedContentResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> redeemCode(PromoCode.RedeemCodeRequest paramRedeemCodeRequest, Response.Listener<PromoCode.RedeemCodeResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> removeFromLibrary(Collection<String> paramCollection, String paramString, Response.Listener<ModifyLibrary.ModifyLibraryResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> replicateLibrary(LibraryReplicationRequest paramLibraryReplicationRequest, Response.Listener<LibraryReplicationResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> requestAgeVerificationForm(Response.Listener<ChallengeResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> resendAgeVerificationCode(String paramString, Response.Listener<ChallengeResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> resolveLink(String paramString1, String paramString2, Response.Listener<ResolveLink.ResolvedLink> paramListener, Response.ErrorListener paramErrorListener, DfeRequest.CancelListener paramCancelListener);
  
  public abstract Request<?> revoke$b40b8a6(String paramString, Response.Listener<RevokeResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> saveContentFilters(Response.Listener<ContentFilters.ContentFilterSettingsResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> search(String paramString, Response.Listener<Search.SearchResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> searchSuggest$1c9e90a1(String paramString, int paramInt1, int paramInt2, boolean paramBoolean, Response.Listener<SearchSuggest.SearchSuggestResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> setPlusOne(String paramString, boolean paramBoolean, Response.Listener<PlusOneResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> updateFamilyPurchaseSetting(String paramString1, String paramString2, int paramInt, Response.Listener<SetFamilyPurchaseSettingResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> updateFamilySharingSettings(SharingSetting.FamilySharingSetting[] paramArrayOfFamilySharingSetting, Response.Listener<SharingSetting.UpdateFamilySharingSettingsResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> updateInstrument(BuyInstruments.UpdateInstrumentRequest paramUpdateInstrumentRequest, Response.Listener<BuyInstruments.UpdateInstrumentResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> updateMarketingEmailsOptIn(boolean paramBoolean, UserSettingsConsistencyTokens paramUserSettingsConsistencyTokens, Response.Listener<UpdateUserSettingResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> updateUserPrivacySetting(int paramInt1, int paramInt2, UserSettingsConsistencyTokens paramUserSettingsConsistencyTokens, Response.Listener<UpdateUserSettingResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> updateUserSetting(UpdateUserSettingRequest paramUpdateUserSettingRequest, UserSettingsConsistencyTokens paramUserSettingsConsistencyTokens, Response.Listener<UpdateUserSettingResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> uploadDeviceConfig(DeviceConfiguration.DeviceConfigurationProto paramDeviceConfigurationProto, String paramString1, String paramString2, Response.Listener<UploadDeviceConfig.UploadDeviceConfigResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> verifyAge(String paramString, Map<String, String> paramMap, Response.Listener<ChallengeResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> verifyAgeVerificationCode(String paramString1, String paramString2, String paramString3, Response.Listener<ChallengeResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public abstract Request<?> verifyAssociation(String paramString1, String paramString2, boolean paramBoolean, Response.Listener<CarrierBilling.VerifyAssociationResponse> paramListener, Response.ErrorListener paramErrorListener);
  
  public static final class DfePostRequest<T extends MessageNano>
    extends DfeRequest<T>
  {
    private final Map<String, String> mPostParams = new HashMap();
    
    public DfePostRequest(String paramString, DfeApiContext paramDfeApiContext, Class<T> paramClass, Response.Listener<T> paramListener, Response.ErrorListener paramErrorListener)
    {
      super(paramDfeApiContext, paramClass, paramListener, paramErrorListener, (byte)0);
      this.mShouldCache = false;
      this.mIncludeCheckinConsistencyToken = true;
      this.mAvoidBulkCancel = true;
    }
    
    public final void addPostParam(String paramString1, String paramString2)
    {
      this.mPostParams.put(paramString1, paramString2);
    }
    
    public final Map<String, String> getParams()
    {
      return this.mPostParams;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.DfeApi
 * JD-Core Version:    0.7.0.1
 */