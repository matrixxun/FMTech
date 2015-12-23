package com.google.commerce.payments.orchestration.proto.ui.common.generic;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface UiFieldGroupingOuterClass
{
  public static final class UiFieldGrouping
    extends MessageNano
  {
    private static volatile UiFieldGrouping[] _emptyArray;
    public int endIndex = 0;
    public int startIndex = 0;
    
    public UiFieldGrouping()
    {
      this.cachedSize = -1;
    }
    
    public static UiFieldGrouping[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new UiFieldGrouping[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.startIndex != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.startIndex);
      }
      if (this.endIndex != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.endIndex);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.startIndex != 0) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.startIndex);
      }
      if (this.endIndex != 0) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.endIndex);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldGroupingOuterClass
 * JD-Core Version:    0.7.0.1
 */