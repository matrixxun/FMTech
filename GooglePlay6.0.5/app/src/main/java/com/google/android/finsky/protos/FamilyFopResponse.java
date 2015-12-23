package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class FamilyFopResponse
  extends MessageNano
{
  public boolean hasResult = false;
  public boolean hasUserMessageHtml = false;
  public int result = 0;
  public String userMessageHtml = "";
  
  public FamilyFopResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.result != 0) || (this.hasResult)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.result);
    }
    if ((this.hasUserMessageHtml) || (!this.userMessageHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.userMessageHtml);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.result != 0) || (this.hasResult)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.result);
    }
    if ((this.hasUserMessageHtml) || (!this.userMessageHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.userMessageHtml);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.FamilyFopResponse
 * JD-Core Version:    0.7.0.1
 */