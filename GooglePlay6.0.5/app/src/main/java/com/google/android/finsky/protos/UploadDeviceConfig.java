package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface UploadDeviceConfig
{
  public static final class UploadDeviceConfigRequest
    extends MessageNano
  {
    public DeviceConfiguration.DeviceConfigurationProto deviceConfiguration = null;
    public String gcmRegistrationId = "";
    public boolean hasGcmRegistrationId = false;
    public boolean hasManufacturer = false;
    public String manufacturer = "";
    
    public UploadDeviceConfigRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.deviceConfiguration != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.deviceConfiguration);
      }
      if ((this.hasManufacturer) || (!this.manufacturer.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.manufacturer);
      }
      if ((this.hasGcmRegistrationId) || (!this.gcmRegistrationId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.gcmRegistrationId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.deviceConfiguration != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.deviceConfiguration);
      }
      if ((this.hasManufacturer) || (!this.manufacturer.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.manufacturer);
      }
      if ((this.hasGcmRegistrationId) || (!this.gcmRegistrationId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.gcmRegistrationId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class UploadDeviceConfigResponse
    extends MessageNano
  {
    public boolean hasUploadDeviceConfigToken = false;
    public String uploadDeviceConfigToken = "";
    
    public UploadDeviceConfigResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasUploadDeviceConfigToken) || (!this.uploadDeviceConfigToken.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.uploadDeviceConfigToken);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasUploadDeviceConfigToken) || (!this.uploadDeviceConfigToken.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.uploadDeviceConfigToken);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.UploadDeviceConfig
 * JD-Core Version:    0.7.0.1
 */