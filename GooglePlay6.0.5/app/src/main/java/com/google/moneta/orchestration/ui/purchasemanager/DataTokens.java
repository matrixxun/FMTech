package com.google.moneta.orchestration.ui.purchasemanager;

import com.google.moneta.orchestration.ui.common.ClientEnvironmentConfig.AndroidEnvironmentConfig;
import com.google.moneta.orchestration.ui.common.SecureDataMappingOuterClass.SecureDataMapping;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface DataTokens
{
  public static final class ActionToken
    extends MessageNano
  {
    public ClientEnvironmentConfig.AndroidEnvironmentConfig androidEnvironmentConfig = null;
    public SecureDataMappingOuterClass.SecureDataMapping[] forwardSecureDataMapping = SecureDataMappingOuterClass.SecureDataMapping.emptyArray();
    public Api.InitializeResponse initializeResponse = null;
    public Api.PurchaseManagerParameters parameters = null;
    public boolean returnSecurePayloadToIntegrator = false;
    public SecureDataMappingOuterClass.SecureDataMapping[] reverseSecureDataMapping = SecureDataMappingOuterClass.SecureDataMapping.emptyArray();
    
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
      if (this.androidEnvironmentConfig != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.androidEnvironmentConfig);
      }
      if ((this.reverseSecureDataMapping != null) && (this.reverseSecureDataMapping.length > 0)) {
        for (int k = 0; k < this.reverseSecureDataMapping.length; k++)
        {
          SecureDataMappingOuterClass.SecureDataMapping localSecureDataMapping2 = this.reverseSecureDataMapping[k];
          if (localSecureDataMapping2 != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localSecureDataMapping2);
          }
        }
      }
      if (this.returnSecurePayloadToIntegrator) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      if ((this.forwardSecureDataMapping != null) && (this.forwardSecureDataMapping.length > 0)) {
        for (int j = 0; j < this.forwardSecureDataMapping.length; j++)
        {
          SecureDataMappingOuterClass.SecureDataMapping localSecureDataMapping1 = this.forwardSecureDataMapping[j];
          if (localSecureDataMapping1 != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(6, localSecureDataMapping1);
          }
        }
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
      if (this.androidEnvironmentConfig != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.androidEnvironmentConfig);
      }
      if ((this.reverseSecureDataMapping != null) && (this.reverseSecureDataMapping.length > 0)) {
        for (int j = 0; j < this.reverseSecureDataMapping.length; j++)
        {
          SecureDataMappingOuterClass.SecureDataMapping localSecureDataMapping2 = this.reverseSecureDataMapping[j];
          if (localSecureDataMapping2 != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localSecureDataMapping2);
          }
        }
      }
      if (this.returnSecurePayloadToIntegrator) {
        paramCodedOutputByteBufferNano.writeBool(5, this.returnSecurePayloadToIntegrator);
      }
      if ((this.forwardSecureDataMapping != null) && (this.forwardSecureDataMapping.length > 0)) {
        for (int i = 0; i < this.forwardSecureDataMapping.length; i++)
        {
          SecureDataMappingOuterClass.SecureDataMapping localSecureDataMapping1 = this.forwardSecureDataMapping[i];
          if (localSecureDataMapping1 != null) {
            paramCodedOutputByteBufferNano.writeMessage(6, localSecureDataMapping1);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.moneta.orchestration.ui.purchasemanager.DataTokens
 * JD-Core Version:    0.7.0.1
 */