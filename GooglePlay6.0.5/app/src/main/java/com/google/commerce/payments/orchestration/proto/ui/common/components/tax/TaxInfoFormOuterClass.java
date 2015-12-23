package com.google.commerce.payments.orchestration.proto.ui.common.components.tax;

import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiFieldValue;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface TaxInfoFormOuterClass
{
  public static final class TaxInfoForm
    extends MessageNano
  {
    private static volatile TaxInfoForm[] _emptyArray;
    public String id = "";
    public String label = "";
    public UiFieldOuterClass.UiField[] taxInfoField = UiFieldOuterClass.UiField.emptyArray();
    
    public TaxInfoForm()
    {
      this.cachedSize = -1;
    }
    
    public static TaxInfoForm[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new TaxInfoForm[0];
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
      if (!this.label.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.label);
      }
      if ((this.taxInfoField != null) && (this.taxInfoField.length > 0)) {
        for (int j = 0; j < this.taxInfoField.length; j++)
        {
          UiFieldOuterClass.UiField localUiField = this.taxInfoField[j];
          if (localUiField != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localUiField);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.id);
      }
      if (!this.label.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.label);
      }
      if ((this.taxInfoField != null) && (this.taxInfoField.length > 0)) {
        for (int i = 0; i < this.taxInfoField.length; i++)
        {
          UiFieldOuterClass.UiField localUiField = this.taxInfoField[i];
          if (localUiField != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localUiField);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class TaxInfoFormValue
    extends MessageNano
  {
    public String taxInfoFormId = "";
    public UiFieldOuterClass.UiFieldValue[] taxInfoValue = UiFieldOuterClass.UiFieldValue.emptyArray();
    
    public TaxInfoFormValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.taxInfoFormId.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.taxInfoFormId);
      }
      if ((this.taxInfoValue != null) && (this.taxInfoValue.length > 0)) {
        for (int j = 0; j < this.taxInfoValue.length; j++)
        {
          UiFieldOuterClass.UiFieldValue localUiFieldValue = this.taxInfoValue[j];
          if (localUiFieldValue != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localUiFieldValue);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.taxInfoFormId.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.taxInfoFormId);
      }
      if ((this.taxInfoValue != null) && (this.taxInfoValue.length > 0)) {
        for (int i = 0; i < this.taxInfoValue.length; i++)
        {
          UiFieldOuterClass.UiFieldValue localUiFieldValue = this.taxInfoValue[i];
          if (localUiFieldValue != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localUiFieldValue);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.tax.TaxInfoFormOuterClass
 * JD-Core Version:    0.7.0.1
 */