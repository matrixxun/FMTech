package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Link
  extends MessageNano
{
  private static volatile Link[] _emptyArray;
  public boolean hasUri = false;
  public boolean hasUriBackend = false;
  public ResolveLink.ResolvedLink resolvedLink = null;
  public String uri = "";
  public int uriBackend = 0;
  
  public Link()
  {
    this.cachedSize = -1;
  }
  
  public static Link[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new Link[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasUri) || (!this.uri.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.uri);
    }
    if (this.resolvedLink != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.resolvedLink);
    }
    if ((this.uriBackend != 0) || (this.hasUriBackend)) {
      i += CodedOutputByteBufferNano.computeInt32Size(3, this.uriBackend);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasUri) || (!this.uri.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.uri);
    }
    if (this.resolvedLink != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.resolvedLink);
    }
    if ((this.uriBackend != 0) || (this.hasUriBackend)) {
      paramCodedOutputByteBufferNano.writeInt32(3, this.uriBackend);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Link
 * JD-Core Version:    0.7.0.1
 */