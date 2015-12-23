package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class MyRewardDetails
  extends MessageNano
{
  public String buttonLabel = "";
  public String expirationDescription = "";
  public long expirationTimeMillis = 0L;
  public boolean hasButtonLabel = false;
  public boolean hasExpirationDescription = false;
  public boolean hasExpirationTimeMillis = false;
  public Link linkAction = null;
  
  public MyRewardDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasExpirationTimeMillis) || (this.expirationTimeMillis != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(1, this.expirationTimeMillis);
    }
    if ((this.hasExpirationDescription) || (!this.expirationDescription.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.expirationDescription);
    }
    if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.buttonLabel);
    }
    if (this.linkAction != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(4, this.linkAction);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasExpirationTimeMillis) || (this.expirationTimeMillis != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(1, this.expirationTimeMillis);
    }
    if ((this.hasExpirationDescription) || (!this.expirationDescription.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.expirationDescription);
    }
    if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.buttonLabel);
    }
    if (this.linkAction != null) {
      paramCodedOutputByteBufferNano.writeMessage(4, this.linkAction);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.MyRewardDetails
 * JD-Core Version:    0.7.0.1
 */