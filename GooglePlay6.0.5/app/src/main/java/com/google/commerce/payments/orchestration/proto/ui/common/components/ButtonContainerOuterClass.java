package com.google.commerce.payments.orchestration.proto.ui.common.components;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface ButtonContainerOuterClass
{
  public static final class Button
    extends MessageNano
  {
    public String buttonTextAfterRefresh = "";
    public String buttonTextBeforeClick = "";
    public String buttonTextDuringRefresh = "";
    public boolean enabled = false;
    public int resendTimeMs = 0;
    public int uiReference = 0;
    
    public Button()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.uiReference != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.uiReference);
      }
      if (this.enabled) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if (!this.buttonTextBeforeClick.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.buttonTextBeforeClick);
      }
      if (!this.buttonTextDuringRefresh.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.buttonTextDuringRefresh);
      }
      if (!this.buttonTextAfterRefresh.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.buttonTextAfterRefresh);
      }
      if (this.resendTimeMs != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(6, this.resendTimeMs);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.uiReference != 0) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.uiReference);
      }
      if (this.enabled) {
        paramCodedOutputByteBufferNano.writeBool(2, this.enabled);
      }
      if (!this.buttonTextBeforeClick.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.buttonTextBeforeClick);
      }
      if (!this.buttonTextDuringRefresh.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.buttonTextDuringRefresh);
      }
      if (!this.buttonTextAfterRefresh.equals("")) {
        paramCodedOutputByteBufferNano.writeString(5, this.buttonTextAfterRefresh);
      }
      if (this.resendTimeMs != 0) {
        paramCodedOutputByteBufferNano.writeInt32(6, this.resendTimeMs);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.ButtonContainerOuterClass
 * JD-Core Version:    0.7.0.1
 */