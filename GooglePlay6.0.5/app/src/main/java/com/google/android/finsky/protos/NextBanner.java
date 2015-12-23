package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class NextBanner
  extends MessageNano
{
  public String colorTextArgb = "";
  public boolean hasColorTextArgb = false;
  public boolean hasSubtitle = false;
  public boolean hasTitle = false;
  public String subtitle = "";
  public String title = "";
  
  public NextBanner()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
    }
    if ((this.hasSubtitle) || (!this.subtitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.subtitle);
    }
    if ((this.hasColorTextArgb) || (!this.colorTextArgb.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.colorTextArgb);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.title);
    }
    if ((this.hasSubtitle) || (!this.subtitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.subtitle);
    }
    if ((this.hasColorTextArgb) || (!this.colorTextArgb.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.colorTextArgb);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.NextBanner
 * JD-Core Version:    0.7.0.1
 */