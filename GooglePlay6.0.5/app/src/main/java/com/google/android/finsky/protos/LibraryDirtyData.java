package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class LibraryDirtyData
  extends MessageNano
{
  public int backend = 0;
  public boolean hasBackend = false;
  public boolean hasLibraryId = false;
  public String libraryId = "";
  
  public LibraryDirtyData()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.backend != 0) || (this.hasBackend)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.backend);
    }
    if ((this.hasLibraryId) || (!this.libraryId.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.libraryId);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.backend != 0) || (this.hasBackend)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.backend);
    }
    if ((this.hasLibraryId) || (!this.libraryId.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.libraryId);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.LibraryDirtyData
 * JD-Core Version:    0.7.0.1
 */