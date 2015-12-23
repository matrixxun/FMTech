package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class SplitDeliveryData
  extends MessageNano
{
  private static volatile SplitDeliveryData[] _emptyArray;
  public long downloadSize = 0L;
  public String downloadUrl = "";
  public long gzippedDownloadSize = 0L;
  public String gzippedDownloadUrl = "";
  public boolean hasDownloadSize = false;
  public boolean hasDownloadUrl = false;
  public boolean hasGzippedDownloadSize = false;
  public boolean hasGzippedDownloadUrl = false;
  public boolean hasId = false;
  public boolean hasSignature = false;
  public String id = "";
  public AndroidAppPatchData patchData = null;
  public String signature = "";
  
  public SplitDeliveryData()
  {
    this.cachedSize = -1;
  }
  
  public static SplitDeliveryData[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new SplitDeliveryData[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasId) || (!this.id.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.id);
    }
    if ((this.hasDownloadSize) || (this.downloadSize != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(2, this.downloadSize);
    }
    if ((this.hasGzippedDownloadSize) || (this.gzippedDownloadSize != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(3, this.gzippedDownloadSize);
    }
    if ((this.hasSignature) || (!this.signature.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.signature);
    }
    if ((this.hasDownloadUrl) || (!this.downloadUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.downloadUrl);
    }
    if ((this.hasGzippedDownloadUrl) || (!this.gzippedDownloadUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(6, this.gzippedDownloadUrl);
    }
    if (this.patchData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(7, this.patchData);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasId) || (!this.id.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.id);
    }
    if ((this.hasDownloadSize) || (this.downloadSize != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(2, this.downloadSize);
    }
    if ((this.hasGzippedDownloadSize) || (this.gzippedDownloadSize != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(3, this.gzippedDownloadSize);
    }
    if ((this.hasSignature) || (!this.signature.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.signature);
    }
    if ((this.hasDownloadUrl) || (!this.downloadUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.downloadUrl);
    }
    if ((this.hasGzippedDownloadUrl) || (!this.gzippedDownloadUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(6, this.gzippedDownloadUrl);
    }
    if (this.patchData != null) {
      paramCodedOutputByteBufferNano.writeMessage(7, this.patchData);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SplitDeliveryData
 * JD-Core Version:    0.7.0.1
 */