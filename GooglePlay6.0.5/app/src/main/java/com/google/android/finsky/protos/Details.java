package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface Details
{
  public static final class BulkDetailsEntry
    extends MessageNano
  {
    private static volatile BulkDetailsEntry[] _emptyArray;
    public DocV2 doc = null;
    
    public BulkDetailsEntry()
    {
      this.cachedSize = -1;
    }
    
    public static BulkDetailsEntry[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new BulkDetailsEntry[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.doc != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.doc);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.doc != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.doc);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class BulkDetailsRequest
    extends MessageNano
  {
    public String[] docid = WireFormatNano.EMPTY_STRING_ARRAY;
    public boolean hasIncludeChildDocs = false;
    public boolean hasIncludeDetails = false;
    public boolean hasSourcePackageName = false;
    public boolean includeChildDocs = true;
    public boolean includeDetails = false;
    public int[] installedVersionCode = WireFormatNano.EMPTY_INT_ARRAY;
    public String sourcePackageName = "";
    
    public BulkDetailsRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.docid != null) && (this.docid.length > 0))
      {
        int m = 0;
        int n = 0;
        for (int i1 = 0; i1 < this.docid.length; i1++)
        {
          String str = this.docid[i1];
          if (str != null)
          {
            m++;
            n += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + n + m * 1;
      }
      if ((this.hasIncludeChildDocs) || (this.includeChildDocs != true)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if ((this.hasIncludeDetails) || (this.includeDetails)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.hasSourcePackageName) || (!this.sourcePackageName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.sourcePackageName);
      }
      if ((this.installedVersionCode != null) && (this.installedVersionCode.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.installedVersionCode.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.installedVersionCode[k]);
        }
        i = i + j + 1 * this.installedVersionCode.length;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.docid != null) && (this.docid.length > 0)) {
        for (int j = 0; j < this.docid.length; j++)
        {
          String str = this.docid[j];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(1, str);
          }
        }
      }
      if ((this.hasIncludeChildDocs) || (this.includeChildDocs != true)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.includeChildDocs);
      }
      if ((this.hasIncludeDetails) || (this.includeDetails)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.includeDetails);
      }
      if ((this.hasSourcePackageName) || (!this.sourcePackageName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.sourcePackageName);
      }
      if ((this.installedVersionCode != null) && (this.installedVersionCode.length > 0)) {
        for (int i = 0; i < this.installedVersionCode.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(7, this.installedVersionCode[i]);
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class BulkDetailsResponse
    extends MessageNano
  {
    public Details.BulkDetailsEntry[] entry = Details.BulkDetailsEntry.emptyArray();
    
    public BulkDetailsResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.entry != null) && (this.entry.length > 0)) {
        for (int j = 0; j < this.entry.length; j++)
        {
          Details.BulkDetailsEntry localBulkDetailsEntry = this.entry[j];
          if (localBulkDetailsEntry != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localBulkDetailsEntry);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.entry != null) && (this.entry.length > 0)) {
        for (int i = 0; i < this.entry.length; i++)
        {
          Details.BulkDetailsEntry localBulkDetailsEntry = this.entry[i];
          if (localBulkDetailsEntry != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localBulkDetailsEntry);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DetailsResponse
    extends MessageNano
  {
    public String analyticsCookie = "";
    public Details.DiscoveryBadge[] discoveryBadge = Details.DiscoveryBadge.emptyArray();
    public DocV1 docV1 = null;
    public DocV2 docV2 = null;
    public boolean enableReviews = true;
    public String footerHtml = "";
    public boolean hasAnalyticsCookie = false;
    public boolean hasEnableReviews = false;
    public boolean hasFooterHtml = false;
    public boolean hasServerLogsCookie = false;
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public Review userReview = null;
    
    public DetailsResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.docV1 != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.docV1);
      }
      if ((this.hasAnalyticsCookie) || (!this.analyticsCookie.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.analyticsCookie);
      }
      if (this.userReview != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.userReview);
      }
      if (this.docV2 != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.docV2);
      }
      if ((this.hasFooterHtml) || (!this.footerHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.footerHtml);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(6, this.serverLogsCookie);
      }
      if ((this.discoveryBadge != null) && (this.discoveryBadge.length > 0)) {
        for (int j = 0; j < this.discoveryBadge.length; j++)
        {
          Details.DiscoveryBadge localDiscoveryBadge = this.discoveryBadge[j];
          if (localDiscoveryBadge != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(7, localDiscoveryBadge);
          }
        }
      }
      if ((this.hasEnableReviews) || (this.enableReviews != true)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.docV1 != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.docV1);
      }
      if ((this.hasAnalyticsCookie) || (!this.analyticsCookie.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.analyticsCookie);
      }
      if (this.userReview != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.userReview);
      }
      if (this.docV2 != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.docV2);
      }
      if ((this.hasFooterHtml) || (!this.footerHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.footerHtml);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(6, this.serverLogsCookie);
      }
      if ((this.discoveryBadge != null) && (this.discoveryBadge.length > 0)) {
        for (int i = 0; i < this.discoveryBadge.length; i++)
        {
          Details.DiscoveryBadge localDiscoveryBadge = this.discoveryBadge[i];
          if (localDiscoveryBadge != null) {
            paramCodedOutputByteBufferNano.writeMessage(7, localDiscoveryBadge);
          }
        }
      }
      if ((this.hasEnableReviews) || (this.enableReviews != true)) {
        paramCodedOutputByteBufferNano.writeBool(8, this.enableReviews);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DiscoveryBadge
    extends MessageNano
  {
    private static volatile DiscoveryBadge[] _emptyArray;
    public float aggregateRating = 0.0F;
    public int backgroundColor = 0;
    public String contentDescription = "";
    public Details.DiscoveryBadgeLink discoveryBadgeLink = null;
    public String downloadCount = "";
    public String downloadUnits = "";
    public Details.FamilyAgeRangeBadge familyAgeRangeBadge = null;
    public Details.FamilyCategoryBadge familyCategoryBadge = null;
    public boolean hasAggregateRating = false;
    public boolean hasBackgroundColor = false;
    public boolean hasContentDescription = false;
    public boolean hasDownloadCount = false;
    public boolean hasDownloadUnits = false;
    public boolean hasIsPlusOne = false;
    public boolean hasServerLogsCookie = false;
    public boolean hasTitle = false;
    public boolean hasUserStarRating = false;
    public Common.Image image = null;
    public boolean isPlusOne = false;
    public Details.PlayerBadge playerBadge = null;
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public String title = "";
    public int userStarRating = 0;
    
    public DiscoveryBadge()
    {
      this.cachedSize = -1;
    }
    
    public static DiscoveryBadge[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new DiscoveryBadge[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if (this.image != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.image);
      }
      if ((this.hasBackgroundColor) || (this.backgroundColor != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.backgroundColor);
      }
      if (this.discoveryBadgeLink != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.discoveryBadgeLink);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(5, this.serverLogsCookie);
      }
      if ((this.hasIsPlusOne) || (this.isPlusOne)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if ((this.hasAggregateRating) || (Float.floatToIntBits(this.aggregateRating) != Float.floatToIntBits(0.0F))) {
        i += 4 + CodedOutputByteBufferNano.computeTagSize(7);
      }
      if ((this.hasUserStarRating) || (this.userStarRating != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.userStarRating);
      }
      if ((this.hasDownloadCount) || (!this.downloadCount.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.downloadCount);
      }
      if ((this.hasDownloadUnits) || (!this.downloadUnits.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.downloadUnits);
      }
      if ((this.hasContentDescription) || (!this.contentDescription.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(11, this.contentDescription);
      }
      if (this.playerBadge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(12, this.playerBadge);
      }
      if (this.familyAgeRangeBadge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(13, this.familyAgeRangeBadge);
      }
      if (this.familyCategoryBadge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(14, this.familyCategoryBadge);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if (this.image != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.image);
      }
      if ((this.hasBackgroundColor) || (this.backgroundColor != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.backgroundColor);
      }
      if (this.discoveryBadgeLink != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.discoveryBadgeLink);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(5, this.serverLogsCookie);
      }
      if ((this.hasIsPlusOne) || (this.isPlusOne)) {
        paramCodedOutputByteBufferNano.writeBool(6, this.isPlusOne);
      }
      if ((this.hasAggregateRating) || (Float.floatToIntBits(this.aggregateRating) != Float.floatToIntBits(0.0F))) {
        paramCodedOutputByteBufferNano.writeFloat(7, this.aggregateRating);
      }
      if ((this.hasUserStarRating) || (this.userStarRating != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.userStarRating);
      }
      if ((this.hasDownloadCount) || (!this.downloadCount.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.downloadCount);
      }
      if ((this.hasDownloadUnits) || (!this.downloadUnits.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(10, this.downloadUnits);
      }
      if ((this.hasContentDescription) || (!this.contentDescription.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(11, this.contentDescription);
      }
      if (this.playerBadge != null) {
        paramCodedOutputByteBufferNano.writeMessage(12, this.playerBadge);
      }
      if (this.familyAgeRangeBadge != null) {
        paramCodedOutputByteBufferNano.writeMessage(13, this.familyAgeRangeBadge);
      }
      if (this.familyCategoryBadge != null) {
        paramCodedOutputByteBufferNano.writeMessage(14, this.familyCategoryBadge);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DiscoveryBadgeLink
    extends MessageNano
  {
    public String criticReviewsUrl = "";
    public boolean hasCriticReviewsUrl = false;
    public boolean hasUserReviewsUrl = false;
    public Link link = null;
    public String userReviewsUrl = "";
    
    public DiscoveryBadgeLink()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.link != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.link);
      }
      if ((this.hasUserReviewsUrl) || (!this.userReviewsUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.userReviewsUrl);
      }
      if ((this.hasCriticReviewsUrl) || (!this.criticReviewsUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.criticReviewsUrl);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.link != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.link);
      }
      if ((this.hasUserReviewsUrl) || (!this.userReviewsUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.userReviewsUrl);
      }
      if ((this.hasCriticReviewsUrl) || (!this.criticReviewsUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.criticReviewsUrl);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FamilyAgeRangeBadge
    extends MessageNano
  {
    public FamilyAgeRangeBadge()
    {
      this.cachedSize = -1;
    }
  }
  
  public static final class FamilyCategoryBadge
    extends MessageNano
  {
    public FamilyCategoryBadge()
    {
      this.cachedSize = -1;
    }
  }
  
  public static final class PlayerBadge
    extends MessageNano
  {
    public Common.Image overlayIcon = null;
    
    public PlayerBadge()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.overlayIcon != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.overlayIcon);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.overlayIcon != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.overlayIcon);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Details
 * JD-Core Version:    0.7.0.1
 */