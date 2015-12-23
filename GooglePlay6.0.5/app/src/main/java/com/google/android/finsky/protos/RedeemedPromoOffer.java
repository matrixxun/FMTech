package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class RedeemedPromoOffer
  extends MessageNano
{
  public String descriptionHtml = "";
  public boolean hasDescriptionHtml = false;
  public boolean hasHeaderText = false;
  public String headerText = "";
  public Common.Image image = null;
  
  public RedeemedPromoOffer()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasHeaderText) || (!this.headerText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.headerText);
    }
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.descriptionHtml);
    }
    if (this.image != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.image);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasHeaderText) || (!this.headerText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.headerText);
    }
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.descriptionHtml);
    }
    if (this.image != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.image);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.RedeemedPromoOffer
 * JD-Core Version:    0.7.0.1
 */