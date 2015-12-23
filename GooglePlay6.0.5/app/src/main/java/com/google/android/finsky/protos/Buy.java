package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface Buy
{
  public static final class BuyResponse
    extends MessageNano
  {
    public String addInstrumentPromptHtml = "";
    public String baseCheckoutUrl = "";
    public ChallengeProto.Challenge challenge = null;
    public CheckoutInfo checkoutInfo = null;
    public String checkoutServiceId = "";
    public String confirmButtonText = "";
    public String continueViaUrl = "";
    public String encodedDeliveryToken = "";
    public boolean hasAddInstrumentPromptHtml = false;
    public boolean hasBaseCheckoutUrl = false;
    public boolean hasCheckoutServiceId = false;
    public boolean hasConfirmButtonText = false;
    public boolean hasContinueViaUrl = false;
    public boolean hasEncodedDeliveryToken = false;
    public boolean hasPermissionError = false;
    public boolean hasPermissionErrorMessageText = false;
    public boolean hasPermissionErrorTitleText = false;
    public boolean hasPurchaseCookie = false;
    public boolean hasPurchaseStatusUrl = false;
    public boolean hasServerLogsCookie = false;
    public int permissionError = 0;
    public String permissionErrorMessageText = "";
    public String permissionErrorTitleText = "";
    public String purchaseCookie = "";
    public Buy.PurchaseNotificationResponse purchaseResponse = null;
    public Buy.PurchaseStatusResponse purchaseStatusResponse = null;
    public String purchaseStatusUrl = "";
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public String[] tosCheckboxHtml = WireFormatNano.EMPTY_STRING_ARRAY;
    
    public BuyResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.purchaseResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.purchaseResponse);
      }
      if (this.checkoutInfo != null) {
        i += CodedOutputByteBufferNano.computeGroupSize(2, this.checkoutInfo);
      }
      if ((this.hasContinueViaUrl) || (!this.continueViaUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.continueViaUrl);
      }
      if ((this.hasPurchaseStatusUrl) || (!this.purchaseStatusUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.purchaseStatusUrl);
      }
      if ((this.hasCheckoutServiceId) || (!this.checkoutServiceId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(12, this.checkoutServiceId);
      }
      if ((this.hasBaseCheckoutUrl) || (!this.baseCheckoutUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(14, this.baseCheckoutUrl);
      }
      if ((this.tosCheckboxHtml != null) && (this.tosCheckboxHtml.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.tosCheckboxHtml.length; m++)
        {
          String str = this.tosCheckboxHtml[m];
          if (str != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + k + j * 2;
      }
      if ((this.permissionError != 0) || (this.hasPermissionError)) {
        i += CodedOutputByteBufferNano.computeInt32Size(38, this.permissionError);
      }
      if (this.purchaseStatusResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(39, this.purchaseStatusResponse);
      }
      if ((this.hasPurchaseCookie) || (!this.purchaseCookie.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(46, this.purchaseCookie);
      }
      if (this.challenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(49, this.challenge);
      }
      if ((this.hasAddInstrumentPromptHtml) || (!this.addInstrumentPromptHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(50, this.addInstrumentPromptHtml);
      }
      if ((this.hasConfirmButtonText) || (!this.confirmButtonText.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(51, this.confirmButtonText);
      }
      if ((this.hasPermissionErrorTitleText) || (!this.permissionErrorTitleText.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(52, this.permissionErrorTitleText);
      }
      if ((this.hasPermissionErrorMessageText) || (!this.permissionErrorMessageText.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(53, this.permissionErrorMessageText);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(54, this.serverLogsCookie);
      }
      if ((this.hasEncodedDeliveryToken) || (!this.encodedDeliveryToken.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(55, this.encodedDeliveryToken);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.purchaseResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.purchaseResponse);
      }
      if (this.checkoutInfo != null) {
        paramCodedOutputByteBufferNano.writeGroup(2, this.checkoutInfo);
      }
      if ((this.hasContinueViaUrl) || (!this.continueViaUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.continueViaUrl);
      }
      if ((this.hasPurchaseStatusUrl) || (!this.purchaseStatusUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.purchaseStatusUrl);
      }
      if ((this.hasCheckoutServiceId) || (!this.checkoutServiceId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(12, this.checkoutServiceId);
      }
      if ((this.hasBaseCheckoutUrl) || (!this.baseCheckoutUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(14, this.baseCheckoutUrl);
      }
      if ((this.tosCheckboxHtml != null) && (this.tosCheckboxHtml.length > 0)) {
        for (int i = 0; i < this.tosCheckboxHtml.length; i++)
        {
          String str = this.tosCheckboxHtml[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(37, str);
          }
        }
      }
      if ((this.permissionError != 0) || (this.hasPermissionError)) {
        paramCodedOutputByteBufferNano.writeInt32(38, this.permissionError);
      }
      if (this.purchaseStatusResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(39, this.purchaseStatusResponse);
      }
      if ((this.hasPurchaseCookie) || (!this.purchaseCookie.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(46, this.purchaseCookie);
      }
      if (this.challenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(49, this.challenge);
      }
      if ((this.hasAddInstrumentPromptHtml) || (!this.addInstrumentPromptHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(50, this.addInstrumentPromptHtml);
      }
      if ((this.hasConfirmButtonText) || (!this.confirmButtonText.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(51, this.confirmButtonText);
      }
      if ((this.hasPermissionErrorTitleText) || (!this.permissionErrorTitleText.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(52, this.permissionErrorTitleText);
      }
      if ((this.hasPermissionErrorMessageText) || (!this.permissionErrorMessageText.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(53, this.permissionErrorMessageText);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(54, this.serverLogsCookie);
      }
      if ((this.hasEncodedDeliveryToken) || (!this.encodedDeliveryToken.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(55, this.encodedDeliveryToken);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class CheckoutInfo
      extends MessageNano
    {
      public String addInstrumentUrl = "";
      public CheckoutOption[] checkoutOption = CheckoutOption.emptyArray();
      public String deprecatedCheckoutUrl = "";
      public Instrument[] eligibleInstrument = Instrument.emptyArray();
      public int[] eligibleInstrumentFamily = WireFormatNano.EMPTY_INT_ARRAY;
      public String[] footerHtml = WireFormatNano.EMPTY_STRING_ARRAY;
      public String[] footnoteHtml = WireFormatNano.EMPTY_STRING_ARRAY;
      public boolean hasAddInstrumentUrl = false;
      public boolean hasDeprecatedCheckoutUrl = false;
      public Buy.LineItem item = null;
      public Buy.LineItem[] subItem = Buy.LineItem.emptyArray();
      
      public CheckoutInfo()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if (this.item != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(3, this.item);
        }
        if ((this.subItem != null) && (this.subItem.length > 0)) {
          for (int i7 = 0; i7 < this.subItem.length; i7++)
          {
            Buy.LineItem localLineItem = this.subItem[i7];
            if (localLineItem != null) {
              i += CodedOutputByteBufferNano.computeMessageSize(4, localLineItem);
            }
          }
        }
        if ((this.checkoutOption != null) && (this.checkoutOption.length > 0)) {
          for (int i6 = 0; i6 < this.checkoutOption.length; i6++)
          {
            CheckoutOption localCheckoutOption = this.checkoutOption[i6];
            if (localCheckoutOption != null) {
              i += CodedOutputByteBufferNano.computeGroupSize(5, localCheckoutOption);
            }
          }
        }
        if ((this.hasDeprecatedCheckoutUrl) || (!this.deprecatedCheckoutUrl.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(10, this.deprecatedCheckoutUrl);
        }
        if ((this.hasAddInstrumentUrl) || (!this.addInstrumentUrl.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(11, this.addInstrumentUrl);
        }
        if ((this.footerHtml != null) && (this.footerHtml.length > 0))
        {
          int i3 = 0;
          int i4 = 0;
          for (int i5 = 0; i5 < this.footerHtml.length; i5++)
          {
            String str2 = this.footerHtml[i5];
            if (str2 != null)
            {
              i3++;
              i4 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
            }
          }
          i = i + i4 + i3 * 2;
        }
        if ((this.eligibleInstrumentFamily != null) && (this.eligibleInstrumentFamily.length > 0))
        {
          int i1 = 0;
          for (int i2 = 0; i2 < this.eligibleInstrumentFamily.length; i2++) {
            i1 += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.eligibleInstrumentFamily[i2]);
          }
          i = i + i1 + 2 * this.eligibleInstrumentFamily.length;
        }
        if ((this.footnoteHtml != null) && (this.footnoteHtml.length > 0))
        {
          int k = 0;
          int m = 0;
          for (int n = 0; n < this.footnoteHtml.length; n++)
          {
            String str1 = this.footnoteHtml[n];
            if (str1 != null)
            {
              k++;
              m += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
            }
          }
          i = i + m + k * 2;
        }
        if ((this.eligibleInstrument != null) && (this.eligibleInstrument.length > 0)) {
          for (int j = 0; j < this.eligibleInstrument.length; j++)
          {
            Instrument localInstrument = this.eligibleInstrument[j];
            if (localInstrument != null) {
              i += CodedOutputByteBufferNano.computeMessageSize(44, localInstrument);
            }
          }
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if (this.item != null) {
          paramCodedOutputByteBufferNano.writeMessage(3, this.item);
        }
        if ((this.subItem != null) && (this.subItem.length > 0)) {
          for (int i1 = 0; i1 < this.subItem.length; i1++)
          {
            Buy.LineItem localLineItem = this.subItem[i1];
            if (localLineItem != null) {
              paramCodedOutputByteBufferNano.writeMessage(4, localLineItem);
            }
          }
        }
        if ((this.checkoutOption != null) && (this.checkoutOption.length > 0)) {
          for (int n = 0; n < this.checkoutOption.length; n++)
          {
            CheckoutOption localCheckoutOption = this.checkoutOption[n];
            if (localCheckoutOption != null) {
              paramCodedOutputByteBufferNano.writeGroup(5, localCheckoutOption);
            }
          }
        }
        if ((this.hasDeprecatedCheckoutUrl) || (!this.deprecatedCheckoutUrl.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(10, this.deprecatedCheckoutUrl);
        }
        if ((this.hasAddInstrumentUrl) || (!this.addInstrumentUrl.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(11, this.addInstrumentUrl);
        }
        if ((this.footerHtml != null) && (this.footerHtml.length > 0)) {
          for (int m = 0; m < this.footerHtml.length; m++)
          {
            String str2 = this.footerHtml[m];
            if (str2 != null) {
              paramCodedOutputByteBufferNano.writeString(20, str2);
            }
          }
        }
        if ((this.eligibleInstrumentFamily != null) && (this.eligibleInstrumentFamily.length > 0)) {
          for (int k = 0; k < this.eligibleInstrumentFamily.length; k++) {
            paramCodedOutputByteBufferNano.writeInt32(31, this.eligibleInstrumentFamily[k]);
          }
        }
        if ((this.footnoteHtml != null) && (this.footnoteHtml.length > 0)) {
          for (int j = 0; j < this.footnoteHtml.length; j++)
          {
            String str1 = this.footnoteHtml[j];
            if (str1 != null) {
              paramCodedOutputByteBufferNano.writeString(36, str1);
            }
          }
        }
        if ((this.eligibleInstrument != null) && (this.eligibleInstrument.length > 0)) {
          for (int i = 0; i < this.eligibleInstrument.length; i++)
          {
            Instrument localInstrument = this.eligibleInstrument[i];
            if (localInstrument != null) {
              paramCodedOutputByteBufferNano.writeMessage(44, localInstrument);
            }
          }
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
      
      public static final class CheckoutOption
        extends MessageNano
      {
        private static volatile CheckoutOption[] _emptyArray;
        public String[] disabledReason = WireFormatNano.EMPTY_STRING_ARRAY;
        public String encodedAdjustedCart = "";
        public String[] footerHtml = WireFormatNano.EMPTY_STRING_ARRAY;
        public String[] footnoteHtml = WireFormatNano.EMPTY_STRING_ARRAY;
        public String formOfPayment = "";
        public boolean hasEncodedAdjustedCart = false;
        public boolean hasFormOfPayment = false;
        public boolean hasInstrumentFamily = false;
        public boolean hasInstrumentId = false;
        public boolean hasPurchaseCookie = false;
        public boolean hasSelectedInstrument = false;
        public Instrument instrument = null;
        public int instrumentFamily = 0;
        public String instrumentId = "";
        public Buy.LineItem[] item = Buy.LineItem.emptyArray();
        public String purchaseCookie = "";
        public boolean selectedInstrument = false;
        public Buy.LineItem[] subItem = Buy.LineItem.emptyArray();
        public Buy.LineItem summary = null;
        public Buy.LineItem total = null;
        
        public CheckoutOption()
        {
          this.cachedSize = -1;
        }
        
        public static CheckoutOption[] emptyArray()
        {
          if (_emptyArray == null) {}
          synchronized (InternalNano.LAZY_INIT_LOCK)
          {
            if (_emptyArray == null) {
              _emptyArray = new CheckoutOption[0];
            }
            return _emptyArray;
          }
        }
        
        protected final int computeSerializedSize()
        {
          int i = super.computeSerializedSize();
          if ((this.hasFormOfPayment) || (!this.formOfPayment.equals(""))) {
            i += CodedOutputByteBufferNano.computeStringSize(6, this.formOfPayment);
          }
          if ((this.hasEncodedAdjustedCart) || (!this.encodedAdjustedCart.equals(""))) {
            i += CodedOutputByteBufferNano.computeStringSize(7, this.encodedAdjustedCart);
          }
          if ((this.hasInstrumentId) || (!this.instrumentId.equals(""))) {
            i += CodedOutputByteBufferNano.computeStringSize(15, this.instrumentId);
          }
          if ((this.item != null) && (this.item.length > 0)) {
            for (int i7 = 0; i7 < this.item.length; i7++)
            {
              Buy.LineItem localLineItem2 = this.item[i7];
              if (localLineItem2 != null) {
                i += CodedOutputByteBufferNano.computeMessageSize(16, localLineItem2);
              }
            }
          }
          if ((this.subItem != null) && (this.subItem.length > 0)) {
            for (int i6 = 0; i6 < this.subItem.length; i6++)
            {
              Buy.LineItem localLineItem1 = this.subItem[i6];
              if (localLineItem1 != null) {
                i += CodedOutputByteBufferNano.computeMessageSize(17, localLineItem1);
              }
            }
          }
          if (this.total != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(18, this.total);
          }
          if ((this.footerHtml != null) && (this.footerHtml.length > 0))
          {
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < this.footerHtml.length; i5++)
            {
              String str3 = this.footerHtml[i5];
              if (str3 != null)
              {
                i3++;
                i4 += CodedOutputByteBufferNano.computeStringSizeNoTag(str3);
              }
            }
            i = i + i4 + i3 * 2;
          }
          if ((this.instrumentFamily != 0) || (this.hasInstrumentFamily)) {
            i += CodedOutputByteBufferNano.computeInt32Size(29, this.instrumentFamily);
          }
          if ((this.hasSelectedInstrument) || (this.selectedInstrument)) {
            i += 1 + CodedOutputByteBufferNano.computeTagSize(32);
          }
          if (this.summary != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(33, this.summary);
          }
          if ((this.footnoteHtml != null) && (this.footnoteHtml.length > 0))
          {
            int n = 0;
            int i1 = 0;
            for (int i2 = 0; i2 < this.footnoteHtml.length; i2++)
            {
              String str2 = this.footnoteHtml[i2];
              if (str2 != null)
              {
                n++;
                i1 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
              }
            }
            i = i + i1 + n * 2;
          }
          if (this.instrument != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(43, this.instrument);
          }
          if ((this.hasPurchaseCookie) || (!this.purchaseCookie.equals(""))) {
            i += CodedOutputByteBufferNano.computeStringSize(45, this.purchaseCookie);
          }
          if ((this.disabledReason != null) && (this.disabledReason.length > 0))
          {
            int j = 0;
            int k = 0;
            for (int m = 0; m < this.disabledReason.length; m++)
            {
              String str1 = this.disabledReason[m];
              if (str1 != null)
              {
                j++;
                k += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
              }
            }
            i = i + k + j * 2;
          }
          return i;
        }
        
        public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
          throws IOException
        {
          if ((this.hasFormOfPayment) || (!this.formOfPayment.equals(""))) {
            paramCodedOutputByteBufferNano.writeString(6, this.formOfPayment);
          }
          if ((this.hasEncodedAdjustedCart) || (!this.encodedAdjustedCart.equals(""))) {
            paramCodedOutputByteBufferNano.writeString(7, this.encodedAdjustedCart);
          }
          if ((this.hasInstrumentId) || (!this.instrumentId.equals(""))) {
            paramCodedOutputByteBufferNano.writeString(15, this.instrumentId);
          }
          if ((this.item != null) && (this.item.length > 0)) {
            for (int n = 0; n < this.item.length; n++)
            {
              Buy.LineItem localLineItem2 = this.item[n];
              if (localLineItem2 != null) {
                paramCodedOutputByteBufferNano.writeMessage(16, localLineItem2);
              }
            }
          }
          if ((this.subItem != null) && (this.subItem.length > 0)) {
            for (int m = 0; m < this.subItem.length; m++)
            {
              Buy.LineItem localLineItem1 = this.subItem[m];
              if (localLineItem1 != null) {
                paramCodedOutputByteBufferNano.writeMessage(17, localLineItem1);
              }
            }
          }
          if (this.total != null) {
            paramCodedOutputByteBufferNano.writeMessage(18, this.total);
          }
          if ((this.footerHtml != null) && (this.footerHtml.length > 0)) {
            for (int k = 0; k < this.footerHtml.length; k++)
            {
              String str3 = this.footerHtml[k];
              if (str3 != null) {
                paramCodedOutputByteBufferNano.writeString(19, str3);
              }
            }
          }
          if ((this.instrumentFamily != 0) || (this.hasInstrumentFamily)) {
            paramCodedOutputByteBufferNano.writeInt32(29, this.instrumentFamily);
          }
          if ((this.hasSelectedInstrument) || (this.selectedInstrument)) {
            paramCodedOutputByteBufferNano.writeBool(32, this.selectedInstrument);
          }
          if (this.summary != null) {
            paramCodedOutputByteBufferNano.writeMessage(33, this.summary);
          }
          if ((this.footnoteHtml != null) && (this.footnoteHtml.length > 0)) {
            for (int j = 0; j < this.footnoteHtml.length; j++)
            {
              String str2 = this.footnoteHtml[j];
              if (str2 != null) {
                paramCodedOutputByteBufferNano.writeString(35, str2);
              }
            }
          }
          if (this.instrument != null) {
            paramCodedOutputByteBufferNano.writeMessage(43, this.instrument);
          }
          if ((this.hasPurchaseCookie) || (!this.purchaseCookie.equals(""))) {
            paramCodedOutputByteBufferNano.writeString(45, this.purchaseCookie);
          }
          if ((this.disabledReason != null) && (this.disabledReason.length > 0)) {
            for (int i = 0; i < this.disabledReason.length; i++)
            {
              String str1 = this.disabledReason[i];
              if (str1 != null) {
                paramCodedOutputByteBufferNano.writeString(48, str1);
              }
            }
          }
          super.writeTo(paramCodedOutputByteBufferNano);
        }
      }
    }
  }
  
  public static final class LineItem
    extends MessageNano
  {
    private static volatile LineItem[] _emptyArray;
    public Buy.Money amount = null;
    public String description = "";
    public boolean hasDescription = false;
    public boolean hasName = false;
    public String name = "";
    public Common.Offer offer = null;
    
    public LineItem()
    {
      this.cachedSize = -1;
    }
    
    public static LineItem[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new LineItem[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasName) || (!this.name.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.name);
      }
      if ((this.hasDescription) || (!this.description.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.description);
      }
      if (this.offer != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.offer);
      }
      if (this.amount != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.amount);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasName) || (!this.name.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.name);
      }
      if ((this.hasDescription) || (!this.description.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.description);
      }
      if (this.offer != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.offer);
      }
      if (this.amount != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.amount);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Money
    extends MessageNano
  {
    public String currencyCode = "";
    public String formattedAmount = "";
    public boolean hasCurrencyCode = false;
    public boolean hasFormattedAmount = false;
    public boolean hasMicros = false;
    public long micros = 0L;
    
    public Money()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasMicros) || (this.micros != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.micros);
      }
      if ((this.hasCurrencyCode) || (!this.currencyCode.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.currencyCode);
      }
      if ((this.hasFormattedAmount) || (!this.formattedAmount.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.formattedAmount);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasMicros) || (this.micros != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.micros);
      }
      if ((this.hasCurrencyCode) || (!this.currencyCode.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.currencyCode);
      }
      if ((this.hasFormattedAmount) || (!this.formattedAmount.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.formattedAmount);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PurchaseNotificationResponse
    extends MessageNano
  {
    public DebugInfo debugInfo = null;
    public boolean hasLocalizedErrorMessage = false;
    public boolean hasPurchaseId = false;
    public boolean hasStatus = false;
    public String localizedErrorMessage = "";
    public String purchaseId = "";
    public int status = 0;
    
    public PurchaseNotificationResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.status != 0) || (this.hasStatus)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.status);
      }
      if (this.debugInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.debugInfo);
      }
      if ((this.hasLocalizedErrorMessage) || (!this.localizedErrorMessage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.localizedErrorMessage);
      }
      if ((this.hasPurchaseId) || (!this.purchaseId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.purchaseId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.status != 0) || (this.hasStatus)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.status);
      }
      if (this.debugInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.debugInfo);
      }
      if ((this.hasLocalizedErrorMessage) || (!this.localizedErrorMessage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.localizedErrorMessage);
      }
      if ((this.hasPurchaseId) || (!this.purchaseId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.purchaseId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PurchaseStatusResponse
    extends MessageNano
  {
    public AndroidAppDeliveryData appDeliveryData = null;
    public String briefMessage = "";
    public boolean hasBriefMessage = false;
    public boolean hasInfoUrl = false;
    public boolean hasStatus = false;
    public boolean hasStatusMsg = false;
    public boolean hasStatusTitle = false;
    public String infoUrl = "";
    public LibraryUpdateProto.LibraryUpdate libraryUpdate = null;
    public Instrument rejectedInstrument = null;
    public int status = 1;
    public String statusMsg = "";
    public String statusTitle = "";
    
    public PurchaseStatusResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.status != 1) || (this.hasStatus)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.status);
      }
      if ((this.hasStatusMsg) || (!this.statusMsg.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.statusMsg);
      }
      if ((this.hasStatusTitle) || (!this.statusTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.statusTitle);
      }
      if ((this.hasBriefMessage) || (!this.briefMessage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.briefMessage);
      }
      if ((this.hasInfoUrl) || (!this.infoUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.infoUrl);
      }
      if (this.libraryUpdate != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.libraryUpdate);
      }
      if (this.rejectedInstrument != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.rejectedInstrument);
      }
      if (this.appDeliveryData != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.appDeliveryData);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.status != 1) || (this.hasStatus)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.status);
      }
      if ((this.hasStatusMsg) || (!this.statusMsg.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.statusMsg);
      }
      if ((this.hasStatusTitle) || (!this.statusTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.statusTitle);
      }
      if ((this.hasBriefMessage) || (!this.briefMessage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.briefMessage);
      }
      if ((this.hasInfoUrl) || (!this.infoUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.infoUrl);
      }
      if (this.libraryUpdate != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.libraryUpdate);
      }
      if (this.rejectedInstrument != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.rejectedInstrument);
      }
      if (this.appDeliveryData != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.appDeliveryData);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Buy
 * JD-Core Version:    0.7.0.1
 */