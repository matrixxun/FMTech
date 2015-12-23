package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Review
  extends MessageNano
{
  private static volatile Review[] _emptyArray;
  public DocV2 author = null;
  public String authorName = "";
  public String comment = "";
  public String commentId = "";
  public String deviceName = "";
  public String documentVersion = "";
  public boolean hasAuthorName = false;
  public boolean hasComment = false;
  public boolean hasCommentId = false;
  public boolean hasDeviceName = false;
  public boolean hasDocumentVersion = false;
  public boolean hasHelpfulCount = false;
  public boolean hasReplyText = false;
  public boolean hasReplyTimestampMsec = false;
  public boolean hasSource = false;
  public boolean hasStarRating = false;
  public boolean hasThumbsUpCount = false;
  public boolean hasTimestampMsec = false;
  public boolean hasTitle = false;
  public boolean hasUrl = false;
  public int helpfulCount = 0;
  public OBSOLETE_PlusProfile oBSOLETEPlusProfile = null;
  public String replyText = "";
  public long replyTimestampMsec = 0L;
  public Common.Image sentiment = null;
  public String source = "";
  public int starRating = 0;
  public long thumbsUpCount = 0L;
  public long timestampMsec = 0L;
  public String title = "";
  public String url = "";
  
  public Review()
  {
    this.cachedSize = -1;
  }
  
  public static Review[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new Review[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasAuthorName) || (!this.authorName.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.authorName);
    }
    if ((this.hasUrl) || (!this.url.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.url);
    }
    if ((this.hasSource) || (!this.source.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.source);
    }
    if ((this.hasDocumentVersion) || (!this.documentVersion.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.documentVersion);
    }
    if ((this.hasTimestampMsec) || (this.timestampMsec != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(5, this.timestampMsec);
    }
    if ((this.hasStarRating) || (this.starRating != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(6, this.starRating);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(7, this.title);
    }
    if ((this.hasComment) || (!this.comment.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(8, this.comment);
    }
    if ((this.hasCommentId) || (!this.commentId.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(9, this.commentId);
    }
    if ((this.hasDeviceName) || (!this.deviceName.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(19, this.deviceName);
    }
    if ((this.hasReplyText) || (!this.replyText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(29, this.replyText);
    }
    if ((this.hasReplyTimestampMsec) || (this.replyTimestampMsec != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(30, this.replyTimestampMsec);
    }
    if (this.oBSOLETEPlusProfile != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(31, this.oBSOLETEPlusProfile);
    }
    if (this.author != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(33, this.author);
    }
    if (this.sentiment != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(34, this.sentiment);
    }
    if ((this.hasHelpfulCount) || (this.helpfulCount != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(35, this.helpfulCount);
    }
    if ((this.hasThumbsUpCount) || (this.thumbsUpCount != 0L)) {
      i += CodedOutputByteBufferNano.computeUInt64Size(38, this.thumbsUpCount);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasAuthorName) || (!this.authorName.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.authorName);
    }
    if ((this.hasUrl) || (!this.url.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.url);
    }
    if ((this.hasSource) || (!this.source.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.source);
    }
    if ((this.hasDocumentVersion) || (!this.documentVersion.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.documentVersion);
    }
    if ((this.hasTimestampMsec) || (this.timestampMsec != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(5, this.timestampMsec);
    }
    if ((this.hasStarRating) || (this.starRating != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(6, this.starRating);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(7, this.title);
    }
    if ((this.hasComment) || (!this.comment.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(8, this.comment);
    }
    if ((this.hasCommentId) || (!this.commentId.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(9, this.commentId);
    }
    if ((this.hasDeviceName) || (!this.deviceName.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(19, this.deviceName);
    }
    if ((this.hasReplyText) || (!this.replyText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(29, this.replyText);
    }
    if ((this.hasReplyTimestampMsec) || (this.replyTimestampMsec != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(30, this.replyTimestampMsec);
    }
    if (this.oBSOLETEPlusProfile != null) {
      paramCodedOutputByteBufferNano.writeMessage(31, this.oBSOLETEPlusProfile);
    }
    if (this.author != null) {
      paramCodedOutputByteBufferNano.writeMessage(33, this.author);
    }
    if (this.sentiment != null) {
      paramCodedOutputByteBufferNano.writeMessage(34, this.sentiment);
    }
    if ((this.hasHelpfulCount) || (this.helpfulCount != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(35, this.helpfulCount);
    }
    if ((this.hasThumbsUpCount) || (this.thumbsUpCount != 0L)) {
      paramCodedOutputByteBufferNano.writeUInt64(38, this.thumbsUpCount);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Review
 * JD-Core Version:    0.7.0.1
 */