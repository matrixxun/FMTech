package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class CreditCardInstrument
  extends MessageNano
{
  public EfeParam[] escrowEfeParam = EfeParam.emptyArray();
  public String escrowHandle = "";
  public int expirationMonth = 0;
  public int expirationYear = 0;
  public boolean hasEscrowHandle = false;
  public boolean hasExpirationMonth = false;
  public boolean hasExpirationYear = false;
  public boolean hasLastDigits = false;
  public boolean hasType = false;
  public String lastDigits = "";
  public int type = 0;
  
  public CreditCardInstrument()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.type != 0) || (this.hasType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
    }
    if ((this.hasEscrowHandle) || (!this.escrowHandle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.escrowHandle);
    }
    if ((this.hasLastDigits) || (!this.lastDigits.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.lastDigits);
    }
    if ((this.hasExpirationMonth) || (this.expirationMonth != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(4, this.expirationMonth);
    }
    if ((this.hasExpirationYear) || (this.expirationYear != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(5, this.expirationYear);
    }
    if ((this.escrowEfeParam != null) && (this.escrowEfeParam.length > 0)) {
      for (int j = 0; j < this.escrowEfeParam.length; j++)
      {
        EfeParam localEfeParam = this.escrowEfeParam[j];
        if (localEfeParam != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(6, localEfeParam);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.type != 0) || (this.hasType)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.type);
    }
    if ((this.hasEscrowHandle) || (!this.escrowHandle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.escrowHandle);
    }
    if ((this.hasLastDigits) || (!this.lastDigits.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.lastDigits);
    }
    if ((this.hasExpirationMonth) || (this.expirationMonth != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(4, this.expirationMonth);
    }
    if ((this.hasExpirationYear) || (this.expirationYear != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(5, this.expirationYear);
    }
    if ((this.escrowEfeParam != null) && (this.escrowEfeParam.length > 0)) {
      for (int i = 0; i < this.escrowEfeParam.length; i++)
      {
        EfeParam localEfeParam = this.escrowEfeParam[i];
        if (localEfeParam != null) {
          paramCodedOutputByteBufferNano.writeMessage(6, localEfeParam);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.CreditCardInstrument
 * JD-Core Version:    0.7.0.1
 */