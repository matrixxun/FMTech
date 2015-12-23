package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Survey
  extends MessageNano
{
  private static volatile Survey[] _emptyArray;
  public SurveyContent content = null;
  public int context = 0;
  public boolean hasContext = false;
  public boolean hasId = false;
  public long id = 0L;
  public Prompt prompt = null;
  
  public Survey()
  {
    this.cachedSize = -1;
  }
  
  public static Survey[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new Survey[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasId) || (this.id != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(1, this.id);
    }
    if (this.prompt != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.prompt);
    }
    if (this.content != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.content);
    }
    if ((this.context != 0) || (this.hasContext)) {
      i += CodedOutputByteBufferNano.computeInt32Size(4, this.context);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasId) || (this.id != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(1, this.id);
    }
    if (this.prompt != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.prompt);
    }
    if (this.content != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.content);
    }
    if ((this.context != 0) || (this.hasContext)) {
      paramCodedOutputByteBufferNano.writeInt32(4, this.context);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Survey
 * JD-Core Version:    0.7.0.1
 */