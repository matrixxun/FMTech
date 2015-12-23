package com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface CreditCardResultOuterClass
{
  public static final class CreditCardInputResult
    extends MessageNano
  {
    private static volatile CreditCardInputResult[] _emptyArray;
    public String cardNumber = "";
    public String cardholderName = "";
    public String cardholderRawName = "";
    public int dominantColor = 0;
    public int expMonth = 0;
    public int expYear = 0;
    public int inputType = 0;
    
    public CreditCardInputResult()
    {
      this.cachedSize = -1;
    }
    
    public static CreditCardInputResult[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new CreditCardInputResult[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.cardNumber.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.cardNumber);
      }
      if (this.expMonth != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.expMonth);
      }
      if (this.expYear != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.expYear);
      }
      if (this.dominantColor != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.dominantColor);
      }
      if (!this.cardholderName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.cardholderName);
      }
      if (this.inputType != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(7, this.inputType);
      }
      if (!this.cardholderRawName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.cardholderRawName);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.cardNumber.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.cardNumber);
      }
      if (this.expMonth != 0) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.expMonth);
      }
      if (this.expYear != 0) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.expYear);
      }
      if (this.dominantColor != 0) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.dominantColor);
      }
      if (!this.cardholderName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(6, this.cardholderName);
      }
      if (this.inputType != 0) {
        paramCodedOutputByteBufferNano.writeInt32(7, this.inputType);
      }
      if (!this.cardholderRawName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(8, this.cardholderRawName);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCardResultOuterClass
 * JD-Core Version:    0.7.0.1
 */