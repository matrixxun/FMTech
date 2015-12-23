package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Family
  extends MessageNano
{
  public FamilyMember[] member = FamilyMember.emptyArray();
  
  public Family()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.member != null) && (this.member.length > 0)) {
      for (int j = 0; j < this.member.length; j++)
      {
        FamilyMember localFamilyMember = this.member[j];
        if (localFamilyMember != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localFamilyMember);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.member != null) && (this.member.length > 0)) {
      for (int i = 0; i < this.member.length; i++)
      {
        FamilyMember localFamilyMember = this.member[i];
        if (localFamilyMember != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localFamilyMember);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Family
 * JD-Core Version:    0.7.0.1
 */