package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ConsumePurchaseResponse
  extends MessageNano
{
  public boolean hasStatus = false;
  public LibraryUpdateProto.LibraryUpdate libraryUpdate = null;
  public int status = 0;
  
  public ConsumePurchaseResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.libraryUpdate != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.libraryUpdate);
    }
    if ((this.status != 0) || (this.hasStatus)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.status);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.libraryUpdate != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.libraryUpdate);
    }
    if ((this.status != 0) || (this.hasStatus)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.status);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ConsumePurchaseResponse
 * JD-Core Version:    0.7.0.1
 */