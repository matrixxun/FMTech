package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface ChallengeProto
{
  public static final class AcknowledgementChallenge
    extends MessageNano
  {
    public String descriptionHtml = "";
    public boolean hasDescriptionHtml = false;
    public boolean hasResponseAcknowledgementParam = false;
    public boolean hasServerLogsCookie = false;
    public boolean hasSubmitButtonLabel = false;
    public boolean hasTitle = false;
    public String responseAcknowledgementParam = "";
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public String submitButtonLabel = "";
    public String title = "";
    
    public AcknowledgementChallenge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasResponseAcknowledgementParam) || (!this.responseAcknowledgementParam.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.responseAcknowledgementParam);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.descriptionHtml);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(4, this.serverLogsCookie);
      }
      if ((this.hasSubmitButtonLabel) || (!this.submitButtonLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.submitButtonLabel);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasResponseAcknowledgementParam) || (!this.responseAcknowledgementParam.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.responseAcknowledgementParam);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.descriptionHtml);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(4, this.serverLogsCookie);
      }
      if ((this.hasSubmitButtonLabel) || (!this.submitButtonLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.submitButtonLabel);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class AddressChallenge
    extends MessageNano
  {
    public Address address = null;
    public ChallengeProto.FormCheckbox[] checkbox = ChallengeProto.FormCheckbox.emptyArray();
    public String descriptionHtml = "";
    public String errorHtml = "";
    public ChallengeProto.InputValidationError[] errorInputField = ChallengeProto.InputValidationError.emptyArray();
    public boolean hasDescriptionHtml = false;
    public boolean hasErrorHtml = false;
    public boolean hasResponseAddressParam = false;
    public boolean hasResponseCheckboxesParam = false;
    public boolean hasTitle = false;
    public int[] requiredField = WireFormatNano.EMPTY_INT_ARRAY;
    public String responseAddressParam = "";
    public String responseCheckboxesParam = "";
    public ChallengeProto.Country[] supportedCountry = ChallengeProto.Country.emptyArray();
    public String title = "";
    
    public AddressChallenge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasResponseAddressParam) || (!this.responseAddressParam.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.responseAddressParam);
      }
      if ((this.hasResponseCheckboxesParam) || (!this.responseCheckboxesParam.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.responseCheckboxesParam);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.descriptionHtml);
      }
      if ((this.checkbox != null) && (this.checkbox.length > 0)) {
        for (int i1 = 0; i1 < this.checkbox.length; i1++)
        {
          ChallengeProto.FormCheckbox localFormCheckbox = this.checkbox[i1];
          if (localFormCheckbox != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(5, localFormCheckbox);
          }
        }
      }
      if (this.address != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.address);
      }
      if ((this.errorInputField != null) && (this.errorInputField.length > 0)) {
        for (int n = 0; n < this.errorInputField.length; n++)
        {
          ChallengeProto.InputValidationError localInputValidationError = this.errorInputField[n];
          if (localInputValidationError != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(7, localInputValidationError);
          }
        }
      }
      if ((this.hasErrorHtml) || (!this.errorHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.errorHtml);
      }
      if ((this.requiredField != null) && (this.requiredField.length > 0))
      {
        int k = 0;
        for (int m = 0; m < this.requiredField.length; m++) {
          k += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.requiredField[m]);
        }
        i = i + k + 1 * this.requiredField.length;
      }
      if ((this.supportedCountry != null) && (this.supportedCountry.length > 0)) {
        for (int j = 0; j < this.supportedCountry.length; j++)
        {
          ChallengeProto.Country localCountry = this.supportedCountry[j];
          if (localCountry != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(10, localCountry);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasResponseAddressParam) || (!this.responseAddressParam.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.responseAddressParam);
      }
      if ((this.hasResponseCheckboxesParam) || (!this.responseCheckboxesParam.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.responseCheckboxesParam);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.descriptionHtml);
      }
      if ((this.checkbox != null) && (this.checkbox.length > 0)) {
        for (int m = 0; m < this.checkbox.length; m++)
        {
          ChallengeProto.FormCheckbox localFormCheckbox = this.checkbox[m];
          if (localFormCheckbox != null) {
            paramCodedOutputByteBufferNano.writeMessage(5, localFormCheckbox);
          }
        }
      }
      if (this.address != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.address);
      }
      if ((this.errorInputField != null) && (this.errorInputField.length > 0)) {
        for (int k = 0; k < this.errorInputField.length; k++)
        {
          ChallengeProto.InputValidationError localInputValidationError = this.errorInputField[k];
          if (localInputValidationError != null) {
            paramCodedOutputByteBufferNano.writeMessage(7, localInputValidationError);
          }
        }
      }
      if ((this.hasErrorHtml) || (!this.errorHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.errorHtml);
      }
      if ((this.requiredField != null) && (this.requiredField.length > 0)) {
        for (int j = 0; j < this.requiredField.length; j++) {
          paramCodedOutputByteBufferNano.writeInt32(9, this.requiredField[j]);
        }
      }
      if ((this.supportedCountry != null) && (this.supportedCountry.length > 0)) {
        for (int i = 0; i < this.supportedCountry.length; i++)
        {
          ChallengeProto.Country localCountry = this.supportedCountry[i];
          if (localCountry != null) {
            paramCodedOutputByteBufferNano.writeMessage(10, localCountry);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class AgeChallenge
    extends MessageNano
  {
    public ChallengeProto.FormTextField birthdate = null;
    public ChallengeProto.FormRadioSelector carrierSelection = null;
    public ChallengeProto.FormListSelector carrierSelectionExtension = null;
    public String carrierSelectionHelpHtml = "";
    public ChallengeProto.FormCheckbox citizenship = null;
    public String descriptionHtml = "";
    public ChallengeProto.FormTextField fullName = null;
    public ChallengeProto.FormRadioSelector genderSelection = null;
    public boolean hasCarrierSelectionHelpHtml = false;
    public boolean hasDescriptionHtml = false;
    public boolean hasSubmitFooterHtml = false;
    public boolean hasTitle = false;
    public ChallengeProto.FormTextField phoneNumber = null;
    public ChallengeProto.FormButton submitButton = null;
    public String submitFooterHtml = "";
    public String title = "";
    
    public AgeChallenge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.descriptionHtml);
      }
      if (this.fullName != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.fullName);
      }
      if (this.birthdate != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.birthdate);
      }
      if (this.phoneNumber != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.phoneNumber);
      }
      if (this.genderSelection != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.genderSelection);
      }
      if (this.carrierSelection != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.carrierSelection);
      }
      if (this.citizenship != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.citizenship);
      }
      if ((this.hasSubmitFooterHtml) || (!this.submitFooterHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.submitFooterHtml);
      }
      if (this.submitButton != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.submitButton);
      }
      if (this.carrierSelectionExtension != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.carrierSelectionExtension);
      }
      if ((this.hasCarrierSelectionHelpHtml) || (!this.carrierSelectionHelpHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(12, this.carrierSelectionHelpHtml);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.descriptionHtml);
      }
      if (this.fullName != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.fullName);
      }
      if (this.birthdate != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.birthdate);
      }
      if (this.phoneNumber != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.phoneNumber);
      }
      if (this.genderSelection != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.genderSelection);
      }
      if (this.carrierSelection != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.carrierSelection);
      }
      if (this.citizenship != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.citizenship);
      }
      if ((this.hasSubmitFooterHtml) || (!this.submitFooterHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.submitFooterHtml);
      }
      if (this.submitButton != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.submitButton);
      }
      if (this.carrierSelectionExtension != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.carrierSelectionExtension);
      }
      if ((this.hasCarrierSelectionHelpHtml) || (!this.carrierSelectionHelpHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(12, this.carrierSelectionHelpHtml);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class AuthenticationChallenge
    extends MessageNano
  {
    public int authenticationType = 1;
    public String challengeDescriptionTextHtml = "";
    public boolean displayClientAuthMessage = false;
    public String documentTitle = "";
    public String formattedPrice = "";
    public String gaiaDescriptionTextHtml = "";
    public String gaiaFooterTextHtml = "";
    public String gaiaHeaderText = "";
    public ChallengeProto.FormCheckbox gaiaOptOutCheckbox = null;
    public String gaiaOptOutDescriptionTextHtml = "";
    public boolean hasAuthenticationType = false;
    public boolean hasChallengeDescriptionTextHtml = false;
    public boolean hasDisplayClientAuthMessage = false;
    public boolean hasDocumentTitle = false;
    public boolean hasFormattedPrice = false;
    public boolean hasGaiaDescriptionTextHtml = false;
    public boolean hasGaiaFooterTextHtml = false;
    public boolean hasGaiaHeaderText = false;
    public boolean hasGaiaOptOutDescriptionTextHtml = false;
    public boolean hasInstrumentDisplayTitle = false;
    public boolean hasResponseAuthenticationTypeParam = false;
    public boolean hasResponseRetryCountParam = false;
    public String instrumentDisplayTitle = "";
    public String responseAuthenticationTypeParam = "";
    public String responseRetryCountParam = "";
    
    public AuthenticationChallenge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.authenticationType != 1) || (this.hasAuthenticationType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.authenticationType);
      }
      if ((this.hasResponseAuthenticationTypeParam) || (!this.responseAuthenticationTypeParam.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.responseAuthenticationTypeParam);
      }
      if ((this.hasResponseRetryCountParam) || (!this.responseRetryCountParam.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.responseRetryCountParam);
      }
      if ((this.hasGaiaHeaderText) || (!this.gaiaHeaderText.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.gaiaHeaderText);
      }
      if ((this.hasGaiaDescriptionTextHtml) || (!this.gaiaDescriptionTextHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.gaiaDescriptionTextHtml);
      }
      if ((this.hasGaiaFooterTextHtml) || (!this.gaiaFooterTextHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.gaiaFooterTextHtml);
      }
      if (this.gaiaOptOutCheckbox != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.gaiaOptOutCheckbox);
      }
      if ((this.hasGaiaOptOutDescriptionTextHtml) || (!this.gaiaOptOutDescriptionTextHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.gaiaOptOutDescriptionTextHtml);
      }
      if ((this.hasDocumentTitle) || (!this.documentTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(11, this.documentTitle);
      }
      if ((this.hasFormattedPrice) || (!this.formattedPrice.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(12, this.formattedPrice);
      }
      if ((this.hasInstrumentDisplayTitle) || (!this.instrumentDisplayTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(13, this.instrumentDisplayTitle);
      }
      if ((this.hasChallengeDescriptionTextHtml) || (!this.challengeDescriptionTextHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(14, this.challengeDescriptionTextHtml);
      }
      if ((this.hasDisplayClientAuthMessage) || (this.displayClientAuthMessage)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(15);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.authenticationType != 1) || (this.hasAuthenticationType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.authenticationType);
      }
      if ((this.hasResponseAuthenticationTypeParam) || (!this.responseAuthenticationTypeParam.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.responseAuthenticationTypeParam);
      }
      if ((this.hasResponseRetryCountParam) || (!this.responseRetryCountParam.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.responseRetryCountParam);
      }
      if ((this.hasGaiaHeaderText) || (!this.gaiaHeaderText.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.gaiaHeaderText);
      }
      if ((this.hasGaiaDescriptionTextHtml) || (!this.gaiaDescriptionTextHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.gaiaDescriptionTextHtml);
      }
      if ((this.hasGaiaFooterTextHtml) || (!this.gaiaFooterTextHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.gaiaFooterTextHtml);
      }
      if (this.gaiaOptOutCheckbox != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.gaiaOptOutCheckbox);
      }
      if ((this.hasGaiaOptOutDescriptionTextHtml) || (!this.gaiaOptOutDescriptionTextHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(10, this.gaiaOptOutDescriptionTextHtml);
      }
      if ((this.hasDocumentTitle) || (!this.documentTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(11, this.documentTitle);
      }
      if ((this.hasFormattedPrice) || (!this.formattedPrice.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(12, this.formattedPrice);
      }
      if ((this.hasInstrumentDisplayTitle) || (!this.instrumentDisplayTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(13, this.instrumentDisplayTitle);
      }
      if ((this.hasChallengeDescriptionTextHtml) || (!this.challengeDescriptionTextHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(14, this.challengeDescriptionTextHtml);
      }
      if ((this.hasDisplayClientAuthMessage) || (this.displayClientAuthMessage)) {
        paramCodedOutputByteBufferNano.writeBool(15, this.displayClientAuthMessage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Challenge
    extends MessageNano
  {
    private static volatile Challenge[] _emptyArray;
    public ChallengeProto.AcknowledgementChallenge acknowledgementChallenge = null;
    public ChallengeProto.AddressChallenge addressChallenge = null;
    public ChallengeProto.AgeChallenge ageChallenge = null;
    public ChallengeProto.AuthenticationChallenge authenticationChallenge = null;
    public ChallengeProto.CvnChallenge cvnChallenge = null;
    public ChallengeProto.ChallengeError error = null;
    public ChallengeProto.FamilyWalletAuthChallenge familyWalletAuthChallenge = null;
    public ChallengeProto.PaymentsUpdateChallenge paymentsUpdateChallenge = null;
    public ChallengeProto.PurchaseManagerChallenge purchaseManagerChallenge = null;
    public ChallengeProto.RapChallenge rapChallenge = null;
    public ChallengeProto.SmsCodeChallenge smsCodeChallenge = null;
    public ChallengeProto.ChallengeSuccess success = null;
    public ChallengeProto.WebViewChallenge webViewChallenge = null;
    
    public Challenge()
    {
      this.cachedSize = -1;
    }
    
    public static Challenge[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Challenge[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.addressChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.addressChallenge);
      }
      if (this.authenticationChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.authenticationChallenge);
      }
      if (this.webViewChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.webViewChallenge);
      }
      if (this.ageChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.ageChallenge);
      }
      if (this.smsCodeChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.smsCodeChallenge);
      }
      if (this.error != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.error);
      }
      if (this.cvnChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.cvnChallenge);
      }
      if (this.paymentsUpdateChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.paymentsUpdateChallenge);
      }
      if (this.rapChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.rapChallenge);
      }
      if (this.success != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.success);
      }
      if (this.acknowledgementChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.acknowledgementChallenge);
      }
      if (this.purchaseManagerChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(12, this.purchaseManagerChallenge);
      }
      if (this.familyWalletAuthChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(13, this.familyWalletAuthChallenge);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.addressChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.addressChallenge);
      }
      if (this.authenticationChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.authenticationChallenge);
      }
      if (this.webViewChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.webViewChallenge);
      }
      if (this.ageChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.ageChallenge);
      }
      if (this.smsCodeChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.smsCodeChallenge);
      }
      if (this.error != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.error);
      }
      if (this.cvnChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.cvnChallenge);
      }
      if (this.paymentsUpdateChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.paymentsUpdateChallenge);
      }
      if (this.rapChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.rapChallenge);
      }
      if (this.success != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.success);
      }
      if (this.acknowledgementChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.acknowledgementChallenge);
      }
      if (this.purchaseManagerChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(12, this.purchaseManagerChallenge);
      }
      if (this.familyWalletAuthChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(13, this.familyWalletAuthChallenge);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ChallengeError
    extends MessageNano
  {
    public ChallengeProto.FormButton cancelButton = null;
    public String descriptionHtml = "";
    public boolean hasDescriptionHtml = false;
    public boolean hasTitle = false;
    public ChallengeProto.FormButton submitButton = null;
    public String title = "";
    
    public ChallengeError()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.descriptionHtml);
      }
      if (this.submitButton != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.submitButton);
      }
      if (this.cancelButton != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.cancelButton);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.descriptionHtml);
      }
      if (this.submitButton != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.submitButton);
      }
      if (this.cancelButton != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.cancelButton);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ChallengeSuccess
    extends MessageNano
  {
    public String description = "";
    public boolean hasDescription = false;
    public boolean hasTitle = false;
    public String title = "";
    
    public ChallengeSuccess()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasDescription) || (!this.description.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.description);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasDescription) || (!this.description.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.description);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Country
    extends MessageNano
  {
    private static volatile Country[] _emptyArray;
    public String displayName = "";
    public boolean hasDisplayName = false;
    public boolean hasRegionCode = false;
    public String regionCode = "";
    
    public Country()
    {
      this.cachedSize = -1;
    }
    
    public static Country[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Country[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasRegionCode) || (!this.regionCode.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.regionCode);
      }
      if ((this.hasDisplayName) || (!this.displayName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.displayName);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasRegionCode) || (!this.regionCode.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.regionCode);
      }
      if ((this.hasDisplayName) || (!this.displayName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.displayName);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CvnChallenge
    extends MessageNano
  {
    public int creditCardType = 0;
    public String descriptionHtml = "";
    public String escrowHandleParam = "";
    public boolean hasCreditCardType = false;
    public boolean hasDescriptionHtml = false;
    public boolean hasEscrowHandleParam = false;
    public boolean hasTitle = false;
    public String title = "";
    
    public CvnChallenge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.descriptionHtml);
      }
      if ((this.creditCardType != 0) || (this.hasCreditCardType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.creditCardType);
      }
      if ((this.hasEscrowHandleParam) || (!this.escrowHandleParam.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.escrowHandleParam);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.descriptionHtml);
      }
      if ((this.creditCardType != 0) || (this.hasCreditCardType)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.creditCardType);
      }
      if ((this.hasEscrowHandleParam) || (!this.escrowHandleParam.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.escrowHandleParam);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FamilyWalletAuthChallenge
    extends MessageNano
  {
    public Approver approver = null;
    public String challengeDescriptionContent = "";
    public String challengeDescriptionTitle = "";
    public Badge documentContentRating = null;
    public Common.Image documentThumbnail = null;
    public String documentTitle = "";
    public String formattedPrice = "";
    public boolean hasChallengeDescriptionContent = false;
    public boolean hasChallengeDescriptionTitle = false;
    public boolean hasDocumentTitle = false;
    public boolean hasFormattedPrice = false;
    public boolean hasHelpTextHtml = false;
    public boolean hasInstrumentDisplayTitle = false;
    public String helpTextHtml = "";
    public String instrumentDisplayTitle = "";
    public Common.Image instrumentIcon = null;
    
    public FamilyWalletAuthChallenge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasChallengeDescriptionTitle) || (!this.challengeDescriptionTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.challengeDescriptionTitle);
      }
      if ((this.hasChallengeDescriptionContent) || (!this.challengeDescriptionContent.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.challengeDescriptionContent);
      }
      if (this.approver != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.approver);
      }
      if ((this.hasDocumentTitle) || (!this.documentTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.documentTitle);
      }
      if (this.documentThumbnail != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.documentThumbnail);
      }
      if (this.documentContentRating != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.documentContentRating);
      }
      if ((this.hasFormattedPrice) || (!this.formattedPrice.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.formattedPrice);
      }
      if ((this.hasInstrumentDisplayTitle) || (!this.instrumentDisplayTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.instrumentDisplayTitle);
      }
      if (this.instrumentIcon != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.instrumentIcon);
      }
      if ((this.hasHelpTextHtml) || (!this.helpTextHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.helpTextHtml);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasChallengeDescriptionTitle) || (!this.challengeDescriptionTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.challengeDescriptionTitle);
      }
      if ((this.hasChallengeDescriptionContent) || (!this.challengeDescriptionContent.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.challengeDescriptionContent);
      }
      if (this.approver != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.approver);
      }
      if ((this.hasDocumentTitle) || (!this.documentTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.documentTitle);
      }
      if (this.documentThumbnail != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.documentThumbnail);
      }
      if (this.documentContentRating != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.documentContentRating);
      }
      if ((this.hasFormattedPrice) || (!this.formattedPrice.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.formattedPrice);
      }
      if ((this.hasInstrumentDisplayTitle) || (!this.instrumentDisplayTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.instrumentDisplayTitle);
      }
      if (this.instrumentIcon != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.instrumentIcon);
      }
      if ((this.hasHelpTextHtml) || (!this.helpTextHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(10, this.helpTextHtml);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class Approver
      extends MessageNano
    {
      public String emailAddress = "";
      public boolean hasEmailAddress = false;
      public boolean hasObfuscatedGaiaId = false;
      public String obfuscatedGaiaId = "";
      
      public Approver()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasObfuscatedGaiaId) || (!this.obfuscatedGaiaId.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(1, this.obfuscatedGaiaId);
        }
        if ((this.hasEmailAddress) || (!this.emailAddress.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(2, this.emailAddress);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasObfuscatedGaiaId) || (!this.obfuscatedGaiaId.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(1, this.obfuscatedGaiaId);
        }
        if ((this.hasEmailAddress) || (!this.emailAddress.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(2, this.emailAddress);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class FormButton
    extends MessageNano
  {
    public ChallengeProto.Challenge[] actionChallenge = ChallengeProto.Challenge.emptyArray();
    public boolean actionFailChallenge = false;
    public String actionUrl = "";
    public boolean hasActionFailChallenge = false;
    public boolean hasActionUrl = false;
    public boolean hasLabel = false;
    public String label = "";
    
    public FormButton()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLabel) || (!this.label.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.label);
      }
      if ((this.hasActionUrl) || (!this.actionUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.actionUrl);
      }
      if ((this.hasActionFailChallenge) || (this.actionFailChallenge)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.actionChallenge != null) && (this.actionChallenge.length > 0)) {
        for (int j = 0; j < this.actionChallenge.length; j++)
        {
          ChallengeProto.Challenge localChallenge = this.actionChallenge[j];
          if (localChallenge != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localChallenge);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLabel) || (!this.label.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.label);
      }
      if ((this.hasActionUrl) || (!this.actionUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.actionUrl);
      }
      if ((this.hasActionFailChallenge) || (this.actionFailChallenge)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.actionFailChallenge);
      }
      if ((this.actionChallenge != null) && (this.actionChallenge.length > 0)) {
        for (int i = 0; i < this.actionChallenge.length; i++)
        {
          ChallengeProto.Challenge localChallenge = this.actionChallenge[i];
          if (localChallenge != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localChallenge);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FormCheckbox
    extends MessageNano
  {
    private static volatile FormCheckbox[] _emptyArray;
    public boolean checked = false;
    public String description = "";
    public boolean hasChecked = false;
    public boolean hasDescription = false;
    public boolean hasId = false;
    public boolean hasPostParam = false;
    public boolean hasRequired = false;
    public String id = "";
    public String postParam = "";
    public boolean required = false;
    
    public FormCheckbox()
    {
      this.cachedSize = -1;
    }
    
    public static FormCheckbox[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new FormCheckbox[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasDescription) || (!this.description.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.description);
      }
      if ((this.hasChecked) || (this.checked)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if ((this.hasRequired) || (this.required)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.hasId) || (!this.id.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.id);
      }
      if ((this.hasPostParam) || (!this.postParam.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.postParam);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasDescription) || (!this.description.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.description);
      }
      if ((this.hasChecked) || (this.checked)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.checked);
      }
      if ((this.hasRequired) || (this.required)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.required);
      }
      if ((this.hasId) || (!this.id.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.id);
      }
      if ((this.hasPostParam) || (!this.postParam.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.postParam);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FormListItem
    extends MessageNano
  {
    private static volatile FormListItem[] _emptyArray;
    public boolean hasLabel = false;
    public boolean hasValue = false;
    public String label = "";
    public String value = "";
    
    public FormListItem()
    {
      this.cachedSize = -1;
    }
    
    public static FormListItem[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new FormListItem[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLabel) || (!this.label.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.label);
      }
      if ((this.hasValue) || (!this.value.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.value);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLabel) || (!this.label.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.label);
      }
      if ((this.hasValue) || (!this.value.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.value);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FormListSelector
    extends MessageNano
  {
    public boolean hasLabel = false;
    public ChallengeProto.FormListItem[] item = ChallengeProto.FormListItem.emptyArray();
    public String label = "";
    
    public FormListSelector()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLabel) || (!this.label.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.label);
      }
      if ((this.item != null) && (this.item.length > 0)) {
        for (int j = 0; j < this.item.length; j++)
        {
          ChallengeProto.FormListItem localFormListItem = this.item[j];
          if (localFormListItem != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localFormListItem);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLabel) || (!this.label.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.label);
      }
      if ((this.item != null) && (this.item.length > 0)) {
        for (int i = 0; i < this.item.length; i++)
        {
          ChallengeProto.FormListItem localFormListItem = this.item[i];
          if (localFormListItem != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localFormListItem);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FormRadioButton
    extends MessageNano
  {
    private static volatile FormRadioButton[] _emptyArray;
    public boolean hasLabel = false;
    public boolean hasSelected = false;
    public boolean hasValue = false;
    public String label = "";
    public boolean selected = false;
    public String value = "";
    
    public FormRadioButton()
    {
      this.cachedSize = -1;
    }
    
    public static FormRadioButton[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new FormRadioButton[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLabel) || (!this.label.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.label);
      }
      if ((this.hasValue) || (!this.value.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.value);
      }
      if ((this.hasSelected) || (this.selected)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLabel) || (!this.label.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.label);
      }
      if ((this.hasValue) || (!this.value.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.value);
      }
      if ((this.hasSelected) || (this.selected)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.selected);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FormRadioSelector
    extends MessageNano
  {
    public boolean hasPostParam = false;
    public String postParam = "";
    public ChallengeProto.FormRadioButton[] radioButton = ChallengeProto.FormRadioButton.emptyArray();
    
    public FormRadioSelector()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.radioButton != null) && (this.radioButton.length > 0)) {
        for (int j = 0; j < this.radioButton.length; j++)
        {
          ChallengeProto.FormRadioButton localFormRadioButton = this.radioButton[j];
          if (localFormRadioButton != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localFormRadioButton);
          }
        }
      }
      if ((this.hasPostParam) || (!this.postParam.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.postParam);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.radioButton != null) && (this.radioButton.length > 0)) {
        for (int i = 0; i < this.radioButton.length; i++)
        {
          ChallengeProto.FormRadioButton localFormRadioButton = this.radioButton[i];
          if (localFormRadioButton != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localFormRadioButton);
          }
        }
      }
      if ((this.hasPostParam) || (!this.postParam.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.postParam);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FormTextField
    extends MessageNano
  {
    public String defaultValue = "";
    public String error = "";
    public boolean hasDefaultValue = false;
    public boolean hasError = false;
    public boolean hasHint = false;
    public boolean hasLabel = false;
    public boolean hasPostParam = false;
    public String hint = "";
    public String label = "";
    public String postParam = "";
    
    public FormTextField()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLabel) || (!this.label.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.label);
      }
      if ((this.hasDefaultValue) || (!this.defaultValue.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.defaultValue);
      }
      if ((this.hasHint) || (!this.hint.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.hint);
      }
      if ((this.hasError) || (!this.error.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.error);
      }
      if ((this.hasPostParam) || (!this.postParam.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.postParam);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLabel) || (!this.label.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.label);
      }
      if ((this.hasDefaultValue) || (!this.defaultValue.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.defaultValue);
      }
      if ((this.hasHint) || (!this.hint.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.hint);
      }
      if ((this.hasError) || (!this.error.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.error);
      }
      if ((this.hasPostParam) || (!this.postParam.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.postParam);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class InputValidationError
    extends MessageNano
  {
    private static volatile InputValidationError[] _emptyArray;
    public String errorMessage = "";
    public boolean hasErrorMessage = false;
    public boolean hasInputField = false;
    public int inputField = 0;
    
    public InputValidationError()
    {
      this.cachedSize = -1;
    }
    
    public static InputValidationError[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new InputValidationError[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.inputField != 0) || (this.hasInputField)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.inputField);
      }
      if ((this.hasErrorMessage) || (!this.errorMessage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.errorMessage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.inputField != 0) || (this.hasInputField)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.inputField);
      }
      if ((this.hasErrorMessage) || (!this.errorMessage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.errorMessage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PaymentsUpdateChallenge
    extends MessageNano
  {
    public boolean hasUseClientCart = false;
    public SingleFopPaymentsIntegratorContext paymentsIntegratorContext = null;
    public boolean useClientCart = false;
    
    public PaymentsUpdateChallenge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.paymentsIntegratorContext != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.paymentsIntegratorContext);
      }
      if ((this.hasUseClientCart) || (this.useClientCart)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.paymentsIntegratorContext != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.paymentsIntegratorContext);
      }
      if ((this.hasUseClientCart) || (this.useClientCart)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.useClientCart);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PurchaseManagerChallenge
    extends MessageNano
  {
    public ChallengeProto.PurchaseManagerPayload buyerActionPayload = null;
    
    public PurchaseManagerChallenge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.buyerActionPayload != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.buyerActionPayload);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.buyerActionPayload != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.buyerActionPayload);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PurchaseManagerPayload
    extends MessageNano
  {
    public boolean hasOpaqueToken = false;
    public byte[] opaqueToken = WireFormatNano.EMPTY_BYTES;
    public SecureData[] secureData = SecureData.emptyArray();
    
    public PurchaseManagerPayload()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasOpaqueToken) || (!Arrays.equals(this.opaqueToken, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(1, this.opaqueToken);
      }
      if ((this.secureData != null) && (this.secureData.length > 0)) {
        for (int j = 0; j < this.secureData.length; j++)
        {
          SecureData localSecureData = this.secureData[j];
          if (localSecureData != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localSecureData);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasOpaqueToken) || (!Arrays.equals(this.opaqueToken, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(1, this.opaqueToken);
      }
      if ((this.secureData != null) && (this.secureData.length > 0)) {
        for (int i = 0; i < this.secureData.length; i++)
        {
          SecureData localSecureData = this.secureData[i];
          if (localSecureData != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localSecureData);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class SecureData
      extends MessageNano
    {
      private static volatile SecureData[] _emptyArray;
      public boolean hasKey = false;
      public boolean hasValue = false;
      public int key = 0;
      public String value = "";
      
      public SecureData()
      {
        this.cachedSize = -1;
      }
      
      public static SecureData[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new SecureData[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasKey) || (this.key != 0)) {
          i += CodedOutputByteBufferNano.computeInt32Size(1, this.key);
        }
        if ((this.hasValue) || (!this.value.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(2, this.value);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasKey) || (this.key != 0)) {
          paramCodedOutputByteBufferNano.writeInt32(1, this.key);
        }
        if ((this.hasValue) || (!this.value.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(2, this.value);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class RapChallenge
    extends MessageNano
  {
    public String description = "";
    public boolean hasDescription = false;
    public boolean hasTitle = false;
    public ChallengeProto.RapRefundParams refundParams = null;
    public ChallengeProto.FormButton submitButton = null;
    public String title = "";
    
    public RapChallenge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasDescription) || (!this.description.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.description);
      }
      if (this.submitButton != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.submitButton);
      }
      if (this.refundParams != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.refundParams);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasDescription) || (!this.description.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.description);
      }
      if (this.submitButton != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.submitButton);
      }
      if (this.refundParams != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.refundParams);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RapRefundParams
    extends MessageNano
  {
    public String docId = "";
    public boolean hasDocId = false;
    public boolean hasOrderId = false;
    public String orderId = "";
    public ChallengeProto.FormTextField requestReason = null;
    
    public RapRefundParams()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasDocId) || (!this.docId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.docId);
      }
      if ((this.hasOrderId) || (!this.orderId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.orderId);
      }
      if (this.requestReason != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.requestReason);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasDocId) || (!this.docId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.docId);
      }
      if ((this.hasOrderId) || (!this.orderId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.orderId);
      }
      if (this.requestReason != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.requestReason);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SmsCodeChallenge
    extends MessageNano
  {
    public String descriptionHtml = "";
    public boolean hasDescriptionHtml = false;
    public boolean hasTitle = false;
    public ChallengeProto.FormButton resendCodeButton = null;
    public ChallengeProto.FormTextField smsCode = null;
    public ChallengeProto.FormButton submitButton = null;
    public String title = "";
    
    public SmsCodeChallenge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.descriptionHtml);
      }
      if (this.smsCode != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.smsCode);
      }
      if (this.resendCodeButton != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.resendCodeButton);
      }
      if (this.submitButton != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.submitButton);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.descriptionHtml);
      }
      if (this.smsCode != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.smsCode);
      }
      if (this.resendCodeButton != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.resendCodeButton);
      }
      if (this.submitButton != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.submitButton);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class WebViewChallenge
    extends MessageNano
  {
    public String cancelButtonDisplayLabel = "";
    public String cancelUrlRegexp = "";
    public boolean hasCancelButtonDisplayLabel = false;
    public boolean hasCancelUrlRegexp = false;
    public boolean hasResponseTargetUrlParam = false;
    public boolean hasStartUrl = false;
    public boolean hasTargetUrlRegexp = false;
    public boolean hasTitle = false;
    public String responseTargetUrlParam = "";
    public String startUrl = "";
    public String targetUrlRegexp = "";
    public String title = "";
    
    public WebViewChallenge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasStartUrl) || (!this.startUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.startUrl);
      }
      if ((this.hasTargetUrlRegexp) || (!this.targetUrlRegexp.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.targetUrlRegexp);
      }
      if ((this.hasCancelButtonDisplayLabel) || (!this.cancelButtonDisplayLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.cancelButtonDisplayLabel);
      }
      if ((this.hasResponseTargetUrlParam) || (!this.responseTargetUrlParam.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.responseTargetUrlParam);
      }
      if ((this.hasCancelUrlRegexp) || (!this.cancelUrlRegexp.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.cancelUrlRegexp);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.title);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasStartUrl) || (!this.startUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.startUrl);
      }
      if ((this.hasTargetUrlRegexp) || (!this.targetUrlRegexp.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.targetUrlRegexp);
      }
      if ((this.hasCancelButtonDisplayLabel) || (!this.cancelButtonDisplayLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.cancelButtonDisplayLabel);
      }
      if ((this.hasResponseTargetUrlParam) || (!this.responseTargetUrlParam.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.responseTargetUrlParam);
      }
      if ((this.hasCancelUrlRegexp) || (!this.cancelUrlRegexp.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.cancelUrlRegexp);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.title);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ChallengeProto
 * JD-Core Version:    0.7.0.1
 */