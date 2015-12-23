package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class MoreByCreatorContainer
  extends MessageNano
{
  public DocV2 creatorInformation = null;
  
  public MoreByCreatorContainer()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.creatorInformation != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.creatorInformation);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.creatorInformation != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.creatorInformation);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.MoreByCreatorContainer
 * JD-Core Version:    0.7.0.1
 */