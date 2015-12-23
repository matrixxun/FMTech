package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class OBSOLETE_Reason
  extends MessageNano
{
  public String briefReason = "";
  public boolean hasBriefReason = false;
  public boolean hasOBSOLETEDetailedReason = false;
  public boolean hasUniqueId = false;
  public String oBSOLETEDetailedReason = "";
  public String uniqueId = "";
  
  public OBSOLETE_Reason()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasBriefReason) || (!this.briefReason.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.briefReason);
    }
    if ((this.hasOBSOLETEDetailedReason) || (!this.oBSOLETEDetailedReason.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.oBSOLETEDetailedReason);
    }
    if ((this.hasUniqueId) || (!this.uniqueId.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.uniqueId);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasBriefReason) || (!this.briefReason.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.briefReason);
    }
    if ((this.hasOBSOLETEDetailedReason) || (!this.oBSOLETEDetailedReason.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.oBSOLETEDetailedReason);
    }
    if ((this.hasUniqueId) || (!this.uniqueId.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.uniqueId);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.OBSOLETE_Reason
 * JD-Core Version:    0.7.0.1
 */