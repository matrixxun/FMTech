package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class SurveyResponse
  extends MessageNano
{
  public Survey[] survey = Survey.emptyArray();
  
  public SurveyResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.survey != null) && (this.survey.length > 0)) {
      for (int j = 0; j < this.survey.length; j++)
      {
        Survey localSurvey = this.survey[j];
        if (localSurvey != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localSurvey);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.survey != null) && (this.survey.length > 0)) {
      for (int i = 0; i < this.survey.length; i++)
      {
        Survey localSurvey = this.survey[i];
        if (localSurvey != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localSurvey);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SurveyResponse
 * JD-Core Version:    0.7.0.1
 */