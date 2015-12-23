package com.google.commerce.payments.orchestration.proto.ui.common;

import com.google.commerce.payments.orchestration.proto.common.ContextOuterClass.NativeClientContext;
import com.google.moneta.api2.common.ExperimentProto.ExperimentContextPb;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface RequestContextOuterClass
{
  public static final class RequestContext
    extends MessageNano
  {
    public int clientType = 0;
    public long clientVersion = 0L;
    public ExperimentProto.ExperimentContextPb experimentContext = null;
    public boolean isPrefetchRequest = false;
    public String languageCode = "";
    public ContextOuterClass.NativeClientContext nativeContext = null;
    public String requestId = "";
    public byte[] sessionData = WireFormatNano.EMPTY_BYTES;
    
    public RequestContext()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!Arrays.equals(this.sessionData, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.sessionData);
      }
      if (!this.languageCode.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.languageCode);
      }
      if (this.clientType != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.clientType);
      }
      if (this.clientVersion != 0L) {
        i += CodedOutputByteBufferNano.computeInt64Size(9, this.clientVersion);
      }
      if (this.nativeContext != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.nativeContext);
      }
      if (!this.requestId.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(11, this.requestId);
      }
      if (this.experimentContext != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(12, this.experimentContext);
      }
      if (this.isPrefetchRequest) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(13);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!Arrays.equals(this.sessionData, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.sessionData);
      }
      if (!this.languageCode.equals("")) {
        paramCodedOutputByteBufferNano.writeString(7, this.languageCode);
      }
      if (this.clientType != 0) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.clientType);
      }
      if (this.clientVersion != 0L) {
        paramCodedOutputByteBufferNano.writeInt64(9, this.clientVersion);
      }
      if (this.nativeContext != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.nativeContext);
      }
      if (!this.requestId.equals("")) {
        paramCodedOutputByteBufferNano.writeString(11, this.requestId);
      }
      if (this.experimentContext != null) {
        paramCodedOutputByteBufferNano.writeMessage(12, this.experimentContext);
      }
      if (this.isPrefetchRequest) {
        paramCodedOutputByteBufferNano.writeBool(13, this.isPrefetchRequest);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.RequestContextOuterClass
 * JD-Core Version:    0.7.0.1
 */