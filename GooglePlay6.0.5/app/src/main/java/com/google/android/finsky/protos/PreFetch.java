package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public final class PreFetch
  extends MessageNano
{
  private static volatile PreFetch[] _emptyArray;
  public String etag = "";
  public boolean hasEtag = false;
  public boolean hasResponse = false;
  public boolean hasSoftTtl = false;
  public boolean hasTtl = false;
  public boolean hasUrl = false;
  public byte[] response = WireFormatNano.EMPTY_BYTES;
  public long softTtl = 0L;
  public long ttl = 0L;
  public String url = "";
  
  public PreFetch()
  {
    this.cachedSize = -1;
  }
  
  public static PreFetch[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new PreFetch[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasUrl) || (!this.url.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.url);
    }
    if ((this.hasResponse) || (!Arrays.equals(this.response, WireFormatNano.EMPTY_BYTES))) {
      i += CodedOutputByteBufferNano.computeBytesSize(2, this.response);
    }
    if ((this.hasEtag) || (!this.etag.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.etag);
    }
    if ((this.hasTtl) || (this.ttl != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(4, this.ttl);
    }
    if ((this.hasSoftTtl) || (this.softTtl != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(5, this.softTtl);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasUrl) || (!this.url.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.url);
    }
    if ((this.hasResponse) || (!Arrays.equals(this.response, WireFormatNano.EMPTY_BYTES))) {
      paramCodedOutputByteBufferNano.writeBytes(2, this.response);
    }
    if ((this.hasEtag) || (!this.etag.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.etag);
    }
    if ((this.hasTtl) || (this.ttl != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(4, this.ttl);
    }
    if ((this.hasSoftTtl) || (this.softTtl != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(5, this.softTtl);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PreFetch
 * JD-Core Version:    0.7.0.1
 */