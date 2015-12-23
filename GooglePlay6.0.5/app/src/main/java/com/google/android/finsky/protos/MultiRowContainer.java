package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class MultiRowContainer
  extends MessageNano
{
  public boolean hasRowCount = false;
  public int rowCount = 1;
  
  public MultiRowContainer()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasRowCount) || (this.rowCount != 1)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.rowCount);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasRowCount) || (this.rowCount != 1)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.rowCount);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.MultiRowContainer
 * JD-Core Version:    0.7.0.1
 */