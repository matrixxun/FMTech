package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class WarmWelcome
  extends MessageNano
{
  public CallToAction[] action = CallToAction.emptyArray();
  
  public WarmWelcome()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.action != null) && (this.action.length > 0)) {
      for (int j = 0; j < this.action.length; j++)
      {
        CallToAction localCallToAction = this.action[j];
        if (localCallToAction != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localCallToAction);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.action != null) && (this.action.length > 0)) {
      for (int i = 0; i < this.action.length; i++)
      {
        CallToAction localCallToAction = this.action[i];
        if (localCallToAction != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localCallToAction);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.WarmWelcome
 * JD-Core Version:    0.7.0.1
 */