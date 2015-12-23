package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class SeriesAntenna
  extends MessageNano
{
  public String colorThemeArgb = "";
  public String episodeSubtitle = "";
  public String episodeTitle = "";
  public boolean hasColorThemeArgb = false;
  public boolean hasEpisodeSubtitle = false;
  public boolean hasEpisodeTitle = false;
  public boolean hasSeriesSubtitle = false;
  public boolean hasSeriesTitle = false;
  public SectionMetadata sectionAlbums = null;
  public SectionMetadata sectionTracks = null;
  public String seriesSubtitle = "";
  public String seriesTitle = "";
  
  public SeriesAntenna()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasSeriesTitle) || (!this.seriesTitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.seriesTitle);
    }
    if ((this.hasSeriesSubtitle) || (!this.seriesSubtitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.seriesSubtitle);
    }
    if ((this.hasEpisodeTitle) || (!this.episodeTitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.episodeTitle);
    }
    if ((this.hasEpisodeSubtitle) || (!this.episodeSubtitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.episodeSubtitle);
    }
    if ((this.hasColorThemeArgb) || (!this.colorThemeArgb.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.colorThemeArgb);
    }
    if (this.sectionTracks != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(6, this.sectionTracks);
    }
    if (this.sectionAlbums != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(7, this.sectionAlbums);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasSeriesTitle) || (!this.seriesTitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.seriesTitle);
    }
    if ((this.hasSeriesSubtitle) || (!this.seriesSubtitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.seriesSubtitle);
    }
    if ((this.hasEpisodeTitle) || (!this.episodeTitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.episodeTitle);
    }
    if ((this.hasEpisodeSubtitle) || (!this.episodeSubtitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.episodeSubtitle);
    }
    if ((this.hasColorThemeArgb) || (!this.colorThemeArgb.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.colorThemeArgb);
    }
    if (this.sectionTracks != null) {
      paramCodedOutputByteBufferNano.writeMessage(6, this.sectionTracks);
    }
    if (this.sectionAlbums != null) {
      paramCodedOutputByteBufferNano.writeMessage(7, this.sectionAlbums);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SeriesAntenna
 * JD-Core Version:    0.7.0.1
 */