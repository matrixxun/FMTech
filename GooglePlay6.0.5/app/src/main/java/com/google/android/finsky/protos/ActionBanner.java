package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ActionBanner
  extends MessageNano
{
  public CallToAction[] action = CallToAction.emptyArray();
  public DocV2 primaryFace = null;
  public DocV2[] secondaryFace = DocV2.emptyArray();
  
  public ActionBanner()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.action != null) && (this.action.length > 0)) {
      for (int k = 0; k < this.action.length; k++)
      {
        CallToAction localCallToAction = this.action[k];
        if (localCallToAction != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localCallToAction);
        }
      }
    }
    if (this.primaryFace != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.primaryFace);
    }
    if ((this.secondaryFace != null) && (this.secondaryFace.length > 0)) {
      for (int j = 0; j < this.secondaryFace.length; j++)
      {
        DocV2 localDocV2 = this.secondaryFace[j];
        if (localDocV2 != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(4, localDocV2);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.action != null) && (this.action.length > 0)) {
      for (int j = 0; j < this.action.length; j++)
      {
        CallToAction localCallToAction = this.action[j];
        if (localCallToAction != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localCallToAction);
        }
      }
    }
    if (this.primaryFace != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.primaryFace);
    }
    if ((this.secondaryFace != null) && (this.secondaryFace.length > 0)) {
      for (int i = 0; i < this.secondaryFace.length; i++)
      {
        DocV2 localDocV2 = this.secondaryFace[i];
        if (localDocV2 != null) {
          paramCodedOutputByteBufferNano.writeMessage(4, localDocV2);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ActionBanner
 * JD-Core Version:    0.7.0.1
 */