package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ReviewTip
  extends MessageNano
{
  private static volatile ReviewTip[] _emptyArray;
  public boolean hasPolarity = false;
  public boolean hasReviewCount = false;
  public boolean hasText = false;
  public boolean hasTipUrl = false;
  public int polarity = 0;
  public long reviewCount = 0L;
  public String text = "";
  public String tipUrl = "";
  
  public ReviewTip()
  {
    this.cachedSize = -1;
  }
  
  public static ReviewTip[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new ReviewTip[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasTipUrl) || (!this.tipUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.tipUrl);
    }
    if ((this.hasText) || (!this.text.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.text);
    }
    if ((this.polarity != 0) || (this.hasPolarity)) {
      i += CodedOutputByteBufferNano.computeInt32Size(3, this.polarity);
    }
    if ((this.hasReviewCount) || (this.reviewCount != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(4, this.reviewCount);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTipUrl) || (!this.tipUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.tipUrl);
    }
    if ((this.hasText) || (!this.text.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.text);
    }
    if ((this.polarity != 0) || (this.hasPolarity)) {
      paramCodedOutputByteBufferNano.writeInt32(3, this.polarity);
    }
    if ((this.hasReviewCount) || (this.reviewCount != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(4, this.reviewCount);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ReviewTip
 * JD-Core Version:    0.7.0.1
 */