package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PlayDocument
  extends MessageNano
{
  public String detailsUrl = "";
  public Common.Docid docid = null;
  public String docidStr = "";
  public boolean hasDetailsUrl = false;
  public boolean hasDocidStr = false;
  public boolean hasSubtitle = false;
  public boolean hasTitle = false;
  public Common.Image[] image = Common.Image.emptyArray();
  public String subtitle = "";
  public String title = "";
  
  public PlayDocument()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.docid != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.docid);
    }
    if ((this.hasDocidStr) || (!this.docidStr.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.docidStr);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.title);
    }
    if ((this.hasSubtitle) || (!this.subtitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.subtitle);
    }
    if ((this.image != null) && (this.image.length > 0)) {
      for (int j = 0; j < this.image.length; j++)
      {
        Common.Image localImage = this.image[j];
        if (localImage != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(5, localImage);
        }
      }
    }
    if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(6, this.detailsUrl);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.docid != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.docid);
    }
    if ((this.hasDocidStr) || (!this.docidStr.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.docidStr);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.title);
    }
    if ((this.hasSubtitle) || (!this.subtitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.subtitle);
    }
    if ((this.image != null) && (this.image.length > 0)) {
      for (int i = 0; i < this.image.length; i++)
      {
        Common.Image localImage = this.image[i];
        if (localImage != null) {
          paramCodedOutputByteBufferNano.writeMessage(5, localImage);
        }
      }
    }
    if ((this.hasDetailsUrl) || (!this.detailsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(6, this.detailsUrl);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PlayDocument
 * JD-Core Version:    0.7.0.1
 */