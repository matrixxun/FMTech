package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class TvShowDetails
  extends MessageNano
{
  public String broadcaster = "";
  public int endYear = 0;
  public boolean hasBroadcaster = false;
  public boolean hasEndYear = false;
  public boolean hasSeasonCount = false;
  public boolean hasStartYear = false;
  public int seasonCount = 0;
  public int startYear = 0;
  
  public TvShowDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasSeasonCount) || (this.seasonCount != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.seasonCount);
    }
    if ((this.hasStartYear) || (this.startYear != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.startYear);
    }
    if ((this.hasEndYear) || (this.endYear != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(3, this.endYear);
    }
    if ((this.hasBroadcaster) || (!this.broadcaster.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.broadcaster);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasSeasonCount) || (this.seasonCount != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.seasonCount);
    }
    if ((this.hasStartYear) || (this.startYear != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.startYear);
    }
    if ((this.hasEndYear) || (this.endYear != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(3, this.endYear);
    }
    if ((this.hasBroadcaster) || (!this.broadcaster.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.broadcaster);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.TvShowDetails
 * JD-Core Version:    0.7.0.1
 */