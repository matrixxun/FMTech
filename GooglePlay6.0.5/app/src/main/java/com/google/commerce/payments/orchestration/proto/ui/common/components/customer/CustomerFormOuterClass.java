package com.google.commerce.payments.orchestration.proto.ui.common.components.customer;

import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.AddressForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.AddressFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.CountrySelector;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.InstrumentFormOuterClass.InstrumentForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.InstrumentFormOuterClass.InstrumentFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageSetOuterClass.LegalMessageSet;
import com.google.commerce.payments.orchestration.proto.ui.common.components.tax.TaxInfoFormOuterClass.TaxInfoForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.tax.TaxInfoFormOuterClass.TaxInfoFormValue;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface CustomerFormOuterClass
{
  public static final class CustomerForm
    extends MessageNano
  {
    public String id = "";
    public int initialTaxInfoForm = -1;
    public InstrumentFormOuterClass.InstrumentForm instrumentForm = null;
    public AddressFormOuterClass.AddressForm legalAddressForm = null;
    public AddressFormOuterClass.CountrySelector legalCountrySelector = null;
    public LegalMessageSetOuterClass.LegalMessageSet legalMessages = null;
    public TaxInfoFormOuterClass.TaxInfoForm[] taxInfoForm = TaxInfoFormOuterClass.TaxInfoForm.emptyArray();
    
    public CustomerForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.legalAddressForm != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.legalAddressForm);
      }
      if (this.instrumentForm != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.instrumentForm);
      }
      if (this.legalMessages != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.legalMessages);
      }
      if (!this.id.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.id);
      }
      if (this.legalCountrySelector != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.legalCountrySelector);
      }
      if ((this.taxInfoForm != null) && (this.taxInfoForm.length > 0)) {
        for (int j = 0; j < this.taxInfoForm.length; j++)
        {
          TaxInfoFormOuterClass.TaxInfoForm localTaxInfoForm = this.taxInfoForm[j];
          if (localTaxInfoForm != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(7, localTaxInfoForm);
          }
        }
      }
      if (this.initialTaxInfoForm != -1) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.initialTaxInfoForm);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.legalAddressForm != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.legalAddressForm);
      }
      if (this.instrumentForm != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.instrumentForm);
      }
      if (this.legalMessages != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.legalMessages);
      }
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(5, this.id);
      }
      if (this.legalCountrySelector != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.legalCountrySelector);
      }
      if ((this.taxInfoForm != null) && (this.taxInfoForm.length > 0)) {
        for (int i = 0; i < this.taxInfoForm.length; i++)
        {
          TaxInfoFormOuterClass.TaxInfoForm localTaxInfoForm = this.taxInfoForm[i];
          if (localTaxInfoForm != null) {
            paramCodedOutputByteBufferNano.writeMessage(7, localTaxInfoForm);
          }
        }
      }
      if (this.initialTaxInfoForm != -1) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.initialTaxInfoForm);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CustomerFormValue
    extends MessageNano
  {
    public InstrumentFormOuterClass.InstrumentFormValue instrument = null;
    public AddressFormOuterClass.AddressFormValue legalAddress = null;
    public String legalCountryCode = "";
    public String legalDocData = "";
    public TaxInfoFormOuterClass.TaxInfoFormValue taxInfo = null;
    
    public CustomerFormValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.instrument != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.instrument);
      }
      if (!this.legalDocData.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.legalDocData);
      }
      if (this.legalAddress != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.legalAddress);
      }
      if (!this.legalCountryCode.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.legalCountryCode);
      }
      if (this.taxInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.taxInfo);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.instrument != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.instrument);
      }
      if (!this.legalDocData.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.legalDocData);
      }
      if (this.legalAddress != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.legalAddress);
      }
      if (!this.legalCountryCode.equals("")) {
        paramCodedOutputByteBufferNano.writeString(5, this.legalCountryCode);
      }
      if (this.taxInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.taxInfo);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.customer.CustomerFormOuterClass
 * JD-Core Version:    0.7.0.1
 */