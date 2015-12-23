package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class CallToAction
  extends MessageNano
{
  private static volatile CallToAction[] _emptyArray;
  public Common.Image buttonIcon = null;
  public String buttonText = "";
  public String dismissalUrl = "";
  public boolean hasButtonText = false;
  public boolean hasDismissalUrl = false;
  public boolean hasType = false;
  public Link link = null;
  public int type = 1;
  
  public CallToAction()
  {
    this.cachedSize = -1;
  }
  
  public static CallToAction[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new CallToAction[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.type != 1) || (this.hasType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
    }
    if ((this.hasButtonText) || (!this.buttonText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.buttonText);
    }
    if (this.buttonIcon != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.buttonIcon);
    }
    if ((this.hasDismissalUrl) || (!this.dismissalUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.dismissalUrl);
    }
    if (this.link != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(5, this.link);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.type != 1) || (this.hasType)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.type);
    }
    if ((this.hasButtonText) || (!this.buttonText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.buttonText);
    }
    if (this.buttonIcon != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.buttonIcon);
    }
    if ((this.hasDismissalUrl) || (!this.dismissalUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.dismissalUrl);
    }
    if (this.link != null) {
      paramCodedOutputByteBufferNano.writeMessage(5, this.link);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.CallToAction
 * JD-Core Version:    0.7.0.1
 */