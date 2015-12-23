package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Badge
  extends MessageNano
{
  private static volatile Badge[] _emptyArray;
  public String browseUrl = "";
  public String description = "";
  public Common.Image expandedBadgeImage = null;
  public boolean hasBrowseUrl = false;
  public boolean hasDescription = false;
  public boolean hasTextInTitleSection = false;
  public boolean hasTitle = false;
  public Common.Image[] image = Common.Image.emptyArray();
  public String textInTitleSection = "";
  public String title = "";
  
  public Badge()
  {
    this.cachedSize = -1;
  }
  
  public static Badge[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new Badge[0];
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
    if ((this.image != null) && (this.image.length > 0)) {
      for (int j = 0; j < this.image.length; j++)
      {
        Common.Image localImage = this.image[j];
        if (localImage != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, localImage);
        }
      }
    }
    if ((this.hasBrowseUrl) || (!this.browseUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.browseUrl);
    }
    if ((this.hasDescription) || (!this.description.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.description);
    }
    if ((this.hasTextInTitleSection) || (!this.textInTitleSection.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.textInTitleSection);
    }
    if (this.expandedBadgeImage != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(6, this.expandedBadgeImage);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.title);
    }
    if ((this.image != null) && (this.image.length > 0)) {
      for (int i = 0; i < this.image.length; i++)
      {
        Common.Image localImage = this.image[i];
        if (localImage != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, localImage);
        }
      }
    }
    if ((this.hasBrowseUrl) || (!this.browseUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.browseUrl);
    }
    if ((this.hasDescription) || (!this.description.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.description);
    }
    if ((this.hasTextInTitleSection) || (!this.textInTitleSection.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.textInTitleSection);
    }
    if (this.expandedBadgeImage != null) {
      paramCodedOutputByteBufferNano.writeMessage(6, this.expandedBadgeImage);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Badge
 * JD-Core Version:    0.7.0.1
 */