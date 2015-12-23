package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class CarrierTosEntry
  extends MessageNano
{
  public boolean hasUrl = false;
  public boolean hasVersion = false;
  public String url = "";
  public String version = "";
  
  public CarrierTosEntry()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasUrl) || (!this.url.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.url);
    }
    if ((this.hasVersion) || (!this.version.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.version);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasUrl) || (!this.url.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.url);
    }
    if ((this.hasVersion) || (!this.version.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.version);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.CarrierTosEntry
 * JD-Core Version:    0.7.0.1
 */