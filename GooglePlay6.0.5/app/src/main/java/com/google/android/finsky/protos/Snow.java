package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Snow
  extends MessageNano
{
  public String clickUrl = "";
  public boolean hasClickUrl = false;
  public boolean hasSnowBadgeText = false;
  public boolean hasSnowText = false;
  public String snowBadgeText = "";
  public String snowText = "";
  
  public Snow()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasClickUrl) || (!this.clickUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.clickUrl);
    }
    if ((this.hasSnowText) || (!this.snowText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.snowText);
    }
    if ((this.hasSnowBadgeText) || (!this.snowBadgeText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.snowBadgeText);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasClickUrl) || (!this.clickUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.clickUrl);
    }
    if ((this.hasSnowText) || (!this.snowText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.snowText);
    }
    if ((this.hasSnowBadgeText) || (!this.snowBadgeText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.snowBadgeText);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Snow
 * JD-Core Version:    0.7.0.1
 */