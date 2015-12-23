package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class UpdateUserSettingResponse
  extends MessageNano
{
  public UserSettingsConsistencyTokens consistencyTokens = null;
  
  public UpdateUserSettingResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.consistencyTokens != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.consistencyTokens);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.consistencyTokens != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.consistencyTokens);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.UpdateUserSettingResponse
 * JD-Core Version:    0.7.0.1
 */