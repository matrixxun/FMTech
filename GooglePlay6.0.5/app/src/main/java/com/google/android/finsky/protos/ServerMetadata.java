package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ServerMetadata
  extends MessageNano
{
  public boolean hasLatencyMillis = false;
  public long latencyMillis = 0L;
  
  public ServerMetadata()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasLatencyMillis) || (this.latencyMillis != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(1, this.latencyMillis);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasLatencyMillis) || (this.latencyMillis != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(1, this.latencyMillis);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ServerMetadata
 * JD-Core Version:    0.7.0.1
 */