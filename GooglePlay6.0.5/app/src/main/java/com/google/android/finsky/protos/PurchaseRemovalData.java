package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PurchaseRemovalData
  extends MessageNano
{
  public boolean hasMalicious = false;
  public boolean malicious = false;
  
  public PurchaseRemovalData()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasMalicious) || (this.malicious)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasMalicious) || (this.malicious)) {
      paramCodedOutputByteBufferNano.writeBool(1, this.malicious);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PurchaseRemovalData
 * JD-Core Version:    0.7.0.1
 */