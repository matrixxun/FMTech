package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class FamilyPurchaseSettingWarning
  extends MessageNano
{
  public boolean hasWarningMessage = false;
  public boolean hasWarningTitle = false;
  public String warningMessage = "";
  public String warningTitle = "";
  
  public FamilyPurchaseSettingWarning()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasWarningTitle) || (!this.warningTitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.warningTitle);
    }
    if ((this.hasWarningMessage) || (!this.warningMessage.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.warningMessage);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasWarningTitle) || (!this.warningTitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.warningTitle);
    }
    if ((this.hasWarningMessage) || (!this.warningMessage.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.warningMessage);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.FamilyPurchaseSettingWarning
 * JD-Core Version:    0.7.0.1
 */