package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public final class SingleFopPaymentsIntegratorContext
  extends MessageNano
{
  public byte[] commonToken = WireFormatNano.EMPTY_BYTES;
  public boolean hasCommonToken = false;
  public boolean hasInstrumentToken = false;
  public byte[] instrumentToken = WireFormatNano.EMPTY_BYTES;
  
  public SingleFopPaymentsIntegratorContext()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasCommonToken) || (!Arrays.equals(this.commonToken, WireFormatNano.EMPTY_BYTES))) {
      i += CodedOutputByteBufferNano.computeBytesSize(1, this.commonToken);
    }
    if ((this.hasInstrumentToken) || (!Arrays.equals(this.instrumentToken, WireFormatNano.EMPTY_BYTES))) {
      i += CodedOutputByteBufferNano.computeBytesSize(2, this.instrumentToken);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasCommonToken) || (!Arrays.equals(this.commonToken, WireFormatNano.EMPTY_BYTES))) {
      paramCodedOutputByteBufferNano.writeBytes(1, this.commonToken);
    }
    if ((this.hasInstrumentToken) || (!Arrays.equals(this.instrumentToken, WireFormatNano.EMPTY_BYTES))) {
      paramCodedOutputByteBufferNano.writeBytes(2, this.instrumentToken);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SingleFopPaymentsIntegratorContext
 * JD-Core Version:    0.7.0.1
 */