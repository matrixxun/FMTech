package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class HttpCookie
  extends MessageNano
{
  private static volatile HttpCookie[] _emptyArray;
  public boolean hasName = false;
  public boolean hasValue = false;
  public String name = "";
  public String value = "";
  
  public HttpCookie()
  {
    this.cachedSize = -1;
  }
  
  public static HttpCookie[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new HttpCookie[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasName) || (!this.name.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.name);
    }
    if ((this.hasValue) || (!this.value.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.value);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasName) || (!this.name.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.name);
    }
    if ((this.hasValue) || (!this.value.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.value);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.HttpCookie
 * JD-Core Version:    0.7.0.1
 */