package com.google.commerce.payments.orchestration.proto.ui.common.generic;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface InfoMessageOuterClass
{
  public static final class InfoMessage
    extends MessageNano
  {
    private static volatile InfoMessage[] _emptyArray;
    public String detailedMessageHtml = "";
    public String messageHtml = "";
    public String showDetailedMessageLabel = "";
    public int uiReference = 0;
    
    public InfoMessage()
    {
      this.cachedSize = -1;
    }
    
    public static InfoMessage[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new InfoMessage[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.messageHtml.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.messageHtml);
      }
      if (!this.detailedMessageHtml.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.detailedMessageHtml);
      }
      if (!this.showDetailedMessageLabel.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.showDetailedMessageLabel);
      }
      if (this.uiReference != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.uiReference);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.messageHtml.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.messageHtml);
      }
      if (!this.detailedMessageHtml.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.detailedMessageHtml);
      }
      if (!this.showDetailedMessageLabel.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.showDetailedMessageLabel);
      }
      if (this.uiReference != 0) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.uiReference);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass
 * JD-Core Version:    0.7.0.1
 */