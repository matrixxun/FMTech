package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class SelectedChild
  extends MessageNano
{
  public String docid = "";
  public boolean hasDocid = false;
  public SelectedChild selectedChild = null;
  
  public SelectedChild()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasDocid) || (!this.docid.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.docid);
    }
    if (this.selectedChild != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.selectedChild);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasDocid) || (!this.docid.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.docid);
    }
    if (this.selectedChild != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.selectedChild);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SelectedChild
 * JD-Core Version:    0.7.0.1
 */