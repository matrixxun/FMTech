package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class FileMetadata
  extends MessageNano
{
  private static volatile FileMetadata[] _emptyArray;
  public int fileType = 0;
  public boolean hasFileType = false;
  public boolean hasSize = false;
  public boolean hasSplitId = false;
  public boolean hasVersionCode = false;
  public long size = 0L;
  public String splitId = "";
  public int versionCode = 0;
  
  public FileMetadata()
  {
    this.cachedSize = -1;
  }
  
  public static FileMetadata[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new FileMetadata[0];
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
    if ((this.hasSplitId) || (!this.splitId.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.splitId);
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
    if ((this.hasSplitId) || (!this.splitId.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.splitId);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.FileMetadata
 * JD-Core Version:    0.7.0.1
 */