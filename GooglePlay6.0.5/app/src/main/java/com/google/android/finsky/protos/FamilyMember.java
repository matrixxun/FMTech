package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class FamilyMember
  extends MessageNano
{
  private static volatile FamilyMember[] _emptyArray;
  public boolean hasRole = false;
  public DocV2 personDocument = null;
  public int role = 0;
  
  public FamilyMember()
  {
    this.cachedSize = -1;
  }
  
  public static FamilyMember[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new FamilyMember[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.role != 0) || (this.hasRole)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.role);
    }
    if (this.personDocument != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.personDocument);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.role != 0) || (this.hasRole)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.role);
    }
    if (this.personDocument != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.personDocument);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.FamilyMember
 * JD-Core Version:    0.7.0.1
 */