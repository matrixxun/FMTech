package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class AndroidAppPatchData
  extends MessageNano
{
  public String baseSignature = "";
  public int baseVersionCode = 0;
  public String downloadUrl = "";
  public boolean hasBaseSignature = false;
  public boolean hasBaseVersionCode = false;
  public boolean hasDownloadUrl = false;
  public boolean hasMaxPatchSize = false;
  public boolean hasPatchFormat = false;
  public long maxPatchSize = 0L;
  public int patchFormat = 1;
  
  public AndroidAppPatchData()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasBaseVersionCode) || (this.baseVersionCode != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.baseVersionCode);
    }
    if ((this.hasBaseSignature) || (!this.baseSignature.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.baseSignature);
    }
    if ((this.hasDownloadUrl) || (!this.downloadUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.downloadUrl);
    }
    if ((this.patchFormat != 1) || (this.hasPatchFormat)) {
      i += CodedOutputByteBufferNano.computeInt32Size(4, this.patchFormat);
    }
    if ((this.hasMaxPatchSize) || (this.maxPatchSize != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(5, this.maxPatchSize);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasBaseVersionCode) || (this.baseVersionCode != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.baseVersionCode);
    }
    if ((this.hasBaseSignature) || (!this.baseSignature.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.baseSignature);
    }
    if ((this.hasDownloadUrl) || (!this.downloadUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.downloadUrl);
    }
    if ((this.patchFormat != 1) || (this.hasPatchFormat)) {
      paramCodedOutputByteBufferNano.writeInt32(4, this.patchFormat);
    }
    if ((this.hasMaxPatchSize) || (this.maxPatchSize != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(5, this.maxPatchSize);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.AndroidAppPatchData
 * JD-Core Version:    0.7.0.1
 */