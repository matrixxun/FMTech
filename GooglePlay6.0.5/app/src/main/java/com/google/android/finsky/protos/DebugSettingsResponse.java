package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class DebugSettingsResponse
  extends MessageNano
{
  public boolean hasPlayCountryDebugInfo = false;
  public boolean hasPlayCountryOverride = false;
  public String playCountryDebugInfo = "";
  public String playCountryOverride = "";
  
  public DebugSettingsResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasPlayCountryOverride) || (!this.playCountryOverride.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.playCountryOverride);
    }
    if ((this.hasPlayCountryDebugInfo) || (!this.playCountryDebugInfo.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.playCountryDebugInfo);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasPlayCountryOverride) || (!this.playCountryOverride.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.playCountryOverride);
    }
    if ((this.hasPlayCountryDebugInfo) || (!this.playCountryDebugInfo.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.playCountryDebugInfo);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.DebugSettingsResponse
 * JD-Core Version:    0.7.0.1
 */