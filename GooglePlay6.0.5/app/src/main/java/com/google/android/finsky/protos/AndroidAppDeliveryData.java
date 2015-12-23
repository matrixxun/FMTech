package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class AndroidAppDeliveryData
  extends MessageNano
{
  public AppFileMetadata[] additionalFile = AppFileMetadata.emptyArray();
  public HttpCookie[] downloadAuthCookie = HttpCookie.emptyArray();
  public long downloadSize = 0L;
  public String downloadUrl = "";
  public EncryptionParams encryptionParams = null;
  public boolean forwardLocked = false;
  public long gzippedDownloadSize = 0L;
  public String gzippedDownloadUrl = "";
  public boolean hasDownloadSize = false;
  public boolean hasDownloadUrl = false;
  public boolean hasForwardLocked = false;
  public boolean hasGzippedDownloadSize = false;
  public boolean hasGzippedDownloadUrl = false;
  public boolean hasImmediateStartNeeded = false;
  public boolean hasInstallLocation = false;
  public boolean hasPostInstallRefundWindowMillis = false;
  public boolean hasRefundTimeout = false;
  public boolean hasServerInitiated = false;
  public boolean hasSignature = false;
  public boolean immediateStartNeeded = false;
  public int installLocation = 0;
  public AndroidAppPatchData patchData = null;
  public long postInstallRefundWindowMillis = 0L;
  public long refundTimeout = 0L;
  public boolean serverInitiated = true;
  public String signature = "";
  public SplitDeliveryData[] splitDeliveryData = SplitDeliveryData.emptyArray();
  
  public AndroidAppDeliveryData()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasDownloadSize) || (this.downloadSize != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(1, this.downloadSize);
    }
    if ((this.hasSignature) || (!this.signature.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.signature);
    }
    if ((this.hasDownloadUrl) || (!this.downloadUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.downloadUrl);
    }
    if ((this.additionalFile != null) && (this.additionalFile.length > 0)) {
      for (int m = 0; m < this.additionalFile.length; m++)
      {
        AppFileMetadata localAppFileMetadata = this.additionalFile[m];
        if (localAppFileMetadata != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(4, localAppFileMetadata);
        }
      }
    }
    if ((this.downloadAuthCookie != null) && (this.downloadAuthCookie.length > 0)) {
      for (int k = 0; k < this.downloadAuthCookie.length; k++)
      {
        HttpCookie localHttpCookie = this.downloadAuthCookie[k];
        if (localHttpCookie != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(5, localHttpCookie);
        }
      }
    }
    if ((this.hasForwardLocked) || (this.forwardLocked)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
    }
    if ((this.hasRefundTimeout) || (this.refundTimeout != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(7, this.refundTimeout);
    }
    if ((this.hasServerInitiated) || (this.serverInitiated != true)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
    }
    if ((this.hasPostInstallRefundWindowMillis) || (this.postInstallRefundWindowMillis != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(9, this.postInstallRefundWindowMillis);
    }
    if ((this.hasImmediateStartNeeded) || (this.immediateStartNeeded)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(10);
    }
    if (this.patchData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(11, this.patchData);
    }
    if (this.encryptionParams != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(12, this.encryptionParams);
    }
    if ((this.hasGzippedDownloadUrl) || (!this.gzippedDownloadUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(13, this.gzippedDownloadUrl);
    }
    if ((this.hasGzippedDownloadSize) || (this.gzippedDownloadSize != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(14, this.gzippedDownloadSize);
    }
    if ((this.splitDeliveryData != null) && (this.splitDeliveryData.length > 0)) {
      for (int j = 0; j < this.splitDeliveryData.length; j++)
      {
        SplitDeliveryData localSplitDeliveryData = this.splitDeliveryData[j];
        if (localSplitDeliveryData != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(15, localSplitDeliveryData);
        }
      }
    }
    if ((this.installLocation != 0) || (this.hasInstallLocation)) {
      i += CodedOutputByteBufferNano.computeInt32Size(16, this.installLocation);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasDownloadSize) || (this.downloadSize != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(1, this.downloadSize);
    }
    if ((this.hasSignature) || (!this.signature.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.signature);
    }
    if ((this.hasDownloadUrl) || (!this.downloadUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.downloadUrl);
    }
    if ((this.additionalFile != null) && (this.additionalFile.length > 0)) {
      for (int k = 0; k < this.additionalFile.length; k++)
      {
        AppFileMetadata localAppFileMetadata = this.additionalFile[k];
        if (localAppFileMetadata != null) {
          paramCodedOutputByteBufferNano.writeMessage(4, localAppFileMetadata);
        }
      }
    }
    if ((this.downloadAuthCookie != null) && (this.downloadAuthCookie.length > 0)) {
      for (int j = 0; j < this.downloadAuthCookie.length; j++)
      {
        HttpCookie localHttpCookie = this.downloadAuthCookie[j];
        if (localHttpCookie != null) {
          paramCodedOutputByteBufferNano.writeMessage(5, localHttpCookie);
        }
      }
    }
    if ((this.hasForwardLocked) || (this.forwardLocked)) {
      paramCodedOutputByteBufferNano.writeBool(6, this.forwardLocked);
    }
    if ((this.hasRefundTimeout) || (this.refundTimeout != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(7, this.refundTimeout);
    }
    if ((this.hasServerInitiated) || (this.serverInitiated != true)) {
      paramCodedOutputByteBufferNano.writeBool(8, this.serverInitiated);
    }
    if ((this.hasPostInstallRefundWindowMillis) || (this.postInstallRefundWindowMillis != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(9, this.postInstallRefundWindowMillis);
    }
    if ((this.hasImmediateStartNeeded) || (this.immediateStartNeeded)) {
      paramCodedOutputByteBufferNano.writeBool(10, this.immediateStartNeeded);
    }
    if (this.patchData != null) {
      paramCodedOutputByteBufferNano.writeMessage(11, this.patchData);
    }
    if (this.encryptionParams != null) {
      paramCodedOutputByteBufferNano.writeMessage(12, this.encryptionParams);
    }
    if ((this.hasGzippedDownloadUrl) || (!this.gzippedDownloadUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(13, this.gzippedDownloadUrl);
    }
    if ((this.hasGzippedDownloadSize) || (this.gzippedDownloadSize != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(14, this.gzippedDownloadSize);
    }
    if ((this.splitDeliveryData != null) && (this.splitDeliveryData.length > 0)) {
      for (int i = 0; i < this.splitDeliveryData.length; i++)
      {
        SplitDeliveryData localSplitDeliveryData = this.splitDeliveryData[i];
        if (localSplitDeliveryData != null) {
          paramCodedOutputByteBufferNano.writeMessage(15, localSplitDeliveryData);
        }
      }
    }
    if ((this.installLocation != 0) || (this.hasInstallLocation)) {
      paramCodedOutputByteBufferNano.writeInt32(16, this.installLocation);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.AndroidAppDeliveryData
 * JD-Core Version:    0.7.0.1
 */