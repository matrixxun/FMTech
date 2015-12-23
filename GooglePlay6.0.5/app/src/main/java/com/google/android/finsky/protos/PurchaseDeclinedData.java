package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PurchaseDeclinedData
  extends MessageNano
{
  public boolean hasReason = false;
  public boolean hasShowNotification = false;
  public int reason = 0;
  public boolean showNotification = true;
  
  public PurchaseDeclinedData()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.reason != 0) || (this.hasReason)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.reason);
    }
    if ((this.hasShowNotification) || (this.showNotification != true)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.reason != 0) || (this.hasReason)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.reason);
    }
    if ((this.hasShowNotification) || (this.showNotification != true)) {
      paramCodedOutputByteBufferNano.writeBool(2, this.showNotification);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PurchaseDeclinedData
 * JD-Core Version:    0.7.0.1
 */