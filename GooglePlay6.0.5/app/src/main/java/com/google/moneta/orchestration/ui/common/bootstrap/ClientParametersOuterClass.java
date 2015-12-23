package com.google.moneta.orchestration.ui.common.bootstrap;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public abstract interface ClientParametersOuterClass
{
  public static final class ClientParameters
    extends MessageNano
  {
    public int[] supportedRedirectFormDisplayType = WireFormatNano.EMPTY_INT_ARRAY;
    public int titleIconStyle = 0;
    
    public ClientParameters()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.titleIconStyle != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.titleIconStyle);
      }
      if ((this.supportedRedirectFormDisplayType != null) && (this.supportedRedirectFormDisplayType.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.supportedRedirectFormDisplayType.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.supportedRedirectFormDisplayType[k]);
        }
        i = i + j + 1 * this.supportedRedirectFormDisplayType.length;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.titleIconStyle != 0) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.titleIconStyle);
      }
      if ((this.supportedRedirectFormDisplayType != null) && (this.supportedRedirectFormDisplayType.length > 0)) {
        for (int i = 0; i < this.supportedRedirectFormDisplayType.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(4, this.supportedRedirectFormDisplayType[i]);
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.moneta.orchestration.ui.common.bootstrap.ClientParametersOuterClass
 * JD-Core Version:    0.7.0.1
 */