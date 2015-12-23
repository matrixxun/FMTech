package com.google.commerce.payments.orchestration.proto.ui.common.components.legal;

import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface LegalMessageOuterClass
{
  public static final class LegalMessage
    extends MessageNano
  {
    public InfoMessageOuterClass.InfoMessage messageText = null;
    public String opaqueData = "";
    public String viewerUrl = "";
    
    public LegalMessage()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.viewerUrl.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.viewerUrl);
      }
      if (!this.opaqueData.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.opaqueData);
      }
      if (this.messageText != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.messageText);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.viewerUrl.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.viewerUrl);
      }
      if (!this.opaqueData.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.opaqueData);
      }
      if (this.messageText != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.messageText);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass
 * JD-Core Version:    0.7.0.1
 */