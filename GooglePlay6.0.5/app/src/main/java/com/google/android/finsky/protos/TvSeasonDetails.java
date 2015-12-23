package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class TvSeasonDetails
  extends MessageNano
{
  public String broadcaster = "";
  public int episodeCount = 0;
  public int expectedEpisodeCount = 0;
  public boolean hasBroadcaster = false;
  public boolean hasEpisodeCount = false;
  public boolean hasExpectedEpisodeCount = false;
  public boolean hasInProgress = false;
  public boolean hasParentDetailsUrl = false;
  public boolean hasReleaseDate = false;
  public boolean hasSeasonIndex = false;
  public boolean inProgress = false;
  public String parentDetailsUrl = "";
  public String releaseDate = "";
  public int seasonIndex = 0;
  
  public TvSeasonDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasParentDetailsUrl) || (!this.parentDetailsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.parentDetailsUrl);
    }
    if ((this.hasSeasonIndex) || (this.seasonIndex != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.seasonIndex);
    }
    if ((this.hasReleaseDate) || (!this.releaseDate.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.releaseDate);
    }
    if ((this.hasBroadcaster) || (!this.broadcaster.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.broadcaster);
    }
    if ((this.hasEpisodeCount) || (this.episodeCount != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(5, this.episodeCount);
    }
    if ((this.hasExpectedEpisodeCount) || (this.expectedEpisodeCount != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(6, this.expectedEpisodeCount);
    }
    if ((this.hasInProgress) || (this.inProgress)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasParentDetailsUrl) || (!this.parentDetailsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.parentDetailsUrl);
    }
    if ((this.hasSeasonIndex) || (this.seasonIndex != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.seasonIndex);
    }
    if ((this.hasReleaseDate) || (!this.releaseDate.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.releaseDate);
    }
    if ((this.hasBroadcaster) || (!this.broadcaster.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.broadcaster);
    }
    if ((this.hasEpisodeCount) || (this.episodeCount != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(5, this.episodeCount);
    }
    if ((this.hasExpectedEpisodeCount) || (this.expectedEpisodeCount != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(6, this.expectedEpisodeCount);
    }
    if ((this.hasInProgress) || (this.inProgress)) {
      paramCodedOutputByteBufferNano.writeBool(7, this.inProgress);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.TvSeasonDetails
 * JD-Core Version:    0.7.0.1
 */