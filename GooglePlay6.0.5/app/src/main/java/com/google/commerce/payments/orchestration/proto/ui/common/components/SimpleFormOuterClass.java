package com.google.commerce.payments.orchestration.proto.ui.common.components;

import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiFieldValue;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface SimpleFormOuterClass
{
  public static final class Field
    extends MessageNano
  {
    private static volatile Field[] _emptyArray;
    public AddressFormOuterClass.AddressForm addressForm = null;
    public AddressFormOuterClass.CountrySelector countrySelector = null;
    public boolean hideFieldsBelow = false;
    public InfoMessageOuterClass.InfoMessage infoMessage = null;
    public OtpFieldOuterClass.OtpField otpField = null;
    public UiFieldOuterClass.UiField uiField = null;
    
    public Field()
    {
      this.cachedSize = -1;
    }
    
    public static Field[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Field[0];
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
      if (this.infoMessage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.infoMessage);
      }
      if (this.otpField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.otpField);
      }
      if (this.hideFieldsBelow) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if (this.countrySelector != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.countrySelector);
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
      if (this.infoMessage != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.infoMessage);
      }
      if (this.otpField != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.otpField);
      }
      if (this.hideFieldsBelow) {
        paramCodedOutputByteBufferNano.writeBool(6, this.hideFieldsBelow);
      }
      if (this.countrySelector != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.countrySelector);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FieldValue
    extends MessageNano
  {
    private static volatile FieldValue[] _emptyArray;
    public AddressFormOuterClass.AddressFormValue addressFormValue = null;
    public AddressFormOuterClass.CountrySelectorValue countrySelectorValue = null;
    public OtpFieldOuterClass.OtpFieldValue otpFieldValue = null;
    public UiFieldOuterClass.UiFieldValue uiFieldValue = null;
    
    public FieldValue()
    {
      this.cachedSize = -1;
    }
    
    public static FieldValue[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new FieldValue[0];
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
      if (this.countrySelectorValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.countrySelectorValue);
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
      if (this.countrySelectorValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.countrySelectorValue);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SimpleForm
    extends MessageNano
  {
    public FormField[] formField = FormField.emptyArray();
    public FormHeaderOuterClass.FormHeader formHeader = null;
    public LegalMessageOuterClass.LegalMessage legalMessage = null;
    
    public SimpleForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.formHeader != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.formHeader);
      }
      if ((this.formField != null) && (this.formField.length > 0)) {
        for (int j = 0; j < this.formField.length; j++)
        {
          FormField localFormField = this.formField[j];
          if (localFormField != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localFormField);
          }
        }
      }
      if (this.legalMessage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.legalMessage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.formHeader != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.formHeader);
      }
      if ((this.formField != null) && (this.formField.length > 0)) {
        for (int i = 0; i < this.formField.length; i++)
        {
          FormField localFormField = this.formField[i];
          if (localFormField != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localFormField);
          }
        }
      }
      if (this.legalMessage != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.legalMessage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class FormField
      extends MessageNano
    {
      private static volatile FormField[] _emptyArray;
      public SimpleFormOuterClass.Field field = null;
      public SimpleFormOuterClass.SubForm subForm = null;
      
      public FormField()
      {
        this.cachedSize = -1;
      }
      
      public static FormField[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new FormField[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if (this.subForm != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, this.subForm);
        }
        if (this.field != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, this.field);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if (this.subForm != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, this.subForm);
        }
        if (this.field != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, this.field);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class SimpleFormValue
    extends MessageNano
  {
    public byte[] dataToken = WireFormatNano.EMPTY_BYTES;
    public FormFieldValue[] formFieldValue = FormFieldValue.emptyArray();
    public String id = "";
    public String legalDocData = "";
    
    public SimpleFormValue()
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
      if ((this.formFieldValue != null) && (this.formFieldValue.length > 0)) {
        for (int j = 0; j < this.formFieldValue.length; j++)
        {
          FormFieldValue localFormFieldValue = this.formFieldValue[j];
          if (localFormFieldValue != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localFormFieldValue);
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
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.dataToken);
      }
      if ((this.formFieldValue != null) && (this.formFieldValue.length > 0)) {
        for (int i = 0; i < this.formFieldValue.length; i++)
        {
          FormFieldValue localFormFieldValue = this.formFieldValue[i];
          if (localFormFieldValue != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localFormFieldValue);
          }
        }
      }
      if (!this.legalDocData.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.legalDocData);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class FormFieldValue
      extends MessageNano
    {
      private static volatile FormFieldValue[] _emptyArray;
      public SimpleFormOuterClass.FieldValue fieldValue = null;
      public SimpleFormOuterClass.SubFormValue subFormValue = null;
      
      public FormFieldValue()
      {
        this.cachedSize = -1;
      }
      
      public static FormFieldValue[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new FormFieldValue[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if (this.subFormValue != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, this.subFormValue);
        }
        if (this.fieldValue != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, this.fieldValue);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if (this.subFormValue != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, this.subFormValue);
        }
        if (this.fieldValue != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, this.fieldValue);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class SubForm
    extends MessageNano
  {
    public SimpleFormOuterClass.Field[] field = SimpleFormOuterClass.Field.emptyArray();
    public FormHeaderOuterClass.FormHeader formHeader = null;
    public LegalMessageOuterClass.LegalMessage legalMessage = null;
    
    public SubForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.formHeader != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.formHeader);
      }
      if ((this.field != null) && (this.field.length > 0)) {
        for (int j = 0; j < this.field.length; j++)
        {
          SimpleFormOuterClass.Field localField = this.field[j];
          if (localField != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localField);
          }
        }
      }
      if (this.legalMessage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.legalMessage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.formHeader != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.formHeader);
      }
      if ((this.field != null) && (this.field.length > 0)) {
        for (int i = 0; i < this.field.length; i++)
        {
          SimpleFormOuterClass.Field localField = this.field[i];
          if (localField != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localField);
          }
        }
      }
      if (this.legalMessage != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.legalMessage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SubFormValue
    extends MessageNano
  {
    public byte[] dataToken = WireFormatNano.EMPTY_BYTES;
    public SimpleFormOuterClass.FieldValue[] fieldValue = SimpleFormOuterClass.FieldValue.emptyArray();
    public String id = "";
    public String legalDocData = "";
    
    public SubFormValue()
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
      if ((this.fieldValue != null) && (this.fieldValue.length > 0)) {
        for (int j = 0; j < this.fieldValue.length; j++)
        {
          SimpleFormOuterClass.FieldValue localFieldValue = this.fieldValue[j];
          if (localFieldValue != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localFieldValue);
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
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.dataToken);
      }
      if ((this.fieldValue != null) && (this.fieldValue.length > 0)) {
        for (int i = 0; i < this.fieldValue.length; i++)
        {
          SimpleFormOuterClass.FieldValue localFieldValue = this.fieldValue[i];
          if (localFieldValue != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localFieldValue);
          }
        }
      }
      if (!this.legalDocData.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.legalDocData);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass
 * JD-Core Version:    0.7.0.1
 */