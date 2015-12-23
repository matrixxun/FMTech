package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ContainerWithNotice
  extends MessageNano
{
  public CallToAction action = null;
  public String explanationTextHtml = "";
  public boolean hasExplanationTextHtml = false;
  public boolean hasSettingType = false;
  public int settingType = 0;
  
  public ContainerWithNotice()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasExplanationTextHtml) || (!this.explanationTextHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.explanationTextHtml);
    }
    if ((this.settingType != 0) || (this.hasSettingType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.settingType);
    }
    if (this.action != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.action);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasExplanationTextHtml) || (!this.explanationTextHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.explanationTextHtml);
    }
    if ((this.settingType != 0) || (this.hasSettingType)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.settingType);
    }
    if (this.action != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.action);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ContainerWithNotice
 * JD-Core Version:    0.7.0.1
 */