package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Warning
  extends MessageNano
{
  private static volatile Warning[] _emptyArray;
  public boolean hasLocalizedMessage = false;
  public String localizedMessage = "";
  
  public Warning()
  {
    this.cachedSize = -1;
  }
  
  public static Warning[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new Warning[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasLocalizedMessage) || (!this.localizedMessage.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.localizedMessage);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasLocalizedMessage) || (!this.localizedMessage.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.localizedMessage);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Warning
 * JD-Core Version:    0.7.0.1
 */