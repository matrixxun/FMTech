package com.google.commerce.payments.orchestration.proto.ui.instrumentmanager;

import com.google.moneta.orchestration.ui.common.ClientEnvironmentConfig.AndroidEnvironmentConfig;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface DataTokens
{
  public static final class ActionToken
    extends MessageNano
  {
    public Api.InitializeResponse initializeResponse = null;
    public Api.InstrumentManagerParameters parameters = null;
    
    public ActionToken()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.parameters != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.parameters);
      }
      if (this.initializeResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.initializeResponse);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.parameters != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.parameters);
      }
      if (this.initializeResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.initializeResponse);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CommonToken
    extends MessageNano
  {
    public ClientEnvironmentConfig.AndroidEnvironmentConfig androidEnvironmentConfig = null;
    public Api.InstrumentManagerParameters parameters = null;
    
    public CommonToken()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.parameters != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.parameters);
      }
      if (this.androidEnvironmentConfig != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.androidEnvironmentConfig);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.parameters != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.parameters);
      }
      if (this.androidEnvironmentConfig != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.androidEnvironmentConfig);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.DataTokens
 * JD-Core Version:    0.7.0.1
 */