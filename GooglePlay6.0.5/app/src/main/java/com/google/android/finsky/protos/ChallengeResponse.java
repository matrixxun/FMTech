package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ChallengeResponse
  extends MessageNano
{
  public ChallengeProto.Challenge challenge = null;
  public boolean challengePassed = false;
  public boolean hasChallengePassed = false;
  
  public ChallengeResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.challenge != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.challenge);
    }
    if ((this.hasChallengePassed) || (this.challengePassed)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.challenge != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.challenge);
    }
    if ((this.hasChallengePassed) || (this.challengePassed)) {
      paramCodedOutputByteBufferNano.writeBool(2, this.challengePassed);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ChallengeResponse
 * JD-Core Version:    0.7.0.1
 */