package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class AlbumDetails
  extends MessageNano
{
  public MusicDetails details = null;
  public ArtistDetails displayArtist = null;
  public boolean hasName = false;
  public String name = "";
  
  public AlbumDetails()
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
    if (this.displayArtist != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.displayArtist);
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
    if (this.displayArtist != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.displayArtist);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.AlbumDetails
 * JD-Core Version:    0.7.0.1
 */