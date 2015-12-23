package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ReviewSnippetsResponse
  extends MessageNano
{
  public ReviewSnippet[] reviewSnippet = ReviewSnippet.emptyArray();
  
  public ReviewSnippetsResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.reviewSnippet != null) && (this.reviewSnippet.length > 0)) {
      for (int j = 0; j < this.reviewSnippet.length; j++)
      {
        ReviewSnippet localReviewSnippet = this.reviewSnippet[j];
        if (localReviewSnippet != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localReviewSnippet);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.reviewSnippet != null) && (this.reviewSnippet.length > 0)) {
      for (int i = 0; i < this.reviewSnippet.length; i++)
      {
        ReviewSnippet localReviewSnippet = this.reviewSnippet[i];
        if (localReviewSnippet != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localReviewSnippet);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ReviewSnippetsResponse
 * JD-Core Version:    0.7.0.1
 */