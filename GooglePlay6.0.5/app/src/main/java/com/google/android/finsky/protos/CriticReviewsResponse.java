package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class CriticReviewsResponse
  extends MessageNano
{
  public Common.Image aggregateSentiment = null;
  public boolean hasPercentFavorable = false;
  public boolean hasSourceText = false;
  public boolean hasTitle = false;
  public boolean hasTotalNumReviews = false;
  public int percentFavorable = 0;
  public Review[] review = Review.emptyArray();
  public Link source = null;
  public String sourceText = "";
  public String title = "";
  public int totalNumReviews = 0;
  
  public CriticReviewsResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
    }
    if (this.aggregateSentiment != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.aggregateSentiment);
    }
    if ((this.hasTotalNumReviews) || (this.totalNumReviews != 0)) {
      i += CodedOutputByteBufferNano.computeUInt32Size(3, this.totalNumReviews);
    }
    if ((this.hasPercentFavorable) || (this.percentFavorable != 0)) {
      i += CodedOutputByteBufferNano.computeUInt32Size(4, this.percentFavorable);
    }
    if ((this.hasSourceText) || (!this.sourceText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.sourceText);
    }
    if (this.source != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(6, this.source);
    }
    if ((this.review != null) && (this.review.length > 0)) {
      for (int j = 0; j < this.review.length; j++)
      {
        Review localReview = this.review[j];
        if (localReview != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(7, localReview);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.title);
    }
    if (this.aggregateSentiment != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.aggregateSentiment);
    }
    if ((this.hasTotalNumReviews) || (this.totalNumReviews != 0)) {
      paramCodedOutputByteBufferNano.writeUInt32(3, this.totalNumReviews);
    }
    if ((this.hasPercentFavorable) || (this.percentFavorable != 0)) {
      paramCodedOutputByteBufferNano.writeUInt32(4, this.percentFavorable);
    }
    if ((this.hasSourceText) || (!this.sourceText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.sourceText);
    }
    if (this.source != null) {
      paramCodedOutputByteBufferNano.writeMessage(6, this.source);
    }
    if ((this.review != null) && (this.review.length > 0)) {
      for (int i = 0; i < this.review.length; i++)
      {
        Review localReview = this.review[i];
        if (localReview != null) {
          paramCodedOutputByteBufferNano.writeMessage(7, localReview);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.CriticReviewsResponse
 * JD-Core Version:    0.7.0.1
 */