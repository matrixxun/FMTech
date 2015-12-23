package com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types;

import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface Dcb
{
  public static final class DcbVerifyAssociationForm
    extends MessageNano
  {
    public LegalMessageOuterClass.LegalMessage requiredMessage = null;
    public String smsMessage = "";
    public String smsPhoneNumber = "";
    
    public DcbVerifyAssociationForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.smsPhoneNumber.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.smsPhoneNumber);
      }
      if (!this.smsMessage.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.smsMessage);
      }
      if (this.requiredMessage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.requiredMessage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.smsPhoneNumber.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.smsPhoneNumber);
      }
      if (!this.smsMessage.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.smsMessage);
      }
      if (this.requiredMessage != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.requiredMessage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DcbVerifyAssociationFormValue
    extends MessageNano
  {
    public String legalDocData = "";
    
    public DcbVerifyAssociationFormValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.legalDocData.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.legalDocData);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.legalDocData.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.legalDocData);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.Dcb
 * JD-Core Version:    0.7.0.1
 */