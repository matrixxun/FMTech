package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface UserActivity
{
  public static final class RecordUserActivityResponse
    extends MessageNano
  {
    public RecordUserActivityResponse()
    {
      this.cachedSize = -1;
    }
  }
  
  public static final class UserActivitySettingsResponse
    extends MessageNano
  {
    public DocV2 currentUser = null;
    public boolean hasOptIn = false;
    public boolean hasSettingsDescription = false;
    public boolean hasSettingsTitle = false;
    public boolean hasSettingsTosHtml = false;
    public boolean optIn = false;
    public String settingsDescription = "";
    public String settingsTitle = "";
    public String settingsTosHtml = "";
    
    public UserActivitySettingsResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.currentUser != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.currentUser);
      }
      if ((this.hasSettingsTitle) || (!this.settingsTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.settingsTitle);
      }
      if ((this.hasSettingsDescription) || (!this.settingsDescription.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.settingsDescription);
      }
      if ((this.hasSettingsTosHtml) || (!this.settingsTosHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.settingsTosHtml);
      }
      if ((this.hasOptIn) || (this.optIn)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.currentUser != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.currentUser);
      }
      if ((this.hasSettingsTitle) || (!this.settingsTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.settingsTitle);
      }
      if ((this.hasSettingsDescription) || (!this.settingsDescription.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.settingsDescription);
      }
      if ((this.hasSettingsTosHtml) || (!this.settingsTosHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.settingsTosHtml);
      }
      if ((this.hasOptIn) || (this.optIn)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.optIn);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.UserActivity
 * JD-Core Version:    0.7.0.1
 */