package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public final class Instrument
  extends MessageNano
{
  private static volatile Instrument[] _emptyArray;
  public Address billingAddress = null;
  public BillingAddressSpec billingAddressSpec = null;
  public CarrierBillingInstrument carrierBilling = null;
  public CarrierBillingInstrumentStatus carrierBillingStatus = null;
  public CreditCardInstrument creditCard = null;
  public DisabledInfo[] disabledInfo = DisabledInfo.emptyArray();
  public String displaySubtitle = "";
  public String displayTitle = "";
  public String editButtonLabel = "";
  public boolean eligibleForFamilyFop = false;
  public String externalInstrumentId = "";
  public FamilyInfo familyInfo = null;
  public boolean hasDisplaySubtitle = false;
  public boolean hasDisplayTitle = false;
  public boolean hasEditButtonLabel = false;
  public boolean hasEligibleForFamilyFop = false;
  public boolean hasExternalInstrumentId = false;
  public boolean hasInstrumentFamily = false;
  public boolean hasInstrumentToken = false;
  public boolean hasPaymentsIntegratorEditToken = false;
  public boolean hasServerLogsCookie = false;
  public boolean hasStatusDescription = false;
  public boolean hasVersion = false;
  public Common.Image iconImage = null;
  public int instrumentFamily = 0;
  public byte[] instrumentToken = WireFormatNano.EMPTY_BYTES;
  public byte[] paymentsIntegratorEditToken = WireFormatNano.EMPTY_BYTES;
  public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
  public String statusDescription = "";
  public StoredValueInstrument storedValue = null;
  public TopupInfo topupInfoDeprecated = null;
  public int version = 0;
  
  public Instrument()
  {
    this.cachedSize = -1;
  }
  
  public static Instrument[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new Instrument[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasExternalInstrumentId) || (!this.externalInstrumentId.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.externalInstrumentId);
    }
    if (this.billingAddress != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.billingAddress);
    }
    if (this.creditCard != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.creditCard);
    }
    if (this.carrierBilling != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(4, this.carrierBilling);
    }
    if (this.billingAddressSpec != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(5, this.billingAddressSpec);
    }
    if ((this.instrumentFamily != 0) || (this.hasInstrumentFamily)) {
      i += CodedOutputByteBufferNano.computeInt32Size(6, this.instrumentFamily);
    }
    if (this.carrierBillingStatus != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(7, this.carrierBillingStatus);
    }
    if ((this.hasDisplayTitle) || (!this.displayTitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(8, this.displayTitle);
    }
    if (this.topupInfoDeprecated != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(9, this.topupInfoDeprecated);
    }
    if ((this.hasVersion) || (this.version != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(10, this.version);
    }
    if (this.storedValue != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(11, this.storedValue);
    }
    if ((this.disabledInfo != null) && (this.disabledInfo.length > 0)) {
      for (int j = 0; j < this.disabledInfo.length; j++)
      {
        DisabledInfo localDisabledInfo = this.disabledInfo[j];
        if (localDisabledInfo != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(12, localDisabledInfo);
        }
      }
    }
    if ((this.hasStatusDescription) || (!this.statusDescription.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(13, this.statusDescription);
    }
    if (this.iconImage != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(14, this.iconImage);
    }
    if ((this.hasPaymentsIntegratorEditToken) || (!Arrays.equals(this.paymentsIntegratorEditToken, WireFormatNano.EMPTY_BYTES))) {
      i += CodedOutputByteBufferNano.computeBytesSize(15, this.paymentsIntegratorEditToken);
    }
    if ((this.hasEditButtonLabel) || (!this.editButtonLabel.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(16, this.editButtonLabel);
    }
    if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
      i += CodedOutputByteBufferNano.computeBytesSize(17, this.serverLogsCookie);
    }
    if (this.familyInfo != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(18, this.familyInfo);
    }
    if ((this.hasDisplaySubtitle) || (!this.displaySubtitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(19, this.displaySubtitle);
    }
    if ((this.hasEligibleForFamilyFop) || (this.eligibleForFamilyFop)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(20);
    }
    if ((this.hasInstrumentToken) || (!Arrays.equals(this.instrumentToken, WireFormatNano.EMPTY_BYTES))) {
      i += CodedOutputByteBufferNano.computeBytesSize(21, this.instrumentToken);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasExternalInstrumentId) || (!this.externalInstrumentId.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.externalInstrumentId);
    }
    if (this.billingAddress != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.billingAddress);
    }
    if (this.creditCard != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.creditCard);
    }
    if (this.carrierBilling != null) {
      paramCodedOutputByteBufferNano.writeMessage(4, this.carrierBilling);
    }
    if (this.billingAddressSpec != null) {
      paramCodedOutputByteBufferNano.writeMessage(5, this.billingAddressSpec);
    }
    if ((this.instrumentFamily != 0) || (this.hasInstrumentFamily)) {
      paramCodedOutputByteBufferNano.writeInt32(6, this.instrumentFamily);
    }
    if (this.carrierBillingStatus != null) {
      paramCodedOutputByteBufferNano.writeMessage(7, this.carrierBillingStatus);
    }
    if ((this.hasDisplayTitle) || (!this.displayTitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(8, this.displayTitle);
    }
    if (this.topupInfoDeprecated != null) {
      paramCodedOutputByteBufferNano.writeMessage(9, this.topupInfoDeprecated);
    }
    if ((this.hasVersion) || (this.version != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(10, this.version);
    }
    if (this.storedValue != null) {
      paramCodedOutputByteBufferNano.writeMessage(11, this.storedValue);
    }
    if ((this.disabledInfo != null) && (this.disabledInfo.length > 0)) {
      for (int i = 0; i < this.disabledInfo.length; i++)
      {
        DisabledInfo localDisabledInfo = this.disabledInfo[i];
        if (localDisabledInfo != null) {
          paramCodedOutputByteBufferNano.writeMessage(12, localDisabledInfo);
        }
      }
    }
    if ((this.hasStatusDescription) || (!this.statusDescription.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(13, this.statusDescription);
    }
    if (this.iconImage != null) {
      paramCodedOutputByteBufferNano.writeMessage(14, this.iconImage);
    }
    if ((this.hasPaymentsIntegratorEditToken) || (!Arrays.equals(this.paymentsIntegratorEditToken, WireFormatNano.EMPTY_BYTES))) {
      paramCodedOutputByteBufferNano.writeBytes(15, this.paymentsIntegratorEditToken);
    }
    if ((this.hasEditButtonLabel) || (!this.editButtonLabel.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(16, this.editButtonLabel);
    }
    if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
      paramCodedOutputByteBufferNano.writeBytes(17, this.serverLogsCookie);
    }
    if (this.familyInfo != null) {
      paramCodedOutputByteBufferNano.writeMessage(18, this.familyInfo);
    }
    if ((this.hasDisplaySubtitle) || (!this.displaySubtitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(19, this.displaySubtitle);
    }
    if ((this.hasEligibleForFamilyFop) || (this.eligibleForFamilyFop)) {
      paramCodedOutputByteBufferNano.writeBool(20, this.eligibleForFamilyFop);
    }
    if ((this.hasInstrumentToken) || (!Arrays.equals(this.instrumentToken, WireFormatNano.EMPTY_BYTES))) {
      paramCodedOutputByteBufferNano.writeBytes(21, this.instrumentToken);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
  
  public static final class FamilyInfo
    extends MessageNano
  {
    public boolean askToBuy = false;
    public long familyId = 0L;
    public boolean hasAskToBuy = false;
    public boolean hasFamilyId = false;
    public boolean hasHohEmailAddress = false;
    public boolean hasHohObfuscatedGaiaId = false;
    public String hohEmailAddress = "";
    public String hohObfuscatedGaiaId = "";
    
    public FamilyInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasFamilyId) || (this.familyId != 0L)) {
        i += 8 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if ((this.hasAskToBuy) || (this.askToBuy)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if ((this.hasHohObfuscatedGaiaId) || (!this.hohObfuscatedGaiaId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.hohObfuscatedGaiaId);
      }
      if ((this.hasHohEmailAddress) || (!this.hohEmailAddress.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.hohEmailAddress);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasFamilyId) || (this.familyId != 0L)) {
        paramCodedOutputByteBufferNano.writeFixed64(1, this.familyId);
      }
      if ((this.hasAskToBuy) || (this.askToBuy)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.askToBuy);
      }
      if ((this.hasHohObfuscatedGaiaId) || (!this.hohObfuscatedGaiaId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.hohObfuscatedGaiaId);
      }
      if ((this.hasHohEmailAddress) || (!this.hohEmailAddress.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.hohEmailAddress);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Instrument
 * JD-Core Version:    0.7.0.1
 */