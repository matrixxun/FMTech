package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class TileTemplate
  extends MessageNano
{
  public String colorTextArgb = "";
  public String colorThemeArgb = "";
  public boolean hasColorTextArgb = false;
  public boolean hasColorThemeArgb = false;
  
  public TileTemplate()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasColorThemeArgb) || (!this.colorThemeArgb.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.colorThemeArgb);
    }
    if ((this.hasColorTextArgb) || (!this.colorTextArgb.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.colorTextArgb);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasColorThemeArgb) || (!this.colorThemeArgb.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.colorThemeArgb);
    }
    if ((this.hasColorTextArgb) || (!this.colorTextArgb.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.colorTextArgb);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.TileTemplate
 * JD-Core Version:    0.7.0.1
 */