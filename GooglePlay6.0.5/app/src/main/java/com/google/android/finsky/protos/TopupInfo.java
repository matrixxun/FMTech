package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class TopupInfo
  extends MessageNano
{
  public boolean hasOptionsContainerDocidDeprecated = false;
  public boolean hasOptionsListUrl = false;
  public boolean hasSubtitle = false;
  public Common.Docid optionsContainerDocid = null;
  public String optionsContainerDocidDeprecated = "";
  public String optionsListUrl = "";
  public String subtitle = "";
  
  public TopupInfo()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasOptionsContainerDocidDeprecated) || (!this.optionsContainerDocidDeprecated.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.optionsContainerDocidDeprecated);
    }
    if ((this.hasOptionsListUrl) || (!this.optionsListUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.optionsListUrl);
    }
    if ((this.hasSubtitle) || (!this.subtitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.subtitle);
    }
    if (this.optionsContainerDocid != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(4, this.optionsContainerDocid);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasOptionsContainerDocidDeprecated) || (!this.optionsContainerDocidDeprecated.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.optionsContainerDocidDeprecated);
    }
    if ((this.hasOptionsListUrl) || (!this.optionsListUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.optionsListUrl);
    }
    if ((this.hasSubtitle) || (!this.subtitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.subtitle);
    }
    if (this.optionsContainerDocid != null) {
      paramCodedOutputByteBufferNano.writeMessage(4, this.optionsContainerDocid);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.TopupInfo
 * JD-Core Version:    0.7.0.1
 */