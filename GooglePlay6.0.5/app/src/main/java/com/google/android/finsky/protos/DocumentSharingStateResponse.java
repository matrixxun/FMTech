package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class DocumentSharingStateResponse
  extends MessageNano
{
  public LibraryUpdateProto.LibraryUpdate[] libraryUpdate = LibraryUpdateProto.LibraryUpdate.emptyArray();
  
  public DocumentSharingStateResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.libraryUpdate != null) && (this.libraryUpdate.length > 0)) {
      for (int j = 0; j < this.libraryUpdate.length; j++)
      {
        LibraryUpdateProto.LibraryUpdate localLibraryUpdate = this.libraryUpdate[j];
        if (localLibraryUpdate != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localLibraryUpdate);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.libraryUpdate != null) && (this.libraryUpdate.length > 0)) {
      for (int i = 0; i < this.libraryUpdate.length; i++)
      {
        LibraryUpdateProto.LibraryUpdate localLibraryUpdate = this.libraryUpdate[i];
        if (localLibraryUpdate != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localLibraryUpdate);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.DocumentSharingStateResponse
 * JD-Core Version:    0.7.0.1
 */