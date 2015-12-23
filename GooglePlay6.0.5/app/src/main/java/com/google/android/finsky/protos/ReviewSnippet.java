package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ReviewSnippet
  extends MessageNano
{
  private static volatile ReviewSnippet[] _emptyArray;
  public boolean hasReviewCount = false;
  public boolean hasSnippedAtBeginning = false;
  public boolean hasSnippedAtEnd = false;
  public boolean hasText = false;
  public boolean hasTip = false;
  public boolean hasTipReviewsUrl = false;
  public long reviewCount = 0L;
  public boolean snippedAtBeginning = false;
  public boolean snippedAtEnd = false;
  public String text = "";
  public String tip = "";
  public String tipReviewsUrl = "";
  
  public ReviewSnippet()
  {
    this.cachedSize = -1;
  }
  
  public static ReviewSnippet[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new ReviewSnippet[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasTip) || (!this.tip.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.tip);
    }
    if ((this.hasText) || (!this.text.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.text);
    }
    if ((this.hasReviewCount) || (this.reviewCount != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(3, this.reviewCount);
    }
    if ((this.hasTipReviewsUrl) || (!this.tipReviewsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.tipReviewsUrl);
    }
    if ((this.hasSnippedAtBeginning) || (this.snippedAtBeginning)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
    }
    if ((this.hasSnippedAtEnd) || (this.snippedAtEnd)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTip) || (!this.tip.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.tip);
    }
    if ((this.hasText) || (!this.text.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.text);
    }
    if ((this.hasReviewCount) || (this.reviewCount != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(3, this.reviewCount);
    }
    if ((this.hasTipReviewsUrl) || (!this.tipReviewsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.tipReviewsUrl);
    }
    if ((this.hasSnippedAtBeginning) || (this.snippedAtBeginning)) {
      paramCodedOutputByteBufferNano.writeBool(5, this.snippedAtBeginning);
    }
    if ((this.hasSnippedAtEnd) || (this.snippedAtEnd)) {
      paramCodedOutputByteBufferNano.writeBool(6, this.snippedAtEnd);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ReviewSnippet
 * JD-Core Version:    0.7.0.1
 */