package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PlayPlusProfileResponse
  extends MessageNano
{
  public boolean hasIsGplusUser = false;
  public boolean isGplusUser = false;
  public PlayDocument userProfile = null;
  
  public PlayPlusProfileResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.userProfile != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.userProfile);
    }
    if ((this.hasIsGplusUser) || (this.isGplusUser)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.userProfile != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.userProfile);
    }
    if ((this.hasIsGplusUser) || (this.isGplusUser)) {
      paramCodedOutputByteBufferNano.writeBool(2, this.isGplusUser);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PlayPlusProfileResponse
 * JD-Core Version:    0.7.0.1
 */