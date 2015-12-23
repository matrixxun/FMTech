package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class MusicDetails
  extends MessageNano
{
  public ArtistDetails[] artist = ArtistDetails.emptyArray();
  public int censoring = 0;
  public int durationSec = 0;
  public String[] genre = WireFormatNano.EMPTY_STRING_ARRAY;
  public boolean hasCensoring = false;
  public boolean hasDurationSec = false;
  public boolean hasLabel = false;
  public boolean hasOriginalReleaseDate = false;
  public boolean hasReleaseDate = false;
  public String label = "";
  public String originalReleaseDate = "";
  public String releaseDate = "";
  public int[] releaseType = WireFormatNano.EMPTY_INT_ARRAY;
  
  public MusicDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.censoring != 0) || (this.hasCensoring)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.censoring);
    }
    if ((this.hasDurationSec) || (this.durationSec != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.durationSec);
    }
    if ((this.hasOriginalReleaseDate) || (!this.originalReleaseDate.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.originalReleaseDate);
    }
    if ((this.hasLabel) || (!this.label.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.label);
    }
    if ((this.artist != null) && (this.artist.length > 0)) {
      for (int i2 = 0; i2 < this.artist.length; i2++)
      {
        ArtistDetails localArtistDetails = this.artist[i2];
        if (localArtistDetails != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(5, localArtistDetails);
        }
      }
    }
    if ((this.genre != null) && (this.genre.length > 0))
    {
      int m = 0;
      int n = 0;
      for (int i1 = 0; i1 < this.genre.length; i1++)
      {
        String str = this.genre[i1];
        if (str != null)
        {
          m++;
          n += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
        }
      }
      i = i + n + m * 1;
    }
    if ((this.hasReleaseDate) || (!this.releaseDate.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(7, this.releaseDate);
    }
    if ((this.releaseType != null) && (this.releaseType.length > 0))
    {
      int j = 0;
      for (int k = 0; k < this.releaseType.length; k++) {
        j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.releaseType[k]);
      }
      i = i + j + 1 * this.releaseType.length;
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.censoring != 0) || (this.hasCensoring)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.censoring);
    }
    if ((this.hasDurationSec) || (this.durationSec != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.durationSec);
    }
    if ((this.hasOriginalReleaseDate) || (!this.originalReleaseDate.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.originalReleaseDate);
    }
    if ((this.hasLabel) || (!this.label.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.label);
    }
    if ((this.artist != null) && (this.artist.length > 0)) {
      for (int k = 0; k < this.artist.length; k++)
      {
        ArtistDetails localArtistDetails = this.artist[k];
        if (localArtistDetails != null) {
          paramCodedOutputByteBufferNano.writeMessage(5, localArtistDetails);
        }
      }
    }
    if ((this.genre != null) && (this.genre.length > 0)) {
      for (int j = 0; j < this.genre.length; j++)
      {
        String str = this.genre[j];
        if (str != null) {
          paramCodedOutputByteBufferNano.writeString(6, str);
        }
      }
    }
    if ((this.hasReleaseDate) || (!this.releaseDate.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(7, this.releaseDate);
    }
    if ((this.releaseType != null) && (this.releaseType.length > 0)) {
      for (int i = 0; i < this.releaseType.length; i++) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.releaseType[i]);
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.MusicDetails
 * JD-Core Version:    0.7.0.1
 */