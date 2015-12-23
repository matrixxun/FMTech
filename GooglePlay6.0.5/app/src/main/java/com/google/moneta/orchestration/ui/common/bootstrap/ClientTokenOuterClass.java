package com.google.moneta.orchestration.ui.common.bootstrap;

import com.google.commerce.payments.orchestration.proto.ui.common.RequestContextOuterClass.RequestContext;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface ClientTokenOuterClass
{
  public static final class ClientToken
    extends MessageNano
  {
    public ClientParametersOuterClass.ClientParameters clientParameters = null;
    public RequestContextOuterClass.RequestContext requestContext = null;
    
    public ClientToken()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.requestContext != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.requestContext);
      }
      if (this.clientParameters != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.clientParameters);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.requestContext != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.requestContext);
      }
      if (this.clientParameters != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.clientParameters);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.moneta.orchestration.ui.common.bootstrap.ClientTokenOuterClass
 * JD-Core Version:    0.7.0.1
 */