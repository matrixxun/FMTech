package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class AndroidAppNotificationData
  extends MessageNano
{
  public String assetId = "";
  public boolean hasAssetId = false;
  public boolean hasInstallReferrer = false;
  public boolean hasVersionCode = false;
  public String installReferrer = "";
  public int versionCode = 0;
  
  public AndroidAppNotificationData()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasVersionCode) || (this.versionCode != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.versionCode);
    }
    if ((this.hasAssetId) || (!this.assetId.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.assetId);
    }
    if ((this.hasInstallReferrer) || (!this.installReferrer.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.installReferrer);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasVersionCode) || (this.versionCode != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.versionCode);
    }
    if ((this.hasAssetId) || (!this.assetId.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.assetId);
    }
    if ((this.hasInstallReferrer) || (!this.installReferrer.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.installReferrer);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.AndroidAppNotificationData
 * JD-Core Version:    0.7.0.1
 */