package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class AppFileMetadata
  extends MessageNano
{
  private static volatile AppFileMetadata[] _emptyArray;
  public String compressedDownloadUrl = "";
  public long compressedSize = 0L;
  public String downloadUrl = "";
  public int fileType = 0;
  public boolean hasCompressedDownloadUrl = false;
  public boolean hasCompressedSize = false;
  public boolean hasDownloadUrl = false;
  public boolean hasFileType = false;
  public boolean hasSize = false;
  public boolean hasVersionCode = false;
  public AndroidAppPatchData patchData = null;
  public long size = 0L;
  public int versionCode = 0;
  
  public AppFileMetadata()
  {
    this.cachedSize = -1;
  }
  
  public static AppFileMetadata[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new AppFileMetadata[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.fileType != 0) || (this.hasFileType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.fileType);
    }
    if ((this.hasVersionCode) || (this.versionCode != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.versionCode);
    }
    if ((this.hasSize) || (this.size != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(3, this.size);
    }
    if ((this.hasDownloadUrl) || (!this.downloadUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.downloadUrl);
    }
    if (this.patchData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(5, this.patchData);
    }
    if ((this.hasCompressedSize) || (this.compressedSize != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(6, this.compressedSize);
    }
    if ((this.hasCompressedDownloadUrl) || (!this.compressedDownloadUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(7, this.compressedDownloadUrl);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.fileType != 0) || (this.hasFileType)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.fileType);
    }
    if ((this.hasVersionCode) || (this.versionCode != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.versionCode);
    }
    if ((this.hasSize) || (this.size != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(3, this.size);
    }
    if ((this.hasDownloadUrl) || (!this.downloadUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.downloadUrl);
    }
    if (this.patchData != null) {
      paramCodedOutputByteBufferNano.writeMessage(5, this.patchData);
    }
    if ((this.hasCompressedSize) || (this.compressedSize != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(6, this.compressedSize);
    }
    if ((this.hasCompressedDownloadUrl) || (!this.compressedDownloadUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(7, this.compressedDownloadUrl);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.AppFileMetadata
 * JD-Core Version:    0.7.0.1
 */