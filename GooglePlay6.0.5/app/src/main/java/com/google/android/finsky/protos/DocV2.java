package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public final class DocV2
  extends MessageNano
{
  private static volatile DocV2[] _emptyArray;
  public Rating.AggregateRating aggregateRating = null;
  public Annotations annotations = null;
  public FilterRules.Availability availability = null;
  public boolean availableForPreregistration = false;
  public String backendDocid = "";
  public int backendId = 0;
  public String backendUrl = "";
  public DocV2[] child = emptyArray();
  public Containers.ContainerMetadata containerMetadata = null;
  public String creator = "";
  public String descriptionHtml = "";
  public DocDetails.DocumentDetails details = null;
  public boolean detailsReusable = false;
  public String detailsUrl = "";
  public int docType = 1;
  public String docid = "";
  public boolean forceShareability = false;
  public boolean hasAvailableForPreregistration = false;
  public boolean hasBackendDocid = false;
  public boolean hasBackendId = false;
  public boolean hasBackendUrl = false;
  public boolean hasCreator = false;
  public boolean hasDescriptionHtml = false;
  public boolean hasDetailsReusable = false;
  public boolean hasDetailsUrl = false;
  public boolean hasDocType = false;
  public boolean hasDocid = false;
  public boolean hasForceShareability = false;
  public boolean hasMature = false;
  public boolean hasPromotionalDescription = false;
  public boolean hasPurchaseDetailsUrl = false;
  public boolean hasReviewsUrl = false;
  public boolean hasServerLogsCookie = false;
  public boolean hasShareUrl = false;
  public boolean hasSnippetsUrl = false;
  public boolean hasSubtitle = false;
  public boolean hasTitle = false;
  public boolean hasTranslatedDescriptionHtml = false;
  public boolean hasUseWishlistAsPrimaryAction = false;
  public Common.Image[] image = Common.Image.emptyArray();
  public boolean mature = false;
  public Common.Offer[] offer = Common.Offer.emptyArray();
  public DocDetails.ProductDetails productDetails = null;
  public String promotionalDescription = "";
  public String purchaseDetailsUrl = "";
  public String reviewsUrl = "";
  public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
  public String shareUrl = "";
  public String snippetsUrl = "";
  public String subtitle = "";
  public ReviewTip[] tip = ReviewTip.emptyArray();
  public String title = "";
  public String translatedDescriptionHtml = "";
  public boolean useWishlistAsPrimaryAction = false;
  
  public DocV2()
  {
    this.cachedSize = -1;
  }
  
  public static DocV2[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new DocV2[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasDocid) || (!this.docid.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.docid);
    }
    if ((this.hasBackendDocid) || (!this.backendDocid.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.backendDocid);
    }
    if ((this.docType != 1) || (this.hasDocType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(3, this.docType);
    }
    if ((this.backendId != 0) || (this.hasBackendId)) {
      i += CodedOutputByteBufferNano.computeInt32Size(4, this.backendId);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.title);
    }
    if ((this.hasCreator) || (!this.creator.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(6, this.creator);
    }
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(7, this.descriptionHtml);
    }
    if ((this.offer != null) && (this.offer.length > 0)) {
      for (int n = 0; n < this.offer.length; n++)
      {
        Common.Offer localOffer = this.offer[n];
        if (localOffer != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(8, localOffer);
        }
      }
    }
    if (this.availability != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(9, this.availability);
    }
    if ((this.image != null) && (this.image.length > 0)) {
      for (int m = 0; m < this.image.length; m++)
      {
        Common.Image localImage = this.image[m];
        if (localImage != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(10, localImage);
        }
      }
    }
    if ((this.child != null) && (this.child.length > 0)) {
      for (int k = 0; k < this.child.length; k++)
      {
        DocV2 localDocV2 = this.child[k];
        if (localDocV2 != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(11, localDocV2);
        }
      }
    }
    if (this.containerMetadata != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(12, this.containerMetadata);
    }
    if (this.details != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(13, this.details);
    }
    if (this.aggregateRating != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(14, this.aggregateRating);
    }
    if (this.annotations != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(15, this.annotations);
    }
    if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(16, this.detailsUrl);
    }
    if ((this.hasShareUrl) || (!this.shareUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(17, this.shareUrl);
    }
    if ((this.hasReviewsUrl) || (!this.reviewsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(18, this.reviewsUrl);
    }
    if ((this.hasBackendUrl) || (!this.backendUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(19, this.backendUrl);
    }
    if ((this.hasPurchaseDetailsUrl) || (!this.purchaseDetailsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(20, this.purchaseDetailsUrl);
    }
    if ((this.hasDetailsReusable) || (this.detailsReusable)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(21);
    }
    if ((this.hasSubtitle) || (!this.subtitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(22, this.subtitle);
    }
    if ((this.hasTranslatedDescriptionHtml) || (!this.translatedDescriptionHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(23, this.translatedDescriptionHtml);
    }
    if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
      i += CodedOutputByteBufferNano.computeBytesSize(24, this.serverLogsCookie);
    }
    if (this.productDetails != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(25, this.productDetails);
    }
    if ((this.hasMature) || (this.mature)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(26);
    }
    if ((this.hasPromotionalDescription) || (!this.promotionalDescription.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(27, this.promotionalDescription);
    }
    if ((this.hasAvailableForPreregistration) || (this.availableForPreregistration)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(29);
    }
    if ((this.tip != null) && (this.tip.length > 0)) {
      for (int j = 0; j < this.tip.length; j++)
      {
        ReviewTip localReviewTip = this.tip[j];
        if (localReviewTip != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(30, localReviewTip);
        }
      }
    }
    if ((this.hasSnippetsUrl) || (!this.snippetsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(31, this.snippetsUrl);
    }
    if ((this.hasForceShareability) || (this.forceShareability)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(32);
    }
    if ((this.hasUseWishlistAsPrimaryAction) || (this.useWishlistAsPrimaryAction)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(33);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasDocid) || (!this.docid.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.docid);
    }
    if ((this.hasBackendDocid) || (!this.backendDocid.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.backendDocid);
    }
    if ((this.docType != 1) || (this.hasDocType)) {
      paramCodedOutputByteBufferNano.writeInt32(3, this.docType);
    }
    if ((this.backendId != 0) || (this.hasBackendId)) {
      paramCodedOutputByteBufferNano.writeInt32(4, this.backendId);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.title);
    }
    if ((this.hasCreator) || (!this.creator.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(6, this.creator);
    }
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(7, this.descriptionHtml);
    }
    if ((this.offer != null) && (this.offer.length > 0)) {
      for (int m = 0; m < this.offer.length; m++)
      {
        Common.Offer localOffer = this.offer[m];
        if (localOffer != null) {
          paramCodedOutputByteBufferNano.writeMessage(8, localOffer);
        }
      }
    }
    if (this.availability != null) {
      paramCodedOutputByteBufferNano.writeMessage(9, this.availability);
    }
    if ((this.image != null) && (this.image.length > 0)) {
      for (int k = 0; k < this.image.length; k++)
      {
        Common.Image localImage = this.image[k];
        if (localImage != null) {
          paramCodedOutputByteBufferNano.writeMessage(10, localImage);
        }
      }
    }
    if ((this.child != null) && (this.child.length > 0)) {
      for (int j = 0; j < this.child.length; j++)
      {
        DocV2 localDocV2 = this.child[j];
        if (localDocV2 != null) {
          paramCodedOutputByteBufferNano.writeMessage(11, localDocV2);
        }
      }
    }
    if (this.containerMetadata != null) {
      paramCodedOutputByteBufferNano.writeMessage(12, this.containerMetadata);
    }
    if (this.details != null) {
      paramCodedOutputByteBufferNano.writeMessage(13, this.details);
    }
    if (this.aggregateRating != null) {
      paramCodedOutputByteBufferNano.writeMessage(14, this.aggregateRating);
    }
    if (this.annotations != null) {
      paramCodedOutputByteBufferNano.writeMessage(15, this.annotations);
    }
    if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(16, this.detailsUrl);
    }
    if ((this.hasShareUrl) || (!this.shareUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(17, this.shareUrl);
    }
    if ((this.hasReviewsUrl) || (!this.reviewsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(18, this.reviewsUrl);
    }
    if ((this.hasBackendUrl) || (!this.backendUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(19, this.backendUrl);
    }
    if ((this.hasPurchaseDetailsUrl) || (!this.purchaseDetailsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(20, this.purchaseDetailsUrl);
    }
    if ((this.hasDetailsReusable) || (this.detailsReusable)) {
      paramCodedOutputByteBufferNano.writeBool(21, this.detailsReusable);
    }
    if ((this.hasSubtitle) || (!this.subtitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(22, this.subtitle);
    }
    if ((this.hasTranslatedDescriptionHtml) || (!this.translatedDescriptionHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(23, this.translatedDescriptionHtml);
    }
    if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
      paramCodedOutputByteBufferNano.writeBytes(24, this.serverLogsCookie);
    }
    if (this.productDetails != null) {
      paramCodedOutputByteBufferNano.writeMessage(25, this.productDetails);
    }
    if ((this.hasMature) || (this.mature)) {
      paramCodedOutputByteBufferNano.writeBool(26, this.mature);
    }
    if ((this.hasPromotionalDescription) || (!this.promotionalDescription.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(27, this.promotionalDescription);
    }
    if ((this.hasAvailableForPreregistration) || (this.availableForPreregistration)) {
      paramCodedOutputByteBufferNano.writeBool(29, this.availableForPreregistration);
    }
    if ((this.tip != null) && (this.tip.length > 0)) {
      for (int i = 0; i < this.tip.length; i++)
      {
        ReviewTip localReviewTip = this.tip[i];
        if (localReviewTip != null) {
          paramCodedOutputByteBufferNano.writeMessage(30, localReviewTip);
        }
      }
    }
    if ((this.hasSnippetsUrl) || (!this.snippetsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(31, this.snippetsUrl);
    }
    if ((this.hasForceShareability) || (this.forceShareability)) {
      paramCodedOutputByteBufferNano.writeBool(32, this.forceShareability);
    }
    if ((this.hasUseWishlistAsPrimaryAction) || (this.useWishlistAsPrimaryAction)) {
      paramCodedOutputByteBufferNano.writeBool(33, this.useWishlistAsPrimaryAction);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.DocV2
 * JD-Core Version:    0.7.0.1
 */