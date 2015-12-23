package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class DocV1
  extends MessageNano
{
  private static volatile DocV1[] _emptyArray;
  public String creator = "";
  public String descriptionHtml = "";
  public DocDetails.DocumentDetails details = null;
  public String detailsUrl = "";
  public String docid = "";
  public OBSOLETE_FinskyDoc finskyDoc = null;
  public boolean hasCreator = false;
  public boolean hasDescriptionHtml = false;
  public boolean hasDetailsUrl = false;
  public boolean hasDocid = false;
  public boolean hasMoreByBrowseUrl = false;
  public boolean hasMoreByHeader = false;
  public boolean hasMoreByListUrl = false;
  public boolean hasRelatedBrowseUrl = false;
  public boolean hasRelatedHeader = false;
  public boolean hasRelatedListUrl = false;
  public boolean hasReviewsUrl = false;
  public boolean hasShareUrl = false;
  public boolean hasTitle = false;
  public boolean hasWarningMessage = false;
  public String moreByBrowseUrl = "";
  public String moreByHeader = "";
  public String moreByListUrl = "";
  public PlusOneData plusOneData = null;
  public String relatedBrowseUrl = "";
  public String relatedHeader = "";
  public String relatedListUrl = "";
  public String reviewsUrl = "";
  public String shareUrl = "";
  public String title = "";
  public String warningMessage = "";
  
  public DocV1()
  {
    this.cachedSize = -1;
  }
  
  public static DocV1[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new DocV1[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.finskyDoc != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.finskyDoc);
    }
    if ((this.hasDocid) || (!this.docid.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.docid);
    }
    if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.detailsUrl);
    }
    if ((this.hasReviewsUrl) || (!this.reviewsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.reviewsUrl);
    }
    if ((this.hasRelatedListUrl) || (!this.relatedListUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.relatedListUrl);
    }
    if ((this.hasMoreByListUrl) || (!this.moreByListUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(6, this.moreByListUrl);
    }
    if ((this.hasShareUrl) || (!this.shareUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(7, this.shareUrl);
    }
    if ((this.hasCreator) || (!this.creator.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(8, this.creator);
    }
    if (this.details != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(9, this.details);
    }
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(10, this.descriptionHtml);
    }
    if ((this.hasRelatedBrowseUrl) || (!this.relatedBrowseUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(11, this.relatedBrowseUrl);
    }
    if ((this.hasMoreByBrowseUrl) || (!this.moreByBrowseUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(12, this.moreByBrowseUrl);
    }
    if ((this.hasRelatedHeader) || (!this.relatedHeader.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(13, this.relatedHeader);
    }
    if ((this.hasMoreByHeader) || (!this.moreByHeader.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(14, this.moreByHeader);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(15, this.title);
    }
    if (this.plusOneData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(16, this.plusOneData);
    }
    if ((this.hasWarningMessage) || (!this.warningMessage.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(17, this.warningMessage);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.finskyDoc != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.finskyDoc);
    }
    if ((this.hasDocid) || (!this.docid.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.docid);
    }
    if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.detailsUrl);
    }
    if ((this.hasReviewsUrl) || (!this.reviewsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.reviewsUrl);
    }
    if ((this.hasRelatedListUrl) || (!this.relatedListUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.relatedListUrl);
    }
    if ((this.hasMoreByListUrl) || (!this.moreByListUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(6, this.moreByListUrl);
    }
    if ((this.hasShareUrl) || (!this.shareUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(7, this.shareUrl);
    }
    if ((this.hasCreator) || (!this.creator.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(8, this.creator);
    }
    if (this.details != null) {
      paramCodedOutputByteBufferNano.writeMessage(9, this.details);
    }
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(10, this.descriptionHtml);
    }
    if ((this.hasRelatedBrowseUrl) || (!this.relatedBrowseUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(11, this.relatedBrowseUrl);
    }
    if ((this.hasMoreByBrowseUrl) || (!this.moreByBrowseUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(12, this.moreByBrowseUrl);
    }
    if ((this.hasRelatedHeader) || (!this.relatedHeader.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(13, this.relatedHeader);
    }
    if ((this.hasMoreByHeader) || (!this.moreByHeader.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(14, this.moreByHeader);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(15, this.title);
    }
    if (this.plusOneData != null) {
      paramCodedOutputByteBufferNano.writeMessage(16, this.plusOneData);
    }
    if ((this.hasWarningMessage) || (!this.warningMessage.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(17, this.warningMessage);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.DocV1
 * JD-Core Version:    0.7.0.1
 */