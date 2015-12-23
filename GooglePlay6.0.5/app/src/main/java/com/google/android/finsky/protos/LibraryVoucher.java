package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class LibraryVoucher
  extends MessageNano
{
  public Common.VoucherId voucherId = null;
  
  public LibraryVoucher()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.voucherId != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.voucherId);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.voucherId != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.voucherId);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.LibraryVoucher
 * JD-Core Version:    0.7.0.1
 */