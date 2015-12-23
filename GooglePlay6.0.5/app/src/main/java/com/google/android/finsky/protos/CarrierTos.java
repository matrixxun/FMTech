package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class CarrierTos
  extends MessageNano
{
  public CarrierTosEntry dcbTos = null;
  public boolean hasNeedsDcbTosAcceptance = false;
  public boolean hasNeedsPiiTosAcceptance = false;
  public boolean needsDcbTosAcceptance = false;
  public boolean needsPiiTosAcceptance = false;
  public CarrierTosEntry piiTos = null;
  
  public CarrierTos()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.dcbTos != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.dcbTos);
    }
    if (this.piiTos != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.piiTos);
    }
    if ((this.hasNeedsDcbTosAcceptance) || (this.needsDcbTosAcceptance)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
    }
    if ((this.hasNeedsPiiTosAcceptance) || (this.needsPiiTosAcceptance)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.dcbTos != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.dcbTos);
    }
    if (this.piiTos != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.piiTos);
    }
    if ((this.hasNeedsDcbTosAcceptance) || (this.needsDcbTosAcceptance)) {
      paramCodedOutputByteBufferNano.writeBool(3, this.needsDcbTosAcceptance);
    }
    if ((this.hasNeedsPiiTosAcceptance) || (this.needsPiiTosAcceptance)) {
      paramCodedOutputByteBufferNano.writeBool(4, this.needsPiiTosAcceptance);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.CarrierTos
 * JD-Core Version:    0.7.0.1
 */