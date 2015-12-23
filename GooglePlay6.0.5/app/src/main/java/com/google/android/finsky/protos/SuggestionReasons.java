package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class SuggestionReasons
  extends MessageNano
{
  public Dismissal neutralDismissal = null;
  public Dismissal positiveDismissal = null;
  public Reason[] reason = Reason.emptyArray();
  
  public SuggestionReasons()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.reason != null) && (this.reason.length > 0)) {
      for (int j = 0; j < this.reason.length; j++)
      {
        Reason localReason = this.reason[j];
        if (localReason != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, localReason);
        }
      }
    }
    if (this.neutralDismissal != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(4, this.neutralDismissal);
    }
    if (this.positiveDismissal != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(5, this.positiveDismissal);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.reason != null) && (this.reason.length > 0)) {
      for (int i = 0; i < this.reason.length; i++)
      {
        Reason localReason = this.reason[i];
        if (localReason != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, localReason);
        }
      }
    }
    if (this.neutralDismissal != null) {
      paramCodedOutputByteBufferNano.writeMessage(4, this.neutralDismissal);
    }
    if (this.positiveDismissal != null) {
      paramCodedOutputByteBufferNano.writeMessage(5, this.positiveDismissal);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SuggestionReasons
 * JD-Core Version:    0.7.0.1
 */