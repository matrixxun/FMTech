package com.google.moneta.orchestration.ui.common;

import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface SecureDataMappingOuterClass
{
  public static final class SecureDataMapping
    extends MessageNano
  {
    private static volatile SecureDataMapping[] _emptyArray;
    public FormFieldReferenceOuterClass.FormFieldReference secureDataField = null;
    public int secureDataKey = 0;
    
    public SecureDataMapping()
    {
      this.cachedSize = -1;
    }
    
    public static SecureDataMapping[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new SecureDataMapping[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.secureDataKey != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.secureDataKey);
      }
      if (this.secureDataField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.secureDataField);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.secureDataKey != 0) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.secureDataKey);
      }
      if (this.secureDataField != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.secureDataField);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.moneta.orchestration.ui.common.SecureDataMappingOuterClass
 * JD-Core Version:    0.7.0.1
 */