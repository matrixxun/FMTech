package com.google.commerce.payments.orchestration.proto.ui.common.components;

import com.google.location.country.Postaladdress.PostalAddress;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface AddressFormOuterClass
{
  public static final class AddressForm
    extends MessageNano
  {
    public int[] addressAutocompleteOption = WireFormatNano.EMPTY_INT_ARRAY;
    public String[] allowedCountryCode = WireFormatNano.EMPTY_STRING_ARRAY;
    public AddressFormOuterClass.DisplayAddress[] availableOption = AddressFormOuterClass.DisplayAddress.emptyArray();
    public byte[] dataToken = WireFormatNano.EMPTY_BYTES;
    public int editability = 0;
    public int[] hiddenField = WireFormatNano.EMPTY_INT_ARRAY;
    public String hideFormFieldsToggleLabel = "";
    public String id = "";
    public int initialAvailableOptionIndex = 0;
    public String initialCountryI18NDataJson = "";
    public AddressFormOuterClass.AddressFormValue initialValue = null;
    public boolean phoneNumberForm = false;
    public String[] postalCodeOnlyCountryCode = WireFormatNano.EMPTY_STRING_ARRAY;
    public int[] readOnlyField = WireFormatNano.EMPTY_INT_ARRAY;
    public String recipientLabel = "";
    public String title = "";
    public int uiReference = 0;
    
    public AddressForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.allowedCountryCode != null) && (this.allowedCountryCode.length > 0))
      {
        int i7 = 0;
        int i8 = 0;
        for (int i9 = 0; i9 < this.allowedCountryCode.length; i9++)
        {
          String str2 = this.allowedCountryCode[i9];
          if (str2 != null)
          {
            i7++;
            i8 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
          }
        }
        i = i + i8 + i7 * 1;
      }
      if (this.phoneNumberForm) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      if (!this.id.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.id);
      }
      if (this.initialValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.initialValue);
      }
      if ((this.postalCodeOnlyCountryCode != null) && (this.postalCodeOnlyCountryCode.length > 0))
      {
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < this.postalCodeOnlyCountryCode.length; i6++)
        {
          String str1 = this.postalCodeOnlyCountryCode[i6];
          if (str1 != null)
          {
            i4++;
            i5 += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
          }
        }
        i = i + i5 + i4 * 1;
      }
      if (!this.recipientLabel.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.recipientLabel);
      }
      if (!this.hideFormFieldsToggleLabel.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(11, this.hideFormFieldsToggleLabel);
      }
      if ((this.readOnlyField != null) && (this.readOnlyField.length > 0))
      {
        int i2 = 0;
        for (int i3 = 0; i3 < this.readOnlyField.length; i3++) {
          i2 += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.readOnlyField[i3]);
        }
        i = i + i2 + 1 * this.readOnlyField.length;
      }
      if (!this.title.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(13, this.title);
      }
      if ((this.hiddenField != null) && (this.hiddenField.length > 0))
      {
        int n = 0;
        for (int i1 = 0; i1 < this.hiddenField.length; i1++) {
          n += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.hiddenField[i1]);
        }
        i = i + n + 1 * this.hiddenField.length;
      }
      if (!this.initialCountryI18NDataJson.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(15, this.initialCountryI18NDataJson);
      }
      if (this.editability != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(20, this.editability);
      }
      if ((this.availableOption != null) && (this.availableOption.length > 0)) {
        for (int m = 0; m < this.availableOption.length; m++)
        {
          AddressFormOuterClass.DisplayAddress localDisplayAddress = this.availableOption[m];
          if (localDisplayAddress != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(21, localDisplayAddress);
          }
        }
      }
      if (this.uiReference != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(25, this.uiReference);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(26, this.dataToken);
      }
      if ((this.addressAutocompleteOption != null) && (this.addressAutocompleteOption.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.addressAutocompleteOption.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.addressAutocompleteOption[k]);
        }
        i = 2 + (i + j) + CodedOutputByteBufferNano.computeRawVarint32Size(j);
      }
      if (this.initialAvailableOptionIndex != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(32, this.initialAvailableOptionIndex);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.allowedCountryCode != null) && (this.allowedCountryCode.length > 0)) {
        for (int i3 = 0; i3 < this.allowedCountryCode.length; i3++)
        {
          String str2 = this.allowedCountryCode[i3];
          if (str2 != null) {
            paramCodedOutputByteBufferNano.writeString(4, str2);
          }
        }
      }
      if (this.phoneNumberForm) {
        paramCodedOutputByteBufferNano.writeBool(5, this.phoneNumberForm);
      }
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(6, this.id);
      }
      if (this.initialValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.initialValue);
      }
      if ((this.postalCodeOnlyCountryCode != null) && (this.postalCodeOnlyCountryCode.length > 0)) {
        for (int i2 = 0; i2 < this.postalCodeOnlyCountryCode.length; i2++)
        {
          String str1 = this.postalCodeOnlyCountryCode[i2];
          if (str1 != null) {
            paramCodedOutputByteBufferNano.writeString(9, str1);
          }
        }
      }
      if (!this.recipientLabel.equals("")) {
        paramCodedOutputByteBufferNano.writeString(10, this.recipientLabel);
      }
      if (!this.hideFormFieldsToggleLabel.equals("")) {
        paramCodedOutputByteBufferNano.writeString(11, this.hideFormFieldsToggleLabel);
      }
      if ((this.readOnlyField != null) && (this.readOnlyField.length > 0)) {
        for (int i1 = 0; i1 < this.readOnlyField.length; i1++) {
          paramCodedOutputByteBufferNano.writeInt32(12, this.readOnlyField[i1]);
        }
      }
      if (!this.title.equals("")) {
        paramCodedOutputByteBufferNano.writeString(13, this.title);
      }
      if ((this.hiddenField != null) && (this.hiddenField.length > 0)) {
        for (int n = 0; n < this.hiddenField.length; n++) {
          paramCodedOutputByteBufferNano.writeInt32(14, this.hiddenField[n]);
        }
      }
      if (!this.initialCountryI18NDataJson.equals("")) {
        paramCodedOutputByteBufferNano.writeString(15, this.initialCountryI18NDataJson);
      }
      if (this.editability != 0) {
        paramCodedOutputByteBufferNano.writeInt32(20, this.editability);
      }
      if ((this.availableOption != null) && (this.availableOption.length > 0)) {
        for (int m = 0; m < this.availableOption.length; m++)
        {
          AddressFormOuterClass.DisplayAddress localDisplayAddress = this.availableOption[m];
          if (localDisplayAddress != null) {
            paramCodedOutputByteBufferNano.writeMessage(21, localDisplayAddress);
          }
        }
      }
      if (this.uiReference != 0) {
        paramCodedOutputByteBufferNano.writeInt32(25, this.uiReference);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(26, this.dataToken);
      }
      if ((this.addressAutocompleteOption != null) && (this.addressAutocompleteOption.length > 0))
      {
        int i = 0;
        for (int j = 0; j < this.addressAutocompleteOption.length; j++) {
          i += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.addressAutocompleteOption[j]);
        }
        paramCodedOutputByteBufferNano.writeRawVarint32(242);
        paramCodedOutputByteBufferNano.writeRawVarint32(i);
        for (int k = 0; k < this.addressAutocompleteOption.length; k++) {
          paramCodedOutputByteBufferNano.writeRawVarint32(this.addressAutocompleteOption[k]);
        }
      }
      if (this.initialAvailableOptionIndex != 0) {
        paramCodedOutputByteBufferNano.writeInt32(32, this.initialAvailableOptionIndex);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class AddressFormValue
    extends MessageNano
  {
    public Postaladdress.PostalAddress address = null;
    public byte[] dataToken = WireFormatNano.EMPTY_BYTES;
    public String id = "";
    public boolean isHidden = false;
    public String phoneNumber = "";
    
    public AddressFormValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.address != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.address);
      }
      if (!this.phoneNumber.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.phoneNumber);
      }
      if (this.isHidden) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if (!this.id.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.id);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(7, this.dataToken);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.address != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.address);
      }
      if (!this.phoneNumber.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.phoneNumber);
      }
      if (this.isHidden) {
        paramCodedOutputByteBufferNano.writeBool(3, this.isHidden);
      }
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(5, this.id);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(7, this.dataToken);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CountrySelector
    extends MessageNano
  {
    public String[] allowedCountryCode = WireFormatNano.EMPTY_STRING_ARRAY;
    public FormHeaderOuterClass.FormHeader formHeader = null;
    public String initialCountryCode = "";
    
    public CountrySelector()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.allowedCountryCode != null) && (this.allowedCountryCode.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.allowedCountryCode.length; m++)
        {
          String str = this.allowedCountryCode[m];
          if (str != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + k + j * 1;
      }
      if (!this.initialCountryCode.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.initialCountryCode);
      }
      if (this.formHeader != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.formHeader);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.allowedCountryCode != null) && (this.allowedCountryCode.length > 0)) {
        for (int i = 0; i < this.allowedCountryCode.length; i++)
        {
          String str = this.allowedCountryCode[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(2, str);
          }
        }
      }
      if (!this.initialCountryCode.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.initialCountryCode);
      }
      if (this.formHeader != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.formHeader);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CountrySelectorValue
    extends MessageNano
  {
    public String countryCode = "";
    public byte[] dataToken = WireFormatNano.EMPTY_BYTES;
    public String id = "";
    
    public CountrySelectorValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.id.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.id);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.dataToken);
      }
      if (!this.countryCode.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.countryCode);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.id);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.dataToken);
      }
      if (!this.countryCode.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.countryCode);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DisplayAddress
    extends MessageNano
  {
    private static volatile DisplayAddress[] _emptyArray;
    public AddressFormOuterClass.AddressFormValue addressFormValue = null;
    
    public DisplayAddress()
    {
      this.cachedSize = -1;
    }
    
    public static DisplayAddress[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new DisplayAddress[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.addressFormValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.addressFormValue);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.addressFormValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.addressFormValue);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass
 * JD-Core Version:    0.7.0.1
 */