package com.google.moneta.orchestration.ui.common;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface ClientEnvironmentConfig
{
  public static final class AndroidEnvironmentConfig
    extends MessageNano
  {
    public String accountName = "";
    public String authTokenType = "";
    public String serverBasePath = "";
    public String serverEesBasePath = "";
    
    public AndroidEnvironmentConfig()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.serverBasePath.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.serverBasePath);
      }
      if (!this.authTokenType.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.authTokenType);
      }
      if (!this.accountName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.accountName);
      }
      if (!this.serverEesBasePath.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.serverEesBasePath);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.serverBasePath.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.serverBasePath);
      }
      if (!this.authTokenType.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.authTokenType);
      }
      if (!this.accountName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.accountName);
      }
      if (!this.serverEesBasePath.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.serverEesBasePath);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.moneta.orchestration.ui.common.ClientEnvironmentConfig
 * JD-Core Version:    0.7.0.1
 */