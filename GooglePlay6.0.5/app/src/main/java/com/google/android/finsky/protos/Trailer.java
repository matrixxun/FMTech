package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Trailer
  extends MessageNano
{
  private static volatile Trailer[] _emptyArray;
  public String duration = "";
  public boolean hasDuration = false;
  public boolean hasThumbnailUrl = false;
  public boolean hasTitle = false;
  public boolean hasTrailerId = false;
  public boolean hasWatchUrl = false;
  public String thumbnailUrl = "";
  public String title = "";
  public String trailerId = "";
  public String watchUrl = "";
  
  public Trailer()
  {
    this.cachedSize = -1;
  }
  
  public static Trailer[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new Trailer[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasTrailerId) || (!this.trailerId.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.trailerId);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.title);
    }
    if ((this.hasThumbnailUrl) || (!this.thumbnailUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.thumbnailUrl);
    }
    if ((this.hasWatchUrl) || (!this.watchUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.watchUrl);
    }
    if ((this.hasDuration) || (!this.duration.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.duration);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTrailerId) || (!this.trailerId.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.trailerId);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.title);
    }
    if ((this.hasThumbnailUrl) || (!this.thumbnailUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.thumbnailUrl);
    }
    if ((this.hasWatchUrl) || (!this.watchUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.watchUrl);
    }
    if ((this.hasDuration) || (!this.duration.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.duration);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Trailer
 * JD-Core Version:    0.7.0.1
 */