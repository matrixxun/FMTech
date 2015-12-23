package com.google.commerce.payments.orchestration.proto.ui.common.generic;

import com.google.commerce.payments.orchestration.proto.common.DateOuterClass.Date;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface UiFieldOuterClass
{
  public static final class UiField
    extends MessageNano
  {
    private static volatile UiField[] _emptyArray;
    public byte[] dataToken = WireFormatNano.EMPTY_BYTES;
    public DateField dateField = null;
    public boolean isDisabled = false;
    public boolean isOptional = false;
    public boolean isSecure = false;
    public String label = "";
    public String name = "";
    public SelectField selectField = null;
    public int semanticHint = 0;
    public TextField textField = null;
    public TooltipOuterClass.Tooltip tooltip = null;
    public int uiReference = 0;
    
    public UiField()
    {
      this.cachedSize = -1;
    }
    
    public static UiField[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new UiField[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.name.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.name);
      }
      if (this.isSecure) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if (this.isOptional) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if (!this.label.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.label);
      }
      if (this.textField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.textField);
      }
      if (this.selectField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.selectField);
      }
      if (this.tooltip != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.tooltip);
      }
      if (this.isDisabled) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(11);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(14, this.dataToken);
      }
      if (this.uiReference != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(15, this.uiReference);
      }
      if (this.dateField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(16, this.dateField);
      }
      if (this.semanticHint != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(18, this.semanticHint);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.name.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.name);
      }
      if (this.isSecure) {
        paramCodedOutputByteBufferNano.writeBool(2, this.isSecure);
      }
      if (this.isOptional) {
        paramCodedOutputByteBufferNano.writeBool(3, this.isOptional);
      }
      if (!this.label.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.label);
      }
      if (this.textField != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.textField);
      }
      if (this.selectField != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.selectField);
      }
      if (this.tooltip != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.tooltip);
      }
      if (this.isDisabled) {
        paramCodedOutputByteBufferNano.writeBool(11, this.isDisabled);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(14, this.dataToken);
      }
      if (this.uiReference != 0) {
        paramCodedOutputByteBufferNano.writeInt32(15, this.uiReference);
      }
      if (this.dateField != null) {
        paramCodedOutputByteBufferNano.writeMessage(16, this.dateField);
      }
      if (this.semanticHint != 0) {
        paramCodedOutputByteBufferNano.writeInt32(18, this.semanticHint);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class DateField
      extends MessageNano
    {
      public DateOuterClass.Date maxDate = null;
      public DateOuterClass.Date minDate = null;
      public int type = 0;
      
      public DateField()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if (this.type != 0) {
          i += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
        }
        if (this.minDate != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, this.minDate);
        }
        if (this.maxDate != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(3, this.maxDate);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if (this.type != 0) {
          paramCodedOutputByteBufferNano.writeInt32(1, this.type);
        }
        if (this.minDate != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, this.minDate);
        }
        if (this.maxDate != null) {
          paramCodedOutputByteBufferNano.writeMessage(3, this.maxDate);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
    
    public static final class SelectField
      extends MessageNano
    {
      public int displayType = 0;
      public String initialValue = "";
      public Option[] option = Option.emptyArray();
      public String unselectedDisplayValue = "";
      
      public SelectField()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.option != null) && (this.option.length > 0)) {
          for (int j = 0; j < this.option.length; j++)
          {
            Option localOption = this.option[j];
            if (localOption != null) {
              i += CodedOutputByteBufferNano.computeMessageSize(1, localOption);
            }
          }
        }
        if (!this.initialValue.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(2, this.initialValue);
        }
        if (!this.unselectedDisplayValue.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(3, this.unselectedDisplayValue);
        }
        if (this.displayType != 0) {
          i += CodedOutputByteBufferNano.computeInt32Size(4, this.displayType);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.option != null) && (this.option.length > 0)) {
          for (int i = 0; i < this.option.length; i++)
          {
            Option localOption = this.option[i];
            if (localOption != null) {
              paramCodedOutputByteBufferNano.writeMessage(1, localOption);
            }
          }
        }
        if (!this.initialValue.equals("")) {
          paramCodedOutputByteBufferNano.writeString(2, this.initialValue);
        }
        if (!this.unselectedDisplayValue.equals("")) {
          paramCodedOutputByteBufferNano.writeString(3, this.unselectedDisplayValue);
        }
        if (this.displayType != 0) {
          paramCodedOutputByteBufferNano.writeInt32(4, this.displayType);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
      
      public static final class Option
        extends MessageNano
      {
        private static volatile Option[] _emptyArray;
        public String displayValue = "";
        public InfoMessageOuterClass.InfoMessage extendedDescriptionInfo = null;
        public ImageWithCaptionOuterClass.ImageWithCaption icon = null;
        public int uiReference = 0;
        public String value = "";
        
        public Option()
        {
          this.cachedSize = -1;
        }
        
        public static Option[] emptyArray()
        {
          if (_emptyArray == null) {}
          synchronized (InternalNano.LAZY_INIT_LOCK)
          {
            if (_emptyArray == null) {
              _emptyArray = new Option[0];
            }
            return _emptyArray;
          }
        }
        
        protected final int computeSerializedSize()
        {
          int i = super.computeSerializedSize();
          if (!this.displayValue.equals("")) {
            i += CodedOutputByteBufferNano.computeStringSize(1, this.displayValue);
          }
          if (!this.value.equals("")) {
            i += CodedOutputByteBufferNano.computeStringSize(2, this.value);
          }
          if (this.extendedDescriptionInfo != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, this.extendedDescriptionInfo);
          }
          if (this.uiReference != 0) {
            i += CodedOutputByteBufferNano.computeInt32Size(5, this.uiReference);
          }
          if (this.icon != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(6, this.icon);
          }
          return i;
        }
        
        public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
          throws IOException
        {
          if (!this.displayValue.equals("")) {
            paramCodedOutputByteBufferNano.writeString(1, this.displayValue);
          }
          if (!this.value.equals("")) {
            paramCodedOutputByteBufferNano.writeString(2, this.value);
          }
          if (this.extendedDescriptionInfo != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, this.extendedDescriptionInfo);
          }
          if (this.uiReference != 0) {
            paramCodedOutputByteBufferNano.writeInt32(5, this.uiReference);
          }
          if (this.icon != null) {
            paramCodedOutputByteBufferNano.writeMessage(6, this.icon);
          }
          super.writeTo(paramCodedOutputByteBufferNano);
        }
      }
    }
    
    public static final class TextField
      extends MessageNano
    {
      public String initialValue = "";
      public boolean isMasked = false;
      public int keyboardLayout = 0;
      public int[] keyboardLayoutVariation = WireFormatNano.EMPTY_INT_ARRAY;
      public int maxLength = 0;
      public int minLength = 0;
      public FormattingSchemesOuterClass.TemplateFormattingScheme templateFormattingScheme = null;
      public Validation[] validation = Validation.emptyArray();
      
      public TextField()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if (this.minLength != 0) {
          i += CodedOutputByteBufferNano.computeInt32Size(1, this.minLength);
        }
        if (this.maxLength != 0) {
          i += CodedOutputByteBufferNano.computeInt32Size(2, this.maxLength);
        }
        if (this.keyboardLayout != 0) {
          i += CodedOutputByteBufferNano.computeInt32Size(4, this.keyboardLayout);
        }
        if ((this.validation != null) && (this.validation.length > 0)) {
          for (int m = 0; m < this.validation.length; m++)
          {
            Validation localValidation = this.validation[m];
            if (localValidation != null) {
              i += CodedOutputByteBufferNano.computeMessageSize(5, localValidation);
            }
          }
        }
        if (!this.initialValue.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(6, this.initialValue);
        }
        if (this.isMasked) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
        }
        if (this.templateFormattingScheme != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(14, this.templateFormattingScheme);
        }
        if ((this.keyboardLayoutVariation != null) && (this.keyboardLayoutVariation.length > 0))
        {
          int j = 0;
          for (int k = 0; k < this.keyboardLayoutVariation.length; k++) {
            j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.keyboardLayoutVariation[k]);
          }
          i = 1 + (i + j) + CodedOutputByteBufferNano.computeRawVarint32Size(j);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if (this.minLength != 0) {
          paramCodedOutputByteBufferNano.writeInt32(1, this.minLength);
        }
        if (this.maxLength != 0) {
          paramCodedOutputByteBufferNano.writeInt32(2, this.maxLength);
        }
        if (this.keyboardLayout != 0) {
          paramCodedOutputByteBufferNano.writeInt32(4, this.keyboardLayout);
        }
        if ((this.validation != null) && (this.validation.length > 0)) {
          for (int m = 0; m < this.validation.length; m++)
          {
            Validation localValidation = this.validation[m];
            if (localValidation != null) {
              paramCodedOutputByteBufferNano.writeMessage(5, localValidation);
            }
          }
        }
        if (!this.initialValue.equals("")) {
          paramCodedOutputByteBufferNano.writeString(6, this.initialValue);
        }
        if (this.isMasked) {
          paramCodedOutputByteBufferNano.writeBool(8, this.isMasked);
        }
        if (this.templateFormattingScheme != null) {
          paramCodedOutputByteBufferNano.writeMessage(14, this.templateFormattingScheme);
        }
        if ((this.keyboardLayoutVariation != null) && (this.keyboardLayoutVariation.length > 0))
        {
          int i = 0;
          for (int j = 0; j < this.keyboardLayoutVariation.length; j++) {
            i += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.keyboardLayoutVariation[j]);
          }
          paramCodedOutputByteBufferNano.writeRawVarint32(122);
          paramCodedOutputByteBufferNano.writeRawVarint32(i);
          for (int k = 0; k < this.keyboardLayoutVariation.length; k++) {
            paramCodedOutputByteBufferNano.writeRawVarint32(this.keyboardLayoutVariation[k]);
          }
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
      
      public static final class Validation
        extends MessageNano
      {
        private static volatile Validation[] _emptyArray;
        public String errorMessage = "";
        public String regex = "";
        
        public Validation()
        {
          this.cachedSize = -1;
        }
        
        public static Validation[] emptyArray()
        {
          if (_emptyArray == null) {}
          synchronized (InternalNano.LAZY_INIT_LOCK)
          {
            if (_emptyArray == null) {
              _emptyArray = new Validation[0];
            }
            return _emptyArray;
          }
        }
        
        protected final int computeSerializedSize()
        {
          int i = super.computeSerializedSize();
          if (!this.regex.equals("")) {
            i += CodedOutputByteBufferNano.computeStringSize(1, this.regex);
          }
          if (!this.errorMessage.equals("")) {
            i += CodedOutputByteBufferNano.computeStringSize(2, this.errorMessage);
          }
          return i;
        }
        
        public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
          throws IOException
        {
          if (!this.regex.equals("")) {
            paramCodedOutputByteBufferNano.writeString(1, this.regex);
          }
          if (!this.errorMessage.equals("")) {
            paramCodedOutputByteBufferNano.writeString(2, this.errorMessage);
          }
          super.writeTo(paramCodedOutputByteBufferNano);
        }
      }
    }
  }
  
  public static final class UiFieldValue
    extends MessageNano
  {
    private static volatile UiFieldValue[] _emptyArray;
    public byte[] dataToken = WireFormatNano.EMPTY_BYTES;
    public DateOuterClass.Date date = null;
    public String name = "";
    public String secureStringValue = "";
    public String stringValue = "";
    
    public UiFieldValue()
    {
      this.cachedSize = -1;
    }
    
    public static UiFieldValue[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new UiFieldValue[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.name.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.name);
      }
      if (!this.stringValue.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.stringValue);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(6, this.dataToken);
      }
      if (this.date != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.date);
      }
      if (!this.secureStringValue.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.secureStringValue);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.name.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.name);
      }
      if (!this.stringValue.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.stringValue);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(6, this.dataToken);
      }
      if (this.date != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.date);
      }
      if (!this.secureStringValue.equals("")) {
        paramCodedOutputByteBufferNano.writeString(8, this.secureStringValue);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass
 * JD-Core Version:    0.7.0.1
 */