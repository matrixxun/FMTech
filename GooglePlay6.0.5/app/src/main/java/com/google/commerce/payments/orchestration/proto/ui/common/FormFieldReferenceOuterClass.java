package com.google.commerce.payments.orchestration.proto.ui.common;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface FormFieldReferenceOuterClass
{
  public static final class FormFieldReference
    extends MessageNano
  {
    private static volatile FormFieldReference[] _emptyArray;
    public int fieldId = 0;
    public String formId = "";
    public int repeatedFieldIndex = -1;
    
    public FormFieldReference()
    {
      this.cachedSize = -1;
    }
    
    public static FormFieldReference[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new FormFieldReference[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.formId.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.formId);
      }
      if (this.fieldId != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.fieldId);
      }
      if (this.repeatedFieldIndex != -1) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.repeatedFieldIndex);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.formId.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.formId);
      }
      if (this.fieldId != 0) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.fieldId);
      }
      if (this.repeatedFieldIndex != -1) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.repeatedFieldIndex);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass
 * JD-Core Version:    0.7.0.1
 */