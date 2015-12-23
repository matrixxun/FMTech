package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class LibraryReplicationRequest
  extends MessageNano
{
  public boolean hasLibraryMutationVersion = false;
  public int libraryMutationVersion = 0;
  public LibraryUpdateProto.ClientLibraryState[] libraryState = LibraryUpdateProto.ClientLibraryState.emptyArray();
  
  public LibraryReplicationRequest()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.libraryState != null) && (this.libraryState.length > 0)) {
      for (int j = 0; j < this.libraryState.length; j++)
      {
        LibraryUpdateProto.ClientLibraryState localClientLibraryState = this.libraryState[j];
        if (localClientLibraryState != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localClientLibraryState);
        }
      }
    }
    if ((this.hasLibraryMutationVersion) || (this.libraryMutationVersion != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.libraryMutationVersion);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.libraryState != null) && (this.libraryState.length > 0)) {
      for (int i = 0; i < this.libraryState.length; i++)
      {
        LibraryUpdateProto.ClientLibraryState localClientLibraryState = this.libraryState[i];
        if (localClientLibraryState != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localClientLibraryState);
        }
      }
    }
    if ((this.hasLibraryMutationVersion) || (this.libraryMutationVersion != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.libraryMutationVersion);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.LibraryReplicationRequest
 * JD-Core Version:    0.7.0.1
 */