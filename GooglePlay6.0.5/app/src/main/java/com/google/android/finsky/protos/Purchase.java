package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface Purchase
{
  public static final class AppPurchaseInfo
    extends MessageNano
  {
    public int appVersionCode = 0;
    public boolean hasAppVersionCode = false;
    
    public AppPurchaseInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasAppVersionCode) || (this.appVersionCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.appVersionCode);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasAppVersionCode) || (this.appVersionCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.appVersionCode);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ApplicableVoucher
    extends MessageNano
  {
    private static volatile ApplicableVoucher[] _emptyArray;
    public boolean applied = false;
    public String discountAmountDescription = "";
    public DocV2 doc = null;
    public String formattedDiscountAmount = "";
    public boolean hasApplied = false;
    public boolean hasDiscountAmountDescription = false;
    public boolean hasFormattedDiscountAmount = false;
    
    public ApplicableVoucher()
    {
      this.cachedSize = -1;
    }
    
    public static ApplicableVoucher[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ApplicableVoucher[0];
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
      if ((this.hasFormattedDiscountAmount) || (!this.formattedDiscountAmount.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.formattedDiscountAmount);
      }
      if ((this.hasApplied) || (this.applied)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.hasDiscountAmountDescription) || (!this.discountAmountDescription.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.discountAmountDescription);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.doc != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.doc);
      }
      if ((this.hasFormattedDiscountAmount) || (!this.formattedDiscountAmount.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.formattedDiscountAmount);
      }
      if ((this.hasApplied) || (this.applied)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.applied);
      }
      if ((this.hasDiscountAmountDescription) || (!this.discountAmountDescription.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.discountAmountDescription);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class AuthenticationInfo
    extends MessageNano
  {
    public int authenticationMethod = 0;
    public int authenticationPreference = 0;
    public long flowStartedTimestampMillis = 0L;
    public boolean hasAuthenticationMethod = false;
    public boolean hasAuthenticationPreference = false;
    public boolean hasFlowStartedTimestampMillis = false;
    public boolean hasLastAuthTimestampMillis = false;
    public long lastAuthTimestampMillis = 0L;
    
    public AuthenticationInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.authenticationPreference != 0) || (this.hasAuthenticationPreference)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.authenticationPreference);
      }
      if ((this.hasLastAuthTimestampMillis) || (this.lastAuthTimestampMillis != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.lastAuthTimestampMillis);
      }
      if ((this.hasFlowStartedTimestampMillis) || (this.flowStartedTimestampMillis != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.flowStartedTimestampMillis);
      }
      if ((this.authenticationMethod != 0) || (this.hasAuthenticationMethod)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.authenticationMethod);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.authenticationPreference != 0) || (this.hasAuthenticationPreference)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.authenticationPreference);
      }
      if ((this.hasLastAuthTimestampMillis) || (this.lastAuthTimestampMillis != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.lastAuthTimestampMillis);
      }
      if ((this.hasFlowStartedTimestampMillis) || (this.flowStartedTimestampMillis != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.flowStartedTimestampMillis);
      }
      if ((this.authenticationMethod != 0) || (this.hasAuthenticationMethod)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.authenticationMethod);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ChangeSubscription
    extends MessageNano
  {
    public String descriptionHtml = "";
    public boolean hasDescriptionHtml = false;
    public boolean hasTitle = false;
    public String title = "";
    
    public ChangeSubscription()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.descriptionHtml);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.descriptionHtml);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ClientCart
    extends MessageNano
  {
    public String addInstrumentPromptHtml = "";
    public DocV2[] applicableVoucher = DocV2.emptyArray();
    public Purchase.ApplicableVoucher[] applicableVouchers = Purchase.ApplicableVoucher.emptyArray();
    public int[] appliedVoucherIndex = WireFormatNano.EMPTY_INT_ARRAY;
    public String buttonText = "";
    public ChallengeProto.Challenge completePurchaseChallenge = null;
    public String[] detailHtml = WireFormatNano.EMPTY_STRING_ARRAY;
    public Common.Image documentThumbnail = null;
    public String[] extendedDetailHtml = WireFormatNano.EMPTY_STRING_ARRAY;
    public String footerHtml = "";
    public String formattedOriginalPrice = "";
    public String formattedPrice = "";
    public boolean hasAddInstrumentPromptHtml = false;
    public boolean hasButtonText = false;
    public boolean hasFooterHtml = false;
    public boolean hasFormattedOriginalPrice = false;
    public boolean hasFormattedPrice = false;
    public boolean hasPriceByline = false;
    public boolean hasPriceByline2 = false;
    public boolean hasPurchaseContextToken = false;
    public boolean hasSubtitle = false;
    public boolean hasTitle = false;
    public Instrument instrument = null;
    public String priceByline = "";
    public String priceByline2 = "";
    public String purchaseContextToken = "";
    public Purchase.SplitTenderInfo splitTenderInfo = null;
    public String subtitle = "";
    public String title = "";
    
    public ClientCart()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasFormattedPrice) || (!this.formattedPrice.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.formattedPrice);
      }
      if ((this.hasPurchaseContextToken) || (!this.purchaseContextToken.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.purchaseContextToken);
      }
      if (this.instrument != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.instrument);
      }
      if ((this.extendedDetailHtml != null) && (this.extendedDetailHtml.length > 0))
      {
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < this.extendedDetailHtml.length; i6++)
        {
          String str2 = this.extendedDetailHtml[i6];
          if (str2 != null)
          {
            i4++;
            i5 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
          }
        }
        i = i + i5 + i4 * 1;
      }
      if ((this.hasFooterHtml) || (!this.footerHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.footerHtml);
      }
      if ((this.hasAddInstrumentPromptHtml) || (!this.addInstrumentPromptHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.addInstrumentPromptHtml);
      }
      if ((this.hasButtonText) || (!this.buttonText.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.buttonText);
      }
      if (this.completePurchaseChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.completePurchaseChallenge);
      }
      if ((this.hasPriceByline) || (!this.priceByline.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.priceByline);
      }
      if ((this.detailHtml != null) && (this.detailHtml.length > 0))
      {
        int i1 = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < this.detailHtml.length; i3++)
        {
          String str1 = this.detailHtml[i3];
          if (str1 != null)
          {
            i1++;
            i2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
          }
        }
        i = i + i2 + i1 * 1;
      }
      if ((this.applicableVoucher != null) && (this.applicableVoucher.length > 0)) {
        for (int n = 0; n < this.applicableVoucher.length; n++)
        {
          DocV2 localDocV2 = this.applicableVoucher[n];
          if (localDocV2 != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(12, localDocV2);
          }
        }
      }
      if ((this.appliedVoucherIndex != null) && (this.appliedVoucherIndex.length > 0))
      {
        int k = 0;
        for (int m = 0; m < this.appliedVoucherIndex.length; m++) {
          k += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.appliedVoucherIndex[m]);
        }
        i = i + k + 1 * this.appliedVoucherIndex.length;
      }
      if ((this.applicableVouchers != null) && (this.applicableVouchers.length > 0)) {
        for (int j = 0; j < this.applicableVouchers.length; j++)
        {
          Purchase.ApplicableVoucher localApplicableVoucher = this.applicableVouchers[j];
          if (localApplicableVoucher != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(14, localApplicableVoucher);
          }
        }
      }
      if ((this.hasFormattedOriginalPrice) || (!this.formattedOriginalPrice.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(15, this.formattedOriginalPrice);
      }
      if ((this.hasPriceByline2) || (!this.priceByline2.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(16, this.priceByline2);
      }
      if (this.documentThumbnail != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(17, this.documentThumbnail);
      }
      if (this.splitTenderInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(18, this.splitTenderInfo);
      }
      if ((this.hasSubtitle) || (!this.subtitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(19, this.subtitle);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasFormattedPrice) || (!this.formattedPrice.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.formattedPrice);
      }
      if ((this.hasPurchaseContextToken) || (!this.purchaseContextToken.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.purchaseContextToken);
      }
      if (this.instrument != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.instrument);
      }
      if ((this.extendedDetailHtml != null) && (this.extendedDetailHtml.length > 0)) {
        for (int n = 0; n < this.extendedDetailHtml.length; n++)
        {
          String str2 = this.extendedDetailHtml[n];
          if (str2 != null) {
            paramCodedOutputByteBufferNano.writeString(5, str2);
          }
        }
      }
      if ((this.hasFooterHtml) || (!this.footerHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.footerHtml);
      }
      if ((this.hasAddInstrumentPromptHtml) || (!this.addInstrumentPromptHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.addInstrumentPromptHtml);
      }
      if ((this.hasButtonText) || (!this.buttonText.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.buttonText);
      }
      if (this.completePurchaseChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.completePurchaseChallenge);
      }
      if ((this.hasPriceByline) || (!this.priceByline.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(10, this.priceByline);
      }
      if ((this.detailHtml != null) && (this.detailHtml.length > 0)) {
        for (int m = 0; m < this.detailHtml.length; m++)
        {
          String str1 = this.detailHtml[m];
          if (str1 != null) {
            paramCodedOutputByteBufferNano.writeString(11, str1);
          }
        }
      }
      if ((this.applicableVoucher != null) && (this.applicableVoucher.length > 0)) {
        for (int k = 0; k < this.applicableVoucher.length; k++)
        {
          DocV2 localDocV2 = this.applicableVoucher[k];
          if (localDocV2 != null) {
            paramCodedOutputByteBufferNano.writeMessage(12, localDocV2);
          }
        }
      }
      if ((this.appliedVoucherIndex != null) && (this.appliedVoucherIndex.length > 0)) {
        for (int j = 0; j < this.appliedVoucherIndex.length; j++) {
          paramCodedOutputByteBufferNano.writeInt32(13, this.appliedVoucherIndex[j]);
        }
      }
      if ((this.applicableVouchers != null) && (this.applicableVouchers.length > 0)) {
        for (int i = 0; i < this.applicableVouchers.length; i++)
        {
          Purchase.ApplicableVoucher localApplicableVoucher = this.applicableVouchers[i];
          if (localApplicableVoucher != null) {
            paramCodedOutputByteBufferNano.writeMessage(14, localApplicableVoucher);
          }
        }
      }
      if ((this.hasFormattedOriginalPrice) || (!this.formattedOriginalPrice.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(15, this.formattedOriginalPrice);
      }
      if ((this.hasPriceByline2) || (!this.priceByline2.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(16, this.priceByline2);
      }
      if (this.documentThumbnail != null) {
        paramCodedOutputByteBufferNano.writeMessage(17, this.documentThumbnail);
      }
      if (this.splitTenderInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(18, this.splitTenderInfo);
      }
      if ((this.hasSubtitle) || (!this.subtitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(19, this.subtitle);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CommitPurchaseRecoverableError
    extends MessageNano
  {
    public int errorType = 0;
    public Purchase.FopDeclinedRecoverableError fopDeclinedRecoverableError = null;
    public boolean hasErrorType = false;
    public Purchase.InstantPurchaseRecoverableError instantPurchaseRecoverableError = null;
    
    public CommitPurchaseRecoverableError()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.errorType != 0) || (this.hasErrorType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.errorType);
      }
      if (this.fopDeclinedRecoverableError != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.fopDeclinedRecoverableError);
      }
      if (this.instantPurchaseRecoverableError != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.instantPurchaseRecoverableError);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.errorType != 0) || (this.hasErrorType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.errorType);
      }
      if (this.fopDeclinedRecoverableError != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.fopDeclinedRecoverableError);
      }
      if (this.instantPurchaseRecoverableError != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.instantPurchaseRecoverableError);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CommitPurchaseResponse
    extends MessageNano
  {
    public AndroidAppDeliveryData appDeliveryData = null;
    public ChallengeProto.Challenge challenge = null;
    public Purchase.CommitPurchaseRecoverableError commitPurchaseRecoverableError = null;
    public String encodedDeliveryToken = "";
    public boolean hasEncodedDeliveryToken = false;
    public boolean hasServerLogsCookie = false;
    public LibraryUpdateProto.LibraryUpdate[] libraryUpdate = LibraryUpdateProto.LibraryUpdate.emptyArray();
    public Acquisition.PostAcquisitionPrompt postAcquisitionPrompt = null;
    public Purchase.PurchaseStatus purchaseStatus = null;
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public Acquisition.SuccessInfo successInfo = null;
    public Buy.Money totalPrice = null;
    
    public CommitPurchaseResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.purchaseStatus != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.purchaseStatus);
      }
      if (this.challenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.challenge);
      }
      if ((this.libraryUpdate != null) && (this.libraryUpdate.length > 0)) {
        for (int j = 0; j < this.libraryUpdate.length; j++)
        {
          LibraryUpdateProto.LibraryUpdate localLibraryUpdate = this.libraryUpdate[j];
          if (localLibraryUpdate != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localLibraryUpdate);
          }
        }
      }
      if (this.appDeliveryData != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.appDeliveryData);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(5, this.serverLogsCookie);
      }
      if ((this.hasEncodedDeliveryToken) || (!this.encodedDeliveryToken.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.encodedDeliveryToken);
      }
      if (this.successInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.successInfo);
      }
      if (this.commitPurchaseRecoverableError != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.commitPurchaseRecoverableError);
      }
      if (this.postAcquisitionPrompt != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.postAcquisitionPrompt);
      }
      if (this.totalPrice != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.totalPrice);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.purchaseStatus != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.purchaseStatus);
      }
      if (this.challenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.challenge);
      }
      if ((this.libraryUpdate != null) && (this.libraryUpdate.length > 0)) {
        for (int i = 0; i < this.libraryUpdate.length; i++)
        {
          LibraryUpdateProto.LibraryUpdate localLibraryUpdate = this.libraryUpdate[i];
          if (localLibraryUpdate != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localLibraryUpdate);
          }
        }
      }
      if (this.appDeliveryData != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.appDeliveryData);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(5, this.serverLogsCookie);
      }
      if ((this.hasEncodedDeliveryToken) || (!this.encodedDeliveryToken.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.encodedDeliveryToken);
      }
      if (this.successInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.successInfo);
      }
      if (this.commitPurchaseRecoverableError != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.commitPurchaseRecoverableError);
      }
      if (this.postAcquisitionPrompt != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.postAcquisitionPrompt);
      }
      if (this.totalPrice != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.totalPrice);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DcbPurchaseInfo
    extends MessageNano
  {
    public boolean hasPassphrase = false;
    public boolean hasSimIdentifierHash = false;
    public String passphrase = "";
    public String simIdentifierHash = "";
    
    public DcbPurchaseInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasSimIdentifierHash) || (!this.simIdentifierHash.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.simIdentifierHash);
      }
      if ((this.hasPassphrase) || (!this.passphrase.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.passphrase);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasSimIdentifierHash) || (!this.simIdentifierHash.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.simIdentifierHash);
      }
      if ((this.hasPassphrase) || (!this.passphrase.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.passphrase);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FopDeclinedRecoverableError
    extends MessageNano
  {
    public boolean hasPurchaseContextToken = false;
    public String purchaseContextToken = "";
    public Purchase.RecoverableErrorPrompt recoverableErrorPrompt = null;
    
    public FopDeclinedRecoverableError()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasPurchaseContextToken) || (!this.purchaseContextToken.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.purchaseContextToken);
      }
      if (this.recoverableErrorPrompt != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.recoverableErrorPrompt);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasPurchaseContextToken) || (!this.purchaseContextToken.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.purchaseContextToken);
      }
      if (this.recoverableErrorPrompt != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.recoverableErrorPrompt);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class InAppPurchaseInfo
    extends MessageNano
  {
    public String appPackageName = "";
    public String appSignatureHash = "";
    public int appVersionCode = 0;
    public int billingApiVersion = 0;
    public String developerPayload = "";
    public boolean hasAppPackageName = false;
    public boolean hasAppSignatureHash = false;
    public boolean hasAppVersionCode = false;
    public boolean hasBillingApiVersion = false;
    public boolean hasDeveloperPayload = false;
    public String[] oldDocid = WireFormatNano.EMPTY_STRING_ARRAY;
    
    public InAppPurchaseInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasBillingApiVersion) || (this.billingApiVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.billingApiVersion);
      }
      if ((this.hasAppPackageName) || (!this.appPackageName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.appPackageName);
      }
      if ((this.hasAppVersionCode) || (this.appVersionCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.appVersionCode);
      }
      if ((this.hasAppSignatureHash) || (!this.appSignatureHash.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.appSignatureHash);
      }
      if ((this.hasDeveloperPayload) || (!this.developerPayload.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.developerPayload);
      }
      if ((this.oldDocid != null) && (this.oldDocid.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.oldDocid.length; m++)
        {
          String str = this.oldDocid[m];
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
      if ((this.hasBillingApiVersion) || (this.billingApiVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.billingApiVersion);
      }
      if ((this.hasAppPackageName) || (!this.appPackageName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.appPackageName);
      }
      if ((this.hasAppVersionCode) || (this.appVersionCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.appVersionCode);
      }
      if ((this.hasAppSignatureHash) || (!this.appSignatureHash.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.appSignatureHash);
      }
      if ((this.hasDeveloperPayload) || (!this.developerPayload.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.developerPayload);
      }
      if ((this.oldDocid != null) && (this.oldDocid.length > 0)) {
        for (int i = 0; i < this.oldDocid.length; i++)
        {
          String str = this.oldDocid[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(6, str);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class InstantPurchaseRecoverableError
    extends MessageNano
  {
    public Purchase.PreparePurchaseResponse preparePurchaseResponse = null;
    public Purchase.RecoverableErrorPrompt recoverableErrorPrompt = null;
    
    public InstantPurchaseRecoverableError()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.preparePurchaseResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.preparePurchaseResponse);
      }
      if (this.recoverableErrorPrompt != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.recoverableErrorPrompt);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.preparePurchaseResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.preparePurchaseResponse);
      }
      if (this.recoverableErrorPrompt != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.recoverableErrorPrompt);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class InstantPurchaseRequest
    extends MessageNano
  {
    public PlayAccountProto.CachedPaymentsLegalDocument[] acceptedLegalDocument = PlayAccountProto.CachedPaymentsLegalDocument.emptyArray();
    public Purchase.AppPurchaseInfo appPurchaseInfo = null;
    public Purchase.AuthenticationInfo authenticationInfo = null;
    public String checkoutRiskHashedDeviceInfo = "";
    public Purchase.ClientCart clientCart = null;
    public Purchase.DcbPurchaseInfo dcbPurchaseInfo = null;
    public Common.Docid docid = null;
    public boolean hasCheckoutRiskHashedDeviceInfo = false;
    public boolean hasOfferType = false;
    public boolean hasPaymentsIntegratorClientContextToken = false;
    public Purchase.InAppPurchaseInfo inAppPurchaseInfo = null;
    public int offerType = 1;
    public byte[] paymentsIntegratorClientContextToken = WireFormatNano.EMPTY_BYTES;
    
    public InstantPurchaseRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.clientCart != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.clientCart);
      }
      if (this.docid != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.docid);
      }
      if ((this.offerType != 1) || (this.hasOfferType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.offerType);
      }
      if (this.appPurchaseInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.appPurchaseInfo);
      }
      if ((this.hasCheckoutRiskHashedDeviceInfo) || (!this.checkoutRiskHashedDeviceInfo.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.checkoutRiskHashedDeviceInfo);
      }
      if (this.authenticationInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.authenticationInfo);
      }
      if (this.dcbPurchaseInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.dcbPurchaseInfo);
      }
      if (this.inAppPurchaseInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.inAppPurchaseInfo);
      }
      if ((this.hasPaymentsIntegratorClientContextToken) || (!Arrays.equals(this.paymentsIntegratorClientContextToken, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(9, this.paymentsIntegratorClientContextToken);
      }
      if ((this.acceptedLegalDocument != null) && (this.acceptedLegalDocument.length > 0)) {
        for (int j = 0; j < this.acceptedLegalDocument.length; j++)
        {
          PlayAccountProto.CachedPaymentsLegalDocument localCachedPaymentsLegalDocument = this.acceptedLegalDocument[j];
          if (localCachedPaymentsLegalDocument != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(10, localCachedPaymentsLegalDocument);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.clientCart != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.clientCart);
      }
      if (this.docid != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.docid);
      }
      if ((this.offerType != 1) || (this.hasOfferType)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.offerType);
      }
      if (this.appPurchaseInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.appPurchaseInfo);
      }
      if ((this.hasCheckoutRiskHashedDeviceInfo) || (!this.checkoutRiskHashedDeviceInfo.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.checkoutRiskHashedDeviceInfo);
      }
      if (this.authenticationInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.authenticationInfo);
      }
      if (this.dcbPurchaseInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.dcbPurchaseInfo);
      }
      if (this.inAppPurchaseInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.inAppPurchaseInfo);
      }
      if ((this.hasPaymentsIntegratorClientContextToken) || (!Arrays.equals(this.paymentsIntegratorClientContextToken, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(9, this.paymentsIntegratorClientContextToken);
      }
      if ((this.acceptedLegalDocument != null) && (this.acceptedLegalDocument.length > 0)) {
        for (int i = 0; i < this.acceptedLegalDocument.length; i++)
        {
          PlayAccountProto.CachedPaymentsLegalDocument localCachedPaymentsLegalDocument = this.acceptedLegalDocument[i];
          if (localCachedPaymentsLegalDocument != null) {
            paramCodedOutputByteBufferNano.writeMessage(10, localCachedPaymentsLegalDocument);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class InstantPurchaseResponse
    extends MessageNano
  {
    public Purchase.CommitPurchaseResponse commitPurchaseResponse = null;
    
    public InstantPurchaseResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.commitPurchaseResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.commitPurchaseResponse);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.commitPurchaseResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.commitPurchaseResponse);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PreparePurchaseResponse
    extends MessageNano
  {
    public DocV2[] applicableVoucher = DocV2.emptyArray();
    public int[] appliedVoucherIndex = WireFormatNano.EMPTY_INT_ARRAY;
    public Purchase.ClientCart cart = null;
    public ChallengeProto.Challenge challenge = null;
    public Purchase.ChangeSubscription changeSubscription = null;
    public boolean hasServerLogsCookie = false;
    public LibraryUpdateProto.LibraryUpdate[] libraryUpdate = LibraryUpdateProto.LibraryUpdate.emptyArray();
    public Purchase.PurchaseStatus purchaseStatus = null;
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    
    public PreparePurchaseResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.purchaseStatus != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.purchaseStatus);
      }
      if (this.challenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.challenge);
      }
      if (this.cart != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.cart);
      }
      if ((this.libraryUpdate != null) && (this.libraryUpdate.length > 0)) {
        for (int n = 0; n < this.libraryUpdate.length; n++)
        {
          LibraryUpdateProto.LibraryUpdate localLibraryUpdate = this.libraryUpdate[n];
          if (localLibraryUpdate != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localLibraryUpdate);
          }
        }
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(5, this.serverLogsCookie);
      }
      if ((this.applicableVoucher != null) && (this.applicableVoucher.length > 0)) {
        for (int m = 0; m < this.applicableVoucher.length; m++)
        {
          DocV2 localDocV2 = this.applicableVoucher[m];
          if (localDocV2 != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(6, localDocV2);
          }
        }
      }
      if ((this.appliedVoucherIndex != null) && (this.appliedVoucherIndex.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.appliedVoucherIndex.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.appliedVoucherIndex[k]);
        }
        i = i + j + 1 * this.appliedVoucherIndex.length;
      }
      if (this.changeSubscription != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.changeSubscription);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.purchaseStatus != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.purchaseStatus);
      }
      if (this.challenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.challenge);
      }
      if (this.cart != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.cart);
      }
      if ((this.libraryUpdate != null) && (this.libraryUpdate.length > 0)) {
        for (int k = 0; k < this.libraryUpdate.length; k++)
        {
          LibraryUpdateProto.LibraryUpdate localLibraryUpdate = this.libraryUpdate[k];
          if (localLibraryUpdate != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localLibraryUpdate);
          }
        }
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(5, this.serverLogsCookie);
      }
      if ((this.applicableVoucher != null) && (this.applicableVoucher.length > 0)) {
        for (int j = 0; j < this.applicableVoucher.length; j++)
        {
          DocV2 localDocV2 = this.applicableVoucher[j];
          if (localDocV2 != null) {
            paramCodedOutputByteBufferNano.writeMessage(6, localDocV2);
          }
        }
      }
      if ((this.appliedVoucherIndex != null) && (this.appliedVoucherIndex.length > 0)) {
        for (int i = 0; i < this.appliedVoucherIndex.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(7, this.appliedVoucherIndex[i]);
        }
      }
      if (this.changeSubscription != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.changeSubscription);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PurchaseStatus
    extends MessageNano
  {
    public String errorMessageHtml = "";
    public boolean hasErrorMessageHtml = false;
    public boolean hasPermissionError = false;
    public boolean hasStatusCode = false;
    public int permissionError = 0;
    public int statusCode = 0;
    
    public PurchaseStatus()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.statusCode != 0) || (this.hasStatusCode)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.statusCode);
      }
      if ((this.hasErrorMessageHtml) || (!this.errorMessageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.errorMessageHtml);
      }
      if ((this.permissionError != 0) || (this.hasPermissionError)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.permissionError);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.statusCode != 0) || (this.hasStatusCode)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.statusCode);
      }
      if ((this.hasErrorMessageHtml) || (!this.errorMessageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.errorMessageHtml);
      }
      if ((this.permissionError != 0) || (this.hasPermissionError)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.permissionError);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RecoverableErrorPrompt
    extends MessageNano
  {
    public String buttonLabel = "";
    public String errorMessageHtml = "";
    public boolean hasButtonLabel = false;
    public boolean hasErrorMessageHtml = false;
    public boolean hasTitle = false;
    public String title = "";
    
    public RecoverableErrorPrompt()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasErrorMessageHtml) || (!this.errorMessageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.errorMessageHtml);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.buttonLabel);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasErrorMessageHtml) || (!this.errorMessageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.errorMessageHtml);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.buttonLabel);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SplitTenderInfo
    extends MessageNano
  {
    public boolean hasSplitApplied = false;
    public boolean hasSplitTenderMessage = false;
    public boolean splitApplied = false;
    public String splitTenderMessage = "";
    
    public SplitTenderInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasSplitTenderMessage) || (!this.splitTenderMessage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.splitTenderMessage);
      }
      if ((this.hasSplitApplied) || (this.splitApplied)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasSplitTenderMessage) || (!this.splitTenderMessage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.splitTenderMessage);
      }
      if ((this.hasSplitApplied) || (this.splitApplied)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.splitApplied);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Purchase
 * JD-Core Version:    0.7.0.1
 */