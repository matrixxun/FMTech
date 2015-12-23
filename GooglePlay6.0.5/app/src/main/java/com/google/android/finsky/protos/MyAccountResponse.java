package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class MyAccountResponse
  extends MessageNano
{
  public SectionMetadata purchaseHistoryMetadata = null;
  public SectionMetadata rewardsMetadata = null;
  public SectionMetadata subscriptionsMetadata = null;
  
  public MyAccountResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.purchaseHistoryMetadata != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.purchaseHistoryMetadata);
    }
    if (this.subscriptionsMetadata != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.subscriptionsMetadata);
    }
    if (this.rewardsMetadata != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.rewardsMetadata);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.purchaseHistoryMetadata != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.purchaseHistoryMetadata);
    }
    if (this.subscriptionsMetadata != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.subscriptionsMetadata);
    }
    if (this.rewardsMetadata != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.rewardsMetadata);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.MyAccountResponse
 * JD-Core Version:    0.7.0.1
 */