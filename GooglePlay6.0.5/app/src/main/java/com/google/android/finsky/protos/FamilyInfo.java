package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class FamilyInfo
  extends MessageNano
{
  public Family family = null;
  public int familyMembershipStatus = 0;
  public boolean hasFamilyMembershipStatus = false;
  
  public FamilyInfo()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.familyMembershipStatus != 0) || (this.hasFamilyMembershipStatus)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.familyMembershipStatus);
    }
    if (this.family != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.family);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.familyMembershipStatus != 0) || (this.hasFamilyMembershipStatus)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.familyMembershipStatus);
    }
    if (this.family != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.family);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.FamilyInfo
 * JD-Core Version:    0.7.0.1
 */