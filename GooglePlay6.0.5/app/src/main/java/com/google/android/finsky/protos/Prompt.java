package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Prompt
  extends MessageNano
{
  public String acceptButtonText = "";
  public boolean hasAcceptButtonText = false;
  public boolean hasPromptText = false;
  public boolean hasRejectButtonText = false;
  public String promptText = "";
  public String rejectButtonText = "";
  
  public Prompt()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasPromptText) || (!this.promptText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.promptText);
    }
    if ((this.hasAcceptButtonText) || (!this.acceptButtonText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.acceptButtonText);
    }
    if ((this.hasRejectButtonText) || (!this.rejectButtonText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.rejectButtonText);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasPromptText) || (!this.promptText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.promptText);
    }
    if ((this.hasAcceptButtonText) || (!this.acceptButtonText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.acceptButtonText);
    }
    if ((this.hasRejectButtonText) || (!this.rejectButtonText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.rejectButtonText);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Prompt
 * JD-Core Version:    0.7.0.1
 */