package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface CarrierBilling
{
  public static final class InitiateAssociationResponse
    extends MessageNano
  {
    public boolean hasUserToken = false;
    public String userToken = "";
    
    public InitiateAssociationResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasUserToken) || (!this.userToken.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.userToken);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasUserToken) || (!this.userToken.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.userToken);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class VerifyAssociationResponse
    extends MessageNano
  {
    public Address billingAddress = null;
    public String carrierErrorMessage = "";
    public CarrierTos carrierTos = null;
    public boolean hasCarrierErrorMessage = false;
    public boolean hasStatus = false;
    public int status = 1;
    
    public VerifyAssociationResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.status != 1) || (this.hasStatus)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.status);
      }
      if (this.billingAddress != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.billingAddress);
      }
      if (this.carrierTos != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.carrierTos);
      }
      if ((this.hasCarrierErrorMessage) || (!this.carrierErrorMessage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.carrierErrorMessage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.status != 1) || (this.hasStatus)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.status);
      }
      if (this.billingAddress != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.billingAddress);
      }
      if (this.carrierTos != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.carrierTos);
      }
      if ((this.hasCarrierErrorMessage) || (!this.carrierErrorMessage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.carrierErrorMessage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.CarrierBilling
 * JD-Core Version:    0.7.0.1
 */