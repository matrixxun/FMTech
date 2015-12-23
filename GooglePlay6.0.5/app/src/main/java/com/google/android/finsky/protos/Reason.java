package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Reason
  extends MessageNano
{
  private static volatile Reason[] _emptyArray;
  public String descriptionHtml = "";
  public Dismissal dismissal = null;
  public boolean hasDescriptionHtml = false;
  public ReasonPlusOne reasonPlusOne = null;
  public ReasonReview reasonReview = null;
  public ReasonUserAction reasonUserAction = null;
  
  public Reason()
  {
    this.cachedSize = -1;
  }
  
  public static Reason[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new Reason[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.descriptionHtml);
    }
    if (this.reasonPlusOne != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(4, this.reasonPlusOne);
    }
    if (this.reasonReview != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(5, this.reasonReview);
    }
    if (this.dismissal != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(7, this.dismissal);
    }
    if (this.reasonUserAction != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(9, this.reasonUserAction);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.descriptionHtml);
    }
    if (this.reasonPlusOne != null) {
      paramCodedOutputByteBufferNano.writeMessage(4, this.reasonPlusOne);
    }
    if (this.reasonReview != null) {
      paramCodedOutputByteBufferNano.writeMessage(5, this.reasonReview);
    }
    if (this.dismissal != null) {
      paramCodedOutputByteBufferNano.writeMessage(7, this.dismissal);
    }
    if (this.reasonUserAction != null) {
      paramCodedOutputByteBufferNano.writeMessage(9, this.reasonUserAction);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Reason
 * JD-Core Version:    0.7.0.1
 */