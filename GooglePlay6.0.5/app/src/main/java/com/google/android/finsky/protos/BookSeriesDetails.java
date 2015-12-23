package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class BookSeriesDetails
  extends MessageNano
{
  public boolean hasPublisher = false;
  public boolean hasSeriesComposition = false;
  public String publisher = "";
  public String seriesComposition = "";
  
  public BookSeriesDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasPublisher) || (!this.publisher.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.publisher);
    }
    if ((this.hasSeriesComposition) || (!this.seriesComposition.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.seriesComposition);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasPublisher) || (!this.publisher.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.publisher);
    }
    if ((this.hasSeriesComposition) || (!this.seriesComposition.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.seriesComposition);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.BookSeriesDetails
 * JD-Core Version:    0.7.0.1
 */