package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ServerCommands
  extends MessageNano
{
  public boolean clearCache = false;
  public String displayErrorMessage = "";
  public boolean hasClearCache = false;
  public boolean hasDisplayErrorMessage = false;
  public boolean hasLogErrorStacktrace = false;
  public String logErrorStacktrace = "";
  public UserSettingDirtyData[] userSettingDirtyData = UserSettingDirtyData.emptyArray();
  
  public ServerCommands()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasClearCache) || (this.clearCache)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
    }
    if ((this.hasDisplayErrorMessage) || (!this.displayErrorMessage.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.displayErrorMessage);
    }
    if ((this.hasLogErrorStacktrace) || (!this.logErrorStacktrace.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.logErrorStacktrace);
    }
    if ((this.userSettingDirtyData != null) && (this.userSettingDirtyData.length > 0)) {
      for (int j = 0; j < this.userSettingDirtyData.length; j++)
      {
        UserSettingDirtyData localUserSettingDirtyData = this.userSettingDirtyData[j];
        if (localUserSettingDirtyData != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(4, localUserSettingDirtyData);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasClearCache) || (this.clearCache)) {
      paramCodedOutputByteBufferNano.writeBool(1, this.clearCache);
    }
    if ((this.hasDisplayErrorMessage) || (!this.displayErrorMessage.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.displayErrorMessage);
    }
    if ((this.hasLogErrorStacktrace) || (!this.logErrorStacktrace.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.logErrorStacktrace);
    }
    if ((this.userSettingDirtyData != null) && (this.userSettingDirtyData.length > 0)) {
      for (int i = 0; i < this.userSettingDirtyData.length; i++)
      {
        UserSettingDirtyData localUserSettingDirtyData = this.userSettingDirtyData[i];
        if (localUserSettingDirtyData != null) {
          paramCodedOutputByteBufferNano.writeMessage(4, localUserSettingDirtyData);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ServerCommands
 * JD-Core Version:    0.7.0.1
 */