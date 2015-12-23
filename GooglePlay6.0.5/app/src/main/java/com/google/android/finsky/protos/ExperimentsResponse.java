package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ExperimentsResponse
  extends MessageNano
{
  public Targets targets = null;
  
  public ExperimentsResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.targets != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.targets);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.targets != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.targets);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ExperimentsResponse
 * JD-Core Version:    0.7.0.1
 */