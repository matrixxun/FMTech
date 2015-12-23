package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class GetReviewsResponse
  extends MessageNano
{
  public boolean hasMatchingCount = false;
  public long matchingCount = 0L;
  public Review[] review = Review.emptyArray();
  public ReviewTip tip = null;
  
  public GetReviewsResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.review != null) && (this.review.length > 0)) {
      for (int j = 0; j < this.review.length; j++)
      {
        Review localReview = this.review[j];
        if (localReview != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localReview);
        }
      }
    }
    if ((this.hasMatchingCount) || (this.matchingCount != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(2, this.matchingCount);
    }
    if (this.tip != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.tip);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.review != null) && (this.review.length > 0)) {
      for (int i = 0; i < this.review.length; i++)
      {
        Review localReview = this.review[i];
        if (localReview != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localReview);
        }
      }
    }
    if ((this.hasMatchingCount) || (this.matchingCount != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(2, this.matchingCount);
    }
    if (this.tip != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.tip);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.GetReviewsResponse
 * JD-Core Version:    0.7.0.1
 */