package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class TrustedSourceContainer
  extends MessageNano
{
  public DocV2 source = null;
  
  public TrustedSourceContainer()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.source != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.source);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.source != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.source);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.TrustedSourceContainer
 * JD-Core Version:    0.7.0.1
 */