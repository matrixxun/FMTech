package com.google.moneta.api2.common;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public abstract interface ExperimentProto
{
  public static final class ExperimentContextPb
    extends MessageNano
  {
    public int[] experimentId = WireFormatNano.EMPTY_INT_ARRAY;
    
    public ExperimentContextPb()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.experimentId != null) && (this.experimentId.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.experimentId.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.experimentId[k]);
        }
        i = i + j + 1 * this.experimentId.length;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.experimentId != null) && (this.experimentId.length > 0)) {
        for (int i = 0; i < this.experimentId.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(1, this.experimentId[i]);
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.moneta.api2.common.ExperimentProto
 * JD-Core Version:    0.7.0.1
 */