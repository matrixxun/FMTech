package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class EarlyUpdateRequest
  extends MessageNano
{
  public DeviceConfiguration.DeviceConfigurationProto deviceConfiguration = null;
  
  public EarlyUpdateRequest()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.deviceConfiguration != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.deviceConfiguration);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.deviceConfiguration != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.deviceConfiguration);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.EarlyUpdateRequest
 * JD-Core Version:    0.7.0.1
 */