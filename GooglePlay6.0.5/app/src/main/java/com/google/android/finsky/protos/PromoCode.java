package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface PromoCode
{
  public static final class RedeemCodeRequest
    extends MessageNano
  {
    public Address address = null;
    public String[] addressCheckedCheckboxId = WireFormatNano.EMPTY_STRING_ARRAY;
    public String code = "";
    public long consumptionAppVersionCode = 0L;
    public Common.Docid docid = null;
    public boolean hasCode = false;
    public boolean hasConsumptionAppVersionCode = false;
    public boolean hasHasUserConfirmation = false;
    public boolean hasOfferType = false;
    public boolean hasPartnerPayload = false;
    public boolean hasPaymentsIntegratorClientContextToken = false;
    public boolean hasRedemptionContext = false;
    public boolean hasToken = false;
    public boolean hasUserConfirmation = false;
    public int offerType = 1;
    public String partnerPayload = "";
    public byte[] paymentsIntegratorClientContextToken = WireFormatNano.EMPTY_BYTES;
    public int redemptionContext = 1;
    public String token = "";
    
    public RedeemCodeRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasCode) || (!this.code.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.code);
      }
      if ((this.hasHasUserConfirmation) || (this.hasUserConfirmation)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if (this.address != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.address);
      }
      if ((this.addressCheckedCheckboxId != null) && (this.addressCheckedCheckboxId.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.addressCheckedCheckboxId.length; m++)
        {
          String str = this.addressCheckedCheckboxId[m];
          if (str != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + k + j * 1;
      }
      if ((this.hasToken) || (!this.token.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.token);
      }
      if ((this.redemptionContext != 1) || (this.hasRedemptionContext)) {
        i += CodedOutputByteBufferNano.computeInt32Size(6, this.redemptionContext);
      }
      if ((this.hasPartnerPayload) || (!this.partnerPayload.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.partnerPayload);
      }
      if (this.docid != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.docid);
      }
      if ((this.offerType != 1) || (this.hasOfferType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(9, this.offerType);
      }
      if ((this.hasConsumptionAppVersionCode) || (this.consumptionAppVersionCode != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(10, this.consumptionAppVersionCode);
      }
      if ((this.hasPaymentsIntegratorClientContextToken) || (!Arrays.equals(this.paymentsIntegratorClientContextToken, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(11, this.paymentsIntegratorClientContextToken);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasCode) || (!this.code.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.code);
      }
      if ((this.hasHasUserConfirmation) || (this.hasUserConfirmation)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.hasUserConfirmation);
      }
      if (this.address != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.address);
      }
      if ((this.addressCheckedCheckboxId != null) && (this.addressCheckedCheckboxId.length > 0)) {
        for (int i = 0; i < this.addressCheckedCheckboxId.length; i++)
        {
          String str = this.addressCheckedCheckboxId[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(4, str);
          }
        }
      }
      if ((this.hasToken) || (!this.token.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.token);
      }
      if ((this.redemptionContext != 1) || (this.hasRedemptionContext)) {
        paramCodedOutputByteBufferNano.writeInt32(6, this.redemptionContext);
      }
      if ((this.hasPartnerPayload) || (!this.partnerPayload.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.partnerPayload);
      }
      if (this.docid != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.docid);
      }
      if ((this.offerType != 1) || (this.hasOfferType)) {
        paramCodedOutputByteBufferNano.writeInt32(9, this.offerType);
      }
      if ((this.hasConsumptionAppVersionCode) || (this.consumptionAppVersionCode != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(10, this.consumptionAppVersionCode);
      }
      if ((this.hasPaymentsIntegratorClientContextToken) || (!Arrays.equals(this.paymentsIntegratorClientContextToken, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(11, this.paymentsIntegratorClientContextToken);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RedeemCodeResponse
    extends MessageNano
  {
    public ChallengeProto.AddressChallenge addressChallenge = null;
    public Common.Docid consumptionAppDocid = null;
    public DocV2 doc = null;
    public String errorMessageHtml = "";
    public boolean hasErrorMessageHtml = false;
    public boolean hasResult = false;
    public boolean hasServerLogsCookie = false;
    public boolean hasStoredValueInstrumentId = false;
    public boolean hasToken = false;
    public LibraryUpdateProto.LibraryUpdate[] libraryUpdate = LibraryUpdateProto.LibraryUpdate.emptyArray();
    public SingleFopPaymentsIntegratorContext paymentsIntegratorContext = null;
    public Acquisition.PostAcquisitionPrompt postAcquisitionPrompt = null;
    public RedeemedPromoOffer redeemedOffer = null;
    public int result = 1;
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public String storedValueInstrumentId = "";
    public Acquisition.SuccessInfo successInfo = null;
    public String token = "";
    public PromoCode.UserConfirmationChallenge userConfirmationChallenge = null;
    
    public RedeemCodeResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.result != 1) || (this.hasResult)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.result);
      }
      if ((this.hasToken) || (!this.token.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.token);
      }
      if (this.userConfirmationChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.userConfirmationChallenge);
      }
      if (this.addressChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.addressChallenge);
      }
      if (this.successInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.successInfo);
      }
      if ((this.hasErrorMessageHtml) || (!this.errorMessageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.errorMessageHtml);
      }
      if (this.redeemedOffer != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.redeemedOffer);
      }
      if ((this.hasStoredValueInstrumentId) || (!this.storedValueInstrumentId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.storedValueInstrumentId);
      }
      if (this.consumptionAppDocid != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.consumptionAppDocid);
      }
      if (this.doc != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.doc);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(12, this.serverLogsCookie);
      }
      if (this.paymentsIntegratorContext != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(13, this.paymentsIntegratorContext);
      }
      if (this.postAcquisitionPrompt != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(14, this.postAcquisitionPrompt);
      }
      if ((this.libraryUpdate != null) && (this.libraryUpdate.length > 0)) {
        for (int j = 0; j < this.libraryUpdate.length; j++)
        {
          LibraryUpdateProto.LibraryUpdate localLibraryUpdate = this.libraryUpdate[j];
          if (localLibraryUpdate != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(15, localLibraryUpdate);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.result != 1) || (this.hasResult)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.result);
      }
      if ((this.hasToken) || (!this.token.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.token);
      }
      if (this.userConfirmationChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.userConfirmationChallenge);
      }
      if (this.addressChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.addressChallenge);
      }
      if (this.successInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.successInfo);
      }
      if ((this.hasErrorMessageHtml) || (!this.errorMessageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.errorMessageHtml);
      }
      if (this.redeemedOffer != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.redeemedOffer);
      }
      if ((this.hasStoredValueInstrumentId) || (!this.storedValueInstrumentId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.storedValueInstrumentId);
      }
      if (this.consumptionAppDocid != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.consumptionAppDocid);
      }
      if (this.doc != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.doc);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(12, this.serverLogsCookie);
      }
      if (this.paymentsIntegratorContext != null) {
        paramCodedOutputByteBufferNano.writeMessage(13, this.paymentsIntegratorContext);
      }
      if (this.postAcquisitionPrompt != null) {
        paramCodedOutputByteBufferNano.writeMessage(14, this.postAcquisitionPrompt);
      }
      if ((this.libraryUpdate != null) && (this.libraryUpdate.length > 0)) {
        for (int i = 0; i < this.libraryUpdate.length; i++)
        {
          LibraryUpdateProto.LibraryUpdate localLibraryUpdate = this.libraryUpdate[i];
          if (localLibraryUpdate != null) {
            paramCodedOutputByteBufferNano.writeMessage(15, localLibraryUpdate);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class UserConfirmationChallenge
    extends MessageNano
  {
    public String buttonLabel = "";
    public String footerHtml = "";
    public String formattedPrice = "";
    public boolean hasButtonLabel = false;
    public boolean hasFooterHtml = false;
    public boolean hasFormattedPrice = false;
    public boolean hasMessageHtml = false;
    public boolean hasPriceBylineHtml = false;
    public boolean hasTitle = false;
    public boolean hasTitleBylineHtml = false;
    public String messageHtml = "";
    public String priceBylineHtml = "";
    public Common.Image thumbnailImage = null;
    public String title = "";
    public String titleBylineHtml = "";
    
    public UserConfirmationChallenge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasTitleBylineHtml) || (!this.titleBylineHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.titleBylineHtml);
      }
      if ((this.hasFormattedPrice) || (!this.formattedPrice.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.formattedPrice);
      }
      if ((this.hasPriceBylineHtml) || (!this.priceBylineHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.priceBylineHtml);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.buttonLabel);
      }
      if (this.thumbnailImage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.thumbnailImage);
      }
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.messageHtml);
      }
      if ((this.hasFooterHtml) || (!this.footerHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.footerHtml);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasTitleBylineHtml) || (!this.titleBylineHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.titleBylineHtml);
      }
      if ((this.hasFormattedPrice) || (!this.formattedPrice.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.formattedPrice);
      }
      if ((this.hasPriceBylineHtml) || (!this.priceBylineHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.priceBylineHtml);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.buttonLabel);
      }
      if (this.thumbnailImage != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.thumbnailImage);
      }
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.messageHtml);
      }
      if ((this.hasFooterHtml) || (!this.footerHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.footerHtml);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PromoCode
 * JD-Core Version:    0.7.0.1
 */