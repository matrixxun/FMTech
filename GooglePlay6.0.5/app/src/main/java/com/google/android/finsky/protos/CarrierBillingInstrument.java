package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class CarrierBillingInstrument
  extends MessageNano
{
  public CarrierTos acceptedCarrierTos = null;
  public String accountType = "";
  public CarrierBillingCredentials credentials = null;
  public String currencyCode = "";
  public EncryptedSubscriberInfo encryptedSubscriberInfo = null;
  public boolean hasAccountType = false;
  public boolean hasCurrencyCode = false;
  public boolean hasInstrumentKey = false;
  public boolean hasSubscriberIdentifier = false;
  public boolean hasTransactionLimit = false;
  public String instrumentKey = "";
  public String subscriberIdentifier = "";
  public long transactionLimit = 0L;
  
  public CarrierBillingInstrument()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasInstrumentKey) || (!this.instrumentKey.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.instrumentKey);
    }
    if ((this.hasAccountType) || (!this.accountType.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.accountType);
    }
    if ((this.hasCurrencyCode) || (!this.currencyCode.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.currencyCode);
    }
    if ((this.hasTransactionLimit) || (this.transactionLimit != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(4, this.transactionLimit);
    }
    if ((this.hasSubscriberIdentifier) || (!this.subscriberIdentifier.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.subscriberIdentifier);
    }
    if (this.encryptedSubscriberInfo != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(6, this.encryptedSubscriberInfo);
    }
    if (this.credentials != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(7, this.credentials);
    }
    if (this.acceptedCarrierTos != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(8, this.acceptedCarrierTos);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasInstrumentKey) || (!this.instrumentKey.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.instrumentKey);
    }
    if ((this.hasAccountType) || (!this.accountType.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.accountType);
    }
    if ((this.hasCurrencyCode) || (!this.currencyCode.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.currencyCode);
    }
    if ((this.hasTransactionLimit) || (this.transactionLimit != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(4, this.transactionLimit);
    }
    if ((this.hasSubscriberIdentifier) || (!this.subscriberIdentifier.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.subscriberIdentifier);
    }
    if (this.encryptedSubscriberInfo != null) {
      paramCodedOutputByteBufferNano.writeMessage(6, this.encryptedSubscriberInfo);
    }
    if (this.credentials != null) {
      paramCodedOutputByteBufferNano.writeMessage(7, this.credentials);
    }
    if (this.acceptedCarrierTos != null) {
      paramCodedOutputByteBufferNano.writeMessage(8, this.acceptedCarrierTos);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.CarrierBillingInstrument
 * JD-Core Version:    0.7.0.1
 */