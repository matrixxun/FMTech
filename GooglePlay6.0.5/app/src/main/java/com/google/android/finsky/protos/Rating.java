package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public abstract interface Rating
{
  public static final class AggregateRating
    extends MessageNano
  {
    public double bayesianMeanRating = 0.0D;
    public long commentCount = 0L;
    public long fiveStarRatings = 0L;
    public long fourStarRatings = 0L;
    public boolean hasBayesianMeanRating = false;
    public boolean hasCommentCount = false;
    public boolean hasFiveStarRatings = false;
    public boolean hasFourStarRatings = false;
    public boolean hasOneStarRatings = false;
    public boolean hasRatingsCount = false;
    public boolean hasStarRating = false;
    public boolean hasThreeStarRatings = false;
    public boolean hasThumbsDownCount = false;
    public boolean hasThumbsUpCount = false;
    public boolean hasTwoStarRatings = false;
    public boolean hasType = false;
    public long oneStarRatings = 0L;
    public long ratingsCount = 0L;
    public float starRating = 0.0F;
    public long threeStarRatings = 0L;
    public long thumbsDownCount = 0L;
    public long thumbsUpCount = 0L;
    public Rating.Tip[] tip = Rating.Tip.emptyArray();
    public long twoStarRatings = 0L;
    public int type = 1;
    
    public AggregateRating()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.type != 1) || (this.hasType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
      }
      if ((this.hasStarRating) || (Float.floatToIntBits(this.starRating) != Float.floatToIntBits(0.0F))) {
        i += 4 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if ((this.hasRatingsCount) || (this.ratingsCount != 0L)) {
        i += CodedOutputByteBufferNano.computeUInt64Size(3, this.ratingsCount);
      }
      if ((this.hasOneStarRatings) || (this.oneStarRatings != 0L)) {
        i += CodedOutputByteBufferNano.computeUInt64Size(4, this.oneStarRatings);
      }
      if ((this.hasTwoStarRatings) || (this.twoStarRatings != 0L)) {
        i += CodedOutputByteBufferNano.computeUInt64Size(5, this.twoStarRatings);
      }
      if ((this.hasThreeStarRatings) || (this.threeStarRatings != 0L)) {
        i += CodedOutputByteBufferNano.computeUInt64Size(6, this.threeStarRatings);
      }
      if ((this.hasFourStarRatings) || (this.fourStarRatings != 0L)) {
        i += CodedOutputByteBufferNano.computeUInt64Size(7, this.fourStarRatings);
      }
      if ((this.hasFiveStarRatings) || (this.fiveStarRatings != 0L)) {
        i += CodedOutputByteBufferNano.computeUInt64Size(8, this.fiveStarRatings);
      }
      if ((this.hasThumbsUpCount) || (this.thumbsUpCount != 0L)) {
        i += CodedOutputByteBufferNano.computeUInt64Size(9, this.thumbsUpCount);
      }
      if ((this.hasThumbsDownCount) || (this.thumbsDownCount != 0L)) {
        i += CodedOutputByteBufferNano.computeUInt64Size(10, this.thumbsDownCount);
      }
      if ((this.hasCommentCount) || (this.commentCount != 0L)) {
        i += CodedOutputByteBufferNano.computeUInt64Size(11, this.commentCount);
      }
      if ((this.hasBayesianMeanRating) || (Double.doubleToLongBits(this.bayesianMeanRating) != Double.doubleToLongBits(0.0D))) {
        i += 8 + CodedOutputByteBufferNano.computeTagSize(12);
      }
      if ((this.tip != null) && (this.tip.length > 0)) {
        for (int j = 0; j < this.tip.length; j++)
        {
          Rating.Tip localTip = this.tip[j];
          if (localTip != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(13, localTip);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.type != 1) || (this.hasType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.type);
      }
      if ((this.hasStarRating) || (Float.floatToIntBits(this.starRating) != Float.floatToIntBits(0.0F))) {
        paramCodedOutputByteBufferNano.writeFloat(2, this.starRating);
      }
      if ((this.hasRatingsCount) || (this.ratingsCount != 0L)) {
        paramCodedOutputByteBufferNano.writeUInt64(3, this.ratingsCount);
      }
      if ((this.hasOneStarRatings) || (this.oneStarRatings != 0L)) {
        paramCodedOutputByteBufferNano.writeUInt64(4, this.oneStarRatings);
      }
      if ((this.hasTwoStarRatings) || (this.twoStarRatings != 0L)) {
        paramCodedOutputByteBufferNano.writeUInt64(5, this.twoStarRatings);
      }
      if ((this.hasThreeStarRatings) || (this.threeStarRatings != 0L)) {
        paramCodedOutputByteBufferNano.writeUInt64(6, this.threeStarRatings);
      }
      if ((this.hasFourStarRatings) || (this.fourStarRatings != 0L)) {
        paramCodedOutputByteBufferNano.writeUInt64(7, this.fourStarRatings);
      }
      if ((this.hasFiveStarRatings) || (this.fiveStarRatings != 0L)) {
        paramCodedOutputByteBufferNano.writeUInt64(8, this.fiveStarRatings);
      }
      if ((this.hasThumbsUpCount) || (this.thumbsUpCount != 0L)) {
        paramCodedOutputByteBufferNano.writeUInt64(9, this.thumbsUpCount);
      }
      if ((this.hasThumbsDownCount) || (this.thumbsDownCount != 0L)) {
        paramCodedOutputByteBufferNano.writeUInt64(10, this.thumbsDownCount);
      }
      if ((this.hasCommentCount) || (this.commentCount != 0L)) {
        paramCodedOutputByteBufferNano.writeUInt64(11, this.commentCount);
      }
      if ((this.hasBayesianMeanRating) || (Double.doubleToLongBits(this.bayesianMeanRating) != Double.doubleToLongBits(0.0D))) {
        paramCodedOutputByteBufferNano.writeDouble(12, this.bayesianMeanRating);
      }
      if ((this.tip != null) && (this.tip.length > 0)) {
        for (int i = 0; i < this.tip.length; i++)
        {
          Rating.Tip localTip = this.tip[i];
          if (localTip != null) {
            paramCodedOutputByteBufferNano.writeMessage(13, localTip);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Tip
    extends MessageNano
  {
    private static volatile Tip[] _emptyArray;
    public boolean hasLanguage = false;
    public boolean hasPolarity = false;
    public boolean hasReviewCount = false;
    public boolean hasText = false;
    public boolean hasTipId = false;
    public String language = "";
    public int polarity = 0;
    public long reviewCount = 0L;
    public String[] snippetReviewId = WireFormatNano.EMPTY_STRING_ARRAY;
    public String text = "";
    public String tipId = "";
    
    public Tip()
    {
      this.cachedSize = -1;
    }
    
    public static Tip[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Tip[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTipId) || (!this.tipId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.tipId);
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
      if ((this.hasLanguage) || (!this.language.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.language);
      }
      if ((this.snippetReviewId != null) && (this.snippetReviewId.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.snippetReviewId.length; m++)
        {
          String str = this.snippetReviewId[m];
          if (str != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + k + j * 1;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTipId) || (!this.tipId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.tipId);
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
      if ((this.hasLanguage) || (!this.language.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.language);
      }
      if ((this.snippetReviewId != null) && (this.snippetReviewId.length > 0)) {
        for (int i = 0; i < this.snippetReviewId.length; i++)
        {
          String str = this.snippetReviewId[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(6, str);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Rating
 * JD-Core Version:    0.7.0.1
 */