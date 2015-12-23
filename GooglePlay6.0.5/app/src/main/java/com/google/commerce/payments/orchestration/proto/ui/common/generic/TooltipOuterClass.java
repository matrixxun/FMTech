package com.google.commerce.payments.orchestration.proto.ui.common.generic;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface TooltipOuterClass
{
  public static final class Tooltip
    extends MessageNano
  {
    public ImageWithCaptionOuterClass.ImageWithCaption icon = null;
    public ImageWithCaptionOuterClass.ImageWithCaption[] image = ImageWithCaptionOuterClass.ImageWithCaption.emptyArray();
    public InfoMessageOuterClass.InfoMessage infoMessage = null;
    public String title = "";
    
    public Tooltip()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.icon != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.icon);
      }
      if (!this.title.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.title);
      }
      if (this.infoMessage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.infoMessage);
      }
      if ((this.image != null) && (this.image.length > 0)) {
        for (int j = 0; j < this.image.length; j++)
        {
          ImageWithCaptionOuterClass.ImageWithCaption localImageWithCaption = this.image[j];
          if (localImageWithCaption != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(6, localImageWithCaption);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.icon != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.icon);
      }
      if (!this.title.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.title);
      }
      if (this.infoMessage != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.infoMessage);
      }
      if ((this.image != null) && (this.image.length > 0)) {
        for (int i = 0; i < this.image.length; i++)
        {
          ImageWithCaptionOuterClass.ImageWithCaption localImageWithCaption = this.image[i];
          if (localImageWithCaption != null) {
            paramCodedOutputByteBufferNano.writeMessage(6, localImageWithCaption);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.generic.TooltipOuterClass
 * JD-Core Version:    0.7.0.1
 */