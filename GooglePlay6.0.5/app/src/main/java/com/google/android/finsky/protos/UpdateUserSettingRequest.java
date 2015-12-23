package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class UpdateUserSettingRequest
  extends MessageNano
{
  public int completedOnboarding = 0;
  public boolean hasCompletedOnboarding = false;
  public MarketingSettings marketingSettings = null;
  public PrivacySetting privacySetting = null;
  
  public UpdateUserSettingRequest()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.marketingSettings != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.marketingSettings);
    }
    if (this.privacySetting != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.privacySetting);
    }
    if ((this.completedOnboarding != 0) || (this.hasCompletedOnboarding)) {
      i += CodedOutputByteBufferNano.computeInt32Size(4, this.completedOnboarding);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.marketingSettings != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.marketingSettings);
    }
    if (this.privacySetting != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.privacySetting);
    }
    if ((this.completedOnboarding != 0) || (this.hasCompletedOnboarding)) {
      paramCodedOutputByteBufferNano.writeInt32(4, this.completedOnboarding);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.UpdateUserSettingRequest
 * JD-Core Version:    0.7.0.1
 */