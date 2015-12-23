package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class SongDetails
  extends MessageNano
{
  public String albumName = "";
  public Badge badge = null;
  public MusicDetails details = null;
  public ArtistDetails displayArtist = null;
  public boolean hasAlbumName = false;
  public boolean hasName = false;
  public boolean hasPreviewUrl = false;
  public boolean hasTrackNumber = false;
  public String name = "";
  public String previewUrl = "";
  public int trackNumber = 0;
  
  public SongDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasName) || (!this.name.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.name);
    }
    if (this.details != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.details);
    }
    if ((this.hasAlbumName) || (!this.albumName.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.albumName);
    }
    if ((this.hasTrackNumber) || (this.trackNumber != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(4, this.trackNumber);
    }
    if ((this.hasPreviewUrl) || (!this.previewUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.previewUrl);
    }
    if (this.displayArtist != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(6, this.displayArtist);
    }
    if (this.badge != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(7, this.badge);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasName) || (!this.name.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.name);
    }
    if (this.details != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.details);
    }
    if ((this.hasAlbumName) || (!this.albumName.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.albumName);
    }
    if ((this.hasTrackNumber) || (this.trackNumber != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(4, this.trackNumber);
    }
    if ((this.hasPreviewUrl) || (!this.previewUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.previewUrl);
    }
    if (this.displayArtist != null) {
      paramCodedOutputByteBufferNano.writeMessage(6, this.displayArtist);
    }
    if (this.badge != null) {
      paramCodedOutputByteBufferNano.writeMessage(7, this.badge);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SongDetails
 * JD-Core Version:    0.7.0.1
 */