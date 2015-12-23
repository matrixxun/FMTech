package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Onboardings
  extends MessageNano
{
  public Onboarding[] onboarding = Onboarding.emptyArray();
  
  public Onboardings()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.onboarding != null) && (this.onboarding.length > 0)) {
      for (int j = 0; j < this.onboarding.length; j++)
      {
        Onboarding localOnboarding = this.onboarding[j];
        if (localOnboarding != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localOnboarding);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.onboarding != null) && (this.onboarding.length > 0)) {
      for (int i = 0; i < this.onboarding.length; i++)
      {
        Onboarding localOnboarding = this.onboarding[i];
        if (localOnboarding != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localOnboarding);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Onboardings
 * JD-Core Version:    0.7.0.1
 */