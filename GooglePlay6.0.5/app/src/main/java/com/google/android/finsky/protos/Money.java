package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Money
  extends MessageNano
{
  public String currencyCode = "";
  public String formattedAmount = "";
  public boolean hasCurrencyCode = false;
  public boolean hasFormattedAmount = false;
  public boolean hasMicros = false;
  public long micros = 0L;
  
  public Money()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasMicros) || (this.micros != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(1, this.micros);
    }
    if ((this.hasCurrencyCode) || (!this.currencyCode.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.currencyCode);
    }
    if ((this.hasFormattedAmount) || (!this.formattedAmount.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.formattedAmount);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasMicros) || (this.micros != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(1, this.micros);
    }
    if ((this.hasCurrencyCode) || (!this.currencyCode.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.currencyCode);
    }
    if ((this.hasFormattedAmount) || (!this.formattedAmount.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.formattedAmount);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Money
 * JD-Core Version:    0.7.0.1
 */