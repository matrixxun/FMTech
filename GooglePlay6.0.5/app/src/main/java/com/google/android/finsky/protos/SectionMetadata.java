package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class SectionMetadata
  extends MessageNano
{
  private static volatile SectionMetadata[] _emptyArray;
  public String browseUrl = "";
  public String descriptionHtml = "";
  public boolean hasBrowseUrl = false;
  public boolean hasDescriptionHtml = false;
  public boolean hasHeader = false;
  public boolean hasListUrl = false;
  public String header = "";
  public String listUrl = "";
  
  public SectionMetadata()
  {
    this.cachedSize = -1;
  }
  
  public static SectionMetadata[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new SectionMetadata[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasHeader) || (!this.header.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.header);
    }
    if ((this.hasListUrl) || (!this.listUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.listUrl);
    }
    if ((this.hasBrowseUrl) || (!this.browseUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.browseUrl);
    }
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.descriptionHtml);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasHeader) || (!this.header.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.header);
    }
    if ((this.hasListUrl) || (!this.listUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.listUrl);
    }
    if ((this.hasBrowseUrl) || (!this.browseUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.browseUrl);
    }
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.descriptionHtml);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SectionMetadata
 * JD-Core Version:    0.7.0.1
 */