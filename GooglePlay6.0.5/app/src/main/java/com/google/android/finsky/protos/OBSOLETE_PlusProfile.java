package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class OBSOLETE_PlusProfile
  extends MessageNano
{
  private static volatile OBSOLETE_PlusProfile[] _emptyArray;
  public String displayName = "";
  public boolean hasDisplayName = false;
  public boolean hasProfileImageUrl = false;
  public Common.Image profileImage = null;
  public String profileImageUrl = "";
  
  public OBSOLETE_PlusProfile()
  {
    this.cachedSize = -1;
  }
  
  public static OBSOLETE_PlusProfile[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new OBSOLETE_PlusProfile[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasDisplayName) || (!this.displayName.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.displayName);
    }
    if ((this.hasProfileImageUrl) || (!this.profileImageUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.profileImageUrl);
    }
    if (this.profileImage != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(5, this.profileImage);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasDisplayName) || (!this.displayName.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.displayName);
    }
    if ((this.hasProfileImageUrl) || (!this.profileImageUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.profileImageUrl);
    }
    if (this.profileImage != null) {
      paramCodedOutputByteBufferNano.writeMessage(5, this.profileImage);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.OBSOLETE_PlusProfile
 * JD-Core Version:    0.7.0.1
 */