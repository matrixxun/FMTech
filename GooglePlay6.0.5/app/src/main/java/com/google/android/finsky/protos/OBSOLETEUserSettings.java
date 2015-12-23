package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class OBSOLETEUserSettings
  extends MessageNano
{
  public boolean hasTosCheckboxMarketingEmailsOptedIn = false;
  public PrivacySetting[] privacySetting = PrivacySetting.emptyArray();
  public boolean tosCheckboxMarketingEmailsOptedIn = false;
  
  public OBSOLETEUserSettings()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasTosCheckboxMarketingEmailsOptedIn) || (this.tosCheckboxMarketingEmailsOptedIn)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
    }
    if ((this.privacySetting != null) && (this.privacySetting.length > 0)) {
      for (int j = 0; j < this.privacySetting.length; j++)
      {
        PrivacySetting localPrivacySetting = this.privacySetting[j];
        if (localPrivacySetting != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, localPrivacySetting);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTosCheckboxMarketingEmailsOptedIn) || (this.tosCheckboxMarketingEmailsOptedIn)) {
      paramCodedOutputByteBufferNano.writeBool(1, this.tosCheckboxMarketingEmailsOptedIn);
    }
    if ((this.privacySetting != null) && (this.privacySetting.length > 0)) {
      for (int i = 0; i < this.privacySetting.length; i++)
      {
        PrivacySetting localPrivacySetting = this.privacySetting[i];
        if (localPrivacySetting != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, localPrivacySetting);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.OBSOLETEUserSettings
 * JD-Core Version:    0.7.0.1
 */