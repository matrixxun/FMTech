package com.google.commerce.payments.orchestration.proto.ui.common.components;

import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiFieldValue;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface OtpFieldOuterClass
{
  public static final class OtpField
    extends MessageNano
  {
    public ButtonContainerOuterClass.Button button = null;
    public UiFieldOuterClass.UiField otp = null;
    public String otpFromSmsRetrievalRegex = "";
    public String otpSenderNumberRegex = "";
    
    public OtpField()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.otp != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.otp);
      }
      if (this.button != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.button);
      }
      if (!this.otpSenderNumberRegex.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.otpSenderNumberRegex);
      }
      if (!this.otpFromSmsRetrievalRegex.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.otpFromSmsRetrievalRegex);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.otp != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.otp);
      }
      if (this.button != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.button);
      }
      if (!this.otpSenderNumberRegex.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.otpSenderNumberRegex);
      }
      if (!this.otpFromSmsRetrievalRegex.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.otpFromSmsRetrievalRegex);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class OtpFieldValue
    extends MessageNano
  {
    public boolean buttonPressed = false;
    public UiFieldOuterClass.UiFieldValue otp = null;
    
    public OtpFieldValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.otp != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.otp);
      }
      if (this.buttonPressed) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.otp != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.otp);
      }
      if (this.buttonPressed) {
        paramCodedOutputByteBufferNano.writeBool(2, this.buttonPressed);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.OtpFieldOuterClass
 * JD-Core Version:    0.7.0.1
 */