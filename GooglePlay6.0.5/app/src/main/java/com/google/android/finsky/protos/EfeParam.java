package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class EfeParam
  extends MessageNano
{
  private static volatile EfeParam[] _emptyArray;
  public boolean hasKey = false;
  public boolean hasValue = false;
  public int key = 1;
  public String value = "";
  
  public EfeParam()
  {
    this.cachedSize = -1;
  }
  
  public static EfeParam[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new EfeParam[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.key != 1) || (this.hasKey)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.key);
    }
    if ((this.hasValue) || (!this.value.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.value);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.key != 1) || (this.hasKey)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.key);
    }
    if ((this.hasValue) || (!this.value.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.value);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.EfeParam
 * JD-Core Version:    0.7.0.1
 */