package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PromotedDoc
  extends MessageNano
{
  private static volatile PromotedDoc[] _emptyArray;
  public String descriptionHtml = "";
  public String detailsUrl = "";
  public boolean hasDescriptionHtml = false;
  public boolean hasDetailsUrl = false;
  public boolean hasSubtitle = false;
  public boolean hasTitle = false;
  public Common.Image[] image = Common.Image.emptyArray();
  public String subtitle = "";
  public String title = "";
  
  public PromotedDoc()
  {
    this.cachedSize = -1;
  }
  
  public static PromotedDoc[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new PromotedDoc[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
    }
    if ((this.hasSubtitle) || (!this.subtitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.subtitle);
    }
    if ((this.image != null) && (this.image.length > 0)) {
      for (int j = 0; j < this.image.length; j++)
      {
        Common.Image localImage = this.image[j];
        if (localImage != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(3, localImage);
        }
      }
    }
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.descriptionHtml);
    }
    if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.detailsUrl);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.title);
    }
    if ((this.hasSubtitle) || (!this.subtitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.subtitle);
    }
    if ((this.image != null) && (this.image.length > 0)) {
      for (int i = 0; i < this.image.length; i++)
      {
        Common.Image localImage = this.image[i];
        if (localImage != null) {
          paramCodedOutputByteBufferNano.writeMessage(3, localImage);
        }
      }
    }
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.descriptionHtml);
    }
    if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.detailsUrl);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PromotedDoc
 * JD-Core Version:    0.7.0.1
 */