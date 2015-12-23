package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface Ownership
{
  public static final class OwnershipInfo
    extends MessageNano
  {
    public boolean autoRenewing = false;
    public boolean bonus = false;
    public Common.Docid bundleDocid = null;
    public Common.SignedData developerPurchaseInfo = null;
    public GroupLicense.GroupLicenseInfo groupLicenseInfo = null;
    public boolean hasAutoRenewing = false;
    public boolean hasBonus = false;
    public boolean hasHidden = false;
    public boolean hasInitiationTimestampMsec = false;
    public boolean hasLibraryExpirationTimestampMsec = false;
    public boolean hasPostDeliveryRefundWindowMsec = false;
    public boolean hasPreordered = false;
    public boolean hasQuantity = false;
    public boolean hasRefundTimeoutTimestampMsec = false;
    public boolean hasStoredValidUntilTimestampMsec = false;
    public boolean hasValidUntilTimestampMsec = false;
    public boolean hidden = false;
    public long initiationTimestampMsec = 0L;
    public long libraryExpirationTimestampMsec = 0L;
    public LibraryVoucher libraryVoucher = null;
    public Common.LicensedDocumentInfo licensedDocumentInfo = null;
    public long postDeliveryRefundWindowMsec = 0L;
    public boolean preordered = false;
    public int quantity = 1;
    public long refundTimeoutTimestampMsec = 0L;
    public Common.RentalTerms rentalTerms = null;
    public long storedValidUntilTimestampMsec = 0L;
    public long validUntilTimestampMsec = 0L;
    
    public OwnershipInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasInitiationTimestampMsec) || (this.initiationTimestampMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.initiationTimestampMsec);
      }
      if ((this.hasValidUntilTimestampMsec) || (this.validUntilTimestampMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.validUntilTimestampMsec);
      }
      if ((this.hasAutoRenewing) || (this.autoRenewing)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.hasRefundTimeoutTimestampMsec) || (this.refundTimeoutTimestampMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(4, this.refundTimeoutTimestampMsec);
      }
      if ((this.hasPostDeliveryRefundWindowMsec) || (this.postDeliveryRefundWindowMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(5, this.postDeliveryRefundWindowMsec);
      }
      if (this.developerPurchaseInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.developerPurchaseInfo);
      }
      if ((this.hasPreordered) || (this.preordered)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
      }
      if ((this.hasHidden) || (this.hidden)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
      }
      if (this.rentalTerms != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.rentalTerms);
      }
      if (this.groupLicenseInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.groupLicenseInfo);
      }
      if (this.licensedDocumentInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.licensedDocumentInfo);
      }
      if ((this.hasQuantity) || (this.quantity != 1)) {
        i += CodedOutputByteBufferNano.computeInt32Size(12, this.quantity);
      }
      if ((this.hasLibraryExpirationTimestampMsec) || (this.libraryExpirationTimestampMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(14, this.libraryExpirationTimestampMsec);
      }
      if (this.libraryVoucher != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(15, this.libraryVoucher);
      }
      if (this.bundleDocid != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(16, this.bundleDocid);
      }
      if ((this.hasBonus) || (this.bonus)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(17);
      }
      if ((this.hasStoredValidUntilTimestampMsec) || (this.storedValidUntilTimestampMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(18, this.storedValidUntilTimestampMsec);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasInitiationTimestampMsec) || (this.initiationTimestampMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.initiationTimestampMsec);
      }
      if ((this.hasValidUntilTimestampMsec) || (this.validUntilTimestampMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.validUntilTimestampMsec);
      }
      if ((this.hasAutoRenewing) || (this.autoRenewing)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.autoRenewing);
      }
      if ((this.hasRefundTimeoutTimestampMsec) || (this.refundTimeoutTimestampMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(4, this.refundTimeoutTimestampMsec);
      }
      if ((this.hasPostDeliveryRefundWindowMsec) || (this.postDeliveryRefundWindowMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(5, this.postDeliveryRefundWindowMsec);
      }
      if (this.developerPurchaseInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.developerPurchaseInfo);
      }
      if ((this.hasPreordered) || (this.preordered)) {
        paramCodedOutputByteBufferNano.writeBool(7, this.preordered);
      }
      if ((this.hasHidden) || (this.hidden)) {
        paramCodedOutputByteBufferNano.writeBool(8, this.hidden);
      }
      if (this.rentalTerms != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.rentalTerms);
      }
      if (this.groupLicenseInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.groupLicenseInfo);
      }
      if (this.licensedDocumentInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.licensedDocumentInfo);
      }
      if ((this.hasQuantity) || (this.quantity != 1)) {
        paramCodedOutputByteBufferNano.writeInt32(12, this.quantity);
      }
      if ((this.hasLibraryExpirationTimestampMsec) || (this.libraryExpirationTimestampMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(14, this.libraryExpirationTimestampMsec);
      }
      if (this.libraryVoucher != null) {
        paramCodedOutputByteBufferNano.writeMessage(15, this.libraryVoucher);
      }
      if (this.bundleDocid != null) {
        paramCodedOutputByteBufferNano.writeMessage(16, this.bundleDocid);
      }
      if ((this.hasBonus) || (this.bonus)) {
        paramCodedOutputByteBufferNano.writeBool(17, this.bonus);
      }
      if ((this.hasStoredValidUntilTimestampMsec) || (this.storedValidUntilTimestampMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(18, this.storedValidUntilTimestampMsec);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Ownership
 * JD-Core Version:    0.7.0.1
 */