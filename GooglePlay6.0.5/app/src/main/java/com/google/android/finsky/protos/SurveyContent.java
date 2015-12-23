package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class SurveyContent
  extends MessageNano
{
  public SurveyAnswer[] answer = SurveyAnswer.emptyArray();
  public boolean hasQuestion = false;
  public boolean hasTitle = false;
  public String question = "";
  public String title = "";
  
  public SurveyContent()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
    }
    if ((this.hasQuestion) || (!this.question.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.question);
    }
    if ((this.answer != null) && (this.answer.length > 0)) {
      for (int j = 0; j < this.answer.length; j++)
      {
        SurveyAnswer localSurveyAnswer = this.answer[j];
        if (localSurveyAnswer != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(3, localSurveyAnswer);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.title);
    }
    if ((this.hasQuestion) || (!this.question.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.question);
    }
    if ((this.answer != null) && (this.answer.length > 0)) {
      for (int i = 0; i < this.answer.length; i++)
      {
        SurveyAnswer localSurveyAnswer = this.answer[i];
        if (localSurveyAnswer != null) {
          paramCodedOutputByteBufferNano.writeMessage(3, localSurveyAnswer);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SurveyContent
 * JD-Core Version:    0.7.0.1
 */