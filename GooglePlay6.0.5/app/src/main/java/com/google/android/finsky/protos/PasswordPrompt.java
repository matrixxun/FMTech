package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PasswordPrompt
  extends MessageNano
{
  public String forgotPasswordUrl = "";
  public boolean hasForgotPasswordUrl = false;
  public boolean hasPrompt = false;
  public String prompt = "";
  
  public PasswordPrompt()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasPrompt) || (!this.prompt.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.prompt);
    }
    if ((this.hasForgotPasswordUrl) || (!this.forgotPasswordUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.forgotPasswordUrl);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasPrompt) || (!this.prompt.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.prompt);
    }
    if ((this.hasForgotPasswordUrl) || (!this.forgotPasswordUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.forgotPasswordUrl);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PasswordPrompt
 * JD-Core Version:    0.7.0.1
 */