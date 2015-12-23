package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ArtistDetails
  extends MessageNano
{
  private static volatile ArtistDetails[] _emptyArray;
  public String detailsUrl = "";
  public ArtistExternalLinks externalLinks = null;
  public boolean hasDetailsUrl = false;
  public boolean hasName = false;
  public String name = "";
  
  public ArtistDetails()
  {
    this.cachedSize = -1;
  }
  
  public static ArtistDetails[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new ArtistDetails[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.detailsUrl);
    }
    if ((this.hasName) || (!this.name.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.name);
    }
    if (this.externalLinks != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.externalLinks);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.detailsUrl);
    }
    if ((this.hasName) || (!this.name.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.name);
    }
    if (this.externalLinks != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.externalLinks);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ArtistDetails
 * JD-Core Version:    0.7.0.1
 */