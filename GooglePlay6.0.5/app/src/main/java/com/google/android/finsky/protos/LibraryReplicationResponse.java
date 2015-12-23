package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class LibraryReplicationResponse
  extends MessageNano
{
  public String[] autoAcquireFreeAppIfHigherVersionAvailableTag = WireFormatNano.EMPTY_STRING_ARRAY;
  public LibraryUpdateProto.LibraryUpdate[] update = LibraryUpdateProto.LibraryUpdate.emptyArray();
  
  public LibraryReplicationResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.update != null) && (this.update.length > 0)) {
      for (int n = 0; n < this.update.length; n++)
      {
        LibraryUpdateProto.LibraryUpdate localLibraryUpdate = this.update[n];
        if (localLibraryUpdate != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localLibraryUpdate);
        }
      }
    }
    if ((this.autoAcquireFreeAppIfHigherVersionAvailableTag != null) && (this.autoAcquireFreeAppIfHigherVersionAvailableTag.length > 0))
    {
      int j = 0;
      int k = 0;
      for (int m = 0; m < this.autoAcquireFreeAppIfHigherVersionAvailableTag.length; m++)
      {
        String str = this.autoAcquireFreeAppIfHigherVersionAvailableTag[m];
        if (str != null)
        {
          j++;
          k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
        }
      }
      i = i + k + j * 1;
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.update != null) && (this.update.length > 0)) {
      for (int j = 0; j < this.update.length; j++)
      {
        LibraryUpdateProto.LibraryUpdate localLibraryUpdate = this.update[j];
        if (localLibraryUpdate != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localLibraryUpdate);
        }
      }
    }
    if ((this.autoAcquireFreeAppIfHigherVersionAvailableTag != null) && (this.autoAcquireFreeAppIfHigherVersionAvailableTag.length > 0)) {
      for (int i = 0; i < this.autoAcquireFreeAppIfHigherVersionAvailableTag.length; i++)
      {
        String str = this.autoAcquireFreeAppIfHigherVersionAvailableTag[i];
        if (str != null) {
          paramCodedOutputByteBufferNano.writeString(2, str);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.LibraryReplicationResponse
 * JD-Core Version:    0.7.0.1
 */