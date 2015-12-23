package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class YoutubeVideoContainer
  extends MessageNano
{
  public boolean hasVideoSubtitle = false;
  public boolean hasVideoTitle = false;
  public String videoSubtitle = "";
  public String videoTitle = "";
  
  public YoutubeVideoContainer()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasVideoTitle) || (!this.videoTitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.videoTitle);
    }
    if ((this.hasVideoSubtitle) || (!this.videoSubtitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.videoSubtitle);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasVideoTitle) || (!this.videoTitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.videoTitle);
    }
    if ((this.hasVideoSubtitle) || (!this.videoSubtitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.videoSubtitle);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.YoutubeVideoContainer
 * JD-Core Version:    0.7.0.1
 */