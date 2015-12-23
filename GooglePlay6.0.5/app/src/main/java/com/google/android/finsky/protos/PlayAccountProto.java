package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface PlayAccountProto
{
  public static final class BrokerRequiredDocuments
    extends MessageNano
  {
    private static volatile BrokerRequiredDocuments[] _emptyArray;
    public long brokerGaiaId = 0L;
    public boolean hasBrokerGaiaId = false;
    public boolean hasPromptHtmlFormat = false;
    public PlayAccountProto.CachedPaymentsLegalDocument[] legalDocument = PlayAccountProto.CachedPaymentsLegalDocument.emptyArray();
    public long[] legalDocumentId = WireFormatNano.EMPTY_LONG_ARRAY;
    public String promptHtmlFormat = "";
    
    public BrokerRequiredDocuments()
    {
      this.cachedSize = -1;
    }
    
    public static BrokerRequiredDocuments[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new BrokerRequiredDocuments[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasBrokerGaiaId) || (this.brokerGaiaId != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.brokerGaiaId);
      }
      if ((this.legalDocumentId != null) && (this.legalDocumentId.length > 0))
      {
        int k = 0;
        for (int m = 0; m < this.legalDocumentId.length; m++) {
          k += CodedOutputByteBufferNano.computeRawVarint64Size(this.legalDocumentId[m]);
        }
        i = i + k + 1 * this.legalDocumentId.length;
      }
      if ((this.hasPromptHtmlFormat) || (!this.promptHtmlFormat.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.promptHtmlFormat);
      }
      if ((this.legalDocument != null) && (this.legalDocument.length > 0)) {
        for (int j = 0; j < this.legalDocument.length; j++)
        {
          PlayAccountProto.CachedPaymentsLegalDocument localCachedPaymentsLegalDocument = this.legalDocument[j];
          if (localCachedPaymentsLegalDocument != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localCachedPaymentsLegalDocument);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasBrokerGaiaId) || (this.brokerGaiaId != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.brokerGaiaId);
      }
      if ((this.legalDocumentId != null) && (this.legalDocumentId.length > 0)) {
        for (int j = 0; j < this.legalDocumentId.length; j++) {
          paramCodedOutputByteBufferNano.writeInt64(2, this.legalDocumentId[j]);
        }
      }
      if ((this.hasPromptHtmlFormat) || (!this.promptHtmlFormat.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.promptHtmlFormat);
      }
      if ((this.legalDocument != null) && (this.legalDocument.length > 0)) {
        for (int i = 0; i < this.legalDocument.length; i++)
        {
          PlayAccountProto.CachedPaymentsLegalDocument localCachedPaymentsLegalDocument = this.legalDocument[i];
          if (localCachedPaymentsLegalDocument != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localCachedPaymentsLegalDocument);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CachedAcceptedLegalDocuments
    extends MessageNano
  {
    private static volatile CachedAcceptedLegalDocuments[] _emptyArray;
    public long brokerGaiaId = 0L;
    public boolean hasBrokerGaiaId = false;
    public PlayAccountProto.CachedPaymentsLegalDocument[] paymentsLegalDocument = PlayAccountProto.CachedPaymentsLegalDocument.emptyArray();
    
    public CachedAcceptedLegalDocuments()
    {
      this.cachedSize = -1;
    }
    
    public static CachedAcceptedLegalDocuments[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new CachedAcceptedLegalDocuments[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasBrokerGaiaId) || (this.brokerGaiaId != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.brokerGaiaId);
      }
      if ((this.paymentsLegalDocument != null) && (this.paymentsLegalDocument.length > 0)) {
        for (int j = 0; j < this.paymentsLegalDocument.length; j++)
        {
          PlayAccountProto.CachedPaymentsLegalDocument localCachedPaymentsLegalDocument = this.paymentsLegalDocument[j];
          if (localCachedPaymentsLegalDocument != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localCachedPaymentsLegalDocument);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasBrokerGaiaId) || (this.brokerGaiaId != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.brokerGaiaId);
      }
      if ((this.paymentsLegalDocument != null) && (this.paymentsLegalDocument.length > 0)) {
        for (int i = 0; i < this.paymentsLegalDocument.length; i++)
        {
          PlayAccountProto.CachedPaymentsLegalDocument localCachedPaymentsLegalDocument = this.paymentsLegalDocument[i];
          if (localCachedPaymentsLegalDocument != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localCachedPaymentsLegalDocument);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CachedPaymentsLegalDocument
    extends MessageNano
  {
    private static volatile CachedPaymentsLegalDocument[] _emptyArray;
    public long endDateMillis = 0L;
    public String externalLegalDocumentId = "";
    public long gracePeriodMillis = 0L;
    public boolean hasEndDateMillis = false;
    public boolean hasExternalLegalDocumentId = false;
    public boolean hasGracePeriodMillis = false;
    public boolean hasLegalDocumentId = false;
    public boolean hasLegalDocumentSubId = false;
    public boolean hasLocale = false;
    public boolean hasMajorVersion = false;
    public boolean hasMinorVersion = false;
    public boolean hasPurchaseCountryCode = false;
    public boolean hasStartDateMillis = false;
    public long legalDocumentId = 0L;
    public String legalDocumentSubId = "";
    public String locale = "";
    public int majorVersion = 0;
    public int minorVersion = 0;
    public String purchaseCountryCode = "";
    public long startDateMillis = 0L;
    
    public CachedPaymentsLegalDocument()
    {
      this.cachedSize = -1;
    }
    
    public static CachedPaymentsLegalDocument[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new CachedPaymentsLegalDocument[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLegalDocumentId) || (this.legalDocumentId != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.legalDocumentId);
      }
      if ((this.hasMajorVersion) || (this.majorVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.majorVersion);
      }
      if ((this.hasMinorVersion) || (this.minorVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.minorVersion);
      }
      if ((this.hasLocale) || (!this.locale.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.locale);
      }
      if ((this.hasPurchaseCountryCode) || (!this.purchaseCountryCode.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.purchaseCountryCode);
      }
      if ((this.hasStartDateMillis) || (this.startDateMillis != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(6, this.startDateMillis);
      }
      if ((this.hasEndDateMillis) || (this.endDateMillis != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(7, this.endDateMillis);
      }
      if ((this.hasGracePeriodMillis) || (this.gracePeriodMillis != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(8, this.gracePeriodMillis);
      }
      if ((this.hasLegalDocumentSubId) || (!this.legalDocumentSubId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.legalDocumentSubId);
      }
      if ((this.hasExternalLegalDocumentId) || (!this.externalLegalDocumentId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.externalLegalDocumentId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLegalDocumentId) || (this.legalDocumentId != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.legalDocumentId);
      }
      if ((this.hasMajorVersion) || (this.majorVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.majorVersion);
      }
      if ((this.hasMinorVersion) || (this.minorVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.minorVersion);
      }
      if ((this.hasLocale) || (!this.locale.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.locale);
      }
      if ((this.hasPurchaseCountryCode) || (!this.purchaseCountryCode.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.purchaseCountryCode);
      }
      if ((this.hasStartDateMillis) || (this.startDateMillis != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(6, this.startDateMillis);
      }
      if ((this.hasEndDateMillis) || (this.endDateMillis != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(7, this.endDateMillis);
      }
      if ((this.hasGracePeriodMillis) || (this.gracePeriodMillis != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(8, this.gracePeriodMillis);
      }
      if ((this.hasLegalDocumentSubId) || (!this.legalDocumentSubId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.legalDocumentSubId);
      }
      if ((this.hasExternalLegalDocumentId) || (!this.externalLegalDocumentId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(10, this.externalLegalDocumentId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CachedPlayAccountInstrument
    extends MessageNano
  {
    public String crossBorderFeeHtml = "";
    public boolean hasCrossBorderFeeHtml = false;
    public boolean hasSuppressAuthentication = false;
    public Instrument instrument = null;
    public String[] purchaseCurrencyCode = WireFormatNano.EMPTY_STRING_ARRAY;
    public boolean suppressAuthentication = false;
    
    public CachedPlayAccountInstrument()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.instrument != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.instrument);
      }
      if ((this.hasCrossBorderFeeHtml) || (!this.crossBorderFeeHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.crossBorderFeeHtml);
      }
      if ((this.purchaseCurrencyCode != null) && (this.purchaseCurrencyCode.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.purchaseCurrencyCode.length; m++)
        {
          String str = this.purchaseCurrencyCode[m];
          if (str != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + k + j * 1;
      }
      if ((this.hasSuppressAuthentication) || (this.suppressAuthentication)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.instrument != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.instrument);
      }
      if ((this.hasCrossBorderFeeHtml) || (!this.crossBorderFeeHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.crossBorderFeeHtml);
      }
      if ((this.purchaseCurrencyCode != null) && (this.purchaseCurrencyCode.length > 0)) {
        for (int i = 0; i < this.purchaseCurrencyCode.length; i++)
        {
          String str = this.purchaseCurrencyCode[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(3, str);
          }
        }
      }
      if ((this.hasSuppressAuthentication) || (this.suppressAuthentication)) {
        paramCodedOutputByteBufferNano.writeBool(4, this.suppressAuthentication);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayAccountGlobalPurchaseCache
    extends MessageNano
  {
    public String ageConfirmationPromptFormat = "";
    public String ageConfirmationWithLegalDocumentPromptFormat = "";
    public int authChallengeSesssionDurationMinutes = 0;
    public PlayAccountProto.BrokerRequiredDocuments[] brokerRequiredDocuments = PlayAccountProto.BrokerRequiredDocuments.emptyArray();
    public boolean hasAgeConfirmationPromptFormat = false;
    public boolean hasAgeConfirmationWithLegalDocumentPromptFormat = false;
    public boolean hasAuthChallengeSesssionDurationMinutes = false;
    public boolean hasMessageSeparator = false;
    public boolean hasTimestamp = false;
    public PlayAccountProto.CachedPaymentsLegalDocument[] latestPaymentsLegalDocument = PlayAccountProto.CachedPaymentsLegalDocument.emptyArray();
    public String messageSeparator = "";
    public long timestamp = 0L;
    
    public PlayAccountGlobalPurchaseCache()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.latestPaymentsLegalDocument != null) && (this.latestPaymentsLegalDocument.length > 0)) {
        for (int k = 0; k < this.latestPaymentsLegalDocument.length; k++)
        {
          PlayAccountProto.CachedPaymentsLegalDocument localCachedPaymentsLegalDocument = this.latestPaymentsLegalDocument[k];
          if (localCachedPaymentsLegalDocument != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localCachedPaymentsLegalDocument);
          }
        }
      }
      if ((this.hasTimestamp) || (this.timestamp != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.timestamp);
      }
      if ((this.brokerRequiredDocuments != null) && (this.brokerRequiredDocuments.length > 0)) {
        for (int j = 0; j < this.brokerRequiredDocuments.length; j++)
        {
          PlayAccountProto.BrokerRequiredDocuments localBrokerRequiredDocuments = this.brokerRequiredDocuments[j];
          if (localBrokerRequiredDocuments != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localBrokerRequiredDocuments);
          }
        }
      }
      if ((this.hasAgeConfirmationPromptFormat) || (!this.ageConfirmationPromptFormat.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.ageConfirmationPromptFormat);
      }
      if ((this.hasAgeConfirmationWithLegalDocumentPromptFormat) || (!this.ageConfirmationWithLegalDocumentPromptFormat.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.ageConfirmationWithLegalDocumentPromptFormat);
      }
      if ((this.hasMessageSeparator) || (!this.messageSeparator.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.messageSeparator);
      }
      if ((this.hasAuthChallengeSesssionDurationMinutes) || (this.authChallengeSesssionDurationMinutes != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(7, this.authChallengeSesssionDurationMinutes);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.latestPaymentsLegalDocument != null) && (this.latestPaymentsLegalDocument.length > 0)) {
        for (int j = 0; j < this.latestPaymentsLegalDocument.length; j++)
        {
          PlayAccountProto.CachedPaymentsLegalDocument localCachedPaymentsLegalDocument = this.latestPaymentsLegalDocument[j];
          if (localCachedPaymentsLegalDocument != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localCachedPaymentsLegalDocument);
          }
        }
      }
      if ((this.hasTimestamp) || (this.timestamp != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.timestamp);
      }
      if ((this.brokerRequiredDocuments != null) && (this.brokerRequiredDocuments.length > 0)) {
        for (int i = 0; i < this.brokerRequiredDocuments.length; i++)
        {
          PlayAccountProto.BrokerRequiredDocuments localBrokerRequiredDocuments = this.brokerRequiredDocuments[i];
          if (localBrokerRequiredDocuments != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localBrokerRequiredDocuments);
          }
        }
      }
      if ((this.hasAgeConfirmationPromptFormat) || (!this.ageConfirmationPromptFormat.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.ageConfirmationPromptFormat);
      }
      if ((this.hasAgeConfirmationWithLegalDocumentPromptFormat) || (!this.ageConfirmationWithLegalDocumentPromptFormat.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.ageConfirmationWithLegalDocumentPromptFormat);
      }
      if ((this.hasMessageSeparator) || (!this.messageSeparator.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.messageSeparator);
      }
      if ((this.hasAuthChallengeSesssionDurationMinutes) || (this.authChallengeSesssionDurationMinutes != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(7, this.authChallengeSesssionDurationMinutes);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayAccountUserPurchaseCache
    extends MessageNano
  {
    public PlayAccountProto.CachedAcceptedLegalDocuments[] acceptedPaymentsLegalDocuments = PlayAccountProto.CachedAcceptedLegalDocuments.emptyArray();
    public int estimatedMaxTaxRateMicros = 0;
    public boolean hasEstimatedMaxTaxRateMicros = false;
    public boolean hasInstantPurchaseEnabled = false;
    public boolean hasInstantPurchasePriceByline = false;
    public boolean hasPlayCountryCode = false;
    public boolean hasPurchaseCountry = false;
    public boolean hasPurchaseFlowEcRefundPolicyNoticeHtml = false;
    public boolean hasPurchaseFlowEcRefundPolicyNoticeWithTosHtml = false;
    public boolean hasServerLogsCookie = false;
    public boolean instantPurchaseEnabled = false;
    public String instantPurchasePriceByline = "";
    public PlayAccountProto.CachedPlayAccountInstrument lastUsedInstrument = null;
    public String playCountryCode = "";
    public PlayAccountProto.CachedPlayAccountInstrument playStoredValueInstrument = null;
    public String purchaseCountry = "";
    public String purchaseFlowEcRefundPolicyNoticeHtml = "";
    public String purchaseFlowEcRefundPolicyNoticeWithTosHtml = "";
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    
    public PlayAccountUserPurchaseCache()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.lastUsedInstrument != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.lastUsedInstrument);
      }
      if (this.playStoredValueInstrument != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.playStoredValueInstrument);
      }
      if ((this.hasPurchaseCountry) || (!this.purchaseCountry.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.purchaseCountry);
      }
      if ((this.hasPurchaseFlowEcRefundPolicyNoticeHtml) || (!this.purchaseFlowEcRefundPolicyNoticeHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.purchaseFlowEcRefundPolicyNoticeHtml);
      }
      if ((this.hasPurchaseFlowEcRefundPolicyNoticeWithTosHtml) || (!this.purchaseFlowEcRefundPolicyNoticeWithTosHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.purchaseFlowEcRefundPolicyNoticeWithTosHtml);
      }
      if ((this.hasPlayCountryCode) || (!this.playCountryCode.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.playCountryCode);
      }
      if ((this.hasInstantPurchaseEnabled) || (this.instantPurchaseEnabled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(9);
      }
      if ((this.hasInstantPurchasePriceByline) || (!this.instantPurchasePriceByline.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.instantPurchasePriceByline);
      }
      if ((this.hasEstimatedMaxTaxRateMicros) || (this.estimatedMaxTaxRateMicros != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(11, this.estimatedMaxTaxRateMicros);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(12, this.serverLogsCookie);
      }
      if ((this.acceptedPaymentsLegalDocuments != null) && (this.acceptedPaymentsLegalDocuments.length > 0)) {
        for (int j = 0; j < this.acceptedPaymentsLegalDocuments.length; j++)
        {
          PlayAccountProto.CachedAcceptedLegalDocuments localCachedAcceptedLegalDocuments = this.acceptedPaymentsLegalDocuments[j];
          if (localCachedAcceptedLegalDocuments != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(13, localCachedAcceptedLegalDocuments);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.lastUsedInstrument != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.lastUsedInstrument);
      }
      if (this.playStoredValueInstrument != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.playStoredValueInstrument);
      }
      if ((this.hasPurchaseCountry) || (!this.purchaseCountry.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.purchaseCountry);
      }
      if ((this.hasPurchaseFlowEcRefundPolicyNoticeHtml) || (!this.purchaseFlowEcRefundPolicyNoticeHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.purchaseFlowEcRefundPolicyNoticeHtml);
      }
      if ((this.hasPurchaseFlowEcRefundPolicyNoticeWithTosHtml) || (!this.purchaseFlowEcRefundPolicyNoticeWithTosHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.purchaseFlowEcRefundPolicyNoticeWithTosHtml);
      }
      if ((this.hasPlayCountryCode) || (!this.playCountryCode.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.playCountryCode);
      }
      if ((this.hasInstantPurchaseEnabled) || (this.instantPurchaseEnabled)) {
        paramCodedOutputByteBufferNano.writeBool(9, this.instantPurchaseEnabled);
      }
      if ((this.hasInstantPurchasePriceByline) || (!this.instantPurchasePriceByline.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(10, this.instantPurchasePriceByline);
      }
      if ((this.hasEstimatedMaxTaxRateMicros) || (this.estimatedMaxTaxRateMicros != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(11, this.estimatedMaxTaxRateMicros);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(12, this.serverLogsCookie);
      }
      if ((this.acceptedPaymentsLegalDocuments != null) && (this.acceptedPaymentsLegalDocuments.length > 0)) {
        for (int i = 0; i < this.acceptedPaymentsLegalDocuments.length; i++)
        {
          PlayAccountProto.CachedAcceptedLegalDocuments localCachedAcceptedLegalDocuments = this.acceptedPaymentsLegalDocuments[i];
          if (localCachedAcceptedLegalDocuments != null) {
            paramCodedOutputByteBufferNano.writeMessage(13, localCachedAcceptedLegalDocuments);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PlayAccountProto
 * JD-Core Version:    0.7.0.1
 */