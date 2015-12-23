package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class DeviceAssociation
  extends MessageNano
{
  public boolean hasUserTokenRequestAddress = false;
  public boolean hasUserTokenRequestMessage = false;
  public String userTokenRequestAddress = "";
  public String userTokenRequestMessage = "";
  
  public DeviceAssociation()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasUserTokenRequestMessage) || (!this.userTokenRequestMessage.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.userTokenRequestMessage);
    }
    if ((this.hasUserTokenRequestAddress) || (!this.userTokenRequestAddress.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.userTokenRequestAddress);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasUserTokenRequestMessage) || (!this.userTokenRequestMessage.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.userTokenRequestMessage);
    }
    if ((this.hasUserTokenRequestAddress) || (!this.userTokenRequestAddress.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.userTokenRequestAddress);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.DeviceAssociation
 * JD-Core Version:    0.7.0.1
 */