package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PlayDailyCard
  extends MessageNano
{
  public boolean hasVideoId = false;
  public PlayDailyRelatedLink[] relatedLink = PlayDailyRelatedLink.emptyArray();
  public String videoId = "";
  
  public PlayDailyCard()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasVideoId) || (!this.videoId.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.videoId);
    }
    if ((this.relatedLink != null) && (this.relatedLink.length > 0)) {
      for (int j = 0; j < this.relatedLink.length; j++)
      {
        PlayDailyRelatedLink localPlayDailyRelatedLink = this.relatedLink[j];
        if (localPlayDailyRelatedLink != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, localPlayDailyRelatedLink);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasVideoId) || (!this.videoId.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.videoId);
    }
    if ((this.relatedLink != null) && (this.relatedLink.length > 0)) {
      for (int i = 0; i < this.relatedLink.length; i++)
      {
        PlayDailyRelatedLink localPlayDailyRelatedLink = this.relatedLink[i];
        if (localPlayDailyRelatedLink != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, localPlayDailyRelatedLink);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PlayDailyCard
 * JD-Core Version:    0.7.0.1
 */