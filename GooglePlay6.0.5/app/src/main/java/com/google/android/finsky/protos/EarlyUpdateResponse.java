package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class EarlyUpdateResponse
  extends MessageNano
{
  public EarlyDocumentInfo[] earlyDocumentInfo = EarlyDocumentInfo.emptyArray();
  
  public EarlyUpdateResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.earlyDocumentInfo != null) && (this.earlyDocumentInfo.length > 0)) {
      for (int j = 0; j < this.earlyDocumentInfo.length; j++)
      {
        EarlyDocumentInfo localEarlyDocumentInfo = this.earlyDocumentInfo[j];
        if (localEarlyDocumentInfo != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localEarlyDocumentInfo);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.earlyDocumentInfo != null) && (this.earlyDocumentInfo.length > 0)) {
      for (int i = 0; i < this.earlyDocumentInfo.length; i++)
      {
        EarlyDocumentInfo localEarlyDocumentInfo = this.earlyDocumentInfo[i];
        if (localEarlyDocumentInfo != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localEarlyDocumentInfo);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.EarlyUpdateResponse
 * JD-Core Version:    0.7.0.1
 */