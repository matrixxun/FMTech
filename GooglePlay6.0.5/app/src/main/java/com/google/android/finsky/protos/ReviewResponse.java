package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ReviewResponse
  extends MessageNano
{
  public CriticReviewsResponse criticReviewsResponse = null;
  public GetReviewsResponse getResponse = null;
  public boolean hasNextPageUrl = false;
  public boolean hasSuggestionsListUrl = false;
  public String nextPageUrl = "";
  public String suggestionsListUrl = "";
  public Review updatedReview = null;
  
  public ReviewResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.getResponse != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.getResponse);
    }
    if ((this.hasNextPageUrl) || (!this.nextPageUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.nextPageUrl);
    }
    if (this.updatedReview != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.updatedReview);
    }
    if ((this.hasSuggestionsListUrl) || (!this.suggestionsListUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.suggestionsListUrl);
    }
    if (this.criticReviewsResponse != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(5, this.criticReviewsResponse);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.getResponse != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.getResponse);
    }
    if ((this.hasNextPageUrl) || (!this.nextPageUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.nextPageUrl);
    }
    if (this.updatedReview != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.updatedReview);
    }
    if ((this.hasSuggestionsListUrl) || (!this.suggestionsListUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.suggestionsListUrl);
    }
    if (this.criticReviewsResponse != null) {
      paramCodedOutputByteBufferNano.writeMessage(5, this.criticReviewsResponse);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ReviewResponse
 * JD-Core Version:    0.7.0.1
 */