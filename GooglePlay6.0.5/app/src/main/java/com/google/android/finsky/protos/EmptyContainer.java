package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class EmptyContainer
  extends MessageNano
{
  public String emptyMessage = "";
  public boolean hasEmptyMessage = false;
  
  public EmptyContainer()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasEmptyMessage) || (!this.emptyMessage.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.emptyMessage);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasEmptyMessage) || (!this.emptyMessage.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.emptyMessage);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.EmptyContainer
 * JD-Core Version:    0.7.0.1
 */