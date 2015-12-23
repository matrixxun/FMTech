package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface Response
{
  public static final class Payload
    extends MessageNano
  {
    public AcceptTosResponse acceptTosResponse = null;
    public AckNotificationResponse ackNotificationResponse = null;
    public Restore.GetBackupDeviceChoicesResponse backupDeviceChoicesResponse = null;
    public Restore.GetBackupDocumentChoicesResponse backupDocumentChoicesResponse = null;
    public BuyInstruments.BillingProfileResponse billingProfileResponse = null;
    public Browse.BrowseResponse browseResponse = null;
    public Details.BulkDetailsResponse bulkDetailsResponse = null;
    public Buy.BuyResponse buyResponse = null;
    public ChallengeResponse challengeResponse = null;
    public BuyInstruments.CheckIabPromoResponse checkIabPromoResponse = null;
    public BuyInstruments.CheckInstrumentResponse checkInstrumentResponse = null;
    public CheckPromoOfferResponse checkPromoOfferResponse = null;
    public Purchase.CommitPurchaseResponse commitPurchaseResponse = null;
    public ConsumePurchaseResponse consumePurchaseResponse = null;
    public ContentFilters.ContentFilterSettingsResponse contentFilterResponse = null;
    public BuyInstruments.CreateInstrumentResponse createInstrumentResponse = null;
    public DebugSettingsResponse debugSettingsResponse = null;
    public DeliveryResponse deliveryResponse = null;
    public Details.DetailsResponse detailsResponse = null;
    public DismissSurveyResponse dismissSurveyResponse = null;
    public DocumentSharingStateResponse documentSharingStateResponse = null;
    public EarlyUpdateResponse earlyUpdateResponse = null;
    public ExperimentsResponse experimentsResponse = null;
    public FamilyFopResponse familyFopResponse = null;
    public ContentFlagging.FlagContentResponse flagContentResponse = null;
    public GetFamilyPurchaseSettingResponse getFamilyPurchaseSettingResponse = null;
    public BuyInstruments.GetInitialInstrumentFlowStateResponse getInitialInstrumentFlowStateResponse = null;
    public SharingSetting.GetFamilySharingSettingsResponse getSharingSettingsResponse = null;
    public GetUserSettingsResponse getUserSettingsResponse = null;
    public CarrierBilling.InitiateAssociationResponse initiateAssociationResponse = null;
    public Purchase.InstantPurchaseResponse instantPurchaseResponse = null;
    public BuyInstruments.InstrumentSetupInfoResponse instrumentSetupInfoResponse = null;
    public LibraryReplicationResponse libraryReplicationResponse = null;
    public ListResponse listResponse = null;
    public Log.LogResponse logResponse = null;
    public ModifyLibrary.ModifyLibraryResponse modifyLibraryResponse = null;
    public MyAccountResponse myAccountResponse = null;
    public PingResponse pingResponse = null;
    public PlusOneResponse plusOneResponse = null;
    public PlusProfileResponse plusProfileResponse = null;
    public Preloads.PreloadsResponse preloadsResponse = null;
    public Purchase.PreparePurchaseResponse preparePurchaseResponse = null;
    public Buy.PurchaseStatusResponse purchaseStatusResponse = null;
    public RateSuggestedContentResponse rateSuggestedContentResponse = null;
    public UserActivity.RecordUserActivityResponse recordUserActivityResponse = null;
    public PromoCode.RedeemCodeResponse redeemCodeResponse = null;
    public BuyInstruments.RedeemGiftCardResponse redeemGiftCardResponse = null;
    public ResolveLink.ResolvedLink resolveLinkResponse = null;
    public ReviewResponse reviewResponse = null;
    public ReviewSnippetsResponse reviewSnippetsResponse = null;
    public RevokeResponse revokeResponse = null;
    public Search.SearchResponse searchResponse = null;
    public SearchSuggest.SearchSuggestResponse searchSuggestResponse = null;
    public SelfUpdateResponse selfUpdateResponse = null;
    public SetFamilyPurchaseSettingResponse setFamilyPurchaseSettingReponse = null;
    public SurveyResponse surveyResponse = null;
    public Toc.TocResponse tocResponse = null;
    public BuyInstruments.UpdateInstrumentResponse updateInstrumentResponse = null;
    public SharingSetting.UpdateFamilySharingSettingsResponse updateSharingSettingsResponse = null;
    public UpdateUserSettingResponse updateUserSettingResponse = null;
    public UploadDeviceConfig.UploadDeviceConfigResponse uploadDeviceConfigResponse = null;
    public UserActivity.UserActivitySettingsResponse userActivitySettingsResponse = null;
    public CarrierBilling.VerifyAssociationResponse verifyAssociationResponse = null;
    
    public Payload()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.listResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.listResponse);
      }
      if (this.detailsResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.detailsResponse);
      }
      if (this.reviewResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.reviewResponse);
      }
      if (this.buyResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.buyResponse);
      }
      if (this.searchResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.searchResponse);
      }
      if (this.tocResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.tocResponse);
      }
      if (this.browseResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.browseResponse);
      }
      if (this.purchaseStatusResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.purchaseStatusResponse);
      }
      if (this.updateInstrumentResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.updateInstrumentResponse);
      }
      if (this.logResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.logResponse);
      }
      if (this.checkInstrumentResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.checkInstrumentResponse);
      }
      if (this.plusOneResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(12, this.plusOneResponse);
      }
      if (this.flagContentResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(13, this.flagContentResponse);
      }
      if (this.ackNotificationResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(14, this.ackNotificationResponse);
      }
      if (this.initiateAssociationResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(15, this.initiateAssociationResponse);
      }
      if (this.verifyAssociationResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(16, this.verifyAssociationResponse);
      }
      if (this.libraryReplicationResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(17, this.libraryReplicationResponse);
      }
      if (this.revokeResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(18, this.revokeResponse);
      }
      if (this.bulkDetailsResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(19, this.bulkDetailsResponse);
      }
      if (this.resolveLinkResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(20, this.resolveLinkResponse);
      }
      if (this.deliveryResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(21, this.deliveryResponse);
      }
      if (this.acceptTosResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(22, this.acceptTosResponse);
      }
      if (this.rateSuggestedContentResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(23, this.rateSuggestedContentResponse);
      }
      if (this.checkPromoOfferResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(24, this.checkPromoOfferResponse);
      }
      if (this.instrumentSetupInfoResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(25, this.instrumentSetupInfoResponse);
      }
      if (this.redeemGiftCardResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(26, this.redeemGiftCardResponse);
      }
      if (this.modifyLibraryResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(27, this.modifyLibraryResponse);
      }
      if (this.uploadDeviceConfigResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(28, this.uploadDeviceConfigResponse);
      }
      if (this.plusProfileResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(29, this.plusProfileResponse);
      }
      if (this.consumePurchaseResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(30, this.consumePurchaseResponse);
      }
      if (this.billingProfileResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(31, this.billingProfileResponse);
      }
      if (this.preparePurchaseResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(32, this.preparePurchaseResponse);
      }
      if (this.commitPurchaseResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(33, this.commitPurchaseResponse);
      }
      if (this.debugSettingsResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(34, this.debugSettingsResponse);
      }
      if (this.checkIabPromoResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(35, this.checkIabPromoResponse);
      }
      if (this.userActivitySettingsResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(36, this.userActivitySettingsResponse);
      }
      if (this.recordUserActivityResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(37, this.recordUserActivityResponse);
      }
      if (this.redeemCodeResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(38, this.redeemCodeResponse);
      }
      if (this.selfUpdateResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(39, this.selfUpdateResponse);
      }
      if (this.searchSuggestResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(40, this.searchSuggestResponse);
      }
      if (this.getInitialInstrumentFlowStateResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(41, this.getInitialInstrumentFlowStateResponse);
      }
      if (this.createInstrumentResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(42, this.createInstrumentResponse);
      }
      if (this.challengeResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(43, this.challengeResponse);
      }
      if (this.backupDeviceChoicesResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(44, this.backupDeviceChoicesResponse);
      }
      if (this.backupDocumentChoicesResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(45, this.backupDocumentChoicesResponse);
      }
      if (this.earlyUpdateResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(46, this.earlyUpdateResponse);
      }
      if (this.preloadsResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(47, this.preloadsResponse);
      }
      if (this.myAccountResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(48, this.myAccountResponse);
      }
      if (this.contentFilterResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(49, this.contentFilterResponse);
      }
      if (this.experimentsResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(50, this.experimentsResponse);
      }
      if (this.surveyResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(51, this.surveyResponse);
      }
      if (this.pingResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(52, this.pingResponse);
      }
      if (this.updateUserSettingResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(53, this.updateUserSettingResponse);
      }
      if (this.getUserSettingsResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(54, this.getUserSettingsResponse);
      }
      if (this.getSharingSettingsResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(56, this.getSharingSettingsResponse);
      }
      if (this.updateSharingSettingsResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(57, this.updateSharingSettingsResponse);
      }
      if (this.reviewSnippetsResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(58, this.reviewSnippetsResponse);
      }
      if (this.documentSharingStateResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(59, this.documentSharingStateResponse);
      }
      if (this.getFamilyPurchaseSettingResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(60, this.getFamilyPurchaseSettingResponse);
      }
      if (this.setFamilyPurchaseSettingReponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(61, this.setFamilyPurchaseSettingReponse);
      }
      if (this.dismissSurveyResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(62, this.dismissSurveyResponse);
      }
      if (this.instantPurchaseResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(63, this.instantPurchaseResponse);
      }
      if (this.familyFopResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(64, this.familyFopResponse);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.listResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.listResponse);
      }
      if (this.detailsResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.detailsResponse);
      }
      if (this.reviewResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.reviewResponse);
      }
      if (this.buyResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.buyResponse);
      }
      if (this.searchResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.searchResponse);
      }
      if (this.tocResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.tocResponse);
      }
      if (this.browseResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.browseResponse);
      }
      if (this.purchaseStatusResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.purchaseStatusResponse);
      }
      if (this.updateInstrumentResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.updateInstrumentResponse);
      }
      if (this.logResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.logResponse);
      }
      if (this.checkInstrumentResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.checkInstrumentResponse);
      }
      if (this.plusOneResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(12, this.plusOneResponse);
      }
      if (this.flagContentResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(13, this.flagContentResponse);
      }
      if (this.ackNotificationResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(14, this.ackNotificationResponse);
      }
      if (this.initiateAssociationResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(15, this.initiateAssociationResponse);
      }
      if (this.verifyAssociationResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(16, this.verifyAssociationResponse);
      }
      if (this.libraryReplicationResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(17, this.libraryReplicationResponse);
      }
      if (this.revokeResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(18, this.revokeResponse);
      }
      if (this.bulkDetailsResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(19, this.bulkDetailsResponse);
      }
      if (this.resolveLinkResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(20, this.resolveLinkResponse);
      }
      if (this.deliveryResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(21, this.deliveryResponse);
      }
      if (this.acceptTosResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(22, this.acceptTosResponse);
      }
      if (this.rateSuggestedContentResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(23, this.rateSuggestedContentResponse);
      }
      if (this.checkPromoOfferResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(24, this.checkPromoOfferResponse);
      }
      if (this.instrumentSetupInfoResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(25, this.instrumentSetupInfoResponse);
      }
      if (this.redeemGiftCardResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(26, this.redeemGiftCardResponse);
      }
      if (this.modifyLibraryResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(27, this.modifyLibraryResponse);
      }
      if (this.uploadDeviceConfigResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(28, this.uploadDeviceConfigResponse);
      }
      if (this.plusProfileResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(29, this.plusProfileResponse);
      }
      if (this.consumePurchaseResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(30, this.consumePurchaseResponse);
      }
      if (this.billingProfileResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(31, this.billingProfileResponse);
      }
      if (this.preparePurchaseResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(32, this.preparePurchaseResponse);
      }
      if (this.commitPurchaseResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(33, this.commitPurchaseResponse);
      }
      if (this.debugSettingsResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(34, this.debugSettingsResponse);
      }
      if (this.checkIabPromoResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(35, this.checkIabPromoResponse);
      }
      if (this.userActivitySettingsResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(36, this.userActivitySettingsResponse);
      }
      if (this.recordUserActivityResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(37, this.recordUserActivityResponse);
      }
      if (this.redeemCodeResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(38, this.redeemCodeResponse);
      }
      if (this.selfUpdateResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(39, this.selfUpdateResponse);
      }
      if (this.searchSuggestResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(40, this.searchSuggestResponse);
      }
      if (this.getInitialInstrumentFlowStateResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(41, this.getInitialInstrumentFlowStateResponse);
      }
      if (this.createInstrumentResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(42, this.createInstrumentResponse);
      }
      if (this.challengeResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(43, this.challengeResponse);
      }
      if (this.backupDeviceChoicesResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(44, this.backupDeviceChoicesResponse);
      }
      if (this.backupDocumentChoicesResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(45, this.backupDocumentChoicesResponse);
      }
      if (this.earlyUpdateResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(46, this.earlyUpdateResponse);
      }
      if (this.preloadsResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(47, this.preloadsResponse);
      }
      if (this.myAccountResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(48, this.myAccountResponse);
      }
      if (this.contentFilterResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(49, this.contentFilterResponse);
      }
      if (this.experimentsResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(50, this.experimentsResponse);
      }
      if (this.surveyResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(51, this.surveyResponse);
      }
      if (this.pingResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(52, this.pingResponse);
      }
      if (this.updateUserSettingResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(53, this.updateUserSettingResponse);
      }
      if (this.getUserSettingsResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(54, this.getUserSettingsResponse);
      }
      if (this.getSharingSettingsResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(56, this.getSharingSettingsResponse);
      }
      if (this.updateSharingSettingsResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(57, this.updateSharingSettingsResponse);
      }
      if (this.reviewSnippetsResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(58, this.reviewSnippetsResponse);
      }
      if (this.documentSharingStateResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(59, this.documentSharingStateResponse);
      }
      if (this.getFamilyPurchaseSettingResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(60, this.getFamilyPurchaseSettingResponse);
      }
      if (this.setFamilyPurchaseSettingReponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(61, this.setFamilyPurchaseSettingReponse);
      }
      if (this.dismissSurveyResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(62, this.dismissSurveyResponse);
      }
      if (this.instantPurchaseResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(63, this.instantPurchaseResponse);
      }
      if (this.familyFopResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(64, this.familyFopResponse);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ResponseWrapper
    extends MessageNano
  {
    public ServerCommands commands = null;
    public Notification[] notification = Notification.emptyArray();
    public Response.Payload payload = null;
    public PreFetch[] preFetch = PreFetch.emptyArray();
    public ServerCookies serverCookies = null;
    public ServerMetadata serverMetadata = null;
    public Targets targets = null;
    
    public ResponseWrapper()
    {
      this.cachedSize = -1;
    }
    
    public static ResponseWrapper parseFrom(byte[] paramArrayOfByte)
      throws InvalidProtocolBufferNanoException
    {
      return (ResponseWrapper)MessageNano.mergeFrom$1ec43da(new ResponseWrapper(), paramArrayOfByte, paramArrayOfByte.length);
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.payload != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.payload);
      }
      if (this.commands != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.commands);
      }
      if ((this.preFetch != null) && (this.preFetch.length > 0)) {
        for (int k = 0; k < this.preFetch.length; k++)
        {
          PreFetch localPreFetch = this.preFetch[k];
          if (localPreFetch != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localPreFetch);
          }
        }
      }
      if ((this.notification != null) && (this.notification.length > 0)) {
        for (int j = 0; j < this.notification.length; j++)
        {
          Notification localNotification = this.notification[j];
          if (localNotification != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localNotification);
          }
        }
      }
      if (this.serverMetadata != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.serverMetadata);
      }
      if (this.targets != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.targets);
      }
      if (this.serverCookies != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.serverCookies);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.payload != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.payload);
      }
      if (this.commands != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.commands);
      }
      if ((this.preFetch != null) && (this.preFetch.length > 0)) {
        for (int j = 0; j < this.preFetch.length; j++)
        {
          PreFetch localPreFetch = this.preFetch[j];
          if (localPreFetch != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localPreFetch);
          }
        }
      }
      if ((this.notification != null) && (this.notification.length > 0)) {
        for (int i = 0; i < this.notification.length; i++)
        {
          Notification localNotification = this.notification[i];
          if (localNotification != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localNotification);
          }
        }
      }
      if (this.serverMetadata != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.serverMetadata);
      }
      if (this.targets != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.targets);
      }
      if (this.serverCookies != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.serverCookies);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Response
 * JD-Core Version:    0.7.0.1
 */