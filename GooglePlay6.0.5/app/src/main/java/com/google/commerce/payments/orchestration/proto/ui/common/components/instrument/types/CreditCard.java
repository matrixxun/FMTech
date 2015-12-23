package com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types;

import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.AddressForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.AddressFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageSetOuterClass.LegalMessageSet;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface CreditCard
{
  public static final class BinRange
    extends MessageNano
  {
    private static volatile BinRange[] _emptyArray;
    public int cardNumberLength = 0;
    public int[] digitGrouping = WireFormatNano.EMPTY_INT_ARRAY;
    public String end = "";
    public String errorMessage = "";
    public String start = "";
    
    public BinRange()
    {
      this.cachedSize = -1;
    }
    
    public static BinRange[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new BinRange[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.start.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.start);
      }
      if (!this.end.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.end);
      }
      if (this.cardNumberLength != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.cardNumberLength);
      }
      if ((this.digitGrouping != null) && (this.digitGrouping.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.digitGrouping.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.digitGrouping[k]);
        }
        i = i + j + 1 * this.digitGrouping.length;
      }
      if (!this.errorMessage.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.errorMessage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.start.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.start);
      }
      if (!this.end.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.end);
      }
      if (this.cardNumberLength != 0) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.cardNumberLength);
      }
      if ((this.digitGrouping != null) && (this.digitGrouping.length > 0)) {
        for (int i = 0; i < this.digitGrouping.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(4, this.digitGrouping[i]);
        }
      }
      if (!this.errorMessage.equals("")) {
        paramCodedOutputByteBufferNano.writeString(5, this.errorMessage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CardType
    extends MessageNano
  {
    private static volatile CardType[] _emptyArray;
    public CreditCard.BinRange[] binRange = CreditCard.BinRange.emptyArray();
    public String cvcHintHeader = "";
    public ImageWithCaptionOuterClass.ImageWithCaption cvcHintImage = null;
    public String cvcHintText = "";
    public int cvcLength = 0;
    public ImageWithCaptionOuterClass.ImageWithCaption icon = null;
    public byte[] typeToken = WireFormatNano.EMPTY_BYTES;
    
    public CardType()
    {
      this.cachedSize = -1;
    }
    
    public static CardType[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new CardType[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.binRange != null) && (this.binRange.length > 0)) {
        for (int j = 0; j < this.binRange.length; j++)
        {
          CreditCard.BinRange localBinRange = this.binRange[j];
          if (localBinRange != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localBinRange);
          }
        }
      }
      if (this.cvcLength != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.cvcLength);
      }
      if (!Arrays.equals(this.typeToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(4, this.typeToken);
      }
      if (!this.cvcHintText.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.cvcHintText);
      }
      if (!this.cvcHintHeader.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.cvcHintHeader);
      }
      if (this.icon != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.icon);
      }
      if (this.cvcHintImage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.cvcHintImage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.binRange != null) && (this.binRange.length > 0)) {
        for (int i = 0; i < this.binRange.length; i++)
        {
          CreditCard.BinRange localBinRange = this.binRange[i];
          if (localBinRange != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localBinRange);
          }
        }
      }
      if (this.cvcLength != 0) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.cvcLength);
      }
      if (!Arrays.equals(this.typeToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(4, this.typeToken);
      }
      if (!this.cvcHintText.equals("")) {
        paramCodedOutputByteBufferNano.writeString(7, this.cvcHintText);
      }
      if (!this.cvcHintHeader.equals("")) {
        paramCodedOutputByteBufferNano.writeString(8, this.cvcHintHeader);
      }
      if (this.icon != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.icon);
      }
      if (this.cvcHintImage != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.cvcHintImage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CreditCardForm
    extends MessageNano
  {
    public boolean allowCameraInput = true;
    public CreditCard.CardType[] allowedCardType = CreditCard.CardType.emptyArray();
    public AddressFormOuterClass.AddressForm billingAddress = null;
    public int[] cameraInputPreference = WireFormatNano.EMPTY_INT_ARRAY;
    public String id = "";
    public CreditCard.CreditCardFormValue initialValue = null;
    public CreditCard.BinRange[] invalidBin = CreditCard.BinRange.emptyArray();
    public LegalMessageSetOuterClass.LegalMessageSet legalMessages = null;
    public int maxExpirationMonth = 0;
    public int maxExpirationYear = 0;
    public int minExpirationMonth = 0;
    public int minExpirationYear = 0;
    public int[] nfcInputPreference = WireFormatNano.EMPTY_INT_ARRAY;
    public String title = "";
    
    public CreditCardForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.billingAddress != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.billingAddress);
      }
      if (this.initialValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.initialValue);
      }
      if (this.legalMessages != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.legalMessages);
      }
      if (!this.id.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.id);
      }
      if ((this.allowedCardType != null) && (this.allowedCardType.length > 0)) {
        for (int i2 = 0; i2 < this.allowedCardType.length; i2++)
        {
          CreditCard.CardType localCardType = this.allowedCardType[i2];
          if (localCardType != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(6, localCardType);
          }
        }
      }
      if ((this.invalidBin != null) && (this.invalidBin.length > 0)) {
        for (int i1 = 0; i1 < this.invalidBin.length; i1++)
        {
          CreditCard.BinRange localBinRange = this.invalidBin[i1];
          if (localBinRange != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(7, localBinRange);
          }
        }
      }
      if (this.minExpirationMonth != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.minExpirationMonth);
      }
      if (this.minExpirationYear != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(9, this.minExpirationYear);
      }
      if (this.maxExpirationMonth != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(10, this.maxExpirationMonth);
      }
      if (this.maxExpirationYear != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(11, this.maxExpirationYear);
      }
      if (this.allowCameraInput != true) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(12);
      }
      if (!this.title.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(13, this.title);
      }
      if ((this.cameraInputPreference != null) && (this.cameraInputPreference.length > 0))
      {
        int m = 0;
        for (int n = 0; n < this.cameraInputPreference.length; n++) {
          m += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.cameraInputPreference[n]);
        }
        i = i + m + 1 * this.cameraInputPreference.length;
      }
      if ((this.nfcInputPreference != null) && (this.nfcInputPreference.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.nfcInputPreference.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.nfcInputPreference[k]);
        }
        i = i + j + 2 * this.nfcInputPreference.length;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.billingAddress != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.billingAddress);
      }
      if (this.initialValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.initialValue);
      }
      if (this.legalMessages != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.legalMessages);
      }
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(5, this.id);
      }
      if ((this.allowedCardType != null) && (this.allowedCardType.length > 0)) {
        for (int m = 0; m < this.allowedCardType.length; m++)
        {
          CreditCard.CardType localCardType = this.allowedCardType[m];
          if (localCardType != null) {
            paramCodedOutputByteBufferNano.writeMessage(6, localCardType);
          }
        }
      }
      if ((this.invalidBin != null) && (this.invalidBin.length > 0)) {
        for (int k = 0; k < this.invalidBin.length; k++)
        {
          CreditCard.BinRange localBinRange = this.invalidBin[k];
          if (localBinRange != null) {
            paramCodedOutputByteBufferNano.writeMessage(7, localBinRange);
          }
        }
      }
      if (this.minExpirationMonth != 0) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.minExpirationMonth);
      }
      if (this.minExpirationYear != 0) {
        paramCodedOutputByteBufferNano.writeInt32(9, this.minExpirationYear);
      }
      if (this.maxExpirationMonth != 0) {
        paramCodedOutputByteBufferNano.writeInt32(10, this.maxExpirationMonth);
      }
      if (this.maxExpirationYear != 0) {
        paramCodedOutputByteBufferNano.writeInt32(11, this.maxExpirationYear);
      }
      if (this.allowCameraInput != true) {
        paramCodedOutputByteBufferNano.writeBool(12, this.allowCameraInput);
      }
      if (!this.title.equals("")) {
        paramCodedOutputByteBufferNano.writeString(13, this.title);
      }
      if ((this.cameraInputPreference != null) && (this.cameraInputPreference.length > 0)) {
        for (int j = 0; j < this.cameraInputPreference.length; j++) {
          paramCodedOutputByteBufferNano.writeInt32(15, this.cameraInputPreference[j]);
        }
      }
      if ((this.nfcInputPreference != null) && (this.nfcInputPreference.length > 0)) {
        for (int i = 0; i < this.nfcInputPreference.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(16, this.nfcInputPreference[i]);
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CreditCardFormValue
    extends MessageNano
  {
    public AddressFormOuterClass.AddressFormValue billingAddress = null;
    public String cardNumber = "";
    public String cardholderName = "";
    public String cvc = "";
    public int expirationMonth = 0;
    public int expirationYear = 0;
    public String id = "";
    public CreditCardResultOuterClass.CreditCardInputResult[] inputResult = CreditCardResultOuterClass.CreditCardInputResult.emptyArray();
    public String lastFourDigits = "";
    public String legalDocData = "";
    public byte[] typeToken = WireFormatNano.EMPTY_BYTES;
    
    public CreditCardFormValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.cardNumber.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.cardNumber);
      }
      if (!this.cvc.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.cvc);
      }
      if (this.expirationMonth != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.expirationMonth);
      }
      if (this.expirationYear != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.expirationYear);
      }
      if (!this.lastFourDigits.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.lastFourDigits);
      }
      if (!this.cardholderName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.cardholderName);
      }
      if (this.billingAddress != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.billingAddress);
      }
      if (!Arrays.equals(this.typeToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(11, this.typeToken);
      }
      if (!this.legalDocData.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(12, this.legalDocData);
      }
      if (!this.id.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(13, this.id);
      }
      if ((this.inputResult != null) && (this.inputResult.length > 0)) {
        for (int j = 0; j < this.inputResult.length; j++)
        {
          CreditCardResultOuterClass.CreditCardInputResult localCreditCardInputResult = this.inputResult[j];
          if (localCreditCardInputResult != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(14, localCreditCardInputResult);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.cardNumber.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.cardNumber);
      }
      if (!this.cvc.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.cvc);
      }
      if (this.expirationMonth != 0) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.expirationMonth);
      }
      if (this.expirationYear != 0) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.expirationYear);
      }
      if (!this.lastFourDigits.equals("")) {
        paramCodedOutputByteBufferNano.writeString(6, this.lastFourDigits);
      }
      if (!this.cardholderName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(9, this.cardholderName);
      }
      if (this.billingAddress != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.billingAddress);
      }
      if (!Arrays.equals(this.typeToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(11, this.typeToken);
      }
      if (!this.legalDocData.equals("")) {
        paramCodedOutputByteBufferNano.writeString(12, this.legalDocData);
      }
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(13, this.id);
      }
      if ((this.inputResult != null) && (this.inputResult.length > 0)) {
        for (int i = 0; i < this.inputResult.length; i++)
        {
          CreditCardResultOuterClass.CreditCardInputResult localCreditCardInputResult = this.inputResult[i];
          if (localCreditCardInputResult != null) {
            paramCodedOutputByteBufferNano.writeMessage(14, localCreditCardInputResult);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CreditCardUpdateForm
    extends MessageNano
  {
    public AddressFormOuterClass.AddressForm billingAddress = null;
    public String cardLabel = "";
    public String cvcHintHeader = "";
    public ImageWithCaptionOuterClass.ImageWithCaption cvcHintImage = null;
    public String cvcHintText = "";
    public int cvcLength = 0;
    public int[] hiddenField = WireFormatNano.EMPTY_INT_ARRAY;
    public ImageWithCaptionOuterClass.ImageWithCaption icon = null;
    public String id = "";
    public CreditCard.CreditCardUpdateFormValue initialValue = null;
    public LegalMessageSetOuterClass.LegalMessageSet legalMessages = null;
    public int maxMonth = 0;
    public int maxYear = 0;
    public int minMonth = 0;
    public int minYear = 0;
    
    public CreditCardUpdateForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.cardLabel.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.cardLabel);
      }
      if (this.cvcLength != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.cvcLength);
      }
      if (this.minMonth != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.minMonth);
      }
      if (this.minYear != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.minYear);
      }
      if (this.maxMonth != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(6, this.maxMonth);
      }
      if (this.maxYear != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(7, this.maxYear);
      }
      if (!this.id.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.id);
      }
      if (this.icon != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.icon);
      }
      if ((this.hiddenField != null) && (this.hiddenField.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.hiddenField.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.hiddenField[k]);
        }
        i = i + j + 1 * this.hiddenField.length;
      }
      if (this.cvcHintImage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.cvcHintImage);
      }
      if (!this.cvcHintText.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(12, this.cvcHintText);
      }
      if (!this.cvcHintHeader.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(13, this.cvcHintHeader);
      }
      if (this.billingAddress != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(14, this.billingAddress);
      }
      if (this.initialValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(15, this.initialValue);
      }
      if (this.legalMessages != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(16, this.legalMessages);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.cardLabel.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.cardLabel);
      }
      if (this.cvcLength != 0) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.cvcLength);
      }
      if (this.minMonth != 0) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.minMonth);
      }
      if (this.minYear != 0) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.minYear);
      }
      if (this.maxMonth != 0) {
        paramCodedOutputByteBufferNano.writeInt32(6, this.maxMonth);
      }
      if (this.maxYear != 0) {
        paramCodedOutputByteBufferNano.writeInt32(7, this.maxYear);
      }
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(8, this.id);
      }
      if (this.icon != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.icon);
      }
      if ((this.hiddenField != null) && (this.hiddenField.length > 0)) {
        for (int i = 0; i < this.hiddenField.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(10, this.hiddenField[i]);
        }
      }
      if (this.cvcHintImage != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.cvcHintImage);
      }
      if (!this.cvcHintText.equals("")) {
        paramCodedOutputByteBufferNano.writeString(12, this.cvcHintText);
      }
      if (!this.cvcHintHeader.equals("")) {
        paramCodedOutputByteBufferNano.writeString(13, this.cvcHintHeader);
      }
      if (this.billingAddress != null) {
        paramCodedOutputByteBufferNano.writeMessage(14, this.billingAddress);
      }
      if (this.initialValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(15, this.initialValue);
      }
      if (this.legalMessages != null) {
        paramCodedOutputByteBufferNano.writeMessage(16, this.legalMessages);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CreditCardUpdateFormValue
    extends MessageNano
  {
    public AddressFormOuterClass.AddressFormValue billingAddress = null;
    public String cardholderName = "";
    public String cvc = "";
    public String legalDocData = "";
    public int newMonth = 0;
    public int newYear = 0;
    
    public CreditCardUpdateFormValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.newMonth != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.newMonth);
      }
      if (this.newYear != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.newYear);
      }
      if (!this.cvc.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.cvc);
      }
      if (this.billingAddress != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.billingAddress);
      }
      if (!this.legalDocData.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.legalDocData);
      }
      if (!this.cardholderName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.cardholderName);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.newMonth != 0) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.newMonth);
      }
      if (this.newYear != 0) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.newYear);
      }
      if (!this.cvc.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.cvc);
      }
      if (this.billingAddress != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.billingAddress);
      }
      if (!this.legalDocData.equals("")) {
        paramCodedOutputByteBufferNano.writeString(6, this.legalDocData);
      }
      if (!this.cardholderName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(7, this.cardholderName);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard
 * JD-Core Version:    0.7.0.1
 */