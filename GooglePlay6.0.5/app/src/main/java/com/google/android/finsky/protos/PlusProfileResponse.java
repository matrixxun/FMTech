package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PlusProfileResponse
  extends MessageNano
{
  public boolean hasIsGplusUser = false;
  public boolean isGplusUser = true;
  public DocV2 partialUserProfile = null;
  public OBSOLETE_PlusProfile plusProfile = null;
  
  public PlusProfileResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.plusProfile != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.plusProfile);
    }
    if (this.partialUserProfile != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.partialUserProfile);
    }
    if ((this.hasIsGplusUser) || (this.isGplusUser != true)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.plusProfile != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.plusProfile);
    }
    if (this.partialUserProfile != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.partialUserProfile);
    }
    if ((this.hasIsGplusUser) || (this.isGplusUser != true)) {
      paramCodedOutputByteBufferNano.writeBool(3, this.isGplusUser);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PlusProfileResponse
 * JD-Core Version:    0.7.0.1
 */