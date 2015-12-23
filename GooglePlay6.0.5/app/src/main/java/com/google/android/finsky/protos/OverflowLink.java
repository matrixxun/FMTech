package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class OverflowLink
  extends MessageNano
{
  private static volatile OverflowLink[] _emptyArray;
  public boolean hasTitle = false;
  public Link link = null;
  public String title = "";
  
  public OverflowLink()
  {
    this.cachedSize = -1;
  }
  
  public static OverflowLink[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new OverflowLink[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
    }
    if (this.link != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.link);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.title);
    }
    if (this.link != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.link);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.OverflowLink
 * JD-Core Version:    0.7.0.1
 */