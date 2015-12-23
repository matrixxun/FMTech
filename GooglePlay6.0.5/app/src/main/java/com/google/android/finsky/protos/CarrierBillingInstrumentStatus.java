package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class CarrierBillingInstrumentStatus
  extends MessageNano
{
  public int apiVersion = 0;
  public boolean associationRequired = false;
  public PasswordPrompt carrierPasswordPrompt = null;
  public String carrierSupportPhoneNumber = "";
  public CarrierTos carrierTos = null;
  public DeviceAssociation deviceAssociation = null;
  public boolean hasApiVersion = false;
  public boolean hasAssociationRequired = false;
  public boolean hasCarrierSupportPhoneNumber = false;
  public boolean hasName = false;
  public boolean hasPasswordRequired = false;
  public String name = "";
  public boolean passwordRequired = false;
  
  public CarrierBillingInstrumentStatus()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.carrierTos != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.carrierTos);
    }
    if ((this.hasAssociationRequired) || (this.associationRequired)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
    }
    if ((this.hasPasswordRequired) || (this.passwordRequired)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
    }
    if (this.carrierPasswordPrompt != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(4, this.carrierPasswordPrompt);
    }
    if ((this.hasApiVersion) || (this.apiVersion != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(5, this.apiVersion);
    }
    if ((this.hasName) || (!this.name.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(6, this.name);
    }
    if (this.deviceAssociation != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(7, this.deviceAssociation);
    }
    if ((this.hasCarrierSupportPhoneNumber) || (!this.carrierSupportPhoneNumber.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(8, this.carrierSupportPhoneNumber);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.carrierTos != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.carrierTos);
    }
    if ((this.hasAssociationRequired) || (this.associationRequired)) {
      paramCodedOutputByteBufferNano.writeBool(2, this.associationRequired);
    }
    if ((this.hasPasswordRequired) || (this.passwordRequired)) {
      paramCodedOutputByteBufferNano.writeBool(3, this.passwordRequired);
    }
    if (this.carrierPasswordPrompt != null) {
      paramCodedOutputByteBufferNano.writeMessage(4, this.carrierPasswordPrompt);
    }
    if ((this.hasApiVersion) || (this.apiVersion != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(5, this.apiVersion);
    }
    if ((this.hasName) || (!this.name.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(6, this.name);
    }
    if (this.deviceAssociation != null) {
      paramCodedOutputByteBufferNano.writeMessage(7, this.deviceAssociation);
    }
    if ((this.hasCarrierSupportPhoneNumber) || (!this.carrierSupportPhoneNumber.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(8, this.carrierSupportPhoneNumber);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.CarrierBillingInstrumentStatus
 * JD-Core Version:    0.7.0.1
 */