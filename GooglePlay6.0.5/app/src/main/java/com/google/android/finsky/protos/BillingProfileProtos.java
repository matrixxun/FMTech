package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface BillingProfileProtos
{
  public static final class BillingProfile
    extends MessageNano
  {
    public BillingProfileProtos.BillingProfileOption[] billingProfileOption = BillingProfileProtos.BillingProfileOption.emptyArray();
    public boolean hasPaymentsIntegratorCommonToken = false;
    public boolean hasRemindMeLaterLabel = false;
    public boolean hasSelectedExternalInstrumentId = false;
    public Instrument[] instrument = Instrument.emptyArray();
    public byte[] paymentsIntegratorCommonToken = WireFormatNano.EMPTY_BYTES;
    public Common.Image remindMeLaterIconImage = null;
    public String remindMeLaterLabel = "";
    public String selectedExternalInstrumentId = "";
    
    public BillingProfile()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.instrument != null) && (this.instrument.length > 0)) {
        for (int k = 0; k < this.instrument.length; k++)
        {
          Instrument localInstrument = this.instrument[k];
          if (localInstrument != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localInstrument);
          }
        }
      }
      if ((this.hasSelectedExternalInstrumentId) || (!this.selectedExternalInstrumentId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.selectedExternalInstrumentId);
      }
      if ((this.billingProfileOption != null) && (this.billingProfileOption.length > 0)) {
        for (int j = 0; j < this.billingProfileOption.length; j++)
        {
          BillingProfileProtos.BillingProfileOption localBillingProfileOption = this.billingProfileOption[j];
          if (localBillingProfileOption != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localBillingProfileOption);
          }
        }
      }
      if ((this.hasPaymentsIntegratorCommonToken) || (!Arrays.equals(this.paymentsIntegratorCommonToken, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(6, this.paymentsIntegratorCommonToken);
      }
      if (this.remindMeLaterIconImage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.remindMeLaterIconImage);
      }
      if ((this.hasRemindMeLaterLabel) || (!this.remindMeLaterLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.remindMeLaterLabel);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.instrument != null) && (this.instrument.length > 0)) {
        for (int j = 0; j < this.instrument.length; j++)
        {
          Instrument localInstrument = this.instrument[j];
          if (localInstrument != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localInstrument);
          }
        }
      }
      if ((this.hasSelectedExternalInstrumentId) || (!this.selectedExternalInstrumentId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.selectedExternalInstrumentId);
      }
      if ((this.billingProfileOption != null) && (this.billingProfileOption.length > 0)) {
        for (int i = 0; i < this.billingProfileOption.length; i++)
        {
          BillingProfileProtos.BillingProfileOption localBillingProfileOption = this.billingProfileOption[i];
          if (localBillingProfileOption != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localBillingProfileOption);
          }
        }
      }
      if ((this.hasPaymentsIntegratorCommonToken) || (!Arrays.equals(this.paymentsIntegratorCommonToken, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(6, this.paymentsIntegratorCommonToken);
      }
      if (this.remindMeLaterIconImage != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.remindMeLaterIconImage);
      }
      if ((this.hasRemindMeLaterLabel) || (!this.remindMeLaterLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.remindMeLaterLabel);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class BillingProfileOption
    extends MessageNano
  {
    private static volatile BillingProfileOption[] _emptyArray;
    public CarrierBillingInstrumentStatus carrierBillingInstrumentStatus = null;
    public String displayTitle = "";
    public String externalInstrumentId = "";
    public GenericInstrument genericInstrument = null;
    public boolean hasDisplayTitle = false;
    public boolean hasExternalInstrumentId = false;
    public boolean hasPaymentsIntegratorInstrumentToken = false;
    public boolean hasServerLogsCookie = false;
    public boolean hasType = false;
    public boolean hasTypeName = false;
    public Common.Image iconImage = null;
    public byte[] paymentsIntegratorInstrumentToken = WireFormatNano.EMPTY_BYTES;
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public TopupInfo topupInfo = null;
    public int type = 0;
    public String typeName = "";
    
    public BillingProfileOption()
    {
      this.cachedSize = -1;
    }
    
    public static BillingProfileOption[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new BillingProfileOption[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.type != 0) || (this.hasType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
      }
      if ((this.hasDisplayTitle) || (!this.displayTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.displayTitle);
      }
      if ((this.hasExternalInstrumentId) || (!this.externalInstrumentId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.externalInstrumentId);
      }
      if (this.topupInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.topupInfo);
      }
      if (this.carrierBillingInstrumentStatus != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.carrierBillingInstrumentStatus);
      }
      if (this.genericInstrument != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.genericInstrument);
      }
      if ((this.hasPaymentsIntegratorInstrumentToken) || (!Arrays.equals(this.paymentsIntegratorInstrumentToken, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(7, this.paymentsIntegratorInstrumentToken);
      }
      if (this.iconImage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.iconImage);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(9, this.serverLogsCookie);
      }
      if ((this.hasTypeName) || (!this.typeName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.typeName);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.type != 0) || (this.hasType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.type);
      }
      if ((this.hasDisplayTitle) || (!this.displayTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.displayTitle);
      }
      if ((this.hasExternalInstrumentId) || (!this.externalInstrumentId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.externalInstrumentId);
      }
      if (this.topupInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.topupInfo);
      }
      if (this.carrierBillingInstrumentStatus != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.carrierBillingInstrumentStatus);
      }
      if (this.genericInstrument != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.genericInstrument);
      }
      if ((this.hasPaymentsIntegratorInstrumentToken) || (!Arrays.equals(this.paymentsIntegratorInstrumentToken, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(7, this.paymentsIntegratorInstrumentToken);
      }
      if (this.iconImage != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.iconImage);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(9, this.serverLogsCookie);
      }
      if ((this.hasTypeName) || (!this.typeName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(10, this.typeName);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.BillingProfileProtos
 * JD-Core Version:    0.7.0.1
 */