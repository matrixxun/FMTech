package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class StoredValueInstrument
  extends MessageNano
{
  public Money balance = null;
  public boolean hasType = false;
  public TopupInfo topupInfo = null;
  public int type = 32;
  
  public StoredValueInstrument()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.type != 32) || (this.hasType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
    }
    if (this.balance != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.balance);
    }
    if (this.topupInfo != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.topupInfo);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.type != 32) || (this.hasType)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.type);
    }
    if (this.balance != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.balance);
    }
    if (this.topupInfo != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.topupInfo);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.StoredValueInstrument
 * JD-Core Version:    0.7.0.1
 */