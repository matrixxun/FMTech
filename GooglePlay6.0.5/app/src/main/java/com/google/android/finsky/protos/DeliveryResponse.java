package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class DeliveryResponse
  extends MessageNano
{
  public AndroidAppDeliveryData appDeliveryData = null;
  public boolean hasStatus = false;
  public int status = 1;
  
  public DeliveryResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.status != 1) || (this.hasStatus)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.status);
    }
    if (this.appDeliveryData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.appDeliveryData);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.status != 1) || (this.hasStatus)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.status);
    }
    if (this.appDeliveryData != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.appDeliveryData);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.DeliveryResponse
 * JD-Core Version:    0.7.0.1
 */