package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PrivacySettings
  extends MessageNano
{
  public PrivacySetting[] privacySetting = PrivacySetting.emptyArray();
  
  public PrivacySettings()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.privacySetting != null) && (this.privacySetting.length > 0)) {
      for (int j = 0; j < this.privacySetting.length; j++)
      {
        PrivacySetting localPrivacySetting = this.privacySetting[j];
        if (localPrivacySetting != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localPrivacySetting);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.privacySetting != null) && (this.privacySetting.length > 0)) {
      for (int i = 0; i < this.privacySetting.length; i++)
      {
        PrivacySetting localPrivacySetting = this.privacySetting[i];
        if (localPrivacySetting != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localPrivacySetting);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PrivacySettings
 * JD-Core Version:    0.7.0.1
 */