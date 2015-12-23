package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Annotations
  extends MessageNano
{
  public String applicableVoucherDescription = "";
  public String attributionHtml = "";
  public Badge badgeForContentRating = null;
  public Badge[] badgeForCreator = Badge.emptyArray();
  public Badge[] badgeForDoc = Badge.emptyArray();
  public DocV2 creatorDoc = null;
  public SectionMetadata[] detailsPageCluster = SectionMetadata.emptyArray();
  public BadgeContainer[] docBadgeContainer = BadgeContainer.emptyArray();
  public Badge[] featureBadge = Badge.emptyArray();
  public boolean hasApplicableVoucherDescription = false;
  public boolean hasAttributionHtml = false;
  public boolean hasOfferNote = false;
  public boolean hasPrivacyPolicyUrl = false;
  public Link link = null;
  public MyRewardDetails myRewardDetails = null;
  public MySubscriptionDetails mySubscriptionDetails = null;
  public OBSOLETE_Reason oBSOLETEReason = null;
  public String offerNote = "";
  public Warning optimalDeviceClassWarning = null;
  public OverflowLink[] overflowLink = OverflowLink.emptyArray();
  public PlusOneData plusOneData = null;
  public String privacyPolicyUrl = "";
  public PromotedDoc[] promotedDoc = PromotedDoc.emptyArray();
  public PurchaseDetails purchaseDetails = null;
  public PurchaseHistoryDetails purchaseHistoryDetails = null;
  public SectionMetadata sectionBodyOfWork = null;
  public SectionMetadata sectionCoreContent = null;
  public SectionMetadata sectionCrossSell = null;
  public SectionMetadata sectionFeaturedApps = null;
  public SectionMetadata sectionMoreBy = null;
  public SectionMetadata sectionPurchaseCrossSell = null;
  public SectionMetadata sectionPurchaseRelatedTopics = null;
  public SectionMetadata sectionRelated = null;
  public SectionMetadata sectionRelatedDocType = null;
  public SectionMetadata sectionSuggestForRating = null;
  public SelectedChild selectedChild = null;
  public Snippet snippet = null;
  public DocV2[] subscription = DocV2.emptyArray();
  public SuggestionReasons suggestionReasons = null;
  public Template template = null;
  public VideoAnnotations videoAnnotations = null;
  public VoucherInfo[] voucherInfo = VoucherInfo.emptyArray();
  public Warning[] warning = Warning.emptyArray();
  
  public Annotations()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.sectionRelated != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.sectionRelated);
    }
    if (this.sectionMoreBy != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.sectionMoreBy);
    }
    if (this.plusOneData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.plusOneData);
    }
    if ((this.warning != null) && (this.warning.length > 0)) {
      for (int i6 = 0; i6 < this.warning.length; i6++)
      {
        Warning localWarning = this.warning[i6];
        if (localWarning != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(4, localWarning);
        }
      }
    }
    if (this.sectionBodyOfWork != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(5, this.sectionBodyOfWork);
    }
    if (this.sectionCoreContent != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(6, this.sectionCoreContent);
    }
    if (this.template != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(7, this.template);
    }
    if ((this.badgeForCreator != null) && (this.badgeForCreator.length > 0)) {
      for (int i5 = 0; i5 < this.badgeForCreator.length; i5++)
      {
        Badge localBadge3 = this.badgeForCreator[i5];
        if (localBadge3 != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(8, localBadge3);
        }
      }
    }
    if ((this.badgeForDoc != null) && (this.badgeForDoc.length > 0)) {
      for (int i4 = 0; i4 < this.badgeForDoc.length; i4++)
      {
        Badge localBadge2 = this.badgeForDoc[i4];
        if (localBadge2 != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(9, localBadge2);
        }
      }
    }
    if (this.link != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(10, this.link);
    }
    if (this.sectionCrossSell != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(11, this.sectionCrossSell);
    }
    if (this.sectionRelatedDocType != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(12, this.sectionRelatedDocType);
    }
    if ((this.promotedDoc != null) && (this.promotedDoc.length > 0)) {
      for (int i3 = 0; i3 < this.promotedDoc.length; i3++)
      {
        PromotedDoc localPromotedDoc = this.promotedDoc[i3];
        if (localPromotedDoc != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(13, localPromotedDoc);
        }
      }
    }
    if ((this.hasOfferNote) || (!this.offerNote.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(14, this.offerNote);
    }
    if ((this.subscription != null) && (this.subscription.length > 0)) {
      for (int i2 = 0; i2 < this.subscription.length; i2++)
      {
        DocV2 localDocV2 = this.subscription[i2];
        if (localDocV2 != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(16, localDocV2);
        }
      }
    }
    if (this.oBSOLETEReason != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(17, this.oBSOLETEReason);
    }
    if ((this.hasPrivacyPolicyUrl) || (!this.privacyPolicyUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(18, this.privacyPolicyUrl);
    }
    if (this.suggestionReasons != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(19, this.suggestionReasons);
    }
    if (this.optimalDeviceClassWarning != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(20, this.optimalDeviceClassWarning);
    }
    if ((this.docBadgeContainer != null) && (this.docBadgeContainer.length > 0)) {
      for (int i1 = 0; i1 < this.docBadgeContainer.length; i1++)
      {
        BadgeContainer localBadgeContainer = this.docBadgeContainer[i1];
        if (localBadgeContainer != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(21, localBadgeContainer);
        }
      }
    }
    if (this.sectionSuggestForRating != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(22, this.sectionSuggestForRating);
    }
    if (this.sectionPurchaseCrossSell != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(24, this.sectionPurchaseCrossSell);
    }
    if ((this.overflowLink != null) && (this.overflowLink.length > 0)) {
      for (int n = 0; n < this.overflowLink.length; n++)
      {
        OverflowLink localOverflowLink = this.overflowLink[n];
        if (localOverflowLink != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(25, localOverflowLink);
        }
      }
    }
    if (this.creatorDoc != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(26, this.creatorDoc);
    }
    if ((this.hasAttributionHtml) || (!this.attributionHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(27, this.attributionHtml);
    }
    if (this.purchaseHistoryDetails != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(28, this.purchaseHistoryDetails);
    }
    if (this.badgeForContentRating != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(29, this.badgeForContentRating);
    }
    if ((this.voucherInfo != null) && (this.voucherInfo.length > 0)) {
      for (int m = 0; m < this.voucherInfo.length; m++)
      {
        VoucherInfo localVoucherInfo = this.voucherInfo[m];
        if (localVoucherInfo != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(30, localVoucherInfo);
        }
      }
    }
    if (this.sectionFeaturedApps != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(32, this.sectionFeaturedApps);
    }
    if ((this.hasApplicableVoucherDescription) || (!this.applicableVoucherDescription.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(33, this.applicableVoucherDescription);
    }
    if ((this.detailsPageCluster != null) && (this.detailsPageCluster.length > 0)) {
      for (int k = 0; k < this.detailsPageCluster.length; k++)
      {
        SectionMetadata localSectionMetadata = this.detailsPageCluster[k];
        if (localSectionMetadata != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(34, localSectionMetadata);
        }
      }
    }
    if (this.videoAnnotations != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(35, this.videoAnnotations);
    }
    if (this.sectionPurchaseRelatedTopics != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(36, this.sectionPurchaseRelatedTopics);
    }
    if (this.mySubscriptionDetails != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(37, this.mySubscriptionDetails);
    }
    if (this.myRewardDetails != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(38, this.myRewardDetails);
    }
    if ((this.featureBadge != null) && (this.featureBadge.length > 0)) {
      for (int j = 0; j < this.featureBadge.length; j++)
      {
        Badge localBadge1 = this.featureBadge[j];
        if (localBadge1 != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(39, localBadge1);
        }
      }
    }
    if (this.selectedChild != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(40, this.selectedChild);
    }
    if (this.purchaseDetails != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(41, this.purchaseDetails);
    }
    if (this.snippet != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(42, this.snippet);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.sectionRelated != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.sectionRelated);
    }
    if (this.sectionMoreBy != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.sectionMoreBy);
    }
    if (this.plusOneData != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.plusOneData);
    }
    if ((this.warning != null) && (this.warning.length > 0)) {
      for (int i5 = 0; i5 < this.warning.length; i5++)
      {
        Warning localWarning = this.warning[i5];
        if (localWarning != null) {
          paramCodedOutputByteBufferNano.writeMessage(4, localWarning);
        }
      }
    }
    if (this.sectionBodyOfWork != null) {
      paramCodedOutputByteBufferNano.writeMessage(5, this.sectionBodyOfWork);
    }
    if (this.sectionCoreContent != null) {
      paramCodedOutputByteBufferNano.writeMessage(6, this.sectionCoreContent);
    }
    if (this.template != null) {
      paramCodedOutputByteBufferNano.writeMessage(7, this.template);
    }
    if ((this.badgeForCreator != null) && (this.badgeForCreator.length > 0)) {
      for (int i4 = 0; i4 < this.badgeForCreator.length; i4++)
      {
        Badge localBadge3 = this.badgeForCreator[i4];
        if (localBadge3 != null) {
          paramCodedOutputByteBufferNano.writeMessage(8, localBadge3);
        }
      }
    }
    if ((this.badgeForDoc != null) && (this.badgeForDoc.length > 0)) {
      for (int i3 = 0; i3 < this.badgeForDoc.length; i3++)
      {
        Badge localBadge2 = this.badgeForDoc[i3];
        if (localBadge2 != null) {
          paramCodedOutputByteBufferNano.writeMessage(9, localBadge2);
        }
      }
    }
    if (this.link != null) {
      paramCodedOutputByteBufferNano.writeMessage(10, this.link);
    }
    if (this.sectionCrossSell != null) {
      paramCodedOutputByteBufferNano.writeMessage(11, this.sectionCrossSell);
    }
    if (this.sectionRelatedDocType != null) {
      paramCodedOutputByteBufferNano.writeMessage(12, this.sectionRelatedDocType);
    }
    if ((this.promotedDoc != null) && (this.promotedDoc.length > 0)) {
      for (int i2 = 0; i2 < this.promotedDoc.length; i2++)
      {
        PromotedDoc localPromotedDoc = this.promotedDoc[i2];
        if (localPromotedDoc != null) {
          paramCodedOutputByteBufferNano.writeMessage(13, localPromotedDoc);
        }
      }
    }
    if ((this.hasOfferNote) || (!this.offerNote.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(14, this.offerNote);
    }
    if ((this.subscription != null) && (this.subscription.length > 0)) {
      for (int i1 = 0; i1 < this.subscription.length; i1++)
      {
        DocV2 localDocV2 = this.subscription[i1];
        if (localDocV2 != null) {
          paramCodedOutputByteBufferNano.writeMessage(16, localDocV2);
        }
      }
    }
    if (this.oBSOLETEReason != null) {
      paramCodedOutputByteBufferNano.writeMessage(17, this.oBSOLETEReason);
    }
    if ((this.hasPrivacyPolicyUrl) || (!this.privacyPolicyUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(18, this.privacyPolicyUrl);
    }
    if (this.suggestionReasons != null) {
      paramCodedOutputByteBufferNano.writeMessage(19, this.suggestionReasons);
    }
    if (this.optimalDeviceClassWarning != null) {
      paramCodedOutputByteBufferNano.writeMessage(20, this.optimalDeviceClassWarning);
    }
    if ((this.docBadgeContainer != null) && (this.docBadgeContainer.length > 0)) {
      for (int n = 0; n < this.docBadgeContainer.length; n++)
      {
        BadgeContainer localBadgeContainer = this.docBadgeContainer[n];
        if (localBadgeContainer != null) {
          paramCodedOutputByteBufferNano.writeMessage(21, localBadgeContainer);
        }
      }
    }
    if (this.sectionSuggestForRating != null) {
      paramCodedOutputByteBufferNano.writeMessage(22, this.sectionSuggestForRating);
    }
    if (this.sectionPurchaseCrossSell != null) {
      paramCodedOutputByteBufferNano.writeMessage(24, this.sectionPurchaseCrossSell);
    }
    if ((this.overflowLink != null) && (this.overflowLink.length > 0)) {
      for (int m = 0; m < this.overflowLink.length; m++)
      {
        OverflowLink localOverflowLink = this.overflowLink[m];
        if (localOverflowLink != null) {
          paramCodedOutputByteBufferNano.writeMessage(25, localOverflowLink);
        }
      }
    }
    if (this.creatorDoc != null) {
      paramCodedOutputByteBufferNano.writeMessage(26, this.creatorDoc);
    }
    if ((this.hasAttributionHtml) || (!this.attributionHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(27, this.attributionHtml);
    }
    if (this.purchaseHistoryDetails != null) {
      paramCodedOutputByteBufferNano.writeMessage(28, this.purchaseHistoryDetails);
    }
    if (this.badgeForContentRating != null) {
      paramCodedOutputByteBufferNano.writeMessage(29, this.badgeForContentRating);
    }
    if ((this.voucherInfo != null) && (this.voucherInfo.length > 0)) {
      for (int k = 0; k < this.voucherInfo.length; k++)
      {
        VoucherInfo localVoucherInfo = this.voucherInfo[k];
        if (localVoucherInfo != null) {
          paramCodedOutputByteBufferNano.writeMessage(30, localVoucherInfo);
        }
      }
    }
    if (this.sectionFeaturedApps != null) {
      paramCodedOutputByteBufferNano.writeMessage(32, this.sectionFeaturedApps);
    }
    if ((this.hasApplicableVoucherDescription) || (!this.applicableVoucherDescription.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(33, this.applicableVoucherDescription);
    }
    if ((this.detailsPageCluster != null) && (this.detailsPageCluster.length > 0)) {
      for (int j = 0; j < this.detailsPageCluster.length; j++)
      {
        SectionMetadata localSectionMetadata = this.detailsPageCluster[j];
        if (localSectionMetadata != null) {
          paramCodedOutputByteBufferNano.writeMessage(34, localSectionMetadata);
        }
      }
    }
    if (this.videoAnnotations != null) {
      paramCodedOutputByteBufferNano.writeMessage(35, this.videoAnnotations);
    }
    if (this.sectionPurchaseRelatedTopics != null) {
      paramCodedOutputByteBufferNano.writeMessage(36, this.sectionPurchaseRelatedTopics);
    }
    if (this.mySubscriptionDetails != null) {
      paramCodedOutputByteBufferNano.writeMessage(37, this.mySubscriptionDetails);
    }
    if (this.myRewardDetails != null) {
      paramCodedOutputByteBufferNano.writeMessage(38, this.myRewardDetails);
    }
    if ((this.featureBadge != null) && (this.featureBadge.length > 0)) {
      for (int i = 0; i < this.featureBadge.length; i++)
      {
        Badge localBadge1 = this.featureBadge[i];
        if (localBadge1 != null) {
          paramCodedOutputByteBufferNano.writeMessage(39, localBadge1);
        }
      }
    }
    if (this.selectedChild != null) {
      paramCodedOutputByteBufferNano.writeMessage(40, this.selectedChild);
    }
    if (this.purchaseDetails != null) {
      paramCodedOutputByteBufferNano.writeMessage(41, this.purchaseDetails);
    }
    if (this.snippet != null) {
      paramCodedOutputByteBufferNano.writeMessage(42, this.snippet);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Annotations
 * JD-Core Version:    0.7.0.1
 */