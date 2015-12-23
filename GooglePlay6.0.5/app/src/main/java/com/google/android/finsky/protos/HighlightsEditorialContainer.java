package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class HighlightsEditorialContainer
  extends MessageNano
{
  public boolean hasTextHorizontalAlignment = false;
  public boolean hasTextVerticalAlignment = false;
  public int textHorizontalAlignment = 0;
  public int textVerticalAlignment = 0;
  
  public HighlightsEditorialContainer()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.textVerticalAlignment != 0) || (this.hasTextVerticalAlignment)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.textVerticalAlignment);
    }
    if ((this.textHorizontalAlignment != 0) || (this.hasTextHorizontalAlignment)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.textHorizontalAlignment);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.textVerticalAlignment != 0) || (this.hasTextVerticalAlignment)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.textVerticalAlignment);
    }
    if ((this.textHorizontalAlignment != 0) || (this.hasTextHorizontalAlignment)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.textHorizontalAlignment);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.HighlightsEditorialContainer
 * JD-Core Version:    0.7.0.1
 */