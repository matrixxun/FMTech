package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface ResolveLink
{
  public static final class DirectPurchase
    extends MessageNano
  {
    public String detailsUrl = "";
    public boolean hasDetailsUrl = false;
    public boolean hasOfferType = false;
    public boolean hasParentDocid = false;
    public boolean hasPurchaseDocid = false;
    public int offerType = 1;
    public String parentDocid = "";
    public String purchaseDocid = "";
    
    public DirectPurchase()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.detailsUrl);
      }
      if ((this.hasPurchaseDocid) || (!this.purchaseDocid.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.purchaseDocid);
      }
      if ((this.hasParentDocid) || (!this.parentDocid.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.parentDocid);
      }
      if ((this.offerType != 1) || (this.hasOfferType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.offerType);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.detailsUrl);
      }
      if ((this.hasPurchaseDocid) || (!this.purchaseDocid.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.purchaseDocid);
      }
      if ((this.hasParentDocid) || (!this.parentDocid.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.parentDocid);
      }
      if ((this.offerType != 1) || (this.hasOfferType)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.offerType);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class HelpCenter
    extends MessageNano
  {
    public String contextId = "";
    public boolean hasContextId = false;
    
    public HelpCenter()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasContextId) || (!this.contextId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.contextId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasContextId) || (!this.contextId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.contextId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RedeemGiftCard
    extends MessageNano
  {
    public boolean hasPartnerPayload = false;
    public boolean hasPrefillCode = false;
    public String partnerPayload = "";
    public String prefillCode = "";
    
    public RedeemGiftCard()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasPrefillCode) || (!this.prefillCode.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.prefillCode);
      }
      if ((this.hasPartnerPayload) || (!this.partnerPayload.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.partnerPayload);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasPrefillCode) || (!this.prefillCode.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.prefillCode);
      }
      if ((this.hasPartnerPayload) || (!this.partnerPayload.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.partnerPayload);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ResolvedLink
    extends MessageNano
  {
    public int backend = 0;
    public String browseUrl = "";
    public String detailsUrl = "";
    public ResolveLink.DirectPurchase directPurchase = null;
    public Common.Docid docid = null;
    public boolean hasBackend = false;
    public boolean hasBrowseUrl = false;
    public boolean hasDetailsUrl = false;
    public boolean hasHomeUrl = false;
    public boolean hasMyAccountUrl = false;
    public boolean hasQuery = false;
    public boolean hasSearchUrl = false;
    public boolean hasServerLogsCookie = false;
    public boolean hasWishlistUrl = false;
    public ResolveLink.HelpCenter helpCenter = null;
    public String homeUrl = "";
    public String myAccountUrl = "";
    public String query = "";
    public ResolveLink.RedeemGiftCard redeemGiftCard = null;
    public String searchUrl = "";
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public String wishlistUrl = "";
    
    public ResolvedLink()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.detailsUrl);
      }
      if ((this.hasBrowseUrl) || (!this.browseUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.browseUrl);
      }
      if ((this.hasSearchUrl) || (!this.searchUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.searchUrl);
      }
      if (this.directPurchase != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.directPurchase);
      }
      if ((this.hasHomeUrl) || (!this.homeUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.homeUrl);
      }
      if (this.redeemGiftCard != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.redeemGiftCard);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(7, this.serverLogsCookie);
      }
      if (this.docid != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.docid);
      }
      if ((this.hasWishlistUrl) || (!this.wishlistUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.wishlistUrl);
      }
      if ((this.backend != 0) || (this.hasBackend)) {
        i += CodedOutputByteBufferNano.computeInt32Size(10, this.backend);
      }
      if ((this.hasQuery) || (!this.query.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(11, this.query);
      }
      if ((this.hasMyAccountUrl) || (!this.myAccountUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(12, this.myAccountUrl);
      }
      if (this.helpCenter != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(13, this.helpCenter);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.detailsUrl);
      }
      if ((this.hasBrowseUrl) || (!this.browseUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.browseUrl);
      }
      if ((this.hasSearchUrl) || (!this.searchUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.searchUrl);
      }
      if (this.directPurchase != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.directPurchase);
      }
      if ((this.hasHomeUrl) || (!this.homeUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.homeUrl);
      }
      if (this.redeemGiftCard != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.redeemGiftCard);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(7, this.serverLogsCookie);
      }
      if (this.docid != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.docid);
      }
      if ((this.hasWishlistUrl) || (!this.wishlistUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.wishlistUrl);
      }
      if ((this.backend != 0) || (this.hasBackend)) {
        paramCodedOutputByteBufferNano.writeInt32(10, this.backend);
      }
      if ((this.hasQuery) || (!this.query.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(11, this.query);
      }
      if ((this.hasMyAccountUrl) || (!this.myAccountUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(12, this.myAccountUrl);
      }
      if (this.helpCenter != null) {
        paramCodedOutputByteBufferNano.writeMessage(13, this.helpCenter);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ResolveLink
 * JD-Core Version:    0.7.0.1
 */