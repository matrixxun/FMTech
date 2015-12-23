package com.google.commerce.payments.orchestration.proto.ui.common.components;

import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface FormHeaderOuterClass
{
  public static final class FormHeader
    extends MessageNano
  {
    public byte[] dataToken = WireFormatNano.EMPTY_BYTES;
    public InfoMessageOuterClass.InfoMessage[] description = InfoMessageOuterClass.InfoMessage.emptyArray();
    public String id = "";
    public String title = "";
    public int uiReference = 0;
    
    public FormHeader()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.id.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.id);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.dataToken);
      }
      if (!this.title.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.title);
      }
      if ((this.description != null) && (this.description.length > 0)) {
        for (int j = 0; j < this.description.length; j++)
        {
          InfoMessageOuterClass.InfoMessage localInfoMessage = this.description[j];
          if (localInfoMessage != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(5, localInfoMessage);
          }
        }
      }
      if (this.uiReference != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(7, this.uiReference);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.id);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.dataToken);
      }
      if (!this.title.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.title);
      }
      if ((this.description != null) && (this.description.length > 0)) {
        for (int i = 0; i < this.description.length; i++)
        {
          InfoMessageOuterClass.InfoMessage localInfoMessage = this.description[i];
          if (localInfoMessage != null) {
            paramCodedOutputByteBufferNano.writeMessage(5, localInfoMessage);
          }
        }
      }
      if (this.uiReference != 0) {
        paramCodedOutputByteBufferNano.writeInt32(7, this.uiReference);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.FormHeaderOuterClass
 * JD-Core Version:    0.7.0.1
 */