package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class SubscriptionDetails
  extends MessageNano
{
  public boolean hasSubscriptionPeriod = false;
  public int subscriptionPeriod = 1;
  
  public SubscriptionDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.subscriptionPeriod != 1) || (this.hasSubscriptionPeriod)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.subscriptionPeriod);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.subscriptionPeriod != 1) || (this.hasSubscriptionPeriod)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.subscriptionPeriod);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SubscriptionDetails
 * JD-Core Version:    0.7.0.1
 */