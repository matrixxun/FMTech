package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public final class Targets
  extends MessageNano
{
  public boolean hasSignature = false;
  public byte[] signature = WireFormatNano.EMPTY_BYTES;
  public long[] targetId = WireFormatNano.EMPTY_LONG_ARRAY;
  
  public Targets()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.targetId != null) && (this.targetId.length > 0))
    {
      int j = 0;
      for (int k = 0; k < this.targetId.length; k++) {
        j += CodedOutputByteBufferNano.computeRawVarint64Size(this.targetId[k]);
      }
      i = i + j + 1 * this.targetId.length;
    }
    if ((this.hasSignature) || (!Arrays.equals(this.signature, WireFormatNano.EMPTY_BYTES))) {
      i += CodedOutputByteBufferNano.computeBytesSize(2, this.signature);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.targetId != null) && (this.targetId.length > 0)) {
      for (int i = 0; i < this.targetId.length; i++) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.targetId[i]);
      }
    }
    if ((this.hasSignature) || (!Arrays.equals(this.signature, WireFormatNano.EMPTY_BYTES))) {
      paramCodedOutputByteBufferNano.writeBytes(2, this.signature);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Targets
 * JD-Core Version:    0.7.0.1
 */