package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class RevokeResponse
  extends MessageNano
{
  public LibraryUpdateProto.LibraryUpdate libraryUpdate = null;
  
  public RevokeResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.libraryUpdate != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.libraryUpdate);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.libraryUpdate != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.libraryUpdate);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.RevokeResponse
 * JD-Core Version:    0.7.0.1
 */