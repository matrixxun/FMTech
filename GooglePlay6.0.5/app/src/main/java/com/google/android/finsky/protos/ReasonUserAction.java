package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ReasonUserAction
  extends MessageNano
{
  public boolean hasLocalizedDescriptionHtml = false;
  public String localizedDescriptionHtml = "";
  public DocV2 person = null;
  
  public ReasonUserAction()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.person != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.person);
    }
    if ((this.hasLocalizedDescriptionHtml) || (!this.localizedDescriptionHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.localizedDescriptionHtml);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.person != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.person);
    }
    if ((this.hasLocalizedDescriptionHtml) || (!this.localizedDescriptionHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.localizedDescriptionHtml);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ReasonUserAction
 * JD-Core Version:    0.7.0.1
 */