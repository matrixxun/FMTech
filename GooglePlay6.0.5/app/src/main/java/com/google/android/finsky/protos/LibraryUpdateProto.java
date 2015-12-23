package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface LibraryUpdateProto
{
  public static final class ClientLibraryState
    extends MessageNano
  {
    private static volatile ClientLibraryState[] _emptyArray;
    public int corpus = 0;
    public boolean hasCorpus = false;
    public boolean hasHashCodeSum = false;
    public boolean hasLibraryId = false;
    public boolean hasLibrarySize = false;
    public boolean hasServerToken = false;
    public long hashCodeSum = 0L;
    public String libraryId = "";
    public int librarySize = 0;
    public byte[] serverToken = WireFormatNano.EMPTY_BYTES;
    
    public ClientLibraryState()
    {
      this.cachedSize = -1;
    }
    
    public static ClientLibraryState[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ClientLibraryState[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.corpus != 0) || (this.hasCorpus)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.corpus);
      }
      if ((this.hasServerToken) || (!Arrays.equals(this.serverToken, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.serverToken);
      }
      if ((this.hasHashCodeSum) || (this.hashCodeSum != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.hashCodeSum);
      }
      if ((this.hasLibrarySize) || (this.librarySize != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.librarySize);
      }
      if ((this.hasLibraryId) || (!this.libraryId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.libraryId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.corpus != 0) || (this.hasCorpus)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.corpus);
      }
      if ((this.hasServerToken) || (!Arrays.equals(this.serverToken, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.serverToken);
      }
      if ((this.hasHashCodeSum) || (this.hashCodeSum != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.hashCodeSum);
      }
      if ((this.hasLibrarySize) || (this.librarySize != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.librarySize);
      }
      if ((this.hasLibraryId) || (!this.libraryId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.libraryId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LibraryAppDetails
    extends MessageNano
  {
    public String[] certificateHash = WireFormatNano.EMPTY_STRING_ARRAY;
    public boolean hasIsOwnedViaLicense = false;
    public boolean hasPostDeliveryRefundWindowMsec = false;
    public boolean hasRefundTimeoutTimestampMsec = false;
    public boolean isOwnedViaLicense = false;
    public long postDeliveryRefundWindowMsec = 0L;
    public long refundTimeoutTimestampMsec = 0L;
    
    public LibraryAppDetails()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.certificateHash != null) && (this.certificateHash.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.certificateHash.length; m++)
        {
          String str = this.certificateHash[m];
          if (str != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + k + j * 1;
      }
      if ((this.hasRefundTimeoutTimestampMsec) || (this.refundTimeoutTimestampMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.refundTimeoutTimestampMsec);
      }
      if ((this.hasPostDeliveryRefundWindowMsec) || (this.postDeliveryRefundWindowMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(4, this.postDeliveryRefundWindowMsec);
      }
      if ((this.hasIsOwnedViaLicense) || (this.isOwnedViaLicense)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.certificateHash != null) && (this.certificateHash.length > 0)) {
        for (int i = 0; i < this.certificateHash.length; i++)
        {
          String str = this.certificateHash[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(2, str);
          }
        }
      }
      if ((this.hasRefundTimeoutTimestampMsec) || (this.refundTimeoutTimestampMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.refundTimeoutTimestampMsec);
      }
      if ((this.hasPostDeliveryRefundWindowMsec) || (this.postDeliveryRefundWindowMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(4, this.postDeliveryRefundWindowMsec);
      }
      if ((this.hasIsOwnedViaLicense) || (this.isOwnedViaLicense)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.isOwnedViaLicense);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LibraryFamilySharingDetails
    extends MessageNano
  {
    public int familySharingRole = 0;
    public boolean hasFamilySharingRole = false;
    public boolean hasSharingOriginatorGaiaId = false;
    public String sharingOriginatorGaiaId = "";
    
    public LibraryFamilySharingDetails()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.familySharingRole != 0) || (this.hasFamilySharingRole)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.familySharingRole);
      }
      if ((this.hasSharingOriginatorGaiaId) || (!this.sharingOriginatorGaiaId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.sharingOriginatorGaiaId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.familySharingRole != 0) || (this.hasFamilySharingRole)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.familySharingRole);
      }
      if ((this.hasSharingOriginatorGaiaId) || (!this.sharingOriginatorGaiaId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.sharingOriginatorGaiaId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LibraryInAppDetails
    extends MessageNano
  {
    public boolean hasSignature = false;
    public boolean hasSignedPurchaseData = false;
    public String signature = "";
    public String signedPurchaseData = "";
    
    public LibraryInAppDetails()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasSignedPurchaseData) || (!this.signedPurchaseData.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.signedPurchaseData);
      }
      if ((this.hasSignature) || (!this.signature.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.signature);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasSignedPurchaseData) || (!this.signedPurchaseData.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.signedPurchaseData);
      }
      if ((this.hasSignature) || (!this.signature.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.signature);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LibraryMutation
    extends MessageNano
  {
    private static volatile LibraryMutation[] _emptyArray;
    public LibraryUpdateProto.LibraryAppDetails appDetails = null;
    public boolean deleted = false;
    public Common.Docid docid = null;
    public long documentHash = 0L;
    public Common.FamilyShareability familyShareability = null;
    public LibraryUpdateProto.LibraryFamilySharingDetails familySharingDetails = null;
    public boolean hasDeleted = false;
    public boolean hasDocumentHash = false;
    public boolean hasOfferType = false;
    public boolean hasPreordered = false;
    public boolean hasValidUntilTimestampMsec = false;
    public LibraryUpdateProto.LibraryInAppDetails inAppDetails = null;
    public int offerType = 1;
    public boolean preordered = false;
    public LibraryUpdateProto.LibrarySubscriptionDetails subscriptionDetails = null;
    public long validUntilTimestampMsec = 0L;
    
    public LibraryMutation()
    {
      this.cachedSize = -1;
    }
    
    public static LibraryMutation[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new LibraryMutation[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.docid != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.docid);
      }
      if ((this.offerType != 1) || (this.hasOfferType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.offerType);
      }
      if ((this.hasDocumentHash) || (this.documentHash != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.documentHash);
      }
      if ((this.hasDeleted) || (this.deleted)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      if (this.appDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.appDetails);
      }
      if (this.subscriptionDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.subscriptionDetails);
      }
      if (this.inAppDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.inAppDetails);
      }
      if ((this.hasValidUntilTimestampMsec) || (this.validUntilTimestampMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(8, this.validUntilTimestampMsec);
      }
      if ((this.hasPreordered) || (this.preordered)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(9);
      }
      if (this.familySharingDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.familySharingDetails);
      }
      if (this.familyShareability != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.familyShareability);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.docid != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.docid);
      }
      if ((this.offerType != 1) || (this.hasOfferType)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.offerType);
      }
      if ((this.hasDocumentHash) || (this.documentHash != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.documentHash);
      }
      if ((this.hasDeleted) || (this.deleted)) {
        paramCodedOutputByteBufferNano.writeBool(4, this.deleted);
      }
      if (this.appDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.appDetails);
      }
      if (this.subscriptionDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.subscriptionDetails);
      }
      if (this.inAppDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.inAppDetails);
      }
      if ((this.hasValidUntilTimestampMsec) || (this.validUntilTimestampMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(8, this.validUntilTimestampMsec);
      }
      if ((this.hasPreordered) || (this.preordered)) {
        paramCodedOutputByteBufferNano.writeBool(9, this.preordered);
      }
      if (this.familySharingDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.familySharingDetails);
      }
      if (this.familyShareability != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.familyShareability);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LibrarySubscriptionDetails
    extends MessageNano
  {
    public boolean autoRenewing = false;
    public long deprecatedValidUntilTimestampMsec = 0L;
    public boolean hasAutoRenewing = false;
    public boolean hasDeprecatedValidUntilTimestampMsec = false;
    public boolean hasInitiationTimestampMsec = false;
    public boolean hasSignature = false;
    public boolean hasSignedPurchaseData = false;
    public boolean hasTrialUntilTimestampMsec = false;
    public long initiationTimestampMsec = 0L;
    public String signature = "";
    public String signedPurchaseData = "";
    public long trialUntilTimestampMsec = 0L;
    
    public LibrarySubscriptionDetails()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasInitiationTimestampMsec) || (this.initiationTimestampMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.initiationTimestampMsec);
      }
      if ((this.hasDeprecatedValidUntilTimestampMsec) || (this.deprecatedValidUntilTimestampMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.deprecatedValidUntilTimestampMsec);
      }
      if ((this.hasAutoRenewing) || (this.autoRenewing)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.hasTrialUntilTimestampMsec) || (this.trialUntilTimestampMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(4, this.trialUntilTimestampMsec);
      }
      if ((this.hasSignedPurchaseData) || (!this.signedPurchaseData.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.signedPurchaseData);
      }
      if ((this.hasSignature) || (!this.signature.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.signature);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasInitiationTimestampMsec) || (this.initiationTimestampMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.initiationTimestampMsec);
      }
      if ((this.hasDeprecatedValidUntilTimestampMsec) || (this.deprecatedValidUntilTimestampMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.deprecatedValidUntilTimestampMsec);
      }
      if ((this.hasAutoRenewing) || (this.autoRenewing)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.autoRenewing);
      }
      if ((this.hasTrialUntilTimestampMsec) || (this.trialUntilTimestampMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(4, this.trialUntilTimestampMsec);
      }
      if ((this.hasSignedPurchaseData) || (!this.signedPurchaseData.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.signedPurchaseData);
      }
      if ((this.hasSignature) || (!this.signature.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.signature);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LibraryUpdate
    extends MessageNano
  {
    private static volatile LibraryUpdate[] _emptyArray;
    public int corpus = 0;
    public boolean hasCorpus = false;
    public boolean hasHasMore = false;
    public boolean hasLibraryId = false;
    public boolean hasMore = false;
    public boolean hasServerToken = false;
    public boolean hasStatus = false;
    public String libraryId = "";
    public LibraryUpdateProto.LibraryMutation[] mutation = LibraryUpdateProto.LibraryMutation.emptyArray();
    public byte[] serverToken = WireFormatNano.EMPTY_BYTES;
    public int status = 1;
    
    public LibraryUpdate()
    {
      this.cachedSize = -1;
    }
    
    public static LibraryUpdate[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new LibraryUpdate[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.status != 1) || (this.hasStatus)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.status);
      }
      if ((this.corpus != 0) || (this.hasCorpus)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.corpus);
      }
      if ((this.hasServerToken) || (!Arrays.equals(this.serverToken, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(3, this.serverToken);
      }
      if ((this.mutation != null) && (this.mutation.length > 0)) {
        for (int j = 0; j < this.mutation.length; j++)
        {
          LibraryUpdateProto.LibraryMutation localLibraryMutation = this.mutation[j];
          if (localLibraryMutation != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localLibraryMutation);
          }
        }
      }
      if ((this.hasHasMore) || (this.hasMore)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      if ((this.hasLibraryId) || (!this.libraryId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.libraryId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.status != 1) || (this.hasStatus)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.status);
      }
      if ((this.corpus != 0) || (this.hasCorpus)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.corpus);
      }
      if ((this.hasServerToken) || (!Arrays.equals(this.serverToken, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(3, this.serverToken);
      }
      if ((this.mutation != null) && (this.mutation.length > 0)) {
        for (int i = 0; i < this.mutation.length; i++)
        {
          LibraryUpdateProto.LibraryMutation localLibraryMutation = this.mutation[i];
          if (localLibraryMutation != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localLibraryMutation);
          }
        }
      }
      if ((this.hasHasMore) || (this.hasMore)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.hasMore);
      }
      if ((this.hasLibraryId) || (!this.libraryId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.libraryId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.LibraryUpdateProto
 * JD-Core Version:    0.7.0.1
 */