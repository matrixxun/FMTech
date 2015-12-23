package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class TvEpisodeDetails
  extends MessageNano
{
  public int episodeIndex = 0;
  public boolean hasEpisodeIndex = false;
  public boolean hasParentDetailsUrl = false;
  public boolean hasReleaseDate = false;
  public String parentDetailsUrl = "";
  public String releaseDate = "";
  
  public TvEpisodeDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasParentDetailsUrl) || (!this.parentDetailsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.parentDetailsUrl);
    }
    if ((this.hasEpisodeIndex) || (this.episodeIndex != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.episodeIndex);
    }
    if ((this.hasReleaseDate) || (!this.releaseDate.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.releaseDate);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasParentDetailsUrl) || (!this.parentDetailsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.parentDetailsUrl);
    }
    if ((this.hasEpisodeIndex) || (this.episodeIndex != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.episodeIndex);
    }
    if ((this.hasReleaseDate) || (!this.releaseDate.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.releaseDate);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.TvEpisodeDetails
 * JD-Core Version:    0.7.0.1
 */