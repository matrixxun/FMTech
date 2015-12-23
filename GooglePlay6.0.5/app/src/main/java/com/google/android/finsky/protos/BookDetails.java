package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class BookDetails
  extends MessageNano
{
  public String aboutTheAuthor = "";
  public int bookType = 0;
  public boolean hasAboutTheAuthor = false;
  public boolean hasBookType = false;
  public boolean hasIsbn = false;
  public boolean hasNumberOfPages = false;
  public boolean hasPublicationDate = false;
  public boolean hasPublisher = false;
  public boolean hasSeriesLine = false;
  public String isbn = "";
  public int numberOfPages = 0;
  public String publicationDate = "";
  public String publisher = "";
  public String seriesLine = "";
  
  public BookDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasPublisher) || (!this.publisher.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.publisher);
    }
    if ((this.hasPublicationDate) || (!this.publicationDate.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.publicationDate);
    }
    if ((this.hasIsbn) || (!this.isbn.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(6, this.isbn);
    }
    if ((this.hasNumberOfPages) || (this.numberOfPages != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(7, this.numberOfPages);
    }
    if ((this.hasAboutTheAuthor) || (!this.aboutTheAuthor.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(17, this.aboutTheAuthor);
    }
    if ((this.bookType != 0) || (this.hasBookType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(27, this.bookType);
    }
    if ((this.hasSeriesLine) || (!this.seriesLine.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(28, this.seriesLine);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasPublisher) || (!this.publisher.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.publisher);
    }
    if ((this.hasPublicationDate) || (!this.publicationDate.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.publicationDate);
    }
    if ((this.hasIsbn) || (!this.isbn.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(6, this.isbn);
    }
    if ((this.hasNumberOfPages) || (this.numberOfPages != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(7, this.numberOfPages);
    }
    if ((this.hasAboutTheAuthor) || (!this.aboutTheAuthor.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(17, this.aboutTheAuthor);
    }
    if ((this.bookType != 0) || (this.hasBookType)) {
      paramCodedOutputByteBufferNano.writeInt32(27, this.bookType);
    }
    if ((this.hasSeriesLine) || (!this.seriesLine.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(28, this.seriesLine);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.BookDetails
 * JD-Core Version:    0.7.0.1
 */