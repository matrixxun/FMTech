package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class SelfUpdateResponse
  extends MessageNano
{
  public boolean hasLatestClientVersionCode = false;
  public boolean hasRequiresUploadDeviceConfig = false;
  public int latestClientVersionCode = 0;
  public boolean requiresUploadDeviceConfig = false;
  
  public SelfUpdateResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasRequiresUploadDeviceConfig) || (this.requiresUploadDeviceConfig)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
    }
    if ((this.hasLatestClientVersionCode) || (this.latestClientVersionCode != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.latestClientVersionCode);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasRequiresUploadDeviceConfig) || (this.requiresUploadDeviceConfig)) {
      paramCodedOutputByteBufferNano.writeBool(1, this.requiresUploadDeviceConfig);
    }
    if ((this.hasLatestClientVersionCode) || (this.latestClientVersionCode != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.latestClientVersionCode);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SelfUpdateResponse
 * JD-Core Version:    0.7.0.1
 */