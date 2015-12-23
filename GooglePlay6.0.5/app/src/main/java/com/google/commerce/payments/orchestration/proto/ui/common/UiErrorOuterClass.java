package com.google.commerce.payments.orchestration.proto.ui.common;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface UiErrorOuterClass
{
  public static final class FormFieldMessage
    extends MessageNano
  {
    private static volatile FormFieldMessage[] _emptyArray;
    public FormFieldReferenceOuterClass.FormFieldReference formFieldReference = null;
    public String message = "";
    
    public FormFieldMessage()
    {
      this.cachedSize = -1;
    }
    
    public static FormFieldMessage[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new FormFieldMessage[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.message.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.message);
      }
      if (this.formFieldReference != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.formFieldReference);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.message.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.message);
      }
      if (this.formFieldReference != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.formFieldReference);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class UiError
    extends MessageNano
  {
    public int action = 1;
    public String errorCode = "";
    public UiErrorOuterClass.FormFieldMessage[] formFieldMessage = UiErrorOuterClass.FormFieldMessage.emptyArray();
    public String internalDetails = "";
    public String message = "";
    
    public UiError()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.message.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.message);
      }
      if ((this.formFieldMessage != null) && (this.formFieldMessage.length > 0)) {
        for (int j = 0; j < this.formFieldMessage.length; j++)
        {
          UiErrorOuterClass.FormFieldMessage localFormFieldMessage = this.formFieldMessage[j];
          if (localFormFieldMessage != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localFormFieldMessage);
          }
        }
      }
      if (!this.errorCode.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.errorCode);
      }
      if (!this.internalDetails.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.internalDetails);
      }
      if (this.action != 1) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.action);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.message.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.message);
      }
      if ((this.formFieldMessage != null) && (this.formFieldMessage.length > 0)) {
        for (int i = 0; i < this.formFieldMessage.length; i++)
        {
          UiErrorOuterClass.FormFieldMessage localFormFieldMessage = this.formFieldMessage[i];
          if (localFormFieldMessage != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localFormFieldMessage);
          }
        }
      }
      if (!this.errorCode.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.errorCode);
      }
      if (!this.internalDetails.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.internalDetails);
      }
      if (this.action != 1) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.action);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass
 * JD-Core Version:    0.7.0.1
 */