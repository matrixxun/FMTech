package com.google.commerce.payments.orchestration.proto.ui.common;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface ResponseContextOuterClass
{
  public static final class ResponseContext
    extends MessageNano
  {
    public byte[] analyticsToken = WireFormatNano.EMPTY_BYTES;
    public int[] globalClientFeatures = WireFormatNano.EMPTY_INT_ARRAY;
    public byte[] logToken = WireFormatNano.EMPTY_BYTES;
    public String requestId = "";
    public long responseTimeMillis = -1L;
    public byte[] sessionData = WireFormatNano.EMPTY_BYTES;
    
    public ResponseContext()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.responseTimeMillis != -1L) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.responseTimeMillis);
      }
      if (!Arrays.equals(this.sessionData, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.sessionData);
      }
      if (!Arrays.equals(this.logToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(5, this.logToken);
      }
      if (!this.requestId.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.requestId);
      }
      if (!Arrays.equals(this.analyticsToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(7, this.analyticsToken);
      }
      if ((this.globalClientFeatures != null) && (this.globalClientFeatures.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.globalClientFeatures.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.globalClientFeatures[k]);
        }
        i = i + j + 1 * this.globalClientFeatures.length;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.responseTimeMillis != -1L) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.responseTimeMillis);
      }
      if (!Arrays.equals(this.sessionData, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.sessionData);
      }
      if (!Arrays.equals(this.logToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(5, this.logToken);
      }
      if (!this.requestId.equals("")) {
        paramCodedOutputByteBufferNano.writeString(6, this.requestId);
      }
      if (!Arrays.equals(this.analyticsToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(7, this.analyticsToken);
      }
      if ((this.globalClientFeatures != null) && (this.globalClientFeatures.length > 0)) {
        for (int i = 0; i < this.globalClientFeatures.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(8, this.globalClientFeatures[i]);
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.ResponseContextOuterClass
 * JD-Core Version:    0.7.0.1
 */