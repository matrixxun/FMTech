package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class SurveyAnswer
  extends MessageNano
{
  private static volatile SurveyAnswer[] _emptyArray;
  public int answerType = 0;
  public String description = "";
  public boolean hasAnswerType = false;
  public boolean hasDescription = false;
  public Common.Image icon = null;
  
  public SurveyAnswer()
  {
    this.cachedSize = -1;
  }
  
  public static SurveyAnswer[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new SurveyAnswer[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.answerType != 0) || (this.hasAnswerType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.answerType);
    }
    if (this.icon != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.icon);
    }
    if ((this.hasDescription) || (!this.description.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.description);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.answerType != 0) || (this.hasAnswerType)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.answerType);
    }
    if (this.icon != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.icon);
    }
    if ((this.hasDescription) || (!this.description.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.description);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SurveyAnswer
 * JD-Core Version:    0.7.0.1
 */