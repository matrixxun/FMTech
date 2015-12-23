package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class VideoDetails
  extends MessageNano
{
  public String[] audioLanguage = WireFormatNano.EMPTY_STRING_ARRAY;
  public String[] captionLanguage = WireFormatNano.EMPTY_STRING_ARRAY;
  public String contentRating = "";
  public VideoCredit[] credit = VideoCredit.emptyArray();
  public long dislikes = 0L;
  public String duration = "";
  public String[] genre = WireFormatNano.EMPTY_STRING_ARRAY;
  public boolean hasContentRating = false;
  public boolean hasDislikes = false;
  public boolean hasDuration = false;
  public boolean hasLikes = false;
  public boolean hasReleaseDate = false;
  public long likes = 0L;
  public String releaseDate = "";
  public VideoRentalTerm[] rentalTerm = VideoRentalTerm.emptyArray();
  public Trailer[] trailer = Trailer.emptyArray();
  
  public VideoDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.credit != null) && (this.credit.length > 0)) {
      for (int i8 = 0; i8 < this.credit.length; i8++)
      {
        VideoCredit localVideoCredit = this.credit[i8];
        if (localVideoCredit != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localVideoCredit);
        }
      }
    }
    if ((this.hasDuration) || (!this.duration.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.duration);
    }
    if ((this.hasReleaseDate) || (!this.releaseDate.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.releaseDate);
    }
    if ((this.hasContentRating) || (!this.contentRating.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.contentRating);
    }
    if ((this.hasLikes) || (this.likes != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(5, this.likes);
    }
    if ((this.hasDislikes) || (this.dislikes != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(6, this.dislikes);
    }
    if ((this.genre != null) && (this.genre.length > 0))
    {
      int i5 = 0;
      int i6 = 0;
      for (int i7 = 0; i7 < this.genre.length; i7++)
      {
        String str3 = this.genre[i7];
        if (str3 != null)
        {
          i5++;
          i6 += CodedOutputByteBufferNano.computeStringSizeNoTag(str3);
        }
      }
      i = i + i6 + i5 * 1;
    }
    if ((this.trailer != null) && (this.trailer.length > 0)) {
      for (int i4 = 0; i4 < this.trailer.length; i4++)
      {
        Trailer localTrailer = this.trailer[i4];
        if (localTrailer != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(8, localTrailer);
        }
      }
    }
    if ((this.rentalTerm != null) && (this.rentalTerm.length > 0)) {
      for (int i3 = 0; i3 < this.rentalTerm.length; i3++)
      {
        VideoRentalTerm localVideoRentalTerm = this.rentalTerm[i3];
        if (localVideoRentalTerm != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(9, localVideoRentalTerm);
        }
      }
    }
    if ((this.audioLanguage != null) && (this.audioLanguage.length > 0))
    {
      int n = 0;
      int i1 = 0;
      for (int i2 = 0; i2 < this.audioLanguage.length; i2++)
      {
        String str2 = this.audioLanguage[i2];
        if (str2 != null)
        {
          n++;
          i1 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
        }
      }
      i = i + i1 + n * 1;
    }
    if ((this.captionLanguage != null) && (this.captionLanguage.length > 0))
    {
      int j = 0;
      int k = 0;
      for (int m = 0; m < this.captionLanguage.length; m++)
      {
        String str1 = this.captionLanguage[m];
        if (str1 != null)
        {
          j++;
          k += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
        }
      }
      i = i + k + j * 1;
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.credit != null) && (this.credit.length > 0)) {
      for (int i1 = 0; i1 < this.credit.length; i1++)
      {
        VideoCredit localVideoCredit = this.credit[i1];
        if (localVideoCredit != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localVideoCredit);
        }
      }
    }
    if ((this.hasDuration) || (!this.duration.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.duration);
    }
    if ((this.hasReleaseDate) || (!this.releaseDate.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.releaseDate);
    }
    if ((this.hasContentRating) || (!this.contentRating.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.contentRating);
    }
    if ((this.hasLikes) || (this.likes != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(5, this.likes);
    }
    if ((this.hasDislikes) || (this.dislikes != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(6, this.dislikes);
    }
    if ((this.genre != null) && (this.genre.length > 0)) {
      for (int n = 0; n < this.genre.length; n++)
      {
        String str3 = this.genre[n];
        if (str3 != null) {
          paramCodedOutputByteBufferNano.writeString(7, str3);
        }
      }
    }
    if ((this.trailer != null) && (this.trailer.length > 0)) {
      for (int m = 0; m < this.trailer.length; m++)
      {
        Trailer localTrailer = this.trailer[m];
        if (localTrailer != null) {
          paramCodedOutputByteBufferNano.writeMessage(8, localTrailer);
        }
      }
    }
    if ((this.rentalTerm != null) && (this.rentalTerm.length > 0)) {
      for (int k = 0; k < this.rentalTerm.length; k++)
      {
        VideoRentalTerm localVideoRentalTerm = this.rentalTerm[k];
        if (localVideoRentalTerm != null) {
          paramCodedOutputByteBufferNano.writeMessage(9, localVideoRentalTerm);
        }
      }
    }
    if ((this.audioLanguage != null) && (this.audioLanguage.length > 0)) {
      for (int j = 0; j < this.audioLanguage.length; j++)
      {
        String str2 = this.audioLanguage[j];
        if (str2 != null) {
          paramCodedOutputByteBufferNano.writeString(10, str2);
        }
      }
    }
    if ((this.captionLanguage != null) && (this.captionLanguage.length > 0)) {
      for (int i = 0; i < this.captionLanguage.length; i++)
      {
        String str1 = this.captionLanguage[i];
        if (str1 != null) {
          paramCodedOutputByteBufferNano.writeString(11, str1);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.VideoDetails
 * JD-Core Version:    0.7.0.1
 */