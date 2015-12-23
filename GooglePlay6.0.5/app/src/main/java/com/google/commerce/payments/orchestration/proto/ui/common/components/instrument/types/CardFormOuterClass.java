package com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types;

import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.AddressForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.AddressFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.FormHeaderOuterClass.FormHeader;
import com.google.commerce.payments.orchestration.proto.ui.common.components.OtpFieldOuterClass.OtpField;
import com.google.commerce.payments.orchestration.proto.ui.common.components.OtpFieldOuterClass.OtpFieldValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.FormattingSchemesOuterClass.TemplateFormattingScheme;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldGroupingOuterClass.UiFieldGrouping;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiFieldValue;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface CardFormOuterClass
{
  public static final class CardField
    extends MessageNano
  {
    private static volatile CardField[] _emptyArray;
    public AddressFormOuterClass.AddressForm addressForm = null;
    public boolean hideFieldsBelow = false;
    public OtpFieldOuterClass.OtpField otpField = null;
    public UiFieldOuterClass.UiField uiField = null;
    
    public CardField()
    {
      this.cachedSize = -1;
    }
    
    public static CardField[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new CardField[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.uiField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.uiField);
      }
      if (this.addressForm != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.addressForm);
      }
      if (this.otpField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.otpField);
      }
      if (this.hideFieldsBelow) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.uiField != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.uiField);
      }
      if (this.addressForm != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.addressForm);
      }
      if (this.otpField != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.otpField);
      }
      if (this.hideFieldsBelow) {
        paramCodedOutputByteBufferNano.writeBool(6, this.hideFieldsBelow);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CardFieldValue
    extends MessageNano
  {
    private static volatile CardFieldValue[] _emptyArray;
    public AddressFormOuterClass.AddressFormValue addressFormValue = null;
    public OtpFieldOuterClass.OtpFieldValue otpFieldValue = null;
    public UiFieldOuterClass.UiFieldValue uiFieldValue = null;
    
    public CardFieldValue()
    {
      this.cachedSize = -1;
    }
    
    public static CardFieldValue[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new CardFieldValue[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.uiFieldValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.uiFieldValue);
      }
      if (this.addressFormValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.addressFormValue);
      }
      if (this.otpFieldValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.otpFieldValue);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.uiFieldValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.uiFieldValue);
      }
      if (this.addressFormValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.addressFormValue);
      }
      if (this.otpFieldValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.otpFieldValue);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CardForm
    extends MessageNano
  {
    public CardFormOuterClass.PanCategory[] allowedPanCategory = CardFormOuterClass.PanCategory.emptyArray();
    public int[] cameraInputUiPreference = WireFormatNano.EMPTY_INT_ARRAY;
    public CardFormOuterClass.CardSubform[] cardSubform = CardFormOuterClass.CardSubform.emptyArray();
    public CardFormOuterClass.ExcludedPanCategory[] excludedPanCategory = CardFormOuterClass.ExcludedPanCategory.emptyArray();
    public FormHeaderOuterClass.FormHeader formHeader = null;
    public CardFormOuterClass.CardFormValue initialValue = null;
    public String invalidPanMessage = "";
    public ImageWithCaptionOuterClass.ImageWithCaption[] logo = ImageWithCaptionOuterClass.ImageWithCaption.emptyArray();
    public int logoUiPreference = 0;
    public int[] nfcInputPreference = WireFormatNano.EMPTY_INT_ARRAY;
    public String noMatchPanMessage = "";
    public int primaryAccountNumberUiReference = 0;
    public int resolvedOnlyLogoStartingIndex = -1;
    
    public CardForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.formHeader != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.formHeader);
      }
      if (this.initialValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.initialValue);
      }
      if ((this.cardSubform != null) && (this.cardSubform.length > 0)) {
        for (int i4 = 0; i4 < this.cardSubform.length; i4++)
        {
          CardFormOuterClass.CardSubform localCardSubform = this.cardSubform[i4];
          if (localCardSubform != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localCardSubform);
          }
        }
      }
      if ((this.logo != null) && (this.logo.length > 0)) {
        for (int i3 = 0; i3 < this.logo.length; i3++)
        {
          ImageWithCaptionOuterClass.ImageWithCaption localImageWithCaption = this.logo[i3];
          if (localImageWithCaption != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localImageWithCaption);
          }
        }
      }
      if ((this.cameraInputUiPreference != null) && (this.cameraInputUiPreference.length > 0))
      {
        int i1 = 0;
        for (int i2 = 0; i2 < this.cameraInputUiPreference.length; i2++) {
          i1 += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.cameraInputUiPreference[i2]);
        }
        i = i + i1 + 1 * this.cameraInputUiPreference.length;
      }
      if ((this.allowedPanCategory != null) && (this.allowedPanCategory.length > 0)) {
        for (int n = 0; n < this.allowedPanCategory.length; n++)
        {
          CardFormOuterClass.PanCategory localPanCategory = this.allowedPanCategory[n];
          if (localPanCategory != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(7, localPanCategory);
          }
        }
      }
      if (!this.invalidPanMessage.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.invalidPanMessage);
      }
      if ((this.excludedPanCategory != null) && (this.excludedPanCategory.length > 0)) {
        for (int m = 0; m < this.excludedPanCategory.length; m++)
        {
          CardFormOuterClass.ExcludedPanCategory localExcludedPanCategory = this.excludedPanCategory[m];
          if (localExcludedPanCategory != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(9, localExcludedPanCategory);
          }
        }
      }
      if (this.primaryAccountNumberUiReference != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(10, this.primaryAccountNumberUiReference);
      }
      if ((this.nfcInputPreference != null) && (this.nfcInputPreference.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.nfcInputPreference.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.nfcInputPreference[k]);
        }
        i = i + j + 1 * this.nfcInputPreference.length;
      }
      if (this.logoUiPreference != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(13, this.logoUiPreference);
      }
      if (!this.noMatchPanMessage.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(14, this.noMatchPanMessage);
      }
      if (this.resolvedOnlyLogoStartingIndex != -1) {
        i += CodedOutputByteBufferNano.computeInt32Size(15, this.resolvedOnlyLogoStartingIndex);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.formHeader != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.formHeader);
      }
      if (this.initialValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.initialValue);
      }
      if ((this.cardSubform != null) && (this.cardSubform.length > 0)) {
        for (int i1 = 0; i1 < this.cardSubform.length; i1++)
        {
          CardFormOuterClass.CardSubform localCardSubform = this.cardSubform[i1];
          if (localCardSubform != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localCardSubform);
          }
        }
      }
      if ((this.logo != null) && (this.logo.length > 0)) {
        for (int n = 0; n < this.logo.length; n++)
        {
          ImageWithCaptionOuterClass.ImageWithCaption localImageWithCaption = this.logo[n];
          if (localImageWithCaption != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localImageWithCaption);
          }
        }
      }
      if ((this.cameraInputUiPreference != null) && (this.cameraInputUiPreference.length > 0)) {
        for (int m = 0; m < this.cameraInputUiPreference.length; m++) {
          paramCodedOutputByteBufferNano.writeInt32(6, this.cameraInputUiPreference[m]);
        }
      }
      if ((this.allowedPanCategory != null) && (this.allowedPanCategory.length > 0)) {
        for (int k = 0; k < this.allowedPanCategory.length; k++)
        {
          CardFormOuterClass.PanCategory localPanCategory = this.allowedPanCategory[k];
          if (localPanCategory != null) {
            paramCodedOutputByteBufferNano.writeMessage(7, localPanCategory);
          }
        }
      }
      if (!this.invalidPanMessage.equals("")) {
        paramCodedOutputByteBufferNano.writeString(8, this.invalidPanMessage);
      }
      if ((this.excludedPanCategory != null) && (this.excludedPanCategory.length > 0)) {
        for (int j = 0; j < this.excludedPanCategory.length; j++)
        {
          CardFormOuterClass.ExcludedPanCategory localExcludedPanCategory = this.excludedPanCategory[j];
          if (localExcludedPanCategory != null) {
            paramCodedOutputByteBufferNano.writeMessage(9, localExcludedPanCategory);
          }
        }
      }
      if (this.primaryAccountNumberUiReference != 0) {
        paramCodedOutputByteBufferNano.writeInt32(10, this.primaryAccountNumberUiReference);
      }
      if ((this.nfcInputPreference != null) && (this.nfcInputPreference.length > 0)) {
        for (int i = 0; i < this.nfcInputPreference.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(11, this.nfcInputPreference[i]);
        }
      }
      if (this.logoUiPreference != 0) {
        paramCodedOutputByteBufferNano.writeInt32(13, this.logoUiPreference);
      }
      if (!this.noMatchPanMessage.equals("")) {
        paramCodedOutputByteBufferNano.writeString(14, this.noMatchPanMessage);
      }
      if (this.resolvedOnlyLogoStartingIndex != -1) {
        paramCodedOutputByteBufferNano.writeInt32(15, this.resolvedOnlyLogoStartingIndex);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CardFormValue
    extends MessageNano
  {
    public String accountNumber = "";
    public CardFormOuterClass.CardSubformValue[] cardSubformValue = CardFormOuterClass.CardSubformValue.emptyArray();
    public String id = "";
    public CreditCardResultOuterClass.CreditCardInputResult[] inputResult = CreditCardResultOuterClass.CreditCardInputResult.emptyArray();
    public byte[] panCategoryToken = WireFormatNano.EMPTY_BYTES;
    public byte[] token = WireFormatNano.EMPTY_BYTES;
    
    public CardFormValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.id.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.id);
      }
      if (!Arrays.equals(this.token, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.token);
      }
      if (!this.accountNumber.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.accountNumber);
      }
      if ((this.cardSubformValue != null) && (this.cardSubformValue.length > 0)) {
        for (int k = 0; k < this.cardSubformValue.length; k++)
        {
          CardFormOuterClass.CardSubformValue localCardSubformValue = this.cardSubformValue[k];
          if (localCardSubformValue != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(5, localCardSubformValue);
          }
        }
      }
      if ((this.inputResult != null) && (this.inputResult.length > 0)) {
        for (int j = 0; j < this.inputResult.length; j++)
        {
          CreditCardResultOuterClass.CreditCardInputResult localCreditCardInputResult = this.inputResult[j];
          if (localCreditCardInputResult != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(6, localCreditCardInputResult);
          }
        }
      }
      if (!Arrays.equals(this.panCategoryToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(7, this.panCategoryToken);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.id);
      }
      if (!Arrays.equals(this.token, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.token);
      }
      if (!this.accountNumber.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.accountNumber);
      }
      if ((this.cardSubformValue != null) && (this.cardSubformValue.length > 0)) {
        for (int j = 0; j < this.cardSubformValue.length; j++)
        {
          CardFormOuterClass.CardSubformValue localCardSubformValue = this.cardSubformValue[j];
          if (localCardSubformValue != null) {
            paramCodedOutputByteBufferNano.writeMessage(5, localCardSubformValue);
          }
        }
      }
      if ((this.inputResult != null) && (this.inputResult.length > 0)) {
        for (int i = 0; i < this.inputResult.length; i++)
        {
          CreditCardResultOuterClass.CreditCardInputResult localCreditCardInputResult = this.inputResult[i];
          if (localCreditCardInputResult != null) {
            paramCodedOutputByteBufferNano.writeMessage(6, localCreditCardInputResult);
          }
        }
      }
      if (!Arrays.equals(this.panCategoryToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(7, this.panCategoryToken);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CardSubform
    extends MessageNano
  {
    private static volatile CardSubform[] _emptyArray;
    public CardFormOuterClass.CardField[] cardField = CardFormOuterClass.CardField.emptyArray();
    public int collapsibleFieldsEndIndex = -1;
    public UiFieldGroupingOuterClass.UiFieldGrouping[] fieldGrouping = UiFieldGroupingOuterClass.UiFieldGrouping.emptyArray();
    public FormHeaderOuterClass.FormHeader formHeader = null;
    public CardFormOuterClass.CardSubformValue initialValue = null;
    public LegalMessageOuterClass.LegalMessage legalMessage = null;
    
    public CardSubform()
    {
      this.cachedSize = -1;
    }
    
    public static CardSubform[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new CardSubform[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.formHeader != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.formHeader);
      }
      if (this.initialValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.initialValue);
      }
      if ((this.cardField != null) && (this.cardField.length > 0)) {
        for (int k = 0; k < this.cardField.length; k++)
        {
          CardFormOuterClass.CardField localCardField = this.cardField[k];
          if (localCardField != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localCardField);
          }
        }
      }
      if ((this.fieldGrouping != null) && (this.fieldGrouping.length > 0)) {
        for (int j = 0; j < this.fieldGrouping.length; j++)
        {
          UiFieldGroupingOuterClass.UiFieldGrouping localUiFieldGrouping = this.fieldGrouping[j];
          if (localUiFieldGrouping != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localUiFieldGrouping);
          }
        }
      }
      if (this.legalMessage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.legalMessage);
      }
      if (this.collapsibleFieldsEndIndex != -1) {
        i += CodedOutputByteBufferNano.computeInt32Size(7, this.collapsibleFieldsEndIndex);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.formHeader != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.formHeader);
      }
      if (this.initialValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.initialValue);
      }
      if ((this.cardField != null) && (this.cardField.length > 0)) {
        for (int j = 0; j < this.cardField.length; j++)
        {
          CardFormOuterClass.CardField localCardField = this.cardField[j];
          if (localCardField != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localCardField);
          }
        }
      }
      if ((this.fieldGrouping != null) && (this.fieldGrouping.length > 0)) {
        for (int i = 0; i < this.fieldGrouping.length; i++)
        {
          UiFieldGroupingOuterClass.UiFieldGrouping localUiFieldGrouping = this.fieldGrouping[i];
          if (localUiFieldGrouping != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localUiFieldGrouping);
          }
        }
      }
      if (this.legalMessage != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.legalMessage);
      }
      if (this.collapsibleFieldsEndIndex != -1) {
        paramCodedOutputByteBufferNano.writeInt32(7, this.collapsibleFieldsEndIndex);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CardSubformValue
    extends MessageNano
  {
    private static volatile CardSubformValue[] _emptyArray;
    public CardFormOuterClass.CardFieldValue[] cardFieldValue = CardFormOuterClass.CardFieldValue.emptyArray();
    public String id = "";
    public String legalDocData = "";
    public byte[] token = WireFormatNano.EMPTY_BYTES;
    
    public CardSubformValue()
    {
      this.cachedSize = -1;
    }
    
    public static CardSubformValue[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new CardSubformValue[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.id.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.id);
      }
      if (!Arrays.equals(this.token, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.token);
      }
      if ((this.cardFieldValue != null) && (this.cardFieldValue.length > 0)) {
        for (int j = 0; j < this.cardFieldValue.length; j++)
        {
          CardFormOuterClass.CardFieldValue localCardFieldValue = this.cardFieldValue[j];
          if (localCardFieldValue != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localCardFieldValue);
          }
        }
      }
      if (!this.legalDocData.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.legalDocData);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.id);
      }
      if (!Arrays.equals(this.token, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.token);
      }
      if ((this.cardFieldValue != null) && (this.cardFieldValue.length > 0)) {
        for (int i = 0; i < this.cardFieldValue.length; i++)
        {
          CardFormOuterClass.CardFieldValue localCardFieldValue = this.cardFieldValue[i];
          if (localCardFieldValue != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localCardFieldValue);
          }
        }
      }
      if (!this.legalDocData.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.legalDocData);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ExcludedPanCategory
    extends MessageNano
  {
    private static volatile ExcludedPanCategory[] _emptyArray;
    public String errorMessage = "";
    public CardFormOuterClass.PanPrefixSet[] prefixSet = CardFormOuterClass.PanPrefixSet.emptyArray();
    
    public ExcludedPanCategory()
    {
      this.cachedSize = -1;
    }
    
    public static ExcludedPanCategory[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ExcludedPanCategory[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.prefixSet != null) && (this.prefixSet.length > 0)) {
        for (int j = 0; j < this.prefixSet.length; j++)
        {
          CardFormOuterClass.PanPrefixSet localPanPrefixSet = this.prefixSet[j];
          if (localPanPrefixSet != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localPanPrefixSet);
          }
        }
      }
      if (!this.errorMessage.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.errorMessage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.prefixSet != null) && (this.prefixSet.length > 0)) {
        for (int i = 0; i < this.prefixSet.length; i++)
        {
          CardFormOuterClass.PanPrefixSet localPanPrefixSet = this.prefixSet[i];
          if (localPanPrefixSet != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localPanPrefixSet);
          }
        }
      }
      if (!this.errorMessage.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.errorMessage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PanCategory
    extends MessageNano
  {
    private static volatile PanCategory[] _emptyArray;
    public int[] cardSubformIndex = WireFormatNano.EMPTY_INT_ARRAY;
    public int concealedVisibleDigitsCount = 0;
    public FormattingSchemesOuterClass.TemplateFormattingScheme format = null;
    public int logoIndex = 0;
    public int minLength = -1;
    public byte[] panCategoryToken = WireFormatNano.EMPTY_BYTES;
    public int panChecksumType = 0;
    public CardFormOuterClass.PanPrefixSet[] prefixSet = CardFormOuterClass.PanPrefixSet.emptyArray();
    
    public PanCategory()
    {
      this.cachedSize = -1;
    }
    
    public static PanCategory[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new PanCategory[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.prefixSet != null) && (this.prefixSet.length > 0)) {
        for (int m = 0; m < this.prefixSet.length; m++)
        {
          CardFormOuterClass.PanPrefixSet localPanPrefixSet = this.prefixSet[m];
          if (localPanPrefixSet != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localPanPrefixSet);
          }
        }
      }
      if (!Arrays.equals(this.panCategoryToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.panCategoryToken);
      }
      if (this.format != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.format);
      }
      if (this.logoIndex != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.logoIndex);
      }
      if ((this.cardSubformIndex != null) && (this.cardSubformIndex.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.cardSubformIndex.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.cardSubformIndex[k]);
        }
        i = 1 + (i + j) + CodedOutputByteBufferNano.computeRawVarint32Size(j);
      }
      if (this.panChecksumType != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(6, this.panChecksumType);
      }
      if (this.concealedVisibleDigitsCount != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(7, this.concealedVisibleDigitsCount);
      }
      if (this.minLength != -1) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.minLength);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.prefixSet != null) && (this.prefixSet.length > 0)) {
        for (int m = 0; m < this.prefixSet.length; m++)
        {
          CardFormOuterClass.PanPrefixSet localPanPrefixSet = this.prefixSet[m];
          if (localPanPrefixSet != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localPanPrefixSet);
          }
        }
      }
      if (!Arrays.equals(this.panCategoryToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.panCategoryToken);
      }
      if (this.format != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.format);
      }
      if (this.logoIndex != 0) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.logoIndex);
      }
      if ((this.cardSubformIndex != null) && (this.cardSubformIndex.length > 0))
      {
        int i = 0;
        for (int j = 0; j < this.cardSubformIndex.length; j++) {
          i += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.cardSubformIndex[j]);
        }
        paramCodedOutputByteBufferNano.writeRawVarint32(42);
        paramCodedOutputByteBufferNano.writeRawVarint32(i);
        for (int k = 0; k < this.cardSubformIndex.length; k++) {
          paramCodedOutputByteBufferNano.writeInt32NoTag(this.cardSubformIndex[k]);
        }
      }
      if (this.panChecksumType != 0) {
        paramCodedOutputByteBufferNano.writeInt32(6, this.panChecksumType);
      }
      if (this.concealedVisibleDigitsCount != 0) {
        paramCodedOutputByteBufferNano.writeInt32(7, this.concealedVisibleDigitsCount);
      }
      if (this.minLength != -1) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.minLength);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PanPrefixSet
    extends MessageNano
  {
    private static volatile PanPrefixSet[] _emptyArray;
    public long firstDigits = 0L;
    public int[] lastDigitBitset = WireFormatNano.EMPTY_INT_ARRAY;
    public int length = 0;
    public long[] middleDigits = WireFormatNano.EMPTY_LONG_ARRAY;
    
    public PanPrefixSet()
    {
      this.cachedSize = -1;
    }
    
    public static PanPrefixSet[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new PanPrefixSet[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.length != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.length);
      }
      if (this.firstDigits != 0L) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.firstDigits);
      }
      if ((this.middleDigits != null) && (this.middleDigits.length > 0))
      {
        int m = 0;
        for (int n = 0; n < this.middleDigits.length; n++) {
          m += CodedOutputByteBufferNano.computeRawVarint64Size(this.middleDigits[n]);
        }
        i = 1 + (i + m) + CodedOutputByteBufferNano.computeRawVarint32Size(m);
      }
      if ((this.lastDigitBitset != null) && (this.lastDigitBitset.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.lastDigitBitset.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.lastDigitBitset[k]);
        }
        i = 1 + (i + j) + CodedOutputByteBufferNano.computeRawVarint32Size(j);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.length != 0) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.length);
      }
      if (this.firstDigits != 0L) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.firstDigits);
      }
      if ((this.middleDigits != null) && (this.middleDigits.length > 0))
      {
        int m = 0;
        for (int n = 0; n < this.middleDigits.length; n++) {
          m += CodedOutputByteBufferNano.computeRawVarint64Size(this.middleDigits[n]);
        }
        paramCodedOutputByteBufferNano.writeRawVarint32(26);
        paramCodedOutputByteBufferNano.writeRawVarint32(m);
        for (int i1 = 0; i1 < this.middleDigits.length; i1++) {
          paramCodedOutputByteBufferNano.writeRawVarint64(this.middleDigits[i1]);
        }
      }
      if ((this.lastDigitBitset != null) && (this.lastDigitBitset.length > 0))
      {
        int i = 0;
        for (int j = 0; j < this.lastDigitBitset.length; j++) {
          i += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.lastDigitBitset[j]);
        }
        paramCodedOutputByteBufferNano.writeRawVarint32(34);
        paramCodedOutputByteBufferNano.writeRawVarint32(i);
        for (int k = 0; k < this.lastDigitBitset.length; k++) {
          paramCodedOutputByteBufferNano.writeInt32NoTag(this.lastDigitBitset[k]);
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass
 * JD-Core Version:    0.7.0.1
 */