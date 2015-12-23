package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class InAppNotificationData
  extends MessageNano
{
  public String checkoutOrderId = "";
  public boolean hasCheckoutOrderId = false;
  public boolean hasInAppNotificationId = false;
  public String inAppNotificationId = "";
  
  public InAppNotificationData()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasCheckoutOrderId) || (!this.checkoutOrderId.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.checkoutOrderId);
    }
    if ((this.hasInAppNotificationId) || (!this.inAppNotificationId.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.inAppNotificationId);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasCheckoutOrderId) || (!this.checkoutOrderId.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.checkoutOrderId);
    }
    if ((this.hasInAppNotificationId) || (!this.inAppNotificationId.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.inAppNotificationId);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.InAppNotificationData
 * JD-Core Version:    0.7.0.1
 */